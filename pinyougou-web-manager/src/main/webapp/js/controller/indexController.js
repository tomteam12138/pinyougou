app.controller("indexController",function($scope,loginService,orderService){
	
	$scope.showName = function(){
		loginService.showName().success(function(response){
			$scope.loginName = response.username;
			$scope.loginTime = response.logintime;
		});
	}
    $scope.total = 0;
    $scope.findOrderTotal = function(){
        orderService.findOrderTotal().success(function(response){
            $scope.total= response;
        });
    }

});