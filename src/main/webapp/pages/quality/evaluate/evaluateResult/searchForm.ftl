<table width="100%">
	<tr>
		<td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>详细查询(模糊输入)</B></td>
	</tr>
	<tr>
		<td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
	</tr>
	<tr>
		<td>学生学号：</td>
		<td><input type="text" name="evaluateResult.student.code" value="${RequestParameters["evaluateResult.student.id"]?default('')}" style="width:100px" maxlength="50"/></td>
	</tr>
	<tr>
		<td>课程序号：</td>
		<td><input type="text" name="evaluateResult.task.seqNo" value="${RequestParameters["evaluateResult.task.seqNo"]?default('')}" style="width:100px" maxlength="50"/></td>
	</tr>
	<tr>
		<td>评教状态：</td>
		<td><@htm.select2 name="evaluateResult.statState" selected=RequestParameters["evaluateResult.statState"]?default("") hasAll=true style="width:100px"/></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><br><button onclick="search()">查询</button><br></td>
	</tr>
</table>