<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
	 <table id="backBar" width="100%"></table>
    <@table.table width="100%"  sortable="true" id="listTable">
     <@table.thead>
       <@table.selectAllTd id="teachProductId"/>
  	   <@table.sortTd name="field.teachProduct.productName" id="teachProduct.productName" width="25%"/>
  	   <@table.td text="完成人" width="15%"/>
  	   <@table.sortTd name="field.teachProduct.productionAwardLevel" id="teachProduct.productionAwardLevel.name"/>
       <@table.td text="所属部门"/>
  	   <@table.sortTd name="field.teachProduct.productionAwardType" id="teachProduct.productionAwardType.name"/>
  	   <@table.sortTd name="field.teachProduct.awardTime" id="teachProduct.awardTime"/>
      </@>
  	  <@table.tbody datas=teachProducts;teachProduct,teachProduct_index>
    	<@table.selectTd id="teachProductId" value=teachProduct.id/>
		<td><A href="#" onclick="info(${teachProduct.id})">${teachProduct.productName?if_exists}</td>
		<td>${teachProduct.cooperateOfTeacher?if_exists}</td>
		<td><@i18nName (teachProduct.productionAwardLevel)?if_exists/></td>
		<td><@i18nName (teachProduct.department)?if_exists/></td>
		<td><@i18nName (teachProduct.productionAwardType)?if_exists/></td>
		<td>${teachProduct.awardTime?string("yyyy")}</td>
   	  </@>
    </@>
    <@htm.actionForm name="actionForm" action="teachProduct.do" entity="teachProduct"></@>
	<script>
	   var bar = new ToolBar('backBar','教学获奖情况列表',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("<@msg.message key="action.info"/>",'info()', "detail.gif");
	   bar.addPrint("<@msg.message key="action.print"/>");
	</script>
</body>
 <#include "/templates/foot.ftl"/>