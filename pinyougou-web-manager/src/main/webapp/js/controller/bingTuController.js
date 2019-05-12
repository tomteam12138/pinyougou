app.controller('bingTuController' ,function($scope,$controller  ,bingTuService){

    $controller('baseController',{$scope:$scope});//继承

    $scope.findPic2=function(){
        bingTuService.findPic2().success(
            function(response){
                $scope.list=response;
                $('#container2').highcharts({
                    chart: {
                        plotBackgroundColor: null,
                        plotBorderWidth: null,
                        plotShadow: false
                    },
                    title: {
                        text: '运营商销售饼状图(下拉折线图)'
                    },
                    tooltip: {
                        headerFormat: '{series.name}<br>',
                        pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: false
                            },
                            showInLegend: true
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: '',
                        data: $scope.list
                    }]
                });
            }
        );
    }
});

