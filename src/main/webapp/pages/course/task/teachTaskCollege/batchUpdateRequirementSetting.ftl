<#include "/templates/head.ftl"/>
<body onload="initForm()">
    <table id="bar"></table>
    <table class="formTable" width="100%">
        <tr>
            <td class="darkColumn" colspan="4" align="center"><b>设置选项</b></td>
        </tr>
        <form method="post" action="?method=batchUpdateRequirement" name="actionForm" onsubmit="return false;">
        <@searchParams/>
        <input type="hidden" name="taskIds" value="${RequestParameters['taskIds']}"/>
        <tr>
            <td class="title" width="20%"><@msg.message key="attr.teachLangType"/>：</td>
            <td>
	            <select  name="teachLangTypeId"  style="width:100px">
	                <#list sort_byI18nName(teachLangTypes) as type>
	                   <option value=${type.id}><@i18nName type/></option>
	                </#list>
	            </select>
            </td>
        </tr>
        </form>
        <tr>
            <td class="darkColumn" colspan="4" align="center"><button accesskey="S" onclick="save()"><@msg.message key="action.save"/>(<u>S</u>)</button>　<button accesskey="R" onclick="initForm()"><@msg.message key="action.reset"/>(<u>R</u>)</button></td>
        </tr>
    </table>
    <@table.table width="100%" align="center">
        <@table.thead>
            <@table.td name="attr.courseNo"/>
            <@table.td name="entity.courseType"/>
            <@table.td name="entity.teachClass"/>
            <@table.td name="entity.teacher"/>
            <@table.td name="attr.teachLangType"/>
            <@table.td name="attr.credit"/>
        </@>
        <@table.tbody datas=tasks?sort_by("seqNo");task>
            <td>${(task.course.code)?default('')}</td>
            <td>${(task.course.name)?default('')}</td>
            <td>${(task.teachClass.name)?default('')}</td>
            <td><@getTeacherNames task.arrangeInfo.teachers/></td>
            <td><@i18nName (task.requirement.teachLangType)?if_exists/></td> 
            <td>${(task.course.credits)?default('')}</td>
        </@>
    </@>
    <script>
        var bar = new ToolBar("bar", "课程要求", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("<@msg.message key="action.save"/>", "save()");
        bar.addBack("<@msg.message key="action.back"/>");
        
        function save() {
            document.actionForm.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>