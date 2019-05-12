//服务层
app.service('SeckillService',function($http){

	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seckill/seckillorder.do?page='+page+"&rows="+rows, searchEntity);
	}    	
});
