<#include "/templates/head.ftl"/>
<body >
  
  <table id="examActivityBar" width="100%"></table>
  <script>
     var bar=new ToolBar('examActivityBar','排考结果列表',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("打印考试安排","print()");
</script>

   <@table.table width="100%">
    <@table.thead>
      <@table.td name="attr.taskNo" width="8%"/>
      <@table.td name="attr.courseName" width="20%"/>
      <@table.td name="entity.teachClass" width="20%" />
      <td width="30%">考试日期</td>
      <td width="8%">考试地点</td>
      <td width="8%">是否主考</td>
    </@table.thead>
    <@table.tbody datas=examActivities;activity>
      <td><A href="courseTableForTeacher.do?method=taskTable&task.id=${activity.task.id}" title="查看课程安排">${activity.task.seqNo?if_exists}</A></td>
      <td><A href="teachTaskSearch.do?method=info&task.id=${activity.task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName activity.task.course/></A></td>      
      <td><#if activity.task.requirement.isGuaPai>挂牌<#else>${activity.task.teachClass.name?html}</#if></td>
      <#if activity.task.arrangeInfo.getExamGroup(activity.examType)?exists>
         <#assign group =activity.task.arrangeInfo.getExamGroup(activity.examType)>
      </#if>
      <#if group!="null"&&group.isPublish?default(false)=false>
      <td colspan="3"><@msg.message key="exam.noDeploySituation"/></td>
      <#else>
      <td>${activity.time.firstDay?string("yyyy-MM-dd")} ${activity.task.arrangeInfo.digestExam(activity.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],activity.examType.id,":day :time")}</td>
      <td><@i18nName activity.room?if_exists/></td>
      <td><#if  teacher.name=activity.examMonitor.examiner?if_exists.name?if_exists?string|| teacher.name=activity.examMonitor.examinerName?default("")>主考</#if>
          <#if  teacher.name=activity.examMonitor.invigilator?if_exists.name?if_exists?string|| teacher.name=activity.examMonitor.invigilatorName?default("")>监考</#if></td>
      </#if>          
	</@>
  </@>
  <br>
</body> 
<#include "/templates/foot.ftl"/> 