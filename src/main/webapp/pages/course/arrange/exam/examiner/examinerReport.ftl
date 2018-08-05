<#include "/templates/head.ftl"/>
 <table id="examinerBar" width="100%" border="0"></table>
 <#list notices as notice>
  <table width="100%" border="0" style="font-size:16px">
     <tr  align="center" ><td><B><@i18nName systemConfig.school/>监考通知</B></td></tr>
     <tr  align="center"><td><B>${calendar.year}学年 <#if calendar.term='1'>第一学期<#elseif calendar.term='2'>第二学期<#else>${calendar.term}</#if></B><td></tr>
     <tr><td>监考教师: ${notice.teacherName}</td></tr>
  </table>
  <@table.table width="100%" id="listTable" class="listTable">
    <@table.thead>
      <@table.td name="attr.taskNo" width="8%"/>
      <@table.td name="attr.courseName" width="20%"/>
      <@table.td name="entity.teachClass" width="20%" />
      <td width="30%">考试日期</td>
      <td width="8%">考试地点</td>
      <td width="8%">是否主考</td>
    </@table.thead>
    <@table.tbody datas=notice.examActivities;activity>
      <td><A href="courseTable.do?method=taskTable&task.id=${activity.task.id}" title="查看课程安排">${activity.task.seqNo?if_exists}</A></td>
      <td><A href="teachTask.do?method=info&task.id=${activity.task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName activity.task.course/></A></td>      
      <td><#if activity.task.requirement.isGuaPai>挂牌<#else>${activity.task.teachClass.name?html}</#if></td>
      <td>${activity.time.firstDay?string("yyyy-MM-dd")} ${activity.task.arrangeInfo.digestExam(activity.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],activity.examType.id,":day :time")}</td>
      <td><@i18nName activity.room?if_exists/></td>
      <td><#if notice.teacherName=activity.examMonitor.examiner?if_exists.name?if_exists?string|| notice.teacherName=activity.examMonitor.examinerName?default("")>主考</#if>
          <#if notice.teacherName=activity.examMonitor.invigilator?if_exists.name?if_exists?string|| notice.teacherName=activity.examMonitor.invigilatorName?default("")>监考</#if></td>
	</@>
  </@>
  <br>
  <table width="100%">
    <tr align="center">
      <td><table><tr><td id="noticeTdId">${RequestParameters['notice.notice']?if_exists}</td></tr></table></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
       <td align="right">${RequestParameters['notice.department']?if_exists}&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
    <tr>
       <td align="right">${RequestParameters['notice.date']?if_exists}&nbsp;&nbsp;&nbsp;&nbsp;</td>
    </tr>
   </table>
	  <#if notice_has_next>
		  <#if (notice_index + 1) % (RequestParameters['preCount']?number)?default(1) == 0>
		  	<div style='PAGE-BREAK-AFTER: always'></div>
		  </#if>
	  </#if>
 </#list>
<form name="actionForm" method="post" action="" onsubmit="return false;"></form>
	<script>
		var bar = new ToolBar("examinerBar", "打印通知(每页${RequestParameters['preCount']?default(1)}个)", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("每页数量", "settingCount()");
		bar.addClose("<@msg.message key="action.close"/>");
		bar.addBack("重新设置内容");
		
		var maxCount = 10;
		var form = document.actionForm;
		function settingCount() {
			var count = prompt("设置每页打印个数？" ,"${RequestParameters['preCount']?default(1)}");
			if (count == null || count == "" || isNaN(count)) {
				alert("您设置的每页打印个数不正确。");
				return;
			}
			if (count > maxCount) {
				alert("每页打印个数不能超过" + maxCount + "个。");
				return;
			}
			
			form.action = "examiner.do?method=examinerReport";
			addInput(form, "examActivityIds", "${RequestParameters['examActivityIds']}", "hidden");
			addInput(form, "calendar.id", "${RequestParameters['calendar.id']}", "hidden");
			addInput(form, "notice.notice", $("noticeTdId").innerHTML, "hidden");
			addInput(form, "preCount", count, "hidden");
			form.submit();
		}
	</script>
</body> 
<#include "/templates/foot.ftl"/> 