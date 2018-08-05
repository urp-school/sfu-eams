<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
<table cellpadding="0" cellspacing="0" width="100%" border="0">	
	<form name="commonForm" action="studentStatusAlterRequisition.do" method="post" onsubmit="return false;">
		<br>
		<tr>
			<td align="center" colspan="6" class="contentTableTitleTextStyle" bgcolor="#ffffff">
				<B>新增学籍变动审批表</B>
			</td>
		</tr>
			<table width="90%" align="center" class="listTable">
			<#assign std = result.student />
			<tr colSpan="18">
				<td align="center" class="darkColumn"><@msg.message key="attr.stdNo"/></td>
				<td class="grayStyle" >&nbsp;${std.code}</td>
				<td align="center" class="darkColumn"><@msg.message key="attr.personName"/></td>
				<td class="grayStyle" >&nbsp;<@i18nName std /></td>
			</tr>
			<tr>
				<td align="center" class="darkColumn"><@msg.message key="entity.studentType"/></td>
				<td class="grayStyle">&nbsp;<@i18nName std.type?if_exists /></td>
                <td align="center" class="darkColumn" >学院</td>
				<td class="grayStyle">&nbsp;<@i18nName std.department?if_exists /></td>
			</tr>
			<tr>				
				<td align="center" class="darkColumn"><@msg.message key="entity.speciality"/></td>
				<td class="grayStyle">&nbsp;<@i18nName std.firstMajor?if_exists /></td>
				<td align="center" class="darkColumn"><@msg.message key="entity.specialityAspect"/></td>
				<td class="grayStyle">&nbsp;<@i18nName std.firstAspect?if_exists /></td>
			</tr>
			<tr>
				<td align="center" class="darkColumn" id="f_homeAddress"><@bean.message key="attr.familyAddress"/><font color="red">*</font></td>
				<td class="brightStyle" colspan="3"><input type="text" name="basicInfo.homeAddress" maxlength="100" style="width:100%;border:1 solid #000000;" value="${std.basicInfo.homeAddress?default("")?html}"/></td>
			</tr>
			<tr>
				<td align="center" class="darkColumn" id="f_postCode"><@bean.message key="attr.postCode"/><font color="red">*</font></td>
				<td class="brightStyle"><input type="text" name="basicInfo.postCode" maxlength="10" style="width:100%;border:1 solid #000000;" value="${std.basicInfo.postCode?default("")?html}"/></td>
				<td align="center" class="darkColumn" id="f_phone"><@bean.message key="attr.phoneOfHome"/><font color="red">*</font></td>
				<td class="brightStyle"><input type="text" name="basicInfo.phone" maxlength="15" style="width:100%;border:1 solid #000000;" value="${std.basicInfo.phone?default("")?html}"/></td>
			</tr>
			<tr>
				<td align="center" class="darkColumn" id="f_alterDate" width="20%">学籍变动日期<font color="red">*</font></td>
				<td class="brightStyle"><input type="text" style="width:100%;border:1 solid #000000;" name="record.alterDate" onfocus="calendar()" size="10"/></td>	
				<td align="center" class="darkColumn"  width="20%">变动截止日期</td>
				<td class="brightStyle"><input type="text" style="width:100%;border:1 solid #000000;" name="record.endDate" onfocus="calendar()" size="10"/></td>			
			</tr>
			<tr>
				<td align="center" class="darkColumn" id="f_alterationType">学籍变动种类<font color="red">*</font></td>
				<td class="brightStyle">
					<select id="record.alterationType.id" name="record.alterationType.id" style="width:100%;border:0 solid #000000;" >
						<#list result.alterationTypeList?if_exists?sort_by("code") as type>
	       					<option value="${type.id}"><@i18nName type/></option>
	       				</#list>
					</select>
				</td>
				<td align="center" class="darkColumn" >变更原因</td>
				<td class="brightStyle" >
				<select id="record.alterationReason.id" name="record.alterationReason.id" style="width:100%;border:0 solid #000000;" >
				</select>
				</td>
				<#include "/templates/studentStatusAlteration2Select.ftl"/>
			</tr>
			<tr>
				<td align="center" class="darkColumn" >变更理由备注<br></td>
				<td class="brightStyle" colSpan="3"><textarea name="record.alterationReasonRemark" cols="40" rows="4" style="width:100%;border:1 solid #000000;" ></textarea></td>
			</tr>
			<tr>
				<td align="center" class="darkColumn" >院系意见</td>
				<td class="grayStyle" colSpan="3" style="word-warp:break-word;word-break:break-all">&nbsp;</td>
			</tr>
			<tr>
				<td align="center" class="darkColumn" >备注</td>
				<td class="grayStyle" colSpan="3" style="word-warp:break-word;word-break:break-all">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="4" align="center" class="darkColumn">
				    <input type="hidden" name="method" value="addAlterRequisition"/>
				    <input type="button" onClick="addAlterRequisition()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
				    <input type="reset" onClick="document.commonForm.reset()" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>
       			</td>
			</tr>
			</table>
		</tr>
	</form>
</table>
<script language="javascript" >
	function addAlterRequisition(){
		//document.commonForm.submit();
		doAction(document.commonForm);
	}
	
    function doAction(form){

     var fields = {
         'basicInfo.homeAddress':{'l':'<@bean.message key="attr.familyAddress"/>', 'r':true, 't':'f_homeAddress'},
         'basicInfo.postCode':{'l':'<@bean.message key="attr.postCode"/>', 'r':true, 't':'f_postCode', 'f':'unsigned'},
         'basicInfo.phone':{'l':'<@bean.message key="attr.phoneOfHome"/>', 'r':true, 't':'f_phone'},
         'record.alterDate':{'l':'学籍变动日期', 'r':true, 't':'f_alterDate'},
         'record.alterationType.id':{'l':'学籍变动种类', 'r':true, 't':'f_alterationType'}
     };
    
     var v = new validator(form, fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
</script>
</body>
<#include "/templates/foot.ftl"/>