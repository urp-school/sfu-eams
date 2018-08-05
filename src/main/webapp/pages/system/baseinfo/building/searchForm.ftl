  <table width="100%" onkeypress="DWRUtil.onReturn(event, searchBuilding)">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@msg.message key="baseinfo.searchBuilding"/></B>
      </td>
    <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
    <form name="searchForm" action="building.do?method=search" target="contentListFrame" method="post" onsubmit="return false;">
    <tr><td width="40%"><@bean.message key="attr.code"/>:</td><td><input type="text" name="building.code" style="width:100px;" maxlength="32"/></td></tr>
   	<tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="building.name" style="width:100px;" maxlength="20"/></td></tr>
   	<tr><td><@bean.message key="common.schoolDistrict"/>:</td>
   	    <td><@htm.i18nSelect datas=districts selected="0"  name="building.schoolDistrict.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@>
    </td></tr>
    <#include "../../base.ftl"/>
    <@stateSelect "building"/>
    <tr height="50px"><td align="center" colspan="2"><input type="button" onclick="searchBuilding();" value="<@bean.message key="action.query"/>"   class="buttonStyle"/></td></tr>
    </form>
  </table>
