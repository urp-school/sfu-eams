<#include "/templates/head.ftl"/>
<BODY>
	<table id="toolBar" width="100%"></table>
    <@table.table width="100%">
       <@table.thead>
        <@table.td text=""/>
        <@table.td name="attr.taskNo"/>
        <@table.td name="attr.courseNo"/>
        <@table.td name="attr.courseName"/>
        <@table.td text="实际安排"/>
        <@table.td text="教室地点"/>
        <@table.td text="容纳考试人数"/>
        <@table.td text="实际人数"/>
        <@table.td text="利用率"/>
       </@>
       <@table.tbody datas=utilizations;utilization>
         <#assign activity=utilization[0]>
         <@table.selectTd id="taskId" type="radio" value="${activity.task.id}"/>
         <td>${activity.task.seqNo}</td>
         <td>${activity.task.course.code}</td>
         <td><@i18nName activity.task.course/></td>
         <td>${activity.digest(Session["org.apache.struts.action.LOCALE"],Request["org.apache.struts.action.MESSAGE"],"第:weeks周 :day :time")}</td>
         <td><@i18nName activity.room/></td>
         <td>${utilization[1]}</td>
         <td>${utilization[2]}</td>
         <td><#if utilization[1]=0>>###<#else>${((utilization[2]/utilization[1])*100)?string("##.##")}%</#if></td>
       </@>
    </@table.table>
  <form name="utilizationForm" method="post" action="examArrange.do?method=roomUtilization" onsubmit="return false;">
    <input type="hidden" name="examType.id" value="${RequestParameters['examType.id']}">
    <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}">  
    <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}">
    <input type="hidden" name="pageNo" value="${pageNo}">
    <input type="hidden" name="pageSize" value="${pageSize}">
    <input type="hidden" name="ratio" value="${RequestParameters['ratio']}">
  </form>
 <script>
   var bar=new ToolBar("toolBar","教室利用率统计结果(利用率${RequestParameters['ratio']}以下)",null,true,true);
   bar.addItem("更换教室","changeRoom()");
   bar.addItem("刷新","refreshQuery()","refresh.gif");
   bar.addItem("<@msg.message key="action.print"/>","print()");
   bar.addBack("<@msg.message key="action.back"/>");
   //更换教室
    function changeRoom(){
      var taskId = getSelectId("taskId");
      if(""==taskId){alert("请选择一个或多个排考安排");return;}
      var form =document.utilizationForm;
	  var params=getInputParams(parent.document.arrangedTaskSearchForm,null,false);
      params +="&examType.id=${RequestParameters['examType.id']}";
      addParamsInput(form,params);
      form.action="examArrange.do?method=edit&task.id="+taskId;
      form.submit();
    }
 </script> 

</body>
<#include "/templates/foot.ftl"/> 
  