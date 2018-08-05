<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body leftmargin="0" topmargin="0">
<@getMessage/>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
	<tr>
		<td>
			<table width="100%" align="center" class="listTable">
       			<form name="listForm" action="" onSubmit="return false;">
	   				<tr align="center" class="darkColumn">
				   	 	<td><@bean.message key="attr.taskNo"/></td>
				     	<td><@bean.message key="attr.courseNo"/></td>
				     	<td><@bean.message key="attr.courseName"/></td>
				     	<td><@bean.message key="entity.courseType"/></td>
				     	<td><@bean.message key="attr.year2year"/></td>
				     	<td><@bean.message key="attr.term"/></td>
				     	<td><@bean.message key="entity.studentType"/></td>
				     	<td><@bean.message key="entity.teachClass"/></td>
				     	<td align="center"><@bean.message key="action.input"/></td>
				     	<td align="center"><@bean.message key="system.button.modify"/></td>
				     	<td align="center">维护</td>
				     	<td align="center">导出</td>
	   				</tr>	   
	   				<#list (result.teachTaskList?sort_by("seqNo"))?if_exists as teachTask>
	   				<#if teachTask_index%2==1 ><#assign class="grayStyle" ></#if>
	   				<#if teachTask_index%2==0 ><#assign class="brightStyle" ></#if>
	   				<tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
					    <td>&nbsp;${ teachTask.seqNo?if_exists}</td>
					    <td>&nbsp;${ teachTask.course.code?if_exists}</td>
					    <td>&nbsp;<a href="javascript:showDetail('${teachTask.id}')" title="点击查看考勤记录"><@i18nName teachTask.course?if_exists/></a></td>
					    <td>&nbsp;<@i18nName teachTask.courseType?if_exists/></td>
					    <td>&nbsp;${teachTask.calendar.year}</td>
					    <td>&nbsp;${teachTask.calendar.term}</td>
					    <td>&nbsp;<@i18nName teachTask.teachClass?if_exists.stdType?if_exists/><#--><@getTeacherNames teachTask.arrangeInfo.teachers/>--><#--<@eraseComma teachTask.arrangeInfo.teacherNames?if_exists/>--></td>
					    <td>&nbsp;<@i18nName teachTask.teachClass?if_exists/></td>
	    				<#assign currentFlag=teachTask.calendar.contains(currentdate) />
	    				<#--><#assign currentFlag=true />-->
	    				<td align="center">
	    				<#if currentFlag >
					     	<a href="javascript:inputForm('${teachTask.id}')" >
					      	&lt;&lt;
					     	</a>
	     				<#else>非当前学期
	     				</#if>
	    				</td>
	    				<td align="center">
	     				<#if currentFlag >
		     				<a href="javascript:update('${teachTask.id}')" >
		      					&lt;&lt;
		     				</a>
	     				<#else>非当前学期
	     				</#if>
	    				</td>
	    				<td align="center">	    				
	     				<#if currentFlag >
					     	<a href="javascript:maintain('${teachTask.id}')" >
					      		&lt;&lt;
					     	</a>
	     				<#else>非当前学期
	     				</#if>
	    				</td>
	    				</td>
	    				<td align="center">
	     				<#if currentFlag>
					     	<a href="dutyRecordManagerWithTeacher.do?method=exportData&ids=${teachTask.id}&courseTakeTypeString=,null,1,2,3,4,">&lt;&lt;</a>
	     				<#else>非当前学期
	     				</#if>
	    				</td>
	   				</tr>
	   				</#list>
	   			</form>
     		</table>
		</td>
	</tr>
</table>
</body>
<script>
	function showDetail(teachTaskId){
    	window.open('dutyRecordManagerWithTeacher.do?method=listStudentRecordByTeachTask&teachTaskId='+teachTaskId);
    }
    
    function submitResetForm(){
          var location = self.location.href;
          var action = location.split("/");
          document.resetForm.action = action[action.length-1];
          document.resetForm.submit();
    }
    
    function update(teachTaskId){
    	self.location="dutyRecordManagerWithTeacher.do?method=maintainStudentRecordByTeachTask&teachTaskId="+teachTaskId;
        /*document.pageGoForm.method.value = "maintainRecordByTeachTask";
        document.pageGoForm.teachTaskId.value = teachTaskId;
        var location = self.location.href;
        var url = location.split("/");
        var action = url[url.length-1].split("?");        
        document.pageGoForm.action = action[0];
        document.pageGoForm.submit();*/
    }
    
    function inputForm(teachTaskId){
    self.location="inputDutyRecordWithTeacher.do?method=inputForm&teachTaskId="+teachTaskId;
        /*document.pageGoForm.method.value = "inputForm";
        document.pageGoForm.teachTaskId.value = teachTaskId;
        document.pageGoForm.action = "inputDutyRecordWithTeacher.do";
        document.pageGoForm.submit();*/
    }
    
    function maintain(teachTaskId){
    	window.open("dutyRecordManagerWithTeacher.do?method=maintainRecordByTeachTaskForm&teachTaskId="+teachTaskId);
    	//self.location="dutyRecordManagerWithTeacher.do?method=maintainRecordByTeachTaskForm&teachTaskId="+teachTaskId;
    }
</script>
<#include "/templates/foot.ftl"/>