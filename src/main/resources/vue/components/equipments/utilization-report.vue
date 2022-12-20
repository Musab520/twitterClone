<template id="utilization-report">
    <v-dialog @click:outside="closeDialog" :value="dialog" outlined width="762px" height="700px">
        <v-card full-width class="mx-auto px-4 py-4" outlined>
            <v-card-title>Utilization Report</v-card-title>
            <div v-if="chartData.loading">Loading Chart...</div>
            <chart-js v-if="chartData.loaded" v-bind:chart-datasets="chartDataSet"
                v-bind:chart-labels="chartLabels" chart-type="line" v-bind:chart-options="chartOptions">
            </chart-js>
            <div v-else>
                <p class="text-center">
                    Not Enough Data
                </p>
            </div>
        </v-card>
    </v-dialog>
</template>
<script>
Vue.component("utilization-report", {
    template: "#utilization-report",

    props: {
        id: {
            type: String,
            required: true
        },

        dialog: {
            type: Boolean,
            required: true
        }
    },

    data() {
        return {
            chartData: null
        };
    },

    created() {
        this.chartData = new LoadableData(`/api/equipments/${this.id}/utilization-report`);
    },

    computed: {
        chartLabels() {
            return this.chartData?.data?.dates.map((item) => {
                let date = new Date(item);
                let shortMonthName = new Intl.DateTimeFormat("en-US", { month: "short" }).format;
                return `${date.getDate()} ${shortMonthName(date)}`;
            });
        },

        chartDataSet() {
            let utilization = this.getAvg(this.chartData?.data?.values);
            
            let borderColor = '';
            let backgroundColor = '';
            if (utilization * 3 < 1) {
                borderColor = "hsla(0, 100%, 66%, 1)";
                backgroundColor = "hsla(0, 100%, 66%, 0.2)";
            } else if (utilization * 3 < 2) {
                borderColor = "hsla(37, 95%, 53%, 1)";
                backgroundColor = "hsla(37, 95%, 53%, 0.2)";
            } else {
                borderColor = "hsla(122, 39%, 49%, 1)";
                backgroundColor = "hsla(122, 39%, 49%, 0.2)";
            }
            
            return [
                {
                    label: 'Average Utilization',
                    borderColor: borderColor,
                    backgroundColor: backgroundColor,
                    data: this.chartData?.data?.values
                }
            ];
        },

        chartOptions() {
            return {
                scales: {
                    yAxes: [{
                        ticks: {
                            min: 0.0,
                            max: 1.0,
                            callback: function(value, index, values) {
                                return `${Math.round(value * 100)} %`;
                            }
                        }
                    }]
                }
            }
        }
    },

    methods: {
        getAvg(arr) {
            if (!arr || arr.length == 0)
                return 0;
            
            return arr.reduce((acc, c) => acc + c, 0) / arr.length;
        },

        closeDialog() {
            this.$emit("update:dialog", false);
        }
    }

});
</script>
<style>

</style>