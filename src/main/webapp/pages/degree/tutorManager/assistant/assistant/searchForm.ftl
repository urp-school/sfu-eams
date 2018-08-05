   <table width="100%" onkeypress="DWRUtil.onReturn(event, search)">
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
    <form name="stdSearchForm" action="assistant.do?method=search"  target="contentFrame" method="post" onsubmit="return false;">
    <tr><td width="40%"><@msg.message key="attr.student.code"/>：</td><td><input type="text" name="assistant.std.code" maxlength="32" value="" style="width:100px;"/></td></tr>
    <tr><td><@msg.message key="attr.personName"/>：</td><td><input type="text" name="assistant.std.name" maxlength="20" value="" style="width:100px;"/></td></tr>
    <tr><td><@msg.message key="attr.enrollTurn"/>：</td><td><input type="text" name="assistant.std.enrollYear" maxlength="7" value="" style="width:100px;"/></td></tr>
    <tr><td><@msg.message key="entity.studentType"/>：</td>
        <td>
       <select name="assistant.std.type.id" style="width:100px">
               <option value=""><@msg.message key="common.all"/></option>
               <#list stdTypeList as stdType>
               <option value="${stdType.id}" 
                  <#if RequestParameters['student.type.id']?if_exists==stdType.id?string> selected </#if>>
                  <@i18nName stdType/></option>
               </#list>
       </select>
     <tr><td><@msg.message key="common.college"/>：</td><td>
           <select name="assistant.std.department.id" style="width:100px">
               <option value=""><@msg.message key="common.all"/></option>
               <#list departmentList as depart>
               <option value="${depart.id}"  <#if RequestParameters['student.department.id']?if_exists==depart.id?string> selected</#if> ><@i18nName depart/></option>
               </#list>
           </select>
     </td></tr>
    </tr>
    <tr><td>导师姓名：</td><td><input type="text" name="assistant.tutor.name" maxlength="20" value="" style="width:100px;"/></td></tr>
    <tr height="50px"><td align="center" colspan="2"><button onclick="search();"><@msg.message key="action.query"/></button></td></tr>
    </form>
  </table>
  <table height="400"><tr><td></td></tr></table>