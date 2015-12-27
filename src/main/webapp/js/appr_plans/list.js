define(['jquery', 'moment', 'bootstrap', 'datatables.net', 'datatables.net-bs'], function ($, moment) {
    function PlansList(container) {
        var that = this;
        container = $(container);
        var table;

        function _init() {
            table = container.DataTable({
                columns: [
                    {data: 'name'},
                    {data: 'remark'},
                    {data: 'deadline', render: tableDeadlineRender},
                    {data: 'status', render: tableAppraiseViewButtonRender},
                    {data: 'unfinished', render: tableUnfinishedLabelRender}
                ]
            });
            load_data();
        }

        function tableDeadlineRender(data, type, row, meta) {
            return moment.unix(data).format('YYYY-MM-DD HH:mm');
        }

        function tableAppraiseViewButtonRender(data, type, row, meta) {
            if (data == 1) {
                return '<button type="button" class="btn btn-success">考评</button>';
            } else {
                return '<button type="button" class="btn btn-info">查看</button>'
            }
        }

        function tableUnfinishedLabelRender(data, type, row, meta) {
            if (data) {
                return '<span class="label label-danger">未完成</span>'
            } else {
                return '';
            }
        }

        function load_data() {
            var options = {
                url: 'api/batch',
                data: {'apprPlans': 1, 'apprUnfinished': 1},
                dataType: 'json',
                type: 'post',
                success: load_succ_cb
            };
            $.ajax(options);
        }

        function load_succ_cb(data) {
            var plans = data.apprPlans;
            var unfinished = data.apprUnfinished;
            $.each(plans, function(_, plan) {
                plan.unfinished = (unfinished.indexOf(plan.id) !== -1);
            });
            table.rows.add(plans);
            table.draw();
        }

        _init();
    }

    return PlansList;
});