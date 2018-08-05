<table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
	<tr>
		<td>
   			<form name="teacherQueryForm" method="post" target="teacherQueryFrame" action="" onsubmit="return false;">
				<table  width="100%" align="center" class="listTable">
					<tr>
						<td colspan="6" class="darkColumn" align="center"><#if headValue?exists>${headValue}<#else>查询条件</#if></td>
					</tr>
					<tr>
						<td class="grayStyle" align="center"><@msg.message key="entity.studentType"/>
						</td>
						<td class="brightStyle" align="left" width="20%">
							<select id="stdType" name="stdTypeId" style="width:100%">
								<option value=""><@msg.message key="field.teacherQueryWorkload.selectStudentType"/></option>
							</select>
						</td>
						<td class="grayStyle" align="center">学年度
						</td>
						<td class="brightStyle" align="left" width="20%">
							<select id="year" name="year" style="width:100%">
								<option value=""><@msg.message key="common.all"/></option>
							</select>
						</td>
						<td class="grayStyle" align="center">学期
						</td>
						<td class="brightStyle" align="left" width="20%">
							<select id="term" name="term" style="width:100%">
								<option value=""><@msg.message key="common.all"/></option>
							</select>
						</td>
					</tr>
					<#if extraTR?exists>${extraTR}</#if>
					<tr class="darkColumn" align="center">
						<td colspan="6">
							<input type="button" value="<@msg.message key="action.query"/>" name="button1" onClick="doQuery(this.form)" class="buttonStyle" />
						</td>
					</tr>
				</table>
					<#include "/templates/calendarSelect.ftl"/>
			</from>
		</td>
	</tr>
	<tr>
		<td  align="center">
			<iframe id="the_frame" name="teacherQueryFrame" width="100%" frameborder="0" scrolling="no"></iframe>
		</td>
	</tr>
	</table>