 <table width="100%" onkeypress="DWRUtil.onReturn(event, searchSpecialityAspect)">
     <tr>
      <td colspan="2" class="infoTitle" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/>
          <B><@bean.message key="baseinfo.searchSpecialityAspect"/></B>
      </td>
    </tr>
     <tr>
      <td colspan="2" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
      </td>
    </tr>
    <tr>
        <td><@bean.message key="attr.code"/>:</td>
        <td><input type="text" name="specialityAspect.code" style="width:100px;" maxlength="32"/></td>
    </tr>
    <tr>
        <td><@bean.message key="attr.infoname"/>:</td>
        <td><input type="text" name="specialityAspect.name" style="width:100px;" maxlength="50"/></td>
    </tr>
   	<tr>
   	    <td><@bean.message key="entity.studentType"/>:</td>
   	    <td>
            <select id="stdTypeOfSpeciality" name="specialityAspect.speciality.stdType.id" style="width:100px;">
                <option value=""></option>
            </select>
        </td>
    </tr>
   	<tr>
   	  <td><@bean.message key="common.college"/>:</td><td>
         <select id="department" name="specialityAspect.speciality.department.id" style="width:100px;">
           <option value=""><@bean.message key="common.selectPlease"/></option>
         </select>
      </td>
    </tr>
    <tr><td><@bean.message key="entity.speciality"/>：</td>
      <td>
         <select id="speciality" name="specialityAspect.speciality.id" style="width:100px;">
           <option value=""><@bean.message key="common.selectPlease"/></option>
         </select>
       </td>
    </tr>
    ${extraSearchOptions?default("<input type='hidden' name='specialityAspect.state' value='1'/>")}
    <tr height="50px"><td colspan="2" align="center"><input type="button" onclick="searchSpecialityAspect()" value="<@bean.message key="action.query"/>" class="buttonStyle"/></td></tr>
  </table>