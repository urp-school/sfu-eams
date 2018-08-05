<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="pages/course/task/task.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script>
<script language="JavaScript" type="text/JavaScript" src="pages/course/arrange/arrange.js"></script> 
<style  type="text/css">
<!--
.trans_msg
    {
    filter:alpha(opacity=100,enabled=1) revealTrans(duration=.1,transition=1) blendtrans(duration=.2);
    }
-->
</style>
   <#assign taskList =tasks/>
   <#if RequestParameters['task.arrangeInfo.isArrangeComplete']?default("1")=="0">
   <#include "/pages/course/arrange/taskArrangeSuggestPrompt.ftl"/> 
   <#else>
   <#include "/pages/course/arrange/taskArrangeResult.ftl"/> 
   </#if>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
 <script>initToolTips()</script>
<table id="taskListBar"></table>
 <div id="processDIV" style="display:block">页面加载中...</div>
 <div id="contentDIV" style="display:none">
    <@table.table id="teachTask" width="100%" sortable="true" headIndex="1">
        <form name="taskListForm" action="" method="post" onsubmit="return false;">
        <#--下面两行代码为显示课程详细信息而设，请勿更改或者同名-->
        <input type="hidden" name="type" value="course"/>
        <input type="hidden" name="id" value=""/>
        
        <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']?if_exists}"/>
        <tr bgcolor="#ffffff" onkeypress="DWRUtil.onReturn(event, query)">
            <td align="center" >
                <img src="${static_base}/images/action/search.gif" align="top" onClick="javascript:query()" alt="<@bean.message key="info.filterInResult" />"/>
            </td>
            <td><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.course.code" maxlength="32" value="${RequestParameters['task.course.code']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.course.name" maxlength="20" value="${RequestParameters['task.course.name']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.teachClass.name" maxlength="20" value="${RequestParameters['task.teachClass.name']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.courseType.name" maxlength="20" value="${RequestParameters['task.courseType.name']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.teachClass.planStdCount" maxlength="7" value="${RequestParameters['task.teachClass.planStdCount']?if_exists}"/></td>
            <td><input style="width:100%" type="text" name="task.taskGroup.name" maxlength="20" value="${RequestParameters['task.taskGroup.name']?if_exists}"/></td>
            <td><select name="task.arrangeInfo.isArrangeComplete" style="width:100%" value="${RequestParameters["task.arrangeInfo.isArrangeComplete"]?default("")}"><option value="">全部</option><option value="0">未排</option><option value="1">已排</option></select></td>
        </tr>
        </form>
        <@table.thead>
            <@table.selectAllTd id="taskId"/>
            <@table.sortTd name="attr.taskNo" width="8%" id="task.seqNo"/>
            <@table.sortTd name="attr.courseNo" width="8%" id="task.course.code"/>
            <@table.sortTd name="attr.courseName" width="15%" id="task.course.name"/>
            <@table.sortTd name="entity.teachClass" width="20%" id="task.teachClass.name"/>
            <@table.td name="entity.teacher" width="10%"/>
            <@table.sortTd name="entity.courseType" width="10%" id="task.courseType.name"/>
            <@table.sortTd text="计划人数" width="5%" id="task.teachClass.planStdCount"/>
            <@table.sortTd name="attr.groupName" width="10%" id="task.taskGroup.name"/>
            <@table.sortTd text="排课状态" width="8%" id="task.arrangeInfo.isArrangeComplete"/>
        </@>
        <@table.tbody datas=tasks;task>
            <@table.selectTd id="taskId" value=task.id/>
            <td><#if task.arrangeInfo.isArrangeComplete == false>${task.seqNo?if_exists}<#else><A href="courseTable.do?method=taskTable&task.id=${task.id}">${task.seqNo?if_exists}</a></#if></td>
            <td><A href="javascript:courseInfo('id', ${task.course.id})">${task.course.code}</A></td>
            <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></a></td>
            <td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:190px;overflow:hidden;text-overflow:ellipsis;">${task.teachClass.name?html}</span></td>
            <td id="teachers_${task.id}"><@getTeacherNames task.arrangeInfo.teachers/></td>
            <td><@i18nName task.courseType/></td>
            <td>${task.teachClass.planStdCount}</td>
            <td title="${(task.taskGroup.name)?if_exists?html}" nowrap><span style="display:block;width:100px;overflow:hidden;text-overflow:ellipsis;">${(task.taskGroup.name)?if_exists?html}</span></td>
            <td<#if (task.arrangeInfo.isArrangeComplete)?exists && task.arrangeInfo.isArrangeComplete> style="color:blue"<#else> style="color:red"</#if>>${task.arrangeInfo.isArrangeComplete?string("已排", "未排")}</td>
        </@>
    </@>
</div>
<#list 1..3 as i><br></#list>
<script>
    var bar = new ToolBar('taskListBar','<#if !RequestParameters['task.arrangeInfo.isArrangeComplete']?exists || RequestParameters['task.arrangeInfo.isArrangeComplete']=="0"><@bean.message key="common.notArranged" /><#else><@bean.message key="common.alreadyArranged" /></#if>课程列表',null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("排课/调整", "adjust()");
    var menu1 = bar.addMenu("修改任务","editTeachTask()",'update.gif');
    menu1.addItem("排课建议","editSuggestTime()");
    bar.addItem("更换老师","changeTeacher()");
    bar.addItem("删除结果", "removeResult()", "delete.gif");
    var menu2 = bar.addMenu("<@bean.message key="action.export"/>","exportData()",'excel.png');
    menu2.addItem("平移教学周", "shift()");
    
    document.getElementById('processDIV').style.display="none";
    document.getElementById('contentDIV').style.display="block";
    
    <#--排课状态，结果是或者关系-->
    var arrangedMap=new Object();
    <#list tasks as task>
    arrangedMap["${task.id}"]=${task.arrangeInfo.isArrangeComplete?string("true", "false")};
    </#list>
    function isBeenArranged(isArranged) {
        if (isEmpty(isArranged)) {
            isArranged = false;
        }
        var taskIds = getSelectIds("taskId").split(",");
        var tmp = isArranged;
        for (var i = 0; i < taskIds.length; i++) {
            if (arrangedMap[taskIds[i]] == isArranged) {
                return tmp;
            }
        }
        return !tmp;
    }
    
    <#--不能访问，参数默认为未排(即，使用范围，比如只能在未排课的情况下进行，则isArranged写false)-->
    function isAccess(isArranged) {
        if (isEmpty(isArranged)) {
            isArranged = false;
        }
        if (isArranged) {
            <#--只能对未排课的任务提出警告-->
            if (!isBeenArranged(!isArranged)) {
                alert("当前所选择任务（可能有部分）还没有进行排课。");
                return false;
            }
        } else {
            <#--只能对已排课的任务提出警告-->
            if (isBeenArranged(!isArranged)) {
                alert("只能对皆为未排课的任务进行操作。");
                return false;
            }
        }
        return true;
    }
    
    function displayPrompt(taskId){
        <#if !RequestParameters['task.arrangeInfo.isArrangeComplete']?exists || RequestParameters['task.arrangeInfo.isArrangeComplete']=="0">
        displaySuggestOfArrange(taskId);
        <#else>
        displayArrangeResult(taskId);
        </#if>
    }
    function editSuggestTime(){
        <#--false：只能对未排课的任务操作-->
        if (isAccess(false)) {
            suggestTime(document.taskListForm);
        }
    }
    var form = document.taskListForm;
    function query(){
        transferParams(parent.document.taskForm, form, null, false);
        if ("" == form["task.arrangeInfo.isArrangeComplete"].value) {
            form.action="manualArrange.do?method=search";
        } else {
            form.action="manualArrange.do?method=taskList";
        }
        form.submit();
    }
    
    function courseInfo(selectId, courseId) {
       if (null == courseId || "" == courseId || isMultiId(selectId) == true) {
           alert("请选择一条要操作的记录。");
           return;
       }
       form.action = "courseSearch.do?method=info";
       form[selectId].value = courseId;
       form.submit();
    }
    
    function changeTeacher(){
        <#--true：只能对已排课的任务操作-->
        if (isAccess(true)) {
            setSearchParams();
            form.action="manualArrange.do?method=displayTeachers";
            submitId(form,"taskId",false);
        }
    }
    function setSearchParams(){
        var params = getInputParams(form,null,false);
        params += getInputParams(parent.document.taskForm,null,false);
        addInput(form,"params",params);
    }
    function adjust() {
        var taskId = getCheckBoxValue(document.getElementsByName("taskId"));
        if (taskId.indexOf(",")!=-1) {
            alert("请选择一个进行排课或调整。");
            return;
        }
        if (taskId=="") {
            alert("<@bean.message key="prompt.task.selector" />");
            return;
        }
        setSearchParams();
        form.action="manualArrange.do?method=manualArrange&task.id="+taskId;
        form.submit();
    }
    
    function shift(){
        <#--true：只能对已排课的任务操作-->
        if (isAccess(true)) {
            setSearchParams();
            form.action="manualArrange.do?method=shift";
            var offset = prompt("请输入平移量(正数为向后偏移，负数向前移动)","0");
            if(null!=offset){
                if (confirm("如果平移教学周超出被设置的教学周范围，将删除被移出的教学周。\n"
                          + "比如：假设当前被设置的教学周为1-17周时：\n"
                          + "　　　此时若向前移5周，结果为1-12周；\n"
                          + "　　　若向后移5周，结果为6-17周。\n\n"
                          + "是否要继续？") == false) {
                    return;
                }
                addInput(form,"offset",offset);
                submitId(form,"taskId",true);
            }
        }
    }
    
    function exportData(){
        if ("${RequestParameters["task.arrangeInfo.isArrangeComplete"]?default("")}" != "1") {
            alert("只能对都是已排课的任务进行导出。");
            return;
        }
        setSearchParams();
        form.action="teachTask.do?method=exportSetting";
        form.submit();
    }
    
    function editTeachTask(){
        <#--false：只能对未排课的任务操作-->
        if (isAccess(false)) {
            var id = getCheckBoxValue(document.getElementsByName("taskId"));
            if (id=="") {
                alert("<@bean.message key="prompt.task.selector"/>");
                return;
            }
            if (id.indexOf(",") != -1) {
                alert("<@bean.message key="common.singleSelectPlease" />。");
                return;
            }
            window.open("teachTask.do?method=edit&forward=actionResult&task.id=" + id);
        }
    }
    
    function removeResult() {
        <#--true：只能对已排课的任务操作-->
        if (isAccess(true)) {
            removeArrangeResult();
        }
    }
  </script>
</body>
<#include "/templates/foot.ftl"/>