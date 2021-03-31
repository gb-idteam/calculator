$(document).ready(function(){
    $('input:radio[name=radioHumidifier]').change(function(){
        var ddd = $(this).val();
        $("input[name=inputTest]").val(ddd);
    });
});