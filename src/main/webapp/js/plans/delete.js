define(['jquery', '../common/confirm'], function ($, ConfirmDlg) {

    function PlanDel(container) {
        var that = this;
        var modal = new ConfirmDlg(container);
        var callback;
        var deleting_obj;

        function delete_clicked() {
            modal.setStatus('正在删除...');
            var options = {
                url: 'api/plan/delete',
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

        function delete_ajax_cb() {
            modal.hide();
            if (callback) {
                callback(deleting_obj);
            }
        }

        function delete_err_cb(ajax_obj) {
            var o = ajax_obj;
            var txt = o.responseJSON && o.responseJSON.msg || 'unknown';
            modal.reset();
            modal.setStatus('错误：' + txt);
        }

        this.del = function (cb, plan) {
            callback = cb;
            deleting_obj = plan;
            modal.modal({
                fields: ['name', 'remark'],
                data: plan,
                confirm_cb: delete_clicked
            });
        };
    }

    return PlanDel;
});
