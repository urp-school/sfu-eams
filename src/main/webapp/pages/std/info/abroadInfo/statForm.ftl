<table width="100%" cellpadding="0">
	<tr>
		<td colspan="2" class="infoTitle" align="left" valign="bottom"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<b>到期时间统计条件</b></td>
	</tr>
	<tr>
		<td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
	</tr>
	<tr>
		<td class="infoTitle">所在年级:</td>
	    <td><input type="text" name="student.enrollYear" style="width:60px;" maxlength="7"/></td>
	</tr>
    <tr> 
		<td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
		<td>
			<select id="std_stdTypeOfSpeciality" name="student.type.id" style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/></option>
			</select>	 
		</td>
	</tr>
    <tr>
	    <td class="infoTitle"><@bean.message key="common.college"/>:</td>
	    <td>
			<select id="std_department" name="student.department.id" style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/>...</option>
			</select>
        </td>
    </tr> 
	<tr>
		<td class="infoTitle"><@bean.message key="entity.speciality"/>:</td>
		<td>
			<select id="std_speciality" name="student.firstMajor.id" style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/>...</option>
			</select>
        </td>
	</tr>
	<tr>
		<td class="infoTitle"><@bean.message key="entity.specialityAspect"/>:</td>
		<td>
			<select id="std_specialityAspect" name="student.firstAspect.id" style="width:100px;">
				<option value=""><@bean.message key="filed.choose"/>...</option>
			</select>
		</td>
	</tr>
	<tr>
		<td id="f_date">到期时间：</td>
		<td><input type="text" name="deadlineDate" value="${defaultDate?string("yyyy-MM-dd")}" onfocus="calendar()" style="width:100px" maxlength="10"/></td>
	</tr>
	<tr>
		<td>到期类型</td>
		<td>
			<select name="deadlineType" style="width:100px">
				<option value="visa">签证到期</option>
				<option value="passport">护照到期</option>
				<option value="resideCaed">居住许可证到期</option>
			</select>
		</td>
	</tr>
	<tr height="30">
		<td colspan="2" align="center"><button onclick="statAbroadStdList()">统计</button></td>
	</tr>
</table>
