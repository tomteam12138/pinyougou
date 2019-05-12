//服务层
app.service('seckillService',function($http){
	//读取列表数据绑定到表单中
	this.findSeckillOrder=function(){
		return $http.get('../seckill/findSeckillOrder.do');
	}
    this.delSeckill=function(seckillId){
        return $http.get('../seckill/delSeckill.do?id='+seckillId);
    }
});