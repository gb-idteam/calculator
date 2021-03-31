$(document).ready(function(){
    $('input:radio[name=radioHumidifier]').change(function(){
        var id = $(this).val();
        $("input[name=inputTest]").val(id);
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