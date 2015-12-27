define(['jquery', '../common/confirm'], function ($, ConfirmDlg) {

    function PlanReset(container) {
        var that = this;
        var modal = new ConfirmDlg(container);
        var callback;
        var plan_obj;

        function execute_clicked() {
            modal.setStatus('正在重置...');
            var options = {
                url: 'api/appraise/reset',
                data: {
                    planId: plan_obj.id
                },
                dataType: 'json',
                type: 'post',
                success: ajax_succ_cb,
                error: ajax_err_cb
            };
            $.ajax(options);
            // setTimeout(ajax_succ_cb, 1000);
        }

        function ajax_succ_cb() {
            modal.hide();
            if (callback) {
                plan_obj.status = 0;
                callback(plan_obj);
            }
        }

        function ajax_err_cb(ajax_obj) {
            var o = ajax_obj;
            var txt = o.responseJSON && o.responseJSON.msg || 'unknown';
            modal.reset();
            modal.setStatus('错误：' + txt);
        }

        this.reset = function (cb, plan) {
            callback = cb;
            plan_obj = plan;
            modal.modal({
                fields: ['name', 'remark'],
                data: plan,
                confirm_cb: execute_clicked
            });
        };
    }

    return PlanReset;
});
