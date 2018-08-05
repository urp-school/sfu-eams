	<table width="100%">
	    <form name="stdUserSearchForm" action="courseAndFee.do?method=search" target="contentFrame" method="post" onsubmit="return false;">
	    <input type="hidden" name="pageNo" value="1" />
	    <input type="hidden" name="calendar.id" value=${calendar.id}>
	    <input type="hidden" name="calendar.studentType.id" value=${calendar.studentType.id}>
	    <tr>
	    	<td width="40%"><@bean.message key="attr.student.code"/>:</td>
			<td><input type="text" name="student.code" value="" style="width:100px;" maxlength="32"/></td>
		</tr>
	    <tr>
	    	<td><@bean.message key="attr.personName"/>:</td>
	    	<td><input type="text" name="student.name" value="" style="width:100px;" maxlength="20"/></td>
	    </tr>
	    <tr>
	    	<td><@bean.message key="attr.enrollTurn"/>:</td>
	    	<td><input type="text" name="student.enrollYear" value="" style="width:100px;" maxlength="7"/></td>
	    </tr>
	    <tr>
	    	<td><@bean.message key="entity.studentType"/>:</td>
	    	<td><@htm.i18nSelect datas=calendarStdTypes selected=(student.type.id)?default("") name="student.type.id" style="width:100px"><option value=""><@bean.message key="common.all"/></option></@></td>
	    </tr>
		<tr>
			<td><@bean.message key="common.college"/>:</td>
			<td><@htm.i18nSelect datas=departmentList selected=(student.department.id)?default("") name="student.department.id" style="width:100px"><option value=""><@bean.message key="common.all"/></option></@></td>
		</tr>
		<tr>
			<td>是否在籍:</td>
			<td><@htm.select2 name="student.active" hasAll=true selected="1" style="width:100px;"/></td>
		</tr>
	    <tr height="50px">
	    	<td align="center" colspan="2">
	    		<input type="button" onclick="searchStd(1);" value="<@bean.message key="action.query"/>" class="buttonStyle"/>
	    	</td>
	    </tr>
		</form>
	</table>