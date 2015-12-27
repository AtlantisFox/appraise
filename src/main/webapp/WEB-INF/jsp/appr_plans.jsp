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
        <h1 class="page-header">考评管理</h1>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="dataTable_wrapper">
          <table id="dataTables-plans" class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
              <th>名称</th>
              <th>备注</th>
              <th>截至时间</th>
              <th><!-- 考评/查看 --></th>
              <th><!-- 未完成？ --></th>
            </tr>
            </thead>
          </table>
        </div>
      </div>
    </div>  <!-- Table content -->
  </div>
</div>

<script type="text/javascript" src="js/require.js"></script>
<script type="application/javascript">
  requirejs(['./js/common'], function() {
    requirejs(['./appr_plans']);
  });
</script>
</body>

</html>
