<%@ page isELIgnored="false" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>绩效考评</title>
  <link href="css/common.css" rel="stylesheet" type="text/css">
</head>

<body>

<div id="wrapper">

  <!-- Navigation -->
  <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">SB Admin v2.0</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">
      <li><i class="fa fa-user fa-fw"></i> 您好：${username}</li>
      <li><a href="#"><i class="fa fa-gear fa-fw"></i> 用户设置</a></li>
      <li><a href="logout"><i class="fa fa-sign-out fa-fw"></i> 登出</a></li>
    </ul>
    <!-- /.navbar-top-links -->

    <div class="navbar-default sidebar" role="navigation">
      <div class="sidebar-nav navbar-collapse">
        <ul class="nav" id="side-menu">
          <li><a href="index"><i class="fa fa-dashboard fa-fw"></i> 主页</a></li>
          <li><a href="appr_plans"><i class="fa fa-bolt"></i> 进行评分</a></li>
          <li><a href="indexes"><i class="fa fa-unlock"></i> 指标管理</a></li>
          <li><a href="users"><i class="fa fa-users"></i> 用户管理</a></li>
          <li><a href="plans"><i class="fa fa-gears"></i> 方案管理</a></li>
        </ul>
      </div>
      <!-- /.sidebar-collapse -->
    </div>
    <!-- /.navbar-static-side -->
  </nav>

  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">指标管理</h1>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="dataTable_wrapper">
          <table id="dataTables-indexes" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
              <th>名称</th>
              <th>备注</th>
              <th>分值</th>
              <th>考核部门</th>
              <th>被考核部门</th>
              <th><!-- 修改/已使用按钮 --></th>
              <th><!-- 删除按钮 --></th>
            </tr>
            </thead>
          </table>
        </div>
      </div>
    </div>  <!-- Table content -->

    <div class="modal fade" id="index-edit-dlg" tabindex="-1" role="dialog" aria-labelledby="index-edit-label"
         aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="index-edit-label">指标信息</h4>
          </div>  <!-- modal-header -->
          <div class="modal-body">
            <div class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-3 control-label" for="index-name">名称</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control field-name" id="index-name">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-3 control-label" for="index-remark">备注</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control field-remark" id="index-remark">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-3 control-label" for="index-point">分值</label>
                <div class="col-sm-4">
                  <input type="number" class="form-control field-point" id="index-point">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-3 control-label" for="index-appraiser">考核部门</label>
                <div class="col-sm-9">
                  <select class="form-control field-appraiser" id="index-appraiser"></select>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-3 control-label" for="index-appraisee">被考核部门</label>
                <div class="col-sm-9">
                  <select class="form-control field-appraisee" id="index-appraisee"></select>
                </div>
              </div>
            </div>
          </div>  <!-- modal-body -->
          <div class="modal-footer">
            <button type="button" class="btn btn-primary index-edit-save">保存</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <div class="dlg-status"></div>
          </div>
        </div>
      </div>
    </div>  <!-- modal dialog: index-edit -->

    <div class="modal fade" id="index-del-dlg" tabindex="-1" role="dialog" aria-labelledby="index-del-label"
         aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="index-del-label">确认删除？</h4>
          </div>  <!-- modal-header -->
          <div class="modal-body">
            <div class="form-horizontal">
              <div class="form-group has-feedback">
                <label class="col-sm-2 control-label" for="deldlg-name">指标</label>
                <div class="col-sm-10">
                  <p class="form-control-static field-name" id="deldlg-name"></p>
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label" for="deldlg-remark">备注</label>
                <div class="col-sm-10">
                  <p class="form-control-static field-remark" id="deldlg-remark"></p>
                </div>
              </div>
            </div>
          </div>  <!-- modal-body -->
          <div class="modal-footer">
            <button type="button" class="btn btn-danger dlg-btn-confirm">删除</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <div class="dlg-status"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" src="js/require.js"></script>
<script type="application/javascript">
  requirejs(['./js/common'], function() {
    requirejs(['./indexes']);
  });
</script>
</body>

</html>
