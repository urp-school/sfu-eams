<#include "/templates/head.ftl"/>
 <body>
  <table id="bar" width="100%"></table>
   <@table.table width="100%" id="listTable" sortable="true">
	 <@table.thead>
	   <@table.selectAllTd id="questionTypeId"/>
	   <@table.sortTd id="questionType.name" name="attr.name"/>
	   <@table.sortTd id="questionType.engName" name="attr.engName"/>
	   <@table.sortTd id="questionType.priority" text="优先级"/>
	   <@table.sortTd id="questionType.state" text="是否可用"/>
	 </@>
	 <@table.tbody datas=questionTypes;questionType>
	    <@table.selectTd id="questionTypeId" value=questionType.id/>
	    <td>${questionType.name}</td>
	    <td>${questionType.engName?default('')}</td>
	    <td>${questionType.priority}</td>
	    <td>${questionType.state?string("有效","无效")}</td>
	 </@>
   </@>
 <@htm.actionForm name="actionForm" action="questionType.do" entity="questionType"/>
<script>
   var bar = new ToolBar('bar','问题类别列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.add"/>","add()");
   bar.addItem("<@msg.message key="action.edit"/>","edit()");
   bar.addItem("<@msg.message key="action.delete"/>","remove()");
</script>
 </body>
<#include "/templates/foot.ftl"/>