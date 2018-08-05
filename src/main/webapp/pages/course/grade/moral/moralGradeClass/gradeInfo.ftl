<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="myBar" width="100%"></table>
     <@table.table width="60%" id="listTable" align="center">
	   <@table.thead>
	     <@table.td align="center" width="5%" name="attr.index"/>
	     <@table.td align="center" width="12%" name="attr.stdNo"/>
	     <@table.td width="15%" name="attr.personName"/>
	     <@table.td text="德育成绩"/>
	     <@table.td text="班级"/>
	   </@>
	   <@table.tbody datas=moralGrades?sort_by(["std","code"]);grade,grade_index>
	     <td>${grade_index+1}</td>
		 <td>${grade.std.code}</td>
   	     <td>${grade.std.name}</td>
         <td>${grade.score}</td>
         <td>${(grade.std.firstMajorClass.name)?default('')}</td>
	   </@> 
     </@>
<script>
   var bar = new ToolBar("myBar","德育成绩",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addClose("<@msg.message key="action.close"/>");
</script>
 </body>
<#include "/templates/foot.ftl"/>      