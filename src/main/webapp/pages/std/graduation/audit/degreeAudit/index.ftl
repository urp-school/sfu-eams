<#include "/templates/head.ftl"/>
<body>
  <table id="gradeBar"></table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <form name="stdSearch" method="post" action="degreeAudit.do?method=index" onsubmit="return false;">
  <tr>
   <td style="width:20%" valign="top" class="frameTable_view">
		<#include "/pages/components/initAspectSelectData.ftl"/>
		<#include "searchForm.ftl"/>
	  </form>
     </td>
     <td valign="top">
	     <iframe src="#" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
<script>
    var action="degreeAudit.do";
    function search(pageNo,pageSize,orderBy){
       var form = document.stdSearch;
	   form.action = action+"?method=search";
	   form.target="contentFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    var bar=new ToolBar("gradeBar","学位审核管理",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addHelp("<@msg.message key="action.help"/>");
    search();
    function notPassed(){
       setSelected(document.getElementById("isPass"),3);
       search();
    }
</script> 
 </body>   
<#include "/templates/foot.ftl"/> 