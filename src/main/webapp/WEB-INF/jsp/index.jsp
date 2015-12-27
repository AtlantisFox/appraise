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
        <h1 class="page-header">Dashboard</h1>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-3 col-md-6">
        <div class="panel panel-green">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-th-list fa-5x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge">2</div>
                <div>Ongoing Survey</div>
              </div>
            </div>
          </div>
          <a href="plans">
            <div class="panel-footer">
              <span class="pull-left">View Details</span>
              <span class="pull-right">
                <i class="fa fa-arrow-circle-right"></i>
              </span>

              <div class="clearfix"></div>
            </div>
          </a>
        </div>
      </div>

      <div class="col-lg-3 col-md-6">
        <div class="panel panel-primary">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-user fa-5x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge">26</div>
                <div>Registered Users</div>
              </div>
            </div>
          </div>
          <a href="users">
            <div class="panel-footer">
              <span class="pull-left">View Details</span>
              <span class="pull-right">
                <i class="fa fa-arrow-circle-right"></i>
              </span>

              <div class="clearfix"></div>
            </div>
          </a>
        </div>
      </div>

      <div class="col-lg-3 col-md-6">
        <div class="panel panel-yellow">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3">
                <i class="fa fa-road fa-5x"></i>
              </div>
              <div class="col-xs-9 text-right">
                <div class="huge">2</div>
                <div>Available Indexes</div>
              </div>
            </div>
          </div>
          <a href="indexes">
            <div class="panel-footer">
              <span class="pull-left">View Details</span>
              <span class="pull-right">
                <i class="fa fa-arrow-circle-right"></i>
              </span>

              <div class="clearfix"></div>
            </div>
          </a>
        </div>
      </div>
    </div>
  </div>
</div>

</body>

</html>

