define(['jquery', './password', 'bootstrap', 'bootstrap-switch'], function ($, UserPassword) {

    function UserForm(container) {
        var that = this;
        // private
        container = $(container);
        var username = container.find('.field-username');
        var username_static = container.find('.field-username-static');
        var remark = container.find('.field-remark');
        var password = container.find('.field-password');
        var confirm = container.find('.field-confirm');
        var pwd_hint = container.find('.field-hint-no-passwd');
        var appraisal = container.find('.field-appraisal');
        var account = container.find('.field-account');
        var saving_status = container.find('.dlg-status');
        var btn_save = container.find('.detail-edit-save');
        var callback = null;
        var mode_create;    // flag: in create_user mode
        var v_username;     // flag: username verified
        var v_password;     // flag: password verified

        function _init() {
            // auto focus
            container.on('shown.bs.modal', function () {
                if (mode_create) {
                    username.focus();
                } else {
                    remark.focus();
                }
            });
            container.on('hide.bs.modal', function () {
                control_feedback(username, true);
                control_feedback(password, true);
                control_feedback(confirm, true);
            });
            btn_save.on('click', save_clicked);
            username.on('change', username_check);
            password.focusout(password_check);
            confirm.focusout(password_check);
            appraisal.bootstrapSwitch();
            account.bootstrapSwitch();
        }

        function username_check() {
            v_username = false;
            if (!username.val()) return;
            var options = {
                url: 'api/user/exist.do',
                data: {
                    username: username.val()
                },
                dataType: 'json',
                type: 'post',
                success: username_check_cb
            };
            $.ajax(options);
            // username_check_cb(true);
        }

        function username_check_cb(exist) {
            if (!exist) {
                v_username = true;
                control_feedback(username, true);
            } else {
                control_feedback(username, false, '用户名已存在');
            }
        }

        function password_check(event, complete_check) {
            v_password = false;
            control_feedback(password, true);
            control_feedback(confirm, true);
            if (password.val() === confirm.val()) {
                if (password.val() === '') {
                    if (!mode_create) {
                        // allowed empty password when modifying existing account
                        v_password = true;
                    }
                } else {
                    v_password = true;
                }
                if (!v_password) {
                    if (complete_check) {
                        control_feedback(password, false, '请输入密码');
                        password.focus();
                    }
                }
            } else {
                if (confirm.val() === '' && !complete_check) {
                    // wait for re-type password
                } else {
                    control_feedback(confirm, false, confirm.val() === '' ? '请再输入一遍密码' : '密码不一致');
                    if (complete_check)
                        confirm.focus();
                }
            }
        }

        function control_feedback(control, succ, tip) {
            if (succ) {
                control.closest('.form-group').removeClass('has-error')
                    .find('span.form-control-feedback').hide().tooltip('hide');
            } else {
                var span = control.closest('.form-group').addClass('has-error')
                    .find('span.form-control-feedback')
                    .show();
                if (tip) {
                    span.tooltip('destroy');
                    span.tooltip({
                        title: tip,
                        container: 'body',
                        trigger: 'manual'
                    });
                    setTimeout(function () {
                        span.tooltip('show');
                    }, 200);
                }
            }
        }

        function save_clicked() {
            password_check(null, true);
            if (!v_username || !v_password)
                return;

            btn_save.prop('disabled', true);
            saving_status.text('正在保存...').show();
            var options = {
                url: mode_create ? 'api/user/create.do' : 'api/user/update.do',
                data: that.serialize(),
                dataType: 'json',
                type: 'post',
                success: save_ajax_cb,
                error: save_err_cb
            };
            $.ajax(options);
            // setTimeout(save_ajax_cb, 1000);
        }

        function save_ajax_cb(data) {
            container.modal('hide');
            if (callback)
                callback(that.serialize());
        }

        function save_err_cb(ajax_obj) {
            btn_save.prop('disabled', false);
            var o = ajax_obj;
            var txt = o.responseJSON && o.responseJSON.msg || 'unknown';
            saving_status.text('错误：' + txt);
        }

        function show_modal() {
            password.val('');
            confirm.val('');
            control_feedback(username, true);
            control_feedback(password, true);
            control_feedback(confirm, true);
            saving_status.hide();
            btn_save.prop('disabled', false);
            container.modal({keyboard: false});
        }

        this.create = function (cb) {
            username.show();
            username.val('');
            username_static.hide();
            remark.val('');
            pwd_hint.hide();
            // reset checkbox
            appraisal.bootstrapSwitch('state', false, true);
            account.bootstrapSwitch('state', false, true);
            appraisal.bootstrapSwitch('readonly', false, true);
            account.bootstrapSwitch('readonly', false, true);

            mode_create = true;
            v_username = false;
            v_password = false;
            callback = cb;
            show_modal();
        };

        this.modify = function (cb, user, is_admin) {
            username.hide();
            username.val(user.username);
            username_static.show();
            username_static.text(user.username);
            remark.val(user.remark);
            pwd_hint.show();
            // load checkbox
            appraisal.bootstrapSwitch('state', !!user.isAppraisalAdmin, true);
            account.bootstrapSwitch('state', !!user.isAccountAdmin, true);
            appraisal.bootstrapSwitch('readonly', !is_admin, true);
            account.bootstrapSwitch('readonly', !is_admin, true);

            mode_create = false;
            v_username = true;
            v_password = true;
            callback = cb;
            show_modal();
        };

        this.serialize = function () {
            return {
                username: username.val(),
                password: password.val() && UserPassword.hash(password.val()),
                remark: remark.val(),
                isAppraisalAdmin: appraisal.bootstrapSwitch('state') ? 1 : 0,
                isAccountAdmin: account.bootstrapSwitch('state') ? 1 : 0
            };
        };

        _init();
    }

    return UserForm;

});
