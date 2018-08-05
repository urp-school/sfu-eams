<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="myBar" width="100%" ></table>
  <script>
     var requests=new Object();
  </script>
  <@table.table id="listTable" width="100%" sortable="true">
    <@table.thead>
      <@table.td text=""/>
      <@table.sortTd width="10%" name="attr.taskNo" id="taskAlterRequest.task.seqNo"/>
      <@table.sortTd width="20%" name="attr.courseName" id="taskAlterRequest.task.course.name" />
	  <@table.sortTd width="10%" name="entity.teachClass" id="taskAlterRequest.task.teachClass.name"/>
	  <@table.sortTd width="15%" name="attr.requisitionTime" id="taskAlterRequest.time"/>
	  <@table.td name="attr.requisitionCause"/>
	  <@table.sortTd width="10%" name="common.status" id="taskAlterRequest.availability"/>
	</@>
	<@table.tbody datas=taskAlterRequests;taskAlterRequest>
      <@table.selectTd id="taskAlterRequestId" type="radio" value=taskAlterRequest.id/>
	  <td>${taskAlterRequest.task.seqNo}</td>
	  <td><@i18nName taskAlterRequest.task.course/></td>
	  <td><#if taskAlterRequest.task.requirement.isGuaPai><@msg.message key="attr.GP"/><#else>${taskAlterRequest.task.teachClass.name?html}</#if></td>
	  <td>${taskAlterRequest.time?string("yyyy-MM-dd")}</td>
	  <td id="taskAlterRequest${taskAlterRequest.id}.requisition">${taskAlterRequest.requisition?if_exists}</td>
	  <td><#if taskAlterRequest.availability?default('Y') = "Y"><@bean.message key="common.unprocessed"/>
	      <#elseif taskAlterRequest.availability?default('Y') = "O">
	        <@bean.message key="common.approved"/>
	      <#else><@bean.message key="common.invalidation"/></#if>
	  </td>
	  <script>
	     requests['${taskAlterRequest.id}']=new Object();
	     requests['${taskAlterRequest.id}'].approved=${taskAlterRequest.approved?string};
	  </script>
	 </@>
   </@>
  <#include "alterRequestForm.ftl"/>
  <script language="javascript">
    var form=document.alterForm;
    var bar = new ToolBar("myBar","变更列表",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.edit"/>","edit()");
    bar.addItem("<@msg.message key="action.delete"/>","remove()");
    var form=document.alterForm;
    var action="teacherTaskAlterRequest.do";
    function canOperation(){
       var id=getSelectId("taskAlterRequestId");
       if(""==id){
          alert("请选择变更申请");return id;
       }else{
          if(requests[id].approved){alert("申请已经批准,不能进一步操作");return "";}
          return id;
       }
    }
    function edit(){
       var id=canOperation();
       if(""!=id){
          addInput(form,"taskAlterRequest.id",id);
          form['taskAlterRequest.requisition'].value=document.getElementById("taskAlterRequest"+id+".requisition").innerHTML;
          displayRequest();
       }
    }
    function remove(){
       var id=canOperation();
       if(""!=id){
          if(confirm("确定删除选择的申请?")){
             addInput(form,"taskAlterRequestId",id);
             form.action=action+"?method=remove";
             form.submit();
          }
       }
    }
   </script>
 </body>
<#include "/templates/foot.ftl"/>