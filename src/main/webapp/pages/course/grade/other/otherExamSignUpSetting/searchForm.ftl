  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchSetting)" width="100%">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>详细查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
    <form name="searchForm" action="" target="contentListFrame" method="post" onsubmit="return false;">
    <tr><td width="30%"><@bean.message key="attr.code"/>:</td><td><input type="text" name="setting.examCategory.code" value="${RequestParameters["setting.examCategory.code"]?if_exists}" maxlength="32" style="width:100px;"/></td></tr>
   	<tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="setting.examCategory.name" value="${RequestParameters["setting.examCategory.name"]?if_exists}" maxlength="20" style="width:100px;"/></td></tr>
    <tr height="50px"><td align="center" colspan="2"><input type="button" onclick="searchSetting();" value="<@bean.message key="action.query"/>" class="buttonStyle"/></td></tr>
    </form>
  </table>
