app.service('brandService',function($http){
    //新增品牌
    this.save=function(entity){
        return $http.post('../brand/save.do',entity);
    }
});