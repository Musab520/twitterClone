<template id="project-list-dialog">
    <v-row justify="center">
        <v-dialog v-model="dialog" max-width="520px" scrollable="false" @click:outside="closeDialog"
                  @keydown="closeDialog">
            <v-card class="full-width">
                <v-card-title class="pa-4">
                    {{ $trans('equipmentsPage.projectListDialog.selectProject') }}
                </v-card-title>
                <v-divider></v-divider>
                <v-card-text width="100%" class="pa-4">
                    <v-container width="100%" class="ma-0 pa-0">
                        <v-row class="text ma-0">
                            {{ $trans('equipmentsPage.projectListDialog.needAnotherProjectList') }}&nbsp;
                            <a class="clickable-text" @click="projectListCreationDialog = true">
                                {{ $trans('equipmentsPage.projectListDialog.createNewOne') }}
                            </a>
                        </v-row>
                        <v-row width="100%" class="mx-0 mb-0 mt-2">
                            <v-sheet outlined width="100%" class="pa-0">
                                <v-data-table width="100%" :fixed-header="true"
                                              :height="`${(Math.min(projectLists.length, 8) + 1) * 48}px`" :headers="headers"
                                              :items="sortedProjectLists" hide-default-footer>
                                    <template v-slot:item.projectList="{ item }">
                                        <span>{{ item.name }}</span>
                                        <span class="counter mr-2">{{ item.equipmentCount }}</span>
                                    </template>
                                    <template v-slot:item.fromDate="{ item }">
                                        <span>{{ getDateString(item.fromDate) }}</span>
                                    </template>
                                    <template v-slot:item.toDate="{ item }">
                                        <span>{{ getDateString(item.toDate)}}</span>
                                    </template>
                                    <template v-slot:item.addOrDelete="{ item }">
                                        <v-icon color="primary" v-if="!item.added && item.eligible"
                                                @click="addEquipmentToProjectList(item.id)">
                                            mdi-plus-circle-outline
                                        </v-icon>
                                        <v-icon color="#FF5252" v-else-if="item.eligible"
                                                @click="removeEquipmentFromProjectList(item.id)">
                                            mdi-minus-circle-outline
                                        </v-icon>
                                        <v-icon v-else>
                                            mdi-plus-circle-outline
                                        </v-icon>
                                    </template>
                                </v-data-table>
                            </v-sheet>
                        </v-row>
                    </v-container>
                </v-card-text>
                <v-divider></v-divider>
                <v-card-action class="pa-4 d-flex justify-end">
                    <v-btn text color="primary" class="uppercase" @click="closeDialog">
                        {{ $trans('equipmentsPage.projectListDialog.close') }}
                    </v-btn>
                </v-card-action>
            </v-card>
        </v-dialog>
        <project-list-creation-dialog v-if="projectListCreationDialog" @close="handleProjectCreationClosing">
        </project-list-creation-dialog>
    </v-row>
</template>
<script>
    Vue.component("project-list-dialog", {
        template: "#project-list-dialog",

        props: {
            id: {
                type: String,
                required: true
            }
        },

        data() {
            return {
                dialog: true,
                headers: [
                    {
                        text: this.$trans('equipmentsPage.projectListDialog.projectsListTable.projectListName'),
                        value: 'projectList',
                        sortable: false
                    },
                    {
                        text: this.$trans('equipmentsPage.projectListDialog.projectsListTable.projectListFrom'),
                        value: 'fromDate',
                        sortable: false
                    },
                    {
                        text: this.$trans('equipmentsPage.projectListDialog.projectsListTable.projectListTo'),
                        value: 'toDate',
                        sortable: false
                    },
                    {
                        text: '',
                        value: 'addOrDelete',
                        sortable: false
                    }
                ],
                projectLists: [],
                projectListCreationDialog: false
            }
        },

        computed: {
            sortedProjectLists() {
                return this.projectLists.slice().sort((a, b) => a.eligible < b.eligible);
            }
        },

        created() {
            fetch(`/api/project-list/for-equipment/${this.id}`)
                    .then(res => res.json())
                    .then(res => {
                        this.projectLists = res;
                        this.projectListCreationDialog = (res.length === 0);
                    });
        },

        methods: {
            getDateString(date) {
                return new Date(date).toLocaleDateString();
            },
            handleProjectCreationClosing() {
                this.projectListCreationDialog = false;
                fetch(`/api/project-list/for-equipment/${this.id}`)
                        .then(res => res.json())
                        .then(res => {
                            this.projectLists = res;
                            if (res.length === 0)
                                this.closeDialog();
                        });
            },
            closeDialog() {
                this.dialog = false;
                this.$emit('close');
            },

            addEquipmentToProjectList(projectListId) {
                fetch(`/api/project-list/${projectListId}/equipment`, {
                    method: 'POST',
                    body: JSON.stringify({id: this.id})
                }).then(() => {
                    this.projectLists = this.projectLists.map(projectList => {
                        if (projectList.id === projectListId) {
                            projectList.added = true;
                        }
                        return projectList;
                    });
                });
            },

            removeEquipmentFromProjectList(projectListId) {
                fetch(`/api/project-list/${projectListId}/equipment/${this.id}`, {
                    method: 'DELETE'
                }).then(() => {
                    this.projectLists = this.projectLists.map(projectList => {
                        if (projectList.id === projectListId) {
                            projectList.added = false;
                        }
                        return projectList;
                    });
                });
            }
        }
    });
</script>
<style scoped>
    .text {
        font-family: 'Roboto';
        font-style: normal;
        font-weight: 400;
        font-size: 16px;
        line-height: 24px;
    }

    .clickable-text {
        font-weight: bold;
        text-decoration: underline;
    }

    .counter {
        font-weight: 700;
        font-size: 12px;
        line-height: 16px;
        background-color: rgba(16, 35, 56, 0.05);
        padding: 2px 6px;
        border-radius: 4px;
    }
</style>