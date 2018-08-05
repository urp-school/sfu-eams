<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','教学获奖情况列表',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("<@msg.message key="action.info"/>",'info()');
	   bar.addPrint("<@msg.message key="action.print"/>");
	</script>
    <@table.table width="100%"  sortable="true" id="listTable">
     <@table.thead>
       <@table.selectAllTd id="teachProductId"/>
  	   <@table.sortTd name="field.teachProduct.productName" id="teachProduct.productName" width="25%"/>
  	   <@table.td text="成果完成人" width="10%"/>
  	   <@table.sortTd name="field.teachProduct.productionAwardLevel" id="teachProduct.productionAwardLevel.name"/>
       <@table.td text="所属部门"/>
  	   <@table.sortTd name="field.teachProduct.productionAwardType" id="teachProduct.productionAwardType.name"/>
  	   <@table.sortTd text="颁奖机构" id="teachProduct.giveAwardPlace"/>
  	   <@table.sortTd text="获奖名称" id="teachProduct.awardName"/>
  	   <@table.sortTd name="field.teachProduct.awardTime" id="teachProduct.awardTime"/>
      </@>
  	  <@table.tbody datas=teachProducts;teachProduct,teachProduct_index>
    	<@table.selectTd id="teachProductId" value=teachProduct.id />
		<td>${teachProduct.productName?if_exists}</td>
		<td>${teachProduct.cooperateOfTeacher?if_exists}</td>
		<td>${teachProduct.productionAwardLevel?if_exists.name?if_exists}</td>
		<td>${teachProduct.department?if_exists.name?if_exists}</td>
		<td>${teachProduct.productionAwardType?if_exists.name?if_exists}</td>
		<td>${(teachProduct.giveAwardPlace)?if_exists}</td>
		<td>${teachProduct.awardName?if_exists}</td>
		<td>${teachProduct.awardTime?string("yyyy")}</td>
   	  </@>
    </@>
<@htm.actionForm name="actionForm" action="teachProductForTeacher.do" entity="teachProduct"></@>
</body>
 <#include "/templates/foot.ftl"/>