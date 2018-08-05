<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar"></table>
   <table border="0"  class="frameTable_title" width="100%">
      <tr height="22px">
       <td></td>      
      <form name="searchForm" target="contentFrame" method="post" action="teacherTaskAlterRequest.do?method=index" onsubmit="return false;">
      <input type="hidden" name="taskAlterRequest.teacher.id" value="${teacher.id}"/>
      <#assign studentType=stdTypeList?first/>
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
		          <B>申请/任务</B>      
		      </td>
		  <tr>
		    <tr>
		      <td  colspan="8" style="font-size:0px">
		          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
		      </td>
		   </tr>
	       <tr>
	         <td class="padding" id="defaultItem" onclick="search(this,'alterRequestList')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">我的申请
	         </td>
	       </tr>
	       <tr>
	         <td class="padding"  onclick="search(this,'taskList')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
	         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">我的课程
	         </td>
	       </tr>
		  </table>
	  <td>
      <td valign="top" colspan="12">
	     <iframe  src="#"
	     id="contentFrame" name="contentFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%">
	     </iframe>
      </td>
      </tr>
     </table>
 <script>
   var bar = new ToolBar("myBar","课程安排变更申请",null,true,true);
   bar.addHelp("<@msg.message key="action.help"/>");
   form=document.searchForm;
   action="teacherTaskAlterRequest.do";
   document.getElementById("defaultItem").onclick();
   function search(td,what){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      form.action=action+"?method="+what;
      form.target="contentFrame";
      form.submit();
   }
 </script> 
 </body>
<#include "/templates/foot.ftl"/> 
  