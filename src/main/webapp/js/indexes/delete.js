define(['jquery', '../common/confirm'], function ($, ConfirmDlg) {

    function IndexDel(container) {
        var that = this;
        // private
        var modal = new ConfirmDlg(container);
        var deleting_obj = null;
        var callback = null;

        function delete_clicked() {
            modal.setStatus('正在删除...');
            var options = {
                url: 'api/index/delete',
                data: {
                    id: deleting_obj.id
                },
                dataType: 'json',
                type: 'post',
                success: delete_ajax_cb,
                error: delete_err_cb
            };
            $.ajax(options);
            // setTimeout(delete_ajax_cb, 1000);
        }

        function delete_ajax_cb(data) {
            modal.hide();
            if (callback) {
                callback(data)
            }
        }

        function delete_err_cb(ajax_obj) {
            var o = ajax_obj;
            var txt = o.responseJSON && o.responseJSON.msg || 'unknown';
            modal.reset();
            modal.setStatus('错误：' + txt);
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
