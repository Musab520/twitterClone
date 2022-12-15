<template id="home">
    <app-layout :name="pageName" style="height: 100vh; overflow-y: hidden;">
        <v-row>
            <v-col v-if="flag" class="col-4">
                <v-container ref="scrollableContainer" fluid style="height: 100%;">
                    <v-row ref="searchRow" no-gutters>
                        <v-text-field label="Search"  single-line outlined v-model="searchQuery"></v-text-field>
                        <v-btn height="56px" class="ml-6 px-4" color="primary" @click="flag = !flag">
                            <span class="material-symbols-outlined mr-2 ">
                                edit
                            </span>
                            FILTER
                        </v-btn>
                    </v-row>
                    <v-row no-gutters>
                        <v-col class="pa-0 ma-0" no-gutters
                            style="height:80vh; width: 100%; overflow-y:scroll ; overflow-x: hidden;">
                            <v-expansion-panels>
                                <v-expansion-panel v-for="(item,i) in viewedEquipments" :key="i"
                                    @click="setCenter(item.id)">
                                    <v-expansion-panel-header style="position:relative ">
                                        <div style="position:absolute;top: 18; right: 6;">
                                            <v-menu offset-y>
                                                <template v-slot:activator="{ on, attrs }">
                                                    <span class="material-symbols-outlined" v-bind="attrs" v-on="on"
                                                        style="color:#00000099">
                                                        more_vert
                                                    </span>
                                                </template>
                                                <v-list>
                                                    <v-list-item v-for="(e, index) in items" :key="index"
                                                        @click="openDialog(index, item.id)">
                                                        <v-list-item-icon class="mr-3">
                                                            <span class="material-symbols-outlined  ">
                                                                {{e.icon}}
                                                            </span>
                                                        </v-list-item-icon>
                                                        <v-list-item-content>
                                                            <v-list-item-title v-text="e.title"></v-list-item-title>
                                                        </v-list-item-content>
                                                    </v-list-item>
                                                </v-list>
                                            </v-menu>
                                        </div>

                                        <v-img style="border-radius: 5px;"
                                            lazy-src="https://images.unsplash.com/photo-1621470778644-98bfef3a63d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1744&q=80" max-height="60%"
                                            max-width="40%" src="https://images.unsplash.com/photo-1621470778644-98bfef3a63d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1744&q=80">
                                        </v-img>
                                        <div style="align-self:stretch">
                                            <div class="d-flex flex-column ml-4" style="height:100%">
                                                <div>
                                                    <h3 class="mt-1 mb-2">{{getEquipmentTitle(item)}}</h3>
                                                    <span style="color:#00000099">{{item.type}}</span>
                                                </div>
                                                <a class="mt-auto mb-1" @click="redirect(item.id)"
                                                    style="text-decoration: underline; color:#102338;width: fit-content;">Details
                                                    Page</a>
                                            </div><template id="home">
    <app-layout :name="pageName" style="height: 100vh; overflow-y: hidden;">
        <v-row>
            <v-col v-if="flag" class="col-4">
                <v-container ref="scrollableContainer" fluid style="height: 100%;">
                    <v-row ref="searchRow" no-gutters>
                        <v-text-field label="Search"  single-line outlined v-model="searchQuery"></v-text-field>
                        <v-btn height="56px" class="ml-6 px-4" color="primary" @click="flag = !flag">
                            <span class="material-symbols-outlined mr-2 ">
                                edit
                            </span>
                            FILTER
                        </v-btn>
                    </v-row>
                    <v-row no-gutters>
                        <v-col class="pa-0 ma-0" no-gutters
                            style="height:80vh; width: 100%; overflow-y:scroll ; overflow-x: hidden;">
                            <v-expansion-panels>
                                <v-expansion-panel v-for="(item,i) in viewedEquipments" :key="i"
                                    @click="setCenter(item.id)">
                                    <v-expansion-panel-header style="position:relative ">
                                        <div style="position:absolute;top: 18; right: 6;">
                                            <v-menu offset-y>
                                                <template v-slot:activator="{ on, attrs }">
                                                    <span class="material-symbols-outlined" v-bind="attrs" v-on="on"
                                                        style="color:#00000099">
                                                        more_vert
                                                    </span>
                                                </template>
                                                <v-list>
                                                    <v-list-item v-for="(e, index) in items" :key="index"
                                                        @click="openDialog(index, item.id)">
                                                        <v-list-item-icon class="mr-3">
                                                            <span class="material-symbols-outlined  ">
                                                                {{e.icon}}
                                                            </span>
                                                        </v-list-item-icon>
                                                        <v-list-item-content>
                                                            <v-list-item-title v-text="e.title"></v-list-item-title>
                                                        </v-list-item-content>
                                                    </v-list-item>
                                                </v-list>
                                            </v-menu>
                                        </div>

                                        <v-img style="border-radius: 5px;"
                                            lazy-src="https://images.unsplash.com/photo-1621470778644-98bfef3a63d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1744&q=80" max-height="60%"
                                            max-width="40%" src="https://images.unsplash.com/photo-1621470778644-98bfef3a63d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1744&q=80">
                                        </v-img>
                                        <div style="align-self:stretch">
                                            <div class="d-flex flex-column ml-4" style="height:100%">
                                                <div>
                                                    <h3 class="mt-1 mb-2">{{getEquipmentTitle(item)}}</h3>
                                                    <span style="color:#00000099">{{item.type}}</span>
                                                </div>
                                                <a class="mt-auto mb-1" @click="redirect(item.id)"
                                                    style="text-decoration: underline; color:#102338;width: fit-content;">Details
                                                    Page</a>
                                            </div>
                                        </div>

                                    </v-expansion-panel-header>
                                    <v-expansion-panel-content>
                                        <equipment-tabs-map :equipment-id="item.id"></equipment-tabs-map>
                                    </v-expansion-panel-content>
                                </v-expansion-panel>
                            </v-expansion-panels>
                        </v-col>
                    </v-row>
                </v-container>
            </v-col>
            <v-col v-else class="col-4">
                <v-container ref="scrollableContainer" fluid style="height:95vh" class="d-flex flex-column">
                    <div>
                        <v-row no-gutters class="row d-flex flex-row align-center justify-space-between">
                            <div>
                                <span color="primary" class="title">Filters</span>
                            </div>
                            <div>
                                <v-btn height="56px" class="ml-6 px-4" color="primary" @click="flag = !flag">
                                    <span class="material-symbols-outlined mr-2 ">
                                        edit
                                    </span>
                                    EQUIPMENTS
                                </v-btn>
                            </div>
                        </v-row>
                    </div>
                    <div class="mt-10">
                        <v-select :items="companies" label="Company" v-model="companyFilter" item-text="name"
                            item-value="companyId" return-object filled multiple style="border: none;"></v-select>
                        <v-select label="Type" filled multiple @change=""></v-select>
                        <v-select label="Location" filled multiple @change=""></v-select>
                        <v-select label="Status" filled multiple @change=""></v-select>

                    </div>
                    <div class=" mb-12   mt-auto">
                        <v-btn width="100%" class=" mt-5 py-8 px-4" elevation="0" color="primary" @click="flag = !flag">
                            FILTER
                        </v-btn>

                        <v-btn width="100%" class=" mt-5 py-8 px-4" elevation="0" color="gray"
                            style="border:1px solid #102338;" @click="flag = !flag">
                            CLEAR FILTERS
                        </v-btn>
                    </div>
                </v-container>
            </v-col>
            <v-col class="col-8 pa-0">
                <l-map style="max-height: 100%; width: 100%; z-index: 0;" ref="map" v-bind:zoom="zoom"
                    v-bind:center="center">
                    <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
                    <equipment-marker v-if="equipments.loaded" v-for="equipment in equipments.data"
                        :equipment-id="equipment.id" @click="handleMarkerClick">
                    </equipment-marker>
                </l-map>
            </v-col>
        </v-row>
        <div class="text-center">
            <v-dialog v-model="maintenanceDialog" width="40%" persistent>
                <v-card>
                    <v-card-title>
                        <span class="text-h5">Add new Maintenance Event</span>
                    </v-card-title>

                    <v-card-text class="mt-4">

                        <v-container class="pb-0">
                            <v-row>
                                <v-col cols="12" sm="6" md="6" class="pa-0 pr-2">
                                    <v-text-field v-model="defaultMaintenance.code" outlined label="Event Code"
                                        required>
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pa-0 pl-2">
                                    <v-text-field v-model="defaultMaintenance.details" outlined label="Event Details"
                                        required>
                                    </v-text-field>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="primary darken-1" text @click="closeMaintenance">
                            Cancel
                        </v-btn>
                        <v-btn color="white darken-1" text @click="saveMaintenance" style="background-color:#102338;">
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <v-dialog v-model="documentDialog" width="40%" persistent>
                <v-card>
                    <v-card-title class="">
                        <span class="text-h5">New Document</span>
                    </v-card-title>

                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col cols="12" sm="6" md="6">
                                    <v-text-field v-model="defaultDocument.name" label="Name" outlined>
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6">
                                    <v-file-input v-model="defaultDocument.file" label="File" outlined>
                                    </v-file-input>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="primary darken-1" text @click="closeDocument">
                            Cancel
                        </v-btn>
                        <v-btn color="white darken-1" style="background-color:#102338" text @click="saveDocument">
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <v-dialog v-model="equipmentDialog" width="40%" persistent>
                <v-card>
                    <v-card-title>
                        <span class="text-h5">Edit Equipment</span>
                    </v-card-title>

                    <v-card-text class="mt-2">
                        <v-container class="px-0">
                            <v-row no-gutters>
                                <v-col cols="12" sm="6" md="6" class="pr-2">
                                    <v-text-field v-model="equipmentDetails.type" label="Type" outlined>
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pl-2">
                                    <v-text-field v-model="equipmentDetails.productionDate" outlined
                                        label="Production Date">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pr-2">
                                    <v-text-field v-model="equipmentDetails.licencePlate" outlined
                                        label="Licence Plate">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pl-2">
                                    <v-text-field v-model="equipmentDetails.registrationNumber" outlined
                                        label="Registration Number">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pr-2">
                                    <v-text-field v-model="equipmentDetails.licenceNumber" outlined
                                        label="Licence Number">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pl-2">
                                    <v-text-field v-model="equipmentDetails.manufacturer" outlined label="Manufacturer">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pr-2">
                                    <v-text-field v-model="equipmentDetails.serialNumber" outlined
                                        label="Serial Number">
                                    </v-text-field>
                                </v-col>


                            </v-row>
                        </v-container>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="primary darken-1" text @click="closeEquipment">
                            Cancel
                        </v-btn>
                        <v-btn color="white darken-1" text @click="editEquipment" style="background-color:#102338;">
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>



        </div>
    </app-layout>
</template>
<script>
Vue.component("home", {
    template: "#home",

    data() {
        return {
            pageName: "Home Page",
            url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
            attribution: '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            zoom: 8,
            center: [47.313220, -1.319482],
            equipments: new LoadableData("/api/equipment"),
            scrollerHeight: -1,
            searchQuery: "",
            flag: true,
            items: [
                { title: 'Add Maintenance Event', icon: "settings", path: "/equipment/" },
                { title: 'Add Document', icon: "description", path: "/equipment/" },
                { title: 'Edit', icon: "edit", path: "/equipment/" },
            ],
            defaultMaintenance: {
                "details": "",
                "equipmentId": "",
                "authorId": "",
                "code": ""
            },
            defaultDocument: {
                name: "",
                file: "",
            },
            equipmentDetails: {
                id: '',
                type: '',
                productionDate: '',
                companyId: '',
                licencePlate: '',
                registrationNumber: '',
                licenceNumber: '',
                manufacturer: '',
                serialNumber: '',
            },
            maintenanceDialog: false,
            documentDialog: false,
            equipmentDialog: false,
            equipmentIdSelected: "",
            userId: "",
            companies: [],
            companyFilter: "",
            scrolabeHeight: window.height / 22.222
        };
    },

    created() {
        this.userId = this.$javalin.state.userDetails.user_id;
        this.equipments.refresh();
        window.addEventListener("resize", this.myEventHandler);
    },

    destroyed() {
        window.removeEventListener("resize", this.myEventHandler);
    },

    methods: {

        myEventHandler(e) {
            this.scrolabeHeight = window.height / 22.222
        },

        getEquipmentDetailsLink(equipmentId) {
            return `/equipment/${equipmentId}/details`;
        },

        getEquipmentTitle(equipment) {
            let equipmentTitle = "";
            if (equipment.manufacturer) {
                equipmentTitle += `${equipment.manufacturer}`
            }
            if (equipment.type) {
                equipmentTitle += ` ${equipment.type}`
            }

            if (equipment.licencePlate) {
                equipmentTitle += ` (${equipment.licencePlate})`;
            }
            return equipmentTitle;
        },

        setCenter(equipmentId) {
            let equipment = new LoadableData(`/api/equipment/${equipmentId}`)
            this.center = [equipment.data.latitude, equipment.data.longitude]
            this.$refs.map.setCenter(this.center)
            this.zoom = 13
            this.$refs.map.setZoom(this.zoom)
        },

        handleMarkerClick(evt) {
            this.center = [evt.latlng.lat, evt.latlng.lng]
            this.$refs.map.setCenter(this.center)
            this.zoom = 13
            this.$refs.map.setZoom(this.zoom)
        },

        redirect(equipmentId) {
            let URI = "equipment/" + equipmentId + "/details"
            window.open(URI)
        },

        saveMaintenance() {
            this.defaultMaintenance.equipmentId = this.equipmentIdSelected
            this.defaultMaintenance.authorId = this.userId
            this.addMaintenance()
            this.closeMaintenance()
        },

        addMaintenance() {
            fetch(`/api/equipment/${this.equipmentIdSelected}/maintenance`, { method: "POST", 'Content-Type': 'application/json', body: JSON.stringify(this.defaultMaintenance) })
        },

        closeMaintenance() {
            this.defaultMaintenance = ""
            this.maintenanceDialog = false
        },

        saveDocument() {
            let form_data = new FormData();
            form_data.set('name', this.defaultDocument.name)
            form_data.set('file', this.defaultDocument.file)
            fetch(`/api/equipment/${this.equipmentIdSelected}/document`, { method: 'POST', 'Content-Type': 'multipart/form-data', body: form_data })
            this.closeDocument()
        },

        closeDocument() {
            this.defaultDocument = "";
            this.documentDialog = false;
        },

        closeEquipment(){
            this.equipmentDialog = false
            this.$nextTick(() => {
                this.equipmentCompanyDetails = ""
                this.equipmentDetails =""
                this.equipments.refresh();
            })
        },

        editEquipment(){
            fetch(`/api/admin/equipment/${this.equipmentIdSelected}`, { method: 'PUT', 'Content-Type': 'application/json', body: JSON.stringify(this.equipmentDetails) })
            this.closeEquipment()
        },

        openDialog(index, equipmentId) {
            this.equipmentIdSelected = equipmentId;
            switch (index) {
                case 0:
                    this.maintenanceDialog = true
                    break;
                case 1:
                    this.documentDialog = true
                    break;
                case 2:
                    this.equipmentDetails = new LoadableData(`/api/equipment/${this.equipmentIdSelected}`).data;
                    this.equipmentDialog = true
                    break;

            }
        },
    },

    computed: {
        viewedEquipments() {
            if (!this.equipments.loaded) {
                return [];
            }
            return this.equipments.data.filter(equipment => {
                let result = false
                try {
                    for (let equipmentKey in equipment) {
                        if (equipment[equipmentKey]?.includes?.(this.searchQuery)) {
                            result = true;
                            break;
                        }
                    }
                } catch (err) {
                    console.error(err);
                } finally {
                    return result;
                }
            })
        }
    },


    mounted() {
        this.$nextTick(() => {
            this.viewedEquipments = this.equipments.data
            this.scrollerHeight = this.$refs.scrollableContainer.offsetHeight - this.$refs.searchRow.offsetHeight - 24
            this.companies = new LoadableData(`api/admin/company`).data;

        })
    }
});
</script>

<style scoped>
.v-expansion-panel-header__icon {
    align-self: flex-end;
    margin-right: 6px;
}

.v-expansion-panel-content__wrap {
    padding: 0;
}

.v-expansion-panel-header {
    padding: 0;
    padding-bottom: 16px;
    padding-top: 16px;
}

.mdi-chevron-down::before {
    color: #102338;
}

.v-expansion-panel v-expansion-panel--next-active {
    box-shadow: none !important;
}

.v-expansion-panel::before {
    box-shadow: none !important;
}
</style>

                                        </div>

                                    </v-expansion-panel-header>
                                    <v-expansion-panel-content>
                                        <equipment-tabs-map :equipment-id="item.id"></equipment-tabs-map>
                                    </v-expansion-panel-content>
                                </v-expansion-panel>
                            </v-expansion-panels>
                        </v-col>
                    </v-row>
                </v-container>
            </v-col>
            <v-col v-else class="col-4">
                <v-container ref="scrollableContainer" fluid style="height:95vh" class="d-flex flex-column">
                    <div>
                        <v-row no-gutters class="row d-flex flex-row align-center justify-space-between">
                            <div>
                                <span color="primary" class="title">Filters</span>
                            </div>
                            <div>
                                <v-btn height="56px" class="ml-6 px-4" color="primary" @click="flag = !flag">
                                    <span class="material-symbols-outlined mr-2 ">
                                        edit
                                    </span>
                                    EQUIPMENTS
                                </v-btn>
                            </div>
                        </v-row>
                    </div>
                    <div class="mt-10">
                        <v-select :items="companies" label="Company" v-model="companyFilter" item-text="name"
                            item-value="companyId" return-object filled multiple style="border: none;"></v-select>
                        <v-select label="Type" filled multiple @change=""></v-select>
                        <v-select label="Location" filled multiple @change=""></v-select>
                        <v-select label="Status" filled multiple @change=""></v-select>

                    </div>
                    <div class=" mb-12   mt-auto">
                        <v-btn width="100%" class=" mt-5 py-8 px-4" elevation="0" color="primary" @click="flag = !flag">
                            FILTER
                        </v-btn>

                        <v-btn width="100%" class=" mt-5 py-8 px-4" elevation="0" color="gray"
                            style="border:1px solid #102338;" @click="flag = !flag">
                            CLEAR FILTERS
                        </v-btn>
                    </div>
                </v-container>
            </v-col>
            <v-col class="col-8 pa-0">
                <l-map style="max-height: 100%; width: 100%; z-index: 0;" ref="map" v-bind:zoom="zoom"
                    v-bind:center="center">
                    <l-tile-layer :url="url" :attribution="attribution"></l-tile-layer>
                    <equipment-marker v-if="equipments.loaded" v-for="equipment in equipments.data"
                        :equipment-id="equipment.id" @click="handleMarkerClick">
                    </equipment-marker>
                </l-map>
            </v-col>
        </v-row>
        <div class="text-center">
            <v-dialog v-model="maintenanceDialog" width="40%" persistent>
                <v-card>
                    <v-card-title>
                        <span class="text-h5">Add new Maintenance Event</span>
                    </v-card-title>

                    <v-card-text class="mt-4">

                        <v-container class="pb-0">
                            <v-row>
                                <v-col cols="12" sm="6" md="6" class="pa-0 pr-2">
                                    <v-text-field v-model="defaultMaintenance.code" outlined label="Event Code"
                                        required>
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pa-0 pl-2">
                                    <v-text-field v-model="defaultMaintenance.details" outlined label="Event Details"
                                        required>
                                    </v-text-field>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="primary darken-1" text @click="closeMaintenance">
                            Cancel
                        </v-btn>
                        <v-btn color="white darken-1" text @click="saveMaintenance" style="background-color:#102338;">
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <v-dialog v-model="documentDialog" width="40%" persistent>
                <v-card>
                    <v-card-title class="">
                        <span class="text-h5">New Document</span>
                    </v-card-title>

                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col cols="12" sm="6" md="6">
                                    <v-text-field v-model="defaultDocument.name" label="Name" outlined>
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6">
                                    <v-file-input v-model="defaultDocument.file" label="File" outlined>
                                    </v-file-input>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="primary darken-1" text @click="closeDocument">
                            Cancel
                        </v-btn>
                        <v-btn color="white darken-1" style="background-color:#102338" text @click="saveDocument">
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>
            <v-dialog v-model="equipmentDialog" width="40%" persistent>
                <v-card>
                    <v-card-title>
                        <span class="text-h5">Edit Equipment</span>
                    </v-card-title>

                    <v-card-text class="mt-2">
                        <v-container class="px-0">
                            <v-row no-gutters>
                                <v-col cols="12" sm="6" md="6" class="pr-2">
                                    <v-text-field v-model="equipmentDetails.type" label="Type" outlined>
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pl-2">
                                    <v-text-field v-model="equipmentDetails.productionDate" outlined
                                        label="Production Date">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pr-2">
                                    <v-text-field v-model="equipmentDetails.licencePlate" outlined
                                        label="Licence Plate">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pl-2">
                                    <v-text-field v-model="equipmentDetails.registrationNumber" outlined
                                        label="Registration Number">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pr-2">
                                    <v-text-field v-model="equipmentDetails.licenceNumber" outlined
                                        label="Licence Number">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pl-2">
                                    <v-text-field v-model="equipmentDetails.manufacturer" outlined label="Manufacturer">
                                    </v-text-field>
                                </v-col>
                                <v-col cols="12" sm="6" md="6" class="pr-2">
                                    <v-text-field v-model="equipmentDetails.serialNumber" outlined
                                        label="Serial Number">
                                    </v-text-field>
                                </v-col>
                             

                            </v-row>
                        </v-container>
                    </v-card-text>

                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="primary darken-1" text @click="closeEquipment">
                            Cancel
                        </v-btn>
                        <v-btn color="white darken-1" text @click="editEquipment" style="background-color:#102338;">
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-dialog>



        </div>
    </app-layout>
</template>
<script>
Vue.component("home", {
    template: "#home",

    data() {
        return {
            pageName: "Home Page",
            url: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
            attribution: '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
            zoom: 8,
            center: [47.313220, -1.319482],
            equipments: new LoadableData("/api/equipment"),
            scrollerHeight: -1,
            searchQuery: "",
            flag: true,
            items: [
                { title: 'Add Maintenance Event', icon: "settings", path: "/equipment/" },
                { title: 'Add Document', icon: "description", path: "/equipment/" },
                { title: 'Edit', icon: "edit", path: "/equipment/" },
            ],
            defaultMaintenance: {
                "details": "",
                "equipmentId": "",
                "authorId": "",
                "code": ""
            },
            defaultDocument: {
                name: "",
                file: "",
            },
            equipmentDetails: {
                id: '',
                type: '',
                productionDate: '',
                companyId: '',
                licencePlate: '',
                registrationNumber: '',
                licenceNumber: '',
                manufacturer: '',
                serialNumber: '',
            },
            maintenanceDialog: false,
            documentDialog: false,
            equipmentDialog: false,
            equipmentIdSelected: "",
            userId: "",
            companies: [],
            companyFilter: "",
            scrolabeHeight: window.height / 22.222
        };
    },
    
    created() {
        this.userId = this.$javalin.state.userDetails.user_id;
        this.equipments.refresh();
        window.addEventListener("resize", this.myEventHandler);
    },

    destroyed() {
        window.removeEventListener("resize", this.myEventHandler);
    },

    methods: {

        myEventHandler(e) {
            this.scrolabeHeight = window.height / 22.222
        },

        getEquipmentDetailsLink(equipmentId) {
            return `/equipment/${equipmentId}/details`;
        },

        getEquipmentTitle(equipment) {
            let equipmentTitle = "";
            if (equipment.manufacturer) {
                equipmentTitle += `${equipment.manufacturer}`
            }
            if (equipment.type) {
                equipmentTitle += ` ${equipment.type}`
            }

            if (equipment.licencePlate) {
                equipmentTitle += ` (${equipment.licencePlate})`;
            }
            return equipmentTitle;
        },

        setCenter(equipmentId) {
            let equipment = new LoadableData(`/api/equipment/${equipmentId}`)
            this.center = [equipment.data.latitude, equipment.data.longitude]
            this.$refs.map.setCenter(this.center)
            this.zoom = 13
            this.$refs.map.setZoom(this.zoom)
        },

        handleMarkerClick(evt) {
            this.center = [evt.latlng.lat, evt.latlng.lng]
            this.$refs.map.setCenter(this.center)
            this.zoom = 13
            this.$refs.map.setZoom(this.zoom)
        },

        redirect(equipmentId) {
            let URI = "equipment/" + equipmentId + "/details"
            window.open(URI)
        },

        saveMaintenance() {
            this.defaultMaintenance.equipmentId = this.equipmentIdSelected
            this.defaultMaintenance.authorId = this.userId
            this.addMaintenance()
            this.closeMaintenance()
        },

        addMaintenance() {
            fetch(`/api/equipment/${this.equipmentIdSelected}/maintenance`, { method: "POST", 'Content-Type': 'application/json', body: JSON.stringify(this.defaultMaintenance) })
        },

        closeMaintenance() {
            this.defaultMaintenance = ""
            this.maintenanceDialog = false
        },

        saveDocument() {
            let form_data = new FormData();
            form_data.set('name', this.defaultDocument.name)
            form_data.set('file', this.defaultDocument.file)
            fetch(`/api/equipment/${this.equipmentIdSelected}/document`, { method: 'POST', 'Content-Type': 'multipart/form-data', body: form_data })
            this.closeDocument()
        },

        closeDocument() {
            this.defaultDocument = "";
            this.documentDialog = false;
        },
       
        closeEquipment(){
            this.equipmentDialog = false
            this.$nextTick(() => {
                this.equipmentCompanyDetails = ""
                this.equipmentDetails ="" 
                this.equipments.refresh();
            })
        },

        editEquipment(){
            fetch(`/api/admin/equipment/${this.equipmentIdSelected}`, { method: 'PUT', 'Content-Type': 'application/json', body: JSON.stringify(this.equipmentDetails) })
            this.closeEquipment()
        },

        openDialog(index, equipmentId) {
            this.equipmentIdSelected = equipmentId;
            switch (index) {
                case 0:
                    this.maintenanceDialog = true
                    break;
                case 1:
                    this.documentDialog = true
                    break;
                case 2:
                    this.equipmentDetails = new LoadableData(`/api/equipment/${this.equipmentIdSelected}`).data;
                    this.equipmentDialog = true
                    break;

            }
        },
    },

    computed: {
        viewedEquipments() {
            if (!this.equipments.loaded) {
                return [];
            }
            return this.equipments.data.filter(equipment => {
                let result = false
                try {
                    for (let equipmentKey in equipment) {
                        if (equipment[equipmentKey]?.includes?.(this.searchQuery)) {
                            result = true;
                            break;
                        }
                    }
                } catch (err) {
                    console.error(err);
                } finally {
                    return result;
                }
            })
        }
    },


    mounted() {
        this.$nextTick(() => {
            this.viewedEquipments = this.equipments.data
            this.scrollerHeight = this.$refs.scrollableContainer.offsetHeight - this.$refs.searchRow.offsetHeight - 24
            this.companies = new LoadableData(`api/admin/company`).data;

        })
    }
});
</script>

<style scoped>
.v-expansion-panel-header__icon {
    align-self: flex-end;
    margin-right: 6px;
}

.v-expansion-panel-content__wrap {
    padding: 0;
}

.v-expansion-panel-header {
    padding: 0;
    padding-bottom: 16px;
    padding-top: 16px;
}

.mdi-chevron-down::before {
    color: #102338;
}

.v-expansion-panel v-expansion-panel--next-active {
    box-shadow: none !important;
}

.v-expansion-panel::before {
    box-shadow: none !important;
}
</style>
