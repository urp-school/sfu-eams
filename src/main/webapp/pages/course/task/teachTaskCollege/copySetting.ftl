<#include "/templates/head.ftl"/>
<body>
<table id="copySettingBar"></table>
<script>
  var bar=new ToolBar("copySettingBar","拷贝教学任务",null,true,true);
  bar.addItem("确定复制",'copy(document.copySettingForm)');
  bar.addBack("<@msg.message key="action.back"/>");
  bar.addHelp("<@msg.message key="action.help"/>","teachTask/copySetting");
</script>
   	 <#assign continuedWeek><@bean.message key="attr.continuedWeek"/></#assign>
   	 <#assign oddWeek><@bean.message key="attr.oddWeek"/></#assign>
   	 <#assign evenWeek><@bean.message key="attr.evenWeek"/></#assign>
   	 <#assign randomWeek><@bean.message key="attr.randomWeek"/></#assign>
     <#assign weekCycle={'1':'${continuedWeek}',
                         '2':'${oddWeek}',
                         '3':'${evenWeek}',
                         '4':'${randomWeek}'}>
     <table width="100%" align="center" class="formTable">
        <form name="copySettingForm" action="?method=copy" method="post" onsubmit="return false;">
        <input type="hidden" name="params" value="${RequestParameters['params']?if_exists}">
	    <tr>
	    <td class="darkColumn">1、复制到指定学期
  	        <select id="stdType" name="teachCalendar.studentType.id" style="width:100px"><option value="${calendar.studentType.id}"></option></select>
  	        <select id="year" name="teachCalendar.year" style="width:100px"><option value="${calendar.year}"></option></select>
  	        <select id="term" name="teachCalendar.term" style="width:100px"><option value="${calendar.term}"></option></select>
  	     </td>
	    </tr>
	    <tr>
  	     <td class="darkColumn">
  	      2、<input type="checkbox" name="taskCopyParams.copySeqNo"/>
  	       保持课程序号不变
  	       <input type="checkbox" name="taskCopyParams.deleteExistedTask"/>
  	        如果序号相同,则删除已有任务
  	       <input type="checkbox" name="taskCopyParams.deleteExistedCourseTakes"/>
  	        如果序号相同,删除学生名单
  	     </td>
	    </tr>
	    <tr>
  	     <td class="darkColumn">
  	      3、<input type="checkbox" name="taskCopyParams.copyCourseTakes" checked/>
  	       复制学生名单 
  	     </td>
	    </tr>
	    <tr>
	    	<td class="darkColumn">4、每个任务复制数量为&nbsp;<select name="taskCopyParams.copyCount" style="width:50px"><#list 1..20 as i><option value="${i}">${i}</option></#list></select>&nbsp;个</td>
	    </tr>
	    <tr>
  	     <td class="darkColumn">
  	      5、说明：不复制的任务的排课结果、排考结果等
  	     </td>
	    </tr>
	   <tr class="darkColumn">
	     <td colspan="6" align="center" >
	       待复制的任务列表(${taskList?size}个任务)
	     </td>
	   </tr>
     </table>
     <table  class="listTable">
	 <tr align="center" class="darkColumn">
	      <td width="5%"><@bean.message key="attr.taskNo"/></td>
	      <td width="15%"><@bean.message key="attr.courseName"/></td>
	      <td width="15%"><@bean.message key="entity.teachClass"/></td>
	      <td width="10%"><@bean.message key="entity.teacher"/></td>
	      <td width="4%">占用周</td>
	      <td width="4%">周课时</td>
	      <td width="4%">周数</td>
	      <td width="4%">总课时</td>
	      <td width="4%">节次</td>
	    </tr>
	    <#list taskList?sort_by("seqNo") as task>
	   	  <#if task_index%2==1 ><#assign class="grayStyle" ></#if>
		  <#if task_index%2==0 ><#assign class="brightStyle" ></#if>
	     <tr class="${class}" align="center" >
	      <td><input type="hidden" name="taskId" value="${task.id}">${task.seqNo?if_exists}</td>
	      <td><@i18nName task.course/></td> 
	      <td>${task.teachClass.name?html}</td>
	      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
	      <td>${weekCycle[task.arrangeInfo.weekCycle?string]}</td>
	      <td>${task.arrangeInfo.weekUnits}</td>
	      <td>${task.arrangeInfo.weeks}</td>
	      <td>${task.arrangeInfo.overallUnits}</td>
	      <td>${task.arrangeInfo.courseUnits}</td>
	    </tr>
		</#list>
     </table>
   </form>
     <script>
        function copy(form){
           if(confirm("请确认复制的学年度和学期,以及其他复制参数是否正确?")) {
            form.submit();
           }
        }
       
     </script>
 </body>
<#include "/templates/calendarSelect.ftl"/>
<#include "/templates/foot.ftl"/>