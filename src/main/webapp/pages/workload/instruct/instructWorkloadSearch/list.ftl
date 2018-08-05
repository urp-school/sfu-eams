<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','工作量列表信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   var manageMenu = bar.addMenu('管理工具',null);
   manageMenu.addItem("查看详细信息","detailObject()");
   manageMenu.addItem("导出","exportObject()");
</script>
<#include "../instructWorkload/instructWorkloadList.ftl"/>	
<script language="javascript">
	var form = document.selectForm;
	var action="instructWorkloadSearch.do";
	function detailObject(){
		form.action=action+"?method=loadDetail";
		submitId(form,"instructWorkloadId",false);
	}
	function exportObject(){
		form.action=action+"?method=export";
		transferParams(parent.form,form,"instructWorkload");
		form.submit();
	}
    orderBy =function(what){
		parent.search(1,${pageSize},what);
	}
	function pageGoWithSize(pageNo,pageSize){
       parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
    }
 </script>
</body>
 <#include "/templates/foot.ftl"/>