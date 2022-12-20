<template id="request-for-quotation-list">
    <request-for-quotation-layout>
        <v-sheet
            outlined
            rounded
            class="py-4 px-6 ml-n2"
            min-height="700">
            <v-row class="pt-4" dense>
                <v-col md="2" align="center" justify="center">
                    <v-text-field
                        :label="$trans('quotationListingPage.search')"
                        @change="filterRFQs"
                        v-model="searchFilter"
                        prepend-icon="mdi-magnify"
                        ></v-text-field>
                </v-col>
                <v-col md="1" class="d-flex justify-start align-center">
                    <v-select v-if="equipmentTypesLoadable.loaded"
                              class="pl-2 mt-0 pt-0"
                              :items="equipmentTypes"
                              @change="filterRFQs"
                              v-model="equipmentTypeFilter"
                              hide-details
                              :label="$trans('quotationListingPage.type')"
                              dense
                              outlined
                              ></v-select>
                    <span v-else>
                        Loading Options..
                    </span>
                </v-col>
                <v-col md="2" class="d-flex justify-start align-center">
                    <v-select
                        v-if="equipmentManufacturersLoadable.loaded"
                        class="px-3"
                        :items="equipmentManufacturers"
                        @change="filterRFQs"
                        v-model="equipmentManufacturerFilter"
                        :label="$trans('quotationListingPage.manufacturer')"
                        dense
                        hide-details
                        outlined
                        ></v-select>
                    <span v-else>
                        Loading Options..
                    </span>
                </v-col>
                <v-col md="4" class="d-flex justify-start align-center">
                    <v-switch
                        @change="filterRFQs"
                        v-model="havingOffersFilter"
                        hide-details hide-details class="px-4 mt-0 v-input--reverse">
                        <template #label>
                            {{ $trans('quotationListingPage.havingOffers') }}
                        </template>
                    </v-switch>
                    <v-switch
                        @change="filterRFQs"
                        v-model="singleOfferFilter"
                        hide-details class="px-4 mt-0 v-input--reverse">
                        <template #label>
                            {{ $trans('quotationListingPage.singleOffer') }}
                        </template>
                    </v-switch>
                </v-col>
                <v-col md="3" class="d-flex justify-end align-center">
                    <v-btn hide-details outlined class="px-4"  color="primary" large
                           @click.stop="columnSelectorDialog = true">
                        <v-icon class="mr-2">mdi-view-week-outline</v-icon>
                        {{ $trans('quotationListingPage.showHideColumns') }}
                    </v-btn>
                    <v-dialog v-model="columnSelectorDialog" max-width="600px">
                        <v-card>
                            <v-card-title>
                                <span class="text-h5">
                                    {{ $trans('quotationListingPage.selectColumns') }}
                                </span>
                            </v-card-title>
                            <v-card-text>
                                <v-container>
                                    <v-row>
                                        <v-combobox
                                            v-model="selectedColumns"
                                            :items="columnsToSelect"
                                            :label="$trans('quotationListingPage.selectColumnsYouWantToView')"
                                            clearable
                                            multiple
                                            small-chips>
                                        </v-combobox>
                                    </v-row>
                                </v-container>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn color="blue darken-1" text @click="closeColumnSelectionDialog">
                                    {{ $trans('quotationListingPage.close') }}
                                </v-btn>
                                <v-btn color="blue darken-1" text @click="selectColumns">
                                    {{ $trans('quotationListingPage.save') }}
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>

                    <v-btn  hide-details color="primary" @click="createFRQ()"dark class=" px-4 mx-2"  large>
                        <v-icon>mdi-request-quote</v-icon>
                        {{ $trans('quotationListingPage.createRfq') }}
                    </v-btn>
                </v-col>

            </v-row>
            <v-divider class="mt-1 mb-6"></v-divider>
            <v-row dense>
                <v-col class="d-flex justify-center">
                    <v-progress-circular
                        v-if="RFQs.loading"
                        indeterminate
                        color="primary"
                        ></v-progress-circular>
                </v-col>
            </v-row>
            <v-sheet
                outlined
                rounded
                v-if="RFQs.loaded && getRFQs.length > 0"
                >
                <v-data-table
                    :headers="headers"
                    :items="getRFQs"
                    :search="search"
                    >
                    <template v-slot:item.created-on="{ item }">
                        <span>{{ item.createdOn.asDate().toDateString()}}</span>
                    </template>
                    <template v-slot:item.from-date="{ item }">
                        <span>{{ item.fromDate?.asDate().toDateString() }}</span>
                    </template>
                    <template v-slot:item.to-date="{ item }">
                        <span>{{ item.toDate?.asDate().toDateString() }}</span>
                    </template>
                    <template v-slot:item.location-name="{ item }">
                        <span>{{ item.locationName?? "--" }}</span>
                    </template>
                  <template v-slot:item.criteria-count="{ item }">
                    <v-btn
                        :depressed="item.criteriaCount==0"    :disabled="item.criteriaCount==0"
                        hide-details outlined black--text small  @click.stop="getCriteriaDetails(item.id)" class=" px-1 mx-0">
                      ({{ item.criteriaCount==0?'-': item.criteriaCount }}) Show Details
                    </v-btn>
                    <v-dialog v-model="criteriaDetailsDialog" width="700">
                      <v-card>
                        <v-card-title class="">{{ $trans('quotationListingPage.criteriaDetailsDialogTitle') }}</v-card-title>
                        <v-card-text>
                            <template>
                              <v-data-table
                                  :headers="criteriaDetailsHeaders"
                                  :items="getCriteriaDetailsData"
                                  :items-per-page="5"
                                  class="elevation-1"
                              >
                                <template v-slot:item.created-on="{ item }">
                                  <span>{{ item.creationOn?.asDate().toDateString()}}</span>
                                </template>
                                <template v-slot:item.produced-after="{ item }">
                                  <span>{{ item.producedAfter?.asDate().toYearString() }}</span>
                                </template>

                              </v-data-table>
                            </template>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn text @click="criteriaDetailsDialog = false">
                            {{ $trans('quotationListingPage.criteriaDetailsDialogClose')}}
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                  </template>
                  <template v-slot:item.offers="{ item }">
                    <span>{{  item.offers==0?'-': item.offers}}</span>
                  </template>
                    <template v-slot:item.status="{ item }">
                        <v-chip label small class="d-flex justify-center" style="width: 80px;"
                                :color="getStatusColor(item.status)" :dark="isDarkStatus(item.status)">
                            <b>{{ item.status }}</b>
                        </v-chip>
                    </template>
                    <template  v-slot:[`item.actions`]="{ item }">
                        <!--            <v-icon   class=" pencil-icon-color" class=" mr-2" @click="editRFQ(item.id)">mdi-pencil</v-icon>-->
                        <v-dialog v-model="deleteRequestDialog" width="500">
                            <template v-slot:activator="{ on, attrs }">
                                <v-btn
                                    :depressed="item.status == 'closed'"    :disabled="item.status == 'closed'"
                                    hide-details outlined black--text small  @click.stop="deleteRequestDialog = true" class=" px-1 mx-0">
                                    {{ $trans('quotationListingPage.closeRfq') }}
                                </v-btn>
                            </template>

                            <v-card>
                                <v-card-title class="">{{ $trans('quotationListingPage.closeRequest') }}</v-card-title>
                                <v-card-text>{{ $trans('quotationListingPage.closeRequestConfirmation')}}</v-card-text>
                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn text @click="deleteRequestDialog = false">
                                        {{ $trans('quotationListingPage.closeRequestConfirmationCancel')}}
                                    </v-btn>
                                    <v-btn color="red" text @click="closeFRQ(item.id)">
                                        {{ $trans('quotationListingPage.closeRequestConfirmationClose')}}
                                    </v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                        <v-icon
                            v-if="!$isRtl()"
                            class="goto-icon-color float-right"
                            @click="gotoOffers(item.id)">
                            mdi-chevron-right
                        </v-icon>
                        <v-icon
                            v-else
                            class="goto-icon-color float-left"
                            @click="gotoOffers(item.id)">
                            mdi-chevron-left
                        </v-icon>
                    </template>

                </v-data-table>
            </v-sheet>
            <v-row class="py-16 d-flex flex-column align-center justify-center" v-else >
                <img class="mx-auto" style="width:20%" src="/no_data.svg"/>
                <p class="pt-4 body-2">
                    {{ $trans('misc.noResultsFound')}}
                </p>
            </v-row>
        </v-sheet>
    </request-for-quotation-layout>
</template>


<script>
    Vue.component("request-for-quotation-list", {
        template: "#request-for-quotation-list",
        data() {
            return {
                deleteRequestDialog: false,
                columnSelectorDialog: false,
                criteriaDetailsDialog:false,
                columns: {
                    'Note': {
                        text: this.$trans('quotationListingPage.rfqListTableHeaders.note'),
                        value: 'internalNote'
                    },
                    'Creation Date': {
                        text: this.$trans('quotationListingPage.rfqListTableHeaders.creationDate'),
                        value: 'created-on'
                    },
                    'Location': {
                        text: this.$trans('quotationListingPage.rfqListTableHeaders.location'),
                        value: 'location-name'
                    },
                    'From': {
                        text: this.$trans('quotationListingPage.rfqListTableHeaders.from'),
                        value: 'from-date'
                    },
                    'To': {text: this.$trans('quotationListingPage.rfqListTableHeaders.to'),
                        value: 'to-date'
                    },
                    'Offers': {
                        text: this.$trans('quotationListingPage.rfqListTableHeaders.offers'),
                        value: 'offers'
                    },
                    'Status': {
                        text: this.$trans('quotationListingPage.rfqListTableHeaders.status'),
                        value: 'status'
                    },
                  'Criteria':{
                    text: this.$trans('quotationListingPage.rfqListTableHeaders.criteria'),
                    value: 'criteria-count'
                  }
                },
                search: '',
                columnsToSelect: [],
                selectedColumns: [],
                selectedHeaders: [],
                searchFilter: "",
                equipmentTypeFilter: "",
                equipmentManufacturerFilter: "",
                singleOfferFilter: "",
                havingOffersFilter: "",
                RFQs: [],
                CriteriaDetails: [],
              criteriaDetailsHeaders: [
                { text: 'Quantity', value: 'quantity' },

                {
                  text: 'Type',
                  align: 'start',
                  value: 'type',
                },
                { text: 'Manufacturer', value: 'manufacturer' },
                { text: 'Produced After', value: 'produced-after' },
                { text: 'Creation Date', value: 'created-on' }

              ],
                equipmentTypesLoadable: new LoadableData(`/api/equipments/lookup/types`),
                equipmentManufacturersLoadable: new LoadableData(`/api/equipments/lookup/manufacturers`)
            }
        },
        computed: {
            headers() {
                return [...this.selectedHeaders, {
                        text: this.$trans('quotationListingPage.rfqListTableHeaders.actions'),
                        value: 'actions',
                        filterable: false,
                        sortable: false
                    }
                ];
            },
            equipmentTypes() {
                let arr = [];
                if (this.equipmentTypesLoadable.loaded) {
                    arr.push(...this.equipmentTypesLoadable.data)
                }
                return arr;

            },
            equipmentManufacturers() {
                let arr = [];
                  arr.push('Any Manufacturers');
                if (this.equipmentManufacturersLoadable.loaded) {
                    arr.push(...this.equipmentManufacturersLoadable.data)
                }
                return arr;
            },
            getRFQs()
            {
                return   this.RFQs.data;
            },
          getCriteriaDetailsData() {
            return this.CriteriaDetails.data;
          },
        },
        created() {
            this.columnsToSelect = Object.keys(this.columns);
            this.selectedHeaders = this.columnsToSelect.map(col => this.columns[col]);
            this.RFQs = new LoadableData(`/api/request-for-quotations/my-requests`);
        },
        mounted() {
            this.equipmentTypesLoadable.refresh();
            this.equipmentManufacturersLoadable.refresh();
        },
        methods: {
            getDateString(date){
                return new Date(date).toLocaleDateString()
            },
            closeFRQ(id) {
                fetch(`/api/request-for-quotations/close-rfq/${id}`, {
                    method: "PUT", 'Content-Type': 'application/json'
                }).then(() => {
                    window.location.assign("/" + this.$javalin.state.userDetails.companyId + "/request-for-quotation-list");
                });
            },
            gotoOffers(id)
            {
                window.location.assign(`/${id}/request-for-quotation-offers`);
            },
            editRFQ(id)
            {
                window.location.assign(`/request-for-quotation/${id}`);
            },
            createFRQ()
            {
                window.location.assign(`/${this.$javalin.state.userDetails.companyId}/new-request-for-quotation`);
            },
            selectColumns() {
                this.selectedHeaders = this.selectedColumns.map(col => this.columns[col]);

                this.closeColumnSelectionDialog();
            },

            closeColumnSelectionDialog() {
                this.selectedColumns = [];
                this.columnSelectorDialog = false;
            },
            getStatusColor(status) {
                switch (status) {
                    case 'Created':
                        return '#F9A315';
                    case 'In Progress':
                        return '#1976D2';
                    case 'Completed':
                        return '#4CAF50';
                    case 'Closed':
                        return '#FF5252';
                }
            },
            isDarkStatus(status) {
                return status !== 'New Offer';
            },
            filterRFQs() {
                let query = []

                if (this.searchFilter) {
                    query.push(`internal_note=${this.searchFilter}`)
                }

                if (this.equipmentManufacturerFilter && this.equipmentManufacturerFilter !== ""&&this.equipmentManufacturerFilter !=='Any Manufacturers') {
                    query.push(`manufacturer=${this.equipmentManufacturerFilter}`)
                }

                if (this.equipmentTypeFilter && this.equipmentTypeFilter !== "") {
                    query.push(`type=${this.equipmentTypeFilter}`)
                }

                if (this.singleOfferFilter) {
                    query.push(`singleOffer=${this.singleOfferFilter}`)
                }
                if (this.havingOffersFilter) {
                    query.push(`havingOffers=${this.havingOffersFilter}`)
                }
                debugger;
                if(query!=null&&query.length!=0)
                {
                  this.RFQs = new LoadableData(`/api/request-for-quotations/my-requests/search?${query.join('&')}`);
                }else {
                  this.RFQs = new LoadableData(`/api/request-for-quotations/my-requests`);
                }

            },
          getCriteriaDetails(id)
          {
            this.CriteriaDetails  = new LoadableData(`/api/request-for-quotations/${id}/criteria-list`);
            this.criteriaDetailsDialog = true;

          }
        }

    });
</script>
<style scoped>

    table tr:hover td .pencil-icon-color{
        color: orange  !important;
    }

    table tr:hover td .delete-icon-color {
        color: red !important;
    }
    table tr:hover td  .goto-icon-color
    {
        color: black !important;

    }
    .v-input--reverse .v-input__slot {
        flex-direction: row-reverse;
        justify-content: flex-end;
    }


    .v-data-footer
    {
        justify-content: center;
    }
    .v-data-footer__select
    {
        margin-left: unset !important;
    }

    .v-input--reverse .v-input__slot {
        flex-direction: row-reverse;
        justify-content: flex-end;
    }
    .v-application--is-ltr .v-input--selection-controls__input {
        margin-right: 0;
        margin-left: 8px;
    }

    .v-application--is-rtl .v-input--selection-controls__input {
        margin-left: 0;
        margin-right: 8px;
    }

    .v-label {
        display: block;
        flex: 1;
    }


</style>