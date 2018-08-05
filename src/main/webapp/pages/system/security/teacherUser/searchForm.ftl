   <table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchTeacherUser)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>教师用户查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <form name="teacherUserSearchForm" action="teacherUser.do?method=search" onsubmit="return false;" target="contentFrame" method="post">
    <tr><td> <@bean.message key="teacher.code"/>:</td><td><input type="text" name="teacher.code"  value="${RequestParameters['teacher.code']?if_exists}" style="width:100px;" maxlength="32"/></td></tr>
    <tr><td><@bean.message key="attr.personName"/>:</td><td><input type="text" name="teacher.name"  value="${RequestParameters['teacher.name']?if_exists}" style="width:100px;" maxlength="50"/></td></tr>
    <tr><td><@bean.message key="common.college"/>&nbsp;&nbsp;:</td><td>
    <@htm.i18nSelect datas=departments selected="RequestParameters['teacher.department.id']?default('')" name="teacher.department.id" style="width:100px">
    <option value=""><@bean.message key="common.all"/></option></@></td>
    </tr>
    <tr>
	    <td class="title">在职状态:</td>
	    <td><@htm.i18nSelect datas=teacherWorkStateList selected=RequestParameters['teacher.state.id']?default("") name="teacher.state.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@></td>
	</tr>
    <tr><td align="center" colspan="2"><button onclick="searchTeacherUser();"><@bean.message key="action.query"/></button></td></tr>
    </form>
 </table>