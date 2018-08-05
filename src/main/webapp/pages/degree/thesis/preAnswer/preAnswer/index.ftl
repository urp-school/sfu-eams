<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','论文预答辩',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<#include "condition.ftl"/>
<script>
	var form = document.listForm;
    function search(pageNo,pageSize){
    	 form.action ='preAnswer.do?method=stdList';
      	 goToPage(form,pageNo,pageSize);
    }
  	search();
</script>
</body>
<#include "/templates/foot.ftl"/>
