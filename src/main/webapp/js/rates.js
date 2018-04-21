var RateMonitor = {
    page: 0,
    limit: 500,
    chart: null,
    chartSelector: "#history",

    getData: function( callback ) {
        var self = this;
        d3.json("json/rates?limit=" + self.limit + "&page=" + self.page, callback);
    },

    getCurrent: function( callback ) {
        d3.json("json/current", callback);
    },

    buildChart: function() {
        var self = this;

        nv.addGraph(function() {
            var chart = nv.models.lineChart()
                .options({
                    transitionDuration: 300,
                    useInteractiveGuideline: true
                })
            ;

            chart.xAxis
                .axisLabel("Date")
                .tickFormat(function(d) { return d3.time.format('%x')(new Date(d)); })
            ;

            chart.yAxis
                .axisLabel('Rate')
                .tickFormat(d3.format(',.4f'))
            ;

            self.getData(function(jsonData) {
                d3.select(self.chartSelector)
                    .append('svg')
                    .datum(self.parseSeries(jsonData))
                    .call(self.chart);
            })

            nv.utils.windowResize(chart.update);

            self.chart = chart;

            return chart;
        });
    },

    previousPage: function() {
        var self = this;

        if (self.page > 0) {
            self.page--;
            self.update();
        }
    },

    nextPage: function() {
        var self = this;

        self.page++;
        self.update();
    },

    currentPage: function() {
        var self = this;

        self.page = 0;
        self.update();
    },

    update: function() {
        var self = this;

        if (self.chart) {
            self.getData(function(jsonData) {
                d3.select(self.chartSelector)
                    .datum(self.parseSeries(jsonData))
                    .call(self.chart);
            })
        }
    },

    parseSeries: function( jsonData ) {
        var buyRates = [], sellRates = [];

        if (jsonData) {
            jsonData.rates.reverse().forEach(function(rate) {
                buyRates.push({x: new Date(rate.parsedDate), y: rate.buyRate});
                sellRates.push({x: new Date(rate.parsedDate), y: rate.sellRate});
            });
        }

        return [
            {
                values: buyRates,
                key: "Buy Rate",
                color: "#ff7f0e",
            },
            {
                values: sellRates,
                key: "Sell Rate",
                color: "#2ca02c"
            }
        ];
    },

    testRates: function(inputField, outputField, rate) {
        function update() {
                var test_value = d3.select(inputField).property("value");
                var result = (test_value * rate).toFixed(2);
                d3.select(outputField).html(result);
        }
        d3.select(inputField).on("keyup", update);
        d3.select(inputField).on("change", update);
        update();
    }
};

