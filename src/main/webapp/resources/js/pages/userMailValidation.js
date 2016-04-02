function addMailValidation(url, currentMail) {
    if ($("#email").length) {
        $("#email").rules('add', {
            remote: {
                url: url,
                type: "post",
                data: {email: function () {
                    return $("#email").val();
                },
                    currentEmail: currentMail}
            },
            messages: {
                remote: "Email already exist!"
            }
        });
    }
}
