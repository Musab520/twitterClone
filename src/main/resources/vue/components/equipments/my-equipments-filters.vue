<template id="my-equipments-filters">
  <v-sheet class="mx-auto" :outlined="$vuetify.breakpoint.mdAndUp" rounded>
    <form ref="filters">
      <div class="pa-5 rounded-tl-0 ">
        <v-row class="row py-2 d-flex justify-space-between align-center ">
                                <span class="primary--text font-weight-bold d-none d-md-block">
                                    <v-icon>mdi-filter-outline </v-icon>
                                    {{ $trans('myEquipmentsPage.filters') }}
                                </span>
          <v-btn @click="ClearFields" class="text-uppercase pr-0 d-none d-md-block" color="grey" plain>
            {{ $trans('myEquipmentsPage.clearFilters') }}
          </v-btn>
        </v-row>
        <v-row class="  px-0 d-flex flex-row">
          <v-select v-if="equipmentsTypes.loaded" :items="getEquipmentsTypes" v-model="typeFilter"
                    @change="filterMyEquipments"
                    :label="$trans('myEquipmentsPage.typeFilterPlaceholder')" outlined></v-select>
        </v-row>
        <v-row class="  px-0 d-flex flex-row">
          <v-select v-if="equipmentsManufacturers.loaded" :items="getEquipmentsManufacturers"
                    v-model="manufacturerFilter" @change="filterMyEquipments"
                    :label="$trans('myEquipmentsPage.manufacturerFilterPlaceholder')" outlined>
          </v-select>
        </v-row>
        <v-row class="  px-0 d-flex flex-row">
          <v-select v-if="equipmentsStatuses.loaded" :items="getEquipmentsStatuses"
                    v-model="statusFilter" @change="filterMyEquipments"
                    :label="$trans('myEquipmentsPage.statusFilterPlaceholder')" outlined></v-select>
        </v-row>
        <row align="center" justify="center"
             class="row py-2   d-flex justify-space-between align-center ">
                                <span class="primary--text ">{{ $trans('myEquipmentsPage.documentsAttachedFilter')
                                  }}</span>
          <v-switch v-model="documentsAttechedFilter" @change="filterMyEquipments"
                    class="pt-0 mt-0" hide-details></v-switch>
        </row>
      </div>
    </form>
  </v-sheet>
</template>

<script>
Vue.component("my-equipments-filters", {
  template: "#my-equipments-filters",
  data:()=>({
    typeFilter: "",
    statusFilter: "",
    companyFilter: "",
    manufacturerFilter: "",
    documentsAttechedFilter: false,

    equipmentsTypes: ["All"],
    equipmentsManufacturers: ["All"],
    equipmentsStatuses: ["All"],
  }),
  computed:
      {
        getEquipmentsTypes() {
          return this.equipmentsTypes.data;

        },
        getEquipmentsManufacturers() {
          return this.equipmentsManufacturers.data;
        },
        getEquipmentsStatuses() {
          return this.equipmentsStatuses.data;
        },
      },
  created()
  {
    this.equipmentsTypes = new LoadableData(`/api/equipments/lookup/types`);
    this.equipmentsManufacturers = new LoadableData(`/api/equipments/lookup/manufacturers`);
    this.equipmentsStatuses = new LoadableData(`/api/equipments/lookup/statuses`);
  },
  mounted()
  {
    this.equipmentsTypes.refresh();
    this.equipmentsStatuses.refresh();
    this.equipmentsManufacturers.refresh();
  },
  methods:
      {
        ClearFields() {
          this.statusFilter = null;
          this.typeFilter = null;
          this.manufacturerFilter = null;
          this.documentsAttechedFilter = null;
          this.$emit('clear-fields');
        },
        filterMyEquipments() {
          let query = []

          if (this.statusFilter && this.statusFilter !== "All") {
            query.push(`status=${this.statusFilter}`)
          }
          if (this.manufacturerFilter && this.manufacturerFilter !== "All") {
            query.push(`manufacturer=${this.manufacturerFilter}`)
          }
          if (this.documentsAttechedFilter) {
            query.push(`documentsAtteched=${this.documentsAttechedFilter}`)
          }
          if (this.typeFilter && this.typeFilter !== "All") {
            query.push(`type=${this.typeFilter}`)
          }

          if (this.sortingCriteria) {
            query.push(`orderBy=${this.sortingCriteria.orderBy}`)
            query.push(`order=${this.sortingCriteria.order}`)
          }

          this.$emit('filter-my-equipments',query);
        },
      }

});

</script>

<style scoped>

</style>