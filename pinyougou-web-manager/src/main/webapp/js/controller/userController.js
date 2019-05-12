app.controller("userController",function($scope,$controller,$http,userService) {
    // AngularJS中的继承:伪继承
    $controller('baseController', {$scope: $scope});

    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(page,rows){
        // 向后台发送请求获取数据:
        userService.search(page,rows).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            alert("123");
            alert(response.rows[0].role.roleDesc);
            alert(response.rows[2].role.roleDesc);
            $scope.list = response.rows;
        });
    }
    $scope.UID =null;
    $scope.findRoleList = function (uid) {
        $scope.UID = uid;
        userService.findRoleList().success(function (response) {
            $scope.roleList = response;
        });
    }

    
    $scope.save = function (roleId) {
        userService.save($scope.UID,roleId).success(function (response) {
            if (response.flag ){
                alert(response.message);
                $scope.reloadList();
            }else {
                alert(response.message);
            }
        });
    }

});
