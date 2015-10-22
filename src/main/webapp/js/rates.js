//
// Display the rates as a chart
//

function rmBuildChart(chartId, chartLimit, history) {
    d3.json("json/rates?limit=" + chartLimit, function(jsonData) {
        var chart;
        var data;

        nv.addGraph(function() {
            chart = nv.models.lineChart()
                .options({
                    transitionDuration: 300,
                    useInteractiveGuideline: true
                })
            ;

            chart.xAxis
                .axisLabel("Date")
                    .tickFormat(function(d) {
                      return d3.time.format('%x')(new Date(d))
                })
            ;

            chart.yAxis
                .axisLabel('Rate')
                .tickFormat(function(d) {
                    if (d == null) {
                        return 'N/A';
                    }
                    return d3.format(',.4f')(d);
                })
            ;

            d3.select(chartId)
                .append('svg')
                .datum(parseSeries(jsonData))
                .call(chart);

            nv.utils.windowResize(chart.update);

            return chart;
        });
    });

    function parseSeries(jsonData) {
        var buyRates = [],
            sellRates = [];

        jsonData.rates.reverse().forEach(function(rate) {
            buyRates.push({x: rate.parsedDate, y: rate.buyRate});
            sellRates.push({x: rate.parsedDate, y: rate.sellRate});
        });

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
    }

};
