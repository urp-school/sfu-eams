  <table width="100%" onkeypress="DWRUtil.onReturn(event, searchStudentType)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>学生类别查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <form name="searchForm" action="studentType.do?method=search" target="contentListFrame" method="post" onsubmit="return false;">
    <tr><td width="40%"><@bean.message key="attr.code"/>:</td><td><input type="text" name="studentType.code" maxlength="32" style="width:100px;"/></td></tr>
   	<tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="studentType.name"  style="width:100px;" maxlength="20"/></td></tr>
    <#include "../../base.ftl"/>
    <@stateSelect "studentType"/>
   	<tr height="50px"><td colspan="2" align="center"><button onclick="searchStudentType();"><@bean.message key="action.query"/></button></td></tr>
    </form>
  </table>