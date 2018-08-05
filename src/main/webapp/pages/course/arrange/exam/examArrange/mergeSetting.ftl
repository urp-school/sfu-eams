<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 
<body  LEFTMARGIN="0" TOPMARGIN="0" >
<table id="mergeBar"></table>

 <table width="100%" border="0" class="listTable">
    <tr align="center" class="darkColumn">
      <td align="center" width="3%">
        <input type="radio" onClick="toggleCheckBox(document.getElementsByName('taskId'),event);">
      </td>
      <td width="10%"><@msg.message key="attr.taskNo"/></td>
      <td width="20%"><@bean.message key="attr.courseName"/></td>
      <td width="20%"><@bean.message key="entity.teachClass"/></td>
      <td width="10%"><@bean.message key="entity.teacher"/></td>
      <td width="20%">考试时间</td>
      <td width="20%">考场安排(教室/考试人数/空闲)</td>
    </tr>
    <#list taskList as task>
   	  <#if task_index%2==1 ><#assign class="grayStyle"/></#if>
	  <#if task_index%2==0 ><#assign class="brightStyle"/></#if>
     <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" onclick="onRowChange(event)">
      <td class="select">
        <input type="radio" name="taskId" value="${task.id}">
      </td>
      <td>${task.seqNo}</td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}"><@i18nName task.course?if_exists/></a></td>
      <td>${task.teachClass.name}</td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td>${task.arrangeInfo.digestExam(task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],RequestParameters['examType.id'],"第:weeks周 :day :time")}</td>
      <td><#list task.arrangeInfo.getExamActivities(examType) as activity><#if activity.room?exists><@i18nName activity.room/>/${activity.examTakes?size}/空闲${(activity.room.capacityOfExam)?default(0)-activity.examTakes?size}<#else>没有教室/${activity.examTakes?size}/</#if><#if activity_has_next><br></#if></#list></td>   
    </tr>
	</#list>
	</table>	
	
<table class="infoTable" width="100%">
    <tr align="center" class="darkColumn">
      <td width="20%"><@bean.message key="common.tip"/></td>
      <td><@bean.message key="entity.content"/></td>
    </tr>
	 <tr bgcolor="#ffffff">
		 <td class="title" width="20%">合并影响</td>
		 <td class="content">选择合并后保留的教学任务,其他同一时间的考场安排将会合并按照选定任务排考教室里</td>
	 </tr>
	<table>
 <form name="taskListForm" method="post" action="" onsubmit="return false;">
    <input type="hidden" name="params" value="${RequestParameters['params']?if_exists}">
    <input type="hidden" name="examType.id" value="${RequestParameters['examType.id']?if_exists}">
 </form>
<script>
   var bar=new ToolBar("mergeBar","排课结果合并",null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if sameTime>
   bar.addItem("<@bean.message key="action.merge"/>","merge()");
   <#else>
   $("error").innerHTML="你选择的考试不在同一时间，不能进行教室合并";
   </#if>
   bar.addBack("<@msg.message key="action.back"/>");
    function merge(){
       var form = document.taskListForm;
       var id = getRadioValue(document.getElementsByName("taskId"));
       if (id == "") {
       	alert("<@bean.message key="info.task.merge.select"/>");
       	return;
       }
       form.action="examArrange.do?method=mergeRoom&reservedId="+id+"&taskIds=${RequestParameters['taskIds']}";
       if(confirm("确定合并?")){
          form.submit();
       }
    }
</script>
</body>
<#include "/templates/foot.ftl"/>