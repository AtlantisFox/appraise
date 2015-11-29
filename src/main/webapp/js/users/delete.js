define(['jquery', 'bootstrap'], function ($) {

    function UserDel(container) {
        var that = this;
        // private
        container = $(container);
        var username = container.find('.field-name');
        var remark = container.find('.field-remark');
        var btn_del = container.find('.deldlg-btn');
        var del_status = container.find('.dlg-status');
        var deleting_obj = null;
        var callback = null;

        function _init() {
            btn_del.on('click', delete_clicked);
        }

        function delete_clicked() {
            del_status.text('正在删除...').show();
            var options = {
                url: 'api/user/delete.do',
                data: {
                    username: deleting_obj.username
                },
                dataType: 'json',
                type: 'post',
                success: delete_ajax_cb
            };
            $.ajax(options);
            // setTimeout(delete_ajax_cb, 1000);
        }

        function delete_ajax_cb(data) {
            container.modal('hide');
            if (callback) {
                callback(deleting_obj)
            }
        }

        this.del = function (cb, user) {
            username.text(user.username);
            remark.text(user.remark);
            deleting_obj = user;
            callback = cb;
            del_status.hide();
            container.modal();
        };

        _init();
    }

    return UserDel;
});
