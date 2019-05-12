<<<<<<< Updated upstream
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
=======
//控制层
app.controller('userController' ,function($scope,$controller   ,userService){

    $controller('baseController',{$scope:$scope});//继承

    $scope.findTotal=function(){
        userService.findTotal().success(
            function(response){
                $scope.total=response;
            }
        );
    }
    //读取列表数据绑定到表单中

    $scope.findAll=function(){
        userService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }


    //分页
    $scope.findPage=function(page,rows){
        userService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne=function(id){
        userService.findOne(id).success(
            function(response){
                $scope.entity= response;
            }
        );
    }

    //保存
    $scope.save=function(){
        var serviceObject;//服务层对象
        if($scope.entity.id!=null){//如果有ID
            serviceObject=userService.update( $scope.entity ); //修改
        }else{
            serviceObject=userService.add( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.flag){
                    //重新查询
                    $scope.reloadList();//重新加载
                }else{
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        userService.dele( $scope.selectIds ).success(
            function(response){
                if(response.flag){
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        userService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
            //    $scope.total=response.total;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    $scope.updateStatus = function(userId,status){
        userService.updateStatus(userId,status).success(function(response){
            if(response.flag){
                //重新查询
                $scope.reloadList();//重新加载
            }else{
>>>>>>> Stashed changes
                alert(response.message);
            }
        });
    }
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
});
