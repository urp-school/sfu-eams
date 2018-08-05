<#include "/templates/head.ftl"/>
<body>
    <#assign labInfo><@bean.message key="page.buildingInfo.label"/></#assign> 
	<#include "/templates/back.ftl"/>
     <table  class="infoTable">
	   <tr>
	     <td class="title">&nbsp;<@bean.message key="attr.code"/>:</td>
	     <td class="content"> ${building.code}</td>
	     <td class="title">&nbsp;<@bean.message key="attr.infoname"/>:</td>
	     <td class="content"> ${building.name?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title">&nbsp;<@bean.message key="attr.engName"/>:</td>
	     <td class="content">${building.engName?if_exists}</td>
   	     <td class="title">&nbsp;<@bean.message key="common.abbreviation"/>:</td>
         <td class="content">${building.abbreviation?if_exists}</td>
	   </tr>
	   <tr>
	     <td class="title">&nbsp;<@bean.message key="common.schoolDistrict"/>:</td>
	     <td class="content"><@i18nName building.schoolDistrict/></td>
	     <td class="title">&nbsp;<@bean.message key="attr.state"/>:</td>
         <td class="content">
      	      <#if building.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
         </td>
	   </tr>
	   <tr>
        <td class="title">&nbsp;<@bean.message key="attr.createAt"/>:</td>
        <td class="content">${(building.createAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title">&nbsp;<@bean.message key="attr.modifyAt"/>:</td>
        <td class="content">${(building.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
       </tr>
      </table>
</body>
<#include "/templates/foot.ftl"/>