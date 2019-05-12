// 定义服务层:
app.service("orderService",function($http){

	// this.search = function(page,rows,searchEntity){
	// 	return $http.post("../order/search.do?pageNum="+page+"&pageSize="+rows,searchEntity);
	// }
    this.searchOrder = function(){
        return $http.post("../order/searchOrder.do");
    }
    this.findOrderTotal = function(){
        return $http.post("../order/findOrderTotal.do");
    }

});