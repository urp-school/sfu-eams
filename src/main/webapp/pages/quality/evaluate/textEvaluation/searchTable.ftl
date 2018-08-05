	<table width="100%" class="searchTable">
		<tr>
			<td>开课院系</td>
			<td ><@htm.i18nSelect datas=departmentList name="textEvaluation.task.arrangeInfo.teachDepart.id"  selected="" style="width:100px">
					<option value=""><@bean.message key="common.all"/></option>
				</@>
			</td>
		</tr>
		<tr>
			<td><@msg.message key="attr.taskNo"/></td>
			<td >
				<input type="text" name="textEvaluation.task.seqNo" maxlength="32" style="width:100px;"/>
			</td>
		</tr>
		<tr>
			<td><@msg.message key="teacher.code"/></td>
			<td >
				<input type="text" name="textEvaluation.teacher.code" maxlength="32" style="width:100px;"/>
			</td>
		</tr>
		<tr>
			<td><@bean.message key="textEvaluation.teacher"/></td>
			<td >
				<input type="text" name="textEvaluation.teacher.name" style="width:100px;" maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td><@bean.message key="textEvaluation.affirm"/></td>
			<td >
				<select name="isAffirm" style="width:100px;">
					<option value="">全部</option>
					<option value="1">通过</option>
					<option value="0">不通过</option>
					<option value="null">未确认</option>
				</select>
			</td>
		</tr>
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