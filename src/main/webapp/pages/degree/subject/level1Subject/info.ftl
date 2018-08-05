<#include "/templates/head.ftl"/>
 <body>
<#assign labInfo>一级学科详细信息</#assign>  
<#include "/templates/back.ftl"/>     
     <table class="infoTable" >
	   <tr>
	     <td class="title" >代码:</td>
	     <td class="content"> ${level1Subject.code}</td>
	     <td class="title" >名称:</td>
	     <td class="content"> ${level1Subject.name?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title" >学科门类:</td>
	     <td class="content">${level1Subject.category?if_exists.id?if_exists}</td>
 	     <td class="title" >英文名称:</td>
         <td class="content">${level1Subject.engName?if_exists}</td>	     
	   </tr>
	   <tr>
	     <td class="title">博士学位授予权:</td>
         <td class="content">${level1Subject.forDoctor?if_exists?string("有","无")}</td>
         <td class="title" >硕士学位授予权:</td>
         <td class="content">${level1Subject.forMaster?if_exists?string("有","无")}</td>	     
	   </tr>
	   <tr>
         <td class="title" >批准时间:</td>
         <td colspan="3" class="content" >${level1Subject.ratifyTime?if_exists}</td>
       </tr>
     </table>
 </body>
<#include "/templates/foot.ftl"/>