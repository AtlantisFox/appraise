define(['jquery', 'bootstrap'], function ($) {

    function IndexForm(container) {
        var that = this;
        // private
        container = $(container);
        var id;
        var name = container.find('.field-name');
        var remark = container.find('.field-remark');
        var point = container.find('.field-point');
        var appraiser = container.find('.field-appraiser');
        var appraisee = container.find('.field-appraisee');
        var saving_status = container.find('.dlg-status');
        var btn_save = container.find('.index-edit-save');
        var callback;
        var mode_create;

        function _init() {
            container.on('shown.bs.modal', function () {
                name.focus();
            });

            container.on('hide.bs.modal', function () {
            });

            btn_save.on('click', save_clicked);
        }

        this.load_users = function (users) {
            $.each([appraiser, appraisee], function (_, ctrl) {
                ctrl.empty();
                if (ctrl === appraisee) {
                    ctrl.append($('<option>', {
                        value: '',
                        text: '共性指标'
                    }));
                }
                $.each(users, function (_, user) {
                    ctrl.append($('<option>', {
                        value: user.username,
                        text: user.remark
                    }));
                });
            });
        };

        function save_clicked() {
            saving_status.text('正在保存...').show();
            // TODO: ajax save
            setTimeout(save_ajax_cb, 1000);
        }

        function save_ajax_cb(data) {
            container.modal('hide');
            // TODO: return response data
            if (callback)
                callback(that.serialize());
        }

        var defaults = {
            point: 5
        };

        this.create = function (cb) {
            this.modify(cb, defaults);
            mode_create = true;
        };

        this.modify = function (cb, index) {
            mode_create = false;
            id = index.id;
            name.val(index.name || '');
            remark.val(index.remark || '');
            point.val(index.point || 0);
            appraiser.val(index.appraiser);
            appraisee.val(index.appraisee || '');

            callback = cb;
            saving_status.hide();
            container.modal();
        };

        this.serialize = function () {
            return {
                id: id,
                name: name.val(),
                remark: remark.val(),
                point: point.val(),
                appraiser: appraiser.val(),
                appraisee: appraisee.val()
            }
        };

        _init();
    }

    return IndexForm;
});
