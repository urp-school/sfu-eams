<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="infoTable">
		<tr>
			<td class="title"><@msg.message key="attr.taskNo"/>：</td>
			<td class="content">${alteration.task.seqNo}</td>
			<td class="title"><@msg.message key="attr.courseNo"/>：</td>
			<td class="content">${alteration.task.course.code}</td>
		</tr>
		<tr>
			<td class="title"><@msg.message key="attr.courseName"/>：</td>
			<td class="content"><@i18nName alteration.task.course/></td>
			<td class="title"><@msg.message key="entity.courseCategory"/>：</td>
			<td class="content"><@i18nName alteration.courseCategory/></td>
		</tr>
		<tr>
			<td class="title">教师工号：</td>
			<td class="content">${alteration.teacher.code}</td>
			<td class="title">教师姓名：</td>
			<td class="content"><@i18nName alteration.teacher/></td>
		</tr>
		<tr>
			<td class="title">教师年龄：</td>
			<td class="content">${(alteration.teacher.age)?if_exists}</td>
			<td class="title">教师院系：</td>
			<td class="content"><@i18nName alteration.teacher.department/></td>
		</tr>
		<tr>
			<td class="title"><@msg.message key="workload.teacherAffirm"/>：</td>
			<td class="content"><#if (alteration.teacherAffirm)?exists && alteration.teacherAffirm><@msg.message key="action.affirm"/><#else><@msg.message key="action.negate"/></#if></td>
			<td class="title"><@msg.message key="workload.collegeAffirm"/>：</td>
			<td class="content"><#if (alteration.collegeAffirm)?exists && alteration.collegeAffirm><@msg.message key="action.affirm"/><#else><@msg.message key="action.negate"/></#if></td>
		</tr>
		<tr>
			<td class="title">修改前信息：</td>
			<td class="content" colspan="3">
				上课人数[${alteration.alterBefore.studentNumber}]&nbsp;工作量系统[${alteration.alterBefore.modulus.modulusValue}//<@i18nName alteration.alterBefore.modulus.studentType/>//<@i18nName alteration.alterBefore.modulus.courseCategory/>]<br>
				上课周数[${alteration.alterBefore.weeks?string("0.##")}]&nbsp;总课时[${alteration.alterBefore.totleCourses?string("0.##")}]&nbsp;周课时[${alteration.alterBefore.classNumberOfWeek?string("0.##")}]&nbsp;上课周数[${alteration.alterBefore.totleWorkload?string("0.##")}]<br>
				已支付报酬[<#if (alteration.alterBefore.payReward)?exists && alteration.alterBefore.payReward><@msg.message key="action.affirm"/><#else><@msg.message key="action.negate"/></#if>]&nbsp;计工作量[<#if (alteration.alterBefore.calcWorkload)?exists && alteration.alterBefore.calcWorkload><@msg.message key="action.affirm"/><#else><@msg.message key="action.negate"/></#if>]<br>
				备注[${(alteration.alterBefore.remark)?default("&nbsp;")}]
			</td>
		</tr>
		<tr>
			<td class="title">修改后信息：</td>
			<td class="content" colspan="3">
				上课人数[${alteration.alterAfter.studentNumber}]&nbsp;工作量系统[${alteration.alterAfter.modulus.modulusValue}//<@i18nName alteration.alterAfter.modulus.studentType/>//<@i18nName alteration.alterAfter.modulus.courseCategory/>]<br>
				上课周数[${alteration.alterAfter.weeks?string("0.##")}]&nbsp;总课时[${alteration.alterAfter.totleCourses?string("0.##")}]&nbsp;周课时[${alteration.alterAfter.classNumberOfWeek?string("0.##")}]&nbsp;上课周数[${alteration.alterAfter.totleWorkload?string("0.##")}]<br>
				已支付报酬[<#if (alteration.alterAfter.payReward)?exists && alteration.alterAfter.payReward><@msg.message key="action.affirm"/><#else><@msg.message key="action.negate"/></#if>]&nbsp;计工作量[<#if (alteration.alterAfter.calcWorkload)?exists && alteration.alterAfter.calcWorkload><@msg.message key="action.affirm"/><#else><@msg.message key="action.negate"/></#if>]<br>
				备注[${(alteration.alterAfter.remark)?default("&nbsp;")}]
			</td>
		</tr>
		<tr>
			<td class="title">修改人：</td>
			<td class="content">${alteration.workloadBy.name}</td>
			<td class="title">修改时：</td>
			<td class="content">${alteration.workloadAt?string("yyyy-MM-dd HH:mm:ss")}</td>
		</tr>
		<tr>
			<td class="title">访问路径：</td>
			<td class="content">${alteration.workloadFrom}</td>
			<td class="title"></td>
			<td class="content"></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "工作量修改日志详细情况", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBack("<@msg.message key="action.back"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>