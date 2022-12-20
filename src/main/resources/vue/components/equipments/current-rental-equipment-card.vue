<template id="current-rental-equipment-card">
  <div>
    <v-card
        class="mx-auto"
        outlined>
      <div
            justify="center" style="height: 40px;
 display: flex;
  align-items: center;
  justify-content: center;" cols="12" fluid class="lighten-4 text-center" :class="getColor()">
        <span  class=" lighten-3" :class="getTextColor()">
          {{rental_daysleft}} {{ $trans('currentRentalPage.equipmentCardDetails.daysLeft') }}
        </span>
      </div>
        <v-img
          height="160"
          :src="equipment_image ?? '/equipment-placeholder.png'"
      >
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
      <span class="text-subtitle" style="font-size: 11px" >{{ $trans('currentRentalPage.equipmentCardDetails.dateFrom') }}</span><br/>
      <span color="black"  style="font-size: 15px;font-weight: 400">  {{rental_datefrom}}</span>
      </div>
      <div>
        <span class="text-subtitle" style="font-size: 11px" >{{ $trans('currentRentalPage.equipmentCardDetails.dateTo') }}</span><br/>
        <span  color="black"  style="font-size: 15px !important; font-weight: 400">{{rental_dateto}}</span>
      </div>
    </v-row>
      <v-row class="d-flex justify-start justify-space-between pb-5 py-3 mt-0 ml-4 mr-3">
        <div>
          <span class="text-subtitle" style="font-size: 11px" >{{ $trans('currentRentalPage.equipmentCardDetails.reserverFilterPlaceholder') }}</span><br/>
          <span color="black"  style="font-size: 15px;font-weight: 400">  {{company_name }}</span>
        </div>
      </v-row>
    </v-card>
  </div>
</template>
<script>
Vue.component("current-rental-equipment-card", {
  template: "#current-rental-equipment-card",
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
    equipment_image: {
      type: String,
      required: true
    } ,

    rental_datefrom: {
      type: String,
      required: true
    },
    rental_dateto: {
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
    },
    rental_daysleft: {
      type: Number,
      required: true
    }


  }
  ,methods:
      {
        getColor()
        {

          if(this.rental_daysleft>=30) {return 'green'}
          else if(this.rental_daysleft>=10) {return 'orange'}
          else {return 'red' }
        },getTextColor()
        {

          if(this.rental_daysleft>=30) {return 'green--text'}
          else if(this.rental_daysleft>=10) {return 'orange--text'}
          else {return 'red--text' }
        }
      }

});

</script>