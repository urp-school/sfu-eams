<#include "/templates/head.ftl"/>
 <script>
    var detailArray = {};
    var teachTaskArray= new Array();
    <#list result.pagination.items as teachTask>
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
    	var taskName = getName(taskId);
    	self.opener.setTeachTaskIdAndDescriptions(taskId,taskName);
    	var teacherArray = teachTaskArray[taskId];
    	self.opener.removeSelect("teacherId");
    	self.opener.addDateOfSelect("teacherId",teacherArray);
    	window.close();
    }
    function doclear(){
    	self.opener.setTeachTaskIdAndDescriptions('','');
    	self.opener.removeSelect("teacherId");
    	window.close();
    }
 </script>
 <BODY onblur="self.focus();" LEFTMARGIN="0" TOPMARGIN="0">
 	<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','<@bean.message key="field.questionnaireStatistic.courseList"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("选择课程","doSubmit()");
</script>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">  
   <tr>
    <td>
     <table width="100%" align="center" class="listTable">
	   <tr align="center" class="darkColumn">
	     <td align="center">&nbsp;</td>
	     <td><@msg.message key="attr.taskNo"/></td>
	     <td><@bean.message key="field.teachAccident.courseName"/></td>
	     <td><@bean.message key="field.questionnaireStatistic.courseTeacher"/></td>
	     <td><@bean.message key="field.questionnaireStatistic.courseOpenTime"/></td>
	     <td><@bean.message key="field.questionnaireStatistic.courseOpenCollege"/></td>
	   </tr>
	   <#if result.pagination?exists>
	   <form name="listForm" action="" onsubmit="return false;">
	   <#list result.pagination.items?if_exists?sort_by("id") as teachTask>
	   <#if teachTask_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if teachTask_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}">
	    <script>
	       detailArray['${teachTask.id}'] = {'name':'${teachTask.course.name}'};
	    </script>
	    <td width="5%" align="center" bgcolor="#CBEAFF">
	     <input type="radio" name="courseId" value="${teachTask.id}"/></td>
	     <td width="15%"align="center">${teachTask.seqNo}</td>
	    <td align="center">${teachTask.course.name}</td>
	    <td align="center"width="17%"><#list teachTask.arrangeInfo.teachers?if_exists?sort_by("name") as teacher>
	    		<#if teacher_index==teachTask.arrangeInfo.teachers?size-1>
	    			${teacher.name?if_exists}
	    		<#else>
	    			${teacher.name?if_exists},
	    		</#if>
	    	</#list>
	    </td>
	    <td align="center">${teachTask.calendar.year}&nbsp;&nbsp;${teachTask.calendar.term}</td>
	    <td align="center">${teachTask.arrangeInfo.teachDepart.name}</td>
	   </tr>
	   </#list>
	   </form>
	   <form name="pageGoForm" method="post" action="" onsubmit="return false;">
	    <#assign paginationName="pagination"/>
	    <#include "/templates/pageBar.ftl"/>
	    </form> 
	    </#if>
     </table>
    </td>
   </tr>
    <td align="center" colspan="3" >
     <input type="button" name="button1" value="<@bean.message key="action.confirm"/>" class="buttonStyle"
            onClick="doSubmit();"  />&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" name="button2" value="<@bean.message key="field.evaluate.clear"/>" class="buttonStyle" 
            onClick="doclear();"/>
    </td>
   </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>