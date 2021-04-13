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
        post(this.value, "/selectHumidifier");
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

function post(id, path) {
    // var xhr = new XMLHttpRequest();
    // xhr.open('POST', document.URL + path, true);
    // xhr.withCredentials = true;
    // xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    // xhr.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
    // xhr.send('idSelectHumidifier='+ id);
    // xhr.onload = function() {
    //     if (xhr.status != 200) { // HTTP ошибка?
    //         // обработаем ошибку
    //         alert( 'Ошибка: ' + xhr.status);
    //         return;
    //     }
    //     window.location = xhr.getResponseHeader('Location');
    // };
    // xhr.onprogress = function(event) {
    //     // выведем прогресс
    //     alert(`Загружено ${event.loaded} из ${event.total}`);
    // };
    // xhr.onerror = function() {
    //     // обработаем ошибку, не связанную с HTTP (например, нет соединения)
    // };

    $.ajax({
        url : document.URL + path,
        accept : 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
        data : 'idSelectHumidifier='+ id,
        contentType : 'application/x-www-form-urlencoded',
        type : 'POST',
        success : function(data) {
            alert('save');
        },
        error : function(xhr, status, errorThrown) {
            alert('adding component failed with status: ' + status + ". "
                + errorThrown);
        }
    });
}