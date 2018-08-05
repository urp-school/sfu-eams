<#include "/templates/head.ftl"/>
<BODY>
    <#macro statusStyle status>
      style="background-color:<#if status>green<#else>yellow</#if>" title="点击改变状态"
    </#macro>
	<table id="myBar"></table>	
	<table width="100%" class="listTable">
	 <form name="actionForm" method="post" action="courseGradeDuplicate.do?method=saveGradeState" onsubmit="return false;">
	  <input type="hidden" name="taskId" value="${task.id}">
	  <tr class="darkColumn"><td colspan="7"><@msg.message key="attr.taskNo"/>:${task.seqNo} <@msg.message key="attr.courseNo"/>:${task.course.code} <@msg.message key="attr.courseName"/>:<@i18nName task.course/> 授课教师:<@getTeacherNames task.arrangeInfo.teachers/></td></tr>
	  <tr>
	    <td>百分比</td><td colspan="4"><#list gradeTypeInState as gradeType><@i18nName gradeType/> ${(gradeState.getPercent(gradeType)?string.percent)?if_exists}&nbsp;</#list></td>
	    <td>总评成绩精确度</td><td>${gradeState.precision}</td>
	  </tr>
	  <tr><td>成绩类型</td><#list gradeTypes as gradeType><td><@i18nName gradeType/> </td></#list></tr>
	  <tr><td>录入状态</td><#list gradeTypes as gradeType><input name="${gradeType.id}Input" type="hidden" value="${gradeState.isInput(gradeType)?string("1","0")}"><td onclick="changeStatus(event)" id="${gradeType.id}Input"  <@statusStyle gradeState.isInput(gradeType)/>>${gradeState.isInput(gradeType)?string("是","否")}</td></#list></tr>
	  <tr><td>确认状态</td><#list gradeTypes as gradeType><input name="${gradeType.id}Confirm" type="hidden" value="${gradeState.isConfirmed(gradeType)?string("1","0")}"><td onclick="changeStatus(event)" id="${gradeType.id}Confirm" <@statusStyle gradeState.isConfirmed(gradeType)/>>${gradeState.isConfirmed(gradeType)?string("是","否")}</td></#list></tr>
      <tr><td>发布状态</td><#list gradeTypes as gradeType><input name="${gradeType.id}Publish" type="hidden" value="${gradeState.isPublished(gradeType)?string("1","0")}"><td onclick="changeStatus(event)" id="${gradeType.id}Publish" <@statusStyle gradeState.isPublished(gradeType)/>>${gradeState.isPublished(gradeType)?string("是","否")}</td></#list></tr>
	</table>
	</form>
	<pre>
	   录入状态是指:该成绩(所有学生)教师是否已经录入一遍
	   确认状态是指:该成绩(所有学生)教师是否确认,一般情况下是通过二次录入进行确认
	   发布状态是指:该成绩(所有学生)是否让学生可见.	  
	</pre>
    <script>
       var bar  = new ToolBar("myBar","成绩状态",null,true,true);
       bar.addItem("<@msg.message key="action.save"/>","save()","save.gif");
       bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
       
       var form =document.actionForm;
       function save(){
          var params = getInputParams(parent.document.taskForm);
          addParamsInput(form,params);
          form.submit();
       }
       function changeStatus(event){
          var td = getEventTarget(event);
          if(form[td.id].value==0){
             td.style.backgroundColor="green"
             td.innerHTML="是";
             form[td.id].value=1;
          }else{
             td.style.backgroundColor="yellow"
             td.innerHTML="否";
             form[td.id].value=0;
          }
       }
    </script>
</body>
<#include "/templates/foot.ftl"/> 