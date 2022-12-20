<template id="request-for-quotation-bidding-list">
    <request-for-quotation-layout>
        <v-sheet
            outlined
            rounded
            class="px-4 pb-4"
            min-height="700">
            <v-row>
                <v-col cols="12" md="3" align="center"
                       justify="center">
                    <v-text-field
                         class="d-none"
                        :label="$trans('requestForQuotationBiddingListPage.search')"
                        @change="filterBiddingRFQs"
                        v-model="searchFilter"
                        hide-details="auto"
                        prepend-icon="mdi-magnify"
                        ></v-text-field>
                </v-col>
              <v-col md="6" class="d-none d-md-block">
                <request-for-quotation-bidding-filters @filter="filterBiddingRFQs"></request-for-quotation-bidding-filters>
              </v-col>
              <v-col cols="12" sm="6" class="d-block d-md-none">
                <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn color="primary" dark large width="100%" v-bind="attrs" v-on="on">
                      Filters
                    </v-btn>

                  </template>
                  <v-card>
                    <v-toolbar dark color="primary">
                      <v-btn icon dark @click="dialog = false">
                        <v-icon>mdi-close</v-icon>
                      </v-btn>
                      <v-toolbar-title>{{ $trans('companyEquipmentsPage.filters') }}</v-toolbar-title>
                    </v-toolbar>
                    <div class="pa-5">
                      <v-row>
                        <request-for-quotation-bidding-filters @filter="filterBiddingRFQs"></request-for-quotation-bidding-filters>
                      </v-row>
                    </div>
                  </v-card>
                </v-dialog>
              </v-col>
              <v-col cols="12" sm="6" md="3" class="d-flex justify-end pt-0 pt-sm-3 mt-md-3 mt-0">
                    <v-btn hide-details outlined color="primary" large
                           :width=$vuetify.breakpoint.smAndDown?'100%':''
                           @click.stop="columnSelectorDialog = true">
                        <v-icon class="mr-2">mdi-view-week-outline</v-icon>
                        {{ $trans('requestForQuotationBiddingListPage.showHideColumn') }}
                    </v-btn>
                    <v-dialog v-model="columnSelectorDialog" max-width="600px">
                        <v-card>
                            <v-card-title>
                                <span class="text-h5">
                                    {{ $trans('requestForQuotationBiddingListPage.selectColumns') }}
                                </span>
                            </v-card-title>
                            <v-card-text>
                                <v-container>
                                    <v-row>
                                        <v-combobox
                                            v-model="selectedColumns"
                                            :items="columnsToSelect"
                                            :label="$trans('requestForQuotationBiddingListPage.selectColumnsYouWantToView')"
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
                                    {{ $trans('requestForQuotationBiddingListPage.close') }}
                                </v-btn>
                                <v-btn color="blue darken-1" text @click="selectColumns">
                                    {{ $trans('requestForQuotationBiddingListPage.save') }}
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </v-col>
            </v-row>
            <v-divider class="my-4"></v-divider>
            <v-row>
                <v-col class="d-flex justify-center">
                    <v-progress-circular
                        v-if="biddingRFQs.loading"
                        indeterminate
                        color="primary"
                        ></v-progress-circular>
                </v-col>
            </v-row>
            <v-sheet
                outlined
                rounded
                v-if="biddingRFQs.loaded && getBiddingRFQs.length > 0">
                <v-data-table
                    :headers="headers"
                    :items="getBiddingRFQs"
                    :search="search">
                    <template v-slot:item.created-on="{ item }">
                        <span>{{ item.creationDate?.asDate().toDateString() }}</span>
                    </template>
                    <template v-slot:item.from-date="{ item }">
                        <span>{{ item.fromDate?.asDate().toDateString() }}</span>
                    </template>
                    <template v-slot:item.to-date="{ item }">
                        <span>{{ item.toDate?.asDate().toDateString() }}</span>
                    </template>
                    <template v-slot:item.status="{ item }">
                        <v-chip label small class="d-flex justify-center" style="width: 80px;"
                                :color="getStatusColor(item.status)" :dark="isDarkStatus(item.status)">
                            <b>{{ item.status }}</b>
                        </v-chip>
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

                  <template v-slot:[`item.actions`]="{ item }">
                        <v-btn  :depressed="item.applied == 1"    :disabled="item.applied == 1"    hide-details outlined black--text small
                                @click="gotoMyBidding(item.id , item.applied)" class=" px-1 mx-0">
                            {{ $trans('requestForQuotationBiddingListPage.applyNow') }}
                        </v-btn>
                    </template>
                </v-data-table>
            </v-sheet>
            <v-row class="py-16 d-flex flex-column align-center justify-center" v-else>
                <img class="mx-auto" width="128" src="/no_data.svg"/>
                <p class="pt-4 body-2">
                    {{ $trans('misc.noResultsFound')}}
                </p>
            </v-row>
        </v-sheet>
    </request-for-quotation-layout>
</template>
<script>
    Vue.component("request-for-quotation-bidding-list", {
        template: "#request-for-quotation-bidding-list",
        data() {
            return {
              dialog: false,
              deleteRequestDialog: false,
                columnSelectorDialog: false,
                criteriaDetailsDialog:false,
              columns: {
                    'Company': {
                        text: this.$trans('requestForQuotationBiddingListPage.biddingListTableHeaders.company'),
                        value: 'companyName'
                    },
                    'Note': {
                        text: this.$trans('requestForQuotationBiddingListPage.biddingListTableHeaders.note'),
                        value: 'internalNote'
                    },
                    'Creation Date': {
                        text: this.$trans('requestForQuotationBiddingListPage.biddingListTableHeaders.creationDate'),
                        value: 'created-on'
                    },
                    'From': {
                        text: this.$trans('requestForQuotationBiddingListPage.biddingListTableHeaders.from'),
                        value: 'from-date'
                    },
                    'To': {
                        text: this.$trans('requestForQuotationBiddingListPage.biddingListTableHeaders.to'),
                        value: 'to-date'
                    },
                    'Location': {
                        text: this.$trans('requestForQuotationBiddingListPage.biddingListTableHeaders.location'),
                        value: 'locationName'
                    },
                    'Status': {
                        text: this.$trans('requestForQuotationBiddingListPage.biddingListTableHeaders.status'),
                        value: 'status'
                    },
                  'Criteria':{
                    text: this.$trans('quotationListingPage.rfqListTableHeaders.criteria'),
                    value: 'criteria-count'
                  }
                },
                biddingRFQs: [],
                search: '',
                columnsToSelect: [],
                selectedColumns: [],
                selectedHeaders: [],
                searchFilter: "",
                equipmentTypeFilter: "",
                equipmentManufacturerFilter: "",
                singleOfferFilter: "",
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
            }
        },
        computed: {
            headers() {
                return [...this.selectedHeaders, {text: 'Actions', value: 'actions', filterable: false, sortable: false}];
            },
            getEquipmentManufacturers() {
                let arr = [];
                arr.push('Any Manufacturers');
              if (this.equipmentManufacturers.loaded) {
                    arr.push(...this.equipmentManufacturers.data)
                }
                return arr;
            },
            getEquipmentTypes() {
                let arr = [];
                if (this.equipmentTypes.loaded) {
                    arr.push(...this.equipmentTypes.data)
                }
                return arr;
            },
            getBiddingRFQs()
            {
                return this.biddingRFQs.data;
            },
            getCriteriaDetailsData() {
                return this.CriteriaDetails.data;
            },

        },
        created() {
            this.columnsToSelect = Object.keys(this.columns);
            this.selectedHeaders = this.columnsToSelect.map(col => this.columns[col]);

            this.biddingRFQs = new LoadableData(`api/bidding/thread`);
        }
        , mounted() {
            this.biddingRFQs.refresh();
        },
        methods: {
            getDateString(date) {
                return  new Date(date).toLocaleDateString()
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

            gotoMyBidding(id, applied) {
                if (applied != 1)
                {
                    let bidder_id = this.$javalin.state.userDetails.companyId;
                    fetch(`/api/request-for-quotations/${id}/new-thread`, {
                        method: 'POST', 'Content-Type': 'application/json', body: JSON.stringify(
                                {
                                    "requestForQuotationId": id,
                                },
                                )
                    }).then(() => {
                        let userid = this.$javalin.pathParams["userId"];
                        window.location.assign(`/my-company/bidding`);
                    })
                }

            },
            filterBiddingRFQs(query) {
                if (this.searchFilter) {
                    query.push(`internal_note=${this.searchFilter}`)
                }

                if (this.equipmentManufacturerFilter && this.equipmentManufacturerFilter !== "" &&this.equipmentManufacturerFilter !=='Any Manufacturers') {
                    query.push(`manufacturer=${this.equipmentManufacturerFilter}`)
                }

                if (this.equipmentTypeFilter && this.equipmentTypeFilter !== "") {
                    query.push(`type=${this.equipmentTypeFilter}`)
                }

                if (this.singleOfferFilter) {
                    query.push(`singleOffer=${this.singleOfferFilter}`)
                }
                this.biddingRFQs = new LoadableData(`api/bidding/thread/search?${query.join('&')}`);
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

    table tr:hover td .pencil-icon-color {
        color: orange !important;
    }

    table tr:hover td .delete-icon-color {
        color: red !important;
    }

    table tr:hover td .goto-icon-color {
        color: black !important;

    }

    .v-input--reverse .v-input__slot {
        flex-direction: row-reverse;
        justify-content: flex-end;
    }


    .v-data-footer {
        justify-content: center;
    }

    .v-data-footer__select {
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