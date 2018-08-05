	   <table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>详细查询(模糊输入)</B>
	      </td>
	    </tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
	   </tr>
	  </table>
	  <table class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
	    <tr> 
	     <td class="infoTitle"><@bean.message key="attr.stdNo"/>:</td>
	     <td><input name="signUpStd.std.code" type="text" maxlength="32" style="width:100px" value="${RequestParameters["signUpStd.std.code"]?if_exists}"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.personName"/>:</td>
	     <td><input name="signUpStd.std.name" type="text" maxlength="20" value="${RequestParameters["signUpStd.std.name"]?if_exists}" style="width:100px"/></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="attr.enrollTurn"/>:</td>
	     <td><input type="text" name="signUpStd.std.enrollYear"  value="${RequestParameters["signUpStd.std.enrollYear"]?if_exists}" maxlength="7" style="width:100px"></td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.studentType"/>:</td>
	     <td>
		     <select name="signUpStd.std.type.id" value="${RequestParameters["signUpStd.std.type.id"]?if_exists}" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<option value=${studentType.id}><@i18nName studentType/></option>
		     	<#list calendarStdTypes?sort_by("code") as stdType>
		     	<option value=${stdType.id}><@i18nName stdType/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle"><@bean.message key="entity.department"/>:</td>
	     <td>
		     <select name="signUpStd.std.department.id" value="${RequestParameters["signUpStd.std.department.id"]?if_exists}" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(departmentList) as depart>
                <option value=${depart.id}><@i18nName depart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    <tr>
	     <td class="infoTitle" ><@bean.message key="entity.speciality"/>:</td>
	     <td>
	        <select name="specialitySetting.id" style="width:100px" value="${RequestParameters["specialitySetting.id"]?if_exists}">
	         <option value=""><@bean.message key="common.all"/></option>
	         <#list (signUpSetting.specialitySettings)?if_exists as specialitySetting>
	           <option value="${specialitySetting.id}"><@i18nName specialitySetting.aspect/></option>
	         </#list>
	         </select>
	    </tr>
	    
	    <tr>
	     <td class="infoTitle">申报院系</td>
	     <td>
		     <select name="signUpStd.matriculated.speciality.department.id" value="${RequestParameters["signUpStd.matriculated.speciality.department.id"]?if_exists}" style="width:100px">
		     	<option value=""><@bean.message key="common.all"/></option>
		     	<#list sort_byI18nName(departmentList) as depart>
                <option value=${depart.id}><@i18nName depart/></option>
		     	</#list>
		     </select>
	     </td>
	    </tr>
	    
	    <tr>
	      <td class="infoTitle">服从调剂</td>
	      <td><@htm.select2  name="signUpStd.isAdjustable" selected=(RequestParameters["signUpStd.isAdjustable"]?string)?default("") hasAll=true style="width:100px"/></td>
	    </tr>
	    <tr>
	      <td class="infoTitle">是否录取</td>
	      <td><@htm.select2  name="isMatriculated" selected=(RequestParameters["isMatriculated"]?string)?default("") hasAll=true style="width:100px"/></td>
	    </tr>
	    <tr align="center">
	     <td colspan="2">
		     <button onClick="search()" accesskey="Q" class="buttonStyle" style="width:60px">
		       <@bean.message key="action.query"/>(<U>Q</U>)
		     </button>
	     </td>
	    </tr>
	  </table>