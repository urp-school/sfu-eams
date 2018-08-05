<#include "/templates/head.ftl"/>
<body >
 <table id="gradeAchivementSettingListBar"></table>
  <@table.table id="listTable" style="width:100%" sortable="true">
  <@table.thead>
    <@table.selectAllTd id="settingId" width="5%"/>
    <@table.sortTd text="学年度" id="gradeAchivementSetting.toSemester.year" style="width:25%"/>
    <@table.sortTd text="名称" id="gradeAchivementSetting.name" style="width:25%"/>
    <@table.sortTd text="测评年级" id="gradeAchivementSetting.grades" style="width:25%"/>
    <@table.sortTd text="是否开放" id="gradeAchivementSetting.published" style="width:20%"/>
  </@table.thead>
  <@table.tbody datas=settings;data>
	 <@table.selectTd id="settingId" value=data.id/>
	 <td>${data.toSemester.year}</td>
	 <td>${data.name}</td>
	 <td>${data.grades}</td>
     <td>${data.published?string("是","否")}</td>
  </@table.tbody>
 </@table.table>
  <form name="actionForm" target="_self" method="post" action="" onsubmit="return false;">
     <input type="hidden" name="params" value="<#list RequestParameters?keys as key><#if !key?contains("method")>&${key}=${RequestParameters[key]}</#if></#list>"/>
     <input type="hidden" name="setting.toSemester.id" value="${RequestParameters['setting.toSemester.id']!}"/>
  </form>
  <script>
     var bar = new ToolBar('gradeAchivementSettingListBar', '设置列表', null, true, false);
     bar.setMessage('<@getMessage/>');
     bar.addItem('<@bean.message key="action.add"/>', 'addSwitch()');
     bar.addItem('<@bean.message key="action.edit"/>', 'editSwitch()');
     bar.addItem('<@bean.message key="action.delete"/>', 'removeSwitch()');
     var form = document.actionForm;
     function addSwitch(){
    	form.action="gradeAchivementSetting.do?method=edit"
    	form.submit();
     }
     function editSwitch(){
    	submitId(form,"settingId",false,"?method=edit");
     }
     function removeSwitch(){
    	submitId(form,"settingId",true,"?method=remove");
     }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 
