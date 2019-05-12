//服务层
app.service('typeTemplateCheckService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../typeTemplateCheck/findAll.do');
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../typeTemplateCheck/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../typeTemplateCheck/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../typeTemplateCheck/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../typeTemplateCheck/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../typeTemplateCheck/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../typeTemplateCheck/search.do?page='+page+"&rows="+rows, searchEntity);
	}    
	
	this.updateStatus = function(ids,status){
		return $http.get('../typeTemplateCheck/updateStatus.do?ids='+ids+"&status="+status);
	}
});
