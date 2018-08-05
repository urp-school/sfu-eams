  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>精品课程查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <form name="searchForm" action="fineCourse.do?method=search" target="contentListFrame" method="post" action="" onsubmit="return false;">
    <tr><td><@bean.message key="attr.courseName"/>:</td><td><input type="text" name="fineCourse.courseName" style="width:80px;" maxlength="20"/></td></tr>
   	<tr><td>等级:</td><td><@htm.i18nSelect datas=levels name="fineCourse.level.id" selected="" style="width:100px"><option value="">请选择..</option></@></td></tr>
   	<tr><td align="center" colspan="2"><input type="button" onclick="search();" value="<@bean.message key="action.query"/>" class="buttonStyle"/></td></tr>
    </form>
  </table>
  