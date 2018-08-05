   <table class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>详细查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <form name="stdSearchForm" action="assistantTutor.do?method=search"  target="contentFrame" method="post" onsubmit="return false;">
    <tr><td><@bean.message key="attr.student.code"/>:</td><td><input type="text" name="assistant.std.code" maxlength="32" value="" style="width:80px;" /></td></tr>
    <tr><td><@bean.message key="attr.personName"/>:</td><td><input type="text" name="assistant.std.name" maxlength="20" value="" style="width:80px;" /></td></tr>
    <tr><td><@bean.message key="attr.enrollTurn"/>:</td><td><input type="text" name="assistant.std.enrollYear" maxlength="7" value="" style="width:80px;" /></td></tr>
    <tr><td align="center" colspan="2"><button onclick="search();"><@bean.message key="action.query"/></button></td></tr>
    </form>
  </table>