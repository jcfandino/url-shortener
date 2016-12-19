var app = angular.module("app", ['ui.bootstrap']);

app.controller({ UrlController: function($scope, $http) {
    var ctx = { shortUrl: null, longUrl: null, error: null };
    $scope.ctx = ctx;
    ctx.create = function() {
        $http.post("/", ctx.longUrl)
            .success(function(data, status, headers) {
                ctx.error = null;
                ctx.shortUrl = headers('Location');
            })
            .error(function(data, status) {
                ctx.shortUrl = null;
                ctx.error = "Failed to save URL: " + data;
            })
    }
}});
