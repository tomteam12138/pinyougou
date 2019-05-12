// 定义服务层:
app.service("userService",function($http){

    this.search = function(page,rows){
        return $http.post("../user/findAll.do?pageNum="+page+"&pageSize="+rows);
    }

    this.findRoleList = function () {
        return $http.get("../user/findRoleList.do")
    }

    this.save = function (userId,roleId) {
        return $http.post("../user/save.do?userId="+userId+"&roleId="+roleId)
    }

});