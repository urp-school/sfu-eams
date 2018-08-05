<#include "/templates/head.ftl"/>
 <script language="javascript">
 	function doQuery(){
 		var form = document.teacherQueryForm;
 		if(form.stdTypeId.value==""){
 			alert("<@bean.message key="field.teacherQueryWorkload.selectStudentType"/>");
 			return;
 		}
 		if(form['year'].value==""||form['term'].value==""){
 			alert("学年度或学期不能为空。");
 			return;
 		}
 		form.action="teachWorkloadStat.do?method=doGetTeacherAcgWorkload";
 		form.target ="teacherQueryFrame";
 		form.submit();
 	}
 </script>
 <#assign headValue>教师人均工作量</#assign>
	<#include "../../stdCalendarConditions.ftl"/>
</body>
<#include "/templates/foot.ftl"/>