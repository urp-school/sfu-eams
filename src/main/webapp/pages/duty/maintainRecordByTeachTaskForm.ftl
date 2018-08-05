<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td width="75%" colspan="3" align="center" height="30">
      <#include "/templates/messages.ftl">
    </td>
    <td colspan="1" align="center" height="30" class="message fade-ffff00" >    	
    	<div class="message fade-ffff00" id="showMessage"></div>
    </td>
    <td width="5%" colspan="3" align="center" height="30">
    </td>
   </tr>
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B>按课程表录入考勤</B>
    </td>
   </tr>
      <tr>
   	 <table width="85%" align="center" class="listTable">
	   <tr align="center" class="darkColumn">
	     <td width="10%"><@msg.message key="attr.taskNo"/></td>
	     <td width="10%"><@bean.message key="attr.courseNo"/></td>
	     <td width="20%"><@bean.message key="attr.courseName"/></td>
	     <td width="14%"><@bean.message key="entity.courseType"/></td>
	     <td width="11%"><@bean.message key="attr.year2year"/></td>
	     <td width="5%"><@bean.message key="attr.term"/></td>
	     <td width="10%"><@bean.message key="entity.teacher"/></td>
	     <td width="19%"><@bean.message key="entity.teachClass"/></td>	     
	   </tr>	   
	   <tr class="brightStyle">
	    <td>&nbsp;${ result.teachTask.seqNo?if_exists}</td>
	    <td>&nbsp;${ result.teachTask.course.code?if_exists}</td>
	    <td>&nbsp;<@i18nName result.teachTask.course?if_exists/></td>
	    <td>&nbsp;<@i18nName result.teachTask.courseType?if_exists/></td>
	    <td>&nbsp;${result.teachTask.calendar.year}</td>
	    <td>&nbsp;${result.teachTask.calendar.term}</td>
	    <td>&nbsp;<@getTeacherNames result.teachTask.arrangeInfo.teachers?if_exists/><#--<@eraseComma teachTask.arrangeInfo.teacherNames?if_exists/>--></td>
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
     <table width="85%" align="center" class="listTable">
       <form name="commonForm" method="post" action="inputDutyRecord.do" onSubmit="return false;" target="_blank">
       <tr class="brightStyle" >
	    <td>
			开始日期<input type="text" id="beginDate" name="beginDate" onfocus="calendar()" size="10" maxlength="10"/>&nbsp;&nbsp;学期开始时间${result.teachTask.calendar.start?string('yyyy-MM-dd')}
	    </td>
	    <td>
	    	结束日期<input type="text" id="endDate" name="endDate" onfocus="calendar()" size="10" maxlength="10"/>&nbsp;&nbsp;学期结束时间${result.teachTask.calendar.finish?string('yyyy-MM-dd')}
	    </td>
	   </tr>
	   
	   <tr>
	    <td colspan="3" align="center" class="darkColumn">	     
	     <input type="hidden" name="teachTaskId" value="${result.teachTask.id}"/>
	     <input type="hidden" name="method" value="maintainRecordByTeachTask" />
	     <input type="button" onClick="search(this.form)" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />&nbsp;
	     <input type="button"  onClick="resetForm(this.form)" value="<@bean.message key="system.button.reset" />" name="reset1"  class="buttonStyle" />
        </td>
	   </tr>
	   
	   </form>
     </table>
    </td>
   </tr>   
  </table>
 </body>
 <script> 	
 	
 	function resetForm(form){
 		document.getElementById("showMessage").innerHTML="";
    	document.getElementById("showMessage").style.display ="none";
		form.reset(); 
 	}
 	
    function search(form){
    	document.getElementById("showMessage").innerHTML="";
    	document.getElementById("showMessage").style.display ="none";
    	if(check()){form.action=getAction(self.location.href);form.submit();}
    }
    function check(){
    	var flag=true;
    	var msg="";
    	if($("beginDate").value!=""){
	    	if(getDateTime($("beginDate").value)){
	    		if((getDateTime($("beginDate").value)-(getDateTime('${result.teachTask.calendar.start?string('yyyy-MM-dd')}')))<0){
	    			msg+="起始日期早于学期开始日期！\n";flag=false;
	    		}
	    	}else{msg+="起始日期格式不正确！\n";flag=false;}
    	}
    	if($("endDate").value!=""){
	    	if(getDateTime($("endDate").value)){
	    		if((getDateTime($("endDate").value)-(getDateTime('${result.teachTask.calendar.finish?string('yyyy-MM-dd')}')))>0){
	    			msg+="结束日期晚于学期结束日期！\n";flag=false;
	    		}
	    	}else{msg+="结束日期格式不正确！\n";flag=false;}
    	}
    	if($("beginDate").value!=""&&$("endDate").value!=""){
    	   if(getDateTime($("beginDate").value)>getDateTime($("endDate").value)){
    	      msg+="开始日期应早于结束日期\n";
    	   }
    	}
    	if(msg!=""){alert(msg); flag=false;}
    	return flag;
    }
    
    function getDateTime(dateString){
    	var dateArray = dateString.split("-");
    	if(dateArray.length<3){alert(dateString);return;}
    	var date = new Date(dateArray[0],dateArray[1]-1,dateArray[2]);
    	return date.getTime();
    }
    
    function showMessage(){
    	document.getElementById("showMessage").innerHTML="操作成功";    	
    	document.getElementById("showMessage").className="message fade-ffff00";
    	document.getElementById("showMessage").style.display ="block";
    }
    function getAction(location){
    	var actionAll = location.split("/");
    	var action = actionAll[actionAll.length-1].split("?");
    	return action[0];
    }    
 </script>
<#include "/templates/foot.ftl"/>