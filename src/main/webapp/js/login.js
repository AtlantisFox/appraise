requirejs(['./common'], function() {
    requirejs(['jquery', './users/password'], function($, pwd) {
        var container = $('#form-login');
        var banner = container.find('.dlg-banner');
        var username = container.find('.field-username');
        var password = container.find('.field-password');
        var submit = container.find('.dlg-login');

        function _init() {
            banner.hide();
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
            console.log('succeed');
            // TODO: verify window.location.pathname available on most platforms
            var pieces = window.location.pathname.split('.');
            pieces[0] = 'index';
            window.location = pieces.join('.');
        }

        function ajax_err_cb() {
            console.log(arguments);
            banner.show();
            setFocus();
            submit.prop('disabled', false);
        }

        _init();
    });
});
