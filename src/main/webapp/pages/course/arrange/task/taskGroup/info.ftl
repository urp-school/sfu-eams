<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script> 
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="taskGroupInfoBar"></table>
   <table width="100%" border="0">
    <tr><td>
   <div class="dynamic-tab-pane-control tab-pane" id="tabPane1">
   <script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false);</script>
   
   <div style="display: block;" class="tab-page" id="tabPage1">
   <h2 class="tab"><a href="#"><@bean.message key="common.baseInfo"/></a></h2>
   <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage1"));</script>
   
	 <table width="100%" align="center" class="listTable">
	   <tr>
	     <td class="grayStyle" width="20%">&nbsp;<@bean.message key="attr.infoname"/>：</td>
	     <td class="brightStyle">${taskGroup.name}</td>
	     <td class="grayStyle" width="20%">&nbsp;<@bean.message key="attr.groupNo"/>：</td>
	     <td class="brightStyle">${taskGroup.id}</td>
	     <td class="grayStyle" width="20%">&nbsp;<@bean.message key="attr.priority"/>：</td>
	     <td class="brightStyle">${taskGroup.priority}</td>
	   </tr>
	   <tr>
	     <td class="grayStyle">&nbsp;<@bean.message key="attr.isSameTime"/>：</td>
	     <td class="brightStyle">
	         <#if taskGroup.isSameTime ==true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if>
	     </td>
	     <td class="grayStyle">&nbsp;<@bean.message key="attr.arrangedTaskCount"/>：</td>
	     <td class="brightStyle">${arrangedCount}</td>
	     <td class="grayStyle">&nbsp;<@bean.message key="attr.groupTaskCount"/>：</td>
	     <td class="brightStyle">${taskCount}</td>
	   </tr>
	 </table>
     <#include "availTimeInfo.ftl" />
     <table width="100%" align="center" class="listTable">
        <tr>
	     <td class="grayStyle" width="20%" >&nbsp;<@bean.message key="attr.groupRooms"/>：</td>
	     <td class="brightStyle">
	     <#if taskGroup.suggest.rooms?exists&&taskGroup.suggest.rooms?size!=0>
	     <#list taskGroup.suggest.rooms as room>
	         <@i18nName room/>/<@i18nName room.configType/>/(${room.capacityOfCourse})人
	         <#if (room_index%2==1)><br><#else>&nbsp;</#if>
	     </#list>
	     <#else><@bean.message key="info.group.noRoom"/></#if>
	     </td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="20%">&nbsp;<@bean.message key="attr.remark"/>：</td>
	     <td class="brightStyle">${taskGroup.suggest.time.remark?if_exists}</td>
	   </tr>
	</table>
   </div>
     
   <div style="display: block;" class="tab-page" id="tabPage2">
   <h2 class="tab"><a href="#"><@bean.message key="entity.teachTask" />(${taskCount})</a></h2>
   <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage2"));</script>
    <#include "groupTaskList.ftl"/>
   </div>
   
   <#include "adminClassList.ftl"/>
   </div>
   </td></tr></table>
   <form name="taskGroupForm" action="" method="post" onsubmit="return false;">
     <input name="taskGroup.id" type="hidden" value="${taskGroup.id}"/>
   </form>
   <script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskGroup.js"></script>
   <script type="text/javascript">
     setupAllTabs();
     var taskGroupId=${taskGroup.id};
     var taskCount=${taskCount};
     var adminClassCount=${taskGroup.adminClasses?size};
     var courseCode=<#if taskGroup.course?exists>'${taskGroup.course.code}'<#else>''</#if>
     var courseTypeName=<#if taskGroup.courseType?exists>'<@i18nName taskGroup.courseType/>'<#else>''</#if>
 
   var bar = new ToolBar('taskGroupInfoBar','<@bean.message key="entity.taskGroup" /><@bean.message key="common.detailInfo" />',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("排课建议", "updateSuggest()", 'update.gif');
   <#if !RequestParameters['notDisplayOperation']?exists>
   var menu1 = bar.addMenu("新增排课组","parent.newGroup()");
   menu1.addItem("修改排课组","edit()");
   menu1.addItem("<@bean.message key="action.deleteGroup"/>", "deleteGroup()");
   var menu2 = bar.addMenu("添加任务", "addTasks()",'new.gif');
   menu2.addItem("添加教师", "addTeachersSetting()");
   menu2.addItem("添加班级", "addAdminClassesSetting()");
   menu2.addItem("修改任务","taskEdit()");
   menu2.addItem("修改人数","updatePlanStdCount()");
   menu2.addItem("删除任务", "deleteTask()");
   menu2.addItem("删除班级","removeAdminClass()");
   
   function addTeachersSetting() {
    form.action = "taskGroup.do?method=addTeachersSetting";
    form.submit();
   }
   
   function taskEdit() {
    var id = getCheckBoxValue(document.getElementsByName("taskId"));
    if (id == "") {
        alert("<@bean.message key="prompt.task.selector" />");
        return;
    }
    if (id.indexOf(",") != -1) {
        alert("<@bean.message key="common.singleSelectPlease" />。");
        return;
    }
    window.open("teachTask.do?method=edit&forward=actionResult&task.id=" + id);
   }
   
   function addAdminClassesSetting() {
    form.action = "taskGroup.do?method=addAdminClassesSetting";
    form.submit();
   }
   </#if>
  </script>
 </body>
<#include "/templates/foot.ftl"/>