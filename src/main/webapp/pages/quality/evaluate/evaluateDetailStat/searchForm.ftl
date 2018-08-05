<#if !searchFormFlag?exists || searchFormFlag == "beenStat">
<input type="hidden" name="evaluateTeacher.calendar.id" value="${calendar.id}"/>
<table width="100%">
	<tr>
		<td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>评教结果查询(模糊查询)</B></td>
	</tr>
	<tr>
		<td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
	</tr>
	<tr>
		<td>教师工号：</td>
		<td><input type="text" name="evaluateTeacher.teacher.code" value="${RequestParameters["evaluateTeacher.teacher.code"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>教师姓名：</td>
		<td><input type="text" name="evaluateTeacher.teacher.name" value="${RequestParameters["evaluateTeacher.teacher.name"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>课程代码：</td>
		<td><input type="text" name="evaluateTeacher.course.code" value="${RequestParameters["evaluateTeacher.course.code"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>课程名称：</td>
		<td><input type="text" name="evaluateTeacher.course.name" value="${RequestParameters["evaluateTeacher.course.name"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>所在院系：</td>
		<td><@htm.i18nSelect datas=departments?sort_by("name") name="evaluateTeacher.department.id" selected=RequestParameters["evaluateTeacher.teacher.department.id"]?default("") maxlength="50" style="width:100px"><option value="">...</option></@></td>
	</tr>
    <tr>
        <td>问卷分类：</td>
        <td><select name="questionnaireId" style="width:100px" value="${RequestParameters["questionnaireId"]?default("")}">
            <option value="">...</option>
            <#list questionnaires?if_exists as questionnaire>
            <option value="${questionnaire.id}">${questionnaire.description}</option>
            </#list>
        </select></td>
    </tr>
	<tr>
		<td colspan="2" align="center" height="50"><button onclick="search('beenStat')">查询</button><br></td>
	</tr>
</table>
<table><tr><td height="160"></td></tr></table>
<input type="hidden" name="orderBy" value="evaluateTeacher.sumScore desc"/>
<input type="hidden" name="teachCalendar.id" value=""/>
<#elseif searchFormFlag == "noStat">
<input type="hidden" name="evaluateResult.teachCalendar.id" value="${calendar.id}"/>
<table width="100%">
	<tr>
		<td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>记录查询(模糊输入)</B></td>
	</tr>
	<tr>
		<td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
	</tr>
	<tr>
		<td>教师工号：</td>
		<td><input type="text" name="evaluateResult.teacher.code" value="${RequestParameters["teacherNo"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>教师姓名：</td>
		<td><input type="text" name="evaluateResult.teacher.name" value="${RequestParameters["teacherName"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>课程序号：</td>
		<td><input type="text" name="evaluateResult.task.seqNo" value="${RequestParameters["task.seqNo"]?if_exists}" maxlength="4" style="width:100px"/></td>
	</tr>
	<tr>
		<td>课程代码：</td>
		<td><input type="text" name="evaluateResult.task.course.code" value="${RequestParameters["task.course.code"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>课程名称：</td>
		<td><input type="text" name="evaluateResult.task.course.name" value="${RequestParameters["task.course.name"]?if_exists}" maxlength="50" style="width:100px"/></td>
	</tr>
	<tr>
		<td>所在院系：</td>
		<td><@htm.i18nSelect datas=departments?if_exists selected=RequestParameters["evaluateResult.department.id"]?default("") name="evaluateResult.department.id" style="width:100px"><option value="">...</option></@></td>
	</tr>
	<tr>
		<td colspan="2" align="center" height="50"><button onclick="search('noStat')">查询</button><br></td>
	</tr>
</table>
<table><tr><td height="130"></td></tr></table>
</#if>
<input type="hidden" name="searchFormFlag" value=""/>