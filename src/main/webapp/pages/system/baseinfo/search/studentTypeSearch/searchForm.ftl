<table width="100%">
	<tr>
      	<td colspan="2" class="infoTitle" align="left"><img src="${static_base}/images/action/info.gif" align="top"/><B>学生类别查询</B></td>
    </tr>
    <tr>
      	<td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
   	</tr>
    <tr>
    	<td width="40%"><@bean.message key="attr.code"/>:</td>
    	<td><input type="text" name="studentType.code" maxlength="50" style="width:100px;"/></td>
    </tr>
   	<tr>
   		<td><@bean.message key="attr.infoname"/>:</td>
   		<td><input type="text" name="studentType.name" style="width:100px;" maxlength="30"/></td>
   	</tr>
	<tr>
		<td><@msg.message key="attr.state"/>:</td>
		<td>
			<select name="studentType.state" style="width:100px;">
		   		<option value="1"><@bean.message key="common.enabled"/></option>
		   		<option value="0"><@bean.message key="common.disabled"/></option>
	   		</select>
	   	</td>
	</tr>
   	<tr height="50px">
   		<td colspan="2" align="center">
   			<button onclick="search();"><@bean.message key="action.query"/></button>
   		</td>
   	</tr>
</table>