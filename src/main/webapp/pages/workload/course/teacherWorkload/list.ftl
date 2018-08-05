<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 	<table id="teachWorkloadBar" width="100%"></table>
  	<@table.table id="listTable" width="100%" sortable="true">
  	  <@table.thead>
  		<@table.selectAllTd id="workloadId" />
  		<td>工作量类别</td>
  		<td>说明</td>
  		<@table.sortTd name="entity.studentType" id="workload.studentType.name"/>
  		<@table.sortTd name="workload.taskWorkload" id="workload.totleWorkload"/>
  		<@table.sortTd name="workload.teacherAffirm" id="workload.teacherAffirm"/>
  		<@table.sortTd name="workload.academicTerm" id="workload.teachCalendar.start"/>
  	  </@>
  	  <@table.tbody datas=workloads;workload>
   		<td align="center" bgcolor="#CBEAFF"><input type="checkbox" name="workloadId" value="${workload.id}"></td>
   		<td align="center"><#if workload.isTeachWorkload>教学工作量<#else>实习工作量</#if></td>
   		<td align="center"><#if workload.isTeachWorkload>${(workload.courseName)?if_exists}(${(workload.courseSeq)?if_exists})</#if></td>
   		<td align="center">${workload.studentType?if_exists.name?if_exists}</td>
   		<td align="center">${workload.totleWorkload?string("###0.0")}</td>
   		<td align="center">
   			<#if workload.teacherAffirm==true><@msg.message key="workload.true"/>
   			<#else><@msg.message key="workload.false"/>
   			</#if>
   		</td>
   		<td align="center">${workload.teachCalendar.year}&nbsp;${workload.teachCalendar.term}</td>
   		</@>
  	 </@>
 	<form name="listForm" method="post" action="" onsubmit="return false;"></form>
 	<script>
 	  var bar = new ToolBar("teachWorkloadBar","教师工作量",null,true,true);
 	  bar.setMessage('<@getMessage/>');
 	  bar.addItem("<@msg.message key="workload.lookDetailInfo"/>","info()");
 	  bar.addItem("批量确认","teacherAffirm()");
 	  
	 var form = document.listForm;
	 var action="teacherWorkload.do"
	 function info(){
	 	form.action=action+"?method=info";
	 	submitId(form,"workloadId",false);
	 }
	 function teacherAffirm(){
	 	form.action=action+"?method=affirm&estate=true";
	 	setSearchParams(parent.form,form);
	 	submitId(form,"workloadId",true,null,"你确定要确认选择的工作量吗?\n");
	 }
</script>
</body>
 <#include "/templates/foot.ftl"/>