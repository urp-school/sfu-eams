<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="bar" width="100%"></table>
     <@table.table width="100%" id="listTable" sortable="true">
       <@table.thead>
         <@table.selectAllTd id="textEvaluationId"/>
	     <@table.sortTd  width="14%" name="field.select.courseName" id="textEvaluation.task.course.name"/>
	     <@table.sortTd  width="10%" name="textEvaluation.object" id="teacher.name"/>
	     <@table.sortTd  width="8%" text="课程评教" id="textEvaluation.isForCourse"/>	
	     <@table.td width="35%" name="textEvaluation.ideaContext"/>
	   </@>
	   <@table.tbody datas=textEvaluations;textEvaluation>
	    <@table.selectTd id="textEvaluationId" value="${textEvaluation.id}" type="checkbox"/>
	    <td><@i18nName textEvaluation.task.course?if_exists/></td>
	    <td><#if textEvaluation.isForCourse?exists&&textEvaluation.isForCourse==true><#else>${textEvaluation.teacher.name}</#if></td>
	    <td>${textEvaluation.isForCourse?default(false)?string("是","否")}</td>
	    <td>${textEvaluation.context?if_exists?html}</td>
	   </@>
	  </@>
	  <@htm.actionForm name="actionForm" action="textEvaluation.do" entity="textEvaluation">
	     <input type="hidden" name="params" value="<#list RequestParameters?keys as key>&${key}=${RequestParameters[key]}</#list>"/>
	  </@>
  <script language="javascript">
       var form=document.actionForm;
		function exportSetting(){
			resetParameters()
	        var textEvaluationId = getSelectIds("textEvaluationId");
	        var confirmCaption = "是否导出查询出的所有任务?";
	        if (null != textEvaluationId && "" != textEvaluationId) {
	            confirmCaption = "是否导出下面选中的" + textEvaluationId.split(",").length + "条教学任务。 ";
	            addInput(form, "textEvaluationId", textEvaluationId, "hidden");
	        }
	        if(confirm(confirmCaption)){
	            form.action="?method=export"+queryStr;
				addInput(form,"keys","task.seqNo,task.course.name,teacher.name,isForCourse,context,calendar.year,calendar.term,");
				addInput(form,"titles","课程序号,课程名称,意见对象,是否课程评教,意见内容,学年度,学期,是否确认");
                form.submit();			
			}
		}
		
		function resetParameters() {
	        if (null != form["textEvaluationId"]) {
	            form["textEvaluationId"].value = "";
	        }
        }
        
        function setSearchParams(){
          document.actionForm.params.value = queryStr;
          form.target = "_self";
        } 
	   var bar = new ToolBar('bar','<@bean.message key="textEvaluation.idea"/>（<@bean.message key="textEvaluation.affirmTrue"/>）',null,true,true);
	   bar.setMessage('<@getMessage/>');

	   bar.addItem("<@msg.message key="action.export"/>","exportSetting();exportList()");
	   bar.addPrint("<@msg.message key="action.print"/>");
 </script>
 </body>
<#include "/templates/foot.ftl"/>