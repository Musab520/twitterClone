<template id="equipment-reservation-dialog">
    <v-row justify="center">
        <v-dialog v-model="dialog" max-width="958px" @click:outside="closeDialog">
            <v-sheet class="full-width">
                <v-form ref="form" class="full-width">
                    <div class="pa-5 rounded-tl-0">
                        <v-row class="full-width mt-1">
                            <v-col cols="12" md="6" lg="4">
                                <v-text-field
                                    hide-details=true
                                    :label="$trans('equipmentsPage.equipmentReserveDialog.equipmentName')"
                                    readonly
                                    disabled
                                    outlined
                                    v-model="name">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" md="6" lg="4">
                                <v-text-field
                                    hide-details=true
                                    :label="$trans('equipmentsPage.equipmentReserveDialog.type')"
                                    readonly
                                    disabled
                                    outlined
                                    v-model="type">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" md="6" lg="4">
                                <v-text-field
                                    hide-details=true
                                    :label="$trans('equipmentsPage.equipmentReserveDialog.manufacturer')"
                                    readonly
                                    disabled
                                    outlined
                                    v-model="manufacturer">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" md="6" lg="4">
                                <v-text-field
                                    hide-details=true
                                    :label="$trans('equipmentsPage.equipmentReserveDialog.productionYear')"
                                    readonly
                                    disabled
                                    outlined
                                    v-model="productionYear.asDate().toYearString()">
                                </v-text-field>
                            </v-col>
                            <v-menu transition="scale-transition" bottom offset-y right min-width="auto" :close-on-content-click="false">
                                <template v-slot:activator="{ on, attrs }">
                                    <v-col cols="12" lg="8">
                                        <v-text-field
                                            hide-details=true
                                            v-model="datesText"
                                            :rules="dateRules"
                                            :label="$trans('equipmentsPage.equipmentReserveDialog.reservationPeriod')"
                                            append-icon="mdi-calendar"
                                            required
                                            readonly
                                            outlined v-bind="attrs"
                                            v-on="on">
                                        </v-text-field>
                                    </v-col>
                                </template>
                                <v-date-picker :allowed-dates="dateAllowed" v-model="frqItem.dates" range no-title color="primary" scrollable>
                                    <v-spacer></v-spacer>
                                    <v-btn text color="primary" @click="frqItem.dates = []" class="uppercase">
                                      {{ $trans('equipmentsPage.equipmentReserveDialog.clearDates') }}
                                    </v-btn>
                                </v-date-picker>
                            </v-menu>
                        </v-row>
                        <v-row class="full-width">
                            <v-col cols="12">
                                <v-textarea
                                    hide-details=true
                                    required
                                    outlined
                                    name="input-7-4"
                                    :label="$trans('equipmentsPage.equipmentReserveDialog.privetNote')"
                                    v-model="frqItem.internalNote">
                                </v-textarea>
                            </v-col>
                        </v-row>
                        <v-row class="full-width">
                            <v-col cols="12">
                                <v-sheet outlined rounded>
                                    <div class="d-flex align-center pa-2">
                                        <div class="pr-2">
                                            <p style="font-weight: 600;" class=" bold request-location ma-0">
                                              {{ $trans('equipmentsPage.equipmentReserveDialog.location') }}:
                                            </p>
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
                        <v-row class="full-width">
                            <v-col cols="12" class="d-flex justify-end align-center">
                                <v-btn color="primary" class="uppercase" @click="submit" large outlined :loading="submitLoading">
                                    {{ $trans('equipmentsPage.equipmentReserveDialog.requestForReservationButton') }}
                                </v-btn>
                            </v-col>
                        </v-row>
                    </div>
                </v-form>
            </v-sheet>
        </v-dialog>
    </v-row>
</template>
<script>
Vue.component("equipment-reservation-dialog", {
    template: "#equipment-reservation-dialog",

    props: {
        name: {
            type: String,
            required: true
        },
        type: {
            type: String,
            required: true
        },
        manufacturer: {
            type: String,
            required: true
        },
        productionYear: {
            type: String,
            required: true
        },
        id: {
            type: String,
            required: true
        }
    },

    data() {
        return {
            dialog: true,
            frqItem: {
                dates: [],
                internalNote: '',
                locationName: '',
                longitude: 39.1776,
                latitude: 21.4925,
            },
            submitLoading: false,
            showFromPicker: false,
            showToPicker: false,
            reservations: [],
            map: {
                zoom: 6,
                mapOptions: { zoomControl: false }
            },
            dateRules: [
                v => !!v || 'Reservation Period is required',
                v => (this.frqItem.dates && this.frqItem.dates.length == 2) || 'Both beginning and end of the Period should be specified'
            ]
        }
    },

    computed: {
        locationCoordinates() {
            return {
                lng: this.frqItem.longitude ?? 0.0,
                lat: this.frqItem.latitude ?? 0.0
            }
        },

        datesText() {
            return this.frqItem.dates.sort(this.dateCompare).join(' ~ ');
        }
    },

    created() {
        fetch(`/api/equipments/${this.id}/reservations`)
            .then(res => res.json())
            .then(res => {
                this.reservations = res;
            });
    },

    methods: {
        closeDialog() {
            this.dialog = false;
            this.$emit('close');
        },

        submit() {
            this.$refs.form.validate();
            if (this.frqItem.dates.length < 2) return;

            let dates = this.frqItem.dates.concat().sort();

            let body = {
                "fromDate": new Date(dates[0]),
                "toDate": new Date(dates[1]),
                "internalNote": this.frqItem.internalNote,
                "locationName": this.frqItem.locationName,
                "longitude": this.frqItem.longitude,
                "latitude": this.frqItem.latitude
            };

            this.submitLoading = true;
            fetch(
                `/api/equipments/${this.id}/reserve`,
                { method: 'POST', body: JSON.stringify(body), 'Content-Type': 'application/json' }
            )
            .then(res => {
                if (res.status === 400) {
                    res.json().then(parsedRes => {
                        alert(parsedRes.REQUEST_BODY.map(it => it.message).join('\n'));
                    })
                    this.submitLoading = false;
                } else {
                    res.json().then(parsedRes => {
                        this.submitLoading = false;
                        this.closeDialog();
                        window.location = `/request-for-quotations/${parsedRes.requestForQuotationId}/threads/${parsedRes.threadId}`;
                    });
                }
            });
        },

        datePickerValueSelection(pickerMenuRef, value) {
            pickerMenuRef.save(value)
        },

        syncLocation(coordinates) {
            this.frqItem.locationName = coordinates.locationName;
            this.frqItem.longitude = coordinates.longitude;
            this.frqItem.latitude = coordinates.latitude;
        },

        dateCompare(date1, date2) {
            let [y1, m1, d1] = date1.split('-').map(s => parseInt(s, 10));
            let [y2, m2, d2] = date2.split('-').map(s => parseInt(s, 10));

            if (y1 > y2)
                return 1;
            if (y1 < y2)
                return -1;

            if (m1 > m2)
                return 1;
            if (m1 < m2)
                return -1;

            if (d1 > d2)
                return 1;
            if (d1 < d2)
                return -1;

            return 0;
        },

        dateToString(date) {
            return [date.getFullYear(), date.getMonth() + 1, date.getDate()].join('-');
        },

        dateAllowed(date) {
            if (this.dateCompare(date, this.dateToString(new Date())) == -1) return false;

            if (this.frqItem.dates.length > 0) {
                let dates = [date, this.frqItem.dates[0]].sort(this.dateCompare);

                for (reservation of this.reservations) {
                    let from = this.dateToString(new Date(reservation.from));
                    let to = this.dateToString(new Date(reservation.to));

                    if (this.dateCompare(dates[1], from) >= 0 && this.dateCompare(dates[0], to) <= 0)
                        return false;
                }
                return true;
            } else {
                for (reservation of this.reservations) {
                    let from = this.dateToString(new Date(reservation.from));
                    let to = this.dateToString(new Date(reservation.to));

                    if (this.dateCompare(date, from) >= 0 && this.dateCompare(date, to) <= 0)
                        return false;
                }
                return true;
            }
        }
    }
});
</script>
<style scoped>
.selection-stat {
    color: rgba(0, 0, 0, 0.6);
}

.selection-btn {
    position: absolute;
    top: 0;
    right: 0;
}

.selected-item {
    border: 5px solid #4CAF50;
    box-shadow: 0px 4px 5px rgba(0, 0, 0, 0.14), 0px 1px 10px rgba(0, 0, 0, 0.12), 0px 2px 4px rgba(0, 0, 0, 0.2);
    border-radius: 8px;
}

.selection-icon {
    border-radius: 0px 0px 0px 7px;
}
</style>