<#include "/templates/head.ftl"/>
<body>
<#assign labInfo><@bean.message key="page.classroomInfo.label"/></#assign>  
<#include "/templates/back.ftl"/>
     <table class="infoTable">
	   <tr>
	     <td class="title"><@bean.message key="attr.code"/>:</td>
	     <td class="content">${classroom.code}</td>
	     <td class="title"><@bean.message key="attr.infoname"/>:</td>
	     <td class="content">${classroom.name?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title"><@bean.message key="common.schoolDistrict"/>:</td>
	     <td class="content"><@i18nName classroom.schoolDistrict?if_exists/></td>
 	     <td class="title"><@bean.message key="attr.engName"/>:</td>
         <td class="content">${classroom.engName?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title"><@bean.message key="common.classroomConfigType"/>:</td>
         <td class="content"><@i18nName classroom.configType/></td>
         <td class="title"><@bean.message key="common.building"/>:</td>
         <td class="content"><@i18nName classroom.building?if_exists/></td>
	   </tr>
	   <tr>
	    <td class="title"><@bean.message key="entity.department"/>:</td>
        <td class="content" colspan="3">
            <#list classroom.departments as depart>
              <option value="${depart.id}"><@i18nName depart/></option>
            </#list>
        </td>
       </tr>
	   <tr>
        <td class="title"><@bean.message key="attr.floor"/>:</td>
        <td class="content">${classroom.floor?if_exists}</td>
        <td class="title"><@msg.message key="attr.capacityOfExam"/>:</td>
        <td class="content">${classroom.capacityOfExam?if_exists}</td>
       </tr>
	   <tr>
	    <td class="title"><@msg.message key="attr.capacityOfCourse"/>:</td>
        <td class="content">${classroom.capacityOfCourse?if_exists}</td>
	   	<td class="title">容量（人数）:</td>
	   	<td class="content">${(classroom.capacity)?default(0)}</td>
       </tr>
	   <tr>
	    <td class="title">是否排课检查:</td>
        <td class="content">${(classroom.isCheckActivity)?default(true)?string("是", "否")}</td>
	    <td class="title"><@bean.message key="attr.state"/>:</td>
        <td class="content">
			<#if classroom.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
        </td>
       </tr>
	   <tr>
        <td class="title"><@bean.message key="attr.createAt"/>:</td>
        <td class="content">${(classroom.createAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title"><@bean.message key="attr.modifyAt"/>:</td>
        <td class="content">${(classroom.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
       </tr>
       <tr>
	    <td class="title"><@bean.message key="common.remark" />:</td>
        <td class="content" colspan="3">${classroom.remark?if_exists}</td>
       </tr>
      </table>
</body>
<#include "/templates/foot.ftl"/>