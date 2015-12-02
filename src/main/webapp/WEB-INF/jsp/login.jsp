<%@ page isELIgnored="false" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>SB Admin 2 - Bootstrap Admin Theme</title>
  <link href="css/login.css" rel="stylesheet">
</head>

<body>

  <div class="container">
    <div class="row">
      <div class="col-md-4 col-md-offset-4">
        <div class="login-panel panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Please Sign In</h3>
          </div>
          <div class="panel-body" id="form-login">
            <div class="alert alert-warning dlg-banner" style="display: none;">登陆失败，请重试。</div>
            <form role="form">
              <fieldset>
                <div class="form-group">
                  <input class="form-control field-username" placeholder="Username" name="username" type="text" value="${username}">
                </div>
                <div class="form-group">
                  <input class="form-control field-password" placeholder="Password" name="password" type="password" value="">
                </div>
                <!--
                <div class="checkbox">
                  <label>
                    <input name="remember" type="checkbox" value="Remember Me">Remember Me
                  </label>
                </div>
                -->
                <!-- Change this to a button or input when using this as a form -->
                <button type="submit" class="btn btn-lg btn-success btn-block dlg-login">Login</button>
              </fieldset>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script type="text/javascript" src="js/require.js" data-main="js/login.js"></script>

</body>

</html>
