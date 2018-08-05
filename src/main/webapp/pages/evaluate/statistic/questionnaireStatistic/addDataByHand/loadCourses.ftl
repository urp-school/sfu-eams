<#include "/templates/head.ftl"/>
 <script>
    var detailArray = {};
    var teachTaskArray= new Array();
    <#list result.teachTaskPage.items as teachTask>
    	teachTaskArray['${teachTask.id}']= new Array();
    	<#list teachTask.arrangeInfo.teachers?if_exists as teacher>
    		teachTaskArray['${teachTask.id}'][${teacher_index}]=new Array();
    		teachTaskArray['${teachTask.id}'][${teacher_index}][0]='${teacher.id}';
    		teachTaskArray['${teachTask.id}'][${teacher_index}][1]='${teacher.name}';
    	</#list>
    </#list>
    function getIds(){
       return(getRadioValue(document.getElementsByName("courseId")));
    }
    
    function getName(id){
       if (id != ""){
          return detailArray[id]['name'];
       }else{
          return "";
       }       
    }
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value = pageNo;
       document.pageGoForm.submit();
    }
    function doSubmit(){
    	var taskId = getIds();
    	if(""==taskId){
    		alert("请选择一个教学任务");
    		return 
    	}
    	var flag = document.getElementById("questionnaire"+taskId);
    	if("false"==flag){
    		if(confrim("你选课的课程没有问卷,请在[课程问卷]里面指定好问卷再提交")){
    			window.close();
    			return;
    		}
    	}
    	var departmentValue= document.getElementById("questionnaireValue"+taskId);
    	if(null!=self.opener.document.getElementById("departmentId")){
    		self.opener.addOption("departmentId",departmentValue.value);
    	}
    	var taskName = getName(taskId);
    	self.opener.setTeachTaskIdAndDescriptions(taskId,taskName);
    	var teacherArray = teachTaskArray[taskId];
    	self.opener.removeSelect("teacherId");
    	self.opener.addDateOfSelect("teacherId",teacherArray);
    	window.close();
    }
 </script>
 <BODY onblur="self.focus();" LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar" width="100%"></table>
	<script>
   		var bar = new ToolBar('backBar','<@bean.message key="field.questionnaireStatistic.courseList"/>',null,true,true);
   		bar.setMessage('<@getMessage/>');
   		bar.addItem("选课课程","doSubmit()");
	</script>
<table cellpadding="0" cellspacing="0" width="100%" border="0">  
   <tr>
    <td>
    <#if result.teachTaskPage?exists>
     <table width="100%" align="center" class="listTable">
	   <tr align="center" class="darkColumn">
	     <td align="center">&nbsp;</td>
	     <td><@msg.message key="attr.taskNo"/></td>
	     <td><@bean.message key="field.teachAccident.courseName"/></td>
	     <td><@bean.message key="field.questionnaireStatistic.courseTeacher"/></td>
	     <td><@bean.message key="field.questionnaireStatistic.courseOpenTime"/></td>
	     <td><@bean.message key="field.questionnaireStatistic.courseOpenCollege"/></td>
	     <td>课程问卷</td>
	   </tr>
	   <form name="listForm" action="" onsubmit="return false;">  
	   <#list result.teachTaskPage.items?sort_by("seqNo")?if_exists as teachTask>
	   <#if teachTask_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if teachTask_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}">
	    <script>
	       detailArray['${teachTask.id}'] = {'name':'${teachTask.course.name}'};
	    </script>
	    <td width="5%" align="center" bgcolor="#CBEAFF">
	     <input type="radio" name="courseId" value="${teachTask.id}"/></td>
	     <td align="center">${teachTask.seqNo}</td>
	    <td align="center">${teachTask.course.name}</td>
	    <td align="center"><#list teachTask.arrangeInfo.teachers?if_exists?sort_by("name") as teacher>
	    		<#if teacher_index==teachTask.arrangeInfo.teachers?size-1>
	    			${teacher.name?if_exists}
	    		<#else>
	    			${teacher.name?if_exists},
	    		</#if>
	    	</#list>
	    </td>
	    <td align="center">${teachTask.calendar.year}&nbsp;&nbsp;${teachTask.calendar.term}</td>
	    <td align="center">${teachTask.arrangeInfo.teachDepart.name}</td>
	    <td align="center"><input type="hidden" id="questionnaire${teachTask.id}" name="questionnaire${teachTask.id}" value="<#if teachTask.questionnaire?exists>true<#else>false</#if>"><#if teachTask.questionnaire?exists><input type="hidden" id="questionnaireValue${teachTask.id}" name="questionnaireValue${teachTask.id}"  value="${teachTask.questionnaire.id},${teachTask.questionnaire.depart.name}">${teachTask.questionnaire.description}<#else><font red="red">无问卷</font></#if></td>
	   </tr>
	   </#list>
	   </form>
	   <form name="pageGoForm" method="post">
	    <#assign paginationName="teachTaskPage" />
	    <#include "/templates/pageBar.ftl"/>
	    </form> 
     </table>
     </#if>
    </td>
   </tr>
   <tr>
    <td align="center" colspan="3" >
     <input type="button" name="button1" value="<@bean.message key="action.confirm"/>" class="buttonStyle"
            onClick="doSubmit();"/>
    </td>
   </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>