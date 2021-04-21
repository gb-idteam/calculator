$(document).ready(function(){
    var pattern = /^[a-z0-9_-]+@[a-z0-9-]+\.([a-z]{1,6}\.)?[a-z]{2,6}$/i;
    var mail = $('#mail');

    mail.blur(function(){
        if(mail.val() != ''){
            if(mail.val().search(pattern) == 0){
                $('#valid').email('Подходит');
                $('#submit').attr('disabled', false);
                mail.removeClass('error').addClass('ok');
            }else{
                $('#valid').email('Не подходит');
                $('#submit').attr('disabled', true);
                mail.addClass('ok');
            }
        }else{
            $('#valid').email('Поле e-mail не должно быть пустым!');
            mail.addClass('error');
            $('#submit').attr('disabled', true);
        }
    });
});