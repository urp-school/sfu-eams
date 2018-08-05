<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar"></table>
   <table border="0"  class="frameTable_title" width="100%">
      <tr height="22px">
       <td></td>      
      <form name="calendarForm" target="teachTaskListFrame" method="post" action="requirePrefer.do?method=index" onsubmit="return false;">
      <input type="hidden" name="listWhat" value="listTask"/>
      <input type="hidden" name="teacher.id" value="${teacher.id}"/>
      <#include "/pages/course/calendar.ftl"/>
     </form>
      </tr>
     </table>
     <table border="0"  class="frameTable" width="100%" height="90%" >  
      <tr>
        <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
	      <table  width="100%" id ="viewTables" style="font-size:10pt">
	      <tr>
		      <td  class="infoTitle" align="left" valign="bottom">
		       <img src="${static_base}/images/action/info.gif" align="top"/>
		          <B>课程要求</B>
		      </td>
		  <tr>
		    <tr>
		      <td  colspan="8" style="font-size:0px">
		          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
		      </td>
		   </tr>
	       <tr>
	         <td class="padding" id="defaultItem" onclick="search(this,'requirePrefer')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">教材设置
	         </td>
	       </tr>
	       <tr>
	         <td class="padding"  onclick="search(this,'multimediaRequirement')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">多媒体教室要求
	         </td>
	       </tr>
	        <tr>
	         <td class="padding"  onclick="search(this,'laboratoryRequirement')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">实验室要求
	         </td>
	       </tr>
		  </table>
	  <td>
      <td valign="top" colspan="12">
	     <iframe  src="#"
	     id="teachTaskListFrame" name="teachTaskListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	     </iframe>
      </td>
      </tr>
     </table>
 <script>
   var bar = new ToolBar("myBar","<@bean.message key="info.taskRequirement.management" />",null,true,true);
   bar.addHelp("<@msg.message key="action.help"/>");
   form=document.calendarForm;
   document.getElementById("defaultItem").onclick();
   function search(td,actionURL){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      form.action = (isEmpty(actionURL) ? "requirePrefer.do" : actionURL) + ".do?method=search"
      form.target="teachTaskListFrame";
      form.submit();
   }
 </script> 
 </body>
<#include "/templates/foot.ftl"/> 
  