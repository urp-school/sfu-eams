<table wdith="100%" onkeypress="DWRUtil.onReturn(event, searchSchoolDistrict)">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@msg.message key="baseinfo.searchSchoolDistrict"/></B>
      </td>
    <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
    <form name="searchForm" action="schoolDistrict.do?method=search" target="contentListFrame" method="post" onsubmit="return false;">
    <tr><td width="40%"><@bean.message key="attr.code"/>:</td><td><input type="text" name="schoolDistrict.code" style="width:100px;" maxlength="32"/></td></tr>
   	<tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="schoolDistrict.name" style="width:100px;" maxlength="25"/></td></tr>
    <#include "../../base.ftl"/>
    <@stateSelect "schoolDistrict"/>
   	<tr height="50px"><td align="center" colspan="2"><button onclick="searchSchoolDistrict();"><@bean.message key="action.query"/></button></td></tr>
    </form>
  </table>
