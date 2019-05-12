app.controller("contentController",function($scope,contentService){
	$scope.contentList = [];
	// 根据分类ID查询广告的方法:
	$scope.findByCategoryId = function(categoryId){
		contentService.findByCategoryId(categoryId).success(function(response){
			$scope.contentList[categoryId] = response;
		});
	}
	
	//搜索  （传递参数）
	$scope.search=function(){
		location.href="http://localhost:9004/search.html#?keywords="+$scope.keywords;
	}


// 根据父ID查询分类
    $scope.findByParentId =function(parentId){

        contentService.findByParentId(parentId).success(
        	function(response){
            $scope.list=response;
        });
    }

    $scope.findByParentId2 =function(parentId){
        contentService.findByParentId(parentId).success(

            function(response){

                $scope.list2=response;
                console.log($scope.list2);
            });
    }

    $scope.findByParentId3 =function(parentId){
        contentService.findByParentId(parentId).success(

            function(response){
                $scope.list3=response;
                console.log($scope.list3);
            });
    }
});