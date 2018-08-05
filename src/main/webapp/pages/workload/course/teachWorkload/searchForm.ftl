  <table  align="center" width="100%">
   <form name="teachWorkloadForm" method="post" target="teachWorkloadQueryFrame" action="" onsubmit="return false;">
	<tr>
	<td>
	<table id="the_tableChild" width="100%" align="center" class="listTable">
		<tr class="darkColumn" align="center">
			<td colspan="6">
				<B><@msg.message key="workload.baseInfo"/></B>
			</td>
		</tr>
		<tr >
			<td class="grayStyle" align="center"><@msg.message key="entity.studentType"/>:
			</td>
			<td class="brightStyle" align="left">
				<select id="stdType" name="studentTypeId" style=":width:200px;">
				</select>
			</td>
			<td class="grayStyle" align="center"><@msg.message key="attr.year2year"/>
			</td>
			<td class="brightStyle" align="left">
				<select id="year" name="teachWorkload.teachCalendar.year" style="width:200px;">
				</select>
			</td>
			<td class="grayStyle" align="center"><@msg.message key="attr.term"/>
			</td>
			<td class="brightStyle" align="left">
				<select id="term" name="teachWorkload.teachCalendar.term" style="width:200px;">
				</select>
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center"><@msg.message key="workload.college"/>:
			</td>
			<td class="brightStyle" align="left">
				<@htm.i18nSelect datas=openCollegeList selected="" name="teachWorkload.college.id" style="width:200px;">
					<option value=""><@msg.message key="common.all"/></option>
				</@>
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.courseId"/>
			</td>
			<td class="brightStyle" align="left">
				<input type="text" name="teachWorkload.courseSeq" style="width:200px;" maxlength="32"/>
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.courseName"/>
			</td>
			<td class="brightStyle" align="left">
				<input type="text" name="teachWorkload.courseName" style="width:200px;" maxlength="25">
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.another"/>
			</td>
			<td class="brightStyle" align="left" colspan="5">
				<@msg.message key="workload.payBill"/>
				<select name="teachWorkload.payReward">
					<option value="">全部</option>
					<option value="true">已支付</option>
					<option value="false">未支付</option>
				</select>
				<@msg.message key="workload.calcWorkload"/>
				<select name="teachWorkload.calcWorkload">
					<option value="">全部</option>
					<option value="true">计</option>
					<option value="false">不计</option>
				</select>
				教师确认
				<select name="teachWorkload.teacherAffirm">
					<option value="">全部</option>
					<option value="true">确认</option>
					<option value="false">未确认</option>
				</select>
				院系确认
				<select name="teachWorkload.collegeAffirm">
					<option value="">全部</option>
					<option value="true">确认</option>
					<option value="false">未确认</option>
				</select>
			</td>
		</tr>
	</table>
	</td>
	</tr>
	<tr>
		<td>
			<table width="100%" align="center" class="listTable">
				<tr>
					<td rowspan="3" class="grayStyle" align="center">
						<@msg.message key="workload.teacherInfo"/>
					</td>
					<td class="grayStyle" align="center">
						<@msg.message key="workload.teacherId"/>
					</td>
					<td class="brightStyle" align="left">
						<input type="text" name="teachWorkload.teacherInfo.teacher.code" style="width:150px;" maxlength="32">
					</td>
					<td class="grayStyle" align="center">
						<@msg.message key="workload.teacherCollege"/>:
					</td>
					<td class="brightStyle" align="left">
						<@htm.i18nSelect datas=departmentList selected="" name="teachWorkload.teacherInfo.teachDepart.id" style="width:150px;">
							<option value=""><@msg.message key="common.all"/></option>
						</@>
					</td>
					<td class="grayStyle" align="center">
					</td>
					<td class="brightStyle" align="left">
					</td>
				</tr>
				<tr>
					<td class="grayStyle" align="center">
						<@msg.message key="workload.teacherName"/>:
					</td>
					<td class="brightStyle" align="left">
						<input type="text" name="teachWorkload.teacherInfo.teacherName" style="width:150px;" maxlength="25"/>
					</td>
					<td class="grayStyle" align="center">
						<@msg.message key="workload.gender"/>:
					</td>
					<td class="brightStyle" align="left">
						<@htm.i18nSelect datas=genderList selected="" name="teachWorkload.teacherInfo.gender.id" style="width:150px;">
							<option value=""><@msg.message key="common.all"/></option>
						</@>
					</td>
					<td class="grayStyle" align="center">
						<@msg.message key="workload.teacherTitle"/>
					</td>
					<td class="brightStyle" align="left">
						<@htm.i18nSelect datas=teacherTitleList selected="" name="teachWorkload.teacherInfo.teacherTitle.id" style="width:150px;">
							<option value=""><@msg.message key="common.all"/></option>
						</@>
					</td>
				</tr>
				<tr>
					<td class="grayStyle" align="center">
						<@msg.message key="workload.teacherAge"/>:
					</td>
					<td class="brightStyle" align="left">
						<input type="text" name="teacerAge1" style="width:50px;" maxlength="3"/>
						<@msg.message key="workload.age1"/>
						<input type="text" name="teacerAge2" style="width:50px;" maxlength="3"/>
						<@msg.message key="workload.age2"/>
					</td>
					<td class="grayStyle" align="center">
						<@msg.message key="workload.edudegree"/>:
					</td>
					<td class="brightStyle" align="left">
						<@htm.i18nSelect datas=eduDegreeList selected="" name="teachWorkload.teacherInfo.eduDegree.id" style="width:150px;">
							<option value=""><@msg.message key="common.all"/></option>
						</@>
					</td>
					<td class="grayStyle" align="center">
						<@msg.message key="workload.teacherType"/>
					</td>
					<td class="brightStyle" align="left">
						<@htm.i18nSelect datas=teacherTypeList selected="" name="teachWorkload.teacherInfo.teacherType.id" style="width:150px;">
							<option value=""><@msg.message key="common.all"/></option>
						</@>
					</td>
				</tr>
				<tr>
					<td class="darkColumn" align="center"  colspan="7">
					 	<button name="button1" onClick="search()" class="buttonStyle"><@msg.message key="system.button.query"/></button>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<#assign stdTypeNullable=true>
	<#assign yearNullable=true>
	<#assign termNullable=true>
	<#include "/templates/calendarSelect.ftl"/>
	</form>
	<tr>
		<td align="center">
			<iframe name="teachWorkloadQueryFrame" width="100%" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>
</table>