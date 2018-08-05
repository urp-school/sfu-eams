<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  	<table id="myBar" width="100%"></table>
  	<@table.table id="listTable" sortable="true">
    	<@table.thead>
      		<@table.selectAllTd id="taskAlterRequestId"/>
      		<@table.sortTd width="10%" name="attr.taskNo" id="taskAlterRequest.task.seqNo"/>
      		<@table.sortTd width="20%" name="attr.courseName" id="taskAlterRequest.task.course.name" />
	  		<@table.sortTd width="10%" name="entity.teacher" id="taskAlterRequest.teacher.name"/>
	  		<@table.sortTd width="15%" name="attr.requisitionTime" id="taskAlterRequest.time"/>
	  		<@table.td name="attr.requisitionCause"/>
	  		<@table.sortTd width="10%" name="common.status" id="taskAlterRequest.availability"/>
		</@>
		<@table.tbody datas=taskAlterRequests;taskAlterRequest>
      		<@table.selectTd id="taskAlterRequestId" value=taskAlterRequest.id/>
	  		<td>${taskAlterRequest.task.seqNo}</td>
	  		<td><@i18nName taskAlterRequest.task.course/></td>
	  		<td>${taskAlterRequest.teacher.name}</td>
	  		<td>${taskAlterRequest.time?string("yyyy-MM-dd")}</td>
	  		<td>${taskAlterRequest.requisition?if_exists}</td>
	  		<td><#if taskAlterRequest.availability?default('Y') = "Y">
	  				<@bean.message key="common.unprocessed"/>
	      		<#elseif taskAlterRequest.availability?default('Y') = "O">
	        		<@bean.message key="common.approved"/>
	      		<#else><@bean.message key="common.invalidation"/></#if>
	  		</td>
	 	</@>
   	</@>
   	<@htm.actionForm name="alterForm" action="taskAlterRequest.do" entity="taskAlterRequest">
     	<input name="availability" type="hidden" value=""/>
   	</@>
  	<script language="javascript">
	    var form=document.alterForm;
	    
	    var bar = new ToolBar("myBar","变更列表",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    bar.addItem("批准","form.availability.value='O';multiAction('updateState')");
	    bar.addItem("不予批准","form.availability.value='N';multiAction('updateState')");
	    bar.addItem("<@msg.message key="action.delete"/>","remove()");
   	</script>
</body>
<#include "/templates/foot.ftl"/>