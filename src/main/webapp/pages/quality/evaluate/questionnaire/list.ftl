<#include "/templates/head.ftl"/>
 <body>
  <table id="bar" width="100%"></table>
   <@table.table width="100%" align="center" sortable="true" id="questionnaire">
	 <@table.thead>
	   <@table.selectAllTd id="questionnaireId"/>
	   <@table.sortTd id="questionnaire.description" text="问卷描述"/>
	   <@table.sortTd id="questionnaire.depart.name" text="制作部门"/>
	   <@table.sortTd id="questionnaire.state" name="field.questionnaire.estate"/>
	 </@>
	 <@table.tbody datas=questionnaires;questionnaire>
	 	<@table.selectTd id="questionnaireId" value=questionnaire.id/>
	    <td ><A href="#" onclick="detail(${questionnaire.id})">${questionnaire.description?if_exists}</A></td>
	    <td><@i18nName questionnaire.depart/></td>
	    <td>${questionnaire.state?string("有效","无效")}</td>
	 </@>
   </@>
 <@htm.actionForm name="actionForm" action="questionnaire.do" entity="questionnaire"/>
<script>
   var bar = new ToolBar('bar','评教问卷列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.add"/>","add()");
   bar.addItem("<@msg.message key="action.edit"/>","edit()");
   bar.addItem("<@msg.message key="action.delete"/>","remove()");
   bar.addItem("<@msg.message key="action.info"/>","detail()");
   function detail(questionnaireId){
   		form.action = "questionnaire.do?method=detail";
   		if (null == questionnaireId || "" == questionnaireId) {
   		   submitId(document.actionForm,"questionnaireId",false);
   		} else {
   		   addInput(form, "questionnaireId", questionnaireId, "hidden");
   		   form.submit();
   		}
   }
</script>
 </body>
<#include "/templates/foot.ftl"/>