//定义处理器
app.controller("brandController",function($scope,$http,$controller,brandService){
    //继承某个Controller
    $controller("baseController",{$scope:$scope});
    $scope.findAll=function(){
        brandService.findAll().success(function(response){
            $scope.list=response;
        }).error(function(){
            alert("获取数据失败");
        });
    };

    //根据页号和页大小查询数据列表
    $scope.findPage = function (page, rows) {
        brandService.findPage().success(function (response) {
            //response是一个我们自己定义的PageResult
            $scope.list = response.rows;
            //更新总记录数
            $scope.paginationConf.totalItems = response.total;

        });
    };
    $scope.searchEntity={ };
    //根据页号和页大小模糊查询数据列表
    $scope.search = function (page, rows) {
        brandService.search(page,rows,$scope.searchEntity).success(function (response) {
            //response是一个我们自己定义的PageResult
            $scope.list = response.rows;
            //更新总记录数
            $scope.paginationConf.totalItems = response.total;
        });
    };
    //新增品牌,修改
    $scope.save=function(){
        var obj;
        if($scope.entity.id!=null){
            obj=brandService.update($scope.entity);
        }else{
            obj=brandService.add($scope.entity);
        }
        obj.success(function(result){
            if(result.success){
                //新增,修改 成功
                $scope.reloadList();
            }else{
                //新增.修改 失败
                alert(result.massage);
            }
        });
    };
    //查询一条数据
    $scope.findOne=function(id){
        brandService.findOne(id).success(function(data){
            //修改成功
            $scope.entity = data;
        });
    };

    //删除方法
    $scope.delete=function(){
        //判断是否选中
        if($scope.selectedIds==0){
            alert("请先选择要删除的记录");
            return;
        }
        if(confirm("真的要删除吗?")){
            brandService.delete($scope.selectedIds).success(function(data){
                if(data.success){
                    //删除成功
                    $scope.reloadList();
                }else{
                    alert("删除失败");
                }
            });
        };
    };
});