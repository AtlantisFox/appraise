define(['jquery', 'bootstrap'], function ($) {

    function IndexForm(container) {
        var that = this;
        // private
        container = $(container);
        var name = container.find('.field-name');
        var weight = container.find('.field-weight');
        var remark = container.find('.field-remark');
        var point = container.find('.field-point');
        var appraiser = container.find('.field-appraiser');
        var appraisee = container.find('.field-appraisee');
        var saving_status = container.find('.dlg-status');
        var btn_save = container.find('.index-edit-save');
        var users = {};
        var indexes = {};
        var row;
        var callback;
        var mode_create;

        function _init() {
            container.on('shown.bs.modal', function () {
                name.focus();
            });

            container.on('hide.bs.modal', function () {
            });

            name.on('change', index_changed);
            btn_save.on('click', save_clicked);
        }

        this.load_data = function (data) {
            name.empty();
            indexes = {};
            $.each(data.indexes, function (_, index) {
                indexes[index.id] = index;
                name.append($('<option>', {
                    value: index.id,
                    text: index.name
                }));
            });

            users = {};
            $.each(data.users, function (_, user) {
                users[user.username] = user;
            });
        };

        function index_changed() {
            var index_name = name.val();
            var index = indexes[index_name] || {};
            remark.text(index.remark || '');
            point.text(index.point || '');
            var user1 = users[index.appraiser] || {};
            appraiser.text(user1.remark || '');
            var user2 = users[index.appraisee] || {};
            appraisee.text(user2.remark || '');
        }

        function save_clicked() {
            saving_status.text('正在保存...').show();
            // TODO: ajax save
            setTimeout(save_ajax_cb, 1);
        }

        function save_ajax_cb(data) {
            container.modal('hide');
            // TODO: return response data
            if (callback)
                callback(that.serialize(), row);
        }

        this.create = function (cb) {
            this.modify(cb, {});
            mode_create = true;
        };

        this.modify = function (cb, index, row_id) {
            mode_create = false;
            name.val(index.indexId);
            weight.val(index.weight || 0);
            row = row_id;
            index_changed();

            callback = cb;
            saving_status.hide();
            container.modal();
        };

        this.serialize = function () {
            return {
                indexId: parseInt(name.val()),
                weight: weight.val()
            };
        };

        _init();
    }

    return IndexForm;
});
