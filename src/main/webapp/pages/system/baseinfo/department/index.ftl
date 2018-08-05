<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="myBar"></table>
  <table class="frameTable">
   <tr valign="top">
    <td width="20%" class="frameTable_view">
        <#include "searchForm.ftl"/>
        <table><tr height="400px"><td></td></tr></table>
    </td>
    <td>
	  <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe> 
    </td>
   </tr>
  </table>
  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    type="department";
    keys="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state,dateEstablished,isCollege,isTeaching";
    titles="<@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="attr.engName"/>,<@msg.message key="attr.abbreviation"/>,<@msg.message key="attr.description"/>,<@msg.message key="attr.createAt"/>,<@msg.message key="attr.modifyAt"/>,<@msg.message key="attr.remark"/>,<@msg.message key="attr.state"/>,<@msg.message key="attr.dateEstablished"/>,<@msg.message key="department.isCollege" />,<@msg.message key="department.isTeaching" />";
    labelInfo="<@bean.message key="page.departmentListFrame.label" />";
    search();
    searchDepartment=search;
    initBaseInfoBar();
  </script>
</body>
<#include "/templates/foot.ftl"/>