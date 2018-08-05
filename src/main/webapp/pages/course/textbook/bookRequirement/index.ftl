<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
<script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>

<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bookRequirementBar"></table>
 <table  class="frameTable_title">
      <tr>
       <td  id="viewTD0" class="transfer"  onclick="javascript:changeView1('view0',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><@msg.message key="textbook.assignRequireTask"/></font>
       </td>
       <td  id="viewTD1" class="padding" onclick="javascript:changeView1('view1',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><@msg.message key="textbook.notAssignRequireTask"/></font> 
       </td>
       <td class="separator">|</td>
      
      <form name="taskForm" target="teachTaskListFrame" method="post" method="bookRequirement.do?method=index" onsubmit="return false;">
      <input name="searchWhat" value="requirementList" type="hidden"/>
      <#include "/pages/course/calendar.ftl"/>
       </form>
      </tr>
     </table>
     

   <table class="frameTable">
   <tr>
    <td  style="width:160px"  class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">  
     <iframe  src="#"
     id="contentFrame" name="contentFrame" scrolling="no"
     marginwidth="0" marginheight="0"      frameborder="0"  height="100%" width="100%">
     </iframe>
	</td>
   </tr>
  </table>

<script language="javascript">
	var bar=new ToolBar('bookRequirementBar','<@msg.message key="textbook.requireManagement"/>',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addItem("打印教材发放清单","reportForAdminClass()","print.gif");
	bar.addHelp("<@msg.message key="action.help"/>");
	
   	viewNum=2;
   	function changeView1(id,event){
     changeView(getEventTarget(event));
     if(id=="view1"){
         $("view1").style.display = "block"; 
         $("view0").style.display = "none";
         search("taskList",1,null,null);
     }else{
         $("view0").style.display = "block"; 
         $("view1").style.display = "none";
         search("requirementList",1,null,null);
     }
   }
   function search(type,pageNo,pageSize,orderBy){
      var form =document.requireSearchForm;  
      if(type=="taskList"){
          form =document.taskSearchForm;       
      }
      goToPage(form,pageNo,pageSize,orderBy);
   }
   function reportForAdminClass(){
      window.open("bookRequirement.do?method=adminClassHome&calendar.id=${calendar.id}");
   }
 search("requirementList",1,null,null);
 function searchRequires(){
    search("requirementList",1,null,null);
 }
 function searchTaskList(){
    search("taskList",1,null,null);
 }
 </script>
<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 
</body>
<#include "/templates/foot.ftl"/>