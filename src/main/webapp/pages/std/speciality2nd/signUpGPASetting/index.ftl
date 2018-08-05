<#include "/templates/head.ftl"/>

 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table width="100%" border="0" id="myBar"></table>
  <@table.table id="listTable" width="100%">
   <@table.thead>
     <@table.selectAllTd id="signUpGPASettingId"/>
     <@table.td name="attr.currentLevle"/>
     <@table.td name="attr.targetLevel"/>
     <@table.td name="attr.secondSpecialityGrade"/>
   </@>
   <@table.tbody datas=signUpGPASettings?sort_by("fromRank");setting>
     <@table.selectTd id="signUpGPASettingId" value=setting.id/>
     <td width="20%">${setting.fromRank}</td>
	 <td width="25%">${setting.toRank}</td>
     <td width="15%">${setting.GPAGap?string.number}</td>
   </@>
 </@>
 </body>
<@htm.actionForm name="actionForm" action="speciality2ndSignUpGPASetting.do" entity="signUpGPASetting"></@>
 <script>
    var bar= new ToolBar("myBar","<@bean.message key="info.secondSpecialitySignUpSetting.gradeSetting"/>",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.add"/>",'add()');
    bar.addItem("<@msg.message key="action.edit"/>",'edit()');
    bar.addItem("<@msg.message key="action.delete"/>",'remove()');
    bar.addItem("返回设置","parent.search()","backward.gif");
 </script>
<#include "/templates/foot.ftl"/>