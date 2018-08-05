<table width="100%"class="frameTable"> 
	<tr>
	<td width="20%" valign="top" class="frameTable_view">
	<table width="100%" class="searchTable">
		<form id="searchForm" name="searchForm" method="post" target="questionnaireResults">
			<tr>
				<td colspan="4" align="center"><@msg.message key="textEvaluation.selectCondition"/></td>
			</tr>
			<tr>
				<td width="35%"><@msg.message key="entity.studentType"/></td>
				<td><select id="stdType" name="questionnaireStat.stdType.id" style="width:100%">
				</select>
				</td>
			</tr>
			<tr>
				<td>学年度</td>
				<td><select id="year" name="questionnaireStat.calendar.year" style="width:100%">
				    <option value=""></option>
					</select>
				</td>
			</tr>
			<tr>
				<td>学期</td>
				<td><select id="term" name="questionnaireStat.calendar.term" style="width:100%">
				    <option value=""></option>
					</select>
				</td>
			</tr>
			<tr>
				<td>开课院系</td>
				<td><@htm.i18nSelect datas=departmentList selected="" name="questionnaireStat.depart.id" style="width:100%">
						<option value="">全部</option>
					</@>
				</td>
		</tr>
		<tr>
			<td><@msg.message key="attr.taskNo"/></td>
			<td><input type="text" name="questionnaireStat.taskSeqNo" style="width:100%" maxlength="32"/></td>
		</tr>
		<tr>
			<td><@msg.message key="field.questionnaireStatistic.teacherName"/></td>
			<td><input type="text" name="questionnaireStat.teacher.name" style="width:100%" maxlength="20"/></td>
		</tr>
		<tr>
			<td><@msg.message key="field.characterTeacher.course"/></td>
			<td><input type="text" name="questionnaireStat.course.name" style="width:100%" maxlength="20"/></td>
		</tr>
		<tr>
			<td>课程代码</td>
			<td><input type="text" name="questionnaireStat.course.code" style="width:100%" maxlength="32"/></td>
		</tr>
		<tr>
			<td>问题类别</td>
			<td><@htm.i18nSelect datas=questionTypeList selected="" name="selectTypeId" style="width:100%">
					<option value="">全部</option>
					<option value="0">总评</option>
				</@>
			</td>
		</tr>
		<tr>
			<td>分值类型</td>
			<td><select name="selectMark" style="width:100%">
				<option value="">全部</option>
				<option value="A">优</option>
				<option value="B">良</option>
				<option value="C">中</option>
				<option value="D">差</option>
			</select>
			</td>
		</tr>
		<tr>
			<td>对照标准</td>
			<td><select name="evaluationCriteriaId" style="width:100%">
				<#list evaluationCriterias as criteria>
				<option value="${criteria.id}">${criteria.name}</option>
				</#list>
			</select>
			</td>
		</tr>
		<tr>
			<td colspan="4"  align="center">
				<input type="button" value="查询" name="buttonSubmit" onClick="search()" class="buttonStyle"/>
			</td>
		</tr>
		<tr>
			<td colspan="4"  align="center">
				<font color="red">
	   			1.如果结果中问题类别成绩为空,则评教成绩属手工录入<br>2.优良中差比重：优(>=90),良(>=80),中(>=60),差(>=0)<br>3.2007-2008(2)学年以前的数据都是历史数据,所以有效票数都是0
	   		    </font>
			</td>
		</tr>
		</table>
		    <#assign yearNullable=true><#assign termNullable=true>
			<#include "/templates/calendarSelect.ftl"/>
		<input type="hidden" name="titles" value="开课院系,教师部门,教师代码,教师名称,教师类别,课程代码,课程序号,课程名称,入学年份,教学班名称,有效票数,<#list questionTypeList?if_exists as questionType>${questionType.name?js_string},</#list>总评成绩,学年度,学期">
		<input type="hidden" name="keys" value="depart.name,teacher.department.name,teacher.code,teacher.name,teacher.teacherType.name,course.code,taskSeqNo,course.name,task.teachClass.enrollTurn,task.teachClass.name,validTickets,<#list questionTypeList?if_exists as questionType>questionTypeStat_${questionType.id},</#list>scoreDisplay,calendar.year,calendar.term">
		</form>
		</td>
		<td valign="top">
			<iframe name="questionnaireResults" width="100%" frameborder="0" scrolling="no"></iframe>
		</td>
		</tr>
	</table>