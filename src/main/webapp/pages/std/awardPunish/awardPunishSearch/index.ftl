<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
<script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>

<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bar"></table>
   <table class="frameTable">
   <tr>
      <form name="actionForm" action="awardPunishSearch.do?method=search" method="post" target="contentFrame" onsubmit="return false;">
    <td width="20%" class="frameTable_view">
		<#include "/pages/components/initAspectSelectData.ftl"/>
      	<#include "searchForm.ftl"/>
      </td>
      </form>
    <td valign="top">  
     <iframe src="#" id="contentFrame" name="contentFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
	</td>
   </tr>
  </table>
<script language="javascript">
   viewNum=2;
   function changeView1(id,event){
     changeView(getEventTarget(event));
     if(id=="view1"){
         $("view1").style.display = "block"; 
         $("view0").style.display = "none";
         search("award",1,null,null);
     }else{
         $("view0").style.display = "block"; 
         $("view1").style.display = "none";
         search("punishment",1,null,null);
     }
   }
   function search(type, pageNo, pageSize, orderBy){
      var form = document.actionForm;  
      goToPage(form, pageNo, pageSize, orderBy);
   }
   search("award", 1, null, null);
   var bar=new ToolBar('bar', '学生奖惩查询', null, true, true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@bean.message key="action.help"/>");
 </script>
<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 
</body>
<#include "/templates/foot.ftl"/>