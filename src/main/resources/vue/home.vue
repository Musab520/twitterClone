<template id="home">
    <app-layout>
        <v-overlay absolute class="main-content" color="#000000" opacity=".5">
            <v-container>
                <h1 class="main-title">
                    {{ $trans('homepage.mainTitle') }}
                </h1>
                <p class="text-uppercase description"
                   :class="{'description-large-font': $isRtl()}">
                    {{ $trans('homepage.subTitle') }}
                    <span class="secondary--text">
                        {{ $trans('homepage.subTitleHighlight') }}
                    </span>
                </p>
                <v-row class="pt-16 mt-md-2 mt-lg-16 search-component">
                    <v-text-field
                        :label="$trans('homepage.searchInputPlaceholder')"
                        light
                        solo
                        tile
                        class="rounded search-component--input"
                        :class="{'rounded-r-0': !$isRtl(), 'rounded-l-0': $isRtl()}"
                        v-model="searchQuery"
                        @keydown.enter="searchEquipments"
                        ></v-text-field>
                    <v-btn
                        height="48px"
                        tile
                        class="primary white--text rounded search-component--button"
                        :class="{'rounded-l-0': !$isRtl(), 'rounded-r-0': $isRtl()}"
                        @click="searchEquipments">
                        <v-icon
                            right
                            dark
                            class="me-2 d-none d-sm-flex">
                            mdi-magnify
                        </v-icon>
                        {{ $trans('homepage.searchButton') }}
                    </v-btn>
                </v-row>
                <h3 class="white--text d-flex justify-center">
                    {{ $trans('homepage.mostPopular') }}
                </h3>
                <v-row class="mt-4 d-flex justify-center popular-item--list">
                    <v-chip v-for="item in mostPopular"
                        outlined
                        link
                        class="popular-item--chip"
                        @click="searchByEquipmentType(item.route)">
                        {{ $trans(item.title) }}
                    </v-chip>
                </v-row>

                <v-dialog v-model="dialogCompanyRegistration" width="70vw" persistent>
                    <v-card>
                        <v-card-title>
                            <span class="text-h5">Create Profile</span>
                        </v-card-title>
                        <v-form 
                            ref="form"
                            v-model="valid"
                            @submit.prevent="createCompany"
                            >

                            <v-card-text class="pb-0">
                                <v-container class="px-0 pb-0">
                                            <v-text-field  label="Business Name" required v-model="company.name" :rules="rules.name"
                                                           outlined>
                                            </v-text-field>
                                            <v-text-field  label="Phone Number" required v-model="company.mobile" :rules="rules.mobile" outlined>
                                            </v-text-field>
                                </v-container>
                            </v-card-text>
                            <v-card-actions>
                                <v-spacer class="d-sm-flex d-none"></v-spacer>
                                <v-btn type="submit" :disabled="changeStatus" class="ma-4 mb-2 dialog--button" color="primary" x-large>
                                    Create
                                </v-btn>
                            </v-card-actions>
                        </v-form>
                    </v-card>
                </v-dialog>

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
                searchQuery: '',
                company: {
                    name: "",
                    mobile: ""
                },
                rules: {
                    name: [v => !!v || 'Name is required', v => /^.{8,}/.test(v) || "the  name must contain at least 8 characters\n"],
                    mobile: [v => !!v || 'Mobile is required', v => /^[+()\d- ]{9,16}/.test(v) || "Mobile number must be formatted"],
                },
                mostPopular:[
                    {
                        route:'Caterpiller',
                        title:"homepage.caterpillerCategory"
                    },
                    {
                        route:'Backhoe',
                        title:"homepage.backhoeCategory"
                    },
                    {
                        route:'JCB',
                        title:"homepage.jcbCategory"
                    },
                    {
                        route:'Truck',
                        title:"homepage.truckCategory"
                    },
                    {
                        route:'Bulldozer',
                        title:"homepage.bulldozerCategory"
                    },
                ]

            }
        },
        created() {
            let comapny_Id = this.$javalin.state.userDetails.companyId;
            if (!comapny_Id || comapny_Id === "0") {
                this.dialogCompanyRegistration = true;
            } else {
                this.dialogCompanyRegistration = false;
            }

        },
        computed: {
            changeStatus() {
                if (this.company.name && /^.{8,}/.test(this.company.name) &&
                        this.company.mobile && /^[+()\d- ]{9,16}/.test(this.company.mobile)
                        ) {
                    this.formValidate = true
                } else
                    this.formValidate = false

                return !this.formValidate;
            },

        },

        methods: {
            colorBinding(attr) {
                if (attr) {
                    return 'black'
                } else
                    return 'blue'

            },
            searchByEquipmentType(type) {
                window.location.href = `/equipments?type=${type}`
            },
            searchEquipments() {
                window.location.href = `/equipments?searchTerm=${this.searchQuery}`
            },
            createCompany() {
                fetch('/api/user/company', {method: 'POST', 'Content-Type': 'application/json', body: JSON.stringify(this.company)})
                        .then(res => {
                            this.dialogCompanyRegistration = false;
                            window.location = `/my-company/my-equipments`
                        })
            },
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



