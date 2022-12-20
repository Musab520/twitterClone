<template id="equipment-creation-dialog">
        <v-dialog v-model="dialog" max-width="50%" @click:outside="closeDialog">
            <v-card class="">
                <v-card-title>
                    <span class="text-h5">{{$trans('equipmentsPage.equipmentAddDialog.addNewEquipment')}}</span>
                </v-card-title>
                <v-card-text class="mt-2">
                    <v-container class="px-0">
                        <v-row no-gutters>
                            <v-col cols="12" sm="6" md="6" class="pr-2">
                                <v-text-field v-model="equipmentItem.type" label="Type" outlined>
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="6" class="pl-2">
                              <v-menu
                                  ref="productionMenu"
                                  v-model="showProductionPicker"
                                  :close-on-content-click="false"
                                  :return-value.sync="equipmentItem.productionDate"
                                  transition="scale-transition"
                                  offset-y
                                  min-width="auto">
                                <template v-slot:activator="{ on, attrs }">
                                  <v-text-field
                                      v-model="equipmentItem.productionDate"
                                      label="Production Date"
                                      append-icon="mdi-calendar"
                                      readonly
                                      outlined
                                      v-bind="attrs"
                                      v-on="on"></v-text-field>
                                </template>
                                <v-date-picker
                                    v-model="equipmentItem.productionDate"
                                    no-title
                                    color="primary"
                                    scrollable>
                                  <v-spacer></v-spacer>
                                  <v-btn text color="primary" @click="showProductionPicker = false">
                                    Cancel
                                  </v-btn>
                                  <v-btn text color="primary" @click="datePickerValueSelection($refs.productionMenu, equipmentItem.productionDate)">
                                    OK
                                  </v-btn>
                                </v-date-picker>
                              </v-menu>

                            </v-col>
                            <v-col cols="12" sm="6" md="6" class="pr-2">
                                <v-text-field v-model="equipmentItem.licencePlate" outlined :label="$trans('equipmentsPage.equipmentAddDialog.licencePlate')">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="6" class="pl-2">
                                <v-text-field v-model="equipmentItem.registrationNumber" outlined
                                    :label="$trans('equipmentsPage.equipmentAddDialog.registrationNumber')">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="6" class="pr-2">
                                <v-text-field v-model="equipmentItem.licenceNumber" outlined :label="$trans('equipmentsPage.equipmentAddDialog.licenceNumber')">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="6" class="pl-2">
                                <v-text-field v-model="equipmentItem.manufacturer" outlined :label="$trans('equipmentsPage.equipmentAddDialog.manufacturer')">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="6" class="pr-2">
                                <v-text-field v-model="equipmentItem.serialNumber" outlined :label="$trans('equipmentsPage.equipmentAddDialog.serialNumber')">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="6" class="pl-2">
                                <v-text-field v-model="equipmentItem.model" outlined :label="$trans('equipmentsPage.equipmentAddDialog.model')">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="6" class="pl-2">
                                <v-text-field v-model="equipmentItem.tonnage" outlined type="number" :label="$trans('equipmentsPage.equipmentAddDialog.tonnage')">
                                </v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="6" class="pr-2">
                                <v-text-field v-model="equipmentItem.maxWeight" outlined type="number" :label="$trans('equipmentsPage.equipmentAddDialog.maxWeight')">
                                </v-text-field>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="primary darken-1" text @click="closeDialog">
                        {{$trans('equipmentsPage.equipmentAddDialog.cancel')}}
                    </v-btn>
                    <v-btn color="white darken-1" text @click="save" style="background-color:#102338;">
                        {{$trans('equipmentsPage.equipmentAddDialog.save')}}

                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
</template>
<script>
Vue.component("equipment-creation-dialog", {
    template: "#equipment-creation-dialog",

    data() {
        return {
            dialog: true,
            userCompanyId: "",
            showProductionPicker: false,
            equipmentItem: {}
        }
    },

    computed: {

    },

    mounted() {
    },

    methods: {
        datePickerValueSelection(pickerMenuRef, value) {
          pickerMenuRef.save(value)
        },
        closeDialog() {
            this.dialog = false;
            this.$emit('close');
        },
        save() {
            let userId = this.$javalin.state.userDetails.user_id;
            this.equipmentItem.companyId = new LoadableData(`/api/users/${userId}`).data.companyId
            console.log(this.equipmentItem)
            fetch(`/api/equipments`, { method: 'POST', 'Content-Type': 'application/json', body: JSON.stringify(this.equipmentItem) })
            this.closeDialog()
        },
    }
});
</script>
