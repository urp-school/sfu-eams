<#include "/templates/head.ftl"/>
<body >
  
  <table id="examActivityBar"></table>

 <@table.table width="100%" sortable="true" id="listTable">
    <@table.thead>
       <@table.selectAllTd id="examActivityId"/>
      <td width="8%" id="activity.task.seqNo" class="tableHeaderSort"><@msg.message key="attr.taskNo"/></td>
      <td width="15%" id="activity.task.course.name" class="tableHeaderSort"><@msg.message key="attr.courseName"/></td>
      <td width="20%" id="activity.task.teachClass.name" class="tableHeaderSort"><@msg.message key="entity.teachClass"/></td>
      <td width="12%" id="activity.time.year,activity.time.validWeeksNum desc,activity.time.weekId,activity.time.startTime,activity.id"  desc="activity.time.year desc,activity.time.validWeeksNum,activity.time.weekId desc,activity.time.startTime desc" class="tableHeaderSort">考试安排</td>
      <td width="8%" id="activity.room.name" class="tableHeaderSort">地点</td>
      <td width="10%" id="activity.department" class="tableHeaderSort">主考院系</td>
      <td width="8%">主考</td>
      <td width="10%" id="activity.examMonitor.depart.name" class="tableHeaderSort">监考院系</td>
      <td width="8%">监考</td>
    </@>
    <@table.tbody datas=examActivities;activity>
      <@table.selectTd id="examActivityId"  type="checkBox" value="${activity.id}"/>
      <td><A href="courseTable.do?method=taskTable&task.id=${activity.task.id}" title="查看课程安排">${activity.task.seqNo?if_exists}</A></td>
      <td><A href="teachTask.do?method=info&task.id=${activity.task.id}" title="<@msg.message key="info.task.info"/>"><@i18nName activity.task.course/></A></td>      
      <td>${activity.task.teachClass.name?html}</td>
      <td>${activity.task.arrangeInfo.digestExam(activity.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],RequestParameters['examType.id'],":date :time")}</td>
      <td><@i18nName activity.room?if_exists/></td>
      <td><@i18nName activity.department?if_exists/></td>
      <td><@i18nName activity.examMonitor.examiner?if_exists/> ${activity.examMonitor.examinerName?if_exists}</td>
      <td><@i18nName activity.examMonitor.depart?if_exists/></td>
      <td><@i18nName activity.examMonitor.invigilator?if_exists/> ${activity.examMonitor.invigilatorName?if_exists}</td>
   </@>
</@>
    <form name="actionForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="examActivityIds" value=""/>
        <input type="hidden" name="extraCount" value=""/>
        <input type="hidden" name="orderBy" value="${RequestParameters['orderBy']?default('')}"/>
        <input type="hidden" name="calendar.id" value="${RequestParameters["calendar.id"]}"/>
        <input type="hidden" name="examType.id" value="${RequestParameters["examType.id"]}"/>
        <input type="hidden" name="keys" value="task.seqNo,task.course.code,task.course.name,task.arrangeInfo.teacherCodes,task.arrangeInfo.teacherNames,task.teachClass.name,task.teachClass.stdCount,examTakeCount,time,date,room.name,task.arrangeInfo.teachDepart.name,examMonitor.examinerNames,examMonitor.depart.name,examMonitor.invigilatorNames,task.requirement.isGuaPai"/>
        <input type="hidden" name="titles" value="<@msg.message key="attr.taskNo"/>,<@msg.message key="attr.courseNo"/>,<@msg.message key="attr.courseName"/>,教师工号,教师姓名,教学班,上课人数,考试人数,考试安排,考试时间,考试地点,主考院系,主考老师,监考院系,监考老师,是否挂牌"/>
    </form>
  <script>
     var bar=new ToolBar('examActivityBar','排考结果列表',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("<@msg.message key="action.export"/>","exportData()","excel.png");
     var printMenu=bar.addMenu("<@msg.message key="action.print"/>通知","printData()","print.gif");
     printMenu.addItem("打印试卷带标签","printPaperLabel()","print.gif");
     printMenu.addItem("预览座位表","seatReport()","print.gif");
     printMenu.addItem("考场汇总", "arrangeRoomTimeList()");
     var menu =bar.addMenu("依据开课院系设置主考老师","batchSet('batchSetExaminers')");
     menu.addItem("依据授课教师设置主考","setExaminerByTeacher()");
     
     var menu1 =bar.addMenu("批量设置主考院系","batchSet('batchSetExaminers1&type=depart')");
     menu1.addItem("批量设置主考老师","batchSet('batchSetExaminers1')");
     menu1.addItem("批量设置监考院系","batchSet('batchSetInvigilators&type=depart')");
     menu1.addItem("批量设置监考老师","batchSet('batchSetInvigilators')");

    var form = document.actionForm;
    form["examActivityIds"].value = "";
    form["extraCount"].value = "";
    
    function batchSet(type){
       var examActivityIds = getSelectIds("examActivityId");
	   if(""==examActivityIds){alert("请选择考试安排进行设置");return;}
       form.target="_blank";
       form.action = "examiner.do?method=" + type;
       form["examActivityIds"].value = examActivityIds;
       form.submit();
    }
    function exportData(){
       var examActivityIds = getSelectIds("examActivityId");
	   if(""==examActivityIds){alert("请选择考试安排进行设置");return;}
	   form["examActivityIds"].value = examActivityIds;
       form.action="examiner.do?method=export";
       form.target="";
       form.submit();
    }
    function printData(){
       var examActivityIds = getSelectIds("examActivityId");
	   if(""==examActivityIds){alert("请选择考试安排进行设置");return;}
	   form["examActivityIds"].value = examActivityIds;
       form.action="examiner.do?method=examinerReportSetting";
       form.target="_blank";
       form.submit();
    }
    function setExaminerByTeacher(){
    	var form=parent.document.taskSearchForm;
        var examActivityIds = getSelectIds("examActivityId");
	    if(""==examActivityIds){alert("请选择考试安排进行设置");return;}	
	    if(!confirm("依据授课教师,设置主考.多个教师的任务将选择其中一个.\n已经有主考的安排也会替换掉,是否继续?"))return;
        var examType=form["examType.id"].value;
        form.action="examiner.do?method=setExaminerByTeacher&examActivityIds="+examActivityIds;
        form.submit();
    }
    function seatReport(){
        var examActivityIds = getSelectIds("examActivityId");
        if(""==examActivityIds){alert("请选择一个或多个排考安排");return;}
        if(""!=orderByStr){orderByStr+=",activity.task.course.code asc"}
        window.open("examiner.do?method=seatReport&examType.id=${RequestParameters['examType.id']}&examActivityIds="+examActivityIds+"&orderBy="+orderByStr);
    }
    function arrangeRoomTimeList(){
        window.open("examiner.do?method=arrangeRoomTimeList&examType.id=${RequestParameters['examType.id']}&calendar.id=${RequestParameters['calendar.id']}");
    }
    function printPaperLabel(){
        var examActivityIds = getSelectIds("examActivityId");
        if ("" == examActivityIds) {
            alert("请选择一个或多个排考安排");
            return;
        }
        var extraCount = prompt("需要给每个考场多加几份试卷？", 0);
        if (!/^\d+$/.test(extraCount) || parseInt(extraCount) < 0 || parseInt(extraCount) > 1000) {
        	alert("请输入0-1000的整数！");
        	return;
        }
        if(extraCount == null){
        	extraCount = 0;
        }
        form.action = "examiner.do?method=printPaperLabel";
        form["examActivityIds"].value = examActivityIds;
        form["extraCount"].value = extraCount;
        form.target = "_blank";
        form.submit();
    }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 
