<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/system/SystemMessage.js"></script>
<body>
	<table id="backBar"></table>
	<@table.table width="100%" sortable="true" id="listTable" headIndex="1">
		<form name="msgListForm" method="post" action="systemMessage.do?method=newly" onsubmit="return false;">
			<input type="hidden" name="method" value="newly"/>
			<tr onkeypress="DWRUtil.onReturn(event, query)">
				<td align="center"><img src="${static_base}/images/action/search.gif"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/></td>
				<td><input type="text" name="systemMessage.message.title" maxlength="50" value="${RequestParameters['systemMessage.message.title']?if_exists}" style="width:100%"/></td>
				<td><input type="text" name="systemMessage.message.sender.name" maxlength="32" value="${RequestParameters['systemMessage.message.sender.name']?if_exists}" style="width:100%"/></td>
				<td><input type="text" name="systemMessage.message.sender.userName" maxlength="50" value="${RequestParameters['systemMessage.message.sender.userName']?if_exists}" style="width:100%"/></td>
				<td><input type="text" name="systemMessage.message.activeOn" maxlength="10" value="${RequestParameters['systemMessage.message.activeOn']?if_exists}" style="width:100%" onFocus="calendar()"/></td>
			</tr>
	  	</form>
			<@table.thead>
				<@table.selectAllTd id="messageId"/>
				<@table.sortTd id="systemMessage.message.title" text="消息标题"/>
				<@table.sortTd id="systemMessage.message.sender.name" text="发送者" />
				<@table.sortTd id="systemMessage.message.sender.userName" text="发送者姓名" />
				<@table.sortTd id="systemMessage.message.activeOn" text="接收时间" />
			</@>
			<@table.tbody datas=systemMessages;systemMessage>
				<@table.selectTd id="messageId" value="${systemMessage.id}"/>
		    	<td width="20%"><A href="systemMessage.do?method=info&systemMessage.id=${systemMessage.id}">${systemMessage.message.title?if_exists}</A></td>
		    	<#if systemMessage.message.sender.isCategory(3)?default(false) && systemMessage.recipient.isCategory(1)?default(false)>
		    	<td width="15%">系统管理员</td>
		    	<td width="15%">系统管理员</td>
		    	<#else>
		    	<td width="15%">${systemMessage.message.sender?if_exists.name?if_exists}</td>
		    	<td width="15%">${systemMessage.message.sender?if_exists.userName?if_exists}</td>
		    	</#if>
		    	<td width="10%">${systemMessage.message.activeOn?string("yyyy-MM-dd")?if_exists}</td>
		  	</@>
    </@>
    <br><br><br><br><br><br><br><br><br><br>	
	<script>
		var bar = new ToolBar('backBar','新消息',null,true,true);
   		bar.setMessage('<@getMessage/>');
	   	bar.addItem("<@msg.message key="action.delete"/>",'remove("newly")','junkmail.gif');
	   	bar.addItem('<@msg.message key="action.info"/>',info,'detail.gif');
	   	
	    var form = document.msgListForm;
	   
	    function query(pageNo,pageSize){
	       	goToPage(form,pageNo,pageSize);
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>