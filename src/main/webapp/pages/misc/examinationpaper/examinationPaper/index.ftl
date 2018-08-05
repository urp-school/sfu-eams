<#include "/templates/head.ftl"/>
<body>
	<table id="examinationPaperBar"></table>
	<table class="frameTable_title">
      <tr>
       <td style="width:50px"/>
          <font color="blue"><@bean.message key="action.advancedQuery"/></font>
       </td>
       <td>|</td>
      <form name="examinationPaperForm" target="examinationPaperFrame" method="post" action="examinationPaper.do?method=index" onsubmit="return false;">
        <#include "/pages/course/calendar.ftl"/>
      </tr>
    </table>
	<table class="frameTable" width="100%">
	   	<tr>
	   		<td width="20%" class="frameTable_view"><#include "searchForm.ftl"/></td>
	  </form> 		
	    	<td valign="top">
	 			<iframe src="#" id="examinationPaperFrame" name="examinationPaperFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
    		</td>
   		</tr>
  	</table>
	<script>
	  	var bar=new ToolBar("examinationPaperBar","试卷管理",null,true,true);
	  	bar.setMessage('<@getMessage />');
        bar.addHelp();
        
        function searchExaminationPaper() {
	    	var form = document.examinationPaperForm;
	    	form.target = "examinationPaperFrame";
	    	form.action = "examinationPaper.do?method=search";
	    	form.submit();
	    }
	    
	    searchExaminationPaper();
	</script>  
</body>
<#include "/templates/foot.ftl"/>