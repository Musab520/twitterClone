<template id="equipment-list-card">
    <v-sheet
        class="mx-auto card-remove-border rounded-0 py-4 px-4"
        outlined>
      <v-row>
        <v-col cols="12" sm="4">
          <v-img
              class="rounded-lg"
              height="150"
              :src="image ?? '/equipment-placeholder.png'"
          >
            <v-chip rounded
                    readonly
                    class="ms-1 mt-1"
                    :color="getAvailabilityColor(availability)"
                    style="font-weight: normal;font-size: 12px;"
                    small
                    dark>
              {{ availability }}
            </v-chip>
          </v-img>
        </v-col>
        <v-col cols="12" sm="8" class="d-flex flex-column justify-space-between">
          <div class="d-flex justify-space-between">
            <h6 class="title">
              {{ name }}
            </h6>
            <v-hover v-slot="{ hover }">
              <v-btn
                  x-small
                  depressed
                  v-ripple=false
                  outlined
                  height="36"
                  width="36"
                  color="primary"
                  class="map-locate-btn"
                  @click="centerMapOnEquipmentMarker">
                <v-icon :class="{'highlight-map-icon': isMapCenteredForEquipment}">
                  mdi-map-marker-outline
                </v-icon>
              </v-btn>
            </v-hover>
          </div>
          <div class="d-flex equipment-info-tags body-2 flex-grow-1">
            <span>{{ type }}</span>
            <span v-if="manufacturer" class="mx-1">|</span>
            <span>{{ manufacturer }}</span>
            <span v-if="companyName" class="mx-1">|</span>
            <span>{{ companyName }}</span>
          </div>
          <div class="d-flex justify-end mt-5 mt-sm-0">
            <v-btn
                large
                link
                text
                depressed
                v-ripple=false
                color="primary"
                class="me-2 px-2"
                @click="projectListDialog = true">
              <v-icon class="uppercase me-2">
                mdi-book-plus-multiple-outline
              </v-icon>
              {{ $trans('equipmentsPage.equipmentCard.addToProjectButton') }}
            </v-btn>
            <v-btn
                outlined
                large
                link
                :disabled="!canBeReserved"
                @click="reserveDialog = true"
                color="primary"
                class="px-2"
                :class="{'reserved-disabled uppercase': !canBeReserved}">
              {{ $trans('equipmentsPage.equipmentCard.reserveButton') }}
            </v-btn>
          </div>
        </v-col>
      </v-row>
      <equipment-reservation-dialog
        v-if="reserveDialog"
        :name="name"
        :id="id"
        :type="type"
        :manufacturer="manufacturer"
        :production-year="productionYear"
        @close="reserveDialog = false"></equipment-reservation-dialog>
      <project-list-dialog
        v-if="projectListDialog"
        :id="id"
        @close="projectListDialog = false"></project-list-dialog>
    </v-sheet>
</template>
<script>
Vue.component("equipment-list-card", {
  template: "#equipment-list-card",
  props: {
    id: {
      type: String,
      required: true
    },
    companyName: {
      type: String,
      required: true
    },
    name: {
      type: String,
      required: true
    },
    type: {
      type: String,
      required: true
    },
    manufacturer: {
      type: String,
      required: true
    },
    serialNumber: {
      type: String,
      required: true
    },
    image: {
      type: String,
      required: true
    },
    availability: {
      type: String,
      required: true
    },
    canBeReserved: {
      type: Boolean,
      required: true
    },
    longitude: {
      type: String,
      required: true
    },
    latitude: {
      type: String,
      required: true
    },
    isMapCenteredForEquipment: {
      type: Boolean,
      default: false,
    },
    productionYear: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      reserveDialog: false,
      projectListDialog: false
    }
  },
  methods: {
    getAvailabilityColor(availability) {
      return availability === 'Available' ? 'green' : 'red'
    },
    centerMapOnEquipmentMarker() {
      this.isMapCenteredForEquipment = true
      this.$emit('center-map', {
        equipmentId: this.id,
        coordinates: [this.latitude, this.longitude]
      })
    }
  }
});
</script>

<style scoped>
.card-remove-border {
  border-top: none !important;
  border-left: none !important;
  border-right: none !important;
}

.reserved-disabled {
  background-color: rgba(0, 0, 0, 0.12);
}

.equipment-info-tags {
  color: rgba(0, 0, 0, 0.6);
}

.map-locate-btn {
  background: rgba(16, 35, 56, 0.05);
  border-radius: 4px;
  border: 0;
}

.map-add-border {
  border: 0.125rem solid transparent;
}

.highlight-map-icon {
  color: #F9A315 !important;
}

</style>