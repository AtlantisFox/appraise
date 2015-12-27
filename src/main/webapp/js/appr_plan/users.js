define(['jquery', 'bootstrap', 'datatables.net', 'datatables.net-bs'], function ($) {
    function UserList(container, plan_id, appraise_dlg) {
        var that = this;
        container = $(container);
        var me;
        var users = [];
        var plan_indexes = [];
        var indexes = {};
        var results = {};
        var table;

        function _init() {
            table = container.DataTable({
                columns: [
                    {data: 'username'},
                    {data: 'remark'},
                    {data: null, render: tableAppraiseBtnRender}
                ]
            });
            container.on('click', 'td button.listitem-appraise', function() {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                var data = row.data();
                onAppraiseUser(data);
            });
            load_basic_data();
        }

        function tableAppraiseBtnRender() {
            return '<button type="button" class="btn btn-info listitem-appraise">评分</button>'
        }

        function onAppraiseUser(user) {
            var user_indexes = [];
            $.each(plan_indexes, function(_, planIndex) {
                var id = planIndex.indexId;
                var index = indexes[id];
                if (index.appraiser !== me.username) return;
                if (index.appraisee === '' || index.appraisee === user.username) {
                    user_indexes.push(index);
                }
            });
            appraise_dlg.modal(user, plan_id, user_indexes, results[user.username]);
        }

        function load_basic_data() {
            var options = {
                url: 'api/batch',
                data: {users: 1, indexes: 1, me: 1},
                type: 'post',
                dataType: 'json',
                success: basic_ajax_succ_cb
            };
            $.ajax(options);
        }

        function load_plan_data() {
            var plan_opt = {
                url: 'api/appraise/plan',
                data: {planId: plan_id},
                type: 'post',
                dataType: 'json',
                success: plan_ajax_succ_cb
            };
            $.ajax(plan_opt);
            var result_opt = {
                url: 'api/appraise/appraiser_results',
                data: {planId: plan_id},
                type: 'post',
                dataType: 'json',
                success: result_ajax_succ_cb
            };
            $.ajax(result_opt);
        }

        function basic_ajax_succ_cb(data) {
            users = data.users;
            $.each(data.users, function (_, user) {
                results[user.username] = {};
            });
            $.each(data.indexes, function (_, index) {
                indexes[index.id] = index;
            });
            me = data.me;
            load_plan_data();
            table.rows.add(users);
            table.draw();
        }

        function plan_ajax_succ_cb(data) {
            plan_indexes = data;
        }

        function result_ajax_succ_cb(data) {
            $.each(data, function(_, row) {
                results[row.appraisee][row.indexId] = row.point;
            });
        }

        _init();
    }
    return UserList;
});