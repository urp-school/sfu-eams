<#include "/templates/head.ftl"/>
<body>
<#assign labInfo><@bean.message key="page.adminClassInfo.label"/></#assign>
<#include "/templates/back.ftl"/>  
     <table class="infoTable">
	   <tr>
	     <td class="title">&nbsp;<@bean.message key="attr.code"/>:</td>
	     <td class="content"> ${adminClass.code}</td>
	     <td class="title">&nbsp;<@bean.message key="attr.infoname"/>:</td>
	     <td class="content"> ${adminClass.name}</td>	     
	   </tr>
	   <tr>
	     <td class="title">&nbsp;<@bean.message key="entity.department"/>:</td>
	     <td class="content"><@i18nName adminClass.department/></td>
	     <td class="title">&nbsp;<@bean.message key="adminClass.enrollYear"/>:</td>
         <td class="content">${adminClass.enrollYear?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title">&nbsp;<@bean.message key="entity.speciality"/>:</td>
         <td class="content"><@i18nName adminClass.speciality?if_exists/></td>	     
	     <td class="title">&nbsp;<@bean.message key="adminClass.planStdCount"/>:</td>
         <td class="content">${adminClass.planStdCount?if_exists}</td>	     
	   </tr>
	   <tr>
        <td class="title">&nbsp;<@bean.message key="entity.specialityAspect"/>:</td>
        <td class="content"><@i18nName adminClass.aspect?if_exists/></td>
	    <td class="title">&nbsp;<@bean.message key="adminClass.actualStdCount"/>:</td>
        <td class="content">${adminClass.actualStdCount?if_exists}</td>
       </tr>
	   <tr>
        <td class="title">&nbsp;学制:</td>
        <td class="content">${adminClass.eduLength?if_exists}</td>
	    <td class="title">&nbsp;学籍有效人数:</td>
        <td class="content">${adminClass.stdCount?if_exists}</td>
       </tr>
	   <tr>
        <td class="title">&nbsp;<@bean.message key="attr.dateEstablished"/>:</td>
        <td class="content">${(adminClass.dateEstablished?string("yyyy-MM-dd"))?if_exists}</td>
	    <td class="title">&nbsp;<@bean.message key="adminClass.instructor"/>:</td>
        <td class="content">${(adminClass.instructor.name)?if_exists}</td>
       </tr>
	   <tr>
	   	<td class="title">&nbsp;<@bean.message key="entity.studentType"/>:</td>
        <td class="content"><@i18nName adminClass.stdType?if_exists/></td>
	    <td class="title">&nbsp;<@bean.message key="common.remark"/>:</td>
        <td class="content">${adminClass.remark?if_exists}</td>
       </tr>        
	   <tr>
        <td class="title">&nbsp;<@bean.message key="attr.createAt"/>:</td>
        <td class="content">${(adminClass.createAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title">&nbsp;<@bean.message key="attr.modifyAt"/>:</td>
        <td class="content">${(adminClass.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>   
       </tr>     
      </table>
  </body>
<#include "/templates/foot.ftl"/>