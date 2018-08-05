   <table class="searchTable" onkeypress="DWRUtil.onReturn(event, searchStdUser)">
    <tr>
      <td  colspan="2" class="infoTitle" align="left" valign="bottom" >
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B>学生用户查询</B>
      </td>
    <tr>
      <td  colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
    <form name="stdUserSearchForm" action="stdUser.do?method=search" onsubmit="return false;" target="contentFrame" method="post">
    <input type="hidden" name="pageNo" value="1" />
    <tr><td><@bean.message key="attr.student.code"/>:</td><td><input type="text" name="student.code" value="" style="width:80px;" maxlength="32"/></td></tr>
    <tr><td><@bean.message key="attr.personName"/>:</td><td><input type="text" name="student.name" value="" style="width:80px;" maxlength="50"/></td></tr>
    <tr><td><@bean.message key="attr.enrollTurn"/>:</td><td><input type="text" name="student.enrollYear" value="" style="width:80px;" maxlength="7"/></td></tr>
    <tr><td><@bean.message key="entity.studentType"/>:</td>
        <td>
       <select name="student.type.id" style="width:100px">
               <option value=""><@bean.message key="common.all"/></option>              
               <#list result.stdTypeList as stdType>               
               <option value="${stdType.id}" 
                  <#if RequestParameters['student.type.id']?if_exists==stdType.id?string> selected </#if>>
                  <@i18nName stdType/></option>
               </#list>
       </select>
     <tr><td><@bean.message key="common.college"/>&nbsp;&nbsp;:</td><td>
           <select name="student.department.id" style="width:100px">
               <option value=""><@bean.message key="common.all"/></option>
               <#list result.departmentList as depart>               
               <option value="${depart.id}"  <#if RequestParameters['student.department.id']?if_exists==depart.id?string> selected</#if> ><@i18nName depart/></option>
               </#list>
           </select>    
     </td></tr>    
    </tr>
     <tr><td align="center" colspan="2"><button onclick="searchStdUser();"><@bean.message key="action.query"/></button></td></tr>
     </form>
	</table>