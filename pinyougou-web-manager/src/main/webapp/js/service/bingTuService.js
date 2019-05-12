app.service('bingTuService', function ($http) {

    this.findPic2 = function () {
        return $http.get('../orderList/findPic2.do');
    }
});