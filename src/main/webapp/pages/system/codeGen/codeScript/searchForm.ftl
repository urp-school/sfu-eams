  <table width="100%" onkeypress="DWRUtil.onReturn(event, searchCodeScript)">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>代码生成规则查询</B>
      </td>
    <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
    <form name="searchForm" target="contentListFrame" method="post" onsubmit="return false;">
    <tr><td>编码对象:</td><td><input type="text" name="codeScript.codeName" style="width:100px;" maxlength="32"/></td></tr>
   	<tr><td>编码规则:</td><td><input type="text" name="codeScript.description" style="width:100px;" maxlength="20"/></td></tr>
    <tr height="50px"><td align="center" colspan="2"><input type="button" onclick="searchCodeScript();" value="<@bean.message key="action.query"/>" class="buttonStyle"/></td></tr>
    </form>
  </table>
