<#include "/templates/head.ftl"/>
<body>
	<table id="filePrintBar"></table>
	<table class="frameTable_title">
      <tr>
       <td style="width:50px"/>
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
      <form name="filePrintManageForm" target="filePrintApplicationFrame" method="post" action="filePrintManage.do?method=index" onsubmit="return false;">
        <#include "/pages/course/calendar.ftl"/>
      </tr>
    </table>
	<table class="frameTable" width="100%">
	   	<tr>
	   		<td width="20%" class="frameTable_view">
	   		  <#include "searchForm.ftl"/>
	   		  <table><tr height="300px"><td></td></tr></table>
	   		</td>
	  </form> 		
	    	<td valign="top">
	 			<iframe src="#" id="filePrintApplicationFrame" name="filePrintApplicationFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
    		</td>
   		</tr>
  	</table>
	<script>
	  	var bar=new ToolBar("filePrintBar","请印申请列表",null,true,true);
	  	bar.setMessage('<@getMessage />');
        bar.addHelp();
        
        function searchFilePrintApplication() {
	    	var form = document.filePrintManageForm;
	    	form.target = "filePrintApplicationFrame";
	    	form.action = "filePrintManage.do?method=search";
	    	form.submit();
	    }
	    
	    searchFilePrintApplication();
	</script>  
</body>
<#include "/templates/foot.ftl"/>