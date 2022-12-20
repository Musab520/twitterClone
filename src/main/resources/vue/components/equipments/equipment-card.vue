<template id="equipment-card">
  <div>
    <v-card
        class="mx-auto"
        outlined>
      <v-img
          height="160"
          :src="image"
      >
        <v-chip rounded
                readonly
                class="ma-2"
                :class="{'float-right': !$isRtl(), 'float-left': $isRtl()}"
                :color="getAvailabilityColor(availability)"
                style="
    font-weight: normal;
    font-size: 12px;"
                small
                dark>
          {{ availability }}
        </v-chip>
      </v-img>
      <v-row class="d-flex justify-space-between align-center my-2 ml-4 mr-3">
        <h6 class="title">{{ name }}</h6>
        <v-icon color="accent">mdi-file-document-outline</v-icon>
      </v-row>
      <v-row class="d-flex justify-start align-center my-2 ml-4 mr-3">
        <v-chip
            v-if="type"
            class="caption"
            :class="{'mr-2': !$isRtl(), 'ml-2': $isRtl()}"
            color="blue"
            label
            small
            outlined>
          {{ type }}
        </v-chip>
        <v-chip
            v-if="manufacturer"
            class="caption"
            :class="{'mr-2': !$isRtl(), 'ml-2': $isRtl()}"
            color="blue"
            label
            small
            outlined>
          {{ manufacturer }}
        </v-chip>
        <v-chip
            v-if="serialNumber"
            class="caption"
            :class="{'mr-2': !$isRtl(), 'ml-2': $isRtl()}"
            color="blue"
            label
            small
            outlined>
          {{ serialNumber }}
        </v-chip>
      </v-row>
      <v-card-actions>
        <v-btn
            outlined
            block
            large
            color="primary"
            class="uppercase"
            @click="reserveDialog = true">
            {{ $trans('companyEquipmentsPage.reserveButton') }}
        </v-btn>
      </v-card-actions>
      <equipment-reservation-dialog
        v-if="reserveDialog"
        :name="name"
        :id="id"
        :type="type"
        :manufacturer="manufacturer"
        :production-year="productionYear"
        @close="reserveDialog = false"></equipment-reservation-dialog>
    </v-card>
  </div>
</template>
<script>
Vue.component("equipment-card", {
  template: "#equipment-card",
  props: {
    id: {
      type: String,
      required: true
    },
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
    serialNumber: {
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
    productionYear: {
      type: String,
      required: true
    }
  },

  data() {
    return {
      reserveDialog: false
    }
  },

  methods: {
    getAvailabilityColor(availability) {
      return availability === 'Available' ? 'green' : 'red'
    }
    
  }
});
</script>