/*
* 此js文件主要完成权限的过滤
*   因为，在访问系统详情界面时，普通用户和管理员用户详情页的内容展示是不同的。
*   对于普通用户而言，访问系统详情页只能进行信息的查询的功能，而对信息进行操作的功能是没有的。
*   而对于管理员，访问系统详情界面时，不仅具有信息查看功能，还具有数据操作的功能，如信息的修改、添加、删除等。
* 所以此JS文件最后的实现效果是：
*   在用户访问详情页的时候对其身份进行校验，若是普通用户，则隐藏详情页内的操作按钮，如：修改、添加、删除。只保留信息的刷新按钮。
*   而若是管理员访问详情页面，则将所有的功能性操作显示出来。
* */

/*
$.ready(function () {
    // 页面加载完成后执行下面定义的内容
    $.post("/user/getUser", {}, function (result) {
        if(result.roleId ==2){
            // 访问用户是普通用户
            $(".permission_block").style.display = "none";
        }else if (result.roleId ==1) {
            // 访问用户是管理员

        }else {
            // 当前用户登录状态已失效,提示用户，并跳转到登录页面

        }
    }, "json");
});*/

function getUser() {
    // 页面加载完成后执行下面定义的内容
    $.post("/user/getUser", {"key": "key"}, function (result) {
        if (result.roleId == 2) {
            // 访问用户是普通用户,移除最后一列
            cols.pop();
        } else if (result.roleId == 1) {
            // 访问用户是管理员,展示功能性按钮
            $(".permission_block").show();

        } else {
            // 当前用户登录状态已失效,提示用户，并跳转到登录页面
            window.location.href = "/login";
        }
    }, "json");
}
