<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','教学成果信息维护',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
  <table align="center" width="100%" class="frameTable">
	<tr>
	<td style="width:160px" class="frameTable_view" valign="top">
	<table width="100%" class="searchTable">
	<form name="teachProductForm" method="post" target="teachProductQueryFrame" action="" onsubmit="return false;">
		<tr>
			<td colspan="2" align="center"><@msg.message key="textEvaluation.selectCondition"/></td>
		</tr>
		<tr>
			<td width="35%"><@msg.message key="entity.department"/>:</td>
			<td><@htm.i18nSelect datas=departmentList selected="" name="teachProduct.department.id" style="width:100px">
				<option value="">...</option>
			</@>
			</td>
		</tr>
		<tr>
			<td><@msg.message key="field.teachProduct.productType"/>:</td>
			<td>
			   <@htm.i18nSelect datas=productionTypes selected="" name="teachProduct.productionType.id" style="width:100px">
			   	<option value="">...</option>
			   </@>
			</td>
		</tr>
		<tr>
			<td><@msg.message key="field.teachProduct.productionAwardType"/>:</td>
			<td><@htm.i18nSelect datas=productionAwardTypes selected="" name="teachProduct.productionAwardType.id" style="width:100px">
					<option value="">...</option>
				</@>
			</td>
		</tr>
		<tr>
			<td><@msg.message key="field.teachProduct.productionAwardLevel"/>:</td>
			<td>
			   <@htm.i18nSelect datas=productionAwardLevels selected="" name="teachProduct.productionAwardLevel.id" style="width:100px">
			   		<option value="">...</option>
			   </@>
			</td>
		</tr>
		<tr>
			<td>成果名称</td>
			<td><input type="text" name="teachProduct.productName" maxlength="100" style="width:100px" value=""/>
			</td>
		</tr>
		<tr>
			<td>完成人</td>
			<td><input type="text" name="teachProduct.cooperateOfTeacher" maxlength="30" style="width:100px" value=""/>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button onClick="search(1)" class="buttonStyle"><@msg.message key="system.button.query"/></button>
			</td>
		</tr>
	</table>
	</form>
	</td>
	<td align="center" valign="top">
		<iframe name="teachProductQueryFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
</tr>
</table>
<script>
    var form = document.teachProductForm;
    function search(pageNo,pageSize){
	   form.action="teachProduct.do?method=search";
	   goToPage(form,pageNo,pageSize);
	}
    search();
</script>
</body>
<#include "/templates/foot.ftl"/>