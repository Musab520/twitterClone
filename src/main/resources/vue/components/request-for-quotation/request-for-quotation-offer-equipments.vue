<template id="request-for-quotation-offer-equipments">
  <div>
    <v-col class="mt-4 d-flex flex-column justify-space-between pb-8 equipments-summary">
      <v-row class="mx-2 d-flex justify-space-between align-center mr-0 mb-4">
        <v-col>
          <h6 class="text-h6">
            {{ $trans('requestForQuotationThreadPage.equipmentsSection.equipments') }}
          </h6>
        </v-col>
        <v-col class="d-flex justify-end">
          <v-btn
              :disabled="disabled"
              large
              outlined
              :loading="modifyLoading"
              @click="modifyLoading = true; modifyEquipments = true;">
            {{ $trans('requestForQuotationThreadPage.equipmentsSection.modifyEquipments') }}
          </v-btn>
        </v-col>
      </v-row>
      <table class="mx-3 offer-table">
        <tbody>
        <tr>
          <td class="offer-table-cell px-4">
            {{ $trans('requestForQuotationThreadPage.equipmentsSection.totalEquipmentsThisThreadOffers') }}
          </td>
          <td class="offer-table-cell-value px-4">{{ offeredEquipmentsCount }}</td>
        </tr>
        <tr>
          <td class="offer-table-cell px-4">
            {{ $trans('requestForQuotationThreadPage.equipmentsSection.rFQTotalRequestedEquipments') }}
          </td>
          <td class="offer-table-cell-value px-4">{{ requesterEquipmentsCount }}</td>
        </tr>
        <tr>
          <td class="offer-table-cell px-4">
            {{ $trans('requestForQuotationThreadPage.equipmentsSection.rFQAcceptedEquipmentsUntilNow') }}
          </td>
          <td class="offer-table-cell-value px-4">{{ currentlyReservedEquipmentsCount }}</td>
        </tr>
        </tbody>
      </table>
    </v-col>
    <v-col cols="12">
      <v-row dense no-gutters class="mx-3 mt-2">
        <v-col class="offered-equipments-container">
          <v-row
              dense
              class="offered-equipments d-flex">
            <v-col
                v-for="equipment in currentEquipments"
                :key="equipment.id"
                class="flex-grow-0"
                :class="{'flex-grow-1': $vuetify.breakpoint.mdAndDown}"
                cols="12"
                sm="12"
                lg="6">
              <request-for-quotation-offer-equipments-card
                  :id="equipment.id"
                  :production-date="equipment.productionDate"
                  :manufacturer="equipment.manufacturer"
                  :type="equipment.type"
                  :name="equipment.name"
                  :image="equipment.image"
                  :documents="equipment.documents"></request-for-quotation-offer-equipments-card>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-col>
    <request-for-quotation-offer-equipments-modify
        v-if="modifyEquipments"
        :current-equipments="currentEquipments"
        :company-id="companyId"
        @close="modifyEquipments = false"
        @loaded="modifyLoading = false"
        @modified="equipmentsModified">
    </request-for-quotation-offer-equipments-modify>
  </div>
</template>
<script>
Vue.component("request-for-quotation-offer-equipments", {
  template: "#request-for-quotation-offer-equipments",
  props: {
    offeredEquipmentsCount: {
      type: Number,
      required: true,
    },
    requesterEquipmentsCount: {
      type: Number,
      required: true,
    },
    currentlyReservedEquipmentsCount: {
      type: Number,
      required: true,
    },
    currentEquipments: {
      type: Array,
      required: true,
    },
    companyId: {
      type: String,
      required: true,
    },
    disabled: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      modifyEquipments: false,
      modifyLoading: false
    }
  },
  methods: {
    equipmentsModified() {
      this.$emit('modified');
    }
  }
});
</script>
<style scoped>
.offer-table {
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-collapse: collapse;
}

.offer-table-cell {
  border: 1px solid rgba(0, 0, 0, 0.12);
  color: #757575;
  height: 48px;
}

.offer-table-cell-value {
  border: 1px solid rgba(0, 0, 0, 0.12);
}

.equipments-summary {
  border-bottom: 1px solid rgba(0, 0, 0, 0.12);
}

.offered-equipments-container {
  position: relative;
  top: 0;
  left: 0;
}

.offered-equipments {
  position: absolute;
  overflow: auto;
}
</style>