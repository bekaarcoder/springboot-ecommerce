function checkEmail(form) {
    let url = "/users/check_email";
    let userEmail = $("#email").val();
    let csrf = $("input[name='_csrf']").val();
    let params = {email: userEmail, _csrf: csrf}

    $.post(url, params, function (response) {
        alert("response: " + response);
    });

    return false;
}