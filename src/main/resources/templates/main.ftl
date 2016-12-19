<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <link rel="stylesheet" href="/static/css/bootstrap-ubuntu.min.css" />
  <link rel="stylesheet" href="/static/css/font-awesome.min.css" />
  <link rel="shortcut icon" href="/static/favicon.ico" />
  <script src="/static/js/angular.min.js"></script>
  <title>URL Shortener</title>
</head>

<body ng-app="app">
  <nav class="navbar navbar-default">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand" href="#">URL Shortener</a>
      </div>
    </div>
  </nav>

  <div>
    <div class="container" ng-controller="UrlController" ng-include="'/static/views/urls.html'">
  </div>

  <script src="/static/js/ui-bootstrap-tpls-0.13.4.min.js"></script>
  <script src="/static/js/app.js"></script>
</body>
</html>
