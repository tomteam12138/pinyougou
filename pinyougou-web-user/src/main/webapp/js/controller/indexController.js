//首页控制器
app.controller('indexController',function($scope,loginService){
	$scope.showName=function(){
			loginService.showName().success(
					function(response){
						$scope.loginName=response.loginName;
					}
			);
	}


    //$scope.searchEntity={};//定义搜索对象
    $scope.query=function(){
        loginService.query().success(
            function(response){
                $scope.list=response;

            }
        );
    }









});