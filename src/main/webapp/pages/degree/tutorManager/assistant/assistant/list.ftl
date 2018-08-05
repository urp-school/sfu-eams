<#include "/templates/head.ftl"/>
<body >
    <table id="assistantBar"></table>
     <@table.table width="100%" id="listTable">
	   <@table.thead>
	     <@table.selectAllTd id="assistantId"/>
	     <td width="10%"><@bean.message key="attr.stdNo"/></td>
	     <td width="10%"><@bean.message key="attr.personName"/></td>
	     <td width="10%">开始时间</td>
	     <td width="10%">结束时间</td>
	     <td width="25%">所做工作</td>
	     <td width="5%">学时</td>
	     <td width="15%">导师</td>
	     <td width="10%">导师确认</td>	     
	   </@>
	   <@table.tbody datas=assistants;assistant>
	     <@table.selectTd id="assistantId" value="${assistant.id}"/>
	    <td>&nbsp;${assistant.std.code}</td>
	    <td>
	     <a href="studentDetailByManager.do?method=detail&stdId=${assistant.std.id}">
	      &nbsp;<@i18nName assistant.std?if_exists/>
	     </a>
	    </td>
	    <td>&nbsp;${assistant.startTime}</td>
	    <td>&nbsp;${assistant.endTime}</td>
	    <td>${assistant.job?if_exists}</td>
	    <td>${assistant.period?if_exists}</td>
	    <td>${(assistant.tutor.name)?if_exists}</td>
	    <td>${assistant.isConfirm?if_exists?string('是','否')}</td>
	   </@>
	   </@>
</body>  
<@htm.actionForm name="actionForm" entity="assistant" action="assistant.do"/>   
    <script> 
    var bar = new ToolBar("assistantBar","助教管理",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.add"/>",'add()');  
    bar.addItem("<@msg.message key="action.edit"/>",'edit()');  
    bar.addItem("<@msg.message key="action.delete"/>",'remove()');
    </script>
<#include "/templates/foot.ftl"/>