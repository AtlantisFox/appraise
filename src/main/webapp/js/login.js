requirejs(['jquery', './users/password'], function ($, pwd) {
    var container = $('#form-login');
    var banner = container.find('.dlg-banner-fail');
    var username = container.find('.field-username');
    var password = container.find('.field-password');
    var submit = container.find('.dlg-btn-login');

    function _init() {
        submit.click(doLogin);
        submit.prop('disabled', false);
        setFocus();
    }

    function setFocus() {
        if (username.val()) {
            password.focus();
            password.select();
        } else {
            username.focus();
        }
    }

    function doLogin(event) {
        event.preventDefault();
        submit.prop('disabled', true);
        $.ajax({
            url: 'api/user/auth',
            data: {
                username: username.val(),
                password: pwd.hash(password.val())
            },
            dataType: 'json',
            type: 'post',
            success: ajax_succ_cb,
            error: ajax_err_cb
        });
    }

    function ajax_succ_cb() {
        window.location = 'index';
    }

    function ajax_err_cb() {
        banner.show();
        setFocus();
        submit.prop('disabled', false);
    }

    _init();
});
