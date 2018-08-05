<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="courseTakeForTaskBar"></table>
<#include "electableTaskList.ftl"/>
<#include "filterPrompt.ftl"/>
 <script>
   var bar = new ToolBar("courseTakeForTaskBar", "教学任务管理（实践）", null, true, true);
   bar.setMessage('<@getMessage/>');
   bar.addItem('修改人数', 'batchUpdateStdCountSetting()');
   var menu1=bar.addMenu("学生名单","submitId(document.taskListForm,'taskId',false,'courseTakeForTaskDuplicate.do?method=teachClassStdList')",'list.gif');
   menu1.addItem("班级人数分布","submitId(document.taskListForm,'taskId',true,'courseTakeForTaskDuplicate.do?method=adminClassStdCount')",'list.gif');
   menu1.addItem("选课数据","submitId(document.taskListForm,'taskId',false,'courseTakeForTaskDuplicate.do?method=electRecordList')",'list.gif');
   menu1.addItem("退课名单","submitId(document.taskListForm,'taskId',false,'courseTakeForTaskDuplicate.do?method=withdrawList')",'list.gif');
   menu1.addItem('导出学生名单','exportStdList(document.taskListForm)');
   bar.addItem("<@bean.message key="action.assignStd" />","assignStds();",'update.gif');
   var menu2 = bar.addMenu("添加任务", "addTeachTask()");
   menu2.addItem("修改任务", "editTeachTask()");
   menu2.addItem('<@msg.message key="action.batchEdit"/>', 'batchEdit()');
   menu2.addItem("删除任务", "removeTeachTask()");
   
    var form = document.taskListForm;
    
    function pageGoWithSize(pageNo,pageSize){
        transferParams(parent.document.taskForm,document.taskListForm,null,false);
        document.taskListForm.action="courseTakeForTaskDuplicate.do?method=taskList";
        goToPage(document.taskListForm,pageNo,pageSize);
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
    
    function assignStds(){
        form.action = "courseTakeForTaskDuplicate.do?method=assignStds";
        var taskIds = getSelectIds("taskId");
        if(""==taskIds){
          if(!confirm("没有选择一个或多个教学任务，系统将为查询条件内的所有任务指定学生。\n点击[确定]继续"))return;
           form.action+="&updateSelected=0";
           transferParams(parent.document.taskForm,form,null,false);
        }else{
           if(!confirm("要为选定的教学任务，按照班级指定学生?\n点击[确定]继续"))return;
           form.action+="&updateSelected=1&taskIds="+taskIds;
        }        
        if(confirm("对于已经指定的任务，是否删除学生名单是否重新指定?\n重新指定请点击[确定]，否则点击[取消]")) {
           form.action+="&deleteExisted=1";
        } else {
           form.action+="&deleteExisted=0";
        }
        form.submit();
   }
   function exportStdList(form){
         var Ids = getSelectIds("taskId");
         if(""==Ids){alert("请选择一个或多个教学任务");return;}
         form.action="teachTaskCollege.do?method=exportStdList";
         addInput(form,"teachTaskIds",Ids);
         addInput(form,"attrs","task.seqNo,task.course.code,task.course.name,std.code,std.name,std.enrollYear,std.department.name,firstMajor.name,firstAspect.name,courseTake.courseTakeType.name");
         addInput(form,"attrNames","<@msg.message key="attr.taskNo"/>,<@msg.message key="attr.courseNo"/>,<@msg.message key="attr.courseName"/>,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,入学年份,<@msg.message key="entity.department"/>,<@msg.message key="entity.speciality"/>,方向,修读类别");
         form.submit();
   }
   function filterTasks(){
      var taskIds = getSelectIds("taskId");
      if(""==taskIds){
        alert("请选择超过人数上线的教学任务,进行筛选!");return;
      }else{
         addInput(document.taskListForm,"taskIds",taskIds);
         displayPrompt();
      }
   }
   function setSearchParams(){
        var params = getInputParams(form,null,false);
        params += getInputParams(parent.document.taskForm,null,false);
        addInput(form,"params",params);
   }
   
    function batchUpdateStdCountSetting() {
        var ids = getSelectIds("taskId");
        if (ids == null || ids == "") {
            alert("请选择要修改人数的教学任务。");
            return;
        }
        form.action = "courseTakeForTaskDuplicate.do?method=batchUpdateStdCountSetting";
        setSearchParams();
        addInput(form, "taskIds", ids, "hidden");
        form.submit();
    }
    
    function addTeachTask(){
       form.action = "teachTaskCollege.do?method=selectCourse";
       addInput(form, "calendar.id", "${RequestParameters["calendar.id"]}", "hidden");
       setSearchParams();
       form.submit();
    }
    
    function editTeachTask(){
       var id = getSelectIds("taskId");
       if (isEmpty(id)) {
        alert("请选择一条要操作的记录。");
        return;
       }
       form.action = "teachTaskCollege.do?method=edit";
       addInput(form, "task.id", id, "hidden");
       setSearchParams();
       form.submit();
    }
    
    function removeTeachTask() {
       var ids = getSelectIds("taskId");
       if (isEmpty(ids)) {
        alert("<@bean.message key="prompt.task.selector"/>");
        return;
       }
       if (!confirm("已选定的 " + countId(ids) + " 个任务将被删除。\n<@bean.message key="prompt.task.delete"/>")) {
        return;
       }
       form.action = "teachTaskCollege.do?method=remove";
       addInput(form, "taskIds", ids, "hidden");
       addInput(form, "params", queryStr, "hidden");
       form.submit();
    }
    
    function batchEdit() {
      var ids = getSelectIds("taskId");
      if (isEmpty(ids)) {
        alert("<@bean.message key="prompt.task.selector"/>");
        return;
      }
      window.open("teachTaskCollege.do?method=batchEdit&taskIds=" + ids + "&orderBy=" + orderByStr);
    }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 