<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<script src='dwr/interface/teachPlanService.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TeachPlan.js"></script> 
<body>
	<#assign labInfo>考勤信息新增</#assign>
	<#include "/templates/back.ftl"/>
    <table width="100%"  class="formTable">
      	<form action="attendStatic.do?method=edit" name="planForm" method="post" onsubmit="return false;">
       	<tr class="darkColumn">
         	<td align="left" colspan="4"><B>添加学生</B></td>
       	</tr>
	   	<tr>
         	<td colspan="1" width="50%" align="right">学生学号:<font color="red">*</font></td>
         	<td align="left" colspan="3" width="50%">
         	<input style="width:120px" maxlength="20" type="text" name="studentCode" value=""/>
         	<#if isExist??><label style="font-size:12px">${isExist}<label> </#if>
         	</td>
       	</tr>
       	<tr align="center"  class="darkColumn">
	     <td colspan="4">
           <input type="button" onclick="submit(this.form)" value="查找" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
	     </td>
	   </tr> 
	   </form> 
	</table>
  	<script language="javascript">
  	function submit(form){
  	form.submit();
  	}
 	</script>
</body>
<#include "/templates/foot.ftl"/>