     <table  width="90%" align="center" class="listTable">
       <tr>
          <td colspan="4" class="grayStyle"><input type="checkbox" onClick="displayDiv(event,'addressInfo')"/> 联系信息</td>
       </tr>
     </table>
     
     <div id="addressInfo" style="display:none">  
	 <table  width="90%" align="center" class="listTable">
	   <tr>
	     <td width="15%" align="center" class="grayStyle"><@bean.message key="common.phoneOfCorporation"/>: </td>
	     <td width="35%"  class="brightStyle"><input type="text"  name="tutor.addressInfo.phoneOfCorporation" maxlength="18" value="${tutor.addressInfo?if_exists.phoneOfCorporation?if_exists}"></td>
         <td width="15%" align="center" id="f_postCodeOfCorporation" class="grayStyle"><@bean.message key="common.postCodeOfCorporation"/>: </td>
	     <td width="35%"  class="brightStyle"><input type="text" maxlength="6" name="tutor.addressInfo.postCodeOfCorporation" value="${tutor.addressInfo?if_exists.postCodeOfCorporation?if_exists}"></td>
	   </tr>
	   <tr>
         <td align="center" class="grayStyle"><@bean.message key="common.corporationAddress"/>: </td>
	     <td colspan="3" class="brightStyle"><input type="text" maxlength="100" name="tutor.addressInfo.corporationAddress" value="${tutor.addressInfo?if_exists.corporationAddress?if_exists}" style="width:522px;"></td>
	   </tr>
       <tr>
	     <td align="center" class="grayStyle"><@bean.message key="common.phoneOfHome"/>: </td>
	     <td class="brightStyle"><input type="text" name="tutor.addressInfo.phoneOfHome" maxlength="18" value="${tutor.addressInfo?if_exists.phoneOfHome?if_exists}"></td>
         <td align="center" id="f_postCodeOfFamily" class="grayStyle"><@bean.message key="common.postCodeOfFamily"/>: </td>
	     <td class="brightStyle"><input type="text" name="tutor.addressInfo.postCodeOfFamily" maxlength="6" value="${tutor.addressInfo?if_exists.postCodeOfFamily?if_exists}"></td>
	   </tr>
	   <tr>
         <td align="center" class="grayStyle"><@bean.message key="common.familyAddress"/>: </td>
	     <td colspan="3" class="brightStyle"><input type="text" maxlength="100" name="tutor.addressInfo.familyAddress" value="${tutor.addressInfo?if_exists.familyAddress?if_exists}" style="width:522px;"></td>
	   </tr>
       <tr>
	     <td align="center" id="f_mobilePhone" class="grayStyle"><@bean.message key="common.mobilePhone"/>: </td>
	     <td class="brightStyle"><input type="text" name="tutor.addressInfo.mobilePhone" maxlength="18" value="${tutor.addressInfo?if_exists.mobilePhone?if_exists}"></td>
         <td align="center" class="grayStyle"><@bean.message key="common.fax"/>: </td>
	     <td class="brightStyle"><input type="text" maxlength="18" name="tutor.addressInfo.fax" value="${tutor.addressInfo?if_exists.fax?if_exists}"></td>
	   </tr>
	   <tr>
         <td align="center" id="f_email" class="grayStyle"><@bean.message key="common.email"/>: </td>
	     <td colspan="3" class="brightStyle"><input type="text" maxlength="30" name="tutor.addressInfo.email" value="${tutor.addressInfo?if_exists.email?if_exists}" style="width:522px;"></td>
	   </tr>
	   <tr>
	   <tr>
         <td align="center" class="grayStyle"><@bean.message key="teacher.homepage"/>: </td>
	     <td colspan="3" class="brightStyle"><input type="text" maxlength="100" name="tutor.addressInfo.homepage" value="${tutor.addressInfo?if_exists.homepage?if_exists}" style="width:522px;"></td>
	   </tr>
	   </table>
	   </div>