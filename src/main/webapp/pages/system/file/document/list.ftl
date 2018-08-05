<#include "/templates/head.ftl"/>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','文档列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("查看",'info("${RequestParameters['kind']}")');
   //bar.addItem("下载",'info()','download.gif');
   bar.addItem("上传",'upload("${RequestParameters['kind']}")','action.gif');
   bar.addItem("<@msg.message key="action.delete"/>",'remove("${RequestParameters['kind']}")','delete.gif');
   function info(kind) {
   	addInput(docListForm, "kind", kind, "hidden");
    submitId(docListForm, "documentId", false, "document.do?method=info");
   }
   function upload(kind){
      self.location="document.do?method=uploadSetting&kind="+kind;
   }
   function remove(kind){
     var documentIds= getSelectIds("documentId");
     if(documentIds==""){alert("请选择一个或多个文档进行删除");return;}
     if(confirm("确认删除选定的文档")){
        docListForm.action="document.do?method=remove&documentIds="+documentIds+"&kind="+kind;
        docListForm.submit();
     }
   }
</script>
   <@table.table width="100%" sortable="true" id="listTable">
     <@table.thead>
       <@table.selectAllTd id="documentId"/>
	   <@table.sortTd  text="文档标题" id="document.name"/>
	   <@table.sortTd  text="上传人" id="document.uploaded.name"/>
	   <@table.sortTd  text="上传时间" id="document.uploadOn"/>
	   <@table.sortTd  text="是否置顶" id="document.isUp"/>
	 </@>
	 <@table.tbody datas=documents;document>
	   <@table.selectTd id="documentId" value="${document.id}" type="checkbox"/>
	    <td><A href="download.do?method=download&document.id=${document.id}">${document.name}</A></td>
	    <td>${document.uploaded.name}</td>
	    <td>${document.uploadOn}</td>
	    <td>${document.isUp?if_exists?string("是","否")}</td>
	   </@>
	  </@>
	  </body>
      <form name="docListForm" method="post" action="" onsubmit="return false;"></form>
<#include "/templates/foot.ftl"/>