<template id="company-info">
    <company-profile-layout>
                <v-sheet
                    v-if="company.loaded"
                    outlined
                    rounded
                    shaped>
                    <v-row class="mx-0 my-0">
                        <v-col class="px-0 py-0" cols="12" md="6">
                            <map-component
                                :zoom="map.zoom"
                                :center="map.center"
                                 map-style="width: 100%; min-height: 380px;"
                                :marker="map.marker"
                                :map-options="map.mapOptions">
                            </map-component>
                        </v-col>
                        <v-col cols="12" md="6" class="px-5 py-3">
                                <h6 class="title">
                                    {{ $trans('companyDetailsPage.location') }}
                                </h6>
                                <p class="pt-2 gray-color">{{ getCompany.location }}</p>
                                <h6 class="title">
                                    {{ $trans('companyDetailsPage.totalEquipments') }}
                                </h6>
                                <p class="pt-2 gray-color">
                                    {{ getCompany.totalEquipmentsCount | formatNumber }}
                                </p>
                                <h6 class="title">
                                    {{ $trans('companyDetailsPage.availableEquipments') }}
                                </h6>
                                <p class="pt-2 success--text">
                                    {{ getCompany.availableEquipmentsCount | formatNumber }}
                                </p>
                                <h6 class="title">
                                    {{ $trans('companyDetailsPage.contactInfo') }}
                                </h6>
                                <p class="pt-2 mb-0 gray-color">
                                    {{ getCompany.mobile }}
                                </p>
                                <p class="gray-color">
                                    {{ getCompany.email }}
                                </p>
                        </v-col>
                    </v-row>
                </v-sheet>
    </company-profile-layout>
</template>
<script>
    Vue.component("company-info", {
        template: "#company-info",
        props: {
            companyId: {
                type: String,
                required: true,
            }
        },
        data() {
            return {
                company: null,
                map: {
                    zoom: 15,
                    center: [],
                    marker: [],
                    mapOptions: {zoomControl: false}
                }
            }
        },
        created() {
            const companyId = this.$javalin.pathParams["companyId"]
            this.company = new LoadableData(`/api/companies/${companyId}`);
            this.map.center = [this.company.data.longitude, this.company.data.latitude]
            this.map.marker = [this.company.data.longitude, this.company.data.latitude]
        },
        computed: {
            getCompany() {

                return this.company.data;
            }

        },
        mounted() {
            this.company.refresh();
        },
        filters: {
            formatNumber: function (value) {
                return value.toLocaleString('en-US')
            }
        }
    });
</script>
<style scoped>
    .gray-color {
        color: rgba(0, 0, 0, 0.6)
    }
</style>