<#include "/templates/head.ftl"/>
<body>
    <#assign extraHTML>
        <tr onKeyDown="enterQuery(event)">
        <form method="post" action="taskGroup.do?method=adminClassSearch" name="groupActionForm" onsubmit="return false;">
            <input type="hidden" name="taskGroup.id" value="${(taskGroup.id)?default(RequestParameters["taskGroup.id"])}"/>
            <td><img src="${static_base}/images/action/search.png" align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/></td>
            <td><input type="text" name="adminClass.code" value="${RequestParameters["adminClass.code"]?if_exists}" style="width:100%"/></td>
            <td><input type="text" name="adminClass.name" value="${RequestParameters["adminClass.name"]?if_exists}" style="width:100%"/></td>
            <td width="20%"><@htm.i18nSelect datas=majors selected=(RequestParameters["adminClass.speciality.id"])?default("") name="adminClass.speciality.id" style="width:100%"><option value="">请选择...</option></@></td>
            <td width="20%"><@htm.i18nSelect datas=aspects selected=(RequestParameters["adminClass.aspect.id"])?default("") name="adminClass.aspect.id" style="width:100%"><option value="">请选择...</option></@></td>
            <td><@htm.i18nSelect datas=stdTypes selected=(RequestParameters["adminClass.stdType.id"])?default("") name="adminClass.stdType.id" style="width:100%"><option value="">请选择...</option></@></td>
            <td width="10%"><input type="text" name="adminClass.actualStdCount" value="${RequestParameters["adminClass.actualStdCount"]?if_exists}" style="width:100%"/></td>
            <td width="10%"><input type="text" name="adminClass.stdCount" value="${RequestParameters["adminClass.stdCount"]?if_exists}" style="width:100%"/></td>
        </form>
        </tr>
    </#assign>
    <#include "/pages/system/baseinfo/search/adminClassSearch/adminClassList.ftl"/>
    <script>
            parent.$("message").innerHTML = "";
            parent.$("message").style.display = "none";

        var form = document.groupActionForm;
        
        function query() {
            form.submit();
        }
        
        function enterQuery(event) {
            if (portableEvent(event).keyCode == 13) {
                query();
            }
        }
        
        function displayInfo(id) {
            return;
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>