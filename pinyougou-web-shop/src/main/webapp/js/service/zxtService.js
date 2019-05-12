//服务层
app.service('zxtService',function($http){

	//搜索
	this.findZxt=function(){
		return $http.post('../goodsZxt/find.do');
	}
});
