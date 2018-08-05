<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="electionTaskBar">
     <#assign electable = RequestParameters['task.electInfo.isElectable']>
</table>
<#include "../electableTaskList.ftl"/>
  <script>
   var bar = new ToolBar('electionTaskBar','<#if electable=="0"><@bean.message key="info.task.unelectableList"/><#else><@bean.message key="info.task.electableList"/></#if>',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if electable == "0">
   bar.addItem("修改","editTeachTask()",'update.gif');
   var menu =bar.addMenu("设置退课选项");
   menu.addItem("允许退课","setCancelable(true)");
   menu.addItem("不允许退课","setCancelable(false)");
   bar.addItem("<@bean.message key="action.setElectable"/>","setElectInfo(1)",'update.gif');
   <#else>
   bar.addItem("修改","editTeachTask()",'update.gif');
   bar.addItem("查看选课范围","info()");
   bar.addItem("统一设置范围","setElectInfo(1)",'update.gif','按照规则给每个任务设置选课范围,人数,是否允许退课，即多个任务一个设置。');
   bar.addItem("各项设置范围","edit()",'update.gif',"允许对所选任务进行多项设置，多个任务以所选的第一个任务为选课范围而设置。");
   bar.addItem("<@bean.message key="action.cancelElectable"/>","setElectInfo(0)",'delete.gif');
   </#if>
   
    var form=document.taskListForm;
    function setSearchParams(){
	   var params = getInputParams(form,null,false);
	   params += getInputParams(parent.document.taskForm,null,false);
	   addInput(form,"params",params);
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
	function query(pageNo,pageSize,orderBy){
        transferParams(parent.document.taskForm,form,null,false);
	    form.action="electScope.do?method=taskList";
	    goToPage(form,pageNo,pageSize,orderBy);
	}
    function pageGoWithSize(pageNo,pageSize){
        query(pageNo,pageSize,'${RequestParameters['orderBy']?default("null")}');
    }
    /**
     * 设置教学任务是否可选1 or 0
     */
    function setElectInfo(isElectable){
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
        if(isElectable=="1")
           form.action+="&method=electSetting";
        else{
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
    function edit(){
    	var taskId = getSelectIds("taskId");
    	if(isMultiId(taskId)){if(!confirm("<@bean.message key="prompt.electable.setMulti"/>"))return;}
    	if(taskId==""){alert("<@bean.message key="common.selectPlease"/>");return;}
    	setSearchParams();
    	form.action="electScope.do?method=edit&task.id=" +taskId;
    	form.submit();
    }
    function setCancelable(isCancelable){
    	form.action="electScope.do?method=setCancelable&updateSelected=1&task.electInfo.isElectable=${electable}&isCancelable="+(isCancelable?"1":"0");
    	submitId(form,"taskId",true);
    }
    function info(){
    form.action="electScope.do?method=info";
    submitId(form,"taskId",false);
    }
    function editTeachTask(){
	    form.action="electScope.do?method=editTask";
	    setSearchParams();
	    submitId(form,"taskId",false);
    }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 