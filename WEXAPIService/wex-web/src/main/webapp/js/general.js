function setTickerClass(diff) {
	console.log('passt');
	if (diff == 0) {
		return '';
	}
}

function updateTicker(someData) {
	var floatPrecision=3;
	var currentBuy=parseFloat(someData['currentTicker'].buy).toFixed(3);
	var currentSell=parseFloat(someData['currentTicker'].sell).toFixed(3)
	var currentLast=parseFloat(someData['currentTicker'].last).toFixed(3)
	var ticker = someData['currentTicker'].symbol + 'Grid\\:overviewGrid';
	var buyIncrease = someData['currentTicker'].buy - someData['lastTicker'].buy;
	var sellIncrease = someData['currentTicker'].sell - someData['lastTicker'].sell;
	var lastIncrease = someData['currentTicker'].last - someData['lastTicker'].last;
	var fee=(someData['currentTicker'].last*0.004).toFixed(floatPrecision);
	var bsDiff= (someData['currentTicker'].buy -someData['currentTicker'].sell).toFixed(floatPrecision);
	
	
	$('#' + ticker + ' > tbody > tr > .buyColumn > .ui-outputlabel').text(currentBuy);
	$('#' + ticker + ' > tbody > tr > .sellColumn > .ui-outputlabel').text(currentSell);
	$('#' + ticker + ' > tbody > tr > .lastColumn > .ui-outputlabel').text(currentLast);
	$('#' + ticker + ' > tbody > tr > .fpuColumn > .ui-outputlabel').text(fee);
	$('#' + ticker + ' > tbody > tr > .bsDiffColumn > .ui-outputlabel').text(bsDiff);
	
	if (bsDiff>fee) {
		changeClasses($('#' + ticker + ' > tbody > tr > .bsDiffColumn'),'greenStyle','normalStyle');
	}
	if (bsDiff<=fee) {
		changeClasses($('#' + ticker + ' > tbody > tr > .bsDiffColumn'),'normalStyle','greenStyle');
	}
	
	if (lastIncrease > 0) {
		changeClasses($('#' + ticker + ' > tbody > tr > .lastColumn'),'greenStyle','redStyle normalStyle');
	}
	if (lastIncrease < 0){
		changeClasses($('#' + ticker + ' > tbody > tr > .lastColumn'),'redStyle','greenStyle normalStyle');
	}
}

function changeClasses(object, classToAdd, classToRemove){
	object.removeClass(classToRemove);
	object.addClass(classToAdd);
}