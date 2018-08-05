<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<@table.table align="center" sortable="true" id="listTable" width="1600px">
		<@table.thead>
			<@table.selectAllTd id="teachWorkloadId" rowspan="2"/>
			<@table.sortTd text="开课院系" rowspan="2" id="teachWorkload.college.name"/>
			<@table.sortTd name="workload.teacherName" rowspan="2" id="teachWorkload.teacherInfo.teacherName"/>
			<@table.sortTd name="common.teacherTitle" rowspan="2" id="teachWorkload.teacherInfo.teacherTitle.name"/>
			<@table.sortTd name="workload.gender" rowspan="2" id="teachWorkload.teacherInfo.gender.name"/>
			<@table.td name="workload.teacherAge" rowspan="2"/>
			<@table.sortTd name="workload.teacherEduDegree" rowspan="2" id="teachWorkload.teacherInfo.eduDegree.name"/>
			<@table.sortTd name="workload.teacherDepart" rowspan="2" id="teachWorkload.teacherInfo.teachDepart.name"/>
			<@table.sortTd name="workload.courseName" rowspan="2" id="teachWorkload.courseName"/>
			<@table.td text="是否挂牌" rowspan="2"/>
			<@table.sortTd name="attr.taskNo" rowspan="2" id="teachWorkload.courseSeq"/>
			<@table.sortTd text="人数" rowspan="2" id="teachWorkload.studentNumber"/>
			<@table.sortTd text="周次" rowspan="2" id="teachWorkload.weeks"/>
			<@table.sortTd text="周课时" rowspan="2" id="teachWorkload.classNumberOfWeek"/>
			<@table.sortTd text="总课时" rowspan="2" id="teachWorkload.totleCourses"/>
			<@table.sortTd text="系数" rowspan="2" id="teachWorkload.teachModulus.modulusValue"/>
			<@table.sortTd text="总工作量" rowspan="2" id="teachWorkload.totleWorkload"/>
			<@table.sortTd name="entity.teachClass" rowspan="2" id="teachWorkload.classNames"/>
			<@table.td name="textEvaluation.affirm" colspan="4"/>
			<@table.td name="workload.academicTerm" rowspan="2"/>
			<@table.td text="备注" rowspan="2"/>
		</@>
		<@table.thead>
			<@table.td name="workload.payBill"/>
			<@table.td name="workload.calcWorkload"/>
			<@table.td name="workload.teacherAffirm"/>
			<@table.td name="workload.collegeAffirm"/>
	    </@>
	    <#assign recordSize = teachWorkloads?size/>
	    <@table.tbody datas=teachWorkloads;teachWorkload>
	    	<@table.selectTd id="teachWorkloadId" value=teachWorkload.id/>
		   	<td>${teachWorkload.college.name?if_exists}</td>
		   	<td>${teachWorkload.teacherInfo.teacherName?if_exists}</td>
		   	<td>${teachWorkload.teacherInfo.teacherTitle?if_exists.name?if_exists}</td>
		   	<td>${teachWorkload.teacherInfo.gender?if_exists.name?if_exists}</td>
		   	<td>${teachWorkload.teacherInfo.teacherAge?if_exists}</td>
		   	<td>${teachWorkload.teacherInfo.eduDegree?if_exists.name?if_exists}</td>
		   	<td>${teachWorkload.teacherInfo.teachDepart?if_exists.name?if_exists}</td>
		    <td>${teachWorkload.courseName?if_exists}</td>
		   	<td><#if teachWorkload.teachTask?exists><#if true==teachWorkload.teachTask.requirement.isGuaPai>是<#else>否</#if><#else><#if teachWorkload.courseCategory.id?string=="4">是<#else>否</#if></#if></td>
		   	<td>${teachWorkload.courseSeq?if_exists}</td>
		   	<td>${teachWorkload.studentNumber?if_exists}</td>
		   	<td>${teachWorkload.weeks?if_exists}</td>
		   	<td>${teachWorkload.classNumberOfWeek?default(0)?string("##0.0")}</td>
		   	<td>${teachWorkload.totleCourses?if_exists}</td>
		   	<td>${(teachWorkload.teachModulus.modulusValue)?default(0)?string("##0.0")}</td>
		   	<td>${teachWorkload.totleWorkload?default(0)?string("##0.0")}</td>
		   	<td <#if ((teachWorkload.classNames)?default('')?length>15)>title="${teachWorkload.classNames}"</#if>><#if ((teachWorkload.classNames)?default('')?length>15)>...<#else>${teachWorkload.classNames?if_exists}</#if></td>
		   	<td><#if teachWorkload.payReward==true><@bean.message key="workload.true"/><#else><@bean.message key="workload.false"/></#if></td>
		   	<td><#if teachWorkload.calcWorkload==true><@bean.message key="workload.true"/><#else><@bean.message key="workload.false"/></#if></td>
		   	<td><input type="hidden" id="teacherAffirm${teachWorkload.id}" value="${teachWorkload.teacherAffirm?string}"><#if teachWorkload.teacherAffirm==true><@bean.message key="workload.true"/><#else><@bean.message key="workload.false"/></#if></td>
		   	<td><input type="hidden" id="collegeAffirm${teachWorkload.id}" value="${teachWorkload.collegeAffirm?string}"><#if teachWorkload.collegeAffirm==true><@bean.message key="workload.true"/><#else><@bean.message key="workload.false"/></#if></td>
		   	<td>${teachWorkload.teachCalendar?if_exists.year?if_exists}&nbsp;${teachWorkload.teachCalendar?if_exists.term?if_exists}</td>
		   	<td>${teachWorkload.remark?if_exists}</td>
	    </@>
	</@>
	<#list 1..3 as i><br></#list>
</body>
<#include "/templates/foot.ftl"/>