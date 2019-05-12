// 定义控制器:
app.controller("orderController",function($scope,$controller,$http,orderService){
	// AngularJS中的继承:伪继承
	$controller('baseController',{$scope:$scope});
	$scope.searchEntity={};
	
	//假设定义一个查询的实体：searchEntity
	// $scope.search = function(page,rows){
	// 	// 向后台发送请求获取数据:
	// 	orderService.search(page,rows,$scope.searchEntity).success(function(response){
	// 		$scope.paginationConf.totalItems = response.total;
	// 		$scope.list = response.rows;
	// 	});
	// }
    $scope.searchOrder = function(){
        // 向后台发送请求获取数据:
        orderService.searchOrder().success(function(response){
            $scope.list = response;
        });
    }
    // $scope.findOrderTotal = function(){
    //     // 向后台发送请求获取数据:
    //     orderService.findOrderTotal().success(function(response){
    //         $scope.num = response;
    //     });
    // }
});
