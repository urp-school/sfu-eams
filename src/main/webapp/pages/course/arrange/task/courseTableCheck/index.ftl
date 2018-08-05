<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="myBar"></table>
    <table class="frameTable_title">
     <tr>
      <td id="viewTD1">
      	  <font color="blue"><@bean.message key="action.advancedQuery"/></font>
      </td> 
      <td>|</td>
     <form name="searchForm" method="post" action="courseTableCheck.do?method=index" onsubmit="return false;">
      </td>
      <#include "/pages/course/calendar.ftl"/>
      </tr>
  </table>
 
 <#assign stdTypeList =calendarStdTypes/>
 <#include "/pages/components/initAspectSelectData.ftl"/>
 <table width="100%" class="frameTable" height="85%">
    <tr>
     <td class="frameTable_view" style="width:20%">
     	<#include "searchForm.ftl"/>
     </td>
     </form>
     <td valign="top">
	     <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
  </tr>
</table>
 <script>
   var form =document.searchForm;
   function search(pageNo,pageSize,orderBy){
        form.action="courseTableCheck.do?method=search";
        form.target="contentListFrame";
        goToPage(form,pageNo,pageSize,orderBy);
   }
   search(1);
   function stat(){
      form.action="courseTableCheck.do?method=stat";
      form.submit();
   }
   var bar= new ToolBar("myBar","课表核对管理",null,true,true);
   bar.addItem("统计","stat()");
   bar.addHelp("<@msg.message key="action.help"/>");
 </script>
</body>
<#include "/templates/foot.ftl"/> 