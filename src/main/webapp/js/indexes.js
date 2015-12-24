requirejs(['./indexes/list', './indexes/detail', './indexes/delete'], function (List, Form, DelForm) {
    var form = new Form('#index-edit-dlg');
    var del = new DelForm('#index-del-dlg');
    new List('#dataTables-indexes', form, del);
});
