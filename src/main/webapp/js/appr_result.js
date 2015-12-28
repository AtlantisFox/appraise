requirejs(['./appr_result/users', './appr_result/appraise'], function(UserList, AppraiseDlg) {
    var plan_id = parseInt(window.location.hash.substr(1)) || 0;
    var appraise_dlg = new AppraiseDlg('#appraise-dlg');
    var users = new UserList('#dataTables-users', plan_id, appraise_dlg);
});
