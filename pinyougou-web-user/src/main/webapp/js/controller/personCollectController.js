
app.controller('personCollectController',function($scope,personCollectService){
    $scope.showCollect=function(){
        personCollectService.showCollect().success(
            function(response){
                $scope.list=response;
            }
        );
    }
});