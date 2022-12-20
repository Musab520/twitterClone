<template id="request-for-quotation-list-filters">
    <v-row class="flex-column flex-lg-row mx-0 mt-lg-0 mt-3">
        <v-col lg="2" class="d-flex justify-start align-center">
            <v-select v-if="equipmentTypesLoadable.loaded" class="mt-0 pt-0" :items="equipmentTypes"
                @change="filterRFQs" v-model="equipmentTypeFilter" hide-details
                :label="$trans('quotationListingPage.type')" dense outlined></v-select>
            <span v-else>
                Loading Options..
            </span>
        </v-col>
        <v-col lg="2" class="d-flex justify-start align-center">
            <v-select v-if="equipmentManufacturersLoadable.loaded" :items="equipmentManufacturers"
                @change="filterRFQs" v-model="equipmentManufacturerFilter"
                :label="$trans('quotationListingPage.manufacturer')" dense hide-details outlined></v-select>
            <span v-else>
                Loading Options..
            </span>
        </v-col>
        <v-col lg="4" class="d-flex justify-space-start align-center">
            <v-switch @change="filterRFQs" v-model="havingOffersFilter" inset
                :label=$trans("quotationListingPage.havingOffers") hide-details class="px-4 mt-0 v-input--reverse">
            </v-switch>
            <v-switch @change="filterRFQs" v-model="singleOfferFilter" inset
                :label=$trans("quotationListingPage.singleOffer") hide-details class="px-4 mt-0 v-input--reverse">
            </v-switch>
        </v-col>
    </v-row>
</template>

<script>
Vue.component("request-for-quotation-list-filters", {
    template: "#request-for-quotation-list-filters",

    data() {
        return {
            equipmentTypeFilter: "",
            equipmentManufacturerFilter: "",
            singleOfferFilter: "",
            havingOffersFilter: "",
            equipmentTypesLoadable: new LoadableData(`/api/equipments/lookup/types`),
            equipmentManufacturersLoadable: new LoadableData(`/api/equipments/lookup/manufacturers`)
        };
    },
    methods:
    {
        filterRFQs() {
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
            if (this.havingOffersFilter) {
                query.push(`havingOffers=${this.havingOffersFilter}`)
            }
            this.$emit('filter',query)
        }
    },
    computed:
    {
        equipmentTypes() {
            let arr = ["All"];
            if (this.equipmentTypesLoadable.loaded) {
                arr.push(...this.equipmentTypesLoadable.data)
            }
            return arr;

        },
        equipmentManufacturers() {
            let arr = ["All"];
            if (this.equipmentManufacturersLoadable.loaded) {
                arr.push(...this.equipmentManufacturersLoadable.data)
            }
            return arr;
        },
    },
    mounted() {
        this.equipmentTypesLoadable.refresh();
        this.equipmentManufacturersLoadable.refresh();
    },
});
</script>