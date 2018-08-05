<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="backBar"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
	   <@table.thead>
	     <@table.selectAllTd id="stdId"/>
	     <@table.sortTd width="10%" id="check.std.code" name="attr.stdNo"/>
	     <@table.sortTd width="8%" id="check.std.name" name="attr.personName"/>
	     <@table.sortTd width="8%" id="check.std.enrollYear" name="attr.enrollTurn"/>
	     <#if RequestParameters['majorTypeId'] == "1">
	     <@table.sortTd width="15%"id="check.std.department.name" name="entity.college"/>
	     <@table.sortTd width="15%" id="check.std.firstMajor.name" name="entity.speciality"/>
   	     <#else>
	     <@table.sortTd width="15%"id="check.std.secondMajor.department.name" name="entity.college"/>
	     <@table.sortTd width="13%" id="check.std.secondMajor.name" name="entity.speciality"/>
   	     </#if>
   	     <@table.sortTd width="10%" id="check.credits" name="attr.credit"/>
   	     <@table.sortTd width="10%" id="check.courseNum" text="课程数"/>
   	     <@table.sortTd width="10%" id="check.isConfirm" text="核对"/>
	   </@>
	   <@table.tbody datas=courseTableChecks;check>
	    <@table.selectTd  type="checkbox" id="courseTableCheckId" value="${check.id}"/>
	    <td ><A href="studentDetailByManager.do?method=detail&stdId=${check.std.id}" target="blank" >${check.std.code}</A></td>
        <td >${check.std.name}</td>
        <td >${check.std.enrollYear}</td>
        <#if RequestParameters['majorTypeId']=="1">
        <td ><@i18nName check.std.department/></td>
        <td ><@i18nName check.std.firstMajor?if_exists/></td>
		<#else>
		<td ><@i18nName check.std.secondMajor?if_exists.department?if_exists/></td>
        <td ><@i18nName check.std.secondMajor?if_exists/></td>
		</#if>
		<td>${check.credits}</td>
		<td>${check.courseNum}</td>
		<td><#if check.isConfirm?default(false)><#if check.confirmAt?exists>${check.confirmAt?string("yyyy-MM-dd")}</#if><#else>否</#if></td>
	   </@>
     </@>
  <script language="javascript">
   	var bar = new ToolBar('backBar','课表核对信息',null,true,true);
   	bar.setMessage('<@getMessage/>');
    function info(id){
       //window.open("courseTableCheck.do?method=info&courseTableCheckId="+id);
    }
   	bar.addPrint("<@msg.message key="action.print"/>");
  </script>
  </body>
<#include "/templates/foot.ftl"/>