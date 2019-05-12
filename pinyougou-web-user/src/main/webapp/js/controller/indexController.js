//首页控制器
app.controller('indexController',function($scope,loginService,seckillService){
	$scope.showName=function(){
			loginService.showName().success(
					function(response){
						$scope.loginName=response.loginName;
					}
			);
	}

    $scope.findSeckillOrder=function(){
        seckillService.findSeckillOrder().success(
            function(response){
                $scope.seckill=response;
            }
        );
    }
    $scope.delSeckill=function(seckillId){
        seckillService. delSeckill(seckillId).success(
            function(response){
                if(response.flag){
                    // 删除成功
                    alert(response.message);
                    findSeckillOrder();
                }else{
                    // 删除失败
                    alert(response.message);
                }
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