    function removeArrangeResult(){
        var taskId = getCheckBoxValue(document.getElementsByName("taskId"));
        if(taskId=="") {alert("请选择教学任务");return;}
        if(confirm("删除教学任务的排课结果，信息不可恢复确认删除？")){
            document.taskListForm.action="ecuplManualArrange.do?method=removeArrangeResult&taskIds="+taskId;
            setSearchParams(document.taskListForm,document.taskListForm);
            document.taskListForm.submit();
	    }
    }
