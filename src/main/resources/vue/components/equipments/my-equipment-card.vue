<template id="my-equipment-card">
    <v-card outlined >
      <v-img height="160px" :src="image ?? '/equipment-placeholder.png'">
        <div class="circle float-start mt-2 ms-2" :style="`background-color: ${getavailabilityColor(availability)}`"></div>
      </v-img>
      <v-container class="equipment-card--inner-content ">
        <div class="d-flex justify-space-between align-center">
          <h6 class="title" >
            <span>{{ name }}</span>
            <v-icon v-if="verified" class="ms-2" color="hsla(122, 39%, 49%, 1)">mdi-check-circle</v-icon>
          </h6>
          <v-icon :color="getdocumentsIconColor(documents_atteched)">mdi-file-document-outline</v-icon>
        </div>
        <label class="d-block grey--text">
          {{ [ type, manufacturer ].join(" | ") }}
        </label>

        <div v-if="utilizationEnabled">
          <span>Utilization</span>
          <a class="ms-1 text-decoration-underline" style="color: blue;font-weight: 500;"  @click="utilizationReportPopup = true">VIEW</a>
          <utilization-report :dialog.sync="utilizationReportPopup" :id="id" />
        </div>
        <div v-else class="grey--text">
          <span>Utilization</span>
          <span class="ms-1 text-decoration-underline" style="font-weight: 500;">VIEW</span>
        </div>

        <div v-if="utilizationEnabled" class="d-flex justify-space-between align-center" style="white-space: pre-wrap">
          <v-progress-linear :value="utilization" :color="getUtilizationColor(utilization)" :style="{'background-color':'SeaShell'}"> </v-progress-linear>
          <span class="ms-2"> {{ utilization }}% </span>
        </div>
        <div v-else class="d-flex justify-space-between align-center grey--text" style="white-space: pre-wrap">
          <v-progress-linear  :style="{'background-color':'WhiteSmoke'}" :value="0"></v-progress-linear>
          <span class="ms-2 "> --% </span>
        </div>

        <div class="d-flex justify-space-between">
          <span color="primary">
            {{ $trans('myEquipmentsPage.equipmentCardDetails.disableFlag') }}
          </span>
          <v-switch inset v-model="disable" @change="UpdateEquipmentStatus" class="no-label-switch ma-0 pa-0" hide-details>
          </v-switch>
        </div>

        <v-card-actions class="ma-0 pa-0 mt-5">
          <v-btn outlined block large @click="uploadDialog = true" color="primary">
            {{ $trans('myEquipmentsPage.equipmentCardDetails.modifyDocsButton') }}
          </v-btn>
          <documents :dialog.sync="uploadDialog" :id="id" :entity_name="entity_name" />
        </v-card-actions>

      </v-container>
    </v-card>
</template>
<script>
Vue.component("my-equipment-card", {
  template: "#my-equipment-card",
  props: {
    id: {
      type: String,
      required: true
    },
    entity_name: {
      type: String,
      required: true
    },
    name: {
      type: String,
      required: true
    },
    manufacturer: {
      type: String,
      required: true
    },
    serialnumber: {
      type: String,
      required: true
    },
    type: {
      type: String,
      required: true
    },
    availability: {
      type: String,
      required: true
    },
    image: {
      type: String,
      required: true
    },
    disable: {
      type: Boolean,
      required: true
    },
    documents_atteched: {
      type: Boolean,
      required: true
    },
    verified: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      uploadDialog: false,
      utilizationReportPopup: false,
      entity_name: "equipment",
      utilization: 0,
      utilizationEnabled: false
    }
  },
  
  created() {
    this.entity_name = "equipment";
    fetch(`/api/feature-flag/utilization-report`)
      .then(res => res.json())
      .then(res => {
        this.utilizationEnabled = res;
        
        if (res) {
          fetch(`/api/equipments/${this.id}/utilization`)
            .then(res => res.json())
            .then(res => {
              this.utilization = Math.round(res.value * 100);
            });
        }
      });
  },
  
  methods: {
    getavailabilityColor(availability) {
      if (availability == 'Available') { return 'hsla(122, 39%, 49%, 1)' }
      else { return 'red' }
    },
    getdocumentsIconColor(documents_atteched) {
      if (documents_atteched == true) { return 'accent' }
      else { return 'orange lighten-4' }
    },
    getUtilizationColor(utilization) {
      if (utilization * 3 < 100) return "hsla(0, 100%, 66%, 1)";
      else if (utilization * 3 < 200) return "hsla(37, 95%, 53%, 1)";
      else return "hsla(122, 39%, 49%, 1)";
    },
    UpdateEquipmentStatus() {
      let newStatus = null;
      if (this.disable) {
        newStatus = "Disabled"
      } else {
        newStatus = null;
      }
      fetch(`/api/equipments/` + this.id + '/updateEquipmentStatus', {
        method: "PUT", 'Content-Type': 'application/json',
        body: JSON.stringify({
          status: newStatus
        })
      }).then(() => {
      });
    }
  }
});
</script>
<style>
.circle {
  border: 2px solid white;
  width: 20px;
  height: 20px;
  border-radius: 50%;
}

.no-label-switch .v-input--selection-controls__input {
  margin: 0 !important;
  padding: 0 !important;
}

.equipment-card--inner-content>*
{
  margin-block-end: 0.325rem;
}

</style>