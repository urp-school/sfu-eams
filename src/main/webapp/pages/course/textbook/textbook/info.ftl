<#include "/templates/head.ftl"/>
 <body>
<#assign labInfo>教材详细信息</#assign>  
<#include "/templates/back.ftl"/>     
     <table class="infoTable" >
	   <tr>
	     <td class="title" ><@msg.message key="attr.code"/>:</td>
	     <td class="content"> ${textbook.code}</td>
	     <td class="title" ><@msg.message key="attr.name"/>:</td>
	     <td class="content"> ${textbook.name?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title" ><@msg.message key="textbook.author"/>:</td>
	     <td class="content">${textbook.auth?if_exists}</td>
 	     <td class="title" ><@msg.message key="textbook.version"/>:</td>
         <td class="content">${textbook.version?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title">价格:</td>
         <td class="content"><#if textbook.price?exists>${textbook.price?string("##.##")}</#if></td>
         <td class="title" >ISBN:</td>
         <td class="content">${textbook.ISBN?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title" ><@msg.message key="entity.press"/>:</td>
         <td class="content">${textbook.press?if_exists.name?if_exists}</td>
	     <td class="title" >出版年月:</td>
         <td class="content"><#if textbook.publishedOn?exists>${textbook.publishedOn?string("yyyy-MM-dd")}</#if></td>	     
       </tr>
	   <tr>
	     <td class="title" >教材种类:</td>
         <td class="content"><@i18nName textbook.bookType?if_exists/></td>
         <td class="title" ><@msg.message key="entity.textbookAwardLevel"/>:</td>
         <td class="content"><@i18nName textbook.awardLevel?if_exists/></td>
       </tr>
	   <tr>
         <td class="title" >教材描述:</td>
         <td colspan="3" class="content" >${textbook.description?if_exists}</td>
       </tr>
       <tr>
	     <td class="title" ><@msg.message key="attr.remark"/>:</td>
         <td colspan="3"  class="content">${textbook.remark?if_exists}</td>	     
       </tr>
     </table>
 </body>
<#include "/templates/foot.ftl"/>