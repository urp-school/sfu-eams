  <table class="searchTable" width="100%">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>基础代码查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <tr><td><@bean.message key="attr.code"/>:</td><td><input type="text" name="teachProjectType.code" style="width:100px;" maxlength="32"/></td></tr>
    <tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="teachProjectType.name" style="width:100px;" maxlength="25"/></td></tr>
    <tr><td colspan="2" align="center"><button onclick="search()"><@bean.message key="action.query"/></button></td></tr>
  </table>