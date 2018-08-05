<#include "/templates/head.ftl"/>
 <body>
 <#assign labInfo><@bean.message key="page.departmentInfo.label" /></#assign>  
<#include "/templates/back.ftl"/>    
     <table class="infoTable">
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.code" />:</td>
	     <td class="content"> ${department.code}</td>
	     <td class="title" >&nbsp;<@bean.message key="attr.infoname" />:</td>
	     <td class="content"> ${department.name?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title" >&nbsp;<@bean.message key="attr.engName" />:</td>
	     <td class="content">${department.engName?if_exists}  </td>
	     <td class="title" >&nbsp;<@bean.message key="common.abbreviation" />:</td>
         <td class="content">${department.abbreviation?if_exists}</td>	     
	   </tr>
	   <tr>
         <td class="title" >&nbsp;<@bean.message key="attr.dateEstablished" /> :</td>
         <td class="content">${(department.dateEstablished?string("yyyy-MM-dd"))?if_exists}</td>	   
	     <td class="title" >&nbsp;<@bean.message key="attr.state" />:</td>
         <td class="content" >
      	      <#if department.state?if_exists == true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if>
         </td>
       </tr>
	   <tr>
	    <td class="title" >&nbsp;<@bean.message key="department.isTeaching" />:</td>
        <td class="content" >
      	      <#if department.isTeaching?if_exists == true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if>
        </td> 
	    <td class="title" ><@bean.message key="department.isCollege" />:</td>
        <td class="content" >
      	      <#if department.isCollege?if_exists == true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if>
        </td>
       </tr>
	   <tr>
	    <td class="title" >&nbsp;<@bean.message key="common.remark" />:</td>
        <td class="content" colspan="3">${department.remark?if_exists}</td>
	   <tr>
        <td class="title" >&nbsp;<@bean.message key="attr.createAt" />:</td>
        <td  class="content" >${(department.createAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title" >&nbsp;<@bean.message key="attr.modifyAt" />:</td>
        <td  class="content" >${(department.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>        
       </tr>       
      </table>
  </body>
<#include "/templates/foot.ftl"/>