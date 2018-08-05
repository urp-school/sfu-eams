<#include "/templates/head.ftl"/>
 <body>
 <table id="myBar" width="100%" ></table>
  <table width="90%" align="center" class="formTable">
   <form name="commonForm" action="stdDetail.do?method=savaStdInfo" method="post" onsubmit="return false;">
	   <tr>
	     <td class="title" id="f_ancestralAddress">&nbsp;<@msg.message key="common.tutor"/>：</td>
	     <td>
	     	<select name="std.teacher.id" style="width:250px">
	     		<option value="">......</option>
		        <#list tutorList?sort_by(["department","name"]) as tutor>
			       <option value="${tutor.id}"<#if (std.teacher.id)?exists && std.teacher.id == tutor.id>selected</#if>>${(tutor.department.name)?if_exists}----${tutor.name?if_exists}</option>
			    </#list>
	     	</select>
	     </td>
	   </tr>  
	   <tr class="darkColumn">
	     <td colspan="4" align="center" >
	       <button onClick="form.submit();"><@bean.message key="system.button.submit"/></button>&nbsp;
	       <input type="reset" value="<@bean.message key="system.button.reset"/>" name="reset1" class="buttonStyle"/>
	     </td>
	   </tr>
	  <tr>
	     <td colspan="4">
	      显示内容：导师所属院系----导师姓名
	     </td>
	  </tr>
     </form>
     </table>
  <script language="javascript" >
    var bar =new ToolBar("myBar","修改导师信息",null,true,true);
    bar.addBack("<@msg.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>