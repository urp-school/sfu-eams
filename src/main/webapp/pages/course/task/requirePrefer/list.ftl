<#include "/templates/head.ftl"/>
<body  >   
<table id="teacherTaskBar"></table>

 <table width="100%" border="0" class="listTable" id="taskListTable">
   <form name="taskListForm" method="post" action="" onsubmit="return false;">
   <input type="hidden" name="calendar.id" value="${calendar.id}"/>
    <tr align="center" class="darkColumn">
      <td align="center" width="2%">
        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('taskId'),event);">
      </td>
      <td width="8%"><@bean.message key="attr.taskNo" /></td>
      <td width="8%"><@bean.message key="attr.courseNo"/></td>
      <td width="15%"><@bean.message key="attr.courseName"/></td>
      <td width="20%"><@bean.message key="entity.teachClass" /></td>
      <td width="20%">设置教材</td>
      <td width="15%"><@bean.message key="attr.roomConfigOfTask"/></td>
    </tr>
    <#list taskList as task>
   	  <#if task_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if task_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" 
     onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" 
     onclick="onRowChange(event)">
      <td class="select">
        <input type="checkBox" name="taskId" value="${task.id}">
      </td>
      <td>&nbsp;${task.seqNo}</td>
      <td>&nbsp;${task.course.code}</td>
      <td>
      <A href="teachTaskSearch.do?method=info&task.id=${task.id}" >
      <@i18nName task.course?if_exists/></a>
      </td>
      <td><#if task.requirement.isGuaPai><@msg.message key="attr.GP"/><#else>${task.teachClass.name?html}</#if></td>
      <td><A href="#" onclick="editTextbook(${task.id})"><#if task.requirement.textbooks?size==0>添加教材<#else><@getBeanListNames task.requirement.textbooks?if_exists/></#if></A></td>
      <td>&nbsp;<@i18nName task.requirement.roomConfigType/></td>
      <input type="hidden" id="${task.id}" value="<#if task.isConfirm == true>${task.seqNo}<#else>0</#if>"/>
    </tr>
	</#list>
    </form>	
	</table>
	<script>
	function checkConfirm(id){
	    var elem = document.getElementById(id);
	    return elem.value!=0;
	}
    function editRequire(){
        var ids = getCheckBoxValue(document.getElementsByName("taskId"));
        if(ids==""){alert("<@bean.message key="common.selectPlease" />");return;}
        if(ids.indexOf(",")!=-1){alert("<@bean.message key="common.singleSelectPlease"/>");return;}
        if(checkConfirm(ids)) {alert("教学任务已经确认,暂时不能修改");return;}
        var form = document.taskListForm;
        form.action="requirePrefer.do?method=edit&requirementType=inTask&task.id="+ids;
        form.submit();
    }
    function editTextbook(taskId){
        if(checkConfirm(taskId)) {alert("教学任务已经确认,暂时不能修改");return;}
        var form = document.taskListForm;
        addInput(form,"task.id",taskId,"hidden");
        form.action="requirePrefer.do?method=editTextbook&forward=taskList&requirementType=inTask";
        form.submit();
    }
    function setPreference(){
        var idSeq = getCheckBoxValue(document.getElementsByName("taskId"));
        if(idSeq==""){alert("<@bean.message key="common.selectPlease" />");return;}
        var ids = idSeq.split(",");
        var errors="";
        for(var i=0; i<ids.length;i++){
            if(checkConfirm(ids[i])){
                errors+=document.getElementById(ids[i]).value+",";
            }
        }
        if(errors!=""){
            alert(<@bean.message key="error.teachTask.isConfirmedOf" arg0="errors"/>);
            return;
        }
        var form = document.taskListForm;
        form.action="requirePrefer.do?method=setPreferForTask&taskIds="+idSeq;
        form.submit();
    }
   var bar = new ToolBar('teacherTaskBar','<@bean.message key="entity.teachTask" /> <@bean.message key="common.list" />',null,true,true);
   bar.setMessage('<@getMessage/>');
   //bar.addItem("<@bean.message key="action.setByPreference"/>","javascript:setPreference()",'update.gif');
   bar.addItem("<@bean.message key="action.modify"/>","javascript:editRequire()",'update.gif');    
    </script>
</body>    
<#include "/templates/foot.ftl"/>    
