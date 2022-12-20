<template id="request-for-quotation-equipments-and-offers-equipment-card">
  <v-sheet
      outlined
      rounded>
    <v-row
        dense
        no-gutters
        class="d-flex flex-column flex-grow-0">
      <v-col class="flex-grow-0">
        <img
            :src="image ?? '/equipment-placeholder.png'"
            class="equipment-image"/>

      </v-col>
      <v-col class="d-flex flex-column">
        <h6 class="subtitle-2 pt-2" :class="{'pl-2': !$isRtl(), 'pr-2': $isRtl()}">
          {{ name }}
        </h6>
      </v-col>
      <v-col class="my-3 px-2 d-flex align-center justify-space-between">
        <div class="body-2 equipment-info-tags">
          <span>{{ type }}</span>
          <span v-if="manufacturer">|</span>
          <span>{{ manufacturer }}</span>
        </div>
        <div>
          <v-icon color="accent">mdi-file-document-outline</v-icon>
        </div>
      </v-col>
    </v-row>
  </v-sheet>
</template>
<script>
Vue.component("request-for-quotation-equipments-and-offers-equipment-card", {
  template: "#request-for-quotation-equipments-and-offers-equipment-card",
  props: {
    id: {
      type: String,
      required: true,
    },
    image: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      required: true,
    },
    type: {
      type: String,
      required: true,
    },
    manufacturer: {
      type: String,
      required: true,
    },
    documents: {
      type: Boolean,
      required: true,
    },
  },
});
</script>
<style scoped>
.equipment-info-tags {
  color: rgba(0, 0, 0, 0.6);
}

.equipment-image {
  max-height: 100%;
  max-width: 100%;
}
</style>
