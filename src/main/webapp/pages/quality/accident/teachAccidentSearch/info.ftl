<#include "/templates/head.ftl"/>
 <body>
	<#assign labInfo><@msg.message key=key="field.teachAccident.displayDetailInfo"/></#assign>
	<#include "/templates/back.ftl"/>
     <table width="100%" align="center" class="infoTable">
	   <tr>
	     <td class="title" width="20%"><@msg.message key=key="attr.teachDepart"/></td>
	     <td><@i18nName teachAccident.task.arrangeInfo.teachDepart/></td>
	     <td class="title" width="20%"><@msg.message key=key="entity.course"/></td>
	     <td><@i18nName teachAccident.task.course/></td>
	   </tr>
	   <tr>
	     <td class="title"><@msg.message key=key="entity.teacher"/></td>
	     <td>${teachAccident.teacher.name}</td>
	   	 <td class="title"><@msg.message key=key="field.teachAccident.occurTime"/></td>
	   	 <td>${teachAccident.occurAt?date}</td>
	   </tr>
	   <tr>
	   		<td class="title"><@msg.message key=key="field.teachAccident.accidentDes"/></td>
	   		<td colspan="3">${teachAccident.description}</td>
	   </tr>
	   <tr>
	     <td class="title"><@msg.message key=key="field.teachAccident.remark"/>:</td>
	     <td colspan="3">${teachAccident.remark?if_exists}</td>
	   </tr>
	   <tr>
	   		<td class="title" ><@msg.message key=key="field.teachAccident.accidentAcademic"/>:</td>
	   		<td colspan="3">${teachAccident.task.calendar.year}  ${teachAccident.task.calendar.term}</td>
	   </tr>
     </table>
 </body>
<#include "/templates/foot.ftl"/>