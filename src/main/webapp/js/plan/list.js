define(['jquery', 'bootstrap', 'datatables.net', 'datatables.net-bs'], function ($) {

    function PlanList(container, detail, del_form) {
        container = $(container);
        var that = this;
        var table;
        var users = {};
        var indexes = {};

        function _init() {
            function renderName(indexId, type, row, meta) {
                return indexes[indexId].name || '';
            }

            function renderRemark(indexId, type, row, meta) {
                return indexes[indexId].remark || '';
            }

            function renderAppraiser(indexId, type, row, meta) {
                var user = users[indexes[indexId].appraiser] || {};
                return user.remark || '';
            }

            function renderAppraisee(indexId, type, row, meta) {
                var user = users[indexes[indexId].appraisee] || {};
                return user.remark || '';
            }

            function renderModifyButton(indexId, type, row, meta) {
                return '<button type="button" class="btn btn-info listitem-modify"><i class="fa fa-check"></i> 修改</button>';
            }

            function renderDeleteButton(indexId, type, row, meta) {
                return '<button type="button" class="btn btn-warning listitem-delete"><i class="fa fa-times"></i> 删除</button>';
            }

            table = container.DataTable({
                columns: [{
                    data: 'indexId',
                    render: renderName
                }, {
                    data: 'weight'
                }, {
                    data: 'indexId',
                    render: renderRemark
                }, {
                    data: 'indexId',
                    render: renderAppraiser
                }, {
                    data: 'indexId',
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
                paging: false,
                dom: '<"listitem-toolbar">frtip'
            });
            container.parent().find('div.listitem-toolbar')
                .html('<button type="button" class="btn btn-success listitem-add"><i class="fa fa-plus"></i> 导入指标</button>')
                .select('button.listitem-add')
                .click(function () {
                    detail.create(modify_index_cb);
                });

            container.on('click', 'td button.listitem-modify', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                var data = row.data();
                detail.modify(modify_index_cb, data, row);
            });

            container.on('click', 'td button.listitem-delete', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                var data = row.data();
                console.log(row);
                table.row(row).remove();
                table.draw();
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

        function modify_index_cb(index, row) {
            if (typeof row === 'undefined') {
                // insert
                table.row.add(index);
            } else {
                // update
                table.row(row).data(index);
            }
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
            return table.data().toArray();
        };

        _init();
    }

    return PlanList;
});
