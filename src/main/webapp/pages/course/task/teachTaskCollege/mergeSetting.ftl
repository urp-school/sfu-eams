<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/Menu.js"></script>
 
<body  LEFTMARGIN="0" TOPMARGIN="0" >
<table id="mergeBar"></table>
<script>
   var bar=new ToolBar("mergeBar","<@bean.message key="entity.teachTask"/> <@bean.message key="action.merge"/>",null,true,true);
   bar.addItem("<@bean.message key="action.merge"/>","merge()");
   bar.addBack("<@msg.message key="action.back"/>");
</script>
 <table width="100%" border="0" class="listTable">
 <form name="taskListForm" method="post" action="" onsubmit="return false;">
    <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']}"/>
    <input type="hidden" name="params" value="${RequestParameters['params']?if_exists}">
    <tr align="center" class="darkColumn">
      <td align="center" width="3%">
        <input type="radio" onClick="toggleCheckBox(document.getElementsByName('taskId'),event);">
      </td>
      <td width="10%"><@msg.message key="attr.taskNo"/></td>
      <td width="25%"><@bean.message key="attr.courseName"/></td>
      <td width="20%"><@bean.message key="entity.teachClass"/></td>
      <td width="25%"><@bean.message key="entity.adminClass"/></td>
      <td width="10%"><@bean.message key="entity.teacher"/></td>
      <td width="5%">人数</td>
      <td width="5%"><@bean.message key="attr.credit"/></td>
      <td width="5%"><@bean.message key="attr.creditHour"/></td>
    </tr>
    <#list taskList as task>
   	  <#if task_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if task_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)" 
     onmouseout="swapOutTR(this)" onclick="onRowChange(event)" >
      <td class="select">
        <input type="radio" name="taskId" value="${task.id}">
      </td>
      <td>${task.seqNo}</td>
      <td>
      <A href="teachTask.do?method=info&task.id=${task.id}" >
       <@i18nName task.course?if_exists/></a>
      </td>
      <td>${task.teachClass.name}</td>
      <td><#list task.teachClass.adminClasses as adminClass>&nbsp;${adminClass.name}</#list></td>   
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td>${task.teachClass.planStdCount}</td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.overallUnits}</td>      
    </tr>
	</#list>
	</form>
	</table>	
	
<table class="infoTable" width="100%">
    <tr align="center" class="darkColumn">
      <td width="10%"><@bean.message key="common.tip"/></td>
      <td><@bean.message key="entity.content"/></td>
    </tr>
	<#if courseDiff?exists>
	 <tr bgcolor="#ffffff">
		 <td class="title"><@bean.message key="attr.courseName"/></td>
		 <td class="content"><@bean.message key="info.task.merge.courseTip"/></td>
	 </tr>
	</#if>
    <#if teacherDiff?exists>
	 <tr bgcolor="#ffffff">
		 <td class="title"><@bean.message key="attr.teacherId" /></td>
		 <td class="content"><@bean.message key="info.task.merge.teacherTip"/></td>
	 </tr>
	</#if>
	<#if arrangeCompelte?exists>
	 <tr bgcolor="#ffffff">
		 <td class="title"><@bean.message key="info.task.mergeAffect.arrange"/></td>
		 <td class="content"><@bean.message key="info.task.merge.arrange"/></td>
	 </tr>
     </#if>     
	 <tr bgcolor="#ffffff">
		 <td class="title"><@bean.message key="entity.adminClass"/></td>
		 <td class="content"><@bean.message key="info.task.merge.class"/></td>
	 </tr>
	 
	 <tr bgcolor="#ffffff">
		 <td class="title"><@bean.message key="info.task.mergeAffect.election"/></td>
		 <td class="content"><@bean.message key="info.task.merge.election"/></td>
	 </tr>
	 <tr bgcolor="#ffffff">
		 <td class="title"><@bean.message key="common.other"/></td>
		 <td class="content"><@bean.message key="info.task.merge.remark"/></td>
	 </tr>	 	 
	<table>
<script>
    function merge(){
       <#if courseDiff?exists>
        alert("课程代码不同,不能合并");
        return;
       <#else>
       var form = document.taskListForm;
       var id = getRadioValue(form.taskId);
       if(id==""){alert("<@bean.message key="info.task.merge.select"/>");return;}
       var taskIdElem = document.getElementsByName("taskId");
       var taskIds ="";
       for(var i=0 ;i < taskIdElem.length;i++){
         if(taskIds !="") taskIds +=",";
         taskIds += taskIdElem[i].value;
       }
       form.action="teachTask.do?method=merge&reservedId="+id+"&taskIds=" + taskIds+"&hold=true";
       form.submit();
       </#if>
    }
</script>
</body>
<#include "/templates/foot.ftl"/>