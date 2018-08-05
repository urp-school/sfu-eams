<#assign extraElement>
	<tr>
		<td><@msg.message key="fee.isCompleted"/>：</td>
		<td>
			<select name="isCompleted" style="width:100px">	
				<option value=""><@msg.message key="common.all"/></option>
				<option value="1"><@msg.message key="attr.finished"/></option>
				<option value="0"><@msg.message key="attr.unfinished"/></option>
			</select>
		</td>
	</tr>
</#assign>
<#include "/pages/components/stdSearchSimpleTable.ftl"/>