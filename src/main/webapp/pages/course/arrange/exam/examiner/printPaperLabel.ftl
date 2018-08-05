<#include "/templates/head.ftl"/>
<table id="bar"></table>
<#assign labelCount=labelCount?default(8)>
<#include "/pages/system/calendar/timeFunction.ftl"/>
 <#list examActivities as activity>
  <div style="font-size:14pt" align="center">(${activity.calendar.year} 学年 <#if activity.calendar.term='1'>第一学期<#elseif activity.calendar.term='2'>第二学期<#else>${activity.calendar.term}</#if>)&nbsp;&nbsp;&nbsp;<@msg.message key="attr.teachDepart"/>:<@i18nName activity.task.arrangeInfo.teachDepart/></div>
  <table width="85%"  align="center"  class="listTable" >
      <tr>
        <td style="width:13%"><@msg.message key="attr.courseName"/></td><td style="width:20%"><@i18nName activity.task.course/></td>
        <td style="width:13%"><@msg.message key="entity.courseType"/></td><td style="width:15%"><@i18nName activity.task.courseType/></td>
        <td style="width:12%"><@msg.message key="attr.courseNo"/></td><td style="width:15%">${activity.task.course.code}</td>
        <td style="width:12%"><@msg.message key="attr.taskNo"/></td><td>${activity.task.seqNo}</td>
      </tr>
      <tr>
        <td>主考老师:<@i18nName activity.teacher?if_exists/> ${(activity.examMonitor.examinerName)?default("")}</td><td>监考老师：<#if (activity.examMonitor.invigilator)?exists><@i18nName activity.examMonitor.invigilator/></#if>&nbsp;${(activity.examMonitor.invigilatorName)?default('')}</td>
        <td>授课老师</td><td><@getBeanListNames activity.task.arrangeInfo.teachers/></td>
        <td>教学班</td><td><#if activity.task.requirement.isGuaPai>挂牌<#else>${activity.task.teachClass.name}</#if></td>
        <td>修读人数</td><td>${activity.task.teachClass.stdCount}</td>
      </tr>
      <tr>
        <td>考试日期</td><td>${activity.time.firstDay?string("yy-MM-dd")} <@getTimeStr activity.time.startTime/>-<@getTimeStr activity.time.endTime/> </td>
        <td style="width:13%">考试人数</td><td style="width:6%">${activity.examTakes?size}</td>
        <td style="width:13%">试卷数</td><td style="width:6%">${activity.examTakes?size+extraCount}</td>
        <td style="width:13%">考试地点</td><td style="width:10%"><@i18nName activity.room?if_exists/></td>
      </tr>
   </table>
   <br>
   <#if ((activity_index+1)%labelCount==0)&&activity_has_next>
   <div style='PAGE-BREAK-AFTER: always'></div>
   </#if>
 </#list>
 <form name="actionForm" action="" method="post" onsubmit="return false;">
  <#list RequestParameters?keys as key>
  <input type="hidden" name="${key}" value="${RequestParameters[key]}"/>
  </#list>
 </form>
 <script>
    var bar =new ToolBar("bar","打印试卷带标签(每页${labelCount}个标签)",null,true,true);
    bar.addItem("设置每页打印个数","resetCount()");
    var defaultLabels=8;
    function resetCount(){
       	form =document.actionForm;
       	var labelCount=prompt("设置每页打印个数？","");
       	if(labelCount == null || !/^\d+$/.test(labelCount) || parseInt(labelCount) < 1 || parseInt(labelCount) > 1000){
       		alert("您输入的份数无效或超过1000份。");
    		labelCount = defaultLabels;
       	}
       	addInput(form,"labelCount",labelCount);
       	form.submit();
    }
 </script>
</body>
<#include "/templates/foot.ftl"/> 