<#include "/templates/head.ftl"/>
<body >
  <table id="examActivityBar"></table>
  <script>
     var bar=new ToolBar('examActivityBar','考场汇总列表',null,true,false);
     bar.addItem("预览座位表","seatReport()","print.gif");
</script>
 <@table.table width="100%" id="listTable" sortable="true">
    <@table.thead>
       <@table.selectAllTd id="examActivityId"/>
       <td width="50%" id="activity.time.year,activity.time.validWeeksNum desc,activity.time.weekId,activity.time.startTime" desc="activity.time.year desc,activity.time.validWeeksNum,activity.time.weekId desc,activity.time.startTime desc" class="tableHeaderSort">考试安排</td>
       <td width="40%" id="activity.room.name" class="tableHeaderSort">地点</td>
    </@>
    <@table.tbody datas=examActivities;activity>
      <@table.selectTd id="examActivityId" type="checkBox" value="${activity[0].id+'@'+activity[1].year+'@'+activity[1].validWeeksNum+'@'+activity[1].weekId+'@'+activity[1].startUnit+'@'+activity[1].endUnit}"/>
	  <td>20${(activity[1].firstDay)?string("yy-MM-dd")} 日--${activity[1].timeZone}</td>
      <td><@i18nName activity[0]?if_exists/></td>
   </@>
</@>
	<form name="actionForm" method="post" action="" onsubmit="return false;">
		<input type=hidden id='examActivityIds' name='examActivityIds' value=""/>
	</form>
  <script>
    function seatReport(){
        var examActivityIds = getSelectIds("examActivityId");
        if(""==examActivityIds){alert("请选择一个或多个考场安排");return;}
        var form =document.actionForm;
        form.examActivityIds.value = examActivityIds;
        form.action = "examiner.do?method=seatReportByClassroom&examType.id=${RequestParameters['examType.id']}&calendar.id=${RequestParameters['calendar.id']}";
        form.target = "_blank";
        form.submit();
    }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 