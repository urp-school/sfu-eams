<#include "/templates/head.ftl"/>
<body>
 <#include "/pages/components/initAspectSelectData.ftl"/>
 <table id="myBar"></table>
 <table class="frameTable_title">
    <tr>
       <td  style="width:50px">
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
    <form name="searchForm" target="reportFrame" method="post" action="multiStdGP.do?method=index" onsubmit="return false;">
   </tr>
  </table>
  <table width="100%" border="0" height="100%" class="frameTable">
   <tr>
     <td style="width:20%" valign="top" class="frameTable_view">
		<#include "/pages/components/adminClassSearchTable.ftl"/>
     </td>
    </form>
    <td valign="top">
	     <iframe src="#" id="reportFrame" name="reportFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
	<script>
	    var bar=new ToolBar("myBar","行政班学生绩点表",null,true,true);
	    bar.addHelp("<@msg.message key="action.help"/>");
	    
	    var action="multiStdGP.do";
	    function search(pageNo,pageSize,orderBy){
	       var form = document.searchForm;
		   form.action = action+"?method=adminClassList";
		   form.target="reportFrame";
	       goToPage(form,pageNo,pageSize,orderBy);
	    }
	    searchClass=search;
	    search();
	</script>
</body>
<#include "/templates/foot.ftl"/> 