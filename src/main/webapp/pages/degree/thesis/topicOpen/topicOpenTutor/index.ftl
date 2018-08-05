<#include "/templates/head.ftl"/>
<script>
    function search(){
    	 document.pageGoForm.action ='thesisTopicOpen_tutor.do?method=doStdList';
      	 document.pageGoForm.submit();
    }
     function setDate(){
   		var parames = getInputParams(document.pageGoForm,"thesisManage");
   		thesisTopicOpenFrame.setDate(parames);
   }
    function pageGo(pageNo){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.submit();
    }    
</script> 
<BODY LEFTMARGIN="0" TOPMARGIN="0" onLoad="search();" >
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','论文开题',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
  <#include "../topicOpen/conditions.ftl"/>
</body>     
<#include "/templates/foot.ftl"/>
   