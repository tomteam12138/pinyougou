app.service('zheXianTuService', function ($http) {

    //读取列表数据绑定到表单中
    this.findPic = function () {
        return $http.get('../orderList/findPic.do');
    }
});