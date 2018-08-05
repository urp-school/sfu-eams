<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar"></table>
	<table width="100%" align="center" class="listTable">
		<tr class="darkColumn" align="center">
			<td colspan="7">
				<B><@msg.message key="workload.taskWorkload"/><@msg.message key="postfix.detailInfo"/></B>
			</td>
		</tr>
		<tr>
		    <td rowspan="2" class="grayStyle" align="center">
				<@msg.message key="workload.teacherInfo"/>
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherName"/>:
			</td>
			<td class="brightStyle" align="left">
				${workload.teacherInfo.teacherName?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.gender"/>:
			</td>
			<td class="brightStyle" align="left">
				${workload.teacherInfo.gender?if_exists.name?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherTitle"/>
			</td>
			<td class="brightStyle" align="left">
				${workload.teacherInfo.teacherTitle?if_exists.name?if_exists}
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherAge"/>:
			</td>
			<td class="brightStyle" align="left">
				${workload.teacherInfo.teacherAge?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.edudegree"/>:
			</td>
			<td class="brightStyle" align="left">
				${workload.teacherInfo.eduDegree?if_exists.name?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherType"/>
			</td>
			<td class="brightStyle" align="left">
				${workload.teacherInfo.teacherType?if_exists.name?if_exists}
			</td>
		</tr>
		<tr>
			<td class="grayStyle" algin="center" rowspan="2">
				<@msg.message key="workload.another"/>
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.college"/>
			</td>
			<td class="brightStyle" align="left">
				${workload.college.name}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherCollege"/>
			</td>
			<td class="brightStyle" align="left">
				${workload.teacherInfo.teachDepart?if_exists.name?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.courseName"/>
			</td>
			<td class="brightStyle" align="left">
				${workload.courseName}
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.academic"/>
			</td>
			<td class="brightStyle" align="left">
				${workload.teachCalendar.year}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.term"/>
			</td>
			<td class="brightStyle" align="left">
				${workload.teachCalendar.term}
			</td>
	 		<td class="grayStyle" align="center">
	 			数据进入方式：
			</td>
			<td class="brightStyle" align="left">
				<#if true==workload.isHandInput?exists>手工录入<#else>系统统计</#if>
			</td>
		</tr>
	</table>
	<table width="100%" align="center" class="listTable">
			<tr>
				<td rowspan="3" class="grayStyle"align="center">
					<@msg.message key="workload.taskWorkload"/><@msg.message key="postfix.basInfo"/>
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.studentNumberInClass"/>
				</td>
				<td class="brightStyle"align="left">
					${workload.studentNumber}
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="entity.studentType"/>
				</td>
				<td class="brightStyle"align="left">
					${workload.studentType.name}
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="entity.courseCategory"/>
				</td>
				<td class="brightStyle"align="left">
					${workload.courseCategory?if_exists.name?if_exists}
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.workloadModulus"/>
				</td>
				<td class="brightStyle"align="left">
					<#if workload.teachModulus?exists>${workload.teachModulus.modulusValue?string("##0.0")}<#else>无对应系数</#if>
				</td>
			</tr>
			<tr>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.weeks"/>
				</td>
				<td class="brightStyle"align="left">
					${workload.weeks}
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.totleCourse"/>
				</td>
				<td class="brightStyle"align="left">
					${workload.totleCourses?if_exists}
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.weekCourse"/>
				</td>
				<td class="brightStyle"align="left">
					${workload.classNumberOfWeek?string("##0.0")}
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.taskWorkload"/>
				</td>
				<td class="brightStyle"align="left">
					${workload.totleWorkload?string("##0.0")}
				</td>
			</tr>
			<tr>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.payBill"/>
				</td>
				<td class="brightStyle"align="left">
					<#if workload.payReward==true><@msg.message key="workload.pay1"/>
					<#else><@msg.message key="workload.pay0"/>
					</#if>
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.calcWorkload"/>
				</td>
				<td class="brightStyle"align="left">
					<#if workload.calcWorkload==true><@msg.message key="workload.calcWorkload"/>
					<#else><@msg.message key="workload.calcWorkload0"/>
					</#if>
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.teacherAffirm"/>
				</td>
				<td class="brightStyle"align="left">
					<#if workload.teacherAffirm==true><@msg.message key="workload.affirm1"/>
					<#else><@msg.message key="workload.affirm0"/>
					</#if>
				</td>
				<td class="grayStyle"align="center">
					<@msg.message key="workload.collegeAffirm"/>
				</td>
				<td class="brightStyle"align="left">
					<#if workload.collegeAffirm==true><@msg.message key="workload.affirm1"/>
					<#else><@msg.message key="workload.affirm0"/>
					</#if>
				</td>
			</tr>
			<tr class="darkColumn" align="center">
				<td colspan="9" height="25px;">
					
				</td>
			</tr>
		</table>
   <form name="teachWorkloadForm" method="post" action="" onsubmit="return false;"><input type="hidden" name="workloadIds" value="${workload.id}"></form>
 </body>
 <script>
 	function teacherAffirm(){
 		if("true"==${workload.teacherAffirm?string}){
 			if(!confirm("你已经确认过这个工作量,你还要确认一次吗??")){
 				return;
 			}
 		}
 		if(confirm("你确认要确认这个工作量吗?")){
    		document.teachWorkloadForm.action="teacherWorkload.do?method=affirm&estate=true";
    		setSearchParams(parent.form,document.teachWorkloadForm);
    		document.teachWorkloadForm.submit();
    	}
 	}
   var bar = new ToolBar('backBar','授课工作量详细信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("教师确认","teacherAffirm()");
   bar.addBack();
</script>
<#include "/templates/foot.ftl"/>