requirejs(['./plans/list', './plans/delete'], function (List, DelForm) {
    var del = new DelForm('#plan-del-dlg');
    new List('#dataTables-plans', del);
});
