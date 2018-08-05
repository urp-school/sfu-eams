<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar" width="100%"></table>
  <table class="frameTable" width="100%">
  	<tr>
		<td valign="top" style="width:168px" class="frameTable_view">
			<table id="searchTable" width="100%" class="searchTable">
			<form name="feeDetailForm" method="post" onkeypress="DWRUtil.onReturn(event, goFirstPage)" action="" onsubmit="return false;">
			   <input name="pageNo" value="1" type="hidden">
			   <input name="pageSize" value="20" type="hidden">
				<tr>
					<td width="35%"><@msg.message key="entity.studentType"/></td>
					<td>
						<select id="stdType" name="feeDetail.calendar.studentType.id" style="width:100%;">
							<option value=""><@bean.message key="common.all"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>学年度</td>
					<td>
						<select id="year" name="feeDetail.calendar.year" style="width:100%;">
							<option value=""><@bean.message key="common.all"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>学期</td>
					<td>
						<select id="term" name="feeDetail.calendar.term" style="width:100%;">
							<option value=""><@bean.message key="common.all"/></option>
						</select>
					</td>
				 </tr>
				 <tr>
					<td><@msg.message key="attr.stdNo"/></td>
					<td>
					    <input type="text" name="feeDetail.std.code" maxlength="32" style="width:100%;" tabindex="1">
					</td>
				</tr>
				<tr>
					<td><@bean.message key="field.feeDetail.studentName"/></td>
					<td><input type="text" name="feeDetail.std.name" maxlength="20" style="width:100%;"></td>
				</tr>
				<tr>
					<td>发票</td>
					<td><input type="text" name="feeDetail.invoiceCode" maxlength="32" style="width:100%;"></td>
				</tr>
				<tr>
					<td>收费类型</td>
					<td>
						<select name="feeDetail.type.id" style="width:100%">
							<option value="">全部</option>
							<#list feeTypes?if_exists as feeType>
							<option value="${feeType.id}">${feeType.name}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td rowspan="5"><@bean.message key="field.feeDetail.addFeeCondition.sortCondition"/></td>
					<td><input type="checkBox" name="byWhat" value="calendar.year desc,calendar.term desc" checked>学年度,学期</td>
				</tr>
				<tr>
					<td><input type="checkBox" name="byWhat" value="type.name" checked>收费类型</td>
				</tr>
				<tr>
					<td><input type="checkBox" name="byWhat" value="createAt" checked>收费日期</td>
				</tr>
				<tr>
					<td><input type="checkBox" name="byWhat" value="std.code"><@msg.message key="attr.stdNo"/></td>
				</tr>
				<tr>
					<td><input type="checkBox" name="byWhat" value="invoiceCode"><@bean.message key="field.feeDetail.addFeeCondition.byInvoiceCode"/></td>
				</tr>
				<#assign stdTypeNullable=true>
				<#assign yearNullable=true>
				<#assign termNullable=true>
				<#include "/templates/calendarSelect.ftl"/>
				<tr>
					<td align="center" colspan="2"><input type="submit" value="<@bean.message key="action.query"/>" name="button1" onClick="search()" class="buttonStyle"></td>
				</tr>		
			</form>
			</table>
		</td>
		<td valign="top">
			<iframe name="addFeeQueryFrame" src="#" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
		</td>
	</tr>
  </table>
 <script>
    var bar = new ToolBar('backBar','收费信息查询',null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addHelp("<@msg.message key="action.help"/>");
 	
 	function search(pageNo,pageSize){
 		document.feeDetailForm.action = "feeSearch.do?method=search";
 		document.feeDetailForm.target = "addFeeQueryFrame";
 		goToPage(document.feeDetailForm,pageNo,pageSize);
 	}
 	search();
</script>
</body>
 <#include "/templates/foot.ftl"/>