<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/My97DatePicker/WdatePicker.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/My97DatePicker/config.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/My97DatePicker/calendar.js"></script>
		
<body>
<#assign labInfo>报表起始日期设置</#assign>
<#include "/templates/back.ftl"/>
 <table width="100%" align="center" class="formTable">
  <form action="attendDevice.do?method=edit" name="classroomForm" method="post" onsubmit="return false;">
   <tr class="darkColumn">
     <td align="left" colspan="2"><B>起始日期</B></td>
   </tr>
  <tr>
     <td class="title" width="40%"><font color="red">*</font>开始日期:</td>
     <td><input type="text" id="startDate" name="startDate" maxLength="10" size="10"/>&nbsp;(如：2013-08-26)</td>
  </tr>
  <tr>
     <td class="title"><font color="red">*</font>结束日期:</td>
     <td><input type="text" id="endDate" name="endDate" maxLength="10" size="10"/>&nbsp;(如：2013-08-28)</td>
  </tr>   
   
   <tr class="darkColumn" align="center">
     <td colspan="2">
       <input type="hidden" name="deptId" value="${(taskIds)!}"/>
       <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
       <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
     </td>
   </tr>
   </form>
 </table>
 <script language="JavaScript" type="text/JavaScript" src="scripts/course/attendDevice/DateCheck.js"></script>
  <script language="javascript"> 
     function reset(){
		document.classroomForm.reset();
     }
     function trimStr(str){ 
　　           return str.replace(/(^\s*)|(\s*$)/g, "");  
　　   }
     function save(form,params){
     	var startDate=trimStr(document.getElementById("startDate").value);
     	if(startDate==null || startDate==""){
     		alert("请填写开始日期");
	       	return;
     	}
     	var endDate=trimStr(document.getElementById("endDate").value);
     	if(endDate==null || endDate==""){
     		alert("请填写结束日期");
	       	return;
     	}  	
     	form.action = "attendReportWithSchool.do?method=showReport";
     	form.target="_blank";
     	form.submit();
     }
 </script>
</body> 
<#include "/templates/foot.ftl"/>