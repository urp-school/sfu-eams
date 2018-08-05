<#include "/templates/head.ftl"/>
<BODY>
<table id="myBar"></table>
<table align="center" border="0" width="90%">
 <tr>
 <td colspan="3">
 <div align="center"><@i18nName calendar.studentType/> ${calendar.year} ${calendar.term} <@i18nName examType/> 应考学生最少场次统计表</div>
	<@table.table id="table1" align="center" width="100%">
	  <@table.thead>
	    <@table.td text="序号"/>
	    <@table.td  width="40%" text="所需最少考试场次"/>
	    <@table.td text="人数"/>
	  </@>
	  <@table.tbody datas=takeOfTurn; state,state_index>
	    <td>${state_index+1}</td>
	    <td>${state[0]}</td>
	    <td>${state[1]}</td>
	  </@>
	</@>
 </td>
 </tr>
 <tr>
  <td colspan="3">
    <div align="center"><@i18nName calendar.studentType/> ${calendar.year} ${calendar.term} <@i18nName examType/>应考学生按课程统计表</div>
  </td>
 </tr>
 <tr>
   <#list takeOfCourse?chunk((takeOfCourse?size/3)?int+1) as subTakeOfCourse>
    <td valign="top" width="33%">
	<@table.table id="table2" align="center" width="100%">
	  <@table.thead>
	    <@table.td text="序号" width="10%"/>
	    <@table.td name="attr.courseName"/>
	    <@table.td text="人数"/>
	  </@>
	  <@table.tbody datas=subTakeOfCourse; state,state_index>
	    <td>${state_index+1}</td>
	    <td><@i18nName state[0]/></td>
	    <td>${state[1]}</td>
	  </@>
	</@>
	</td>
   </#list>
   </tr>
  </table>
  <script>
    var bar = new ToolBar("myBar","应考学生统计",null,true,true);
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addClose();
  </script>
</body>
<#include "/templates/foot.ftl"/> 