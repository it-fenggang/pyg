app.controller("searchController", function ($scope, $location, searchService) {

    //定义搜索条件
    $scope.searchMap = {"keywords":"", "category":"", "brand":"", "spec":{}, "price":"", "pageNo":1, "rows":20, "sortField":"", "sort":""};

    $scope.search = function () {

        searchService.search($scope.searchMap).success(function (response) {
            //商品列表
            $scope.resultMap = response;

            //构建分页导航条
            buildPageInfo();

        });
    };

    //添加过滤条件
    $scope.addSearchItem = function (key, value) {

        if("category"==key || "brand"==key || "price"==key){
            $scope.searchMap[key] = value;
        } else {
            //是规格的话；
            $scope.searchMap.spec[key] = value;
        }

        //只要前端有过滤条件修改；则需要根据新的过滤条件查询数据
        $scope.search();
    };

    //删除过滤条件
    $scope.removeSearchItem = function (key) {

        if("category"==key || "brand"==key || "price"==key){
            $scope.searchMap[key] = "";
        } else {
            //是规格的话；
            delete $scope.searchMap.spec[key];
        }

        //只要前端有过滤条件修改；则需要根据新的过滤条件查询数据
        $scope.search();
    };

    //构造分页导航条
    buildPageInfo = function () {
        //在分页导航条中要显示的总页号数
        var showPageNo = 5;
        //要显示的页号集合
        $scope.pageNoList = [];

        //与当前页号左右间隔数
        var interval = Math.floor(showPageNo/2);

        //起始要实现的页号
        var startPageNo = 1;
        //结束要实现的页号
        var endPageNo = $scope.resultMap.totalPages;

        if($scope.resultMap.totalPages > showPageNo){

            startPageNo = parseInt($scope.searchMap.pageNo) - interval;
            endPageNo = parseInt($scope.searchMap.pageNo) + interval;

            //判断是否结束页号大于总页数
            if(endPageNo > $scope.resultMap.totalPages){
                startPageNo = startPageNo - (endPageNo - $scope.resultMap.totalPages);
                endPageNo = $scope.resultMap.totalPages;
            }
            if(startPageNo < 1){
                startPageNo = 1;
                endPageNo = showPageNo;
            }
        }
        
        //前面3个点
        $scope.frontDot = false;
        if(1 < startPageNo){
            $scope.frontDot = true;
        }

        //后面3个点
        $scope.backDot = false;
        if(endPageNo < $scope.resultMap.totalPages){
            $scope.backDot = true;
        }

        //遍历从起始页号到结束页号的编号到集合中
        for (var i = startPageNo; i <= endPageNo; i++) {
            $scope.pageNoList.push(i);
        }

    };

    //判断是否当前页号
    $scope.isCurrentPage = function (pageNo) {
        return $scope.searchMap.pageNo == pageNo;
    };

    //根据页号查询
    $scope.queryByPage = function (pageNo) {
        if(0 < pageNo && pageNo <= $scope.resultMap.totalPages){
            $scope.searchMap.pageNo = pageNo;

            $scope.search();
        }

    };

    //下一页
    $scope.nextPage = function () {
        $scope.searchMap.pageNo = parseInt($scope.searchMap.pageNo) + 1;

        $scope.search();

    };

    //添加排序域
    $scope.addSortField = function (sortField, sort) {
        $scope.searchMap.sortField = sortField;

        $scope.searchMap.sort = sort;

        $scope.search();
    };

    //加载地址中的搜索关键字
    $scope.loadKeywords = function () {
        //获取搜索关键字
        $scope.searchMap.keywords = $location.search()["keywords"];

        //搜索
        $scope.search();

    };
});