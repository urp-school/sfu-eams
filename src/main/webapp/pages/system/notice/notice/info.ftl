<#include "/templates/head.ftl"/>
<body>
<table id="backBar"></table>
<script>
  	 var bar = new ToolBar('backBar','公告详情',null,true,true);
  	 bar.setMessage('<@getMessage/>');
  	 bar.addBack("<@msg.message key="action.back"/>");  	 
</script>
<table class="infoTable">
    <tr>
		<td class="title"	width="30%">公告标题</td>
		<td class="content" colspan="3">${notice.title}</td>
    </tr>
    <tr>
		<td class="title">公告内容</td>
		<td class="content" colspan="3">${(notice.content.content)?default("")}</td>
    </tr>

    <tr>
		<td class="title">发送者</td>
		<td colspan="3">${notice.publisher.name}</td>
    </tr>
    <tr>
		<td class="title" >发布时间</td>
		<td colspan="3">${notice.modifyAt}</td>
    </tr>
    <tr>
		<td class="title" >有效开始时间</td>
		<td ><#if notice.startDate?exists>${notice.startDate?string("yyyy-MM-dd hh:mm")}<#else>无</#if></td>
		<td class="title" >有效结束时间</td>
		<td ><#if notice.finishDate?exists>${notice.finishDate?string("yyyy-MM-dd hh:mm")}<#else>无</#if></td>
    </tr>
</table>
</body>
<#include "/templates/foot.ftl"/>