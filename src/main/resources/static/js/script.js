var idSelectHumidifier;
$(document).ready(function(){
    $('input:radio[name=idSelectHumidifier]').change(function(){
        idSelectHumidifier = $(this).val();
        var price = priceHumidifier(idSelectHumidifier) + calcSummaryOption();
        $("#Summary").text("Сумма: " + price);

        //Для теста

        $("#inputTest").val(idSelectHumidifier);
        //Попытки вытянуть опции без кнопки
        // $("#thead_option").after('' +
        //     '<tr className="tr_option" id="tr_option" th:each="instance : ${options.get(' + id + ')}"> ' +
        //         '<td> ' +
        //             '<label className="container"> ' +
        //             '<input type="checkbox" checked="checked"> ' +
        //             '<span className="checkmark"></span> ' +
        //             '</label>' +
        //         '</td> ' +
        //         '<td th:text="${instance.articleNumber}">арт</td> ' +
        //         '<td th:text="${instance.type.getTxt()}">тип</td> ' +
        //         '<td th:text="${instance.price}">0</td> ' +
        //     '</tr>');
        // $("#tr_option").val(id);
        // $("#table_option").val(id);
        // $(document.createAttribute("idSelectHumidifier",id));
        // $("#tr_option").attr("idSelectHumidifier",id);
        // $("#table_option").attr("idSelectHumidifier",id);
    });
});
$(document).ready(function(){
    $('input:checkbox[name=selectedOptions]').change(function() {
        var price = priceHumidifier(idSelectHumidifier) + calcSummaryOption();
        $("#Summary").text("Сумма: " + price);
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
$(document).ready(function(){
    $('#voltage').change(function() {
        var voltage = $(this).val();
        if (voltage==="220") {
            $('#field_phase').val(1);
        } else {
            $('#field_phase').val(3);
        }
    });
});

function calcSummaryOption() {
    var table = document.getElementById("table_option");
    let lastRow = table.rows[table.rows.length-1];
    let lastCell = lastRow.cells.length-1;
    var sum=0;
    for (var i = 1; i < table.rows.length; i++) {
        let row = table.rows[i];
            if (row.cells.item(0).getElementsByTagName("input").selectedOptions.checked==true) {
                sum = sum +
                    (Number(row.cells.item(lastCell).innerText) || 0);
            }
        }
    return sum;
}

function priceHumidifier(idSelectHumidifier) {
    var table = document.getElementById("table_humidifier");
    let lastRow = table.rows[table.rows.length-1];
    let lastCell = lastRow.cells.length - 1;
    for (var i = 1; i < table.rows.length; i++) {
        let row = table.rows[i];
        if (row.cells.item(0).getElementsByTagName("input").idSelectHumidifier.getAttribute("value")==idSelectHumidifier) {
            return (Number(row.cells.item(lastCell).innerText) || 0);
        }
    }
    return 0;
}