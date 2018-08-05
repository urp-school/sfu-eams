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
   	     <@table.sortTd width="15%" id="instruction.tutor.name" text="导师姓名"/>
   	     <@table.sortTd width="15%" id="instruction.isGraduation" text="指导类别"/>
	   </@>
	   <@table.tbody datas=instructions;instruction>
	    <@table.selectTd  type="checkbox" id="instructionId" value="${instruction.id}"/>
	    <td><A href="studentDetailByManager.do?method=detail&stdId=${instruction.std.id}" target="blank" >${instruction.std.code}</A></td>
        <td><A href="#" onclick="if(typeof stdIdAction !='undefined') stdIdAction('${instruction.std.id}');" title="${stdNameTitle?if_exists}">${instruction.std.name} </a></td>
        <td><@i18nName instruction.std.basicInfo?if_exists.gender?if_exists/></td>
        <td>${instruction.std.enrollYear}</td>
        <td><@i18nName instruction.std.department/></td>
        <td><@i18nName instruction.std.firstMajor?if_exists/></td>
		<td><@i18nName instruction.tutor/></td>
		<td>${instruction.isGraduation?default(true)?string("毕业指导","平时指导")}</td> 
	   </@>
     </@>
 <@htm.actionForm name="actionForm" action="instruction.do" entity="instruction"/>
 <script>
   var bar = new ToolBar("bar","已经指定该学期指导关系的学生列表",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("删除","remove()");
 </script>
 </body>
<#include "/templates/foot.ftl"/> 