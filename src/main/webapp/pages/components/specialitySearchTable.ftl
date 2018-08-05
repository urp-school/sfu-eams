 <table onkeypress="DWRUtil.onReturn(event, searchSpeciality)" width="100%">
     <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@bean.message key="baseinfo.searchSpeciality"/></B>
      </td>
     <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
    </tr>
    <tr><td><@bean.message key="attr.code"/>:</td><td><input type="text" name="speciality.code" style="width:100px;" maxlength="32"/></td></tr>
   	<tr><td><@bean.message key="attr.infoname"/>:</td><td><input type="text" name="speciality.name" style="width:100px;" maxlength="25"/></td></tr>
    <tr><td><@bean.message key="entity.studentType"/>:</td>
        <td><@htm.i18nSelect datas=stdTypes selected="0"  name="speciality.stdType.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@></td>
    </tr>
    <tr><td><@bean.message key="common.college"/>:</td>
        <td><@htm.i18nSelect datas=departments selected="0" name="speciality.department.id" style="width:100px;"><option value=""><@bean.message key="common.all"/></option></@></td>
    </tr>
    <tr>
       <td class="title">学科门类</td>
       <td>
         <@htm.i18nSelect datas=subjectCategories selected=(speciality.subjectCategory.id)?default("")?string  name="speciality.subjectCategory.id" style="width:100%">
         	<option value="">...</option>
         </@htm.i18nSelect>
       </td>
    </tr>
    <tr>
       <td class="title">专业类别:</td>
       <td>
       	   <select name="speciality.majorType.id" style="width:100%">
       	   	  <option value="">...</option>
			  <#list majorTypes as majorType>        
              <option value="${majorType.id}">${majorType.name}</option>
              </#list>
           </select>        
       </td>
    </tr>
    ${extraSearchOptions?default("<input type='hidden' name='speciality.state' value='1'>")}
    <tr height="50px"><td colspan="2" align="center"><button onclick="searchSpeciality();"><@bean.message key="action.query"/></button></td></tr>
</table>