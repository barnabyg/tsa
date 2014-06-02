// Includes JavaScript from javascript-array.com
// Copyright 2006-2007 javascript-array.com
//

var timeout = 500;
var closetimer = 0;
var ddmenuitem = 0;

// open hidden layer
function mopen(id) {
	// cancel close timer
	mcancelclosetime();

	// close old layer
	if (ddmenuitem) {
		ddmenuitem.style.visibility = 'hidden';
	}

	// get new layer and show it
	ddmenuitem = document.getElementById(id);
	ddmenuitem.style.visibility = 'visible';

}
// close showed layer
function mclose() {
	if (ddmenuitem) {
		ddmenuitem.style.visibility = 'hidden';
	}
}

// go close timer
function mclosetime() {
	closetimer = window.setTimeout(mclose, timeout);
}

// cancel close timer
function mcancelclosetime() {
	if (closetimer) {
		window.clearTimeout(closetimer);
		closetimer = null;
	}
}

// close layer when click-out
document.onclick = mclose;

function SetButtonStatus(sender, target) {
	if (sender.value.length >= 12) {
		document.getElementById(target).disabled = false;
	} else {
		document.getElementById(target).disabled = true;
	}
}

function setDatasetUploadSubmit() {

	if (document.getElementById("names0").value.length > 0) {

		document.getElementById("datasetUploadSubmit").disabled = false;

	} else {

		document.getElementById("datasetUploadSubmit").disabled = true;

	}
}

function setAnalysisSubmit() {

	if ((document.getElementById("strategyName").value.length > 0) &&
	   (document.getElementById("datasetName").value.length > 0)) {

		document.getElementById("analysisSubmit").disabled = false;

	} else {

		document.getElementById("analysisSubmit").disabled = true;

	}
}

function setStrategyUploadSubmit() {

	if (document.getElementById("names0").value.length > 0) {

		document.getElementById("strategyUploadSubmit").disabled = false;

	} else {

		document.getElementById("strategyUploadSubmit").disabled = true;

	}
}

function setVisualiseSubmit() {

	if (document.getElementById("runId").value.length > 0) {

		document.getElementById("visualiseSubmit").disabled = false;

	} else {

		document.getElementById("visualiseSubmit").disabled = true;

	}
}

function addRow(tableID) {

    var table = document.getElementById(tableID);

    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var cell1 = row.insertCell(0);
    var element1 = document.createElement("input");
    element1.type = "checkbox";
    element1.name="chkbox[]";
    cell1.appendChild(element1);

    var cell2 = row.insertCell(1);
    cell2.innerHTML = "Rule " + (rowCount + 1);

    var cell3 = row.insertCell(2);
    cell3.innerHTML = table.rows[0].cells[2].innerHTML;
    cell3.childNodes[0].id = "rules" + (rowCount);
    cell3.childNodes[0].name = "rules[" + rowCount + "]";
}

function deleteRow(tableID) {
    try {
        var table = document.getElementById(tableID);
        var rowCount = table.rows.length;

        for(var i=0; i<rowCount; i++) {
            var row = table.rows[i];
            var chkbox = row.cells[0].childNodes[0];
            if(null != chkbox && true == chkbox.checked) {
                table.deleteRow(i);
                rowCount--;
                i--;
            }
        }
    } catch(e) {
        alert(e);
    }
}

function loadChart() {
	var runId = document.getElementById("runId");
	var chartType = document.getElementById("chartType");

	var runIdStr = runId.options[runId.selectedIndex].text;
	var chartTypeStr = chartType.options[chartType.selectedIndex].text;

	var uri = "chart.go?runId=" + runIdStr + "&chartType=" + chartTypeStr;

	document.getElementById("myframe").src=uri;
}