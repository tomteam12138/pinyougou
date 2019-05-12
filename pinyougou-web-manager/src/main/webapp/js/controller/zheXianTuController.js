app.controller('zheXianTuController' ,function($scope,$controller  ,zheXianTuService){

    $controller('baseController',{$scope:$scope});//继承

    $scope.findPic=function(){
        zheXianTuService.findPic().success(
            function(response){
                $scope.x=response.seller;
                $scope.y = response.num;
                    $('#container').highcharts({
                        chart: {
                            type: 'line'
                        },
                        title: {
                            text: '运营商后台，销售折线图'
                        },
                        subtitle: {
                            text: ''
                        },
                        xAxis: {
                            categories: $scope.x
                        },
                        yAxis: {
                            title: {
                                text: 'number(个)'
                            }
                        },
                        tooltip: {
                            enabled: false,
                            formatter: function() {
                                return '<b>'+ this.series.name +'</b>'+this.x +': '+ this.y +'';
                            }
                        },
                        plotOptions: {
                            line: {
                                dataLabels: {
                                    enabled: true
                                },
                                enableMouseTracking: false
                            }
                        },
                        series: [{
                            name: '销售折线图',
                            data: $scope.y
                        }]
                    });
            }
        );
    }





});