<table width="100%" cellpadding="0">
	<tr class="infoTitle" valign="top" style="font-size:10pt">
		<td class="infoTitle" align="left" valign="bottom" colspan="2">
			<img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<b>查询选项</b>
		</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2">
			<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
		</td>
	</tr>
	<tr>
		<td><@msg.message key="attr.taskNo"/>：</td>
		<td><input type="text" name="alteration.task.seqNo" value="${RequestParameters["alteration.task.seqNo"]?if_exists}" maxlength="10" style="width:100px"/></td>
	</tr>
	<tr>
		<td><@msg.message key="attr.courseNo"/>：</td>
		<td><input type="text" name="alteration.task.course.code" value="${RequestParameters["alteration.task.course.code"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td><@msg.message key="attr.courseName"/>：</td>
		<td><input type="text" name="alteration.task.course.name" value="${RequestParameters["alteration.task.course.name"]?if_exists}" maxlength="30" style="width:100px"/></td>
	</tr>
	<tr>
		<td><@msg.message key="entity.teacher"/>：</td>
		<td><input type="text" name="teacherName" value="${RequestParameters["teacherName"]?if_exists}" maxlength="10" style="width:100px"/></td>
	</tr>
	<tr>
		<td>调前信息：</td>
		<td><input type="text" name="alteration.alterationBefore" value="${RequestParameters["alteration.alterationBefore"]?if_exists}" maxlength="20" style="width:100px"/></td>
	</tr>
	<tr>
		<td>调后信息：</td>
		<td><input type="text" name="alteration.alterationAfter" value="${RequestParameters["alteration.alterationAfter"]?if_exists}" maxlength="20" style="width:100px"/></td>
	</tr>
	<tr>
		<td>访问路径：</td>
		<td><input type="text" name="alteration.alterFrom" value="${RequestParameters["alteration.alterFrom"]?if_exists}" maxlength="20" style="width:100px"/></td>
	</tr>
	<tr>
		<td>起始日期：</td>
		<td><input type="text" name="alterationBeginAt" value="${RequestParameters["alterationBeginAt"]?if_exists}" maxlength="10" style="width:100px" onfocus="calendar()"/></td>
	</tr>
	<tr>
		<td>截止日期：</td>
		<td><input type="text" name="alterationEndAt" value="${RequestParameters["alterationEndAt"]?if_exists}" maxlength="10" style="width:100px" onfocus="calendar()"/></td>
	</tr>
	<tr height="25px">
		<td colspan="2" align="center">
			<button onclick="search()">查询</button>&nbsp;
			<button onclick="formReset()">重置</button>
		</td>
	</tr>
</table>