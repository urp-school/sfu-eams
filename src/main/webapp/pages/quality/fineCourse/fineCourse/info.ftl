<#include "/templates/head.ftl"/>
<body>
<#assign labInfo>精品课程信息</#assign>  
<#include "/templates/back.ftl"/>   
     <table width="90%" align="center"  class="infoTable">
	   <tr>
	     <td id="f_courseName" class="title"><@bean.message key="attr.courseName"/>:</td>
	     <td colspan="3">${(fineCourse.courseName)?if_exists?html}</td>
	   </tr>
	   <tr>
	     <td class="title">级别:</td>
	     <td><@i18nName fineCourse.level/></td>
	     <td class="title"><@msg.message key="entity.department"/>:</td>
	     <td><@i18nName fineCourse.department?if_exists/>&nbsp;</td>
	   </tr>
       <tr>
	     <td class="title">批准年度:</td>
   	     <td colspan="3">${fineCourse.passedYear?default("")}</td>
	   </tr> 
	   <tr>
	     <td class="title">负责人:</td>
   	     <td colspan="3">${(fineCourse.chargeNames)?default('')}</td>
	   </tr> 
	   <tr >
	     <td class="title"><@bean.message key="attr.remark"/>:</td>
	     <td colspan="3">${(fineCourse.remark)?if_exists}</td>
	   </tr>
     </table>
 </body> 
</html>