<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/system/SystemMessage.js"></script>
<body>
	<table id="backBar"></table>
	<script>
	   var bar = new ToolBar('backBar','已发送消息',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("<@msg.message key="action.delete"/>",'remove()');
	   bar.addItem('<@msg.message key="action.info"/>','info()','detail.gif');
	</script>
	<#assign status={"1":"未读","2":"已读",'3':'进入垃圾箱'}/>
	<@table.table width="100%" sortable="true" id="listTable" headIndex="1">
   		<form name="msgListForm" method="post" action="systemMessage.do?method=sendbox" onsubmit="return false;">
			<input type="hidden" name="method" value="sendbox">
			<tr  onkeypress="DWRUtil.onReturn(event, query)">
				<td align="center"><img src="${static_base}/images/action/search.gif"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/></td>
				<td><input type="text" name="systemMessage.message.title" maxlength="50" style="width:100%" value="${RequestParameters['systemMessage.message.title']?if_exists}"/></td>
				<td><input type="text" name="systemMessage.recipient.name" maxlength="32" style="width:100%" value="${RequestParameters['systemMessage.recipient.name']?if_exists}"/></td>
				<td><input type="text" name="systemMessage.recipient.userName" maxlength="50" style="width:100%" value="${RequestParameters['systemMessage.recipient.userName']?if_exists}"/></td>
				<td>&nbsp;</td>
				<td><input type="text" name="systemMessage.message.activeOn" maxlength="10" style="width:100%" value="${RequestParameters['systemMessage.message.activeOn']?if_exists}" onfocus="calendar()"/></td>
			</tr>
		</form>
			<@table.thead>
				<@table.selectAllTd id="systemMessageId"/>
				<@table.sortTd id="systemMessage.message.title" width="10%"  text="消息标题"/>
				<@table.sortTd width="15%" text="接收人帐号" id="systemMessage.recipient.name"/>
				<@table.sortTd width="15%" text="接收人姓名" id="systemMessage.recipient.userName"/>
				<@table.sortTd width="10%" text="状态" id="systemMessage.status"/>
				<@table.sortTd width="10%" text="接收时间" id="systemMessage.message.activeOn" />
			</@>
			<@table.tbody datas=systemMessages;systemMessage>
			    <@table.selectTd id="systemMessageId" value=systemMessage.id/>
			    <td><A href="systemMessage.do?method=info&systemMessage.id=${systemMessage.id}">${systemMessage.message.title?if_exists}</A></td>
			    <td>${systemMessage.recipient.name}</td>
			    <td>${systemMessage.recipient.userName}</td>
			    <td>${status[systemMessage.status?string]}</td>
			    <td>${systemMessage.message.activeOn?string("yyyy-MM-dd")?if_exists}</td>
			</@>
	</@>
    <@htm.actionForm name="actionForm" action="systemMessage.do" entity="systemMessage" onsubmit="return false;">
    	<input type="hidden" name="removeForever" value="1"/>
    	<input type="hidden" name="removeFrom" value="sendbox"/>
    </@>
    <script>
	    function query(){
	       	document.msgListForm.submit();
	    }
    </script>
</body>
<#include "/templates/foot.ftl"/>