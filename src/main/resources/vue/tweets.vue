<template id="tweets">
  <app-layout>
    <v-container fill-height fluid class="d-flex align-baseline pa-0 overflow-hidden">
      <v-row dense no-gutters class="fill-height" style="width: inherit">
        <v-col md="6" lg="4" >
          <v-sheet class="fill-height equipments-list-remove-border">
            <div class="fill-height">

                <v-col v-for="tweet in tweets.data">
                  <equipment-list-card
                      :id="tweet.id"
                      :message="tweet.message"
                      :username="tweet.username"
                  >
                  </equipment-list-card>
                </v-col>
                <div class="py-16 d-flex flex-column align-center justify-center"
                     v-if="this.tweets.loaded && this.tweets.data.length === 0">
                  <img class="mx-auto" style="width:20%" src="/no_data.svg"/>
                  <p class="pt-4 body-2">
                    {{ $trans('misc.noResultsFound') }}
                  </p>
                </div>

            </div>
          </v-sheet>
        </v-col>
      </v-row>
    </v-container>
  </app-layout>
</template>
<script>
Vue.component("tweets", {
  template: "#tweets",
  data() {
    return {
      tweets: []
    }
  },
  created() {
    this.getTweets();
  }
  ,
  methods: {
    getTweets() {
      this.tweets = new LoadableData(`/api/tweets`)
    },
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