requirejs(['./common'], function () {
    requirejs.config({
        shim: {
            'bootstrap-switch': {'deps': ['bootstrap']}
        },
        paths: {
            'bootstrap-switch': '../vendor/bootstrap-switch/dist/js/bootstrap-switch'
        }
    });

    requirejs(['./users/list', './users/detail', './users/delete'], function (UserList, UserForm, UserDel) {
        var form = new UserForm('#user-edit-dlg');
        var del = new UserDel('#user-del-dlg');
        new UserList('#dataTables-users', form, del);
    });
});
