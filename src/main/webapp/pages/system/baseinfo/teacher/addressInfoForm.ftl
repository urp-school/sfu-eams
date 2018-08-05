     <table  width="90%" align="center" class="formTable">
       <tr  class="darkColumn">
          <td colspan="4"><input type="checkbox" onClick="displayDiv(event,'addressInfo')"/><@bean.message key="attr.addressInfo"/></td>
       </tr>
     </table>
     
     <div id="addressInfo" style="display:none">  
	 <table  width="90%"  align="center" class="formTable">
	   <tr>
	     <td width="15%"  class="title"><@bean.message key="common.phoneOfCorporation"/>: </td>
	     <td width="35%" ><input type="text"  name="teacher.addressInfo.phoneOfCorporation" maxlength="18" value="${teacher.addressInfo?if_exists.phoneOfCorporation?if_exists}"></td>
         <td width="15%"  id="f_postCodeOfCorporation" class="title"><@bean.message key="common.postCodeOfCorporation"/>: </td>
	     <td width="35%" ><input type="text" maxlength="6" name="teacher.addressInfo.postCodeOfCorporation" value="${teacher.addressInfo?if_exists.postCodeOfCorporation?if_exists}"></td>
	   </tr>
	   <tr>
         <td  class="title"><@bean.message key="common.corporationAddress"/>: </td>
	     <td colspan="3" ><input type="text"  name="teacher.addressInfo.corporationAddress" value="${teacher.addressInfo?if_exists.corporationAddress?if_exists}" style="width:522px;"  maxLength="100px"></td>
	   </tr>
       <tr>
	     <td  class="title"><@bean.message key="common.phoneOfHome"/>: </td>
	     <td><input type="text" name="teacher.addressInfo.phoneOfHome" maxlength="18" value="${teacher.addressInfo?if_exists.phoneOfHome?if_exists}"></td>
         <td  id="f_postCodeOfFamily" class="title"><@bean.message key="common.postCodeOfFamily"/>: </td>
	     <td><input type="text" name="teacher.addressInfo.postCodeOfFamily" maxlength="6" value="${teacher.addressInfo?if_exists.postCodeOfFamily?if_exists}"></td>
	   </tr>
	   <tr>
         <td  class="title"><@bean.message key="common.familyAddress"/>: </td>
	     <td colspan="3" ><input type="text"  name="teacher.addressInfo.familyAddress" value="${teacher.addressInfo?if_exists.familyAddress?if_exists}" style="width:522px;" maxLength="100px"></td>
	   </tr>
       <tr>
	     <td  id="f_mobilePhone" class="title"><@bean.message key="common.mobilePhone"/>: </td>
	     <td><input type="text" name="teacher.addressInfo.mobilePhone"  maxlength="18" value="${teacher.addressInfo?if_exists.mobilePhone?if_exists}"></td>
         <td  class="title"><@bean.message key="common.fax"/>: </td>
	     <td><input type="text" maxlength="18" name="teacher.addressInfo.fax" value="${teacher.addressInfo?if_exists.fax?if_exists}"></td>
	   </tr>
	   <tr>
         <td  id="f_email" class="title"><@bean.message key="common.email"/>: </td>
	     <td colspan="3" ><input type="text"  name="teacher.addressInfo.email" value="${teacher.addressInfo?if_exists.email?if_exists}" style="width:522px;"  maxLength="100px"></td>
	   </tr>
	   <tr>
	   <tr>
         <td  class="title"><@bean.message key="teacher.homepage"/>: </td>
	     <td colspan="3" ><input type="text"  name="teacher.addressInfo.homepage" value="${teacher.addressInfo?if_exists.homepage?if_exists}" style="width:522px;"  maxLength="100px"></td>
	   </tr>
	   </table>
	   </div>