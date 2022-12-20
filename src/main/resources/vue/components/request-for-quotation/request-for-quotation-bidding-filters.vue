<template id="request-for-quotation-bidding-filters">
    <v-row class="flex-column flex-md-row mx-0 mt-lg-0 mt-0">
      <v-col md="3" class="d-flex justify-start align-center">
        <v-select
            class="pl-2 mt-0 pt-0"
            :items="getEquipmentTypes"
            @change="filterBiddingRFQs"
            v-model="equipmentTypeFilter"
            hide-details
            :label="$trans('requestForQuotationBiddingListPage.type')"
            dense
            outlined
        ></v-select>
      </v-col>
      <v-col md="6" class="d-flex justify-start align-center">
        <v-select
            class="px-3"
            :items="getEquipmentManufacturers"
            @change="filterBiddingRFQs"
            v-model="equipmentManufacturerFilter"
            :label="$trans('requestForQuotationBiddingListPage.manufacturer')"
            dense
            hide-details
            outlined
        ></v-select>
      </v-col>
      <v-col md="3" class="d-flex justify-start align-center">
        <v-switch
            @change="filterBiddingRFQs"
            v-model="singleOfferFilter"
            hide-details class="px-4 mt-0 v-input--reverse">
          <template #label>
            {{ $trans('requestForQuotationBiddingListPage.singleOffer') }}
          </template>
        </v-switch>
      </v-col>
    </v-row>
</template>

<script>
Vue.component("request-for-quotation-bidding-filters", {
    template: "#request-for-quotation-bidding-filters",

    data() {
        return {
          equipmentManufacturersLoadable: new LoadableData(`/api/equipments/lookup/manufacturers`),
          equipmentTypesLoadable: new LoadableData(`/api/equipments/lookup/types`),
          equipmentManufacturers: ["All"],
          singleOfferFilter: "",

          equipmentTypeFilter: "",
          equipmentManufacturerFilter: "",
        };
    },
    mounted() {
    this.equipmentManufacturersLoadable.refresh();
    this.equipmentTypesLoadable.refresh();
    },
    methods:
    {
      filterBiddingRFQs() {
        let query = []

        if (this.searchFilter) {
          query.push(`internal_note=${this.searchFilter}`)
        }

        if (this.equipmentManufacturerFilter && this.equipmentManufacturerFilter !== "" && this.equipmentManufacturerFilter !== "All") {
          query.push(`manufacturer=${this.equipmentManufacturerFilter}`)
        }

        if (this.equipmentTypeFilter && this.equipmentTypeFilter !== "" && this.equipmentTypeFilter !== "All") {
          query.push(`type=${this.equipmentTypeFilter}`)
        }

        if (this.singleOfferFilter) {
          query.push(`singleOffer=${this.singleOfferFilter}`)
        }

        this.$emit('filter',query);
      }
    },
    computed:
    {
      getEquipmentManufacturers() {
        let arr = ["All"];
        if (this.equipmentManufacturersLoadable.loaded) {
          arr.push(...this.equipmentManufacturersLoadable.data)
        }
        return arr;
      },
      getEquipmentTypes() {
        let arr = ["All"];
        if (this.equipmentTypesLoadable.loaded) {
          arr.push(...this.equipmentTypesLoadable.data)
        }
        return arr;
      },
    },
    mounted() {

    },
});
</script>