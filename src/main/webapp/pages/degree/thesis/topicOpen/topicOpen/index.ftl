<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
   <#include "conditions.ftl"/>
<script>
   var bar = new ToolBar('backBar','论文开题',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
   
   var form = document.pageGoForm;
    function search(pageNo,pageSize){
    	 form.action ='thesisTopicOpen.do?method=doStdList';
      	 goToPage(form,pageNo,pageSize);
    }
   function setData(){
   		var parames = getInputParams(document.pageGoForm);
   		thesisTopicOpenFrame.setData(parames);
   }
   search();
</script>
</body>
<#include "/templates/foot.ftl"/>
    