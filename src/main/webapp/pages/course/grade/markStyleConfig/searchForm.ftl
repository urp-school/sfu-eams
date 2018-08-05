<table width="100%">
	<tr>
		<td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>记录方式查询(模糊查询)</B></td>
	</tr>
	<tr>
		<td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
	</tr>
	<tr>
		<td>记录方式代码：</td>
		<td><input type="text" name="defaultConfig.markStyle.code" value="${RequestParameters["defaultConfig.markStyle.code"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>记录方式名称：</td>
		<td><input type="text" name="defaultConfig.markStyle.name" value="${RequestParameters["defaultConfig.markStyle.name"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>及格线：</td>
		<td><input type="text" name="defaultConfig.markStyle.passScore" value="${RequestParameters["defaultConfig.markStyle.passScore"]?if_exists}" maxlength="3" style="width:100px"/></td>
	</tr>
	<tr>
		<td colspan="2" align="center" height="50"><button onclick="search()">查询</button><br></td>
	</tr>
</table>