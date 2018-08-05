<#include "/templates/head.ftl"/>
 <body>
 <#assign labInfo><@bean.message key="page.schoolDistrictInfo.label" /></#assign>   
	<#include "/templates/back.ftl"/>
   
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.code" />:</td>
	     <td class="content"> ${schoolDistrict.code}</td>
	     <td class="title" >&nbsp;<@bean.message key="attr.infoname" />:</td>
	     <td class="content"> ${schoolDistrict.name?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.engName" />:</td>
	     <td class="content">${schoolDistrict.engName?if_exists}  </td>
	     <td class="title" >&nbsp;<@bean.message key="attr.state" />:</td>	     
         <td class="content" >
      	      <#if schoolDistrict.state?if_exists == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
         </td>    
	   </tr>
	   <tr>
        <td class="title" >&nbsp;<@bean.message key="attr.createAt" />:</td>
        <td  class="content" >${(schoolDistrict.createAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title" >&nbsp;<@bean.message key="attr.modifyAt" />:</td>
        <td  class="content" >${(schoolDistrict.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>        
       </tr> 
      </table>
  </body>
<#include "/templates/foot.ftl"/>