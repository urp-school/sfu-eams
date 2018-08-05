<#include "/templates/head.ftl"/>
<table id="bar"></table>
<#assign labelCount=labelCount?default(8)>
<#include "/pages/system/calendar/timeFunction.ftl"/>
 <#list tasks as task>
  <div style="font-size:14pt" align="center">(${task.calendar.year} 学年 <#if task.calendar.term='1'>第一学期<#elseif task.calendar.term='2'>第二学期<#else>${task.calendar.term}</#if>)&nbsp;&nbsp;&nbsp;<@msg.message key="attr.teachDepart"/>:<@i18nName task.arrangeInfo.teachDepart/></div>
  <table width="85%"  align="center"  class="listTable" >
      <tr>
        <td style="width:13%"><@msg.message key="attr.courseName"/></td><td style="width:20%"><@i18nName task.course/></td>
        <td style="width:13%"><@msg.message key="entity.courseType"/></td><td style="width:15%"><@i18nName task.courseType/></td>
        <td style="width:12%"><@msg.message key="attr.courseNo"/></td><td style="width:15%">${task.course.code}</td>
        <td style="width:12%"><@msg.message key="attr.taskNo"/></td><td>${task.seqNo}</td>
      </tr>
      <tr>
        <td>主考老师</td><td><@getBeanListNames task.arrangeInfo.teachers/></td>
        <td>授课老师</td><td><@getBeanListNames task.arrangeInfo.teachers/></td>
        <td>教学班</td><td><#if task.requirement.isGuaPai>挂牌<#else>${task.teachClass.name}</#if></td>
        <td>修读人数</td><td>${task.teachClass.stdCount}</td>
      </tr>
      <tr>
        <td>考试日期</td><td><#if activityMap['${task.id}']?exists>${activityMap['${task.id}'].time.lastDay?string("yy-MM-dd")} <@getTimeStr activityMap['${task.id}'].time.startTime/>-<@getTimeStr activityMap['${task.id}'].time.endTime/></#if></td>
        <td style="width:13%">考试人数</td><td style="width:6%">${task.teachClass.stdCount}</td>
        <td style="width:13%">试卷数</td><td style="width:6%">${task.teachClass.stdCount+extraCount}</td>
        <td style="width:13%">考试地点</td><td style="width:10%"><#if activityMap['${task.id}']?exists><@i18nName activityMap['${task.id}'].room?if_exists/></#if></td>
      </tr>
   </table>
   <br>
   <#if ((task_index+1)%labelCount==0)&&task_has_next>
   <div style='PAGE-BREAK-AFTER: always'></div>
   </#if>
 </#list>
 <form name="actionForm" method="post" action="" onsubmit="return false;">
  <#list RequestParameters?keys as key>
  <input type="hidden" name="${key}" value="${RequestParameters[key]}" />
  </#list>
 </form>
 <script>
 	var defaultLabels = ${labelCount};
    var bar =new ToolBar("bar","打印试卷带标签(每页" + defaultLabels +"个标签)",null,true,true);
    bar.addItem("设置每页打印个数","resetCount()");
    function resetCount(){
       	form =document.actionForm;
       	var labelCount=prompt("设置每页打印个数？","");
       	if(labelCount == null || !/^\d+$/.test(labelCount) || parseInt(labelCount) < 1 || parseInt(labelCount) > 1000){
       		alert("您输入的份数无效或超过1000份。");
    		labelCount = defaultLabels;
       	}
       	addInput(form, "labelCount", labelCount);
       	form.submit();
    }
 </script>
</body>
<#include "/templates/foot.ftl"/>