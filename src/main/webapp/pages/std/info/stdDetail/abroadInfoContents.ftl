	<table align="center" class="infoTable">
	   <tr>
	     	<td class="title" width="20%"><@msg.message key="std.certificateInfo.passportNo"/>：</td>
	     	<td width="30%">${(std.abroadStudentInfo.passportNo)?default('&nbsp')}</td>
	     	<td width="20%" class="title"  id="f_idCard"><@msg.message key="std.certificateInfo.passportType"/>：</td>
	     	<td width="30%">${(std.abroadStudentInfo.passportType.id)?default("&nbsp")}</td>
	   </tr>
	   <tr>
	     	<td class="title"><@msg.message key="std.certificateInfo.timeExpiredPassport"/>：</td>
	     	<td colspan="3">${(std.abroadStudentInfo.passportDeadline)?default('&nbsp')}</td>
	   </tr>	 	   
	   <tr>
	     	<td class="title"><@msg.message key="std.certificateInfo.visaNo"/>：</td>
	     	<td>${(std.abroadStudentInfo.visaNo)?default('&nbsp')}</td>
	     	<td class="title" id="f_idCard"><@msg.message key="std.certificateInfo.passportCategories"/>：</td>
	     	<td>${(std.abroadStudentInfo.visaType.id)?default("&nbsp")}</td>
	   </tr>
	   <tr>
	     	<td class="title"><@msg.message key="std.certificateInfo.visaExpiredPassport"/>：</td>
	     	<td colspan="3">${(std.abroadStudentInfo.visaDeadline)?default('&nbsp')}</td>
	   </tr>	
	   <tr>
	     	<td class="title"><@msg.message key="std.certificateInfo.residencePermitsNo"/>：</td>
	     	<td>${(std.abroadStudentInfo.resideCaedNo)?default('&nbsp')}</td>	     
	     	<td class="title"><@msg.message key="std.certificateInfo.permitsExpiredPassport"/>：</td>
	      	<td>${(std.abroadStudentInfo.resideCaedDeadline)?default('&nbsp')}</td>
	   </tr>
	   <tr>
	     	<td class="title" ><@msg.message key="entity.HSKDegree"/>：</td>
	     	<td colspan="3"> <@i18nName (std.abroadStudentInfo.HSKDegree)?if_exists /></td>
	   </tr>
	</table>
