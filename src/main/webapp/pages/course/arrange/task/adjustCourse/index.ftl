<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script> 
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="javascript:loadDefault('task')">
	<table id="bar"></table>
    <table class="frameTable_title" width="100%">
      <tr>
       <form name="taskForm" method="post" target="unArrangedListFrame" action="adjustCourse.do?method=index" onsubmit="return false;">
       <td id="viewTD0" class="transfer" style="width:80px" onclick="javascript:getTask('0',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><@bean.message key="entity.commonCourse"/></font>
       </td>
       <td id="viewTD1" class="padding" style="width:80px" onclick="javascript:getTask('1',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><@bean.message key="entity.GPCourse"/></font>
       </td>
       <td class="infoTitle"></td>
      <td>|</td>
       <input type="hidden" name="isGP" value="0"/>
       <input type="hidden" name="task.arrangeInfo.isArrangeComplete" value="1"/>
       <input type="hidden" name="task.calendar.id" value="${calendar.id}"/>
       <#include "/pages/course/calendar.ftl"/>
      </tr>
   </form>
  </table>

  
  <table width="100%" class="frameTable" height="85%">
    <tr> 
     <td valign="top" >
	     <iframe src="#" id="unArrangedListFrame" name="unArrangedListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
	<script>
		var bar = new ToolBar("bar", "<@msg.message key="entity.teachActivity"/>", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("调课变动记录", "arrangeAleration()");
		bar.addHelp("<@msg.message key="action.help"/>");
		var form =document.taskForm;
		
		function arrangeAleration() {
			form.action = "courseArrangeAlteration.do?method=index";
			form.target = "_self";
			form.submit();
		}
		
		function loadDefault(groupType){
			unArrangedListFrame.window.location="manualArrange.do?method=taskList&order=${order}&task.calendar.id=${calendar.id}&calendar.id=${calendar.id}&task.requirement.isGuaPai=0&task.arrangeInfo.isArrangeComplete=1";     
		}
	
	   	var viewNum = 2;
	   	function getTask(isGP,event){
	     	changeView(getEventTarget(event));
	     	document.taskForm.isGP.value=isGP;
	        form.target = "unArrangedListFrame";
	     	unArrangedListFrame.window.location = "manualArrange.do?method=taskList&order=${order}&task.calendar.id=${calendar.id}&calendar.id=${calendar.id}&task.requirement.isGuaPai=" +isGP + "&task.arrangeInfo.isArrangeComplete=" + document.taskForm['task.arrangeInfo.isArrangeComplete'].value;     
	    }
	    function searchTask(){
	        form.action="manualArrange.do?method=taskList&order=${order}";
	        form.target = "unArrangedListFrame";
	        form.submit();
	    }
	</script>
	<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 
</body>
<#include "/templates/foot.ftl"/> 