requirejs(['./common'], function () {
    requirejs.config({
        shim: {},
        paths: {}
    });

    requirejs(['./plans/list', './plans/delete'], function (List, DelForm) {
        var del = new DelForm('#index-del-dlg');
        new List('#dataTables-plans', del);
    });
});
