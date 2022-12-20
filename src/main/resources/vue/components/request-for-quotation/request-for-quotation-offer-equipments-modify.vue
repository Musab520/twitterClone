<template id="request-for-quotation-offer-equipments-modify">
  <v-row justify="center">
    <v-dialog v-model="dialog" scrollable max-width="30%" persistent>
      <v-card v-if="!equipmentLoading">
        <v-card-title>
          <v-row class="d-flex align-center" dense no-gutters>
            <v-col cols="12" md="9">
              <v-text-field
                  class="body-2"
                  :label="$trans('requestForQuotationThreadPage.equipmentsSection.modifyEquipmentsDialog.search')"
                  prepend-icon="mdi-magnify"
                  @change="searchEquipments"
                  v-model="searchFilter"></v-text-field>
            </v-col>
            <v-col class="d-flex justify-end mb-n3" cols="12" md="3">
              <p class="mb-0 body-2">
                <span class="selection-stat">{{ selection.length }} / {{ totalEquipments.length }}</span>
                {{ $trans('requestForQuotationThreadPage.equipmentsSection.modifyEquipmentsDialog.selected') }}
              </p>
            </v-col>
          </v-row>
        </v-card-title>
        <v-divider></v-divider>
        <v-card-text
            v-if="totalEquipments.length > 0"
            style="height: 70vh">
          <v-item-group v-model="selection" multiple>
            <v-col cols="12">
              <v-row dense no-gutters class="mt-2">
                <v-col>
                  <v-row dense class="d-flex">
                    <v-col v-for="equipment in totalEquipments" :key="equipment.id" class="flex-grow-0"
                      :class="{'flex-grow-1': $vuetify.breakpoint.mdAndDown}" cols="12" sm="12" lg="6">
                      <v-item v-slot="{ active, toggle }">
                        <div :class="{'selected-item': active, 'not-selected-item': !active}">
                          <v-card @click="toggle">
                            <request-for-quotation-offer-equipments-card :id="equipment.id"
                              :production-date="equipment.productionDate" :manufacturer="equipment.manufacturer"
                              :type="equipment.type" :name="equipment.name" :image="equipment.image"
                              :documents="equipment.documents"></request-for-quotation-offer-equipments-card>
                            <div class="selection-btn">
                              <v-sheet v-if="active" width="32" height="32" color="success"
                                class="selection-icon d-flex justify-center align-center">
                                <div>
                                  <v-icon color="white">mdi-check</v-icon>
                                </div>
                              </v-sheet>
                            </div>
                          </v-card>
                        </div>
                      </v-item>
                    </v-col>
                  </v-row>
                </v-col>
              </v-row>
            </v-col>
          </v-item-group>
        </v-card-text>
        <v-card-text
            v-else
            style="height: 70vh"
            class="d-flex flex-column align-center justify-center">
          <img
              class="mx-auto rounded-circle"
              height="160"
              src="/equipment-placeholder.png" />
          <p class="mt-8">
            {{ $trans('requestForQuotationThreadPage.equipmentsSection.modifyEquipmentsDialog.WeCouldNotFoundAnyEquipmentsToOfferForThisRFQ') }}
          </p>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions style="box-shadow: 0px -2px 7px 4px rgba(0, 0, 0, 0.1);">
          <v-row class="d-flex justify-end my-2" dense no-gutters>
            <v-btn text @click="closeDialog">
              {{ $trans('requestForQuotationThreadPage.equipmentsSection.modifyEquipmentsDialog.cancel') }}
            </v-btn>
            <v-btn
                color="primary"
                :class="{'ml-2': !$isRtl(), 'mr-2': $isRtl()}"
                @click="confirmSelection"
                :loading="confirmationLoading"
                :disabled="totalEquipments.length === 0">
              {{ $trans('requestForQuotationThreadPage.equipmentsSection.modifyEquipmentsDialog.confirm') }}
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>
Vue.component("request-for-quotation-offer-equipments-modify", {
  template: "#request-for-quotation-offer-equipments-modify",

  props: {
    currentEquipments: {
      type: Array,
      required: true,
    },
    companyId: {
      type: String,
      required: true,
    }
  },

  data() {
    return {
      selectedEquipments: this.currentEquipments,
      totalEquipments: [],
      dialog: true,
      searchFilter: '',
      selection: [],
      requestForQuotationId: this.$javalin.pathParams["requestForQuotationId"],
      threadId: this.$javalin.pathParams["threadId"],
      equipmentLoading: false,
      confirmationLoading: false
    }
  },

  created() {
    this.getEligibleEquipments()
  },

  methods: {
    getEligibleEquipments(queryString = "") {
      this.equipmentLoading = true;
      fetch(`/api/request-for-quotations/${this.requestForQuotationId}/threads/${this.threadId}/eligible-equipment?${queryString}`)
          .then(res => res.json())
          .then(data => {
            this.totalEquipments = data;
            this.selection = this.selectedEquipments.map(equipment => data.findIndex(e => e.id === equipment.id));
            this.equipmentLoading = false;
            this.$emit('loaded');
          });
    },
    searchEquipments() {
      this.getEligibleEquipments(`name=${this.searchFilter}`)
    },
    closeDialog() {
      this.confirmationLoading = false;
      this.dialog = false;
      this.$emit('close');
    },

    confirmSelection() {
      this.confirmationLoading = true;
      let finalSelectedEquipment = this.selection.map(i => this.totalEquipments[i]);
      let addedEquipment = finalSelectedEquipment
          .filter(item => this.selectedEquipments.findIndex(selected => selected.id === item.id) === -1)
          .map(item => { return { id: item.id } });
      let removedEquipment = this.selectedEquipments
          .filter(item => finalSelectedEquipment.findIndex(selected => selected.id === item.id) === -1)
          .map(item => { return { id: item.id } });

      let body = {
        addedEquipment,
        removedEquipment
      };

      fetch(
        `/api/request-for-quotations/${this.requestForQuotationId}/threads/${this.threadId}/offered-equipment`,
        { method: 'PATCH', body: JSON.stringify(body), 'Content-Type': 'application/json' }
      ).finally(() => {
        this.$emit('modified');
        this.closeDialog();
      });
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