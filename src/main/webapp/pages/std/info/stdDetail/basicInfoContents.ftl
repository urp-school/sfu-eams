	<table class="infoTable">
	   	<tr>
	     	<td width="18%" class="title"><@msg.message key="attr.gender"/>：</td>
	     	<td width="32%"><#if (std.basicInfo.gender)?exists><@i18nName std.basicInfo.gender/><#else>&nbsp;</#if></td>
	     	<td width="18%" class="title"><@msg.message key="attr.birthday"/>：</td>
	     	<td width="32%">${((std.basicInfo.birthday?string('yyyy-MM-dd'))?default('　'))?html}</td>
	   </tr>
	   <tr>
		    <td class="title"><@msg.message key="entity.country"/>：</td>
		    <td><#if (std.basicInfo.country)?exists><@i18nName std.basicInfo.country/><#else>&nbsp;</#if></td>
		    <td class="title" id="f_nation"><@msg.message key="entity.nation"/>：</td>
		    <td><#if (std.basicInfo.nation)?exists><@i18nName std.basicInfo.nation/><#else>&nbsp;</#if></td>
	   </tr>
	   <tr>
		    <td class="title"><@msg.message key="attr.ancestralAddress"/>：</td>
		    <td>${(std.basicInfo.ancestralAddress?default('　'))?html}</td>
		    <td class="title"><@msg.message key="std.idCard"/>：</td>
		    <td>${(std.basicInfo.idCard?default('　'))?html}</td>
	   </tr>
	   <tr>
		    <td class="title"><@msg.message key="entity.politicVisage"/>：</td>
		    <td><#if (std.basicInfo.politicVisage)?exists><@i18nName std.basicInfo.politicVisage/><#else>&nbsp;</#if></td>
		    <td class="title"><@msg.message key="baseCode.maritalStatus"/>：</td>
		    <td><#if (std.basicInfo.maritalStatus)?exists><@i18nName std.basicInfo.maritalStatus/><#else>&nbsp;</#if></td>
	   </tr>
	   <tr>
		    <td class="title"><@msg.message key="attr.postCode"/>：</td>
		    <td>${(std.basicInfo.postCode?default('　'))?html}</td>
		    <td class="title"><@msg.message key="attr.familyAddress"/>：</td>
		    <td>${(std.basicInfo.homeAddress?default('　'))?html}</td>
	   </tr>
	   <tr>
		    <td class="title"><@msg.message key="attr.phone"/>：</td>
		    <td>${(std.basicInfo.phone?default('　'))?html}</td>
		    <td class="title"><@msg.message key="std.parentsName"/>：</td>
		    <td>${((std.basicInfo.parentName)?default('　'))?html}</td>
	   </tr>
	   <tr>
	     	<td class="title"><@msg.message key="std.unitName"/>：</td>
	     	<td>${((std.basicInfo.workPlace)?default('　'))?html}</td>
	     	<td class="title"><@msg.message key="std.unitAddress"/>：</td>
	     	<td>${((std.basicInfo.workAddress)?default('　'))?html}</td>
	   </tr>
	   <tr>
	     	<td class="title"><@msg.message key="std.unitPostCode"/>：</td>
	     	<td>${(std.basicInfo.workPlacePostCode)?default('　')?html}</td>
	     	<td class="title" id="f_workPhone"><@msg.message key="std.unitPhoneNumber"/>：</td>
	     	<td>${(std.basicInfo.workPhone)?default('　')?html}</td>
	   </tr>
	   <tr>
	     	<td class="title"><@msg.message key="attr.mobile"/>：</td>
	     	<td>${std.basicInfo.mobile?default('　')?html}</td>
	     	<td class="title"><@msg.message key="attr.email"/>：</td>
	     	<td>${std.basicInfo.mail?default('　')?html}</td>
	   </tr>
	</table>
