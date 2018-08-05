<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <@table.table  width="100%" sortable="true" id="listTable">
        ${extraHTML?if_exists}
        <@table.thead>
            <@table.selectAllTd id="adminClassId"/>
            <@table.sortTd name="attr.code" id="adminClass.code"/>
            <@table.sortTd name="attr.infoname" id="adminClass.name"/>
            <@table.sortTd text="所在年级" id="adminClass.enrollYear"/>
            <@table.sortTd name="entity.speciality" id="adminClass.speciality.name"/>
            <@table.sortTd name="entity.specialityAspect" id="adminClass.aspect.name"/>
            <@table.sortTd name="entity.studentType" id="adminClass.stdType.name" />
            <@table.sortTd text="在校人数" id="adminClass.actualStdCount"/>
            <@table.sortTd text="学籍有效人数" id="adminClass.stdCount"/>
        </@>
        <@table.tbody datas=adminClasses;adminClass>
            <@table.selectTd id="adminClassId" value=adminClass.id/>
            <td>${adminClass.code}</td>
            <td>${adminClass.name}</td>
            <td>${adminClass.enrollYear}</td>
            <td><@i18nName adminClass.speciality?if_exists/></td>
            <td><@i18nName adminClass.aspect?if_exists/></td>
            <td><@i18nName adminClass.stdType/></td>
            <td>${adminClass.actualStdCount}</td>
            <td>${adminClass.stdCount}</td>
        </@>
    </@>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="task.calendar.id" value="${RequestParameters["task.calendar.id"]}"/>
        <input type="hidden" name="params" value="<#list RequestParameters?keys as key><#if !key?contains("method")>&${key}=${RequestParameters[key]}</#if></#list>"/>
    </form>
    <script>
        var bar = new ToolBar("bar", "班级列表", null, true, false);
        bar.setMessage('<@getMessage/>');
        bar.addItem("班级任务核对", "info()", "detail.gif");
        
        var form = document.actionForm;
        
        function info() {
            submitId(form, "adminClassId", true, "teachTaskCheck.do?method=info");
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>