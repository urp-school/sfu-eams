  <table width="100%" onkeypress="DWRUtil.onReturn(event, searchDepartment)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@msg.message key="baseinfo.searchDepartment"/></B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
    <form name="searchForm" action="department.do?method=search" target="contentListFrame" method="post" onsubmit="return false;">
    <tr><td><@bean.message key="attr.code"/>:</td><td><input type="text" name="department.code" style="width:100px;"/></td></tr>
   	<tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="department.name" style="width:100px;"/></td></tr>
   	<tr><td><@bean.message key="department.isTeaching"/>:</td><td><select name="department.isTeaching" style="width:100px;"><option value=""><@msg.message key="common.all"/></option><option value="1"><@msg.message key="common.yes"/></option><option value="0"><@msg.message key="common.no"/></option></select></td></tr>
   	<tr><td><@bean.message key="department.isCollege"/>:</td><td><select name="department.isCollege" style="width:100px;"><option value=""><@msg.message key="common.all"/></option><option value="1"><@msg.message key="common.yes"/></option><option value="0"><@msg.message key="common.no"/></option></select></td></tr>
    <#include "../../base.ftl"/>
    <@stateSelect "department"/>
   	<tr height="50px"><td align="center" colspan="2"><input type="button" onclick="searchDepartment();" value="<@bean.message key="action.query"/>" class="buttonStyle"/></td></tr>
    </form>
  </table>
