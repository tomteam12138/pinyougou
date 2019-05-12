app.controller('brandController' ,function($scope,$controller,$location ,brandService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中
    $scope.save=function(){
        brandService.save($scope.entity).success(
            function(response){
                if(response.flag){
                    //重新查询
                    alert(response.message);
                    location.href="brand.html";
                }else{
                    alert(response.message);
                }
            }
        );
    }
});