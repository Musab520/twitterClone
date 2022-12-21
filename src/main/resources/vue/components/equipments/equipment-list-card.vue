<template id="equipment-list-card">
    <v-sheet
        class="mx-auto card-remove-border rounded-0 py-4 px-4"
        outlined>
      <v-col>
        <v-col cols="12" sm="4">
            <v-chip rounded
                    readonly
                    class="ms-1 mt-1"
                    :color="getAvailabilityColor(availability)"
                    style="font-weight: normal;font-size: 12px;"
                    small
                    dark>
              {{ availability }}
            </v-chip>
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
                  @click="">
                <v-icon>
                   mdi-Heart-Outline
                </v-icon>
              </v-btn>
            </v-hover>
          </div>
          <div class="d-flex equipment-info-tags body-2 flex-grow-1">
            <span v-if="username" class="mx-1">|</span>
            <span>{{ username }}</span>
            <span v-if="message" class="mx-1">|</span>
            <span>{{ message }}</span>
          </div>
          <div class="d-flex justify-end mt-5 mt-sm-0">
            <v-btn
                outlined
                large
                link
                @click="reserveDialog = true"
                color="primary"
                class="px-2">
              {{ $trans('equipmentsPage.equipmentCard.reserveButton') }}
            </v-btn>
          </div>
        </v-col>
      </v-col>
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
    message: {
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
    username: {
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
.equipment-info-tags {
  color: rgba(0, 0, 0, 0.6);
}

.map-locate-btn {
  background: rgba(16, 35, 56, 0.05);
  border-radius: 4px;
  border: 0;
}

</style>