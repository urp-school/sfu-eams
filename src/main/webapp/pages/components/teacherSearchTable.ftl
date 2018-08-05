  <table width="100%" onkeypress="DWRUtil.onReturn(event, searchTeacher)">
     <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@bean.message key="baseinfo.searchTeacher"/></B>
      </td>
     <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
     </tr>
     <tr><td><@bean.message key="teacher.code"/>：</td><td><input type="text" name="teacher.code" style="width:100px;" value="${RequestParameters['teacher.code']?if_exists}" maxlength="32"/></td></tr>
     <tr><td><@bean.message key="attr.personName"/>:</td><td><input type="text" name="teacher.name" value ="${RequestParameters['teacher.name']?if_exists}"style="width:100px;" maxlength="20"/></td></tr>
     <tr><td><@bean.message key="common.college"/>:</td>
         <td><@htm.i18nSelect datas=departments?sort_by("name") selected=RequestParameters['teacher.department.id']?default("") name="teacher.department.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@></td>
     </tr>
     <tr><td>教师类别:</td>
         <td><@htm.i18nSelect datas=teacherTypes selected=RequestParameters['teacher.teacherType.id']?default("") name="teacher.teacherType.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@></td>
     </tr>
     <tr><td>在职状态:</td>
         <td><@htm.i18nSelect datas=teacherWorkStateList selected=RequestParameters['teacher.workState.id']?default("") name="teacher.workState.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@></td>
     </tr>
     ${extraSearchOptions?default("<input type='hidden' name='teacher.isTeaching' value='1'/>")}
     <tr height="50px"><td align="center" colspan="2"><button onclick="searchTeacher();"><@bean.message key="action.query"/></button></td></tr>
 </table>