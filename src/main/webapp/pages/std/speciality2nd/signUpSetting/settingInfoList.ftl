<#macro displaySetting(setting)>
     <table id="bar${setting.id}"></table>
     <table width="100%" align="center" class="infoTable">
	   <tr>
	    <td class="title" width="20%"><@msg.message key="attr.name"/>：</td>
	    <td colspan="3">${setting.name}</td>
	   </tr>
	   <tr>
	    <td class="title" width="20%"><@msg.message key="info.secondSpecialitySignUpSetting.status"/>：</td>
	    <td width="30%">${setting.isOpen?string("开放","关闭")}</td>
	    <td class="title" width="20%"><@msg.message key="attr.requiredGrade"/>：</td>
	    <td >${setting.minGPA?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="title"><@msg.message key="filed.enrollYear"/>：</td>
	    <td >${setting.enrollTurn?if_exists}</td>
	    <td class="title"><@msg.message key="info.secondSpecialitySignUpSetting.signUpCount"/>：</td>
	    <td >${setting.choiceCount?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="title" ><@msg.message key="attr.startDate"/>：</td>
	    <td >${setting.beginOn?if_exists}</td>
	    <td class="title"><@msg.message key="attr.finishDate"/>：</td>
	    <td >${setting.endOn?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="title"><@msg.message key="info.secondSpecialitySignUpSetting.registerDate"/>：</td>
	    <td >${setting.registerOn?if_exists}</td>
	    <td class="title"><@msg.message key="info.secondSpecialitySignUpSetting.registerAddress"/>：</td>
	    <td >${setting.registerAt?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="title">设置专业个数：</td>
	    <td >${setting.specialitySettings?size}</td>
	    <td class="title">报名对象：</td>
	    <td ><#list setting.scopes as scope>${scope.enrollTurn?if_exists}&nbsp;<@i18nName scope.stdType?if_exists/>&nbsp;<@i18nName scope.department?if_exists/>&nbsp;<@i18nName scope.speciality?if_exists/>&nbsp;<@i18nName scope.aspect?if_exists/>;</#list></td>
	   </tr>
     </table>
</#macro>