requirejs(['./plans/list', './plans/delete', './plans/execute', './plans/reset'], function (List, DelForm, PlanExecute, PlanReset) {
    var del = new DelForm('#plan-del-dlg');
    var execute = new PlanExecute('#plan-exe-dlg');
    var reset = new PlanReset('#plan-reset-dlg');
    new List('#dataTables-plans', del, execute, reset);
});
