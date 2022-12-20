<template id="equipments">
  <app-layout>
    <v-container fill-height fluid class="d-flex align-baseline pa-0 overflow-hidden">
      <v-row dense no-gutters class="fill-height" style="width: inherit">
        <v-col md="6" lg="4" :class="mobileCurrentPage=='equipments'&&!showFilterPage?'d-block':'d-lg-block d-none'">
          <v-sheet class="fill-height equipments-list-remove-border">
            <div class="fill-height"
                 :class="{'equipment-list': $vuetify.breakpoint.mdAndUp}">
              <v-row no-gutters class="d-flex align-content-start">
                <v-col v-for="equipment in equipments.data" :key="equipment.id">
                  <equipment-list-card
                      :id="equipment.id"
                      :company-name="equipment.companyName"
                      :name="equipment.name"
                      :type="equipment.type"
                      :manufacturer="equipment.manufacturer"
                      :serial-number="equipment.serialNumber"
                      :image="equipment.image"
                      :availability="equipment.availability"
                      :can-be-reserved="equipment.status !== 'Disabled'"
                      :longitude="equipment.longitude"
                      :latitude="equipment.latitude"
                      :is-map-centered-for-equipment="currentCenteredEquipment === equipment.id"
                      :production-year="equipment.productionDate"
                      @center-map="handleCenterMapOnEquipmentMarker"
                  >
                  </equipment-list-card>
                </v-col>
                <div class="py-16 d-flex flex-column align-center justify-center"
                     v-if="this.equipments.loaded && this.equipments.data.length === 0">
                  <img class="mx-auto" style="width:20%" src="/no_data.svg"/>
                  <p class="pt-4 body-2">
                    {{ $trans('misc.noResultsFound') }}
                  </p>
                </div>
              </v-row>
            </div>
          </v-sheet>
        </v-col>
      </v-row>
    </v-container>
  </app-layout>
</template>
<script>
Vue.component("equipments", {
  template: "#equipments",
  data() {
    return {
      equipments: [],
      companies: [],
      equipmentTypes: [],
      equipmentManufacturers: [],
      equipmentWorkLocations: [],
      showFromPicker: false,
      showToPicker: false,
      companyFilter: "",
      allCompanyFilter: false,
      typeFilter: "",
      allTypeFilter: false,
      manufacturerFilter: "",
      allManufacturerFilter: false,
      workLocationFilter: "",
      allWorkLocationFilter: false,
      fromFilter: "",
      toFilter: "",
      globalSearchFilter: "",
      currentCompanyId: "",
      sortingOptions: [
        {
          'text': 'Equipment Name - Descending',
          'value': {'orderBy': 'equipment.name', 'order': 'desc'}
        },
        {
          'text': 'Equipment Name - Ascending',
          'value': {'orderBy': 'equipment.name', 'order': 'asc'}
        },
        {
          'text': 'Availability - Descending',
          'value': {'orderBy': 'availability', 'order': 'desc'}
        },
        {
          'text': 'Availability - Ascending',
          'value': {'orderBy': 'availability', 'order': 'asc'}
        },
      ],
      sortingCriteria: "",
      map: {
        zoom: 4,
        center: [0.0, 0.0],
        url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
        attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
        marker: [],
        mapOptions: {zoomControl: false}
      },
      currentCenteredEquipment: null,

      mobileCurrentPage: 'equipments',//equipments/maps
      showFilterPage: false,
    }
  },
  async created() {

    let urlParams = new URLSearchParams(window.location.search);

    let userId = this.$javalin.state.userDetails.user_id;
    this.currentCompanyId = new LoadableData(`/api/users/${userId}`).data.companyId;

    Promise.all([
      this.loadCompaniesFilterOptions(),
      this.loadTypeFilterOptions(),
      this.loadManufacturersFilterOptions(),
      this.loadWorkLocationOptions()
    ]).then(() => {
      if (urlParams.has('type')) {
        this.typeFilter = urlParams.get('type').split(',')
        this.filterEquipments()
      } else if (urlParams.has('searchTerm')) {
        this.globalSearchFilter = urlParams.get('searchTerm')
        this.filterEquipments()
      } else if (urlParams.has('from') && urlParams.has('to')) {
        this.fromFilter = new Date(parseInt(urlParams.get('from'))).toISOString().split('T')[0];
        this.toFilter = new Date(parseInt(urlParams.get('to'))).toISOString().split('T')[0];
        this.filterEquipments()
      } else {
        this.getEquipments()
      }
    })
  },
  methods: {
    getEquipments() {
      this.equipments = new LoadableData(`/api/equipments`)
    },
    filterEquipments() {
      let query = []
      if (this.companyFilter && !this.companyFilter.includes('-1')) {
        query.push(`companyId=${this.companyFilter}`);

        if (this.allCompanyFilter) {
          this.allCompanyFilter = false;
          this.companyFilter = [];
        }

      } else if (this.companyFilter) {
        if (this.companyFilter.length !== 1 && this.companyFilter.length !== this.companies.length && this.allCompanyFilter) {
          this.companyFilter = this.companyFilter.filter(e => e !== '-1');
          this.allCompanyFilter = false;
          query.push(`companyId=${this.companyFilter}`);
        } else {
          this.companyFilter = this.companies.map(function(i) {
            return i.value;
          });
          this.allCompanyFilter = true;
        }
      }

      if (this.typeFilter && !this.typeFilter.find(e => e === 'All')) {
        query.push(`type=${this.typeFilter}`)

        if(this.allTypeFilter){
          this.allTypeFilter = false;
          this.typeFilter = [];
        }
      } else if (this.typeFilter) {
        if (this.typeFilter.length !== 1 && this.typeFilter.length !== this.equipmentTypes.length && this.allTypeFilter) {
          this.typeFilter = this.typeFilter.filter(e => e !== 'All');
          this.allTypeFilter = false;
          query.push(`type=${this.typeFilter}`)
        } else {
          this.typeFilter = this.equipmentTypes;
          this.allTypeFilter = true;
        }
      }

      if (this.manufacturerFilter && !this.manufacturerFilter.find(e => e === 'All')) {
        query.push(`manufacturer=${this.manufacturerFilter}`)

        if(this.allManufacturerFilter){
          this.allManufacturerFilter = false;
          this.manufacturerFilter = [];
        }

      } else if (this.manufacturerFilter) {
        if (this.manufacturerFilter.length !== 1 && this.manufacturerFilter.length !== this.equipmentManufacturers.length && this.allManufacturerFilter) {
          this.manufacturerFilter = this.manufacturerFilter.filter(e => e !== 'All');
          this.allManufacturerFilter = false;
          query.push(`manufacturer=${this.manufacturerFilter}`)
        } else {
          this.manufacturerFilter = this.equipmentManufacturers;
          this.allManufacturerFilter = true;
        }
      }

      if (this.workLocationFilter && !this.workLocationFilter.find(e => e === 'All')) {
        query.push(`workLocation=${this.workLocationFilter}`)

        if(this.allWorkLocationFilter){
          this.allWorkLocationFilter = false;
          this.workLocationFilter = [];
        }

      } else if (this.workLocationFilter) {

        if (this.workLocationFilter.length !== 1 && this.workLocationFilter.length !== this.allWorkLocationFilter.length && this.allWorkLocationFilter) {
          this.workLocationFilter = this.workLocationFilter.filter(e => e !== 'All');
          this.allWorkLocationFilter = false;
          query.push(`workLocation=${this.workLocationFilter}`)
        } else {
          this.workLocationFilter = this.allWorkLocationFilter;
          this.allWorkLocationFilter = true;
        }
      }

      if (this.fromFilter) {
        query.push(`from=${this.fromFilter}`)
      }

      if (this.toFilter) {
        query.push(`to=${this.toFilter}`)
      }

      if (this.globalSearchFilter) {
        query.push(`searchTerm=${this.globalSearchFilter}`)
      }

      if (this.sortingCriteria) {
        query.push(`orderBy=${this.sortingCriteria.orderBy}`)
        query.push(`order=${this.sortingCriteria.order}`)
      }

      this.equipments = new LoadableData(`/api/equipments/search?${query.join("&")}`)
    },
    clearFiltersHandler() {
      this.companyFilter = ""
      this.typeFilter = ""
      this.manufacturerFilter = ""
      this.workLocationFilter = ""
      this.fromFilter = ""
      this.toFilter = ""
      this.globalSearchFilter = ""
      this.getEquipments()
    },
    datePickerValueSelection(pickerMenuRef, value) {
      pickerMenuRef.save(value)
      this.filterEquipments();
    },
    datePickerValueClearance(pickerMenuRef, valueRefInInstance) {
      pickerMenuRef.save("")
      this[valueRefInInstance] = ""
      this.filterEquipments();
    },
    loadCompaniesFilterOptions() {
      this.companies.push({
        text: "All",
        value: "-1",
      });

      fetch(`/api/companies/search?excludeCompanyId=${this.currentCompanyId}`)
          .then(response => response.json())
          .then(companies => {
            this.companies.push(...companies.map(company => ({
                  text: company.name,
                  value: company.id,
                })
            ))
          })
    },
    loadTypeFilterOptions() {
      this.equipmentTypes.push("All");
      this.equipmentTypes.push(...new LoadableData(`/api/equipments/lookup/types`).data)
    },
    loadManufacturersFilterOptions() {
      this.equipmentManufacturers.push("All");
      this.equipmentManufacturers.push(...new LoadableData(`/api/equipments/lookup/manufacturers`).data)
    },
    loadWorkLocationOptions() {
      this.equipmentWorkLocations.push("All");
      this.equipmentWorkLocations.push(...new LoadableData(`/api/equipments/lookup/work-locations`).data);
    },
    GetCenterFromDegrees(data) {
      if (!(data.length > 0)) {
        return false
      }

      const numberOfCoordinates = data.length
      let X = 0.0
      let Y = 0.0
      let Z = 0.0

      data.forEach(datum => {
        let lat = datum[0] * Math.PI / 180
        let long = datum[1] * Math.PI / 180

        let a = Math.cos(lat) * Math.cos(long)
        let b = Math.cos(lat) * Math.sin(long)
        let c = Math.sin(lat)

        X += a
        Y += b
        Z += c
      })

      X /= numberOfCoordinates
      Y /= numberOfCoordinates
      Z /= numberOfCoordinates

      let long = Math.atan2(Y, X)
      let hyp = Math.sqrt(X * X + Y * Y)
      let lat = Math.atan2(Z, hyp)

      let newX = (lat * 180 / Math.PI)
      let newY = (long * 180 / Math.PI)

      return [newX, newY]
    },
    handleCenterMapOnEquipmentMarker(data) {
      this.map.center = data.coordinates
      this.currentCenteredEquipment = data.equipmentId
      setTimeout(() => this.map.zoom = 10, 300)
    },
    recenterMap() {
      if (this.equipmentsMarkers.length > 0) {
        this.map.center = this.GetCenterFromDegrees(this.equipmentsMarkers)
      }
    },
    switchCurrentPage() {
      this.mobileCurrentPage == 'equipments' ? this.mobileCurrentPage = 'map' : this.mobileCurrentPage = 'equipments';
    }
  },
  computed: {
    allSelected() {
      return true;
    },
    equipmentsMarkers() {
      if (this.equipments.data) {
        return Object.values(this.equipments.data).map(equipment => ([equipment.latitude, equipment.longitude]))
      }

      return []
    }
  }
});
</script>
<style>
.remove-border-on-filter {
  border-top: none !important;
  border-left: none !important;
  border-bottom: none !important;
}

.equipments-list-remove-border {
  border-left: none !important;
}

.global-search-remove-border {
  border-top: none !important;
  border-left: none !important;
  border-right: none !important;
}

.equipment-list {
  overflow-x: hidden;
  overflow-y: auto;
  flex-grow: 1;
  position: relative;
  height: 86vh;
}

.equipment-list-content {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
}

.current-page-button {
  position: fixed;
  bottom: 2rem;
  left: 50%;
  transform: translateX(-50%);
}

</style>