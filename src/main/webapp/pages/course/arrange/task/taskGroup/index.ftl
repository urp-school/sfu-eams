<#include "/templates/head.ftl"/>
<BODY>
 <#assign labInfo><@bean.message key="page.taskGroupFrame.label"/></#assign>
 <#include "/templates/help.ftl">
    <table class="frameTable_title">
      <tr>
       <td id="viewTD0" class="transfer" style="width:80px" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><@bean.message key="entity.taskGroup"/></font>
       </td>
       <td>|</td>
      
      <form name="taskGroupForm" method="post" action="taskGroup.do?method=index" onsubmit="return false;">
      <#include "/pages/course/calendar.ftl"/>
       </form>
      </tr>
    </table>
 
  <table width="100%" class="frameTable" height="85%">
    <tr>
     <td class="frameTable_view" style="width:17%">
	     <iframe src="taskGroup.do?method=groupList&calendar.id=${calendar.id}&displayFirst=1&taskGroup.id=${taskGroupId?if_exists}"
	     id="taskGroupListFrame" name="taskGroupListFrame" 
	     marginwidth="0" marginheight="0" scrolling="no" 
	     frameborder="0" height="100%" width="100%">
	     </iframe>
     </td>
     
     <td valign="top" width="83%">
	     <iframe src="#"
	     id="taskGroupContentFrame" name="taskGroupContentFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0" height="100%" width="100%">
	     </iframe>
     </td>
    </tr>
  <table>
 <script>
   function newGroup(){
       var form = document.taskGroupForm;
       form.action="taskGroup.do?method=newGroup";
       form.target="taskGroupContentFrame";
       form.submit();
   }
   var viewNum=1;
   var displayFirst=false;
   
   function getGroupList(){
     selectedGroupType=groupType;
     changeView(event.srcElement);
     taskGroupListFrame.window.location="taskGroup.do?method=groupList&calendar.id=${calendar.id}";
   }
   function setRefreshGroupListTime(time,displayFirstGroup){
       if(null==displayFirstGroup) {
           displayFirst=false;
       } else {
           displayFirst=displayFirstGroup;
       }
       setTimeout("refreshGroupList()",time);
   }
   function refreshGroupList(){
       taskGroupListFrame.document.write("refresh group list..");
       var calendarId = document.taskGroupForm['calendar.id'].value;
       if(displayFirst) {
         taskGroupListFrame.window.location="taskGroup.do?method=groupList&calendar.id="+ calendarId+"&displayFirst=1";
       } else {
         taskGroupListFrame.window.location="taskGroup.do?method=groupList&calendar.id="+ calendarId+"&displayFirst=0";
       }
   }
 </script>
<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 
</body>
<#include "/templates/foot.ftl"/> 
  