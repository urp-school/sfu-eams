<#include "/templates/head.ftl"/>
<body>
 <table id="gradeBar"></table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr >
   <td style="width:20%" valign="top" class="frameTable_view">
		<#include "searchForm.ftl"/>
     </td>
     <td valign="top">
	     <iframe src="#" id="gradeFrame" name="gradeFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
<script>
    var bar=new ToolBar("gradeBar","校内外考试报名管理",null,true,true);
    bar.addHelp("<@msg.message key="action.help"/>");
    
    var action="otherExamSignUp.do"
    function search(pageNo,pageSize,orderBy){
       var form = document.searchForm;
	   form.action = action+"?method=search";
	   form.target="gradeFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    search();
</script> 
</body>
<#include "/templates/foot.ftl"/> 