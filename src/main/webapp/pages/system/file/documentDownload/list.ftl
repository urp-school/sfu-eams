<#include "/templates/head.ftl"/>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','文档列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("下载","download()",'download.gif');
   bar.addBack();
   function download(){
      var ids= getSelectIds("documentId");
      if(""==ids||isMultiId(ids)){alert("请选择一个");return;}
      self.location="download.do?method=download&document.id="+ids;
   }
</script>
   <@table.table width="100%" sortable="true" id="docTable">
     <@table.thead>
       <@table.selectAllTd id="documentId"/>
	   <@table.sortTd  id="document.name" text="文档标题"/>
	   <@table.sortTd  id="document.uploaded.name" text="上传人"/>
	   <@table.sortTd  id="document.uploadOn" text="上传时间"/>
	 </@>
	 <@table.tbody datas=documents;document>
	   <@table.selectTd id="documentId" value="${document.id}" type="checkbox"/>
	    <td><A href="download.do?method=download&document.id=${document.id}">${document.name}</A></td>
	    <td>${document.uploaded.name}</td>
	    <td>${document.uploadOn}</td>
	 </@>
  </@>
 </body>
 <form name="docListForm" method="post" action="" onsubmit="return false;"></form>
<#include "/templates/foot.ftl"/>