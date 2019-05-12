// 定义控制器:
app.controller("seckillOrderController",function($scope,$controller,$http,seckillOrderService){
	// AngularJS中的继承:伪继承
	$controller('baseController',{$scope:$scope});
	
	// 查询所有的品牌列表的方法:
	$scope.findAll = function(){
		// 向后台发送请求:
        seckillOrderService.findAll().success(function(response){
			$scope.list = response;
		});
	}
    //搜索
    $scope.searchEntity = {};
    $scope.search=function(page,rows){
        seckillOrderService.search(page,rows,$scope.searchEntity).success(
            function(response){

                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }


	
});
