<template id="request-for-quotation-equipments-and-offers">
  <v-row dense no-gutters class="d-flex justify-space-between">
    <v-row
        dense
        no-gutters
        class="d-flex justify-center align-content-start"
        v-if="equipments.length > 0">
      <v-row class="full-width" dense no-gutters>
        <v-col
            cols="12"
            md="6">
          <v-sheet
              outlined
              class="sheet-card d-flex justify-center align-center flex-column">
            <div>
              <span class="sheet-card-title body-2">
                {{ $trans('requestForQuotationThreadPage.equipmentsAndOffersSection.offeredEquipments') }}
              </span>
            </div>
            <div>
              <span class="sheet-card-value">{{ offeredEquipmentsCount }}</span>
            </div>
          </v-sheet>
        </v-col>
        <v-col
            cols="12"
            md="6">
          <v-sheet
              outlined
              class="sheet-card d-flex justify-center align-center flex-column">
            <div>
              <span class="sheet-card-title body-2">
                {{ $trans('requestForQuotationThreadPage.equipmentsAndOffersSection.totalPrice') }}
              </span>
            </div>
            <div>
              <span class="sheet-card-value">{{ totalPrice | formatNumberAsCurrency }}</span>
            </div>
          </v-sheet>
        </v-col>
      </v-row>
      <v-row class="full-width px-4" dense no-gutters>
        <v-col
            cols="12"
            class="py-4">
          <h6 class="text-h6">
            {{ $trans('requestForQuotationThreadPage.equipmentsAndOffersSection.equipments') }}
          </h6>
        </v-col>
        <v-col cols="12">
          <v-row dense no-gutters>
            <v-col class="offered-equipments-container">
              <v-row
                  dense
                  class="offered-equipments">
                <v-col
                    v-for="equipment in equipments"
                    :key="equipment.id"
                    class="flex-grow-0"
                    :class="{'flex-grow-1': $vuetify.breakpoint.mdAndDown}"
                    cols="12"
                    sm="12"
                    lg="4"
                >
                  <request-for-quotation-equipments-and-offers-equipment-card
                      :id="equipment.id"
                      :name="equipment.name"
                      :type="equipment.type"
                      :manufacturer="equipment.manufacturer"
                      :documents="equipment.documents"
                  ></request-for-quotation-equipments-and-offers-equipment-card>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-row>
    <v-row class="d-flex flex-column justify-center align-content-center mt-16 mx-4" v-else>
      <img class="mx-auto rounded-circle" style="width:40%" src="/equipment-placeholder.png"/>
      <p class="pt-4 body-2 white-gray-color text-h6">
        {{ $trans('requestForQuotationThreadPage.equipmentsAndOffersSection.noEquipmentsOfferedYet') }}
      </p>
    </v-row>
  </v-row>
</template>
<script>
Vue.component("request-for-quotation-equipments-and-offers", {
  template: "#request-for-quotation-equipments-and-offers",
  props: {
    threadEquipments: {
      type: Array,
      required: true
    },
    offeredEquipmentsCount: {
      type: Number,
      required: true
    },
    threadPrice: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      totalPrice: this.threadPrice,
      equipments: this.threadEquipments
    }
  },
  filters: {
    formatNumberAsCurrency(number) {
      return new Intl.NumberFormat('en-US', {style: 'currency', currency: 'SAR'}).format(number)
    }
  }
});
</script>
<style scoped>
.full-width {
  width: 100%;
}

.sheet-card {
  border-top-width: 0 !important;
  height: 80px !important;
}

.sheet-card-title {
  color: rgba(0, 0, 0, 0.38);
}

.sheet-card-value {
  font-style: normal;
  font-weight: 700;
  font-size: 16px;
  line-height: 22px;
  letter-spacing: 0.1px;
  color: rgba(0, 0, 0, 0.87);
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
