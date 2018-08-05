<#include "/templates/head.ftl"/>
 <body>
<#assign labInfo>二级学科详细信息</#assign>
<#include "/templates/back.ftl"/>
     <table  class="infoTable" >
	   <tr>
	     <td class="title">名称:</td>
	     <td class="content"><@i18nName (level2Subject.speciality)?if_exists/></td>
	     <td class="title">一级学科:</td>
	     <td class="content"><@i18nName (level2Subject.level1Subject)?if_exists/></td>
	   </tr>
	   <tr>
	     <td class="title">是否专业学位:</td>
         <td class="content">${level2Subject.isSpecial?if_exists?string("有","无")}</td>
         <td class="title">是否自主设置:</td>
         <td class="content">${level2Subject.isReserved?if_exists?string("有","无")}</td>
	   </tr>
	   <tr>
	     <td class="title">博士学位授予权:</td>
         <td class="content">${level2Subject.forDoctor?if_exists?string("有","无")}</td>
         <td class="title">硕士学位授予权:</td>
         <td class="content">${level2Subject.forMaster?if_exists?string("有","无")}</td>
	   </tr>
	   <tr>
         <td class="title">批准时间:</td>
         <td colspan="3" class="content">${level2Subject.ratifyTime?if_exists}</td>
       </tr>
     </table>
 </body>
<#include "/templates/foot.ftl"/>