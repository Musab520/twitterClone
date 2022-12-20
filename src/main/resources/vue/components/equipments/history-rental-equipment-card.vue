<template id="history-rental-equipment-card">
  <div>
    <v-card
        class="mx-auto"
        outlined>
      <v-img
          height="160"
          :src="equipment_image ?? '/equipment-placeholder.png'"
      >
        <v-chip rounded readonly class="  float-right ma-2" :color="getavailabilityColor(equipment_availability)"
                style="
    font-weight: normal;
    font-size: 12px;"
                dark>
          {{equipment_availability }}
        </v-chip>
      </v-img>
      <v-row class="d-flex justify-space-between align-center my-2 ml-4 mr-3">
        <h6 class="title">{{ equipment_name }}  </h6>
        <!--        <v-icon color="accent">mdi-file-document-outline</v-icon>-->
      </v-row>
      <v-row class="d-flex justify-start align-center my-2 ml-4 mr-3">
        <template>
          <v-chip
              class="body-2 mr-2"
              color="blue"
              label
              small
              outlined>
            {{ equipment_type }}
          </v-chip>
          <v-chip
              class="body-2 mr-2"
              color="blue"
              label
              small
              outlined>
            {{ company_name }}
          </v-chip>
          <v-chip
              class="body-2 mr-2"
              color="blue"
              label
              small
              outlined>
            {{ serial_number }}
          </v-chip>
          <v-chip
              class="body-2 mr-2"
              color="blue"
              label
              small
              outlined>
            {{ equipment_manufacturer }}
          </v-chip>
        </template>
      </v-row>

      <v-row class="d-flex justify-start justify-space-between  py-3 mt-0 ml-4 mr-3">
        <div>
          <span class="text-subtitle" style="font-size: 11px" >{{ $trans('historyPage.equipmentCardDetailsForRental.dateFrom') }}</span><br/>
          <span color="black"  style="font-size: 15px;font-weight: 400">  {{reservation_datefrom}}</span>
        </div>
        <div>
          <span class="text-subtitle" style="font-size: 11px" >{{ $trans('historyPage.equipmentCardDetailsForRental.dateTo') }}</span><br/>
          <span  color="black"  style="font-size: 15px !important; font-weight: 400">{{reservation_dateto}}</span>
        </div>
      </v-row>
    </v-card>
  </div>
</template>
<script>
Vue.component("history-rental-equipment-card", {
  template: "#history-rental-equipment-card",
  props: {
    id: {
      equipment_type: String,
      required: true
    },
    equipment_name: {
      type: String,
      required: true
    },
    company_name: {
      type: String,
      required: true
    },
    equipment_type: {
      type: String,
      required: true
    },
    equipment_availability: {
      type: String,
      required: true
    },
    equipment_image: {
      type: String,
      required: true
    } ,
    equipment_status: {
      type: String,
      required: true
    } ,
    reservation_datefrom: {
      type: String,
      required: true
    },
    reservation_dateto: {
      type: String,
      required: true
    },
    serial_number: {
      type: String,
      required: true
    },
    equipment_manufacturer: {
      type: String,
      required: true
    }
  },
  methods:
  {
    getavailabilityColor(availability)
    {
      if(availability == 'Available') {return 'green'}
      else {return 'red'}
    }
  }
});

</script>