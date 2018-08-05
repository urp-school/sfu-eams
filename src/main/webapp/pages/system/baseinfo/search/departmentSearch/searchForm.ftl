<table width="100%">
	<tr class="infoTitle" style="text-valign:top;font-size:9pt;text-align:left;font-weight:bold">
		<td colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;详细查询(模糊输入)</td>
	</tr>
	<tr class="font-size:0pt">
		<td colspan="2"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="texttop"/></td>
	</tr>
	<tr>
		<td width="40%"><@bean.message key="attr.code"/>:</td>
		<td><input type="text" name="department.code" style="width:100px;"/></td>
	</tr>
	<tr>
		<td><@bean.message key="attr.infoname"/>:</td>
		<td><input type="text" name="department.name" style="width:100px;"/></td>
	</tr>
	<tr>
		<td><@bean.message key="department.isTeaching"/>:</td>
		<td>
			<select name="department.isTeaching" style="width:100px;">
				<option value=""><@msg.message key="common.all"/></option>
				<option value="1"><@msg.message key="common.yes"/></option>
				<option value="0"><@msg.message key="common.no"/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td><@bean.message key="department.isCollege"/>:</td>
		<td>
			<select name="department.isCollege" style="width:100px;">
				<option value=""><@msg.message key="common.all"/></option>
				<option value="1"><@msg.message key="common.yes"/></option>
				<option value="0"><@msg.message key="common.no"/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td><@msg.message key="attr.state"/>:</td>
		<td>
			<select name="department.state" style="width:100px;">
		   		<option value="1"><@bean.message key="common.enabled"/></option>
		   		<option value="0"><@bean.message key="common.disabled"/></option>
	   		</select>
	   	</td>
	</tr>
   	<tr height="50px">
   		<td colspan="2" align="center">
   			<button onclick="search();"><@bean.message key="action.query"/></button>
   			<button onclick="this.form.reset()"><@bean.message key="action.reset"/></button>
   		</td>
   	</tr>
</table>
