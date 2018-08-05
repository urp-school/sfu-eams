<#include "/templates/head.ftl"/>
<body>
<table id="backBar"></table>
<script>
     function reply(msgId){
        self.location="systemMessage.do?method=reply&systemMessage.id="+msgId;
     }
  	 var bar = new ToolBar('backBar','消息详情',null,true,true);
  	 bar.setMessage('<@getMessage/>');
  	 bar.addItem("回复","reply(${systemMessage.id})","reply.gif");
  	 bar.addBack("<@msg.message key="action.back"/>");
</script>
<table class="infoTable">
    <tr>
		<td class="title"	width="30%">消息标题</td>
		<td class="content" >${systemMessage.message.title?if_exists}</td>
    </tr>
    <tr>
		<td class="title">消息内容</td>
		<td class="content" >${systemMessage.message.body?if_exists}</td>
    </tr>
    <tr>
		<td class="title">消息类别</td>
		<td class="content">${systemMessage.message.type?if_exists.name?if_exists}</td>
    </tr>
    <tr>
		<td class="title">发送者</td>
		<td >${systemMessage.message.sender.userName?if_exists}</td>
    </tr>
    <tr>
		<td class="title" >发送时间</td>
		<td >${systemMessage.message.createAt?if_exists}</td>
    </tr>
    <tr>
		<td class="title" >过期时间</td>
		<td >${systemMessage.message.invalidateOn?if_exists}</td>
    </tr>
</table>
</body>
<#include "/templates/foot.ftl"/>
	  