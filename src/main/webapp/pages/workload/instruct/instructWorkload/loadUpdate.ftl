<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','<@msg.message key="instructWorkload.instructWorkload"/><@msg.message key="postfix.detailInfo"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@msg.message key="action.back"/>');
</script>
 <BODY LEFTMARGIN="0">
	<table width="100%" align="center" class="listTable">
		<form name="instructWorkloadForm" method="post" action="" onsubmit="return false;">
		<tr class="darkColumn" align="center">
			<td colspan="7"><@msg.message key="instructWorkload.instructWorkload"/><@msg.message key="postfix.detailInfo"/>
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
				${instructWorkload.teacherInfo.teacherName}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.gender"/>:
			</td>
			<td class="brightStyle" align="left">
				${(instructWorkload.teacherInfo.gender.name)?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherTitle"/>
			</td>
			<td class="brightStyle" align="left">
				${(instructWorkload.teacherInfo.teacherTitle.name)?if_exists}
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherAge"/>:
			</td>
			<td class="brightStyle" align="left">
				${(instructWorkload.teacherInfo.teacherAge)?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.edudegree"/>:
			</td>
			<td class="brightStyle" align="left">
				${(instructWorkload.teacherInfo.eduDegree.name)?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherType"/>
			</td>
			<td class="brightStyle" align="left">
				${(instructWorkload.teacherInfo.teacherType.name)?if_exists}
			</td>
		</tr>
	</table>
	<table width="100%" align="center" class="listTable">
		<tr >
		<td class="grayStyle" align="center">
			<@msg.message key="workload.teacherCollege"/>
		</td>
		<td class="brightStyle" align="left">
			${instructWorkload.teacherInfo.teachDepart.name}
		</td>
		<td class="grayStyle" align="center">
			<@msg.message key="workload.selectAcademic"/>
		</td>
		<td class="brightStyle" align="left">
			${instructWorkload.teachCalendar.year}
		</td>
		<td class="grayStyle" align="center">
			<@msg.message key="workload.selectterm"/>
		</td>
		<td class="brightStyle" align="left">
			${instructWorkload.teachCalendar.term}
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
				${instructWorkload.studentType.name}
			</td>
			<td class="grayStyle"align="center" colspan="2">
				<@msg.message key="workload.workloadModulus"/>
			</td>
			<td class="brightStyle"align="left" colspan="2">
				${instructWorkload.modulus.modulusValue?string("##0.0")}
			</td>
		</tr>
		<tr>
			<td class="grayStyle"align="center" id="f_studentNumberId">
				<@msg.message key="instructWorkload.studentNumbers"/>
			</td>
			<td class="brightStyle"align="left">
				<input type="text" name="instructWorkload.studentNumber" value="${instructWorkload.studentNumber}" style="width:100px">
			</td>
			<td class="grayStyle"align="center" id= "f_totleWorkload">
				<@msg.message key="instructWorkload.instructWorkload"/>
			</td>
			<td class="brightStyle"align="left">
				<input type="text" name="instructWorkload.totleWorkload" style="width:100px" value="${instructWorkload.totleWorkload?string("##0.0")}">
			</td>
			<td class="grayStyle"align="center">
			</td>
			<td class="brightStyle"align="left">
			</td>
		</tr>
		<tr>
			<td class="grayStyle"align="center">
				<@msg.message key="workload.payBill"/>
			</td>
			<td class="brightStyle"align="left">
				<select name="instructWorkload.payReward" style="width:100%">
					<option value="true" <#if instructWorkload.payReward==true>selected</#if>>已支付</option>
					<option value="false" <#if instructWorkload.payReward==false>selected</#if>>未支付</option>
				</select>
			</td>
			<td class="grayStyle"align="center">
				<@msg.message key="workload.calcWorkload"/>
			</td>
			<td class="brightStyle"align="left">
				<select name="instructWorkload.calcWorkload" style="width:100%">
					<option value="true" <#if instructWorkload.payReward==true>selected</#if>>已计工作量</option>
					<option value="false" <#if instructWorkload.payReward==false>selected</#if>>未计工作量</option>
				</select>
			</td>
			<td class="grayStyle"align="center">
				<@msg.message key="workload.teacherAffirm"/>
			</td>
			<td class="brightStyle"align="left">
				<#if instructWorkload.teacherAffirm==true><@msg.message key="workload.affirm1"/>
				<#else><@msg.message key="workload.affirm0"/>
				</#if>
			</td>
		</tr>
		<tr>
			<td class="darkColumn" align="center"  colspan="9">
				<input type="hidden" name="instructWorkloadId" value="${instructWorkload.id}">
			 	<button  name="button1" onClick="doUpdate()" class="buttonStyle"><@msg.message key="action.update"/></button>
			</td>
		</tr>
		</form>
	</table>
<script language="javascript">
	var form = document.instructWorkloadForm;
 	function doUpdate(){
 		var a_fields = {
          	'instructWorkload.studentNumber':{'l':'<@msg.message key="instructWorkload.studentNumbers"/>', 'r':true, 't':'f_studentNumberId','f':'unsigned','mx':'4'},
         	'instructWorkload.totleWorkload':{'l':'<@msg.message key="instructWorkload.instructWorkload"/>', 'r':true, 't':'f_totleWorkload','f':'real','mx':'10'}
     		};
     	var v = new validator(form, a_fields, null);
 		if (v.exec()) {
			form.action ="instructWorkload.do?method=doUpdate";
			setSearchParams(parent.form,form)
			form.submit();
		}
 		}
 </script>
 </body>
<#include "/templates/foot.ftl"/>