<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
   <table id="bar" width="100%"></table>
     <@table.table width="100%" >
       <@table.thead>
	     <@table.selectAllTd id="rationWorkloadId"/>
	     <@table.td name="rationWorkload.rationCn"/>
	     <@table.td name="rationWorkload.rationEn"/>
	     <@table.td name="rationWorkload.value"/>
	     <@table.td name="rationWorkload.createDepartment"/>
	   </@>
	   <@table.tbody datas=rationWorkloads;rationWorkload>
         <@table.selectTd id="rationWorkloadId" value=rationWorkload.id/>
 	     <td>${rationWorkload.rationCn}</td>
	     <td>${rationWorkload.rationEn?if_exists}</td>
	     <td>${rationWorkload.value?default('')}</td>
	     <td><@i18nName rationWorkload.department/></td>
	   </@>
	  </@>
   <@htm.actionForm name="actionForm" entity="rationWorkload" action="rationWorkload.do"/>
   <script>
     var bar = new ToolBar("bar","额定工作量维护",null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem("<@msg.message key="action.add"/>","add()");
     bar.addItem("<@msg.message key="action.edit"/>","edit()");
     bar.addItem("<@msg.message key="action.delete"/>","remove()");
   </script>
	  </body>
<#include "/templates/foot.ftl"/>