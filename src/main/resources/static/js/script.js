function calcSummaryPrice() {
    var price = priceHumidifier() + calcSummaryOption();
    $("#Summary").text(price);
}

function calcAltitude(atmospherePressure){
    document.getElementById("textAltitude").value = ((1-Math.pow(atmospherePressure/101.325,1/5.255))*288.15/0.0065).toFixed(0);
}

function calcAtmospherePressure(altitude){
    document.getElementById("textAtmospherePressure").value = (101.325 * Math.pow(1-(0.0065*altitude/288.15),5.255)).toFixed(2);
}

$(document).ready(function(){
    $('input:radio[name=idSelectHumidifier]').change(function(){
        calcSummaryPrice();
    });
});
$(document).ready(function(){
    $('input:checkbox[name=selectedOptions]').change(function() {
        calcSummaryPrice();
    });
});
$(document).ready(function(){
    $('input:checkbox[name=selectedDistributor]').change(function() {
        calcSummaryPrice();
    });
});
$(document).ready(function(){
    $('input:radio[id=radioAltitude]').change(function(){
        document.getElementById("textAtmospherePressure").disabled= 'disabled';
        document.getElementById("textAltitude").disabled = '';
    });
});
$(document).ready(function(){
    $('input:radio[id=radioAtmospherePressure]').change(function(){
        document.getElementById("textAtmospherePressure").disabled= '';
        document.getElementById("textAltitude").disabled = 'disabled';
    });
});

function calcSummaryOption() {
    var table = document.getElementById("table_option");
    if (table===null) return 0;
    let lastRow = table.rows[table.rows.length-1];
    let lastCell = lastRow.cells.length-1;
    var sum=0;
    for (var i = 1; i < table.rows.length; i++) {
        let row = table.rows[i];
            if (row.cells.item(0).getElementsByTagName("input").selectedDistributor!=null)
            if (row.cells.item(0).getElementsByTagName("input").selectedDistributor===true) {
                sum = sum +
                    (Number(row.cells.item(lastCell).innerText) || 0);
            }
            if (row.cells.item(0).getElementsByTagName("input").selectedOptions!=null)
            if (row.cells.item(0).getElementsByTagName("input").selectedOptions.checked===true) {
                sum = sum +
                    (Number(row.cells.item(lastCell).innerText) || 0);
            }
        }
    return sum;
}

function priceHumidifier() {
    var table = document.getElementById("table_humidifier");
    let lastRow = table.rows[table.rows.length-1];
    let lastCell = lastRow.cells.length - 1;
    for (var i = 1; i < table.rows.length; i++) {
        let row = table.rows[i];
        if (row.cells.item(0).getElementsByTagName("input").idSelectHumidifier.checked===true) {
            return (Number(row.cells.item(lastCell).innerText) || 0);
        }
    }
    return 0;
}


function option () {
    var title = document.getElementById('project_title'),
        address = document.getElementById('project_address'),
        text = document.querySelectorAll('input');
    if(title.value==="" || address.value==="") {
        text.forEach(function (e) {
            if (e.id !== 'project_title' && e.id !== 'project_address')
                e.disabled = 'disabled';
        });
    } else {
        text.forEach(function (e) {
                e.disabled = '';
        });
    }
}
// title.addEventListener('change', function () {option();});
// address.addEventListener('change', function () {option();});