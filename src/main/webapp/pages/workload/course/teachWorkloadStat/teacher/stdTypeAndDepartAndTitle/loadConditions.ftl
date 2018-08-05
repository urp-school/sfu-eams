<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script language="javascript">
 function doQuery(form){
 	var id = form.stdTypeId.value;
 	if(id==""){
 		alert("<@bean.message key="workload.selectOneStudent"/>");
 		return;
 	}
 	if(form['year'].value==""||form['term'].value==""){
 		alert("学年度或学期不能为空。");
 		return;
 	}
 	form.action="teachWorkloadStat.do?method=doTeacherStdTypeDepartAndTitle&flag=flag";
 	form.target="teacherQueryFrame";
 	form.submit();
 }
 </script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" style="overflow-x:auto;">
  <#assign extraTR>
  	<tr>
		<td class="grayStyle" align="center"><@bean.message key="workload.teacherKind"/></td>
		<td class="brightStyle" align="left">
			<select name="teacherTypeId" style="width:150px;">
				<#list result.teacherTypeList?if_exists as teacherType>
				<option value="${teacherType.id}">${teacherType.name}</option>
				</#list>
			</select>
		</td>
		<td class="grayStyle" align="center"><@bean.message key="workload.statisticContext"/></td>
		<td class="brightStyle" align="left">
			<select name="statisticContextId" style="width:150px;">
				<option value="1"><@bean.message key="workload.peopleNumber"/></option>
				<option value="2"><@bean.message key="workload.workload"/></option>
			</select>
		</td>
		<td class="grayStyle" align="center"></td>
		<td class="brightStyle" align="left"></td>
	</tr>
</#assign>
  <#include "../../stdCalendarConditions.ftl"/>
 </body>
 <#include "/templates/foot.ftl"/>