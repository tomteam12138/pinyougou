 //控制层 
app.controller('userController' ,function($scope,$controller   ,userService){	
	$scope.entity = {};
	//注册用户
	$scope.reg=function(){
		
		//比较两次输入的密码是否一致
		if($scope.password!=$scope.entity.password){
			alert("两次输入密码不一致，请重新输入");
			$scope.entity.password="";
			$scope.password="";
			return ;			
		}
		//新增
		userService.add($scope.entity,$scope.smscode).success(
			function(response){
				alert(response.message);
			}		
		);
	}
    
	//发送验证码
	$scope.sendCode=function(){
		if($scope.entity.phone==null || $scope.entity.phone==""){
			alert("请填写手机号码");
			return ;
		}
		
		userService.sendCode($scope.entity.phone  ).success(
			function(response){
				alert(response.message);
			}
		);		
	}
	$scope.selectAddress=function () {
		userService.selectAddress().success(
            function(response){
                $scope.list=response;
            }
        );
    }
    //删除 地址
    $scope.deleteAddress=function(id){
        //获取选中的复选框
        userService.deleteAddress(id).success(
            function(response){
                if(response.flag){
                    alert(response.message);
                    $scope.selectAddress();//刷新列表
                }else {
                    alert(response.message);
				}
            }
        );
    }

    $scope.saveAddress=function(){
        var serviceObject;//服务层对象
        if($scope.entity.id!=null){//如果有ID
            serviceObject=userService.updateAddress( $scope.entity ); //修改
        }else{
            serviceObject=userService.addAddress( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.flag){
                    //重新查询
                    $scope.selectAddress();//重新加载
                }else{
                    alert(response.message);
                }
            }
        );
    }

    // 查询一个:
    $scope.findOneAddress = function(id){
        userService.findOneAddress(id).success(function(response){

            $scope.entity = response;
        });
    }

	
});	
