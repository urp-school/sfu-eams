  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
    <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>学位审核标准查询</B>
      </td>
    <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
   </tr>
    <form name="searchForm" target="contentListFrame" method="post">
   	<tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="standard.name" style="width:100px;" maxlength="20"/></td></tr>
   	<tr><td><@bean.message key="entity.studentType"/>:</td>
   	    <td><@htm.i18nSelect datas=stdTypeList selected="0" name="standard.stdType.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@></td>
    </tr>
   	<tr><td>一专业:</td>
   	    <td><@htm.select2 selected="1" name="standard.majorType.id" hasAll=false style="width:100px"/></td>
    </tr>
    <tr><td align="center" colspan="2"><button onclick="search();"><@bean.message key="action.query"/></button></td></tr>
    </form>
  </table>
