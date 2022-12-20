<template id="request-for-quotation-offers">
    <request-for-quotation-layout>
        <v-sheet
            outlined
            rounded
            class="py-4 px-6 ml-n2"
            min-height="700">
            <v-row
                dense
                class="d-flex align-center justify-start"
                align="center"
                align-content="center">
                <v-col cols="1">
                    <v-hover v-slot:default="{ hover }">
                        <a
                            class="align-start d-flex text-decoration-none"
                            :href=`/${$javalin.state.userDetails.companyId}/request-for-quotation-list`>
                            <v-icon color="grey" :class="{'primary--text': hover}" v-if="!$isRtl()">mdi-chevron-left</v-icon>
                            <v-icon color="grey" :class="{'primary--text': hover}">mdi-chevron-right</v-icon>
                            <span class="body-1 grey--text" :class="{'primary--text': hover}">
                                {{ $trans('requestForQuotationOffersPage.back') }}
                            </span>
                        </a>
                    </v-hover>
                </v-col>
            </v-row>
            <v-row class="pt-4" dense>
                <v-col md="2"  align="center" justify="center">
                    <v-text-field
                        :label="$trans('requestForQuotationOffersPage.search')"
                        v-model="search"
                        prepend-icon="mdi-magnify"
                        ></v-text-field>
                </v-col>
                <v-col md="2" class="d-flex justify-start align-center">
                    <v-select  class="pl-2 mt-0 pt-0"
                               hide-details
                               :label="$trans('requestForQuotationOffersPage.company')"
                               v-if="biddingCompanies.loaded" :items="getBiddingCompanies"
                               dense
                               outlined
                               ></v-select>
                </v-col>
                <v-col md="2" class="d-flex justify-start align-center">
                    <v-select class="px-3"
                              v-if="equipmentManufacturers.loaded"   :items="getEquipmentManufacturers"
                              :label="$trans('requestForQuotationOffersPage.manufacturer')"
                              dense
                              hide-details
                              outlined
                              ></v-select>
                </v-col>
                <v-col md="2" class="d-flex justify-start align-center">
                    <v-select  class="pl-2 mt-0 pt-0"
                               hide-details
                               :label="$trans('requestForQuotationOffersPage.status')"
                               v-if="RFQStatuses.loaded"  :items="getRFQStatuses"
                               dense
                               outlined
                               ></v-select>
                </v-col>
                <v-col md="4" class="d-flex justify-end align-center">
                    <v-btn  hide-details outlined class="px-4 mx-2" color="primary" large>
                        <v-icon>mdi-table-large</v-icon>
                        {{ $trans('requestForQuotationOffersPage.showHideColumns') }}
                    </v-btn>
                </v-col>
            </v-row>
            <v-divider class="mt-1 mb-6"></v-divider>
            <v-sheet  v-if="RFQOffers.loaded && getRFQOffers.length > 0" outlined
                      rounded>
                <v-data-table
                    :headers="headers"
                    :items="getRFQOffers"
                    :search="search">
                    <template v-slot:item.createdOn="{ item }">
                        <span>{{ item.createdOn?.asDate?.().toDateString()?? '--' }}</span>
                    </template>
                    <template v-slot:item.price="item">
                        <span>{{item.price ?? "--"}}</span>
                    </template>
                    <template v-slot:item.status="{ item }">
                        <v-chip label small class="d-flex justify-center" style="width: 80px;"
                                :color="getStatusColor(item.status)" :dark="isDarkStatus(item.status)">
                            <b>{{ item.status }}</b>
                        </v-chip>
                    </template>
                    <template  v-slot:[`item.goto`]="{ item }">
                        <v-icon
                            v-if="!$isRtl()"
                            class="goto-icon-color float-right"
                            @click="gotoRFQThread(item.id)">
                            mdi-chevron-right
                        </v-icon>
                        <v-icon
                            class="goto-icon-color float-right"
                            @click="gotoRFQThread(item.id)">
                            mdi-chevron-left
                        </v-icon>
                    </template>
                </v-data-table>
            </v-sheet>
            <v-row class="py-16 d-flex flex-column align-center justify-center" v-else >
                <img class="mx-auto" style="width:20%" src="/no_data.svg"/>
                <p class="pt-4 body-2">
                    {{ $trans('misc.noResultsFound') }}
                </p>
            </v-row>
        </v-sheet>
    </request-for-quotation-layout>
</template>
<script>
    Vue.component("request-for-quotation-offers", {
        template: "#request-for-quotation-offers",
        data() {
            return {

                search: '',
                headers: [
                    {
                        text: this.$trans('requestForQuotationOffersPage.offersTableHeaders.company'),
                        align: 'start',
                        filterable: false,
                        value: 'companyName',
                    },
                    {
                        text: this.$trans('requestForQuotationOffersPage.offersTableHeaders.createDate'),
                        value: 'createdOn'
                    },
                    {
                        text: this.$trans('requestForQuotationOffersPage.offersTableHeaders.offeredEquipments'),
                        value: 'offeredEquipments'
                    },
                    {
                        text: this.$trans('requestForQuotationOffersPage.offersTableHeaders.price'),
                        value: 'price'
                    },
                    {
                        text: this.$trans('requestForQuotationOffersPage.offersTableHeaders.status'),
                        value: 'status'
                    },
                    {
                        text: '',
                        value: 'goto'
                    },
                ],
                RFQStatuses: [],
                equipmentManufacturers: [],
                RFQOffers: [],
                biddingCompanies: [],
            }
        },
        created() {
            this.equipmentManufacturers = new LoadableData(`/api/equipments/lookup/manufacturers`);
            this.RFQStatuses = new LoadableData(`/api/request-for-quotations/lookup/statuses`);

            const requestForQuotationId = this.$javalin.pathParams["requestForQuotationId"]
            this.biddingCompanies = new LoadableData(`/api/bidding/${requestForQuotationId}/lookup/companies`);

            this.RFQOffers = new LoadableData(`/api/request-for-quotations/${requestForQuotationId}/offers`);

        }, computed: {
            getRFQStatuses() {
                let arr = [];
                if (this.RFQStatuses.loaded) {
                    arr.push(...this.RFQStatuses.data)
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
            getBiddingCompanies() {
                let arr = [];
                if (this.biddingCompanies.loaded) {
                    arr.push(...this.biddingCompanies.data)
                }
                return arr;
            },
            getRFQOffers() {
                return this.RFQOffers.data;
            },
        }
        , mounted() {
            this.equipmentManufacturers.refresh();
            this.RFQStatuses.refresh();
            this.biddingCompanies.refresh();
            this.RFQOffers.refresh();

        }
        , methods: {
            getDateString(date){
                return new Date(date).toLocaleDateString()
            },
            gotoRFQThread(id)
            {
                const requestForQuotationId = this.$javalin.pathParams["requestForQuotationId"]
                window.location.assign(`/request-for-quotations/${requestForQuotationId}/threads/${id}`);

            },
            getStatusColor(status) {
                switch (status) {
                    case 'new':
                        return 'offer-new';
                    case 'received':
                        return 'offer-received';
                    case 'accepted':
                        return 'offer-accepted';
                    case 'rejected':
                        return 'offer-rejected';
                    case 'closed':
                        return 'offer-closed';
                }
            },

            isDarkStatus(status) {
                return status !== 'New Offer';
            },

        }

    });
</script>
<style scoped>

    .v-data-footer
    {
        justify-content: center;
    }
    .v-data-footer__select
    {
        margin-left: unset !important;
    }
</style>