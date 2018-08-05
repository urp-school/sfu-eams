<#include "/templates/head.ftl"/>
<body>
<#assign labInfo><@bean.message key="page.studentTypeInfo.label" /></#assign>     
<#include "/templates/back.ftl"/> 
     <table  class="infoTable">
	   <tr>
	     <td class="title" ><@bean.message key="attr.code" />:</td>
	     <td class="content"> ${studentType.code}</td>
	     <td class="title" ><@bean.message key="attr.infoname" />:</td>
	     <td class="content"> ${studentType.name}</td>	     
	   </tr>
	   <tr>
	     <td class="title" ><@bean.message key="attr.engName" />:</td>
	     <td class="content">${studentType.engName?if_exists}  </td>
	     <td class="title" ><@bean.message key="common.abbreviation" />:</td>
         <td class="content">${studentType.abbreviation?if_exists}</td>	     
	   </tr>
	   <tr>
         <td class="title" ><@bean.message key="attr.dateEstablished" /> :</td>
         <td class="content">${(studentType.dateEstablished?string("yyyy-MM-dd"))?if_exists}</td>	   
	     <td class="title" ><@bean.message key="attr.state" />:</td>
         <td class="content" >
      	      <#if studentType.state?if_exists == true><@bean.message key="common.yes" /><#else><@bean.message key="common.no" /></#if>
         </td>
       </tr>
	   <tr>
        <td class="title" ><@bean.message key="attr.parentStudentType" />:</td>
        <td  class="content" colspan="3"><@i18nName studentType.superType?if_exists/></td>
       </tr>
       <tr>
        <td class="title" >对应文化程度:</td>
        <td  class="content"><@i18nName studentType.eduDegree?if_exists/></td>
        <td class="title" >毕业获得学位:</td>
        <td  class="content"><@i18nName studentType.degree?if_exists/></td>
       </tr>
	   <tr>
	    <td class="title" ><@bean.message key="entity.department" />:</td>
        <td class="content" colspan="3"><#list studentType.departs as depart><@i18nName depart/>&nbsp;</#list></td>
	   </tr>
	   <tr>
	    <td class="title" ><@bean.message key="common.remark" />:</td>
        <td class="content" colspan="3">${studentType.remark?if_exists}</td>
	   </tr>
	   <tr>
        <td class="title" ><@bean.message key="attr.createAt" />:</td>
        <td  class="content" >${(studentType.createAt?string("yyyy-MM-dd"))?if_exists}</td>
        <td class="title" ><@bean.message key="attr.modifyAt" />:</td>
        <td  class="content" >${(studentType.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>        
       </tr>
      </table>
  </body>
<#include "/templates/foot.ftl"/>