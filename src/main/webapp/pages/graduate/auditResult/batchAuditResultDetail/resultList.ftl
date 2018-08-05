<#list result.resultList?if_exists as auditResult>
	<#list auditResult.orderCourseGroupAuditResults as group>
	<#if !group.courseType.isCompulsory>
  	<div id="${auditResult_index}_${group.courseType.id}" style="visibility: hidden; display:none;">
  		<table align='center' cellpadding='0' cellspacing='0' border='0' width='95%'>
  		<tr align='center'>
	  		<td colspan='5' class='contentTableTitleTextStyle' bgcolor='#ffffff'>
	  		<B>个人培养计划类别完成情况</B>
	  		<td>
	  	</tr>
	  	<tr><td colspan='5'>&nbsp;</td></tr>
	  	<tr>
	  		<td>&nbsp;<@msg.message key="attr.stdNo"/>：${auditResult.student.code}</td>
	  		<td>&nbsp;<@msg.message key="attr.personName"/>：<@i18nName auditResult.student/></td>
	  		<td>&nbsp;<@msg.message key="entity.courseType"/>：<@i18nName group.courseType /></td>
	  		<td>&nbsp;完成学分：${group.creditAuditInfo.completed?if_exists}<#if group.creditAuditInfo.converted?exists&&group.creditAuditInfo.converted!=0>&nbsp;${group.creditAuditInfo.converted}</#if></td>
	  		<td>&nbsp;要求学分：${group.creditAuditInfo.required?if_exists}</td>
	  	</tr>
	  	</table>
		<table align="center" cellpadding="0" cellspacing="0" border="1" width="95%" class="listTable">
		<tr align="center" class="darkColumn">
       		<td><@bean.message key="attr.courseNo"/></td>
       		<td><@bean.message key="attr.courseName"/></td>
       		<td>成绩</td>
       		<td>绩点</td>
       		<td><@msg.message key="attr.credit"/></td>
       		<td>是否完成</td>              
       		<td>备注</td>
      	</tr>
		<#list group.orderPlanCourseAuditResults as planCourse>
		<tr>
			<td align="center">${planCourse.course.code}</td>
			<td align="left">&nbsp;<@i18nName planCourse.course/></td>
       		<td align="center"><#list planCourse.getScoresOrderByScore(null) as score>${score.scoreDisplay?default('无')}<#if score_has_next>/</#if></#list></td>
       		<td align="center"><#list planCourse.scores as score>${((score.GP*100)?int/100)?string('#0.00')?default('无')}<#-->${score.GP?string('0.00')}--><#if score_has_next>/</#if></#list></td>
       		<td align="center">${planCourse.creditAuditInfo.required?if_exists}</td>
       		<td align="center">${planCourse.creditAuditInfo.isPass?if_exists?string("Y","<font color='red'>N</font>")}</td>
       		<td align="center"><#list planCourse.substitionScores as substitionScore><@i18nName substitionScore.course /><#if substitionScore_has_next>/</#if></#list></td>     
      	</tr>
      	</#list>
      	</table>
	</div>
	</#if>
	</#list>
</#list>