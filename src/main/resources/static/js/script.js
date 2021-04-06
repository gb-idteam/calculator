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

//Пока не работает
// $('form').submit(function(){
//     // Если textarea пустое
//     if(!$(this).find('textarea').val()){
//         // отменяем отправку
//         return false;
//     }
// });

// $(document).ready(function(){
// // Разблокировать на будущее для блокировки до внесения данных проекта
//     $('#btn-save').click(function(){
//         option ();
//     });
// });
// $(document).ready(function(){
// // Разблокировать на будущее для блокировки до внесения данных проекта
//     $('#btn-clear').click(function(){
//         option ();
//     });
// });

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