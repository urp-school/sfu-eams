<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <body>
 <table id="myBar" width="100%" ></table>
  <table width="90%" align="center" class="formTable">
   <#assign basicInfo=std.basicInfo/>
   <form name="commonForm" action="stdDetail.do?method=saveBasicInfo" method="post" onsubmit="return false;">
       <input type="hidden" name="basicInfo.id" value="${basicInfo.id}">
	   <tr>
	     <td class="title" id="f_gender" width="20%"><font color="red">*</font>&nbsp;<@bean.message key="attr.gender"/>：</td>
	     <td>
	      <@htm.i18nSelect datas=genders selected=(basicInfo.gender.id)?default('')?string name="basicInfo.gender.id" style="width:100px;"/>
	     </td>
	     <td class="title" id="f_birthday"><font color="red">*</font>&nbsp;<@bean.message key="attr.birthday"/>：</td>
	     <td>
	      <input type="text" name="basicInfo.birthday" maxlength="10" size="10" value="<#if basicInfo.birthday?exists>${basicInfo.birthday?string('yyyy-MM-dd')}</#if>"/>
	      <input type="button" value="日期" class="buttonStyle" style="width:40px" onclick="calendar(this.form['basicInfo.birthday'])">
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_country">&nbsp;<@bean.message key="entity.country"/>：</td>
	     <td>
	      <@htm.i18nSelect datas=countries selected=(basicInfo.country.id)?default('')?string name="basicInfo.country.id" style="width:100px;" >
	       <option value="">...</option>
	      </@>
	     </td>
	     <td class="title" id="f_nation">&nbsp;<@bean.message key="entity.nation"/>：</td>
	     <td>
	     <@htm.i18nSelect datas=nations selected=(basicInfo.nation.id)?default('')?string name="basicInfo.nation.id" style="width:100px;" >
	       <option value="">...</option>
	      </@>
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_ancestralAddress">&nbsp;<@bean.message key="attr.ancestralAddress"/>：</td>
	     <td><input name="basicInfo.ancestralAddress" value="${basicInfo.ancestralAddress?if_exists}" maxlength="100"/></td>
	     <td class="title" id="f_idCard">&nbsp;<@bean.message key="std.idCard"/>：</td>
	     <td><input type="text" name="basicInfo.idCard" maxlength="18" value="${basicInfo.idCard?if_exists}"/></td>
	   </tr>  
       <tr>
	     <td class="title" id="f_politicVisage">&nbsp;<@bean.message key="entity.politicVisage"/>：</td>
	     <td>
 	      <@htm.i18nSelect datas=politicVisages selected=(basicInfo.politicVisage.id)?default('')?string name="basicInfo.politicVisage.id" style="width:100px;">
	       <option value="">...</option>
          </@>
	     </td>
	     <td class="title" id="f_maritalStatus">&nbsp;婚姻状况：</td>
	     <td>
	     <@htm.i18nSelect datas=maritalStatuses selected=(basicInfo.maritalStatus.id)?default('')?string name="basicInfo.maritalStatus.id" style="width:100px;"/>
	     </td>
	   </tr>	
	   <tr>
	     <td class="title" id="f_postCode">&nbsp;<@bean.message key="attr.postCode"/>：</td>
	     <td><input type="text" name="basicInfo.postCode" maxlength="16" value="${basicInfo.postCode?if_exists}"/></td>
	     <td class="title" id="f_homeAddress">&nbsp;<@bean.message key="attr.familyAddress"/>：</td>
	     <td><input name="basicInfo.homeAddress" value="${basicInfo.homeAddress?if_exists}"/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_phone">&nbsp;<@bean.message key="attr.phoneOfHome"/>：</td>
	     <td><input type="text" name="basicInfo.phone" maxlength="25" value="${basicInfo.phone?if_exists}"/></td>
	     <td class="title" id="f_parentName">&nbsp;父母姓名：</td>
	     <td><input name="basicInfo.parentName" value="${(basicInfo.parentName)?if_exists}"/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_workPlace">&nbsp;工作单位：</td>
	     <td><input name="basicInfo.workPlace" value="${(basicInfo.workPlace)?if_exists}"/></td>
	     <td class="title" id="f_workAddress"> &nbsp;工作地址：</td>
	     <td><input name="basicInfo.workAddress" value="${(basicInfo.workAddress)?if_exists}"/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_workPlacePostCode">&nbsp;工作<@bean.message key="attr.postCode"/>：</td>
	     <td><input type="text" name="basicInfo.workPlacePostCode" maxlength="16" value="${(basicInfo.workPlacePostCode)?if_exists}"/></td>
	     <td class="title" id="f_workPhone">&nbsp;工作单位电话：</td>
	     <td><input type="text" name="basicInfo.workPhone" maxlength="25" value="${(basicInfo.workPhone)?if_exists}"/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_mobile">&nbsp;<@bean.message key="attr.mobile"/>：</td>
	     <td><input type="text" name="basicInfo.mobile" maxlength="25" value="${basicInfo.mobile?if_exists}"/></td>
	     <td class="title" id="f_mail">&nbsp;<@bean.message key="attr.email"/>：</td>
	     <td><input type="text" name="basicInfo.mail" maxlength="100" value="${basicInfo.mail?if_exists}"/></td>
	   </tr>

	   <tr class="darkColumn">
	     <td colspan="4" align="center" >
	       <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;
	       <input type="reset" value="<@bean.message key="system.button.reset"/>" name="reset1" class="buttonStyle"/>
	     </td>
	   </tr>
     </form>
     </table>
  <script language="javascript" >
    function doAction(form){   
     var studentBasicInfo_fields = {
         'basicInfo.gender.id':{'l':'<@bean.message key="attr.gender"/>', 'r':true, 't':'f_gender'},
         'basicInfo.birthday':{'l':'<@bean.message key="attr.birthday"/>', 'r':true, 'f':'date', 't':'f_birthday'},
         'basicInfo.mail':{'l':'<@bean.message key="attr.email"/>', 'r':false, 't':'f_mail', 'f':'email'}
     };
     var v3 = new validator(form, studentBasicInfo_fields, null);
     if (v3.exec()) {
        form.submit();
     }
    }
    var bar =new ToolBar("myBar","修改基本信息",null,true,true);
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addBack("<@msg.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>