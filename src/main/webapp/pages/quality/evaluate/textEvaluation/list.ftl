<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="bar" width="100%"></table>
    <#assign affirm><font color="blue">通过</font></#assign>
    <#assign unconfirmed><font color="red">不通过</font></#assign>
     <@table.table width="100%" id="listTable" sortable="true">
       <@table.thead>
         <@table.selectAllTd id="textEvaluationId"/>
	     <@table.sortTd  width="8%" name="attr.taskNo" id="textEvaluation.task.seqNo"/>
	     <@table.sortTd  width="14%" name="field.select.courseName" id="textEvaluation.task.course.name"/>
	     <@table.sortTd  width="10%" name="textEvaluation.object" id="teacher.name"/>
	     <@table.td width="35%" name="textEvaluation.ideaContext"  />
	     <@table.sortTd width="8%" name="textEvaluation.affirm" id="textEvaluation.isAffirm"/>
	   </@>
	   <@table.tbody datas=textEvaluations;textEvaluation>
	    <@table.selectTd id="textEvaluationId" value="${textEvaluation.id}" type="checkbox"/>
	    <td>${textEvaluation.task.seqNo?if_exists}</td>
	    <td><@i18nName textEvaluation.task.course?if_exists/></td>
	    <td><#if textEvaluation.isForCourse?exists&&textEvaluation.isForCourse==true><#else>${(textEvaluation.teacher.name)?if_exists}</#if></td>
	    <td>${textEvaluation.context?if_exists?html}</td>
	    <td>${(textEvaluation.isAffirm?string(affirm, unconfirmed))?default("未确认")}</td>
	   </@>
	  </@>
	  <@htm.actionForm name="actionForm" action="textEvaluation.do" entity="textEvaluation">
	     <input type="hidden" name="isAffirm" value="0"/>
	     <input type="hidden" name="params" value=""/>
	  </@>
  <script language="javascript">
		function exportSetting(){
			addInput(form,"keys","task.seqNo,task.course.name,teacher.name,isForCourse,context,calendar.year,calendar.term,isAffirm");
			addInput(form,"titles","课程序号,课程名称,意见对象,是否课程评教,意见内容,学年度,学期,是否确认");
		}
	   var bar = new ToolBar('bar','<@bean.message key="textEvaluation.idea"/>',null,true,true);
	   bar.setMessage('<@getMessage/>');

	   var menu = bar.addMenu("通过","toAffirm(1)", "update.gif");
	   menu.addItem("不通过","toAffirm(0)", "update.gif");
	   menu.addItem("未确认","toAffirm(null)", "update.gif");
	   bar.addItem("<@msg.message key="action.delete"/>","remove()");
	   bar.addItem("<@msg.message key="action.export"/>","exportSetting();exportList()");
	   bar.addPrint("<@msg.message key="action.print"/>");
	   
	   function toAffirm(state) {
	       form.action = "textEvaluation.do?method=updateAffirm";
	       form["isAffirm"].value = state;
	       form["params"].value = resultQueryStr;
	       submitId(form, "textEvaluationId", true);
	   }
 </script>
 </body>
<#include "/templates/foot.ftl"/>