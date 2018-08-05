function selectRemoveAll(fromSelectName, toSelectName) {
	var fromSelect = form[fromSelectName];
	var toSelect = form[toSelectName];
	while (fromSelect.options.length > 0) {
		toSelect.options.add(new Option(fromSelect.options[0].text, fromSelect.options[0].value));
		fromSelect.options[0] = null;
	}
}

function selectRemoveAnyOne(fromSelectName, toSelectName) {
	var fromSelect = form[fromSelectName];
	var toSelect = form[toSelectName];
	for (var i = 0; i < fromSelect.options.length;) {
		if (fromSelect.options[i].selected) {
			toSelect.options.add(new Option(fromSelect.options[i].text, fromSelect.options[i].value));
			fromSelect.options[i] = null;
		} else {
			i++;
		}
	}
}
