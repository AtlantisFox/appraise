requirejs(['./common'], function() {
    requirejs(['jquery', './users/password'], function($, pwd) {
        var container = $('#form-login');
        var banner = container.find('.detail-banner');
        var username = container.find('.detail-username');
        var password = container.find('.detail-password');
        var submit = container.find('.detail-login');

        function _init() {
            banner.hide();
            submit.click(do_login);
            submit.prop('disabled', false);
            set_focus();
        }

        function set_focus() {
            if (username.val()) {
                // TODO: select all
                password.focus();
                password.select();
            } else {
                username.focus();
            }
        }

        function do_login(event) {
            event.preventDefault();
            submit.prop('disabled', true);
            $.ajax({
                url: 'api/user/auth.do',
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
            var suffix = pieces.length > 0 ? pieces[pieces.length - 1] : 'html';
            window.location = 'index.' + suffix;
        }

        function ajax_err_cb() {
            console.log(arguments);
            banner.show();
            set_focus();
            submit.prop('disabled', false);
        }

        _init();
    });
});
