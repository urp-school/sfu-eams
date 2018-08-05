<#include "/templates/head.ftl"/>
<script src="dwr/interface/examActivityService.js"></script>
<script src='dwr/interface/teacherDAO.js'></script>
<body>
    <table id="bar"></table>
    <#assign groupAdminClasses = taskGroup.adminClasses/>
    <#assign beenAdminClassIds><#list groupAdminClasses as adminClass>,${adminClass.id}</#list>,</#assign>
    <#assign beenAdminClassNames><#list groupAdminClasses as adminClass>${adminClass.name}<#if adminClass_has_next>, </#if></#list></#assign>
    <table class="formTable" width="100%">
        <tr>
            <td class="darkColumn" style="text-align:center;font-weight:bold">添加班级</td>
        </tr>
    </table>
    <table class="formTable" width="100%">
        <form method="post" action="" name="actionForm" onsubmit="return false;">
            <input type="hidden" name="taskGroup.id" value="${taskGroup.id}"/>
        <tr>
            <td class="title" width="20%">已有/选班级(名称)：</td>
            <td id="adminClassNames">${beenAdminClassNames}</td>
            <input type="hidden" name="adminClassIds" value="${beenAdminClassIds}"/>
        </tr>
        <tr>
            <td class="title">班级的操作：</td>
            <td><button onclick="addAdminClass()">添加</button>&nbsp;<button onclick="resetAdminClass()">还原</button></td>
        </tr>
        </form>
    </table>
    <table class="formTable" width="100%">
        <tr>
            <td class="darkColumn" align="center"><button onclick="save()">保存</button></td>
        </tr>
    </table>
    <table width="100%" cellspacing="0" cellpadding="0">
        <tr>
            <td><iframe name="pageIframe" src="#" width="100%" height="100%" frameborder="0" scrolling="no"></iframe></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "[${taskGroup.name}]排课组添加班级", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("保存", "save()");
        bar.addItem("返回", "parent.taskGroupListFrame.getGroupInfo(${taskGroup.id})","backward.gif");
        
        var form = document.actionForm;
        var isChange = false;
        
        function addAdminClass() {
            // 正在加载列表中
            if (null == pageIframe.document.groupActionForm) {
                alert("正在打开班级列表中，请稍候。");
                return;
            }
            // 选择的
            var ids = getCheckBoxValue(pageIframe.document.getElementsByName("adminClassId"));
            if (null == ids || "" == ids) {
                alert("请在下面的列表中，选择要添加的班级。");
                return;
            }
            ids = "," + ids + ",";
            // 组内的
            var adminClassIds = form["adminClassIds"].value;
            var hasBeen = false;
            var hasAdminClass = "";
            var adminClassValues = "";
            <#list adminClasses as adminClass>
            if (ids.indexOf(",${adminClass.id},") >= 0) {
                if (adminClassIds.indexOf(",${adminClass.id},") < 0) {
                    adminClassValues += ("" == adminClassValues ? "" : ", ") + "${adminClass.name}";
		            isChange = true;
                }
                if (form["adminClassIds"].value.indexOf(",${adminClass.id},") >= 0) {
                    hasBeen = true;
                    hasAdminClass += "${adminClass.name} ";
                }
            }
            </#list>
            if (hasBeen && !confirm ("当前选择的班级中，" + hasAdminClass.trim().replace(new RegExp(" ", "gm"), ", ") + "已经被添加或已经在组内了，这些班级将不再被过滤。\n\n是否要继续？")) {
                return;
            }
            form["adminClassIds"].value += ids;
            $("adminClassNames").innerHTML += ("" == $("adminClassNames").innerHTML ? "" : ", ") + adminClassValues;
        }
        
        function resetAdminClass() {
            form['adminClassIds'].value = "${beenAdminClassIds}";
            $('adminClassNames').innerHTML = "${beenAdminClassNames}";
            isChange = false;
        }
        
        resetAdminClass();
        
        function save() {
            var ddd = $("adminClassNames").innerHTML.match(new RegExp(",", "gi"));
            var adminClassCount = (((ddd == null) ? 0 : ddd.length) + 1) - ${groupAdminClasses?size};
            if (adminClassCount == 0 || isChange == false) {
                alert("当前没有添加班级，无需保存。");
                return;
            }
            if (confirm("[${taskGroup.name}]排课组添加了" + adminClassCount + "个班级，保存后将在组内的每个任务中增加" + adminClassCount + "个班级。\n\n确定要保存吗？")) {
                form.action = "taskGroup.do?method=saveAdminClassGroup";
                form.target = "_parent";
                form.submit();
            }
        }
        
        function displayAdminClasses() {
            form.action = "taskGroup.do?method=adminClassSearch";
            form.target = "pageIframe";
            $("message").innerHTML = "正在显示班级列表中，请稍候...";
            $("message").style.display = "block";
            form.submit();
        }
        
        displayAdminClasses();
    </script>
    <script language="JavaScript" type="text/JavaScript" src="scripts/course/DepartTeacher.js"></script>
</body>
<#include "/templates/foot.ftl"/>