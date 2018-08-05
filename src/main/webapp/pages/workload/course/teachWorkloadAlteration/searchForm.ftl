<table width="100%">
	<tr class="infoTitle" valign="top" style="font-size:10pt">
		<td align="left" valign="bottom" colspan="2">
			<img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<b>查询条件</b>
		</td>
	</tr>
	<tr>
		<td style="font-size:0pt" colspan="2">
			<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
		</td>
	</tr>
	<tr>
		<td><@msg.message key="attr.taskNo"/>：</td>
		<td><input type="text" name="alteration.task.seqNo" value="" maxlength="10" style="width:100px"/></td>
	</tr>
	<tr>
		<td><@msg.message key="attr.courseNo"/>：</td>
		<td><input type="text" name="alteration.task.course.code" value="" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td><@msg.message key="attr.courseName"/>：</td>
		<td><input type="text" name="alteration.task.course.name" value="" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>教师姓名：</td>
		<td><input type="text" name="alteration.teacher.name" value="" maxlength="30" style="width:100px"/></td>
	</tr>
	<tr>
		<td><@msg.message key="entity.courseCategory"/>：</td>
		<td><input type="text" name="alteration.courseCategory.name" value="" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td><@msg.message key="workload.teacherAffirm"/>：</td>
		<td>
			<select name="alteration.teacherAffirm" style="width:100px">
				<option value=""><@msg.message key="common.all"/></option>
				<option value="1"><@msg.message key="action.affirm"/></option>
				<option value="0"><@msg.message key="action.negate"/></option>
			</select>
		</td>
	<tr>
	</tr>
		<td><@msg.message key="workload.collegeAffirm"/>：</td>
		<td>
			<select name="alteration.collegeAffirm" style="width:100px">
				<option value=""><@msg.message key="common.all"/></option>
				<option value="1"><@msg.message key="action.affirm"/></option>
				<option value="0"><@msg.message key="action.negate"/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td>修改人：</td>
		<td><input type="text" name="alteration.workloadBy.name" value="" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>修改时间：</td>
		<td><input type="text" name="workloadAt" value="" maxlength="10" style="width:100px" onfocus="calendar()"/></td>
	</tr>
	<tr height="25">
		<td colspan="2" align="center">
			<button onclick="search()">查询</button>&nbsp;
			<button onclick="formReset()">重置</button>
		</td>
	</tr>
</table>