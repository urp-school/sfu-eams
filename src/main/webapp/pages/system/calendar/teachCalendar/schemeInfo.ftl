<#include "/templates/head.ftl"/>
<#assign labInfo>教学日历方案详细信息</#assign>  
<#include "/templates/back.ftl"/>
<body>
 <table  class="infoTable">
   <tr>
 	     <td class="title"  width="25%"><@bean.message key="attr.name"/>：</td> 	     
 	     <td class="content">${scheme.name}</td>
 	     <td class="title" >日历个数：</td> 	     
 	     <td class="content">${scheme.calendars?size}</td>
   </tr>
   <tr>
     <td class="title">学生类别：</td>
     <td class="content"><#list scheme.stdTypes as stdType><@i18nName stdType/></#list></td>
     <td class="title">时间设置：</td> 	     
     <td class="content">${(scheme.timeSetting.name)?default("<font color=\"red\">未设置</font>")}</td>
   </tr>
 </table>
</body>

<#include "/templates/foot.ftl"/>