define(['jquery', 'bootstrap', 'datatables.net', 'datatables.net-bs'], function ($) {

    function IndexesList(container, detail, del_form) {
        var that = this;
        container = $(container);
        var table;
        var users = {};

        function _init() {
            function renderUser(name, type, row, meta) {
                if (name === '') return '全体部门';
                return typeof users[name] !== 'undefined' ? users[name] : '';
            }

            function renderModifyButton(used, type, row, meta) {
                if (!used) {
                    return '<button type="button" class="btn btn-info listitem-modify"><i class="fa fa-check"></i> 修改</button>';
                } else {
                    return '<button type="button" class="btn btn-info" disabled>已使用</button>'
                }
            }

            function renderDeleteButton(used, type, row, meta) {
                if (!used) {
                    return '<button type="button" class="btn btn-warning listitem-delete"><i class="fa fa-times"></i> 删除</button>';
                } else {
                    return '<button type="button" class="btn btn-warning" disabled>已使用</button>'
                }
            }

            table = container.DataTable({
                columns: [
                    {data: 'name'},
                    {data: 'remark'},
                    {data: 'point'},
                    {
                        data: 'appraiser',
                        render: renderUser
                    },
                    {
                        data: 'appraisee',
                        render: renderUser
                    },
                    {
                        className: 'listitem-operation',
                        orderable: false,
                        searchable: false,
                        data: 'used',
                        render: renderModifyButton
                    },
                    {
                        className: 'listitem-operation',
                        orderable: false,
                        searchable: false,
                        data: 'used',
                        render: renderDeleteButton
                    }
                ],
                dom: '<"listitem-toolbar">frtip'
            });
            container.parent().find('div.listitem-toolbar')
                .html('<button type="button" class="btn btn-success listitem-add"><i class="fa fa-plus"></i> 添加新指标</button>')
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
                del_form.del(delete_index_cb, data);
            });

            load_data();
        }

        function add_index_cb(index) {
            var row = table.row.add(index);
            table.draw();
        }

        function modify_index_cb(index) {
            var row = table.row(function (idx, data, node) {
                return (data.id === index.id);
            }).data(index);
            table.draw();
        }

        function delete_index_cb(index) {
            var row = table.row(function (idx, data, node) {
                return (data.id === index.id);
            }).remove();
            table.draw();
        }

        function load_data() {
            // TODO: ajax request
            require(['sample_data'], function (data) {
                var sample = {
                    users: data.users,
                    indexes: data.indexes,
                    usedIndexes: data.usedIndexes
                };
                load_succ_cb(sample);
            });
        }

        function load_succ_cb(data) {
            users = {};
            $.each(data.users, function (_, user) {
                users[user.username] = user.remark;
            });
            $.each(data.indexes, function (_, index) {
                index.used = data.usedIndexes.indexOf(index.id) !== -1;
            });
            table.rows().remove();
            table.rows.add(data.indexes);
            table.draw();
            detail.load_users(data.users);
        }

        _init();
    }

    return IndexesList;
});
