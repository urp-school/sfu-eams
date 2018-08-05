<#include "/templates/head.ftl"/>
<BODY>
    <#macro statusStyle status>
      style="background-color:<#if status>green<#else>yellow</#if>"
    </#macro>
	<table id="myBar"></table>	
	<table width="100%" class="listTable">
	  <tr class="darkColumn"><td colspan="7"><@msg.message key="attr.taskNo"/>:${task.seqNo} <@msg.message key="attr.courseNo"/>:${task.course.code} <@msg.message key="attr.courseName"/>:<@i18nName task.course/> 授课教师:<@getTeacherNames task.arrangeInfo.teachers/></td></tr>
	  <tr>
	    <td>百分比</td><td colspan="4"><#list gradeTypeInState as gradeType><@i18nName gradeType/> ${(gradeState.getPercent(gradeType)?string.percent)?if_exists}&nbsp;</#list></td>
	    <td>总评成绩精确度</td><td>${gradeState.precision}</td>
	  </tr>
	  <tr><td>成绩类型</td><#list gradeTypes as gradeType><td><@i18nName gradeType/> </td></#list></tr>
	  <tr><td>录入状态</td><#list gradeTypes as gradeType><td <@statusStyle gradeState.isInput(gradeType)/>>${gradeState.isInput(gradeType)?string("是","否")}</td></#list></tr>
	  <tr><td>确认状态</td><#list gradeTypes as gradeType><td <@statusStyle gradeState.isConfirmed(gradeType)/>>${gradeState.isConfirmed(gradeType)?string("是","否")}</td></#list></tr>
      <tr><td>发布状态</td><#list gradeTypes as gradeType><td <@statusStyle gradeState.isPublished(gradeType)/>>${gradeState.isPublished(gradeType)?string("是","否")}</td></#list></tr>
	</table>
	<pre>
	   录入状态是指:该成绩(所有学生)教师是否已经录入一遍
	   确认状态是指:该成绩(所有学生)教师是否确认,一般情况下是通过二次录入进行确认
	   发布状态是指:该成绩(所有学生)是否让学生可见.
	</pre>
	<form name="actionForm" method="post" action="courseGrade.do?method=editGradeState" onsubmit="return false;">
	  <input type="hidden" name="taskId" value="${task.id}"/>
	</form>
    <script>
       var form =document.actionForm;
       function edit(){
          form.submit();
       }
       var bar = new ToolBar("myBar","成绩状态",null,true,true);
       bar.addItem("修改","edit()");
       bar.addBack();
    </script>
</body>
<#include "/templates/foot.ftl"/> 