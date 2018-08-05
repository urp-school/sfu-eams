<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','教师指导工作量查询',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<#include "../instructWorkload/searchCondition.ftl"/>
<script>
	var form = document.instructWorkloadForm;
	var action="instructWorkloadSearch.do";
	function search(pageNo,pageSize,orderBy){
	    form.action=action+"?method=search";
	    goToPage(form,pageNo,pageSize,orderBy);
	}
    search(1);
</script>
</body>
<#include "/templates/foot.ftl"/>