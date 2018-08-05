  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchPress)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>出版社查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
   <form name="pressForm" action="" method="post" target="contentFrame" onsubmit="return false;">
    <tr><td><@bean.message key="attr.code"/>:</td><td><input type="text" name="press.code" maxlength="32" style="width:100px;" /></td></tr>
    <tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="press.name" maxlength="20" style="width:100px;" /></td></tr>
    <tr><td colspan="2" align="center"><button onclick="searchPress()"><@bean.message key="action.query"/></button></td></tr>
    </form>
  </table>