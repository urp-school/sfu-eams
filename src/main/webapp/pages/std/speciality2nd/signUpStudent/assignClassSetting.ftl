<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="myBar" width="100%"></table>
<@table.table id="listTable" sortable="true" width="100%">
  <@table.thead>
    <@table.selectAllTd id="specialitySettingId"/>
    <@table.sortTd  name="entity.specialityAspect" id="[0].aspect.name" style="width:30%"/>
    <@table.td text="录取" id="matriculated"/>
    <@table.sortTd text="上限" id="[0].limit"/>
    <@table.td text="对应班级[实际/计划]"/>
    <@table.td text="班级容量"/>
  </@>
  <@table.tbody datas=settings;setting,setting_index>
    <@table.selectTd id="specialitySettingId" value=setting[0].id/>
    <td><@i18nName setting[0].aspect?if_exists/></td>
    <td>${(setting[1])?default('')}</td>
    <td>${setting[0].limit}</td>
    <#assign adminClassCapacity=0>
    <td><#list adminClasses[setting_index] as adminClass><#assign adminClassCapacity=adminClassCapacity+adminClass.freeCapacity()>${adminClass.name}[${adminClass.actualStdCount?default(0)}/${adminClass.planStdCount?default(0)}人]<#if adminClass_has_next><br></#if></#list></td>
    <td>${adminClassCapacity}</td>
  </@>
</@>
<@htm.actionForm name="actionForm" action="speciality2ndSignUpStudent.do" entity="specialitySetting">
  <input type="hidden" name="signUpStd.setting.id" value="${RequestParameters['signUpStd.setting.id']}"/>
</@>
<script>
  var bar = new ToolBar("myBar","专业录取，分班列表(班级容量小于已录取人数时，不能分班)",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("开始分班","multiAction('autoAssignClass')");
  bar.addPrint("<@msg.message key="action.print"/>");
  bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
  
</script>
</body>
<#include "/templates/foot.ftl"/> 
