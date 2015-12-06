define(['jquery'], function ($) {

    function PlanController(plan_id, ctrl_group, meta, list) {
        var that = this;
        var save_btn = ctrl_group.find('.dlg-btn-save');
        var save_tooltip_timeout;
        var true_plan_id;

        function _init() {
            save_btn.on('click', on_save);
            load_basic();
            if (plan_id != 0)
                load_plan();
            //require(['sample_data'], function (data) {
            //    var data_basic = {
            //        users: data.users,
            //        indexes: data.indexes
            //    };
            //    var data_plan = {
            //        meta: data.plan_meta,
            //        indexes: data.plan_indexes
            //    };
            //    list.load_data(data_basic);
            //    meta.set_data(data_plan);
            //    list.load_plan(data_plan);
            //});
        }

        function load_basic() {
            var options = {
                url: 'api/batch',
                data: {users: 1, indexes: 1},
                dataType: 'json',
                type: 'post',
                success: load_basic_succ
            };
            $.ajax(options);
        }

        function load_basic_succ(data) {
            list.load_data(data);
        }

        function load_plan() {
            var options = {
                url: 'api/plan/get',
                data: {id: plan_id},
                dataType: 'json',
                type: 'post',
                success: load_plan_succ
            };
            $.ajax(options);
        }

        function load_plan_succ(data) {
            if (data.meta === null) data.meta = {};
            true_plan_id = data.meta.id;
            meta.set_data(data);
            list.load_plan(data);
        }

        function on_save() {
            var data = {
                meta: meta.serialize(),
                indexes: list.serialize()
            };
            data.meta.id = true_plan_id;
            var options = {
                url: 'api/plan/save',
                data: JSON.stringify(data),
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                success: on_save_succ,
                error: on_save_err
            };
            save_btn.prop('disabled', true);
            $.ajax(options);
        }

        function on_save_succ(data) {
            save_btn.prop('disabled', false);
            true_plan_id = data.meta.id;
            save_tooltip('保存成功');
        }

        function on_save_err(ajax_obj) {
            save_btn.prop('disabled', false);
            var o = ajax_obj;
            var txt = o.responseJSON && o.responseJSON.msg || 'unknown';
            save_tooltip('错误：' + txt);
        }

        function save_tooltip(text) {
            save_tooltip_close();
            save_btn.tooltip({
                title: text,
                container: 'body',
                trigger: 'manual'
            });
            save_btn.tooltip('show');
            save_tooltip_timeout = setTimeout(save_tooltip_close, 5000);
        }

        function save_tooltip_close() {
            if (save_tooltip_timeout !== null) {
                clearTimeout(save_tooltip_timeout);
                save_tooltip_timeout = null;
                save_btn.tooltip('destroy');
            }
        }

        _init();
    }

    return PlanController;
});
