<template id="topbar" xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-app-bar color="#FFFFFF" dense flat absolute app elevation="0">
        <template v-if="hasAppbarExtension()" v-slot:extension>
            <slot name="appbar-extension"></slot>
        </template>
        <v-app-bar-nav-icon color="#000000" @click.stop="changeDrawer()"></v-app-bar-nav-icon>

        <v-toolbar-title class="font-weight-medium" style="color:#00000 ;padding-left: 32px;"> {{name}}
        </v-toolbar-title>

        <v-spacer></v-spacer>
        <v-icon>mdi-bell</v-icon>

    </v-app-bar>
</template>

<script>
Vue.component("topbar", {
    template: "#topbar",
    props:["name"],
    data: () => ({
        user: {},
        drawer: false,
        value: 'recent',
        menus: [
            { title: "Profile", icon: "mdi-account" },
            { title: "Change Password", icon: "mdi-key" },
            { title: "Setting", icon: "mdi-cog" },
            { title: "Logout", icon: "mdi-logout" }
        ],

        items: [
            {
                avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
                title: "Brunch this weekend?",
                subtitle: `<span class="text--primary">Ali Connors</span> &mdash; I'll be in your neighborhood doing errands this weekend. Do you want to hang out?`,
            },
            { divider: true, inset: true },
            {
                avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
                title: 'Summer BBQ <span class="grey--text text--lighten-1">4</span>',
                subtitle: `<span class="text--primary">to Alex, Scott, Jennifer</span> &mdash; Wish I could come, but I'm out of town this weekend.`,
            },
            { divider: true, inset: true },
            {
                avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
                title: "Oui oui",
                subtitle:
                    '<span class="text--primary">Sandra Adams</span> &mdash; Do you have Paris recommendations? Have you ever been?',
            }
        ]
    }),

    methods: {
        changeDrawer() {
            this.$emit('change')
        },

        hasAppbarExtension() {
            return !!this.$slots["appbar-extension"];
        }
    }
});
</script>
