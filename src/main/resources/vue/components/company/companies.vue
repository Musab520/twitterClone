<template id="companies">
  <app-layout>
    <v-col lg="8" sm="12" class="pt-0 mt-0">
      <v-row class="mt-0 align-center filters-container">
            <v-col cols="12" sm="6">
            <v-text-field
                hide-details="auto"
                :label="$trans('companiesPage.companyName')"
                prepend-icon="mdi-magnify"
                @change="filterCompanies"
                v-model="nameFilter"
            ></v-text-field>
            </v-col>
            <v-col cols="12" sm="2" class="py-2">
            <v-select
                hide-details="auto"
                :items="sortingOptions"
                :label="$trans('companiesPage.sort')"
                dense
                outlined
                @change="filterCompanies"
                v-model="sortingCriteria"
            ></v-select>
            </v-col>
            <v-col cols="12" sm="4" class="pt-2 pb-4 pb-sm-2">
            <v-select
                hide-details="auto"
                :items="getCompaniesLocations"
                :label="$trans('companiesPage.location')"
                dense
                outlined
                @change="filterCompanies"
                v-model="locationFilter"
            ></v-select>
            </v-col>
      </v-row>
      <v-row v-if="companies.loading">
        <v-col class="d-flex justify-center mt-10">
          <v-progress-circular
              indeterminate
              color="primary"
          ></v-progress-circular>
        </v-col>
      </v-row>
      <v-row class="mx-auto" justify="center" v-if="companies.loaded && getCompanies.length > 0">
                  <v-col cols="12" xl="4" md="6" xs="12" v-for="company in getCompanies" :key="company.id">
                    <company-card
                        :id="company.id"
                        :name="company.name"
                        :location="company.location"
                        :total-equipments-count="company.totalEquipmentsCount"
                        :available-equipments-count="company.availableEquipmentsCount"
                    ></company-card>
                  </v-col>
      </v-row>
      <v-row class="px-0 mx-0 d-flex flex-column align-center" v-else-if="companies.loaded">
              <img class="mx-auto mb-3 mt-10" width="128" src="/no_data.svg"/>
              <p class="body-2">
                {{ $trans('misc.noResultsFound') }}
              </p>
            </v-row>
      <v-row class="d-flex justify-center" v-if="companies.loadError">
        <p>
          {{ $trans('misc.loadingError') }}
        </p>
      </v-row>
    </v-col>
  </app-layout>
</template>
<script>
Vue.component("companies", {
  template: "#companies",
  data() {
    return {
      companies: [],
      sortingOptions: [
        {
          'text': 'Total Equipments - Descending',
          'value': {'orderBy': 'totalEquipmentsCount', 'order': 'desc'}
        },
        {
          'text': 'Total Equipments - Ascending',
          'value': {'orderBy': 'totalEquipmentsCount', 'order': 'asc'}
        },
        {
          'text': 'Available Equipments - Descending',
          'value': {'orderBy': 'availableEquipmentsCount', 'order': 'desc'}
        },
        {
          'text': 'Available Equipments - Ascending',
          'value': {'orderBy': 'availableEquipmentsCount', 'order': 'asc'}
        },
      ],
      companiesLocations: ["All"],
      nameFilter: "",
      sortingCriteria: "",
      locationFilter: ""
    }
  },
  created() {
    this.companies = new LoadableData(`/api/companies`);
    this.companiesLocations=new LoadableData(`/api/companies/lookup/locations`);
  },
  computed: {
    getCompaniesLocations() {
      let arr = [];
      if (this.companiesLocations.loaded) {
        arr.push(...this.companiesLocations.data)
      }
      return arr;
    },
    getCompanies() {

      return this.companies.data;
    }

  },
  mounted() {
        this.companiesLocations.refresh();
        this.companies.refresh();
      }
  ,methods: {
    filterCompanies() {
      let query = []

      if (this.nameFilter) {
        query.push(`name=${this.nameFilter}`)
      }

      if (this.locationFilter && this.locationFilter !== "All") {
        query.push(`location=${this.locationFilter}`)
      }

      if (this.sortingCriteria) {
        query.push(`orderBy=${this.sortingCriteria.orderBy}`)
        query.push(`order=${this.sortingCriteria.order}`)
      }

      this.companies = new LoadableData(`/api/companies/search?${query.join('&')}`)
    }
  }
});
</script>

<style scoped>
.filters-container
{
  background-color: white;
  position: sticky;
  /* top nav height */
  top:56px;
  z-index: 10;
  border-bottom:1px solid rgba(0,0,0,0.3);
}
</style>