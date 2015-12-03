define(['jquery', '../common/confirm'], function ($, ConfirmDlg) {

    function IndexDel(container) {
        var that = this;
        // private
        var modal = new ConfirmDlg(container);
        var deleting_obj = null;
        var callback = null;

        function delete_clicked() {
            modal.setStatus('正在删除...');
            // TODO: ajax del
            setTimeout(delete_ajax_cb, 1000);
        }

        function delete_ajax_cb(data) {
            modal.hide();
            if (callback) {
                callback(data)
            }
        }

        this.del = function (cb, index) {
            deleting_obj = index;
            callback = cb;
            modal.modal({
                fields: ['name', 'remark'],
                data: index,
                confirm_cb: delete_clicked
            });
        };
    }

    return IndexDel;
});
