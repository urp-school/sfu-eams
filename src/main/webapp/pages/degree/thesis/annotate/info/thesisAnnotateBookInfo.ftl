<table width="100%" align="center" class="infoTable">
	  <input type="hidden" name="annotateBook.id" value="${annotateBook.id?if_exists}">
	  <caption>
      <B>对论文学术水平的总体评价意见</B>
    </caption>
      <tr>
      	<td class="title">论文题目</td>  
	    <td  >
	      ${annotateBook.thesisManage?if_exists.topicOpen?if_exists.thesisTopic?if_exists}
	    </td>
	    <td class="title">论文评阅编号</td>  
	    <td>${annotateBook.serial?if_exists}</td>
      </tr>
      <tr>
      	<td class="title"  id="f_thesisAppraise"><@bean.message key="filed.thesisAnnotate" />：</td>  
	    <td colspan="3">${annotateBook?if_exists.thesisAppraise?if_exists}</td>      	 		
      </tr> 
      <tr>
      	<td class="title"  id="f_reply">对作者可否参加论文答辩的意见：</td>  
	    <td colspan="3">
			<#if annotateBook?if_exists.isReply?exists && annotateBook?if_exists.isReply?string=="A">同意答辩&nbsp;</#if>
			<#if annotateBook?if_exists.isReply?exists && annotateBook?if_exists.isReply?string=="M">修改后答辩&nbsp;</#if>	
			<#if annotateBook?if_exists.isReply?exists && annotateBook?if_exists.isReply?string=="D">不同意答辩&nbsp;</#if>			
	    </td>
      </tr>
      <#if studentFlag?exists&&studentFlag=="doctor">
      <tr align="center">
      <td class="title" id="f_learningLevel" align="left">论文是否达到授予博士学位的学术水平</td>
      <td colspan="3" align="left">
        <#if annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?string=='A'>优秀</#if>
        <#if annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?string=='B'>良好</#if>
        <#if annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?string=='C'>一般</#if>
        <#if annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?string=='D'>不及格</#if>
      </td>
      </tr>
      </#if>
      <tr>
      	<td class="title" width="15%" id="f_name"><@bean.message key="filed.annotatePerson" />：</td>  
	    <td >${annotateBook?if_exists.answerTem?if_exists.name?if_exists}</td> 
      	<td class="title" width="15%" id="f_title"><@bean.message key="filed.annotateJob" />：</td>  
	    <td width="15%">${annotateBook?if_exists.answerTem?if_exists.teacherTitle?if_exists.name?if_exists?string}</td>
      </tr>
      <tr>
      	<td class="title"  id="f_depart"><@bean.message key="filed.annotateCompany" />：</td>  
	    <td colspan="3">${annotateBook.answerTem?if_exists.depart?if_exists}</td>
      </tr>
      <tr>
      		<td align="center" colspan="4">
      			<p align="left">专家签字或盖章:</p>
      			<p align="left">单位:<p align="right">年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日</p>
      		</td>
      </tr>
  </table>