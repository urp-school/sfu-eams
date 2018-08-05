<#include "/templates/head.ftl"/>
<body> 
 <table width="80%" align="center" class="listTable">
   <form name="taskGroupForm" method="post" action="" onsubmit="return false;">
    <input type="hidden" name="taskGroup.id" value="${taskGroup.id}"/>
    <input type="hidden" name="taskGroup.name" value="${RequestParameters['']?if_exists}"/>
       <tr class="darkColumn">
        <td align="center">为${taskGroup.name}排课组指定默认的课程和课程类别</td>
	   </tr>
	   <#if course?exists>
	   <tr class="grayStyle">
        <td >该组内任务的课程全部为<font color="red"><@i18nName course/></font><br>
          <input name="updateCourse" type="checkbox" value="1" checked/>
          <input name="course.id" type="hidden" value="${course.id}"/>
           是否将该课程设置为该组的默认课程，以便系统在添加任务时，默认显示该课程的新任务。
        </td>
	   </tr>
	   </#if>
	   <#if courseType?exists>
	   <tr class="grayStyle">
        <td >该组内任务的课程类别全部为<font color="red"><@i18nName courseType/></font><br>
          <input name="updateCourseType" type="checkbox" value="1" checked/>
          <input name="courseType.id" type="hidden" value="${courseType.id}"/>
           是否将该课程类别设置为该组的默认课程类别，以便系统在添加任务时，默认显示该类别的新任务。
        </td>
	   </tr>
	   </#if>
	   <tr class="darkColumn">
	     <td colspan="6" align="center">
	       <input type="button" value="<@bean.message key="action.next"/>" name="saveButton" onClick="save(this.form)" class="buttonStyle"/>&nbsp;
	       <input type="reset" name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle"/>
	     </td>
	   </tr>
	   </form>
</table>
<script>
    function save(form){
        form.action="taskGroup.do?method=updateGroupCourse";
        form.submit();
    }
</script>
</body>
<#include "/templates/foot.ftl"/> 