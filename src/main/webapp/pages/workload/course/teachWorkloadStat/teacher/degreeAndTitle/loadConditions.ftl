<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script language="javascript">
 function doQuery(form,value){
 		var stdTypeValue = document.getElementById("stdType");
 		if(""==stdTypeValue.value){
 			alert("<@bean.message key="field.teacherQueryWorkload.selectStudentType"/>");
 			return;
 		}
 		var teacherAge =document.teacherQueryForm.teacherAge.value;
 		if(!/^\d*$/.test(teacherAge)){
 			alert("教师年龄必须填写数字");
 			return;
 		}
 		var url="teachWorkloadStat.do?method=doStatisticForTeacher";
 		if(null!=value){
 			url
 		}
 		if(form['year'].value==""||form['term'].value==""){
 			alert("学年度或学期不能为空。");
 			return;
 		}
 		form.action="teachWorkloadStat.do?method=doStatisticForTeacher";
 		form.target ="teacherQueryFrame";
 		form.submit();
 }
 </script>
  <BODY LEFTMARGIN="0" TOPMARGIN="0" style="overflow-x:auto;">
   <#assign extraTR>
  <tr>
   <td class="grayStyle" align="center">学历学位</td>
   <td><select name="degreeFlag" style="width:100%"><option value="1">学位</option><option value="2">学历</option></select></td>
   <td class="grayStyle" align="center">教师年龄</td>
   <td><input type="text" name="teacherAge" style="width:100%" maxlength="3"></td>
   <td></td>
   <td></td>
  </tr>
  </#assign>
  <#include "../../stdCalendarConditions.ftl"/>
   </body>
 <#include "/templates/foot.ftl"/>