<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar" width="100%"></table>
	<#include "../queryConditions.ftl"/>
	<script>
	 var bar = new ToolBar("backBar","查询评教",null,true,true);
	 bar.setMessage('<@getMessage/>');
	 bar.addItem("<@msg.message key="action.export"/>","expFunction()","excel.png","导出的条件来自左边的查询条件");
		var form = document.searchForm;
		var action="questionnaireStatSearch.do";
		function search(pageNo,pageSize,orderBy){
		    form.action=action+"?method=search";
		    goToPage(form,pageNo,pageSize,orderBy);
		}
		function expFunction(){
			form.action=action+"?method=export";
			form.submit();
		}
	    search(1);
	</script>
<#include "/templates/foot.ftl"/>