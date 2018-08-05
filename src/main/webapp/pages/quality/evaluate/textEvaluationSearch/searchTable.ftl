	<table width="100%" class="searchTable">
		<tr>
			<td>开课院系</td>
			<td ><@htm.i18nSelect datas=departmentList name="textEvaluation.task.arrangeInfo.teachDepart.id"  selected="" style="width:100px">
					<option value=""><@bean.message key="common.all"/></option>
				</@>
			</td>
		</tr>
		<tr>
			<td><@msg.message key="teacher.code"/></td>
			<td >
				<input type="text" name="textEvaluation.teacher.code" maxlength="32" style="width:100px;">
			</td>
		</tr>
		<tr>
			<td><@bean.message key="textEvaluation.teacher"/></td>
			<td >
				<input type="text" name="textEvaluation.teacher.name" style="width:100px;" maxlength="20"/>
			</td>
		</tr>
		<input type="hidden" name="textEvaluation.isAffirm" value="1">
		<tr>
			<td><@bean.message key="textEvaluation.course"/></td>
			<td >
				<input type="text" name="textEvaluation.task.course.name" style="width:100px;" maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input type="button" name="queryButton" value="<@bean.message key="system.button.query"/>" class="buttonStyle" onClick="query(this.form)"/>
			</td>
		</tr>
	</table>