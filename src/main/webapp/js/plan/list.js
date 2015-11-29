define(['jquery', 'bootstrap', 'datatables.net', 'datatables.net-bs'], function ($) {

    function PlanList(container, detail, del_form) {
        container = $(container);
        var that = this;
        var table;
        var users = {};
        var indexes = {};

        function _init() {
            function renderName(data, type, row, meta) {
                return indexes[data].name || '';
            }

            function renderRemark(data, type, row, meta) {
                return indexes[data].remark || '';
            }

            function renderAppraiser(data, type, row, meta) {
                var user = users[indexes[data].appraiser] || {};
                return user.remark || '';
            }

            function renderAppraisee(data, type, row, meta) {
                var user = users[indexes[data].appraisee] || {};
                return user.remark || '';
            }

            function renderModifyButton(data, type, row, meta) {
                return '<button type="button" class="btn btn-info listitem-modify"><i class="fa fa-check"></i> 修改</button>';
            }

            function renderDeleteButton(data, type, row, meta) {
                return '<button type="button" class="btn btn-warning listitem-delete"><i class="fa fa-times"></i> 删除</button>';
            }

            table = container.DataTable({
                columns: [{
                    data: 'id',
                    render: renderName,
                }, {
                    data: 'weight'
                }, {
                    data: 'id',
                    render: renderRemark
                }, {
                    data: 'id',
                    render: renderAppraiser
                }, {
                    data: 'id',
                    render: renderAppraisee
                }, {
                    className: 'listitem-operation',
                    orderable: false,
                    searchable: false,
                    render: renderModifyButton
                }, {
                    className: 'listitem-operation',
                    orderable: false,
                    searchable: false,
                    render: renderDeleteButton
                }],
                dom: '<"listitem-toolbar">frtip'
            });
            container.parent().find('div.listitem-toolbar')
                .html('<button type="button" class="btn btn-success listitem-add"><i class="fa fa-plus"></i> 导入指标</button>')
                .select('button.listitem-add')
                .click(function () {
                    detail.create(add_index_cb);
                });

            container.on('click', 'td button.listitem-modify', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                var data = row.data();
                detail.modify(modify_index_cb, data);
            });

            container.on('click', 'td button.listitem-delete', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                var data = row.data();
                // del_form.del(delete_index_cb, data);
            });
        }

        function used_indexes(exclude_current) {
            var list = [];
            table.row(function (idx, data, node) {
                if (data.id !== exclude_current) {
                    list.push(data.id);
                }
            });
            return list;
        }

        function add_index_cb(index) {
            var row = table.row.add(index);
            table.draw();
        }

        function modify_index_cb(index) {
            var row = table.row(function (idx, data, node) {
                return (data.id === index.id);
            })
            row.data(index);
            table.draw();
        }

        function delete_index_cb(index) {
            var row = table.row(function (idx, data, node) {
                return (data.id === index.id);
            }).remove();
            table.draw();
        }

        this.load_data = function (data) {
            indexes = {};
            $.each(data.indexes, function (_, index) {
                indexes[index.id] = index;
            });

            users = {};
            $.each(data.users, function (_, user) {
                users[user.username] = user;
            });

            detail.load_data(data);
        };

        this.load_plan = function (plan) {
            table.rows().remove();
            table.rows.add(plan.indexes);
            table.draw();
        };

        this.serialize = function () {
            // TODO: test
            return table.rows().data();
        };

        _init();
    }

    return PlanList;
});
