<#include "/templates/head.ftl"/>
<table id="backBar"></table>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table width="100%" align="center" class="listTable">
		<tr class="darkColumn" align="center">
			<td colspan="7">
				<B><@msg.message key="instructWorkload.instructWorkload"/><@msg.message key="postfix.detailInfo"/></B>
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
				${(workload.teacherInfo.teacherName)?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.gender"/>:
			</td>
			<td class="brightStyle" align="left">
				${(workload.teacherInfo.gender.name)?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherTitle"/>
			</td>
			<td class="brightStyle" align="left">
				${(workload.teacherInfo.teacherTitle.name)?if_exists}
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherAge"/>:
			</td>
			<td class="brightStyle" align="left">
				${(workload.teacherInfo.teacherAge)?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.edudegree"/>:
			</td>
			<td class="brightStyle" align="left">
				${(workload.teacherInfo.eduDegree.name)?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherType"/>
			</td>
			<td class="brightStyle" align="left">
				${(workload.teacherInfo.teacherType.name)?if_exists}
			</td>
		</tr>
	</table>
	<table width="100%" align="center" class="listTable">
		<tr >
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherCollege"/>
			</td>
			<td class="brightStyle" align="left">
				${(workload.teacherInfo.teachDepart.name)?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="attr.year2year"/>
			</td>
			<td class="brightStyle" align="left">
				${workload.teachCalendar.year}
			</td>
			<td class="grayStyle" align="center">
			<@msg.message key="attr.term"/>
			</td>
			<td class="brightStyle" align="left">
			${workload.teachCalendar.term}
			</td>
		</tr>
	</table>
	<table width="100%" align="center" class="listTable">
		<tr>
			<td rowspan="3" class="grayStyle"align="center">
				<@msg.message key="instructWorkload.instructWorkload"/><@msg.message key="postfix.basInfo"/>
			</td>
			<td class="grayStyle"align="center">
				<@msg.message key="entity.studentType"/>
			</td>
			<td class="brightStyle"align="left">
				${workload.studentType.name}
			</td>
			<td class="grayStyle"align="center" colspan="2">
				<@msg.message key="workload.workloadModulus"/>
			</td>
			<td class="brightStyle"align="left" colspan="2">
				${workload.modulus.modulusValue?string("##0.0")}
			</td>
		</tr>
		<tr>
			<td class="grayStyle"align="center" id="f_studentNumberId">
				<@msg.message key="instructWorkload.studentNumbers"/>
			</td>
			<td class="brightStyle"align="left">
				${workload.studentNumber}
			</td>
			<td class="grayStyle"align="center" id= "f_totleWorkload">
				<@msg.message key="instructWorkload.instructWorkload"/>
			</td>
			<td class="brightStyle"align="left">
				${workload.totleWorkload?string("##0.0")}
			</td>
			<td class="grayStyle"align="center">
			</td>
			<td class="grayStyle"align="center">
			</td>
		</tr>
		<tr>
			<td class="grayStyle"align="center">
				<@msg.message key="workload.payBill"/>
			</td>
			<td class="brightStyle"align="left">
				<#if workload.payReward><@msg.message key="workload.affirm1"/>
				<#else><@msg.message key="workload.affirm0"/>
				</#if>
			</td>
			<td class="grayStyle"align="center">
				<@msg.message key="workload.calcWorkload"/>
			</td>
			<td class="brightStyle"align="left">
				<#if workload.calcWorkload><@msg.message key="workload.affirm1"/>
				<#else><@msg.message key="workload.affirm0"/>
				</#if>
			</td>
			<td class="grayStyle"align="center">
				<@msg.message key="workload.teacherAffirm"/>
			</td>
			<td class="brightStyle"align="left">
				<#if workload.teacherAffirm><@msg.message key="workload.affirm1"/>
				<#else><@msg.message key="workload.affirm0"/>
				</#if>
			</td>
		</tr>
		
		<tr class="darkColumn" align="center">
			<td colspan="7" height="25px;"></td>
		</tr>
	</table>
<form name="detailForm" method="post" action="" onsubmit="return false;">
<input type="hidden" name="workloadIds" value="${workload.id}">
</form>
 <script>
   var bar = new ToolBar('backBar','<@msg.message key="instructWorkload.instructWorkload"/><@msg.message key="postfix.detailInfo"/>',null,true,true);
   bar.addItem("教师确认","teacherAffirm()");
   bar.addBack('<@msg.message key="action.back"/>');
   var form = document.detailForm;
   function teacherAffirm(){
     form.action="teacherWorkload.do?method=affirm&estate=true";
 	 setSearchParams(parent.form,form);
 	 form.submit();
   }
 </script>
 </body>
<#include "/templates/foot.ftl"/>