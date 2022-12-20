<template id="request-for-quotation-layout">
    <app-layout>
        <v-container fluid class="px-0">
            <v-row class="row px-0 mx-0 mt-0">
              <v-col cols="12" md="12" lg="2">
                <side-navigation-new :menu-links="menuLinks">
                  <template v-slot:icon="props">
                        <span v-if="props.selectedItem.component === 'request-for-quotation-list'">
                            <svg width="25" height="24" viewBox="0 0 25 24" fill="currentColor"
                                 xmlns="http://www.w3.org/2000/svg">
                                <g clip-path="url(#clip0_2015_5374)">
                                    <path
                                        d="M14.75 2H6.75C5.65 2 4.75 2.9 4.75 4V20C4.75 21.1 5.65 22 6.75 22H18.75C19.85 22 20.75 21.1 20.75 20V8L14.75 2ZM6.75 20V4H13.75V8H18.75V20H6.75ZM11.75 19H13.75V18H14.75C15.3 18 15.75 17.55 15.75 17V14C15.75 13.45 15.3 13 14.75 13H11.75V12H15.75V10H13.75V9H11.75V10H10.75C10.2 10 9.75 10.45 9.75 11V14C9.75 14.55 10.2 15 10.75 15H13.75V16H9.75V18H11.75V19Z"
                                        fill="currentColor"/>
                                </g>
                                <defs>
                                    <clipPath id="clip0_2015_5374">
                                        <rect width="24" height="24" fill="white" transform="translate(0.75)"/>
                                    </clipPath>
                                </defs>
                            </svg>
                        </span>
                    <span v-else>
                            <svg width="25" height="24" viewBox="0 0 25 24" fill="currentColor"
                                 xmlns="http://www.w3.org/2000/svg">
                                <g clip-path="url(#clip0_2015_5402)">
                                    <path
                                        d="M11.25 13V9C11.25 8.45 10.8 8 10.25 8H6.25V6H11.25V4H8.75V3H6.75V4H5.25C4.7 4 4.25 4.45 4.25 5V9C4.25 9.55 4.7 10 5.25 10H9.25V12H4.25V14H6.75V15H8.75V14H10.25C10.8 14 11.25 13.55 11.25 13Z"
                                        fill="currentColor"/>
                                    <path
                                        d="M19.8404 12.52L14.1804 18.17L11.3504 15.34L9.94043 16.76L14.1804 21L21.2504 13.93L19.8404 12.52Z"
                                        fill="currentColor"/>
                                </g>
                                <defs>
                                    <clipPath id="clip0_2015_5402">
                                        <rect width="24" height="24" fill="white" transform="translate(0.25)"/>
                                    </clipPath>
                                </defs>
                            </svg>
                        </span>
                  </template>
                </side-navigation-new>

              </v-col>
              <v-col cols="12" sm="12" md="12" lg="10" >
                <slot></slot>
              </v-col>
            </v-row>
        </v-container>
    </app-layout>
</template>
<script>
    Vue.component("request-for-quotation-layout", {
        template: "#request-for-quotation-layout",
        data() {
            return {
                menuLinks: []
            }
        },
        created() {
            this.menuLinks = [
                {
                    text: this.$trans('requestForQuotationNavigation.quotation'),
                    icon: '',
                    path: `/${this.$javalin.state.userDetails.companyId}/request-for-quotation-list`,
                    component: 'request-for-quotation-list',
                },
                {
                    text: this.$trans('requestForQuotationNavigation.bidding'),
                    icon: '',
                    path: '/bidding',
                    component: 'request-for-quotation-bidding-list'
                },
            ]
        },
        methods: {
            currentPathEquals(path) {
                return window.location.href.includes(path)
            }
        },
        computed: {}
    });
</script>