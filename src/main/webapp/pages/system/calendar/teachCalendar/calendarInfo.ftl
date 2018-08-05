<#include "/templates/head.ftl"/>
<#assign labInfo>教学日历详细信息</#assign>  
<#include "/templates/back.ftl"/>
<body>
 <table  class="infoTable">
   <tr>
 	     <td class="title"  width="25%"> <@bean.message key="attr.year2year"/>：</td> 	     
 	     <td class="content">${teachCalendar.year?if_exists}</td>
 	     <td class="title"  width="25%"><@bean.message key="attr.term"/>：</td> 	     
 	     <td class="content">${teachCalendar.term?if_exists}</td>
   </tr>
   <tr>
 	     <td class="title" >开始日期：</td> 	     
 	     <td class="content">${teachCalendar.start?if_exists}</td>
 	     <td class="title" >结束日期：</td> 	     
 	     <td class="content">${teachCalendar.finish?if_exists}</td>
   </tr>
   <tr>
     <td class="title">上课周数：</td>
     <td class="content">${teachCalendar.weeks?if_exists}</td>
     <td class="title">总周数：</td>
     <td class="content">${teachCalendar.weekSpan?if_exists}</td>
   </tr>
   <tr>
     <td class="title">是否小学期：</td>
     <td class="content">${teachCalendar.isSmallTerm?default(faslse)?string("是","否")}</td>
     <td class="title">时间设置：</td>
     <td class="content">${teachCalendar.timeSetting.name}</td>
   </tr>
   <tr>
     <td  class="title"><@bean.message key="attr.remark"/>：</td>
     <td colspan="3" class="content">${teachCalendar.remark?if_exists}</td>
   </tr>
 </table>
 <table class="infoTable">
   <tr style="text-align:center">
     <td>周次(年内顺序)</td>
     <td class="title"><@msg.message key="time.week.sun"/></td>
     <td><@msg.message key="time.week.mon"/></td>
     <td><@msg.message key="time.week.tus"/></td>
     <td><@msg.message key="time.week.wen"/></td>
     <td><@msg.message key="time.week.ths"/></td>
     <td><@msg.message key="time.week.fri"/></td>
     <td class="title"><@msg.message key="time.week.sat"/></td>
   </tr>
   <#list dates as weekDates>
   <tr style="text-align:center">
     <td >${weekDates_index+1}(${weekDates_index+teachCalendar.weekStart})</td>
     <td class="title">${(weekDates[0]?string("yy-MM-dd"))?default('')}</td>
     <td>${(weekDates[1]?string("yy-MM-dd"))?default('')}</td>
     <td>${(weekDates[2]?string("yy-MM-dd"))?default('')}</td>
     <td>${(weekDates[3]?string("yy-MM-dd"))?default('')}</td>
     <td>${(weekDates[4]?string("yy-MM-dd"))?default('')}</td>
     <td>${(weekDates[5]?string("yy-MM-dd"))?default('')}</td>
     <td class="title">${(weekDates[6]?string("yy-MM-dd"))?default('')}</td>
   </tr>
   </#list>
 </table>
</body>

<#include "/templates/foot.ftl"/>