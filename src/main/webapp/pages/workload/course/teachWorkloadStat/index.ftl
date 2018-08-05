<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','教学工作量相关统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>     
   <table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
      <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>信息分类</B>      
	      </td>
	  <tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <#assign statNames=['授课教师职称分布','在编和实际授课','部门课程类别','部门教职工类别','教学单位群体','授课工作量周课时','学院授课工作量汇总','教师职称工作量','教师学历学位']/>
       <#assign statMethods=['doTeacherStdTypeDepartAndTitle','doRateOfDepartment','doGetCourseTypeNumber','doGetRateOfTitleAndStdType','doGetWorkloadByTeacherTypeAndDepartmentId','doGetTeacherAcgWorkload','doStatisticTeachAllDeparts','doGetTeacherTitleOfWorkload','doStatisticForTeacher']/>
       <#list statNames as name>
       <tr>
         <td class="padding" <#if name_index==0>id="defaultItem"</#if> onclick="statBy(this,'${statMethods[name_index]}')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" >${name}
         </td>
       </tr>
       </#list>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
</table>
<form name="infoForm" method="post" action="" onsubmit="return false;"></form>
<script>
   document.getElementById("defaultItem").onclick();   
   function statBy(td,method){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      var form = document.infoForm;
      form.action="teachWorkloadStat.do?method="+method;
      form.target="statFrame";
      form.submit();
   }
</script>
</body>
<#include "/templates/foot.ftl"/>