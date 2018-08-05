<#include "/templates/head.ftl"/>
<BODY>
	<table id="autoArrangeBar"></table>
	<table class="frameTable_title">
      	<tr>
       		<td id="viewTD0" class="transfer" style="width:40px" onclick="javascript:getArrange('task',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          		<font color="blue"><@bean.message key="entity.commonCourse"/></font>
       		</td>
       		<td id="viewTD1" class="padding" style="width:60px" onclick="javascript:getArrange('TaskGroup',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          		<font color="blue"><@bean.message key="entity.taskGroup" /></font>
       		</td>
       		<td>|</td>
       	<form name="taskGroupForm" method="post" target="contectFrame" action="autoArrange.do?method=index" onsubmit="return false;">
       		<td class="infoTitle">
	         	<select name="task.arrangeInfo.isArrangeComplete" onChange="search()" style="width:80px">
	             	<option value="0" selected><@bean.message key="common.notArranged"/></option>
	             	<option value="1"><@bean.message key="common.alreadyArranged"/></option>
	         	</select>
       		</td>
	      	<input type="hidden" name="task.calendar.id" value="${calendar.id}"/>
	      	<input type="hidden" name="arrangeType" value="task"/>
	      	<#include "/pages/course/calendar.ftl"/>
      	</tr>
    </table>
    
  	<table width="100%" class="frameTable">
    	<tr valign="top">
    		<td class="frameTable_view">
    			<table border="0" width="100%" height="100%" cellspacing="0" cellpadding="0">
			    	<tr valign="top">
			     		<td align="center">本次排课选用的教室：<br>
			         		<select id="selectedRoom" onDblClick="unSelectRoom()" name="classroom" size="10" MULTIPLE style="width:168px;"></select>
			         	</td>
			        </tr>
			        <tr height="30">
			        	<td align="center"><button onclick="selectRoom()" title="选择教室">向上</button>&nbsp;&nbsp;<button onclick="unSelectRoom()">向下</button></td>
			        </tr>
			        <tr>
			        	<td align="center">
			         		<select id="availableRoom" onDblClick="selectRoom()" MULTIPLE size="10" style="width:168px;">
			            		<#list roomList as room>
			            			<option value="${room.id}"><@i18nName room/>/<@i18nName room.configType/>/${room.capacityOfCourse}</option>
			            		</#list>
			          		</select>
			     		</td>
			     	</tr>
			     </table>
			</td>
      	</form>
     		<td valign="top" width="80%">
	     		<iframe src="#" id="contectFrame" name="contectFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     		</td>
    	</tr>
  	<table>
  	<br>
	<script>
	  	var bar = new ToolBar("autoArrangeBar","<@bean.message key="info.arrange.autoManagement"/>",null,true,true);
	  	bar.setMessage('<@getMessage/>');
	  	bar.addItem("最近一次安排","listLastArrange()");
	  	bar.addHelp("<@msg.message key="action.help"/>","autoArrange/index");
	  	
	   	var viewNum=2;
	   	function getArrange(groupType,event){
	     	changeView(getEventTarget(event));
	     	document.taskGroupForm.arrangeType.value=groupType;
	     	search();
	   	}
	   	function search(){
	      	document.taskGroupForm.action="autoArrange.do?method=list";
	      	document.taskGroupForm.submit();
	   	}
	   	search();
	   	function listLastArrange(){
	       	contectFrame.window.location="autoArrange.do?method=lastArrangeList";
	   	}
	   	function setRefreshTime(time){
	       	setTimeout("search()",time);
	   	}
	   	function selectRoom(){
	      	moveSelectedOption(document.getElementById('availableRoom'),document.getElementById('selectedRoom'));
	   	}
	   	function unSelectRoom(){
	     	moveSelectedOption(document.getElementById('selectedRoom'),document.getElementById('availableRoom'));
	   	}
	</script>
	<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 
</body>
<#include "/templates/foot.ftl"/> 
  