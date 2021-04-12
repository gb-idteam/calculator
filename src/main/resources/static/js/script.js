$(document).ready(function(){
    $('input:radio[name=idSelectHumidifier]').change(function(){
        var price = priceHumidifier() + calcSummaryOption();
        $("#Summary").text(price);
    });
});
$(document).ready(function(){
    $('input:checkbox[name=selectedOptions]').change(function() {
        var price = priceHumidifier() + calcSummaryOption();
        $("#Summary").text(price);
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
        text.forEach(function (e, i) {
            if (e.id !== 'project_title' && e.id !== 'project_address')
                e.disabled = 'disabled';
        });
    } else {
        text.forEach(function (e, i) {
                e.disabled = '';
        });
    }
}
// title.addEventListener('change', function () {option();});
// address.addEventListener('change', function () {option();});