<template id="home">
  <app-layout>
    <v-overlay absolute class="main-content" color="#000000" opacity=".5">
      <v-container>
        <h1 class="main-title">
          {{ $trans('homepage.mainTitle') }}
        </h1>
        <v-row ref="postTweetRow" no-gutters>
          <v-text-field label="Post"  single-line outlined v-model="postQuery"></v-text-field>
          <v-btn height="56px" class="ml-6 px-4" color="primary" @click="postTweet">
                            <span class="material-symbols-outlined mr-2 ">
                                Post Tweet
                            </span>
          </v-btn>
        </v-row>
      </v-container>
    </v-overlay>
  </app-layout>
</template>
<script>

Vue.component("home", {

  template: "#home",
  data() {
    return{
      valid: true,
      formValidate: false,
      isDisabled: true,
      dialogCompanyRegistration: false,
      postQuery: '',
    }
  },
  created() {


  },
  computed: {

  },

  methods: {
    postTweet() {
      fetch(`/api/tweets`, { method: 'POST', 'Content-Type': 'application/json', body: JSON.stringify({"message": this.postQuery})})
    }
  }
});
</script>
<style scoped>
.main-content {
  background: url('/background.png') no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}

.main-title {
  font-size: 6rem;
  font-weight: 500;
  line-height: 6rem;
  font-family: "Roboto", sans-serif !important;
  letter-spacing: 18px;
}
@media screen and (max-width: 960px){
  .main-title
  {
    font-size: 4rem;
    letter-spacing: 5px;
    text-align: center;
  }
  .description
  {
    text-align: center;
  }
  .search-component
  {
    width: 90vw;
    margin:auto;
  }

  .dialog--button
  {
    width: -webkit-fill-available;
  }
}
.description {
  font-family: 'Roboto';
  font-style: normal;
  font-weight: 400;
  font-size: 26px;
  line-height: 32px;
  letter-spacing: 0.01em;
  color: #FFFFFF;
  text-shadow: 0 2px 4px rgb(0 0 0 / 25%);
}

.description-large-font {
  font-size: xx-large;
  text-align: center;
}

.popular-item--chip {
  color: white;
  border-color: rgba(255,255,255,0.5)!important;
  letter-spacing: 1.2px;
}
.popular-item--list
{
  gap: 8px;
}

</style>



