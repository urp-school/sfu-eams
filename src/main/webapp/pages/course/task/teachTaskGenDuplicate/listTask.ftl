<#include "/templates/head.ftl"/>
 <body  LEFTMARGIN="0" TOPMARGIN="0" >
 <#assign labInfo><@bean.message key="info.taskGen.taskListOfScheme" /></#assign>  
 <#include "/templates/back.ftl"/>
 <#include "/pages/course/task/teachTaskList.ftl"/>
 <script>
	function query(pageNo,pageSize){
	    var form = document.taskListForm;
	    var thisParams=getInputParams(form,null,true);
	    addHiddens(form,queryStr);
	    addHiddens(form,thisParams);
	    form.action="teachTaskGenDuplicate.do?method=listTask";
	    goToPage(form,pageNo,pageSize);
	  }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 