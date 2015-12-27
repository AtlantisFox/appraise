define(['jquery', 'bootstrap'], function($) {
    function AppraiseDlg(container) {
        var that = this;
        container = $(container);
        var btn_submit = container.find('.dlg-btn-confirm');
        var status = container.find('.dlg-status');
        var dom_fields = container.find('.form-horizontal');
        var fields = [];
        var plan_id;
        var username;
        var results_;
        var indexes_;
        var ajax_obj = null;

        function _init() {
            btn_submit.click(onSubmitClicked);
        }

        function onSubmitClicked() {
            var data = [];
            // TODO: 验证输入数据
            $.each(fields, function(_, field) {
                var point = parseInt(field.val());
                var index = indexes_[field.data('index')];
                data.push({
                    indexId: index.id,
                    appraisee: username,
                    point: point
                });
                results_[field.data('index')] = point;
            });
            status.text('正在保存数据...');
            btn_submit.prop('disabled', true);
            var options = {
                url: 'api/appraise/appraise?planId=' + plan_id,
                data: JSON.stringify(data),
                type: 'post',
                dataType: 'json',
                contentType: 'application/json',
                success: on_save_success,
                error: on_save_error
            };
            ajax_obj = $.ajax(options);
        }

        function on_save_success(data) {
            container.modal('hide');
            ajax_obj = null;
        }

        function on_save_error(ajax_obj) {
            var o = ajax_obj;
            var txt = o.responseJSON && o.responseJSON.msg || 'unknown';
            status.text('错误：' + txt);
            btn_submit.prop('disabled', false);
        }

        this.modal = function(user, planId, indexes, results) {
            if (ajax_obj) {
                ajax_obj.abort();
                ajax_obj = null;
            }
            status.text('');
            btn_submit.prop('disabled', false);
            results_ = results;
            indexes_ = indexes;
            plan_id = planId;
            username = user.username;
            dom_fields.empty();
            fields = [];
            indexes_ = {};
            $.each(indexes, function(_, index) {
                indexes_[index.id] = index;
                var dom_label = $('<label class="col-sm-2 control-label"></label>');
                dom_label.text(index.name);
                var dom_input = $('<input type="number" class="form-control">');
                dom_input.data('index', index.id);
                var dom_helper = $('<p class="help-block"/>');
                dom_helper.text('满分 ' + index.point + ' 分。' + index.remark);
                var dom_div = $('<div class="col-sm-10"/>');
                dom_div.append(dom_input);
                dom_div.append(dom_helper);
                var dom_form_group = $('<div class="form-group"></div>');
                dom_form_group.append(dom_label);
                dom_form_group.append(dom_div);
                dom_fields.append(dom_form_group);
                fields.push(dom_input);
                if (typeof results[index.id] !== 'undefined') {
                    dom_input.val(results[index.id]);
                }
            });
            container.modal('show');
        };

        _init();
    }

    return AppraiseDlg;
});