 <table width="100%" class="frameTable">
	<tr>
	<td width="20%" class="frameTable_view" valign="top">
	<table width="100%" class="searchTable">
		<form name="instructWorkloadForm" method="post" target="instructWorkloadQueryFrame" action="" onsubmit="return false;">
		<tr><td colspan="2" align="center">查询条件</td>
		</tr>
		<tr><td width="35%"><@msg.message key="workload.teacherName"/>:</td>
			<td><input type="text" name="instructWorkload.teacherInfo.teacherName" style="width:100%" maxlength="25"/></td>
		</tr>
		<tr><td>教师工号</td>
			<td><input type="text" name="instructWorkload.teacherInfo.teacher.code" style="width:100%" maxlength="32"/></td>
		</tr>
		<tr><td><@msg.message key="entity.studentType"/>:</td>
			<td><select id="stdType" name="instructWorkload.studentType.id" style="width:100%">
				</select>
			</td>
		</tr>
		<tr><td>学年度</td>
			<td><select id="year" name="instructWorkload.teachCalendar.year" style="width:100%"></select></td>
		</tr>
		<tr><td>学期</td>
			<td><select id="term" name="instructWorkload.teachCalendar.term" style="width:100%"></select></td>
		</tr>
		<tr><td>教师部门:</td>
			<td><@htm.i18nSelect datas=departmentList selected="" name="instructWorkload.teacherInfo.teachDepart.id" style="width:100%">
					<option value=""><@msg.message key="common.all"/></option>
				</@>
			</td>
		</tr>
		<tr><td>支付报酬</td>
			<td><select name="instructWorkload.payReward" style="width:100%">
					<option value="">全部</option>
					<option value="true">已支付</option>
					<option value="false">未支付</option>
				</select>
			</td>
		</tr>
		<tr><td>计工作量</td>
			<td><select name="instructWorkload.calcWorkload" style="width:100%">
					<option value="">全部</option>
					<option value="true">计工作量</option>
					<option value="false">不计工作量</option>
				</select>
			</td>
		</tr>
		<tr><td>性别:</td>
			<td><@htm.i18nSelect datas=genderList selected="" name="instructWorkload.teacherInfo.gender.id" style="width:100%">
					<option value=""><@msg.message key="common.all"/></option>
				</@>
			</td>
		</tr>
		<tr><td>教师职称:</td>
			<td><@htm.i18nSelect datas=teacherTitleList selected="" name="instructWorkload.teacherInfo.teacherTitle.id" style="width:100%">
					<option value=""><@msg.message key="common.all"/></option>
				</@>
			</td>
		</tr>
		<tr><td>教师类别:</td>
			<td><@htm.i18nSelect datas=teacherTypeList selected="" name="instructWorkload.teacherInfo.teacherType.id" style="width:100%">
					<option value=""><@msg.message key="common.all"/></option>
				</@>
			</td>
		</tr>
		<tr>
		   <td>类别</td>
		   <td>
      <select name="instructWorkload.modulus.itemType" style="width:100px;">
					<option value="thesis">毕业指导</option>
					<option value="practice">实习指导</option>
					<option value="usual">平时指导</option>
				</select>
		   </td>
		</tr>
		<tr>
			<td colspan="2" align="center"><button name="button1" onClick="search()" class="buttonStyle"><@msg.message key="system.button.query"/></button></td>
		</tr>
		<#assign stdTypeNullable=true>
		<#assign yearNullable=true>
		<#assign termNullable=true>
		<#include "/templates/calendarSelect.ftl"/>
		</form>
  </table>
  <td>
  <td valign="top"><iframe name="instructWorkloadQueryFrame" width="100%" height="400" frameborder="0" scrolling="no"></iframe></td>
</tr>
</table>