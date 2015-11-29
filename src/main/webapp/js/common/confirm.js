define(['jquery', 'bootstrap'], function ($) {

    function ConfirmModal(options) {
        var that = this;
        // private
        var container;
        var fields = [];
        var confirm_btn;
        var confirm_cb;
        var cancel_cb;
        var dlg_status = options.status && $(options.status) || container.find('.dlg-status');

        function _init() {
        }

        this.setStatus = function(text) {
            dlg_status.text(text);
            dlg_status.show();
        };

        this.hideStatus = function() {
            dlg_status.hide();
        }
        ;
    }

    return ConfirmModal;
});