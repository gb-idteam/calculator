$(document).ready(function(){
    $('input:radio[name=idSelectHumidifier]').change(function(){
        var idSelectHumidifier = $(this).val();
        var price = priceHumidifier() + calcSummaryOption();
        $("#Summary").text(price);
        //Для теста
        $("#inputTest").val(idSelectHumidifier);
    });
});
$(document).ready(function(){
    $('input:checkbox[name=selectedOptions]').change(function() {
        var price = priceHumidifier() + calcSummaryOption();
        $("#Summary").text(price);
    });
});
$(document).ready(function(){
// Разблокировать на будущее для блокировки до внесения данных проекта
    $('#btn-save').click(function(){
        $('.input').prop('disabled', false);
    });
});
$(document).ready(function(){
// Разблокировать на будущее для блокировки до внесения данных проекта
    $('#btn-clear').click(function(){
        $('.input').prop('disabled', true);
    });
});

function calcSummaryOption() {
    var table = document.getElementById("table_option");
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