$(document).ready(function(){
    $('input:radio[name=idSelectHumidifier]').change(function(){

        var id = $(this).val();
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
        $("#inputTest").val(id);
        // $(document.createAttribute("idSelectHumidifier",id));
        // $("#tr_option").attr("idSelectHumidifier",id);
        // $("#table_option").attr("idSelectHumidifier",id);
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