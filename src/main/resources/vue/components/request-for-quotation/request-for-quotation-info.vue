<template id="request-for-quotation-info">
    <v-col class="d-flex flex-column justify-space-between">
        <v-col cols="12" class="request-container">
            <v-row
                dense
                no-gutters
                class="d-flex justify-center align-content-start px-3 pt-6"
                :class="{'request': !disabled}"
                v-if="!isLoading">
                <v-row class="full-width" dense>
                    <v-col
                        cols="12"
                        lg="6">
                        <v-menu
                            ref="fromMenu"
                            v-model="showFromPicker"
                            :close-on-content-click="false"
                            :return-value.sync="requestForQuotationData.fromDate"
                            transition="scale-transition"
                            offset-y
                            min-width="auto">
                            <template v-slot:activator="{ on, attrs }">
                                <v-text-field
                                    v-model="requestForQuotationData.fromDate"
                                    :label="$trans('requestForQuotationThreadPage.myRequestSection.from')"
                                    append-icon="mdi-calendar"
                                    readonly
                                    outlined
                                    v-bind="attrs"
                                    v-on="on"
                                    :disabled="disabled"></v-text-field>
                            </template>
                            <v-date-picker
                                v-model="requestForQuotationData.fromDate"
                                no-title
                                color="primary"
                                scrollable
                                :disabled="disabled">
                                <v-spacer></v-spacer>
                                <v-btn text color="primary" @click="showFromPicker = false">
                                    {{ $trans('requestForQuotationThreadPage.myRequestSection.fromCancel') }}
                                </v-btn>
                                <v-btn text color="primary"
                                       @click="datePickerValueSelection($refs.fromMenu, requestForQuotationData.fromDate)">
                                    {{ $trans('requestForQuotationThreadPage.myRequestSection.fromOk') }}
                                </v-btn>
                            </v-date-picker>
                        </v-menu>
                    </v-col>
                    <v-col
                        cols="12"
                        lg="6">
                        <v-menu
                            ref="toMenu"
                            v-model="showToPicker"
                            :close-on-content-click="false"
                            :return-value.sync="requestForQuotationData.toDate"
                            transition="scale-transition"
                            offset-y
                            min-width="auto">
                            <template v-slot:activator="{ on, attrs }">
                                <v-text-field
                                    v-model="requestForQuotationData.toDate"
                                    :label="$trans('requestForQuotationThreadPage.myRequestSection.to')"
                                    append-icon="mdi-calendar"
                                    readonly outlined
                                    v-bind="attrs"
                                    v-on="on"
                                    :disabled="disabled"></v-text-field>
                            </template>
                            <v-date-picker
                                v-model="requestForQuotationData.toDate"
                                no-title
                                color="primary"
                                scrollable
                                :disabled="disabled">
                                <v-spacer></v-spacer>
                                <v-btn text color="primary" @click="showToPicker = false">
                                    {{ $trans('requestForQuotationThreadPage.myRequestSection.toCancel') }}
                                </v-btn>
                                <v-btn text color="primary"
                                       @click="datePickerValueSelection($refs.toMenu, requestForQuotationData.toDate)">
                                    {{ $trans('requestForQuotationThreadPage.myRequestSection.toOk') }}
                                </v-btn>
                            </v-date-picker>
                        </v-menu>
                    </v-col>
                </v-row>
              <v-row class="full-width" dense>
                <v-col cols="12" lg="12">

                <v-data-table
                  :headers="criteriaDetailsHeaders"
                  :items="getCriteriaDetailsData"
                  :items-per-page="5"
                  class="elevation-1">
                <template v-slot:item.produced-after="{ item }">
                  <span>{{ item.producedAfter?.asDate().toYearString() }}</span>
                </template>
              </v-data-table>
                  </v-col>
                </v-row>
                <v-row class="full-width" dense>
                    <v-col cols="12" lg="12">
                        <v-sheet outlined rounded>
                            <div class="d-flex align-center pa-2">
                                <div class="pr-2">
                                    <v-icon color="primary">mdi-map-marker-outline</v-icon>
                                </div>
                                <div>
                                    <p class="request-location ma-0">{{ requestForQuotationData.locationName }}</p>
                                </div>
                            </div>
                            <map-component
                                :zoom="map.zoom"
                                :center="locationCoordinates"
                                map-style="width: 100%; height: 295px;"
                                :marker="locationCoordinates"
                                :map-options="map.mapOptions"
                                :draggable="!disabled"
                                translate-location
                                @update="syncLocation">
                            </map-component>
                        </v-sheet>
                    </v-col>
                </v-row>
            </v-row>
        </v-col>
        <v-col
            v-if="!disabled"
            class="align-self-end update-request-container">
            <v-sheet outlined
                     height="100%"
                     width="100%"
                     class="d-flex justify-end align-center update-request">
                <v-btn
                    color="primary"
                    large
                    outlined
                    @click="updateRequest"
                    class="mr-5">
                    {{ $trans('requestForQuotationThreadPage.myRequestSection.updateRequestButton') }}
                </v-btn>
            </v-sheet>
        </v-col>
    </v-col>
</template>
<script>
    Vue.component("request-for-quotation-info", {
        template: "#request-for-quotation-info",
        props: {
            disabled: {
                type: Boolean,
                default: false,
            }
        },
        data() {
            return {
                isLoading: false,
                requestForQuotationData: {
                    fromDate: '',
                    toDate: '',
                    locationName: '',
                    longitude: 39.1776,
                    latitude: 21.4925,
                },
                showFromPicker: false,
                showToPicker: false,
                map: {
                    zoom: 6,
                    mapOptions: {zoomControl: false}
                },
                CriteriaDetails: [],
                criteriaDetailsHeaders: [
                { text: 'Quantity', value: 'quantity' },
                {
                  text: 'Type',
                  align: 'start',
                  value: 'type',
                },
                { text: 'Manufacturer', value: 'manufacturer' },
                { text: 'Produced After', value: 'produced-after' }
              ],
            }
        },
        created() {
            this.loadRequestForQuotation()
            this.CriteriaDetails  = new LoadableData(`/api/request-for-quotations/${this.$javalin.pathParams["requestForQuotationId"]}/criteria-list`);
        },
        methods: {
            loadRequestForQuotation() {
                this.isLoading = true
                const requestForQuotationId = this.$javalin.pathParams["requestForQuotationId"]
                fetch(`/api/request-for-quotations/${requestForQuotationId}`)
                        .then(response => response.json())
                        .then(response => {
                            this.requestForQuotationData = response
                            this.requestForQuotationData.fromDate = new Date(this.requestForQuotationData.fromDate).toLocaleDateString()
                            this.requestForQuotationData.toDate = new Date(this.requestForQuotationData.toDate).toLocaleDateString()
                            this.isLoading = false
                        })
            },


            syncLocation(coordinates) {
                this.requestForQuotationData.locationName = coordinates.location_name
                this.requestForQuotationData.longitude = coordinates.longitude
                this.requestForQuotationData.latitude = coordinates.latitude
            },
            updateRequest() {
                this.$emit('update-request', this.requestForQuotationData)
            }
        },
        computed: {
            locationCoordinates() {
                return {
                    lng: this.requestForQuotationData?.longitude ?? 0.0,
                    lat: this.requestForQuotationData?.latitude ?? 0.0
                }
            },
          getCriteriaDetailsData() {
            return this.CriteriaDetails.data;
          },
        }
    });
</script>
<style scoped>
    .full-width {
        width: 100%;
    }

    .request-container {
        position: relative;
        top: 0;
        left: 0;
    }

    .request {
        position: absolute;
        overflow: auto;
        padding-bottom: 100px !important;
    }

    .request-location {
        font-style: normal;
        font-weight: 400;
        font-size: 16px;
        line-height: 22px;
        letter-spacing: 0.1px;
    }

    .update-request-container {
        z-index: 1000;
    }

    .update-request {
        max-height: 76px !important;
        height: 76px !important;
        position: absolute;
        bottom: 0;
        width: -webkit-fill-available !important;
        border-left-width: 0 !important;
    }
</style>
