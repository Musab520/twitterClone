<template id="new-request-for-quotation">
    <app-layout>
        <v-container fluid >
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
                    <v-form
                        ref="rfq_form"
                        v-model="rfqValid"
                        style="width: 958px;"
                        >
                        <div class="pa-5 rounded-tl-0 ml-n2">

                          <v-row class="full-width" dense>
                            <v-col
                                cols="12"
                                md="6">
                              <v-menu hide-details="auto"
                                      ref="fromMenu"
                                      v-model="showFromPicker"
                                      :close-on-content-click="false"
                                      :return-value.sync="frqItem.fromDate"
                                      transition="scale-transition"
                                      offset-y
                                      min-width="auto">
                                <template v-slot:activator="{ on, attrs }">
                                  <v-text-field
                                      :rules="validationRules.fromDateRules"
                                      v-model="frqItem.fromDate"
                                      label="From"
                                      append-icon="mdi-calendar"
                                      readonly
                                      outlined
                                      v-bind="attrs"
                                      v-on="on"></v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="frqItem.fromDate"
                                    no-title
                                    color="primary"
                                    scrollable>
                                  <v-spacer></v-spacer>
                                  <v-btn text color="primary" @click="showFromPicker = false">
                                    Cancel
                                  </v-btn>
                                  <v-btn text color="primary" @click="datePickerValueSelection($refs.fromMenu, frqItem.fromDate)">
                                    OK
                                  </v-btn>
                                </v-date-picker>
                              </v-menu>
                            </v-col>
                            <v-col
                                cols="12"
                                md="6">
                              <v-menu hide-details="auto"
                                      ref="toMenu"
                                      v-model="showToPicker"
                                      :close-on-content-click="false"
                                      :return-value.sync="frqItem.toDate"
                                      transition="scale-transition"
                                      offset-y
                                      min-width="auto">
                                <template v-slot:activator="{ on, attrs }">
                                  <v-text-field
                                      :rules="validationRules.toDateRules"
                                      v-model="frqItem.toDate"
                                      label="To"
                                      append-icon="mdi-calendar"
                                      readonly outlined
                                      v-bind="attrs"
                                      v-on="on"></v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="frqItem.toDate"
                                    no-title
                                    color="primary"
                                    scrollable>
                                  <v-spacer></v-spacer>
                                  <v-btn text color="primary" @click="showToPicker = false">
                                    Cancel
                                  </v-btn>
                                  <v-btn text color="primary" @click="datePickerValueSelection($refs.toMenu, frqItem.toDate)">
                                    OK
                                  </v-btn>
                                </v-date-picker>
                              </v-menu>
                            </v-col>
                          </v-row>

                          <v-sheet hide-details="auto" :style="isShowError ? 'border: 1px solid red;' : 'border: 1px solid white;'"
                              outlined
                              class="pt-4 px-1  mb-3"
                                   :rules="validationRules.toDateRules"
                              rounded>
                            <v-form
                                ref="criteriaform"
                                v-model="criteriaValid">
                            <v-row class="full-width" dense>
                              <v-col
                                  cols="12"
                                  md="2">
                                <v-text-field hide-details="auto"
                                    label="Equipments Amount"
                                    required
                                    :rules="validationRules.equipmentsAmountRules"
                                    outlined v-model="equipmentCriteriaItem.quantity">
                                </v-text-field>
                              </v-col>
                              <v-col cols="12" md="3">
                                <v-select hide-details="auto"
                                    v-if="equipmentTypes.loaded"  :items="getEquipmentTypes"
                                    v-model="equipmentCriteriaItem.type"
                                    label="Type"
                                    :rules="validationRules.equipmentsTypeRules"
                                    required
                                    outlined
                                ></v-select>
                              </v-col>
                              <v-col cols="12" md="3">
                                <v-select hide-details="auto"
                                    v-if="equipmentManufacturers.loaded"  :items="getEquipmentManufacturers"
                                    v-model="equipmentCriteriaItem.manufacturer"
                                    label="Manufacturer"
                                    outlined
                                ></v-select>
                              </v-col>
                              <v-col cols="12" md="3">
                                <v-select
                                    v-if="equipmentProductionYears.loaded"  :items="getEquipmentProductionYears"
                                    v-model="equipmentCriteriaItem.producedAfter"
                                    label="Minimum Production Year"
                                    outlined
                                ></v-select>
                              </v-col>
                              <v-col cols="12" md="1"  align="center"
                                     justify="center">
                                <v-btn
                                    class=" mt-2  primary white--text mx-2"
                                    fab
                                    dark
                                    small
                                    @click="addTextRow(equipmentCriteriaItem)"
                                    icon >
                                  <v-icon dark>
                                    mdi-plus
                                  </v-icon>
                                </v-btn>
                              </v-col>
                            </v-row>
                            </v-form>

                            <v-row   class="full-width" v-for=" row in equipmentCriteriaItems" dense>
                              <v-col
                                  cols="12"
                                  md="2">
                                <v-text-field disabled hide-details="auto"
                                              label="Equipments Amount"
                                              required
                                              outlined v-model="row.quantity">
                                </v-text-field>
                              </v-col>
                              <v-col cols="12" md="3">
                                <v-select disabled hide-details="auto"
                                          v-if="equipmentTypes.loaded"  :items="getEquipmentTypes"
                                          v-model="row.type"
                                          label="Type"
                                          outlined
                                ></v-select>
                              </v-col>
                              <v-col cols="12" md="3">
                                <v-select  disabled hide-details="auto"
                                          v-if="equipmentManufacturers.loaded"  :items="getEquipmentManufacturers"
                                          v-model="row.manufacturer"
                                          label="Manufacturer"
                                          outlined
                                ></v-select>
                              </v-col>
                              <v-col cols="12" md="3">
                                <v-select disabled
                                    v-if="equipmentProductionYears.loaded"  :items="getEquipmentProductionYears"
                                    v-model="row.producedAfter"
                                    label="Minimum Production Year"
                                    outlined
                                ></v-select>
                              </v-col>
                              <v-col cols="12" md="1"  align="center"
                                     justify="center">
                                <v-btn
                                    class=" mt-2  red white--text mx-2"
                                    fab
                                    dark
                                    small
                                    @click="deleteRow(row)"
                                    icon >
                                  <v-icon dark>
                                    mdi-minus
                                  </v-icon>
                                </v-btn>
                              </v-col>
                            </v-row>

                          </v-sheet>
                          <div v-if="isShowError" class=" error-message-box v-text-field__details"><div class="v-messages theme--light error--text" role="alert"><div class="v-messages__wrapper"><div class="v-messages__message">You have to fill at least one criteria</div></div></div></div>
                            <v-row class="full-width" dense>
                                <v-col
                                    cols="12"
                                    md="12">
                                    <v-textarea
                                        required outlined name="input-7-4" label="Privet Note" v-model="frqItem.internalNote">
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
                                                <p class="request-location ma-0">{{ frqItem.locationName }}</p>
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
                                        @click="createRequestForQuotation()"
                                        color="primary"
                                        outlined>
                                        REQUEST FOR QUOTATION
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
    Vue.component("new-request-for-quotation", {
        template: "#new-request-for-quotation",
        data: () => ({
                selectedtype: '',
                selectedproducedafter: '',
                selectedmanufacturer: '',
          equipmentCriteriaItem: {
            quantity: '',
            type: '',
            manufacturer: '',
            producedAfter: ''
          },
          equipmentCriteriaItems:[],
                frqItem: {
                    id: '',
                    fromDate: '',
                    toDate: '',
                    RequestForQuotationCriteriaList:[],
                    requesterId: '',
                    internalNote: '',
                    locationName: '',
                    longitude: '',
                    latitude: ''
                },
                equipmentTypes: [],
                criteriaValid: true,
                rfqValid: true,
                equipmentManufacturers: [],
                equipmentProductionYears: [],
                showFromPicker: false,
                showProductionPicker: false,
                showToPicker: false,
                 isShowError: false,
                validationRules: {
                    equipmentsAmountRules: [
                    ],
                  fromDateRules: [
                  ],
                  toDateRules: [
                  ],
                  equipmentsTypeRules: [
                  ]
                },
                map: {
                    zoom: 11,
                    center: [],
                    marker: [],
                    mapOptions: {zoomControl: false}
                },
            }),
        created() {
            this.equipmentManufacturers = new LoadableData(`/api/equipments/lookup/manufacturers`);
            this.equipmentTypes = new LoadableData(`/api/equipments/lookup/types`);
            this.equipmentProductionYears = new LoadableData(`/api/equipments/lookup/ProductionDates`);
        },
        computed: {
            locationCoordinates() {
                return {
                    lng: this.frqItem.longitude != "" ? this.frqItem.longitude : 44.296875,
                    lat: this.frqItem.latitude != "" ? this.frqItem.latitude : 22.593725
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
                    arr = this.equipmentProductionYears.data.map(date=> date.asDate().toYearString())
                }
                return arr;
            }
        },
      watch: {
        'equipmentCriteriaItem.type' (val) {
          this.validationRules.equipmentsTypeRules= []
        },
        'equipmentCriteriaItem.quantity' (val) {
          this.validationRules.equipmentsAmountRules = []
        },
        'frqItem.fromDate' (val) {
          this.validationRules.fromDateRules = []
        },
        'frqItem.toDate' (val) {
          this.validationRules.toDateRules = []
        },
        'isShowError'(val) {
          this.isShowError=val;
        },

        },
        mounted() {
            this.equipmentManufacturers.refresh();
            this.equipmentTypes.refresh();
            this.equipmentProductionYears.refresh();
        },
        methods: {
            datePickerValueSelection(pickerMenuRef, value) {
                pickerMenuRef.save(value)
            },
            syncLocation(coordinates) {
                this.frqItem.locationName = coordinates.locationName
                this.frqItem.longitude = coordinates.longitude
                this.frqItem.latitude = coordinates.latitude
            },
            createRequestForQuotation() {
              this.validationRules.fromDateRules = [
                v => !!v || 'Date from is required',
              ];
              this.validationRules.toDateRules = [
                v => !!v || 'Date to is required',
              ];

              if(this.equipmentCriteriaItems==null || this.equipmentCriteriaItems.length==0)
              {
               this.isShowError=true;
              }

              if (this.$refs.rfq_form.validate() && this.frqItem.fromDate!=""&&this.frqItem.toDate!="" &&this.equipmentCriteriaItems.length>0) {
                this.frqItem.requesterId = this.$javalin.state.userDetails.companyId;

                for (let i = 0; i < this.equipmentCriteriaItems.length; i++) {
                  if (this.equipmentCriteriaItems[i].producedAfter != null) {
                    this.equipmentCriteriaItems[i].producedAfter = new Date(this.equipmentCriteriaItems[i].producedAfter);
                  } else {
                    this.equipmentCriteriaItems[i].producedAfter = null;
                  }
                }
                fetch('/api/request-for-quotations', {
                  method: 'POST', 'Content-Type': 'application/json', body: JSON.stringify(
                      {
                        "fromDate": new Date(this.frqItem.fromDate),
                        "toDate": new Date(this.frqItem.toDate),
                        "requesterId": this.frqItem.requesterId,
                        "internalNote": this.frqItem.internalNote,
                        "status": "CREATED",
                        "requestForQuotationType": "PUBLIC_RFQ",
                        "longitude": this.frqItem.longitude,
                        "latitude": this.frqItem.latitude,
                        "locationName": this.frqItem.locationName,
                        "requestForQuotationCriteriaList": this.equipmentCriteriaItems
                      },
                  )
                }).then(() => {
                  window.location.assign("/" + this.$javalin.state.userDetails.companyId + "/request-for-quotation-list");
                })
              }
            },

          addTextRow(newRow){

            this.validationRules.equipmentsAmountRules = [
              v => !!v || 'Equipments amount is required'
            ];
            this.validationRules.equipmentsTypeRules = [
              v => !!v || 'Equipments type is required',
            ];
            if(this.equipmentCriteriaItems!=null)
            {
              this.isShowError=false;
            }
            if (this.$refs.criteriaform.validate() && newRow.type!="" &&newRow.quantity!="") {
              this.equipmentCriteriaItems.push({quantity: newRow.quantity,
                type: newRow.type,
                manufacturer: newRow.manufacturer,
                producedAfter: newRow.producedAfter});
              this.equipmentCriteriaItem.quantity= '';
              this.equipmentCriteriaItem.type= '';
              this.equipmentCriteriaItem.manufacturer= '';
              this.equipmentCriteriaItem.producedAfter= '';

            }


            },
          deleteRow(row) {
              this.equipmentCriteriaItems.splice(this.equipmentCriteriaItems.indexOf(row), 1);
          },
        }
    });

</script>
<style scoped>
     .error-message-box{
        margin-bottom: 8px;
        margin-top: 0px;
        padding-left: 10px;
      }
    .leaflet-pane {
        z-index: unset !important;
    }
    .v-main
    {
        background-color:darkgray !important;
    }
</style>