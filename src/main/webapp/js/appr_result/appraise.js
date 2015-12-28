define(['jquery', 'bootstrap'], function($) {
    function AppraiseDlg(container) {
        var that = this;
        container = $(container);
        var status = container.find('.dlg-status');
        var dom_fields = container.find('.form-horizontal');
        var fields = [];
        var plan_id;
        var username_;
        var results_;
        var indexes_;

        function _init() {
        }

        this.modal = function(username, planId, indexes, results) {
            results_ = results;
            indexes_ = indexes;
            plan_id = planId;
            username_ = username;
            dom_fields.empty();
            fields = [];
            indexes_ = {};
            $.each(indexes, function(_, index) {
                indexes_[index.id] = index;
                var dom_label = $('<label class="col-sm-2 control-label"></label>');
                dom_label.text(index.name);
                var dom_input = $('<input type="text" class="form-control">');
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
                } else {
                    dom_input.val('(未评分)');
                }
                dom_input.prop('readonly', true);
            });
            container.modal('show');
        };

        _init();
    }

    return AppraiseDlg;
});