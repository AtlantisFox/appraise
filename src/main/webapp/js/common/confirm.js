define(['jquery', 'bootstrap'], function ($) {

    function ConfirmModal(container) {
        container = $(container);
        // private
        var that = this;
        var dlg_status = container.find('.dlg-status');
        var confirm_btn = container.find('.dlg-btn-confirm');
        var confirm_cb;
        var data_obj;

        function _init() {
            confirm_btn.on('click', onConfirmClicked);
        }

        this.modal = function(options) {
            // model -> view
            $.each(options.fields, function(_, field) {
                var name, default_val, cls;
                if (typeof field === 'string') {
                    name = field;
                    cls = '.field-' + field;
                    default_val = '';
                } else {
                    name = field.name;
                    cls = field.cls || ('.field-' + name);
                    default_val = field.default_val || '';
                }
                var text = options.data[name];
                if (typeof text === 'undefined') text = default_val;
                container.find(cls).text(text);
            });
            confirm_cb = options.confirm_cb;
            data_obj = options.data;
            this.reset();
            container.modal('show');
        };

        function onConfirmClicked() {
            confirm_btn.prop('disabled', true);
            if (typeof confirm_cb === 'function') {
                confirm_cb(data_obj);
            } else {
                that.hide();
            }
        }

        this.hide = function() {
            container.modal('hide');
        };

        this.reset = function() {
            confirm_btn.prop('disabled', false);
            this.hideStatus();
        };

        this.setStatus = function(text) {
            dlg_status.text(text);
            dlg_status.show();
        };

        this.hideStatus = function() {
            dlg_status.hide();
        };

        _init();
    }

    return ConfirmModal;
});
