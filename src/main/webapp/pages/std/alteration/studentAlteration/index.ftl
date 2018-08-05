<#include "/templates/head.ftl"/>
<body>
 <table id="bar"></table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr>
   <td style="width:20%" valign="top" class="frameTable_view">
     <form name="stdSearch" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="orderBy" value="std.code asc"/>
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
    var bar=new ToolBar("bar","学籍变动管理",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addHelp("<@msg.message key="action.help"/>");
    
    var form = document.stdSearch;
    function search(pageNo,pageSize,orderBy){
	   form.action = "studentAlteration.do?method=search";
	   form.target="contentFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    
    search();
    function notPassed(){
       setSelected(document.getElementById("isPass"),3);
       search();
    }
    
    function stat() {
    	form.action = "studentAlteration.do?method=stat";
	   	form.target="contentFrame";
	   	form.submit();
    }
</script>
</body>
<#include "/templates/foot.ftl"/>