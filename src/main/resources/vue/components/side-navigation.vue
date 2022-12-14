<template id="side-navigation">
    <v-col
        cols="12"
        md="2"
        :class="{'pr-3': $vuetify.breakpoint.mdAndUp,'flex-column': $vuetify.breakpoint.mdAndDown}">
        <v-card class="mx-auto" outlined>
            <v-list flat class="py-0">
                <v-list-item-group v-model="selectedItemIndex" color="primary">
                    <v-list-item v-for="(item, i) in items" :key="i" @click="redirectTo([item.path].flat()[0], i)"
                                 :active-class="$isRtl() ? 'active-navigation-rtl': 'active-navigation'"
                                 :class="activate(item, i)">
                        <v-list-item-icon :class="{'mr-5': !$isRtl(), 'ml-5': $isRtl()}">
                            <slot name="icon" v-if="slotExists('icon')" :selected-item="item"></slot>
                            <v-icon v-else v-text="item.icon"></v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                            <v-list-item-title v-text="item.text"></v-list-item-title>
                        </v-list-item-content>
                    </v-list-item>
                </v-list-item-group>
            </v-list>
        </v-card>
    </v-col>
</template>
<script>
    Vue.component("side-navigation", {
        template: "#side-navigation",
        props: {
            menuLinks: {
                type: Object,
                required: true
            }
        },
        data() {
            return {
                selectedItemIndex: null,
                selectedItem: null,
                items: this.menuLinks
            }
        },
        created() {
            let idx = this.items.findIndex(item => [item.path].flat().map(encodeURI).includes(window.location.pathname));
            if (idx !== -1) {
                this.selectedItemIndex = idx;
                this.selectedItem = this.items[idx];
            } else {
                this.selectedItemIndex = 0;
                this.selectedItem = this.items[0];
            }
        },
        methods: {
            redirectTo(path, i) {
                window.location.assign(path);
            },
            activate(item, index) {
                if (window.location.pathname === encodeURI(item.path)) {
                    this.selectedItemIndex = index
                    this.selectedItem = item
                    return this.$isRtl() ? 'active-navigation-rtl' : 'active-navigation'
                }
                return this.$isRtl() ? 'inactive-navigation-rtl' : 'inactive-navigation'
            },
            slotExists(slotName) {
                return !!this.$slots[slotName] || !!this.$scopedSlots[slotName]
            }
        }
    });
</script>

<style>
    .remove-border-right {
        border-right: none !important;
    }

    .active-navigation {
        border-left: 6px solid #102338 !important;
        background-color: rgba(16, 35, 56, 0.05);
    }

    .active-navigation-rtl {
        border-right: 6px solid #102338 !important;
        background-color: rgba(16, 35, 56, 0.05);
    }

    .inactive-navigation {
        border-left: 6px solid transparent;
    }

    .inactive-navigation-rtl {
        border-right: 6px solid transparent;
    }
</style>