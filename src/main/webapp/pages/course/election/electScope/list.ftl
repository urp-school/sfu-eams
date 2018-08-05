<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="electionTaskBar"></table>
<@table.table id="electtable" width="100%" sortable="true" headIndex="1">
    <tr bgcolor="#ffffff" onkeypress="DWRUtil.onReturn(event, pageGoWithSize)">
      <td align="center" >
        <img src="${static_base}/images/action/search.gif"  align="top" onClick="pageGoWithSize()" alt="<@bean.message key="info.filterInResult" />"/>
      </td>
      <form name="taskListForm" action="" method="post" onsubmit="return false;">
        <#--下面两行代码为显示课程详细信息而设，请勿更改或者同名-->
        <input type="hidden" name="type" value="course"/>
        <input type="hidden" name="id" value=""/>

      <td><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.course.code" maxlength="32" value="${RequestParameters['task.course.code']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.course.name" maxlength="20" value="${RequestParameters['task.course.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.teachClass.name" maxlength="20" value="${RequestParameters['task.teachClass.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.courseType.name" maxlength="20" value="${RequestParameters['task.courseType.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="task.teachClass.planStdCount" maxlength="10" value="${RequestParameters['task.teachClass.planStdCount']?if_exists}"/></td>
      <#assign electCountCompare=''/>
      <#if RequestParameters['electInfo.electCountCompare']?exists>
        <#assign electCountCompare=RequestParameters['electInfo.electCountCompare']/>
      </#if>
      <td>
         <select name="electInfo.electCountCompare" style="width:100%">
            <option value="" <#if electCountCompare='0'> selected</#if>>全部</option>
            <option value="1" <#if electCountCompare='1'> selected</#if>>实选>上限</option>
            <option value="0" <#if electCountCompare='0'> selected</#if>>实选=上限</option>
            <option value="-1" <#if electCountCompare='-1'> selected</#if>>实选<上限</option>
         </select>
      </td>
      <td>
         <select name="task.electInfo.isElectable" style="width:100%">
            <option value="">全部</option>
            <option value="1"><@bean.message key="attr.electable"/></option>
            <option value="0"><@bean.message key="attr.unelectable"/></option>
         </select>
      </td>
      </form>
    </tr>
    
    <@table.thead>
      <@table.selectAllTd id="taskId"/>
      <@table.sortTd name="attr.index" width="6%" id="task.seqNo"/>
      <@table.sortTd width="6%" name="attr.id" id="task.course.code"/>
      <@table.sortTd width="20%" name="attr.courseName" id="task.course.name"/>
      <@table.td width="10%" name="entity.teacher"/>
      <@table.sortTd name="entity.teachClass" id="task.teachClass.name"/>
      <@table.sortTd name="entity.courseType" id="task.courseType.name"/>
      <@table.sortTd width="8%" text="计划" id="task.teachClass.planStdCount"/>
      <@table.sortTd width="8%" text="实际/上限" id="task.teachClass.stdCount"/>
      <@table.sortTd width="8%" text="参选状态" id="task.electInfo.isElectable"/>
    </@>
    <@table.tbody datas=tasks;task>
      <@table.selectTd id="taskId" value=task.id/>
      <td><A href="courseTable.do?method=taskTable&task.id=${task.id}" title="查看课程安排">${task.seqNo?if_exists}</a></td>
      <td><A href="courseInfo('id', ${task.course.id})">${task.course.code}</A></td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course?if_exists/></a></td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:150px;overflow:hidden;text-overflow:ellipsis;"><#if task.teachClass.gender?exists>(<@i18nName task.teachClass.gender/>)</#if>${task.teachClass.name?html}</span></td>
      <td title="${task.courseType.name?html}" nowrap><span style="display:block;width:100px;overflow:hidden;text-overflow:ellipsis;">${task.courseType.name?html}</span></td>
      <td>${task.teachClass.planStdCount}</td>
      <td><A href="teachTask.do?method=printStdListForDuty&teachTaskIds=${task.id}" title="查看学生名单" target="_blank">${task.teachClass.stdCount}/${task.electInfo.maxStdCount}</a></td>
      <#assign beenElectabled><A href="#" onclick="javascript:info('${task.id}')"><font color="blue">已参选</font></A></#assign>
      <td<#if (task.electInfo.isElectable)?exists && task.electInfo.isElectable> style="color:blue"<#else> style="color:red"</#if>>${task.electInfo.isElectable?string(beenElectabled, "不参选")}</td>
     </@>
    </@>
    <script>
   var bar = new ToolBar("electionTaskBar","教学任务列表",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("修改任务(选课)","editTeachTask()","update.gif", "仅对教学任务的选课信息进行修改。");
   var menu1 = bar.addMenu("查看选课范围","info()");
   menu1.addItem("统一设置范围","setElectInfo(1)","update.gif","按照规则给每个任务设置选课范围,人数,是否允许退课，同时把当前设为参选。");
   menu1.addItem("各项设置范围","edit()","update.gif","允许对所选任务进行多项设置，多个任务以所选的第一个任务为选课范围而设置。");
   menu1.addItem("<@bean.message key="action.cancelElectable"/>","setElectInfo(0)","delete.gif");
   var menu2 = bar.addMenu("允许退课","setCancelable(true)");
   menu2.addItem("不允许退课","setCancelable(false)");
   
   
    <#--参选状态，结果是或者关系-->
    var electabledMap=new Object();
    <#list tasks as task>
    electabledMap["${task.id}"]=${task.electInfo.isElectable?string("true", "false")};
    </#list>
    function isBeenElectabled(isElectabled) {
        if (isEmpty(isElectabled)) {
            isElectabled = false;
        }
        var taskIds = getSelectIds("taskId").split(",");
        var tmp = isElectabled;
        for (var i = 0; i < taskIds.length; i++) {
            if (electabledMap[taskIds[i]] == isElectabled) {
                return tmp;
            }
        }
        return !tmp;
    }
    
    <#--不能访问，参数默认为不参选(即，使用范围，比如只能在不参选的情况下进行，则isElectabled写false)-->
    function isAccess(isElectabled) {
        if (isEmpty(isElectabled)) {
            isElectabled = false;
        }
        if (isElectabled) {
            <#--只能对不参选的任务提出警告-->
            if (!isBeenElectabled(!isElectabled)) {
                alert("当前所选择任务（可能有部分）不是已参选。");
                return false;
            }
        } else {
            <#--只能对已参选的任务提出警告-->
            if (isBeenElectabled(!isElectabled)) {
                alert("只能对皆为未参选的任务进行操作。");
                return false;
            }
        }
        return true;
    }

    var form=document.taskListForm;
    function setSearchParams(){
	   var params = getInputParams(form,null,false);
	   params += getInputParams(parent.document.taskForm,null,false);
	   addInput(form,"params",params);
    }
        
    function courseInfo(selectId, courseId) {
       if (isEmpty(courseId) || isMultiId(selectId) == true) {
           alert("请选择一条要操作的记录。");
           return;
       }
       form.action = "courseSearch.do?method=info";
       form[selectId].value = courseId;
       form.submit();
    }
	function query(pageNo,pageSize,orderBy){
        transferParams(parent.document.taskForm,form,null,false);
	    form.action="electScope.do?method=search";
	    goToPage(form,pageNo,pageSize,orderBy);
	}
    function pageGoWithSize(pageNo,pageSize){
        query(pageNo,pageSize,'${RequestParameters['orderBy']?default("null")}');
    }
    /**
     * 设置教学任务是否可选1 or 0
     */
    function setElectInfo(isElectable){
        <#--true：对已参选的任务进行管制-->
        if (isElectable=="0" && isAccess(true) || isElectable=="1") {
            form.action = "electScope.do?isElectable="+isElectable;
            var taskIds = getSelectIds("taskId");
            addInput(form,"taskIds","");
            if(""==taskIds){
              form.action+="&updateSelected=0";
              if(isElectable=="1"){
                 if(!confirm("没有选择一个或多个教学任务，系统将操作查询条件内的所有任务。\n点击[确定]继续")) {
                    return;
                 }
                 setSearchParams();
              }else{
                 transferParams(parent.document.taskForm,form);
              }
            }else{
               addInput(form,"taskIds",taskIds);
               form.action+="&updateSelected=1";
            }
            if(isElectable=="1") {
               form.action+="&method=electSetting";
            } else {
               form.action+="&method=batchUpdateEelectInfo";
               if(!confirm("确定要取消这些课程的参选信息,确认请点击[确定]，否则点击[取消]")) {
                    return;
               }
               if(!confirm("是否删除选课范围,确认请点击[确定]，否则点击[取消]")){
                    form.action+="&setting.removeExistedScope=0";
               }else{
                    form.action+="&setting.removeExistedScope=1";
               }
            }
            form.submit();
        }
    }
    function edit(){
        <#--false：只能对已参选的任务操作-->
        if (isAccess(true)) {
            var taskId = getSelectIds("taskId");
            if(isMultiId(taskId)){if(!confirm("<@bean.message key="prompt.electable.setMulti"/>"))return;}
            if(taskId==""){alert("<@bean.message key="common.selectPlease"/>");return;}
            setSearchParams();
            form.action="electScope.do?method=edit&task.id=" +taskId;
            form.submit();
        }
    }
    function setCancelable(isCancelable){
        <#--false：只能对未参选的任务操作-->
        if (isAccess(false)) {
            form.action="electScope.do?method=setCancelable&updateSelected=1&isCancelable="+(isCancelable?"1":"0");
            submitId(form,"taskId",true);
        }
    }
    function info(taskIdValue){
        <#--false：只能对已参选的任务操作-->
        if (isAccess(true)) {
            form.action="electScope.do?method=info";
            if (isNotEmpty(taskIdValue)) {
                if (isMultiId(taskIdValue)) {
                    alert("链接错误。");
                    return;
                }
                addInput(form, "taskId", taskIdValue, "hidden");
                form.submit();
            } else {
                submitId(form,"taskId",false);
            }
        }
    }
    function editTeachTask(){
	    form.action="electScope.do?method=editTask";
	    setSearchParams();
	    submitId(form,"taskId",false);
    }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 