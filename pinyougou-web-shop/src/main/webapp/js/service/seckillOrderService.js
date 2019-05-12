// 定义服务层:
app.service("seckillOrderService",function($http){
	this.findAll = function(){
		return $http.get("../seckillOrder/findAll.do");
	}

	this.search = function(page,rows,searchEntity){
		return $http.post("../seckillOrder/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	

});