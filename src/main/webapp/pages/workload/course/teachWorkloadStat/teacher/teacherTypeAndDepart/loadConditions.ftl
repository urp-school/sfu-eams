<#include "/templates/head.ftl"/>
 <#assign headValue><@bean.message key="workload.teacherTypeAndStdTypeTitle"/></#assign>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<#assign extraTR>
		<tr>
			<td class="grayStyle" align="center">是否计工作量</td>
			<td colspan="5" width="50%">
				<select name="isCaculate" style="width:50%">
					<option value="">全部</option>
					<option value="true">计</option>
					<option value="false">不计</option>
				</select>
			</td>
		</tr>
	</#assign>
	<#include "../../stdCalendarConditions.ftl"/>
 <script language="javascript">
    var form = document.teacherQueryForm;
 	function doQuery(){
 		if(form.stdTypeId.value==""){
 			alert("<@bean.message key="field.teacherQueryWorkload.selectStudentType"/>");
 			return;
 		}
 		if(form['year'].value==""||form['term'].value==""){
 			alert("学年度或学期不能为空。");
 			return;
 		}
 		form.action="teachWorkloadStat.do?method=doGetWorkloadByTeacherTypeAndDepartmentId";
 		form.submit();
 	}
 </script>
</body>
<#include "/templates/foot.ftl"/>