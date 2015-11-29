define(['jquery', 'bootstrap-datetimepicker'], function ($) {

    function PlanMeta(container) {
        container = $(container);
        var name = container.find('.field-name');
        var remark = container.find('.field-remark');
        var deadline = container.find('.field-deadline');
        var ddl;

        var DATE_FORMAT = 'YYYY-MM-DD HH:mm';

        function _init() {
            deadline.datetimepicker({
                format: DATE_FORMAT
            });
            ddl = deadline.data("DateTimePicker");
        }

        this.set_data = function (plan) {
            var meta = plan.meta;
            name.val(meta.name || '');
            remark.val(meta.remark || '');
            ddl.date(new Date(meta.deadline));
        };

        this.serialize = function () {
            return {};
        };

        _init();
    }

    return PlanMeta;
});
