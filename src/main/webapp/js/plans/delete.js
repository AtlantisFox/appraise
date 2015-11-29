define(['jquery', 'bootstrap'], function ($) {

    function PlanDel(container) {
        var that = this;
        // private
        container = $(container);
        var name = container.find('.field-name');
        var remark = container.find('.field-remark');
        var btn_del = container.find('.deldlg-btn');
        var del_status = container.find('.dlg-status');
        var deleting_obj = null;
        var callback = null;

        function _init() {
            btn_del.on('click', delete_clicked);
        }

        function delete_clicked() {
            del_status.text('正在删除...').show();
            // TODO: ajax del
            setTimeout(delete_ajax_cb, 1000);
        }

        function delete_ajax_cb(data) {
            container.modal('hide');
            if (callback) {
                callback(deleting_obj)
            }
        }

        this.del = function (cb, plan) {
            name.text(plan.name);
            remark.text(plan.remark);
            deleting_obj = plan;
            callback = cb;
            del_status.hide();
            container.modal();
        };

        _init();
    }

    return PlanDel;
});
