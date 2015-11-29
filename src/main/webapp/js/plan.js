requirejs(['./common'], function () {
    requirejs(['./plan/index', './plan/meta', './plan/detail', './plan/list'], function(Ctrl, Meta, Detail, List) {
        var meta = new Meta('#plan-meta');
        var detail = new Detail('#index-edit-dlg');
        var list = new List('#dataTables-indexes', detail);
        new Ctrl(meta, list);
    });
});
