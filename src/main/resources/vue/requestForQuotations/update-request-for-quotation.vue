<template id="update-request-for-quotation">
    <app-layout>
        <v-container fluid v-if="!isLoading">
            <v-row
                dense
                class="d-flex align-center justify-center "
                align="center"
                align-content="center">
                <v-sheet
                    outlined
                    rounded
                    class="mt-4"
                    >
                    <v-form   v-if="frqItem.loaded"
                              ref="form"
                              style="width: 958px; "
                              >
                        <div class="pa-5 rounded-tl-0 ml-n2">
                            <v-row class="full-width" dense>
                                <v-col
                                    cols="12"
                                    md="4">
                                    <v-text-field class="px-2"
                                                  label="Equipments Amount"
                                                  required
                                                  outlined v-model="rfqItemData.quantity">
                                    </v-text-field>
                                </v-col>
                                <v-col
                                    cols="12"
                                    md="4">
                                    <v-menu
                                        ref="fromMenu"
                                        v-model="showFromPicker"
                                        :close-on-content-click="false"
                                        :return-value.sync="rfqItemData.fromDate"
                                        transition="scale-transition"
                                        offset-y
                                        min-width="auto">
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-text-field
                                                v-model="rfqItemData.fromDate"
                                                label="From"
                                                append-icon="mdi-calendar"
                                                readonly
                                                outlined
                                                v-bind="attrs"
                                                v-on="on"></v-text-field>
                                        </template>
                                        <v-date-picker
                                            v-model="rfqItemData.fromDate"
                                            no-title
                                            color="primary"
                                            scrollable>
                                            <v-spacer></v-spacer>
                                            <v-btn text color="primary" @click="showFromPicker = false">
                                                Cancel
                                            </v-btn>
                                            <v-btn text color="primary" @click="datePickerValueSelection($refs.fromMenu, rfqItemData.fromDate)">
                                                OK
                                            </v-btn>
                                        </v-date-picker>
                                    </v-menu>
                                </v-col>
                                <v-col
                                    cols="12"
                                    md="4">
                                    <v-menu
                                        ref="toMenu"
                                        v-model="showToPicker"
                                        :close-on-content-click="false"
                                        :return-value.sync="rfqItemData.toDate"
                                        transition="scale-transition"
                                        offset-y
                                        min-width="auto">
                                        <template v-slot:activator="{ on, attrs }">
                                            <v-text-field
                                                v-model="rfqItemData.toDate"
                                                label="To"
                                                append-icon="mdi-calendar"
                                                readonly outlined
                                                v-bind="attrs"
                                                v-on="on"></v-text-field>
                                        </template>
                                        <v-date-picker
                                            v-model="rfqItemData.toDate"
                                            no-title
                                            color="primary"
                                            scrollable>
                                            <v-spacer></v-spacer>
                                            <v-btn text color="primary" @click="showToPicker = false">
                                                Cancel
                                            </v-btn>
                                            <v-btn text color="primary" @click="datePickerValueSelection($refs.toMenu, rfqItemData.toDate)">
                                                OK
                                            </v-btn>
                                        </v-date-picker>
                                    </v-menu>
                                </v-col>
                            </v-row>
                            <v-row class="full-width" dense>
                                <v-col cols="12" md="4">
                                    <v-select
                                        v-if="equipmentTypes.loaded"  :items="getEquipmentTypes"
                                        v-model="rfqItemData.type"
                                        label="Type"
                                        outlined
                                        ></v-select>
                                </v-col>
                                <v-col cols="12" md="4">
                                    <v-select
                                        v-if="equipmentManufacturers.loaded"  :items="getEquipmentManufacturers"
                                        v-model="rfqItemData.manufacturer"
                                        label="Manufacturer"
                                        outlined
                                        ></v-select>
                                </v-col>
                                <v-col cols="12" md="4">
                                    <v-select
                                        v-if="equipmentProductionYears.loaded"  :items="getEquipmentProductionYears"
                                        v-model="rfqItemData.producedAfter"
                                        label="Minimum Production Year"
                                        outlined
                                        ></v-select>
                                </v-col>
                            </v-row>
                            <v-row class="full-width" dense>
                                <v-col
                                    cols="12"
                                    md="12">
                                    <v-textarea
                                        required outlined name="input-7-4" label="Privet Note" v-model="rfqItemData.internalNote">
                                    </v-textarea>
                                </v-col>
                            </v-row>
                            <v-row class="full-width" dense>
                                <v-col cols="12" md="12">
                                    <v-sheet outlined rounded>
                                        <div class="d-flex align-center pa-2">
                                            <div class="pr-2">
                                                <p style="font-weight: 600;"  class=" bold request-location ma-0">Location:</p>
                                            </div>
                                            <div>
                                                <p class="request-location ma-0">{{ location.cityName }} - {{ location.address }}</p>
                                            </div>
                                        </div>
                                        <map-component
                                            :zoom="map.zoom"
                                            :center="locationCoordinates"
                                            map-style="width: 100%; height: 295px;"
                                            :marker="locationCoordinates"
                                            :map-options="map.mapOptions"
                                            draggable
                                            translate-location
                                            @update="syncLocation">
                                        </map-component>
                                    </v-sheet>
                                </v-col>
                            </v-row>
                            <v-row dense class="pt-4">
                                <v-col cols="12" class="full-width d-flex justify-end align-end"  md="12">
                                    <v-btn
                                        @click="updateRequestForQuotation()"
                                        color="primary"
                                        outlined>
                                        SAVE
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </div>
                    </v-form>
                </v-sheet>
            </v-row>
        </v-container>
    </app-layout>
</template>

<script>
    Vue.component("update-request-for-quotation", {
        template: "#update-request-for-quotation",
        data: () => {
            return {
                isLoading: false,
                selectedtype: '',
                selectedproducedafter: '',
                selectedmanufacturer: '',
                rfqItemData: {
                    id: '',
                    quantity: '',
                    fromDate: '',
                    toDate: '',
                    type: '',
                    requester_id: '',
                    manufacturer: '',
                    producedAfter: '',
                    internalNote: '',
                    locationName: '',
                    longitude: '',
                    latitude: ''
                },
                equipmentTypes: [],
                equipmentManufacturers: [],
                equipmentProductionYears: [],
                showFromPicker: false,
                showToPicker: false,
                validationRules: {
                    equipmentsAmountRules: [
                        v => !!v || 'Equipments amount is required',
                    ]
                },
                map: {
                    zoom: 6,
                    mapOptions: {zoomControl: false}
                },
            }
        },
        created() {
            this.loadTypeFilterOptions();
            this.equipmentManufacturers = new LoadableData(`/api/equipments/lookup/manufacturers`);
            this.equipmentTypes = new LoadableData(`/api/equipments/lookup/types`);
            this.equipmentProductionYears = new LoadableData(`/api/equipments/lookup/ProductionDates`);
            this.loadRequestForQuotation();
        },
        mounted() {
            this.equipmentManufacturers.refresh();
            this.equipmentTypes.refresh();
            this.equipmentProductionYears.refresh();
            this.frqItem.refresh();
        },
        computed: {
            locationCoordinates() {
                return {
                    lng: this.rfqItemData.longitude != "" ? this.rfqItemData.longitude : 44.296875,
                    lat: this.rfqItemData.latitude != "" ? this.rfqItemData.latitude : 22.593725
                }
            },
            getEquipmentTypes() {
                let arr = [];
                if (this.equipmentTypes.loaded) {
                    arr.push(...this.equipmentTypes.data)
                }
                return arr;

            },
            getEquipmentManufacturers() {
                let arr = [];
                if (this.equipmentManufacturers.loaded) {
                    arr.push(...this.equipmentManufacturers.data)
                }
                return arr;
            },
            getEquipmentProductionYears() {
                let arr = [];
                if (this.equipmentProductionYears.loaded) {
                    arr.push(...this.equipmentProductionYears.data)
                }
                return arr;
            }
        },
        methods: {
            datePickerValueSelection(pickerMenuRef, value) {
                pickerMenuRef.save(value)
            },
            syncLocation(coordinates) {
                this.rfqItemData.locationName = coordinates.location_name
                this.rfqItemData.longitude = coordinates.longitude
                this.rfqItemData.latitude = coordinates.latitude
            },
            loadRequestForQuotation() {
                this.isLoading = true
                const requestForQuotationId = this.$javalin.pathParams["requestForQuotationId"]
                this.frqItem = fetch(`/api/request-for-quotations/${requestForQuotationId}`)
                        .then(response => response.json())
                        .then(response => {
                            this.rfqItemData = response
                            this.rfqItemData.fromDate = new Date(this.rfqItemData.fromDate).toLocaleDateString()
                            this.rfqItemData.toDate = new Date(this.rfqItemData.toDate).toLocaleDateString()
                            this.isLoading = false
                        })
            },
            updateRequestForQuotation() {
                this.rfqItemData.fromDate = new Date(this.rfqItemData.fromDate);
                this.rfqItemData.toDate = new Date(this.rfqItemData.toDate);
                fetch(`/api/request-for-quotations/${this.$javalin.pathParams["requestForQuotationId"]}`, {
                    method: "PUT", 'Content-Type': 'application/json',
                    body: JSON.stringify(this.rfqItemData)

                }).then(() => {
                    window.location.assign("/" + this.$javalin.state.userDetails.companyId + "/request-for-quotation-list");
                })
            },

        }
    });

</script>
<style scoped>
    .leaflet-pane {
        z-index: unset !important;
    }
    .v-main
    {
        background-color:darkgray !important;
    }
</style>