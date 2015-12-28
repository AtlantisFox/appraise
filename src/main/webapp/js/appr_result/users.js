define(['jquery', 'bootstrap', 'datatables.net', 'datatables.net-bs'], function ($) {
    function UserList(container, plan_id, appraise_dlg) {
        var that = this;
        container = $(container);
        var users = [];
        var plan_indexes = null;
        var summary = null;
        var indexes = {};
        var results = {};
        var table;

        function _init() {
            table = container.DataTable({
                columns: [
                    {data: 'user'},
                    {data: 'pointCommon'},
                    {data: 'pointUnique'},
                    {data: 'count', render: tableAppraiseBtnRender}
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

        function tableAppraiseBtnRender(count) {
            if (count > 0) {
                return '<button type="button" class="btn btn-info listitem-appraise">详情</button>';
            } else {
                return '无考评指标';
            }
        }

        function indexForUser(username) {
            var user_indexes = [];
            $.each(plan_indexes, function(_, planIndex) {
                var id = planIndex.indexId;
                var index = indexes[id];
                if (index.appraisee === '' || index.appraisee === username) {
                    user_indexes.push(index);
                }
            });
            return user_indexes;
        }

        function onAppraiseUser(summary_row) {
            var user_indexes = indexForUser(summary_row.user);
            appraise_dlg.modal(summary_row.user, plan_id, user_indexes, results[summary_row.user]);
        }

        function load_basic_data() {
            var options = {
                url: 'api/batch',
                data: {users: 1, indexes: 1},
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
            var summary_opt = {
                url: 'api/appraise/summary',
                data: {planId: plan_id},
                type: 'post',
                dataType: 'json',
                success: summary_ajax_succ_cb
            };
            $.ajax(summary_opt);
            var result_opt = {
                url: 'api/appraise/result',
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
        }

        function plan_ajax_succ_cb(data) {
            plan_indexes = data;
            renderTable();
        }

        function summary_ajax_succ_cb(data) {
            summary = data;
            renderTable();
        }

        function renderTable() {
            if (!(summary && plan_indexes))
                return;

            $.each(summary, function (_, sum) {
                sum.count = indexForUser(sum.user).length;
            });
            table.rows().remove();
            table.rows.add(summary);
            table.draw();
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