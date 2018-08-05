<table width="100%" align="center" class="listTable">
	  <input type="hidden" name="annotateBook${idSeq}.id" value="${annotateBook.id?if_exists}">
	  <caption>
      <B>对论文学术水平的总体评价意见</B>
    </caption>
      <tr class="darkColumn">
      	<td class="grayStyle">论文题目<#if flag?exists&&flag=="book"><font color="red">*</font></#if></td>  
	    <td  class="brightStyle">
	      ${annotateBook.thesisManage?if_exists.topicOpen?if_exists.thesisTopic?if_exists}
	    </td>
	    <td class="grayStyle">论文评阅编号<#if flag?exists&&flag=="book"><font color="red">*</font></#if></td>  
	    <td  class="brightStyle"><input type="hidden" name="annotateBook${idSeq}.serial" value="${annotateBook.serial?if_exists}">${annotateBook.serial?if_exists}
	    </td>
      </tr> 
      <tr class="darkColumn">
      	<td class="grayStyle"  id="f_thesisAppraise${idSeq}"><@bean.message key="filed.thesisAnnotate" /><#if flag?exists&&flag=="book"><font color="red">*</font></#if>：</td>  
	    <td  class="brightStyle" colspan="3">
	      <textarea name="annotateBook${idSeq}.thesisAppraise" style="width:100%;border:1 solid #000000;" cols="50" rows="6">${annotateBook?if_exists.thesisAppraise?if_exists}</textarea>
	    </td>      	 		
      </tr> 
      <tr class="darkColumn">
      	<td class="grayStyle"  id="f_reply${idSeq}">对作者可否参加论文答辩的意见<#if flag?exists&&flag=="book"><font color="red">*</font></#if>：</td>  
	    <td  class="brightStyle" colspan="3">
			<input type="radio" value="A" name='annotateBook${idSeq}.isReply' <#if annotateBook?if_exists.isReply?exists && annotateBook?if_exists.isReply?string=="A">checked</#if>>同意答辩&nbsp;
			<input type="radio" value="M" name='annotateBook${idSeq}.isReply' <#if annotateBook?if_exists.isReply?exists && annotateBook?if_exists.isReply?string=="M">checked</#if>>修改后答辩&nbsp;			
			<input type="radio" value="D" name='annotateBook${idSeq}.isReply' <#if annotateBook?if_exists.isReply?exists && annotateBook?if_exists.isReply?string=="D">checked</#if>>不同意答辩&nbsp;			
	    </td>
      </tr>
      <#if studentFlag?exists&&studentFlag=="doctor">
      <tr align="center">
      <td class="grayStyle" id="f_learningLevel${idSeq}" align="left">论文是否达到授予博士学位的学术水平<#if flag?exists&&flag=="book"><font color="red">*</font></#if></td>
      <td class="brightStyle" colspan="3" align="left">
      <input type="radio" value="A" name='annotateBook${idSeq}.evaluateIdea.learningLevel' <#if annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?string=='A'>checked</#if>>优秀
      <input type="radio" value="B" name='annotateBook${idSeq}.evaluateIdea.learningLevel' <#if annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?string=='B'>checked</#if>>良好
      <input type="radio" value="C" name='annotateBook${idSeq}.evaluateIdea.learningLevel' <#if annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?string=='C'>checked</#if>>一般
      <input type="radio" value="D" name='annotateBook${idSeq}.evaluateIdea.learningLevel' <#if annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?exists && annotateBook?if_exists.evaluateIdea?if_exists.learningLevel?string=='D'>checked</#if>>不及格
      </td>
      </tr>
      </#if>
      <tr class="darkColumn">
      	<td class="grayStyle" width="15%" id="f_name${idSeq}"><@bean.message key="filed.annotatePerson" /><#if flag?exists&&flag=="book"><font color="red">*</font></#if>：</td>  
	    <td  class="brightStyle">
	    	<input type="hidden" name="annotateBook${idSeq}.answerTem.id" value="${annotateBook?if_exists.answerTem?if_exists.id?if_exists}">
			<input type="text" name="annotateBook${idSeq}.answerTem.name" maxlength="30" style="width:100%;border:1 solid #000000;" value="${annotateBook?if_exists.answerTem?if_exists.name?if_exists}">
	    </td> 
      	<td class="grayStyle" width="15%" id="f_title${idSeq}"><@bean.message key="filed.annotateJob" /><#if flag?exists&&flag=="book"><font color="red">*</font></#if>：</td>  
	    <td  class="brightStyle" width="15%">
	    	<select name="annotateBook${idSeq}.answerTem.teacherTitle.id" style="width:100%;border:1 solid #000000;">
	    		<#list teacherTitles?if_exists?sort_by("code") as theacherTitle>
	    			<#if annotateBook?if_exists.answerTem?if_exists.teacherTitle?if_exists.id?if_exists?string==theacherTitle.id?string>
	    				<option value="${theacherTitle.id}" selected>${theacherTitle.name?if_exists}</option>
	    			<#else>
	    				<option value="${theacherTitle.id}">${theacherTitle.name?if_exists}</option>
	    			</#if>
	    		</#list>
	    	</select>
	    </td>	
      </tr>
      <tr class="darkColumn">
      	<td class="grayStyle"  id="f_depart${idSeq}"><@bean.message key="filed.annotateCompany" /><#if flag?exists&&flag=="book"><font color="red">*</font></#if>：</td>  
	    <td  class="brightStyle" colspan="3">
			<input type="text" name="annotateBook${idSeq}.answerTem.depart" style="width:100%;border:1 solid #000000;" value="${annotateBook.answerTem?if_exists.depart?if_exists}">
	    </td>
      </tr>
      <tr>
      		<td class="brightStyle" align="center" colspan="4">
      			<p align="left">专家签字或盖章:</p>
      			<p align="left">单位:<p align="right">年&nbsp&nbsp&nbsp&nbsp月&nbsp&nbsp&nbsp&nbsp日</p>
      		</td>
      </tr>
  </table>