define(['jquery', 'bootstrap', 'datatables.net', 'datatables.net-bs'], function ($) {

    function UserList(container, userform, user_del_form) {
        container = $(container);
        var table;

        function _init() {
            function renderTableAdmin(data, type, row, meta) {
                return data ? '管理员' : '';
            }

            function renderTableModifyButton(data, type, row, meta) {
                return '<button type="button" class="btn btn-info listitem-modify"><i class="fa fa-check"></i> 修改</button>';
            }

            function renderTableDeleteButton(data, type, row, meta) {
                return '<button type="button" class="btn btn-warning listitem-delete"><i class="fa fa-times"></i> 删除</button>';
            }

            table = container.DataTable({
                columns: [
                    {data: 'username'},
                    {data: 'remark'},
                    {
                        className: 'listitem-operation',
                        searchable: false,
                        data: 'isAppraisalAdmin',
                        render: renderTableAdmin
                    },
                    {
                        className: 'listitem-operation',
                        searchable: false,
                        data: 'isAccountAdmin',
                        render: renderTableAdmin
                    },
                    {
                        className: 'listitem-operation',    // 居中
                        orderable: false,
                        searchable: false,
                        data: null,
                        render: renderTableModifyButton
                        // defaultContent: '<button type="button" class="btn btn-info listitem-modify"><i class="fa fa-check"></i> 修改</button>'
                    }, {
                        className: 'listitem-operation',
                        orderable: false,
                        searchable: false,
                        data: null,
                        render: renderTableDeleteButton
                        // defaultContent: '<button type="button" class="btn btn-warning listitem-delete"><i class="fa fa-times"></i> 删除</button>'
                    }
                ],
                // responsive: true,
                // scroller: true,
                dom: '<"listitem-toolbar">frtip',   // 添加新用户的按钮，见下 div.listitem-toolbar
                // language: { url: 'js/Chinese.lang.json' }
                order: [[0, 'asc']]
            });
            container.parent().find('div.listitem-toolbar')
                .html('<button type="button" class="btn btn-success listitem-add"><i class="fa fa-plus"></i> 添加新用户</button>')
                .select('button.listitem-add')
                .click(function () {
                    userform.creat(add_user_cb);
                });

            container.on('click', 'td button.listitem-modify', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                var data = row.data();
                userform.modify(modify_user_cb, data, true);
            });

            container.on('click', 'td button.listitem-delete', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                var data = row.data();
                user_del_form.del(delete_user_cb, data);
            });

            load_data();
        }

        function add_user_cb(user) {
            var row = table.row.add(user);
            table.draw();
        }

        function modify_user_cb(user) {
            var row = table.row(function (idx, data, node) {
                return (data.username === user.username);
            }).data(user);
            table.draw();
        }

        function delete_user_cb(user) {
            table.row(function (idx, data, node) {
                return (data.username === user.username);
            }).remove();
            table.draw();
        }

        function load_data() {
            var options = {
                url: 'api/user/list.do',
                dataType: 'json',
                type: 'post',
                success: load_succ_cb
            };
            $.ajax(options);
            // require(['sample_data'], function(data) {
            //     load_succ_cb(data.users);
            // });
        }

        function load_succ_cb(data) {
            table.rows().remove();
            table.rows.add(data);
            table.draw();
        }

        _init();
    }

    return UserList;
});
