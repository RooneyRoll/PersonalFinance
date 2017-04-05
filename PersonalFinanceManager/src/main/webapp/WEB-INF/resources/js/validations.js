$(document).ready(function () {
    //form validations
    $("#login-form").validate({
        rules : {
            username : "required",
            password : "required"
        },
        messages : {
            username : "Моля, въведете потребителско име",
            password : "Моля, въведете парола"
        }
    });

    $("#register-form").validate({
        rules : {
            username : "required",
            password : "required",
            email : {
                required : true,
                email : true
            },
            firstname : "required",
            middlename : "required",
            lastname : "required"
        },
        messages : {
            username : "Моля, въведете потребителско име",
            password : "Моля, въведете парола",
            email : {
                required : "Моля, въведете e-mail",
                email : " Моля, въведете валиден e-mail"
            },
            firstname : "Моля, въведете име",
            middlename : "Моля, въведете презиме",
            lastname : "Моля, въведете фамилия"
        }
    })
});