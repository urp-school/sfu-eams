<#include "/templates/head.ftl"/>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','新消息通知(${systemMessages?size})',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("不要再提醒","parent.removeMsgNotify()");
   bar.addItem("<@msg.message key="action.close"/>","parent.closeMsgNotify()");
   var newMessageSize=${systemMessages?size};
   <#if (systemMessages?size>0)>parent.openMsgNotify();
   <#else>parent.closeMsgNotify();</#if>   
</script>
   <@table.table width="100%" sortable="true" id="listTable">
     <@table.thead>
       <@table.selectAllTd id="messageId"/>
	   <@table.sortTd id="message.title" text="消息标题"/>
	   <@table.sortTd id="message.sender" text="发送者" />
	   <@table.sortTd id="message.activeOn" text="接收时间" />
	 </@>
	 <@table.tbody datas=systemMessages;systemMessage>
	     <@table.selectTd id="messageId" value="${systemMessage.id}"/>
	    <td><A target="_blank" href="systemMessage.do?method=info&systemMessage.id=${systemMessage.id}">${systemMessage.message.title?if_exists}</A></td>
	    <td>${systemMessage.message.sender?if_exists.name?if_exists}</td>
	    <td>${systemMessage.message.activeOn?string("yyyy-MM-dd")?if_exists}</td>
	  </@>
    </@>
 </body>
<#include "/templates/foot.ftl"/>