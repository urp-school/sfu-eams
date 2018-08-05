<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script> 
<script>
 	var unitArray = new Array();
	var weekArray = new Array();
</script>
<style  type="text/css">
<!--
.trans_msg
    {
    filter:alpha(opacity=1000,enabled=1) revealTrans(duration=.0,transition=1) blendtrans(duration=.2);
    }
-->
</style>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self,200)">
<table id="arrangeResultBar"></table>
<div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
<script>initToolTips()</script>
<div id="courseInfo" style="display: block">
	<table class="infoTable" width="100%">
		<tr>
			<td class="title">序号/代码/课程</td>
			<td class="content" style="width:30%">${task.seqNo}(${task.course.code}) <@i18nName task.course/></td>
			<td class="title"><@msg.message key="entity.courseType"/></td>
			<td class="content" style="width:30%"><@i18nName task.courseType/></td>
		</tr>
		<tr>
			<td class="title">起始周/小节数/总周数</td>
			<td class="content">${task.arrangeInfo.weekStart}/${task.arrangeInfo.courseUnits}/${task.arrangeInfo.weeks}</td>
			<td class="title">学分/周课时/总学时</td>
			<td class="content">${task.course.credits}/${task.arrangeInfo.weekUnits}/${task.arrangeInfo.overallUnits}</td>
		</tr>
		<tr>
			<td class="title">计划人数/听课对象</td>
			<td class="content">${task.teachClass.planStdCount} / ${task.teachClass.name?replace("," , ", ")}</td>
			<td class="title">课程组名/组内人数</td>
			<td class="content"><#if (task.taskGroup)?exists>${(task.taskGroup.name)?if_exists?replace("," , ", ")} / ${(task.taskGroup.directTaskStdCount)?if_exists}</#if></td>
		</tr>
	</table>
</div>
<div id="timeTableDiv" style="display:none">
  <#assign unitList=task.calendar.timeSetting.courseUnits>
  <table width="100%" align="center" class="listTable">
    <tr class="darkColumn">
        <td width="60px"></td>
     	<#list unitList?sort_by("index") as unit>
     	<script>unitArray[${unit_index}]='<@i18nName unit/>';</script>
		<td style="width:80px" class="timeSeq${unit.segNo}"><@i18nName unit/></td>
		</#list>
    </tr>
	<#assign units = unitList?size>
	<#list weekList as week>
	<script>weekArray[${week_index}]='<@i18nName week/>';</script>
	<tr>
	    <td width="7%" class="darkColumn"><@i18nName week/></td>
  	    <#list 1..unitList?size as unit>
   		<td id="TD${week_index*unitList?size+unit_index}" onclick="selectUnit(event);" class="infoTitle"></td>
		</#list>
	</tr>
    </#list>
</table>
<table align="center" width="100%" border="0">
	<tr class="infoTitle">
	 <td >图例：</td>
	 <td style="backGround-Color:#94aef3" width="50px"></td><td>班级上课时间</td>
	 <td style="backGround-Color:#94aef3;text-align:center" width="50px">*</td><td>教师上课时间</td>
	 <td style="backGround-Color:yellow" width="50px"></td><td>选中时间</td>
	 <td align="right">
	   <#assign selectUnits = task.arrangeInfo.courseUnits>
	   <#if (selectUnits>4)><#assign selectUnits=4><#elseif  (selectUnits<1)><#assign selectUnits=1></#if>
	     一次选择的节次：
	     <input type="radio" name="selectUnitNum" value='1' <#if selectUnits=1>checked</#if>>单节
	     <input type="radio" name="selectUnitNum" value='2' <#if selectUnits=2>checked</#if>>双节
	     <input type="radio" name="selectUnitNum" value='3' <#if selectUnits=3>checked</#if>>三节
	     <input type="radio" name="selectUnitNum" value='4' <#if selectUnits=4>checked</#if>>四节</td>
	 </td>
	</tr>
</table>
  <table class="formTable" width="100%">
      <tr class="infoTitle">
         <td colspan="3">排课建议</td>
      </tr>
      <tr align="center" class="infoTitle">
	      <td width="30%">建议时间（<input type="checkbox" id="displaySuggestTable" onclick="displaySuggestTable(event);" />显示建议时间表）</td>
	      <td width="20%">建议教室</td>
	      <td width="50%">其他建议</td>
      </tr>
      <tr  align="center" class="infoTitle">
          <td id="suggestTimeAbbreviation"></td>
          <td><@getBeanListNames task.arrangeInfo.suggest.rooms/></td>
          <td>&nbsp;${(task.arrangeInfo.suggest.time.remark)?if_exists}</td>
      </tr>
  </table>
<div id="suggestTimeTable" style="display:none">
  <table align="center" class="listTable" width="100%">
    <tr class="darkColumn">
        <td width="50px"></td>
     	<#list unitList?sort_by("id") as unit>
		<td><@i18nName unit/></td>
		</#list>
    </tr>	
	<#list weekList as week>
	<tr>
	    <td class="darkColumn"><@i18nName week/></td>
  	    <#list 0..unitList?size-1 as unit>
  	    <#assign unitIndex=week_index*unitList?size + unit_index>
   		<td <#if task.arrangeInfo.suggest.time.available[unitIndex..unitIndex]=="1">style="backGround-Color:#94aef3" <#else> style="backGround-Color:yellow"</#if>></td>
		</#list>
	</tr>
    </#list>
 </table>
 <table align="center" width="80%" border="0">
	<tr class="infoTitle">
	 <td>图例:</td>
	 <td style="backGround-Color:#94aef3" width="60px"></td><td>建议上课时间</td>
	 <td style="backGround-Color:yellow" width="60px"></td><td>非建议上课时间</td>
	</tr>
 </table>
</div>
</div>
<#include "roomAndTeacher.ftl"/>
<#include "teacherList.ftl"/>
   <form name="taskActivityForm" method="post" action="" onsubmit="return false;">
      <input type="hidden" name="availTime" value=""/>
	  <input type="hidden" name="task.calendar.id" value="${task.calendar.id}" />
	  <input type="hidden" name="task.arrangeInfo.isArrangeComplete" value="${task.arrangeInfo.isArrangeComplete?string('1','0')}" />  
	  <input type="hidden" name="params" value="${RequestParameters['params']?default('')}">
	  <input type="hidden" name="taskId" value="${task.id}"/>
	  <input type="hidden" name="forward" value="${RequestParameters['forward']?default('')}"/>
   </form>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskActivity.js"></script> 
<script>
    var isOutCount = false;
    var outCountMessage = "";

    var year=${task.calendar.startYear};
    var weeks = ${task.arrangeInfo.weeks};
    var unitsPerDay=${unitList?size};
    var unitsPerWeek=${unitList?size*weekList?size};
    var calendarWeekStart =${task.calendar.weekStart};
    var taskWeekStart = ${task.arrangeInfo.weekStart};
	var availableTime = new String("${availableTime}");
	var index=0;
	var courseId="${task.course.id}";
	var courseName="${task.course.name}";
    var activity=null;
    var table = new CourseTable(year,unitsPerWeek);
    var tipContents = new Array(unitsPerWeek);
    var myContents=new Array(unitsPerWeek);
    
    var modifiedWeekAndRoom =false;
    
    var teacherMap = new Object();
    <#list task.arrangeInfo.teachers as teacher>
    teacherMap["${teacher.id}"] = true;
    </#list>
    
	<#list taskActivities as activity>
       activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","${activity.teacher?if_exists.name?if_exists}","","","${activity.room?if_exists.id?if_exists}","${activity.room?if_exists.name?if_exists}","${activity.time.validWeeks}");
       <#list 1..activity.time.unitCount as unit>
        index=${(activity.time.weekId-1)}*unitsPerDay+${activity.time.startUnit-1+unit_index};
       table.activities[index][table.activities[index].length]=activity;
       </#list>
	</#list>
 	var teachTable = new CourseTable(year,${unitList?size*weekList?size});
    <#list teacherActivities as activity>
       activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","${activity.teacher?if_exists.name?if_exists}","${activity.task.course.id}","${activity.task.course.name}(课程序号:${activity.task.seqNo})","${activity.room?if_exists.id?if_exists}","${activity.room?if_exists.name?if_exists}","${activity.time.validWeeks}");
       <#list 1..activity.time.unitCount as unit>
       index =${(activity.time.weekId-1)}*unitsPerDay+${activity.time.startUnit-1+unit_index};
       teachTable.activities[index][teachTable.activities[index].length]=activity;
       </#list>
	</#list>
 	var adminClassTable = new CourseTable(year,${unitList?size*weekList?size});
    <#list adminClassActivities as activity>
       activity = new TaskActivity("${activity.teacher?if_exists.id?if_exists}","${activity.teacher?if_exists.name?if_exists}","${activity.task.course.id}","${activity.task.course.name}(课程序号:${activity.task.seqNo})","${activity.room?if_exists.id?if_exists}","${activity.room?if_exists.name?if_exists}","${activity.time.validWeeks}");
       <#list 1..activity.time.unitCount as unit>
       index =${(activity.time.weekId-1)}*unitsPerDay+${activity.time.startUnit-1+unit_index};
       adminClassTable.activities[index][adminClassTable.activities[index].length]=activity;
       </#list>
	</#list>
</script>

<script language="JavaScript" type="text/JavaScript" src="scripts/course/<@bean.message key="info.manualArrange.js"/>"></script>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="js.availableTime"/>"></script>
<script>
   	document.getElementById("suggestTimeAbbreviation").innerHTML=abbreviate('${task.arrangeInfo.suggest.time?if_exists.available?if_exists}');
   	function displaySuggestTable(event){
   	    var div = $("suggestTimeTable");
      	var checkbox = getEventTarget(event);
      	if(checkbox.checked){
          	div.style.display = "block";
          	adaptFrameSize();
      	} else {
          	div.style.display = "none";
            adaptFrameSize();
      	}
   	}
   	document.getElementById("timeTableDiv").style.display="block";
   	
   	var bar = new ToolBar('arrangeResultBar','${task.seqNo?if_exists}[<@i18nName task.course/>]<@getTeacherNames task.arrangeInfo.teachers/>',null,true,true);
   	bar.addItem("<@bean.message key="action.detailSetting"/>",'setTimeAndRoom()','update.gif');
   	bar.addItem("<@bean.message key="action.save"/>",'save()','save.gif');
   	<#if RequestParameters["forward"]?exists>
   	bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
   	<#else>
   	bar.addItem("<@bean.message key="action.back"/>","parent.searchTask()","backward.gif");
   	</#if>

</script>
<#include "/templates/foot.ftl"/>