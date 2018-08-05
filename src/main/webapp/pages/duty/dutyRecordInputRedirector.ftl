<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <#assign actionName = RequestParameters['actionName'] />
  <form name="commonForm" action="<#if actionName=="inputDutyRecordWithTeacher.do">dutyRecordManagerWithTeacher.do<#else>${actionName}</#if>" method="post" onsubmit="return false;">
    <input type="hidden" name="flag" value="search" />
    <input type="hidden" name="method" value="<#if actionName=="inputDutyRecordWithTeacher.do">managerByTeacherForm<#elseif actionName=="inputDutyRecord.do">searchTeachTask</#if>" />
    <input type="hidden" name="courseTypeId" value="${RequestParameters["courseTypeId"]?if_exists}" />
	<input type="hidden" name="year" value="${RequestParameters['year']?if_exists}" />
	<input type="hidden" name="term" value="${RequestParameters['term']?if_exists}" />
	<input type="hidden" name="studentTypeId" value="${RequestParameters["studentTypeId"]?if_exists}" />
	<input type="hidden" name="studentTypeDescriptions" value="${RequestParameters["studentTypeDescriptions"]?if_exists}" />
    <input type="hidden" name="departmentId" value="${RequestParameters["departmentId"]?if_exists}" />
	<input type="hidden" name="departmentDescriptions" value="${RequestParameters["departmentDescriptions"]?if_exists}" />
  </form>
  <table  width="100%" cellpadding="0" cellspacing="0">
    <tr>
      <td  class="infoTitle" style="height:22px;" >
       <BR>
      </td>
    </tr>
    <tr>
      <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
          <B><@bean.message key="info.action.success"/></B>
      </td>
   </tr>
   <tr>
     <td id="errorTD"><font color="green">
           <@html.errors />
      <#if RequestParameters.messages?exists>
      <#list RequestParameters.messages?split(",") as message>
         <#if (message?length>2)><@bean.message key="${message}"/></#if>&nbsp;
      </#list>
      </#if>
      </font>
      <BR><BR>
      </td>
   </tr>
   <tr>
         <td style="font-size:12px">该页面将在一秒钟后自动跳转，如果没有自动跳转，请点击
         <a href="javascript: redirect();" >这里</a>
         </td>
   </tr>
  </table>
  <script>
  	function redirect(){
  		var action=getAction(self.location.href);
  		if(action=="inputDutyRecord.do"){doSubmit();
  		}else if(action=="inputDutyRecordWithTeacher.do"){doParentSubmit();
  		}else{doSubmit();}
  	}
  	function getAction(location){
    	var actionAll = location.split("/");
    	var action = actionAll[actionAll.length-1].split("?");
    	return action[0];
    }
    function doSubmit(){
    	setTimeout('document.commonForm.submit()',1000); 	
    }
    function doParentSubmit(){
    	setTimeout('parent.taskSearch()',1000); 	
    }
    redirect()
//     document.commonForm.submit();
  </script>
 </body>
<#include "/templates/foot.ftl"/>