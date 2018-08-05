    //设置任务的建议时间及教室
    function suggestTime(form){
      var id =  getCheckBoxValue(document.getElementsByName("taskId"));
      if(id=="") {alert("请选择一个教学任务设置排课建议");return;}
      if(id.indexOf(",")>-1) {alert("请仅仅选择一个");return;}
      //if(checkConfirm(id)) {alert("<@bean.message key="error.task.modifyUnderConfirm"/>");return;}
      window.open("arrangeSuggest.do?method=edit&task.id="+id,'','scrollbars=auto,width=720,height=480,left=200,top=200,status=no');      
    }