<template id="company-equipments">
    <company-profile-layout>
        <v-row>
            <v-col cols="12" class="d-block d-lg-none">

            <v-dialog
            v-model="dialog"
            fullscreen
            hide-overlay
            transition="dialog-bottom-transition"
            >
            <template v-slot:activator="{ on, attrs }">
                    <v-btn
                color="primary"
                dark
                large
                width="100%"
                v-bind="attrs"
                v-on="on"
                >
                Filters
                </v-btn>
                
            </template>
            <v-card>
                <v-toolbar
                dark
                color="primary"
                >
                <v-btn
                    icon
                    dark
                    @click="dialog = false"
                >
                    <v-icon>mdi-close</v-icon>
                </v-btn>
                <v-toolbar-title>{{ $trans('companyEquipmentsPage.filters') }}</v-toolbar-title>

                </v-toolbar>
                <div class="pa-5 pt-0 pt-lg-5">
                  <company-equipments-filters @filter-equipments="filterEquipments" @clear-filters-handler="clearFiltersHandler"></company-equipments-filters>
                </div>

            </v-card>
            </v-dialog>
            </v-col>

            <v-col cols="12" md="12" lg="8" xl="8">
                    <v-sheet
                        outlined
                        rounded
                        class="rounded-tl-0 pb-4 px-6 ml-n2">
                        <v-row class="my-0 p-0">
                            <v-col sm="6" cols="12">
                                <v-text-field
                                    :label="$trans('companyEquipmentsPage.search')"
                                    prepend-icon="mdi-magnify"
                                    @change="searchEquipments"
                                    v-model="name_filter"
                                    hide-details="auto"
                                    ></v-text-field>
                            </v-col>
                            <v-col sm="6" cols="12" class="primary--text title font-weight-medium d-flex flex-row-reverse justify-start align-center">
                                <span class="mx-2">{{ totalResultsCount }}</span>
                                <span>{{ $trans('companyEquipmentsPage.totalResults') }}</span>
                            </v-col>
                        </v-row>
                        <v-divider class="mt-1 mb-4"></v-divider>
                        <v-row dense>
                            <v-col class="d-flex justify-center">
                                <v-progress-circular
                                    v-if="equipmentsLoadable.loading"
                                    indeterminate
                                    color="primary"
                                    ></v-progress-circular>
                            </v-col>
                        </v-row>
                        <v-row dense v-if="equipmentsLoadable.loaded && getEquipments.length > 0">
                            <v-col style="width: 0;" cols="12" sm="6" md="4" lg="6" xl="4" v-for="equipment in getEquipments" :key="equipment.id">
                                <equipment-card
                                    :id="equipment.id"
                                    :name="equipment.name"
                                    :type="equipment.type"
                                    :manufacturer="equipment.manufacturer"
                                    :serialNumber="equipment.serialNumber"
                                    :availability="equipment.availability"
                                    :productionYear="equipment.productionDate"
                                    :image="equipment.image ?? '/equipment-placeholder.png'"
                                    ></equipment-card>
                            </v-col>
                        </v-row>
                        <v-row class="py-16 d-flex flex-column align-center justify-center" v-else-if="equipmentsLoadable.loaded">
                            <img class="mx-auto" width="128" src="/no_data.svg"/>
                            <p class="pt-4 body-2">
                                {{ $trans('misc.noResultsFound') }}
                            </p>
                        </v-row>
                        <v-row class="d-flex justify-center" v-if="equipmentsLoadable.loadError">
                            <p>
                                {{ $trans('misc.loadingError') }}
                            </p>
                        </v-row>
                    </v-sheet>
            </v-col>

            <v-col class="d-none d-lg-block" lg="4">
              <company-equipments-filters @filter-equipments="filterEquipments" @clear-filters-handler="clearFiltersHandler"></company-equipments-filters>
            </v-col>


    </v-row>
    </company-profile-layout>
</template>
<script>
    Vue.component("company-equipments", {
        template: "#company-equipments",
        data() {
            return {
                dialog: false,
                equipments: [],
                equipmentsLoadable:[],

                filter_query:[],
                name_filter:'',
                combined_query:[],
            }
        },

        created() {
            const companyId = this.$javalin.pathParams["companyId"];
            this.equipmentWorkLocationsLoadable = new LoadableData(`/api/equipments/lookup/work-locations?companyId=${companyId}`);
            this.equipmentsLoadable = new LoadableData(`/api/companies/${companyId}/equipments`);
        },

        mounted() {
            this.equipmentsLoadable.refresh();
            this.equipmentWorkLocationsLoadable.refresh();
        },

        computed: {
            totalResultsCount() {
                if (this.equipmentsLoadable.loaded) {
                    return this.equipmentsLoadable.data.length
                }
                return 0
            },
            getEquipments() {
                let arr = []
                if (this.equipmentsLoadable.loaded) {
                    arr.push(...this.equipmentsLoadable.data);
                }
                return arr;
            }
        },
        methods: {
            filterEquipments(query) {
              this.combined_query=[];
              this.filter_query=query;
              this.combined_query=[...this.filter_query];

              if(this.name_filter.length>0)
              {
                this.combined_query.push(`name=${this.name_filter}`);
              }

              const companyId = this.$javalin.pathParams["companyId"];
              this.equipmentsLoadable = new LoadableData(`/api/companies/${companyId}/equipments/search?${this.combined_query.join("&")}`)
            },
            searchEquipments()
            {
              this.combined_query=[];
              if(this.name_filter.length===0)
              {
                this.combined_query=[...this.filter_query];
              }
              else
              {
                this.combined_query=[...this.filter_query];
                this.combined_query.push(`name=${this.name_filter}`);
              }

              const companyId = this.$javalin.pathParams["companyId"];
              this.equipmentsLoadable = new LoadableData(`/api/companies/${companyId}/equipments/search?${this.combined_query.join("&")}`)
            },
            clearFiltersHandler() {
              this.name_filter='';
              this.filter_query=[];
              this.combined_query=[];

              const companyId = this.$javalin.pathParams["companyId"];
              this.equipmentsLoadable = new LoadableData(`/api/companies/${companyId}/equipments`)
            },
        }
    });
</script>