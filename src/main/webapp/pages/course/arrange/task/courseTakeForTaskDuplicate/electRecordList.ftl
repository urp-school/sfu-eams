<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<table id="electRecordStdBar" ></table>
<#include "filterPrompt.ftl"/>
 <@table.table width="100%">
    <@table.thead>
      <@table.td width="5%" name="attr.index"/>
      <@table.td width="10%" name="attr.stdNo"/>
      <@table.td width="10%" name="attr.personName"/>
      <@table.td width="10%" name="attr.electTurn"/>
      <@table.td width="20%" name="entity.electDateTime"/>
      <@table.td width="10%" name="entity.courseTakeType"/>
      <@table.td width="10%" name="attr.isPitchOn"/>
    </@>
    <@table.tbody datas=electRecords;record,record_index>
      <td>${record_index + 1}</td>
      <td>${record.student.code}</td>
      <td><@i18nName record.student/></td>
      <td>${record.turn}</td>
      <td>${record.electTime?string("yyyy-MM-dd HH:mm")}</td>
      <td><@i18nName record.courseTakeType/></td>
      <td><#if record.isPitchOn == true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
    </@>
 </@>
<form name="electRecordListForm"  method="post" action="courseTakeForTaskDuplicate.do?method=filter" onsubmit="return false;">
	<input type="hidden" name="taskIds" value="${task.id}"/>
</form>
 <script>
   var bar = new ToolBar('electRecordStdBar','<@i18nName task.course/>的选课数据（实践）',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.randomFilter"/>", "displayPrompt()", "list.gif");
   bar.addBack("<@bean.message key="action.back"/>");
   
   function filter(){
     setSearchParams(parent.document.taskForm, document.electRecordListForm, null);
     document.electRecordListForm.submit();
   }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 