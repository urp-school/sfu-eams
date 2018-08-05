<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
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
 		form.action="teachWorkloadStat.do?method=doGetCourseTypeNumber";
 		form.target ="teacherQueryFrame";
 		form.submit();
 	}
 </script>
 <#assign headValue><@bean.message key="workload.courseTypeNumberTitle"/></#assign>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<#include "../../stdCalendarConditions.ftl"/>
</body>
<#include "/templates/foot.ftl"/>