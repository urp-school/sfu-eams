<table width="100%" class="searchTable">
	<tr align="center">
	<td colspan="6"><@bean.message key="textEvaluation.selectCondition"/></td>
	</tr>
	<tr>
		<td class="title">
		<@bean.message key="attr.teachDepart"/>:
		</td>
		<td>
		<@htm.i18nSelect datas=departmentList selected="" name="teachAccident.task.arrangeInfo.teachDepart.id"  style="width:100px;">
		 <option value="">...</option>
		</@>
		</td>
	</tr>
	<tr>
		<td  class="title">
		<@bean.message key="teacher.code"/>:
		</td>
		<td>
		<input type="text" name="teachAccident.teacher.code" maxlength="32" style="width:100px;">
		</td>
	</tr>
	<tr>
		<td class="title">
		<@bean.message key="field.teachAccident.teacherName"/>:
		</td>
		<td>
		<input type="text" name="teachAccident.teacher.name" maxlength="20" style="width:100px;">
		</td>
	</tr>
	<tr>
		<td  class="title">
		<@bean.message key="field.teachAccident.courseId"/>
		</td>
		<td>
		<input type="text" name="teachAccident.task.course.code" maxlength="32" style="width:100px;">
		</td>
	</tr>
	<tr>
		<td  class="title">
		<@bean.message key="field.teachAccident.courseName"/>
		</td>
		<td>
		<input type="text" name="teachAccident.course.name" maxlength="20" style="width:100px;">
		</td>
	</tr>
	<tr>
		<td align="center" colspan="2">
		<button onClick="search()"><@bean.message key="system.button.query"/></button>
		</td>
	</tr>
</table>