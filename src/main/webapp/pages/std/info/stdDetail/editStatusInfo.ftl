<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <body>
 <table id="myBar" width="100%" ></table>
  <table width="90%" align="center" class="formTable">
   <#assign info=std.studentStatusInfo/>
   <form name="commonForm" action="stdDetail.do?method=saveStatusInfo" method="post" onsubmit="return false;">
       <input type="hidden" name="statusInfo.id" value="${info.id}">
	   <tr>
	     <td class="title" width="25%"><@msg.message key="std.examNumber"/>：</td>
	     <td><#if (std.type.degree.id)?if_exists == 102 || (std.type.degree.id)?if_exists == 103>不适用<#else><input name="statusInfo.examNumber" maxlength="40" value="${info.examNumber?if_exists}"/></#if></td>
	     <td class="title"><@msg.message key="std.graduateSchool"/>：</td>
	     <td><input name="statusInfo.graduateSchool" maxlength="40" value="${info.graduateSchool?if_exists}"/></td>
	   </tr>
	   <tr>
	     <td class="title"><@msg.message key="entity.enrollMode"/>：</td>
	     <td><@htm.i18nSelect datas=enrollModes selected=(info.enrollMode.id)?default('')?string name="statusInfo.enrollMode.id" style="width:100px;" /></td>
	     <td class="title"><@msg.message key="entity.educationMode"/>：</td>
	     <td><@htm.i18nSelect datas=educationModes selected=(info.educationMode.id)?default('')?string name="statusInfo.educationMode.id" style="width:100px;" /></td>
	   </tr>
	   <tr>
	     <td class="title"><@msg.message key="std.educationBeforEnroll"/>：</td>
	     <td><@htm.i18nSelect datas=eduDegrees selected=(info.educationBeforEnroll.id)?default('')?string name="statusInfo.educationBeforEnroll.id" style="width:100px;" /></td>
	     <td class="title"><@msg.message key="std.enrollDate"/>：</td>
	     <td><input name="statusInfo.enrollDate" onfocus="calendar()" maxlength="10" value="<#if info.enrollDate?exists>${info.enrollDate?string("yyyy-MM-dd")}</#if>"/></td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="4" align="center" >
	       <input type="button" value="<@bean.message key="system.button.submit" />" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
	     </td>
	   </tr>
     </form>
     </table>
  <script language="javascript" >
    function doAction(form){   
       form.submit();
    }
    var bar =new ToolBar("myBar","修改学籍信息",null,true,true);
    bar.addBack("<@msg.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>