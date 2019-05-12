//服务层
app.service('personCollectService',function($http){

    //查询实体
    this.showCollect=function(){
        return $http.get('../collect/showCollect.do?');
    }


});
