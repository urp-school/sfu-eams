<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script language="javascript">
 	var detailArray=new Array();
 	var task = new Array();
	<#list taskList as task>
		task[${task_index}]=new Array();
		detailArray[${task_index}]=new Array();
	    detailArray[${task_index}][0]='${task.id}';;
		<#list task.arrangeInfo.teachers?if_exists as teacher>
			task[${task_index}][${teacher_index}]=new Array();
			task[${task_index}][${teacher_index}][0]='${teacher.id}';
			task[${task_index}][${teacher_index}][1]='${teacher.name?if_exists}';
		</#list>
	</#list>
	function setTeacherIdAndName(taskId){
	   var arrayList=null;
       for(var i=0;i<detailArray.length;i++){
			if(detailArray[i][0]==taskId){
				arrayList=task[i];
				break;
			}
		}
		var selectObject = document.getElementById("teacherSelect");
		for(var i=selectObject.length-1;i>=0;i--){
			selectObject.remove(i);
		}
		if(arrayList.length>0){
			for(var i=0;i<arrayList.length;i++){
			    DWRUtil.addOptions("teacherSelect",[{'id':arrayList[i][0],'name':arrayList[i][1]}],'id','name');
		    }
		    DWRUtil.addOptions("teacherSelect",[{'id':"",'name':"请选择教师.."}],'id','name');
		}else{
			selectObject.add(new Option("请先选择课程",""));
		}
	}
    function save(form){
    var a_fields={
     		'textEvaluation.task.id':{'l':'<@bean.message key="field.studentEvaluate.selectCourse"/>', 'r':true, 't':'f_courseName'},
     		'textEvaluation.context':{'l':'<@bean.message key="textEvaluation.opinionContext"/>', 'r':true, 't':'f_opinion','mx':200}
     	}
     var cotextValue= document.getElementById("context");
     if(/^\s*$/.test(cotextValue)){
     	 alert("<@bean.message key="textEvaluation.opinionContext"/>"+"不能为空,或者空格");
     	 return;
     }
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.action="textEvaluationStd.do?method=save";
        form.submit();
     }
   }
</script>
 <body>
 <table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','<@bean.message key="textEvaluation.opinion"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>");
</script>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
  <form name="commonForm" method="post" action="" onsubmit="return false;">
    	<table width="100%" align="center" class="formTable">
          <tr align="center" class="darkColumn">
    		<td colspan="2" align="center"><@msg.message key="evaluate.textEvalutation"/></td>
   		  </tr>		
  	     <tr>
	     <td class="title" width="20%" id="f_courseName">&nbsp;<@bean.message key="field.studentEvaluate.selectCourse"/><font color="red">*</font>:</td>
	     <td >
	     	<input type="hidden" name="textEvaluation.calendar.id">
			<select id="taskSelectId" name="textEvaluation.task.id" style="width:300px;" onchange="setTeacherIdAndName(this.value)">
				<#if  (taskList?size!=0)>
					<option value=""><@msg.message key="attr.selectCourse"/></option>
					<#list taskList as task>
						<option value="${task.id}"><@i18nName (task.course)?if_exists/></option>
					</#list>
				<#else>
					<option value=""><@msg.message key="info.noFoundCurriculums"/></option>
				</#if>
			</select>
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_teacher">&nbsp;<@bean.message key="field.studentEvaluate.selectTeacher"/><font color="red">*</font>:</td>
	     <td >
			<input type="hidden" name="textEvaluation.teacherName">
			<select id="teacherSelect" name="textEvaluation.teacher.id" style="width:300px;">
				<option value="">..</option>
			</select>
			<@msg.message key="info.evaluation.unchooseTip"/>
         </td>
	   </tr> 
	   <tr>
	     <td class="title" id="f_opinion">&nbsp;<@bean.message key="textEvaluation.opinionContext"/><font color="red">*</font>:</td>
	     <td ><textarea name="textEvaluation.context" cols="50" rows="5" style="width:300px;"></textarea></td>
	   </tr>
	   <tr class="darkColumn">
	     <td colspan="2" align="center" >
	       <button onClick="save(this.form)"><@bean.message key="system.button.add"/></button>&nbsp;
	     </td>
	   </tr>
     </table>
     </div>
    </td>
   </tr>
   </form>
  </table>
</body>
<#include "/templates/foot.ftl"/>
 