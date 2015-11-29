define(['jquery'], function ($) {

    function PlanController(meta, list) {
        var that = this;

        function _init() {
            require(['sample_data'], function (data) {
                var data_basic = {
                    users: data.users,
                    indexes: data.indexes
                };
                var data_plan = {
                    meta: data.plan_meta,
                    indexes: data.plan_indexes
                };
                list.load_data(data_basic);
                meta.set_data(data_plan);
                list.load_plan(data_plan);
            });
        }

        _init();
    }

    return PlanController;
});
