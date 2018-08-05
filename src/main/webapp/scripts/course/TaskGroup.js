     var form = document.taskGroupForm;
     function edit(){
        form.action="taskGroup.do?method=edit&taskGroup.id="+taskGroupId;
        form.submit();
     }
     function deleteGroup(){
          var form = document.taskGroupForm;
          if(!confirm("确认删除?"))return;
          form.action="taskGroup.do?method=deleteGroup&taskGroup.id="+taskGroupId;
          parent.setRefreshGroupListTime(500,true);
          form.submit();
     }
     function updatePlanStdCount(){
        var form = document.taskGroupForm;
         form.action="taskGroup.do?method=divPlanCount&taskGroup.id="+taskGroupId;
         form.submit();
     }
     function updateSuggest(){
         tp1.setSelectedIndex(1);
         var taskId = getCheckBoxValue(document.getElementsByName("taskId"));
         if(taskId==""){alert("请选择一个");return;}
         if(taskId.indexOf(",")!=-1){alert("请选择单个任务设置建议");return;}
         window.open("arrangeSuggest.do?method=edit&task.id="+taskId,'','scrollbars=auto,width=720,height=480,left=200,top=200,status=no');      
     }
     function hookFun(){
      f_frameStyleResize(self);
     }
     function deleteTask(){
         tp1.setSelectedIndex(1);
         var taskIds = getCheckBoxValue(document.getElementsByName("taskId"));
         if(taskIds==""){alert("请选择一个");return;}
         if(!confirm("确认删除?"))return;
         var form = document.taskGroupForm;
         form.action="taskGroup.do?method=deleteTask&taskIds="+taskIds;

       if(confirm("去除选定任务的建议教室\n点击[确定]系统将会将选定的教学任务的建议教室添从该组内去除，否则点击[取消]"))
          form.action+="&option.removeSuggestRoom=1";
       if(confirm("去除选定任务的建议时间\n点击[确定]系统将会将选定的教学任务的建议时间从该组内去除，否则点击[取消]"))
          form.action+="&option.removeSuggestTime=1";
       if(countId(taskIds)==taskCount){
          if(!confirm("删除指定的教学任务后，由于组内没有任务，将删除对应的排课组。确定删除？"))return;
          parent.setRefreshGroupListTime(500,true);
       }
       form.submit();
     }
     function updateSuggest(){
         tp1.setSelectedIndex(1);
         var taskId = getCheckBoxValue(document.getElementsByName("taskId"));
         if(taskId==""){alert("请选择一个");return;}
         if(taskId.indexOf(",")!=-1){alert("请选择单个任务设置建议");return;}
         window.open("arrangeSuggest.do?method=edit&task.id="+taskId,'','scrollbars=auto,width=720,height=480,left=200,top=200,status=no');      
     }
     function removeAdminClass(){
         tp1.setSelectedIndex(2);
         var adminClassIds = getCheckBoxValue(document.getElementsByName("adminClassId"));
         if(adminClassIds==""){alert("请选择一个");return;}
         if(!confirm("确定删除?"))return;
         var removeEmptyTask = confirm("没有该班级后是否将没有行政班的任务删除？\n删除点击[确定],否则点击[取消]");
         
         var form = document.taskGroupForm;
         form.action="taskGroup.do?method=removeAdminClass&adminClassIds="
                     +adminClassIds
                     +"&removeEmptyTask="
                     +(removeEmptyTask?"1":"0");
         
         if(countId(adminClassIds)==adminClassCount&&removeEmptyTask){
             parent.setRefreshGroupListTime(500,true);
         }
         form.submit();
     }
     
     function addTasks(){
          var form = document.taskGroupForm;
          form.action="taskGroup.do?method=lonelyTaskList&calendar.studentType.id=";
          form.action+=parent.document.taskGroupForm['calendar.studentType.id'].value;
          form.action+="&task.calendar.id="+parent.document.taskGroupForm['calendar.id'].value;
          form.action+="&task.course.code="+courseCode;
          addInput(form,"task.courseType.name",courseTypeName);
          form.submit();
     }