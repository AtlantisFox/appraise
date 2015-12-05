define(['jquery', 'moment', 'bootstrap', 'datatables.net', 'datatables.net-bs'], function ($, moment) {

    function PlansList(container, del_form) {
        var that = this;
        container = $(container);
        var table;
        var users = {};

        function _init() {
            function renderDateTime(date, type, row, meta) {
                return moment(date).format('YYYY-MM-DD HH:mm');
            }

            function renderModifyResetButton(status, type, row, meta) {
                if (status === 0) {
                    return '<button type="button" class="btn btn-info listitem-modify"><i class="fa fa-check"></i> 修改</button>';
                } else {
                    return '<button type="button" class="btn btn-warning listitem-reset"><i class="fa fa-undo"></i> 重置</button>'
                }
            }

            function renderExecuteHistoryButton(status, type, row, meta) {
                if (status === 0) {
                    return '<button type="button" class="btn btn-success listitem-modify"><i class="fa fa-play"></i> 执行</button>';
                } else {
                    return '<button type="button" class="btn btn-primary listitem-reset"><i class="fa fa-list"></i> 结果</button>'
                }
            }

            function renderDeleteButton(status, type, row, meta) {
                if (status === 0) {
                    return '<button type="button" class="btn btn-warning listitem-delete"><i class="fa fa-times"></i> 删除</button>';
                } else {
                    return '<button type="button" class="btn btn-warning listitem-delete disabled"><i class="fa fa-times"></i> 删除</button>';
                }
            }

            table = container.DataTable({
                columns: [
                    {data: 'name'},
                    {data: 'remark'},
                    {
                        data: 'deadline',
                        render: renderDateTime
                    },
                    {
                        data: 'status',
                        className: 'listitem-operation',
                        orderable: false,
                        searchable: false,
                        render: renderModifyResetButton
                    },
                    {
                        data: 'status',
                        className: 'listitem-operation',
                        orderable: false,
                        searchable: false,
                        render: renderExecuteHistoryButton
                    },
                    {
                        data: 'status',
                        className: 'listitem-operation',
                        orderable: false,
                        searchable: false,
                        render: renderDeleteButton
                    }
                ],
                dom: '<"listitem-toolbar">frtip'
            });
            container.parent().find('div.listitem-toolbar')
                .html('<a href="plan.html" class="btn btn-success listitem-add"><i class="fa fa-plus"></i> 添加新计划</a>')
                .select('button.listitem-add');

            container.on('click', 'td button.listitem-modify', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                var data = row.data();
                // detail.modify(modify_plan_cb, data);
            });

            container.on('click', 'td button.listitem-delete', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                var data = row.data();
                del_form.del(delete_plan_cb, data);
            });

            load_data();
        }

        function add_plan_cb(index) {
            var row = table.row.add(index);
            table.draw();
        }

        function modify_plan_cb(index) {
            var row = table.row(function (idx, data, node) {
                return (data.id === index.id);
            }).data(index);
            table.draw();
        }

        function delete_plan_cb(index) {
            var row = table.row(function (idx, data, node) {
                return (data.id === index.id);
            }).remove();
            table.draw();
        }

        function load_data() {
            var options = {
                url: 'api/plan/list',
                dataType: 'json',
                type: 'post',
                success: load_succ_cb
            };
            $.ajax(options);
            //require(['sample_data'], function (data) {
            //    load_succ_cb(data.plans);
            //});
        }

        function load_succ_cb(data) {
            table.rows().remove();
            table.rows.add(data);
            table.draw();
        }

        _init();
    }

    return PlansList;
});
