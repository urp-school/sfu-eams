<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self)">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','课程问卷列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   var evaluateMenu = bar.addMenu('设置评教方式',null,'detail.gif');
   evaluateMenu.addItem("课程评教","evaluate('course')","update.gif","课程评教");
   evaluateMenu.addItem("教师评教","evaluate('teacher')","update.gif","教师评教");
   var questionnaireMenu = bar.addMenu('指定问卷',null,'detail.gif');
   questionnaireMenu.addItem("全部更新","updateIds('all')","update.gif","allUpdate");
   questionnaireMenu.addItem("选择更新","updateIds('select')","update.gif","selectUpdate");
</script>
<table width="100%">
<form name="conditionForm" method="post" action="" onsubmit="return false;">
<tr>
	<td  align="left">
			<select id="questionnaieId" name="questionnaireId" style="width:300px;">
				<option value="">....</option>
				<#list questionnaireList?if_exists as questionnaire>
				   <option value="${questionnaire.id}">${questionnaire.description?if_exists}&nbsp;&nbsp;${questionnaire.depart.name?if_exists}</option>
				</#list>
			</select>
			<font color="red">前面是问卷描述,后面是创建部门</font>
		</td>
	</tr>
	</form>
</table>
<@table.table   width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="teachTaskId"/>
      <@table.sortTd  name="attr.taskNo" id="task.seqNo"/>
      <@table.sortTd  name="attr.courseName" id="task.course.name"/>
      <@table.sortTd  name="entity.courseType"  id="task.courseType.name"/>
      <@table.td     text="使用问卷描述"/>
      <@table.sortTd  text="评教方式"  id="task.requirement.evaluateByTeacher"/>
      <@table.sortTd  text="开课院系"  id="task.arrangeInfo.teachDepart.name"/>
      <@table.sortTd  text="学年度"  id="task.calendar.year"/>
      <@table.sortTd  text="学期"  id="task.calendar.term"/>
    </@>
    <@table.tbody datas=tasks; task>
    	<@table.selectTd id="teachTaskId" value=task.id/>
    	<td>${task.seqNo?if_exists}</td>
	   	<td>${task.course.name?if_exists}</td>
	   	<td>${task.courseType.name?if_exists}</td>
	   	<td><#if task.questionnaire?exists>${task.questionnaire.description?if_exists}&nbsp;&nbsp; ${task.questionnaire.depart.name?if_exists}<#else><font color="red">无问卷</font></#if></td>
	   	<td><#if task.requirement.evaluateByTeacher?exists&&task.requirement.evaluateByTeacher==true>教师评教<#else>课程评教</#if></td>
	   	<td>${task.arrangeInfo.teachDepart.name?if_exists}</td>
	   	<td>${(task.calendar.year)?if_exists}</td>
	   	<td>${(task.calendar.term)?if_exists}</td>
    </@>
 </@>
 <script>
 	var form = document.conditionForm;
 	orderBy =function(what){
		parent.search(1,${pageSize},what);
	}
	function pageGoWithSize(pageNo,pageSize){
       parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
    }
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("teachTaskId")));
    }
    function updateIds(item){
    	ids = getIds();
    	var questionnaireId = document.getElementById("questionnaieId").value;
    	addInput(form,"isType","questionnaire");
    	addInput(form,"questionnaireId",questionnaireId);
    	if(item=="all"){
    	   if(!confirm("你确定要设置所有你负责的吗?")){
    	      return;
    	   }
    	}else{
    		if(ids==""||ids.length<1){
    			alert("请选择一些教学任务然后点击提交");
    			return;
    		}
    		addInput(form,"taskSeq",ids)
    	}
    	addInput(form,"isAll",item)
    	var textValue=DWRUtil.getText("questionnaieId");
    	var alertText="你要把选择的课程使用"+textValue+"问卷吗?";
    	if(""==questionnaireId){
    		alertText="你要把选择的课程的问卷信息清空吗?";
    	}
    	if(confirm(alertText)){
    		form.action="questionnaireTask.do?method=courseTaskModify";
    		setSearchParams(parent.form,form);
			 if("all"==item){
	 			transferParams(parent.form,form);
	 		}
    		form.submit();
    	}
    }
    //选择课程评教和教师评教的设置。
    function evaluate(type){
        var flag=false;
        var tempString="课程评教";
        if(type=="teacher"){
        	flag=true;
        	tempString="教师评教";
        }
    	ids = getIds();
    	
    	var url="selectAction.do?method=courseTaskModify";
    	addInput(form,"isType","evaluate");
    	addInput(form,"isEvaluate",flag);
    	if(ids==""||ids.length<1){
    		if(!confirm("你确定要把所有的条件下的教学任务都设置为"+tempString+"吗?")){
    			return;
    		}
    		addInput(form,"isAll","all");
    		transferParams(parent.form,form);
    	}else{
    		addInput(form,"isAll","select");
    		addInput(form,"taskSeq",ids);
    	}
    	setSearchParams(parent.form,form);
    	form.action="questionnaireTask.do?method=courseTaskModify";
        form.submit();
    }
 </script>
</Body>
<#include "/templates/foot.ftl"/>