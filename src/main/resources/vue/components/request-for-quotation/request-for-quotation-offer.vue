<template id="request-for-quotation-offer">
  <v-col class="mt-4 d-flex flex-column justify-space-between">
    <table class="offer-table">
      <tbody>
      <tr>
        <td class="offer-table-cell px-4">
          {{ $trans('requestForQuotationThreadPage.myOfferSection.offeredEquipments') }}
        </td>
        <td class="offer-table-cell-value px-4">{{ offeredEquipmentsCount }}</td>
      </tr>
      <tr>
        <td class="offer-table-cell px-4">
          {{ $trans('requestForQuotationThreadPage.myOfferSection.from') }}
        </td>
        <td class="offer-table-cell-value px-4">{{ from }}</td>
      </tr>
      <tr>
        <td class="offer-table-cell px-4">
          {{ $trans('requestForQuotationThreadPage.myOfferSection.to') }}
        </td>
        <td class="offer-table-cell-value px-4">{{ to }}</td>
      </tr>
      <tr>
        <td class="offer-table-cell px-4">
          {{ $trans('requestForQuotationThreadPage.myOfferSection.location') }}
        </td>
        <td class="offer-table-cell-value px-4">{{ location }}</td>
      </tr>
      </tbody>
    </table>
    <v-row class="mt-8" dense>
      <v-col cols="12" md="9">
        <v-text-field
            :disabled="disabled"
            v-model="totalPrice"
            :label="$trans('requestForQuotationThreadPage.myOfferSection.totalPrice')"
            outlined
            class="rounded"
            type="number"
        ></v-text-field>
      </v-col>
      <v-col cols="12" md="3">
        <v-select
            :disabled="disabled"
            outlined
            :items="currencies"
            v-model="currency_type"
        ></v-select>
      </v-col>
    </v-row>
    <v-row class="mt-4 d-flex justify-end" :class="{'mr-0': !$isRtl(), 'ml-0': $isRtl()}" dense>
      <v-btn
          :disabled="disabled"
          @click="updateOfferPrice"
          large
          outlined>
        {{ $trans('requestForQuotationThreadPage.myOfferSection.saveButton') }}
      </v-btn>
    </v-row>
  </v-col>
</template>
<script>
Vue.component("request-for-quotation-offer", {
  template: "#request-for-quotation-offer",
  props: {
    offeredEquipmentsCount: {
      type: String,
      required: true,
    },
    from: {
      type: String,
      required: true,
    },
    to: {
      type: String,
      required: true,
    },
    location: {
      type: String,
      required: true,
    },
    price: {
      type: Number,
      required: true,
    },
    status:{
      type: String,
      required: true,
    },
    current_user_party:{
      type: String,
      required: true,
    },
    disabled: {
      type: Boolean,
      default: true
    },
    currency_type:{
      type: String,
      required: true,
    }
  },
  data() {
    return {
      totalPrice: this.price,
      currencies: [],
      currency: this.currency_type
    }
  },
  created() {
    this.currencies.push(...new LoadableData(`/api/request-for-quotations/threads/lookup/CurrencyTypes`).data);

  }, methods:{
    updateOfferPrice() {
      this.$emit('update-offer-price', { totalPrice: this.totalPrice,currency_type:this.currency_type })
    }
  }
});
</script>
<style scoped>
.offer-table {
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-collapse: collapse;
}

.offer-table-cell {
  border: 1px solid rgba(0, 0, 0, 0.12);
  color: #757575;
  height: 48px;
}

.offer-table-cell-value {
  border: 1px solid rgba(0, 0, 0, 0.12);
}

</style>