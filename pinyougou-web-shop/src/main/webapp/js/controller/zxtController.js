 //控制层 
app.controller('zxtController' ,function($scope,$controller,zxtService){
	
	$controller('baseController',{$scope:$scope});//继承

	//商品折线图查询
    $scope.findZxt = function(){
        zxtService.findZxt().success(function(response){
            $scope.name=response.name;
            $scope.count=response.list;
            $('#container').highcharts({
                chart:{
                    type:'line'
                },
                title: {
                    text: '商家后台-销售折线图',
                    x: -20
                },
                subtitle: {
                    text: '数据来源: 品优购中心',
                    x: -20
                },
                xAxis: {
                    categories: $scope.name
                },
                yAxis: {
                    title: {
                        text: '数量 (个)'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }],

                },
                tooltip: {
                    //valueSuffix: '个'
                    enable:false,
                    formatter:function () {
                        return '<b>'+this.series.name+'</b>'+':'+this.x+':'+this.y+'个';
                    }
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    name: '商品',
                    data: $scope.count
                }]
            })
        });
    }
});	
