<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" action="updateStudentBasicInfo.do" method="post" action="" onsubmit="return false;">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="std.update"/></B>
    </td>
   </tr>
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
	   <#--学生基本信息-->
	   <#assign stdId=RequestParameters['stdId'] />
	   <#include "studentBasicInfo.ftl">
	   <tr class="darkColumn">
	     <td colspan="2" align="center" >
	       <input type="hidden" name="method" value="updateStudentBasicInfo" />
	       <input type="button" value="<@bean.message key="system.button.submit" />" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
	     </td>
	   </tr>   
     </table>
    </td>
   </tr>
   </form>
  </table>
  <script language="javascript" >
    function doAction(form){   

     var studentBasicInfo_fields = {
         'basicInfo.gender.id':{'l':'<@bean.message key="attr.gender"/>', 'r':true, 't':'f_gender'},
         'basicInfo.birthday':{'l':'<@bean.message key="attr.birthday"/>', 'r':true, 'f':'date', 't':'f_birthday'}
     };
     var v3 = new validator(form, studentBasicInfo_fields, null);
     if (v3.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>