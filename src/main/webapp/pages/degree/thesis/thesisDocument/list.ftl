<#include "/templates/head.ftl"/>
<body>
<table id="backBar" width="100%"></table>
   <@table.table width="100%">
     <@table.thead>
       <@table.selectAllTd id="documentId"/>
	   <@table.td  text="文档标题"/>
	   <@table.td  text="上传人"/>
	   <@table.td  text="上传时间"/>
	 </@>
	 <@table.tbody datas=thesisDocuments;thesisDocument>
	   <@table.selectTd id="documentId" value="${thesisDocument.id}" type="checkbox"/>
	    <td><A href="thesisDocument.do?method=doDownLoad&document.id=${thesisDocument.id}">${thesisDocument.name}</A></td>
	    <td>${thesisDocument.uploaded.name}</td>
	    <td>${thesisDocument.uploadOn}</td>
	   </@>
	  </@>
	  </body>
      <@htm.actionForm name="docListForm"  entity="document" action="thesisDocument.do" onsubmit="return false;"/>
<script>
   var bar = new ToolBar('backBar','文档列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("查看关联环节",'tacheSetting()','action.gif');
   bar.addItem("覆盖",'reLoad()','action.gif');
   //bar.addItem("下载",'info()','download.gif');
   bar.addItem("上传",'upload()','action.gif');
   bar.addItem("<@msg.message key="action.delete"/>",'remove()','delete.gif');

	var form=document.docListForm;
	orderBy =function(what){
		parent.search(1,${pageSize},what);
	}
	function pageGoWithSize(pageNo,pageSize){
       parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
    }
    function upload(){
    	form.action="thesisDocument.do?method=loadUploadPage";
    	form.submit();
    }
    function remove(){
    	form.action="thesisDocument.do?method=remove";
    	submitId(form,"documentId",true,null,"你确定要删除这些上传文件吗?如果被引用就会删除失败");
    }
    function tacheSetting(){
       singleAction("tacheSetting");
    }
    function reLoad(){
        if(confirm("你确定要覆盖这个文件吗?\n覆盖以后以前引用这个文件\n都会被新文件给覆盖")){
    		form.action="thesisDocument.do?method=loadUploadPage";
    		submitId(form,"documentId",false);
    	}
    }
</script>
<#include "/templates/foot.ftl"/>