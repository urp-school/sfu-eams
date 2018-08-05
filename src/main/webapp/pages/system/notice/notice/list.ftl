<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="pages/system/notice/notice/notice.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','公告列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.info"/>",'info()','info.gif');
   bar.addItem('<@msg.message key="action.new"/>','edit(true,"${RequestParameters['kind']}")','new.gif');
   bar.addItem("<@msg.message key="action.edit"/>",'edit(false,"${RequestParameters['kind']}")','update.gif');
   bar.addItem("<@msg.message key="action.delete"/>",'remove("${RequestParameters['kind']}")','delete.gif');
</script>
   <@table.table width="100%" sortable="true" id="listTable" style="word-break: break-all">
     <@table.thead>
	    <@table.selectAllTd id="noticeId" />
	    <@table.sortTd width="45%" id="notice.title" text="公告标题"/>
	    <@table.sortTd text="发布人"  id="notice.publisher.name"/>
	    <@table.sortTd text="发布时间" id="notice.modifyAt"/>
	    <@table.sortTd  text="是否置顶" id="document.isUp"/>
	   </@>
	   <@table.tbody datas=notices;notice>
	     <@table.selectTd id="noticeId" value=notice.id/>
	     <td>${notice.title}</td>
	     <td>${notice.publisher.name}</td>
	     <td>${notice.modifyAt?string("yyyy-MM-dd")}</td>
	     <td>${notice.isUp?if_exists?string("是","否")}</td>	     
	   </@>
	  </@>
     <form name="msgListForm" method="post" action="" onsubmit="return false;"></form>
 </body>
<#include "/templates/foot.ftl"/>