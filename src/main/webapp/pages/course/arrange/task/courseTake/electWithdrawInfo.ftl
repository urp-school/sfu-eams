<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="electBar"></table>
<@table.table id="listTable" width="100%">
	  <@table.thead>
	   <@table.selectAllTd id="electRecordId"/>
	   <@table.td name="attr.taskNo" width="10%"/>
	   <@table.td name="attr.courseName" width="23%"/>
	   <@table.td name="entity.courseType" width="13%"/>
	   <@table.td name="attr.credit" width="5%"/>
	   <@table.td text="修读类别" width="10%"/>
	   <@table.td text="选课时间"/>
	   <@table.td text="轮次"/>
	   <@table.td text="选上"/>
	  </@>
	  <@table.tbody datas=electRecords;electRecord>
	    <@table.selectTd id="electRecordId" type="checkbox" value="${electRecord.id}"/>
	    <td>${electRecord.task.seqNo}</td>
	    <td><@i18nName electRecord.task.course/></td>
	    <td><@i18nName electRecord.task.courseType/></td>
	    <td>${electRecord.task.course.credits}</td>
	    <td><@i18nName electRecord.courseTakeType/></td>
	    <td>${electRecord.electTime?string("yyyy-MM-dd HH:mm")}</td>
	    <td>${electRecord.turn?default('')}</td>
	    <td>${electRecord.isPitchOn?string("是","否")}</td>
	  </@>
  </@>
 <table id="withdrawBar"></table>
   <@table.table id="listTable" width="100%">
	  <@table.thead>
	   <@table.selectAllTd id="electRecordId"/>
	   <@table.td  name="attr.taskNo" width="10%"/>
	   <@table.td  name="attr.courseName" width="23%"/>
	   <@table.td  name="entity.courseType" width="13%"/>
	   <@table.td  name="attr.credit" width="5%"/>
	   <@table.td text="修读类别" width="10%"/>
	   <@table.td text="退课时间"/>
	   <@table.td text="退课人"/>
	  </@>
	  <@table.tbody datas=withdraws;withdraw>
	    <@table.selectTd id="withdrawId" type="checkbox" value="${withdraw.id}"/>
	    <td>${withdraw.task.seqNo}</td>
	    <td><@i18nName withdraw.task.course/></td>
	    <td><@i18nName withdraw.task.courseType/></td>
	    <td>${withdraw.task.course.credits}</td>
	    <td><@i18nName withdraw.courseTakeType/></td>
	    <td>${withdraw.time?string("yyyy-MM-dd HH:mm")}</td>
	    <td>${withdraw.who?default("")}</td>
	  </@>
  </@>
  <script>
    var bar = new ToolBar("electBar","<@i18nName student/>的选课记录",null,true,true);
    bar.addItem("<@msg.message key="action.print"/>",'print()');
    bar.addBack("<@msg.message key="action.back"/>");
    var bar = new ToolBar("withdrawBar","<@i18nName student/>的退课记录",null,true,true);
    bar.addBack("<@msg.message key="action.back"/>");
  </script>
 </body>
<#include "/templates/foot.ftl"/>