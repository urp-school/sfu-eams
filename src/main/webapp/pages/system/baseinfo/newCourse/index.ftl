<#include "/templates/head.ftl"/>
<BODY>
 <table id="myBar"></table>
 <table class="frameTable" width="100%">
   <tr>
    <td width="20%" class="frameTable_view">
    <form name="searchForm" method="post" target="contentListFrame">
      <#assign extraSearchOptions><#include "../../base.ftl"/><@stateSelect "course"/></#assign>
	  
	  <#include "searchForm.ftl"/>
	  <table><tr height="350px"><td></td></tr></table>
     </form>
    </td>
    <td valign="top">
      <iframe src="#" 
     id="contentListFrame" name="contentListFrame" 
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="0" height="100%" width="100%">
     </iframe>
    </td>
   </tr>
  </table>
  <script language="javascript">
	bar= new ToolBar("myBar","新开课程管理");
	bar.addBack();	
	function search(){
	  document.searchForm.action="newCourse.do?method=search";
	  document.searchForm.submit();
	}
	search();
  </script>
  </body>
<#include "/templates/foot.ftl"/>