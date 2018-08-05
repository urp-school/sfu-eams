<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="confirmBar"></table>
  <script>
     var bar = new ToolBar("confirmBar","<@bean.message key="entity.teachTask" /><@bean.message key="common.list" />",null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem("<@bean.message key="action.affirm"/>","taskConfirm('selected','1')");
     bar.addItem("<@bean.message key="action.confirmAll" />","taskConfirm('all','1')");
     bar.addItem("<@bean.message key="action.cancel"/> <@bean.message key="action.affirm" />","taskConfirm('selected','0')");
     bar.addItem("<@bean.message key="action.cancelAll"/>","taskConfirm('all','0')");
  </script>
<@table.table  width="100%" sortable="true" id="listTable" headIndex="1">
  <form name="taskListForm" method="post" action="" onsubmit="return false;">
    <input type="hidden" name="task.arrangeInfo.teachDepart.id" value="${RequestParameters['task.arrangeInfo.teachDepart.id']?if_exists}"/>
    <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']?if_exists}"/>
    <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']?if_exists}">
    <input type="hidden" name="method" value="search">
    <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery(event)">
      <td align="center" width="3%">
        <img src="${static_base}/images/action/search.gif" align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/>
      </td>
      <td><input style="width:100%" type="text" name="task.seqNo" maxlength="32" value="${RequestParameters['task.seqNo']?if_exists}" maxlength="20"/></td>
      <td><input style="width:100%" type="text" name="task.course.code" maxlength="32" value="${RequestParameters['task.course.code']?if_exists}" maxlength="20"/></td>
      <#if localName?index_of("en")==-1>
      <td><input style="width:100%" type="text" name="task.course.name" maxlength="50" value="${RequestParameters['task.course.name']?if_exists}" maxlength="30"/></td>
      <#else>
      <td><input style="width:100%" type="text" name="task.course.engName" maxlength="50" value="${RequestParameters['task.course.engName']?if_exists}" maxlength="30"/></td>
      </#if>
      <td><input style="width:100%" type="text" name="task.teachClass.name" maxlength="50" value="${RequestParameters['task.teachClass.name']?if_exists}" maxlength="30"/></td>
      <td><input style="width:100%" type="text" name="teacher.name" maxlength="20" value="${RequestParameters['teacher.name']?if_exists}" maxlength="30"/></td>
      <td><input style="width:100%" type="text" name="task.teachClass.planStdCount" maxlength="5" value="${RequestParameters['task.teachClass.planStdCount']?if_exists}" maxlength="8"/></td>      
      <td><input style="width:100%" type="text" name="task.course.credits" maxlength="3" value="${RequestParameters['task.course.credits']?if_exists}" maxlength="5"/></td>
      <td></td>    
    </tr>
    </form>
    <@table.thead>
      <@table.selectAllTd id="taskId"/>
      <@table.sortTd id="task.seqNo" width="8%" name="attr.taskNo"/>
      <@table.sortTd id="task.course.code" width="8%" name="attr.courseNo" />
      <@table.sortTd id="task.course.name" width="25%" name="attr.courseName"/>
      <@table.sortTd id="task.teachClass.name" width="25%" name="entity.teachClass"/>
      <@table.td width="10%" name="entity.teacher"/>
      <@table.sortTd id="task.teachClass.planStdCount" width="5%" text="计划人数"/>
      <@table.sortTd width="5%" id="task.course.credits" name="attr.credit"/>
      <@table.sortTd width="5%" id="task.isConfirm" text="确认"/>
    </@>
    <@table.tbody datas=tasks;task>
      <@table.selectTd id="taskId" value=task.id/>
      <td>${task.seqNo?if_exists}</td>
      <td>${task.course.code?if_exists}</td>      
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></a></td>      
      <td>${task.teachClass.name}</td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td>${task.teachClass.planStdCount}</td>
      <td>${task.course.credits}</td>
      <td><input type="hidden" name="${task.id}" value="<#if task.isConfirm == true>1 <#else>0</#if>"/>
       ${task.isConfirm?string("是","否")}
      </td>
    </@>
	</@>
	<script>	
    function enterQuery(event) {if (portableEvent(event).keyCode == 13)query();}
  	function query(){
        var form = document.taskListForm; 
        transferParams(parent.document.taskForm,form,null,false);
        form.action="teachTaskConfirm.do?method=search";	    
	    form.submit();
	}
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("taskId")));
    }
    function taskConfirm(scope,isConfirm){
       var form = document.taskListForm;
       form.action="teachTaskConfirm.do?method=confirm"
       if(scope=="selected"){
           var taskIds = getIds();
           if(taskIds==""){ alert("<@bean.message key="common.selectPlease" />"); return;}
           if(isConfirm=="1"){            
             if(!confirm("<@bean.message key="prompt.task.confirmSelected"/>")) return;
             form.action += "&taskIds=" + taskIds + "&isConfirm=1";
           }
           else if(isConfirm =="0"){
            if(!confirm("<@bean.message key="prompt.task.unconfirmSelected"/>")) return;
              form.action += "&taskIds=" + taskIds +"&isConfirm=0";
           }            
        }
       else if(scopse="all"){
          var taskElems = document.getElementsByName('taskId');
          if(taskElems.length==	0){ alert("<@bean.message key="info.task.noTaskInQuery"/>"); return;}
          if(isConfirm=="1"){
              if(!confirm("<@bean.message key="prompt.task.confirmAll"/>")) return;
              form.action += "&isConfirm=1";             
          }
          else if(isConfirm=="0"){
             if(!confirm("<@bean.message key="prompt.task.unconfirmAll"/>")) return;
             form.action += "&isConfirm=0";
          }
       }
       form.action += getInputParams(parent.document.taskForm,"task");
       addInput(form,"params",queryStr);
       form.submit();
    }
 </script>
</body> 
<#include "/templates/foot.ftl"/> 