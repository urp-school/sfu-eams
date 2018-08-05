<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table cellpadding="0" cellspacing="0" width="90%" align="center">
   <tr>
    <td align="center" colspan="4">
    <table id="myBar" width="100%"></table>
    </td>
   </tr>
   <tr>
     <td>
   	 <table width="100%" align="center" class="listTable">
	   <tr align="center" class="darkColumn">
	     <td width="10%"><@bean.message key="attr.courseNo"/>11</td>
	     <td width="20%"><@bean.message key="attr.courseName"/></td>
	     <td width="14%"><@bean.message key="entity.courseType"/></td>
	     <td width="11%"><@bean.message key="attr.year2year"/></td>
	     <td width="10%"><@bean.message key="attr.term"/></td>
	     <td width="10%"><@bean.message key="entity.teacher"/></td>
	     <td width="19%"><@bean.message key="entity.teachClass"/></td>	     
	   </tr>
	   <#assign teachTask >${result.teachTask}</#assign>	      
	   <tr class="brightStyle">
	    <td>&nbsp;${ result.teachTask.course.code?if_exists}</td>
	    <td>&nbsp;<@i18nName result.teachTask.course?if_exists/></td>
	    <td>&nbsp;<@i18nName result.teachTask.courseType?if_exists/></td>
	    <td>&nbsp;${result.teachTask.calendar.year}</td>
	    <td>&nbsp;${result.teachTask.calendar.term}</td>
	    <td>&nbsp;<@getTeacherNames result.teachTask.arrangeInfo.teachers?if_exists/></td>
	    <td>&nbsp;<@i18nName result.teachTask.teachClass?if_exists/></td>	    
	   </tr>
     </table>
   </tr>
   <tr>
   <td>
   &nbsp;
   </td>
   </tr>
   <tr>
    <td>
     <table width="100%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center" width="5%"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('ids'),event);"></td>
	     <td><@bean.message key="field.feeDetail.studentName"/></td>
	     <td><@bean.message key="attr.stdNo"/></td>
	     <td>考勤</td>
	     <td>出勤</td>
	     <td>缺勤</td>
	     <td>迟到</td>
	     <td>早退</td>
	     <td>请假</td>
	     <td><@bean.message key="attr.graduate.attendClassRate"/></td>
	     <td>缺勤率</td>
	     <td>修读类别</td>
	     <td><@bean.message key="system.button.modify"/></td>
	   </tr>	   
	   <#list (result.recordList?sort_by(["student","code"]))?if_exists as record>
	   <#if record_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if record_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td align="center"><input type="checkBox" name="ids" value="${record.student.id}"></td>
	    <td>&nbsp;<@i18nName record.student?if_exists/></td>
	    <td>&nbsp;${record.student.code}</td>
	    <td>&nbsp;${record.totalCount?default(0)}</td>
	    <td>&nbsp;${record.dutyCount?default(0)}</td>
	    <td>&nbsp;${record.absenteeismCount?default(0)}</td>
	    <td>&nbsp;${record.lateCount?default(0)}</td>
	    <td>&nbsp;${record.leaveEarlyCount?default(0)}</td>
	    <td>&nbsp;${record.askedForLeaveCount?default(0)}</td>
	    <td>&nbsp;${record.dutyRatio?default(0)?string.percent}</td>
	    <td>&nbsp;${record.absenteeismRatio?default(0)?string.percent}</td>
	    <td>&nbsp;<@i18nName record.getCourseTakeType(false) /></td>
	    <td align="center">
	     <a href="javascript:update(${record.id}, ${record.dutyCount?default(0)}, ${record.totalCount?default(0)}, ${RequestParameters["teachTaskId"]}, ${record.student.id}, 'dutyRecordList');" >
	      &lt;&lt;
	     </a>
	    </td>
	   </tr>
	   </#list>
	   </form>
     </table>
    </td>
   </tr>
  </table>
  <form name="messageForm" method="post" target="_blank" ></form>
 </body>
 <script>
 	var id2code = new Object();
 	<#list result.recordList?if_exists as record>
 		id2code['${record.student.id}']='${record.student.code}';
 	</#list>
 	function getCodes(ids){
       if(ids==""){
       	return "";
       }else{
       	var idArray = ids.split(",");
       	var codes = "";
       	for(var i=0; i<idArray.length; i++){
          if(id2code[idArray[i]]){
			codes +=id2code[idArray[i]]+",";
          }
       	}
       	return codes;
       }
    }
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("ids")));
    }
    
    function update(recordId, dutyCount, totalCount, teachTaskId, stdId, from){
       var url = getAction(self.location.href) + "?method=recordUpdateForm"
                 + "&recordId=" + recordId
                 + "&dutyCount=" + dutyCount 
                 + "&totalCount=" + totalCount
                 + "&teachTaskId=" + teachTaskId
                 + "&stdId=" + stdId
                 + "&from=" + from;
       popupCommonWindow(url ,'updateRecordWin', 350, 200);
    }
    
    function getAction(location){
    	var actionAll = location.split("/");
    	var action = actionAll[actionAll.length-1].split("?");
    	return action[0];
    }
    
    function sendMessage(){
       var stdId = getIds();
       
       if (stdId==""){
          alert("<@bean.message key="info.studentClassManager.selectStudent"/>");
       }else{
           var form = document.messageForm;
           form.action= "systemMessage.do?method=quickSend&who=std&receiptorIds="+getCodes(stdId);
           addInput(form,"message.body","考勤消息","hidden");
           addInput(form,"message.title","考勤消息","hidden");
           addInput(form,"message.type.id","2","hidden");
           form.submit();
       }
    }
    
    function sendMessage1(){
       var stdId = getIds();
       
       if (stdId==""){
          alert("<@bean.message key="info.studentClassManager.selectStudent"/>");
       }else{
           var location = self.location.href;
           var action = location.split("/");
           var url = action[action.length-1] + "?method=messageSendForm&teachTaskId=${RequestParameters["teachTaskId"]}&stdId=" + stdId;
           popupCommonWindow(url, 'messageForm', 400, 300);
       }
    }
    var bar = new ToolBar("myBar","课程考勤管理按照学生显示",null,true,true);
    bar.addItem("发送消息","sendMessage()");
    bar.addBack("<@msg.message key="action.back"/>");
 </script>
<#include "/templates/foot.ftl"/>