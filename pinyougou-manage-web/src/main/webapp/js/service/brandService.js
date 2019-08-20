//定义服务(以后负责与后台交互)
app.service("brandService",function($http){
    this.findAll=function(){
        return $http.get("../brand/findAll.do");
    }
    //根据页号和页大小查询数据列表
    this.findPage = function (page, rows) {
        return $http.get("../brand/findPage.do?page=" + page + "&rows=" + rows);
    };
    this.add=function(entity){
        return $http.post("../brand/add.do",entity)
    };
    this.update=function(entity){
        return $http.post("../brand/update.do",entity)
    };
    //查询一条数据
    this.findOne=function(id){
        return $http.get("../brand/findOne.do?id="+id);
    };
    //删除方法
    this.delete=function(selectIds) {
        return $http.get("../brand/deleteByIds.do?ids="+selectIds);
    };
    this.search = function (page, rows,searchEntity) {
        return $http.post("../brand/search.do?page=" + page + "&rows=" + rows,searchEntity);
    };
    //查询品牌列表
    this.selectOptionList = function () {
        return $http.get("../brand/selectOptionList.do");
    };
});