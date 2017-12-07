var chartMap = new Map(); //
var unitMap = new Map(); //
var tickUnitMap = new Map();
var timerMap = new Map();

function createChart(coin, chart, numUnits, tickUnit,stepSize) {
	var ctx = document.getElementById(chart).getContext('2d');
	var newChart = new Chart(ctx, {
		type : 'line',
		data : {
			datasets : [ {
				label : coin,
				lineTension : 0,
				pointStyle : "cricle",
				pointRadius:0,
				backgroundColor : "rgba(0, 0, 0, 0.0)",
				pointBorderColor : "white",
				borderColor : "white"
			} ]
		},
		options : {
			responsive : false,
			scales : {
				xAxes : [ {
					display : true,
					gridLines : {
						display : true,
						color : "black"
					},
					scaleLabel : {
						display : true,
						color : "whitesmoke"
					}
				} ],
				yAxes : [ {
					display : true,
					gridLines : {
						display : true,
						color : "black"
					},
					ticks : {
						stepSize: stepSize
					},
					scaleLabel : {
						display : true,
						color : "whitesmoke"
					}
				} ]
			}

		}
	});
	chartMap.set(chart, newChart);
	unitMap.set(chart, numUnits);
	tickUnitMap.set(chart, tickUnit);
	var timer = createEmptyDataTimer(chart, tickUnit);
	timerMap.set(chart, timer);
	initialFillChart(chart);

}

function createEmptyDataTimer(chart, tickUnit) {
	var currentChart = chartMap.get(chart);
	var numUnits = unitMap.get(chart);
	var timer = setInterval(
			function() {
				var currentLabel = currentChart.data.labels[currentChart.data.datasets[0].data.length - 1];
				if (currentLabel != null && currentLabel) {

					if (tickUnit === "second" && currentLabel.substring(5, 8) != new Date().getSeconds()
							|| tickUnit === "minute" && currentLabel.substring(3, 5) != new Date().getMinutes() ) {
						if (currentChart.data.datasets[0].data.length > 0
								&& currentChart.data.datasets[0].data.length < (numUnits + 1)) {
							currentChart.data.labels[currentChart.data.datasets[0].data.length] = getCurrentDate(tickUnit);
							currentChart.data.datasets[0].data
									.push(currentChart.data.datasets[0].data[currentChart.data.datasets[0].data.length - 1]);
						} else {
							currentChart.data.labels.splice(0, 1);
							currentChart.data.datasets[0].data.splice(0, 1);
							currentChart.data.labels
									.push(getCurrentDate(tickUnit));
							currentChart.data.datasets[0].data
									.push(currentChart.data.datasets[0].data[currentChart.data.datasets[0].data.length - 1]);
						}
						currentChart.update(1000,"false");
					}

				}
			}, 300);
	return timer;
}

function initialFillChart(chart) {
	var currentChart = chartMap.get(chart);
	var numUnits = unitMap.get(chart);
	for (var i = 0; i < numUnits; i++) {
		currentChart.data.labels.push('');
	}
	currentChart.update();
}

function updateChart(dataValue) {
	var chart = dataValue.substring(0, dataValue.indexOf('#'));
	var newData = dataValue.substring(dataValue.indexOf('#') + 1);
	var currentChart = chartMap.get(chart);
	var numUnits = unitMap.get(chart);
	var tickUnit = tickUnitMap.get(chart);
	var isUpdate = checkIsUpdate(currentChart, tickUnit);

	if (currentChart.data.datasets[0].data.length < (numUnits + 1)) {
		if (isUpdate) {
			currentChart.data.datasets[0].data.pop();
			currentChart.data.datasets[0].data.push(newData);
		} else {
			currentChart.data.labels[currentChart.data.datasets[0].data.length] = getCurrentDate(tickUnit);
			currentChart.data.datasets[0].data.push(newData);
		}
	} else {
		if (isUpdate) {
			currentChart.data.datasets[0].data.pop();
			currentChart.data.datasets[0].data.push(newData);
		} else {
			currentChart.data.labels.splice(0, 1);
			currentChart.data.labels.push(getCurrentDate(tickUnit));
			currentChart.data.datasets[0].data.splice(0, 1);
			currentChart.data.datasets[0].data.push(newData);
		}

	}
	checkTickLabel(currentChart, newData);
	currentChart.update();
}

function checkTickLabel(currentChart, dataValue) {

	if (currentChart.options.scales.yAxes[0].ticks.max == null) {
		currentChart.options.scales.yAxes[0].ticks.max = parseInt(dataValue)
				+ parseInt(parseInt(dataValue) / 10);
	}
	if (currentChart.options.scales.yAxes[0].ticks.min == null) {
		currentChart.options.scales.yAxes[0].ticks.min = parseInt(dataValue)
				- parseInt(parseInt(dataValue) / 10);
	}
	if (dataValue >= currentChart.options.scales.yAxes[0].ticks.max) {
		currentChart.options.scales.yAxes[0].ticks.max = parseInt(dataValue)
				+ parseInt(parseInt(dataValue) / 10);
	}

	if (dataValue <= currentChart.options.scales.yAxes[0].ticks.min) {
		currentChart.options.scales.yAxes[0].ticks.min = parseInt(dataValue)
				- parseInt(parseInt(dataValue) / 10);
	}
}

function checkIsUpdate(currentChart, tickUnit) {
	if (currentChart.data.datasets[0].data.length == 0)
		return false;

	var lastUnit;
	var currentUnit;
	if (tickUnit === 'minute') {
		lastUnit = currentChart.data.labels[currentChart.data.datasets[0].data.length - 1]
				.substring(3, 5);
		currentUnit = new Date().getMinutes();
	}
	if (tickUnit === 'second') {
		lastUnit = currentChart.data.labels[currentChart.data.datasets[0].data.length - 1]
				.substring(6, 8);
		currentUnit = new Date().getSeconds();
	}
	if (lastUnit == "" || lastUnit != currentUnit)
		return false;

	return true;
}

function getCurrentDate(tickUnit) {
	var currentdate = new Date()
	if (tickUnit == "hours")
		return currentDate.getHours();
	if (tickUnit == "minute")
		return currentdate.getHours() + ":" + currentdate.getMinutes();
	if (tickUnit == "second")
		return currentdate.getHours() + ":" + currentdate.getMinutes() + ":"
				+ currentdate.getSeconds();

}

function getMinues(data) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}

function getSeconds(data) {
    if (i < 10) {
        i = "0" + i;
    }
    return i;
}

