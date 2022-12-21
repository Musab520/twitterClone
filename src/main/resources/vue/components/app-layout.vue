<template id="app-layout">
  <v-app>
    <template>
      <div>

        <v-app-bar class="sticky-navbar" elevation="0" color="primary accent-4 mb-2" dense dark :height="navHeight">
          <v-app-bar-nav-icon class="d-flex d-md-none" @click="drawer = true"></v-app-bar-nav-icon>

          <v-toolbar-title class="ps-0 d-none d-md-flex" height="100%" style="flex:none;">
            <v-hover v-if="!$javalin?.state?.userDetails?.user_id" v-slot:default="{ hover }">
              <a href="/login" target="_blank" class="white--text text-decoration-none ms-4"
                :class="{ 'text-decoration-underline': hover }">
                {{ $trans('homepage.navigation.login') }}
              </a>
            </v-hover>
            <v-menu v-if="!user.loading && user.loaded" :close-on-content-click="false" rounded="0" offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="primary" height="100%" v-bind="attrs" v-on="on" tile depressed v-ripple="false"
                  class=" me-2 px-2white--text user-menu-button">
                  <img width="40" src="/user-placeholder.png" />
                  <p class="my-0 mx-2">{{ $trans('homepage.navigation.username') }}</p>
                  <v-icon color="white">mdi-chevron-down</v-icon>
                </v-btn>
              </template>
              <v-list color="primary" class="white--text">
                <v-list-item v-for="(route) in routesList" :key="route.href" class="hover-w-10">
                  <a :href=route.href style="width:100%;height:56px;"
                    class="white--text text-decoration-none d-flex align-center">
                    <v-icon color="white" class="me-3">{{ route.icon }}</v-icon>
                    {{ $trans(route.title) }}
                  </a>
                </v-list-item>

                <v-list-item class="hover-w-10">
                  <a :href=`/logout` style="width:100%;height:56px;"
                    class="red--text font-weight-bold text-decoration-none d-flex align-center">
                    <v-icon color="red" class="me-3">mdi-logout</v-icon>
                    {{ $trans('homepage.navigation.userMenu.logout') }}
                  </a>

                </v-list-item>

              </v-list>
            </v-menu>
          </v-toolbar-title>



          <v-tabs v-model="tab" color="secondary" @change="handleChange" class="d-none d-md-flex justify-center">
            <v-tab exact exact-path href="/">
              {{ $trans('homepage.navigation.homepage') }}
            </v-tab>
            <v-tab exact exact-path href="/tweets">
              {{ $trans('homepage.navigation.newsFeed') }}
            </v-tab>
            <v-tab exact exact-path href="/companies">
              {{ $trans('homepage.navigation.profile') }}
            </v-tab>
            <v-tab exact exact-path href="/request-for-quotations">
              {{ $trans('homepage.navigation.Search') }}
            </v-tab>
          </v-tabs>

          <div class="d-flex ms-auto" style="height:100%">
            <div class="me-4">
              <language-switcher-component />
            </div>
          </div>


        </v-app-bar>
      </div>

    </template>

    <v-navigation-drawer style="z-index: 9999;" v-model="drawer" absolute temporary>
      <v-list nav color="primary" class="white--text d-flex flex-column justify-space-between" height="100%">
        <div>
          <v-toolbar-title class="ps-0 py-4 border-bottom-white" height="100%" style="flex:none;">
            <v-hover v-if="!$javalin?.state?.userDetails?.user_id" v-slot:default="{ hover }">
              <a href="/login" target="_blank" class="white--text text-decoration-none ms-4"
                :class="{ 'text-decoration-underline': hover }">
                {{ $trans('homepage.navigation.login') }}
              </a>
            </v-hover>
            <v-menu v-if="!user.loading && user.loaded" :close-on-content-click="false" rounded="0" offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn color="primary" v-bind="attrs" v-on="on" tile depressed v-ripple="false"
                  class=" me-2 px-2white--text user-menu-button">
                  <img width="40" src="/user-placeholder.png" />
                  <p class="my-0 mx-2">{{ $trans('homepage.navigation.username') }}</p>
                </v-btn>
              </template>
            </v-menu>
          </v-toolbar-title>
          <v-list-item v-for="(route) in routesList" :key="route.href" class="hover-w-10">
            <a :href=route.href style="width:100%;height:56px;"
              class="white--text text-decoration-none d-flex align-center">
              <v-icon color="white" class="me-3">{{ route.icon }}</v-icon>
              {{ $trans(route.title) }}
            </a>
          </v-list-item>
        </div>

        <div>
          <v-divider color="white"></v-divider>
          <v-list-item class="hover-w-10">
            <a :href=`/tweets` style="width:100%;height:56px;"
               class="white--text text-decoration-none d-flex align-center">
              <v-icon color="white" class="me-3">mdi-tractor-variant</v-icon>
              {{ $trans('homepage.navigation.equipments') }}
            </a>
          </v-list-item>
          <v-list-item class="hover-w-10">
            <a :href=`/companies` style="width:100%;height:56px;"
               class="white--text text-decoration-none d-flex align-center">
              <v-icon color="white" class="me-3">mdi-domain</v-icon>
              {{ $trans('homepage.navigation.companies') }}
            </a>
          </v-list-item>
          <v-list-item class="hover-w-10">
            <a :href=`/request-for-quotations` style="width:100%;height:56px;"
               class="white--text text-decoration-none d-flex align-center">
              <v-icon color="white" class="me-3">mdi-cash-clock </v-icon>
              {{ $trans('homepage.navigation.rfqs') }}
            </a>
          </v-list-item>
          <v-divider color="white"></v-divider>

          <v-list-item class="hover-w-10 mt-2" style="flex:none" >
          <a :href=`/logout` style="width:100%;height:56px;"
            class="red--text font-weight-bold text-decoration-none d-flex align-center">
            <v-icon color="red" class="me-3">mdi-logout</v-icon>
            {{ $trans('homepage.navigation.userMenu.logout') }}
          </a>
        </v-list-item>
        </div>


      </v-list>
    </v-navigation-drawer>


    <v-main :style="'margin-top:'+navHeight" max-width="900px">
      <div class="d-flex justify-center align-baseline fill-height">
        <slot></slot>
      </div>
    </v-main>
  </v-app>
</template>

<script>
Vue.component("app-layout", {
  template: "#app-layout",
  data() {
    return {
      tab: null,
      user: null,
      drawer: false,
      group: null,
      navHeight: '56px',
      routesList: [
      ]
    };
  },
  created() {
    this.tab = sessionStorage.getItem('twitter-main-navigation-selected-tab');
    this.user = new LoadableData(`/api/users/${this.$javalin.state.userDetails.user_id}`);
  },
  methods: {
    handleChange(index) {
      sessionStorage.setItem('twitter-main-navigation-selected-tab', index);
    }
  }
});
</script>

<style scoped>
.sticky-navbar {
  position: fixed;
  top: 0;
  z-index: 1;
}

.v-tab {
  width: fit-content;
  padding-inline: 2rem;
}

.user-menu-button::before {
  background-color: transparent !important;
}

.profile-icon {
  color: white !important;
}

.theme--light.v-list-item:hover::before {
  opacity: 0.1;
  background-color: white;
}

.hover-w-10:hover {
  background-color: rgba(255, 255, 255, 0.05);
}
</style>