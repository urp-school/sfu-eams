<#include "/templates/head.ftl"/>
 <body >  
 <table id="bar"></table>
 <@table.table width="100%" sortable="true" id="sortTable">
	   <@table.thead>
	     <@table.selectAllTd id="stdId"/>
	     <@table.sortTd width="10%" id="std.code" name="attr.stdNo"/>
	     <@table.sortTd width="8%" id="std.name" name="attr.personName"/>
	     <@table.sortTd width="5%" id="std.basicInfo.gender.id" name="entity.gender"/>
	     <@table.sortTd width="8%" id="std.enrollYear" name="attr.enrollTurn"/>
	     <@table.sortTd width="15%"id="std.department.name" name="entity.college"/>
	     <@table.sortTd width="15%" id="std.firstMajor.name" name="entity.speciality"/>
   	     <td width="15%" id="std.firstAspect.name"class="tableHeaderSort">方向</td>
   	     <@table.sortTd width="15%" id="tutor.name" text="指导导师"/>
	   </@>
	   <@table.tbody datas=students;std>
	    <@table.selectTd  type="checkbox" id="stdId" value="${std.id}"/>
	    <td ><A href="studentDetailByManager.do?method=detail&stdId=${std.id}" target="blank" >${std.code}</A></td>
        <td ><A href="#" onclick="if(typeof stdIdAction !='undefined') stdIdAction('${std.id}');" title="${stdNameTitle?if_exists}">${std.name} </a></td>
        <td ><@i18nName std.basicInfo?if_exists.gender?if_exists/></td>
        <td >${std.enrollYear}</td>
        <td ><@i18nName std.department/></td>
        <#if RequestParameters['majorTypeId']=="1">
        <td ><@i18nName std.firstMajor?if_exists/></td>
		<td><@i18nName std.firstAspect?if_exists/></td>
		<td><@i18nName std.teacher?if_exists/></td>
		<#else>
		<td ><@i18nName std.secondMajor?if_exists/></td>
		<td><@i18nName std.secondAspect?if_exists/></td>
		<td><@i18nName std.tutor?if_exists/></td>
		</#if>
	   </@>
     </@>
 <@htm.actionForm name="actionForm" action="instruction.do" entity="std">
   <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
   <input type="hidden" name="majorTypeId" value="${RequestParameters['majorTypeId']}"/>
   <input type="hidden" name="isGraduation" value="0"/>
 </@>
 <script>
   var bar = new ToolBar("bar","没有指导信息的学生列表",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("根据导师指定为毕业指导","addInput(document.actionForm,'isGraduation','1');multiAction('genInstructionByTutor')");
   bar.addItem("根据导师指定为平时指导","addInput(document.actionForm,'isGraduation','0');multiAction('genInstructionByTutor')");
 </script>
 </body>   
<#include "/templates/foot.ftl"/> 