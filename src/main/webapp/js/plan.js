requirejs(['./plan/index', './plan/meta', './plan/detail', './plan/list'], function (Ctrl, Meta, Detail, List) {
    var plan_id = parseInt(window.location.hash.substr(1)) || 0;
    var ctrl_group = $('#dlg-control-group');
    var meta = new Meta('#plan-meta');
    var detail = new Detail('#index-edit-dlg');
    var list = new List('#dataTables-indexes', detail);
    new Ctrl(plan_id, ctrl_group, meta, list);
});
