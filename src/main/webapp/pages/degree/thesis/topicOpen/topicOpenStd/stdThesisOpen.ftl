<#if "info"==topicOpenInfo><#assign isDisplay=true><#else><#assign isDisplay=false></#if>
<table  align="center" class="listTable" width="100%" id="listTable">
<form name="<#if formName?exists>#{formName}<#else>topicOpenInfoForm</#if>" method="post" onsubmit="return false;" action="" onsubmit="return false;">
<tr class="darkColumn">
      	<td class="darkColumn" align="center" id="f_thesisTopic" width="10%"><@bean.message key="filed.thesisTitle"/><br></td>
	    <td  class="brightStyle" colspan="4">
	      <#if isDisplay>${(topicOpen.thesisTopic)?if_exists}<#else>
	      <textarea name="topicOpen.thesisTopic" cols="65" rows="1" style="width:100%;border1 solid #000000;">${(topicOpen.thesisTopic)?if_exists}</textarea>
	      </#if>
	    </td>
      </tr>
      <tr>
      		<td class="darkColumn" align="center" id="f_thesisType">论文类型
      		<td class="brightStyle" colspan="4">
      		<#if isDisplay>${(topicOpen.thesisType.name)?if_exists}<#else>
      		 <@htm.i18nSelect datas=thesisTypes?sort_by("id") selected=(topicOpen.thesisType.id)?default(0)?string name="topicOpen.thesisType.id" style="width:100%">
      		       <option value="">请选择</opiton>
      		 </@>
      		</#if>
      </tr>
	  <tr class="darkColumn">
	    <td align="center" rowspan="2" >选题情况</td>
	    <td align="center" class="grayStyle"  id="f_taskLevel">选题来源</td>
	    <td align="center" class="grayStyle"  id="f_projectTaskName">课题名称</td>
	    <td align="center" class="grayStyle"  id="f_passCompany">批准单位</td>
	    <td align="center" class="grayStyle"  id="f_outlaySource"><@bean.message key="filed.moeyFrom" /></td>
	  </tr>
	  <tr>
	    <td class="brightStyle" width="20%">
		  <#if isDisplay>${(topicOpen.taskSource.thesisSourse.name)?if_exists}<#else>
		  <@htm.i18nSelect datas=thesisSources?sort_by("id") selected=(topicOpen.taskSource.thesisSourse.id)?default(0)?string name="topicOpen.taskSource.thesisSourse.id" style="width:100%">
		  <option value="">请选择</opiton>
      	  </@>
		  </#if>
	    </td>
	    <td class="brightStyle" >
	     <#if isDisplay>${(topicOpen.taskSource.projectTaskName)?if_exists}<#else>
	      <textarea name="topicOpen.taskSource.projectTaskName" cols="10" rows="3" style="width:100%;solid #000000;">${(topicOpen.taskSource.projectTaskName)?if_exists}</textarea>
	    </#if>
 	    </td>
	    <td class="brightStyle">
	    <#if isDisplay>${(topicOpen.taskSource.passCompany)?if_exists}<#else>
	      <textarea name="topicOpen.taskSource.passCompany" cols="10" rows="3" style="width:100%;solid #000000;">${(topicOpen.taskSource.passCompany)?if_exists}</textarea>
	   </#if>
	    </td>
	    <td  class="brightStyle">
	    <#if isDisplay>${(topicOpen.taskSource.outlaySource)?if_exists}<#else>
	      <textarea name="topicOpen.taskSource.outlaySource" cols="10" rows="3" style="width:100%;solid #000000;">${(topicOpen.taskSource.outlaySource)?if_exists}</textarea>
	    </#if>
	    </td>
	  </tr> 
      <tr class="darkColumn">
      	<td rowspan="16"  align="center">内容</td>
      </tr>
      <tr>
      	<td class="grayStyle" align="center" colspan="4" id="f_selectTopicKeyWord">论文主题词</td>
      </tr>
      <tr>
	    <td class="brightStyle" colspan="4">
	    <#if isDisplay>${(topicOpen.content.keyWords)?if_exists}<#else>
	      <textarea name="topicOpen.content.keyWords" cols="65" rows="3" style="width:100%;border1 solid #000000;">${(topicOpen.content.keyWords)?if_exists}</textarea>
	    </#if>
	    </td>	  
	  </tr>
	  
	  <tr>
   		<td class="grayStyle" align="center" colspan="4" id="f_meaning">选题的理论意义与实用价值论文主题</td>
	  </tr>
	  <tr>
	    <td class="brightStyle" colspan="4">
	    <#if isDisplay>${(topicOpen.content.meaning)?if_exists}<#else>
	      <textarea name="topicOpen.content.meaning" cols="65" rows="3" style="width:100%;border1 solid #000000;">${(topicOpen.content.meaning)?if_exists}</textarea>
	    </#if>
	    </td>	  
	  </tr>
	  
      <tr class="darkColumn">
      	<td class="grayStyle" align="center" colspan="4" id="f_researchActuality">国内外研究现状</td>  
	  </tr>  
	  <tr>
	    <td class="brightStyle" colspan="4">
	    <#if isDisplay>${(topicOpen.content.researchActuality)?if_exists}<#else>
	      <textarea id="testId" name="topicOpen.content.researchActuality" cols="65" rows="5" style="width:100%;border1 solid #000000;">${(topicOpen.content.researchActuality)?if_exists}</textarea>
	    </#if>
	    </td>      	 		
      </tr>
	            
      <tr class="darkColumn">
      	<td class="grayStyle" align="center" colspan="4"  id="f_thinkingAndMethod">研究的基本思路与方法</td>  
	  </tr>
	  <tr>  
	    <td  class="brightStyle" colspan="4" >
	    <#if isDisplay>${(topicOpen.content.thinkingAndMethod)?if_exists}<#else>
	      <textarea name="topicOpen.content.thinkingAndMethod" cols="65" rows="5" style="width:100%;border1 solid #000000;">${(topicOpen.content.thinkingAndMethod)?if_exists}</textarea>
	    </#if>
	    </td>      	 		
      </tr>
       
      <tr class="darkColumn">
      	<td class="grayStyle" align="center" colspan="4"  id="f_innovate">论文的创新点</td>  
	  </tr>
	  <tr>  
	    <td class="brightStyle" colspan="4">
	    <#if isDisplay>${(topicOpen.content.innovate)?if_exists}<#else>
	      <textarea name="topicOpen.content.innovate" cols="65" rows="3" style="width:100%;border1 solid #000000;">${(topicOpen.content.innovate)?if_exists}</textarea>
	    </#if>
	    </td>      	 		
      </tr>
      <tr class="darkColumn">
      	<td class="grayStyle" align="center" colspan="4"  id="f_expectHarvest">预期成果</td>  
	  </tr>
	  <tr>  
	    <td class="brightStyle" colspan="4">
	    <#if isDisplay>${(topicOpen.content.expectHarvest)?if_exists}<#else>
	      <textarea name="topicOpen.content.expectHarvest" cols="65" rows="3" style="width:100%;border1 solid #000000;">${(topicOpen.content.expectHarvest)?if_exists}</textarea>
	    </#if>
	    </td>      	 		
      </tr>
      
      <tr class="darkColumn">
      	<td class="grayStyle" align="center" colspan="4"  id="f_researchArea">研究内容范围</td>  
	  </tr>
	  <tr>  
	    <td class="brightStyle" colspan="4">
	    <#if isDisplay>${(topicOpen.content.researchArea)?if_exists}<#else>
	      <textarea name="topicOpen.content.researchArea" cols="65" rows="3" style="width:100%;border1 solid #000000;">${(topicOpen.content.researchArea)?if_exists}</textarea>
	    </#if>
	    </td>      	 		
      </tr>
      	<td class="grayStyle" align="center" colspan="2" id="f_wordNumber">论文大约字数</td>
      	<td class="brightStyle" colspan="2" >
      	<#if isDisplay>${(topicOpen.content.aboutThesisNumber)?if_exists}<#else>
      	<input type="text" id="aboutThesisNumber" maxlength="6" name="topicOpen.content.aboutThesisNumber" style="width:80px"  value="${(topicOpen.content.aboutThesisNumber)?if_exists}">
      	</#if>
      	万字
      <tr>
      <tr class="darkColumn">
      	<td align="center" id="f_literature">查阅主要文献</td>
	    <td class="brightStyle" colspan="4">
	       <#if isDisplay>${(topicOpen.referrenceLiterature)?if_exists}<#else>
	      <textarea name="topicOpen.referrenceLiterature" cols="65" rows="8" style="width:100%;">${(topicOpen.referrenceLiterature)?if_exists}</textarea>
	      </#if>
	    </td>
      </tr>
      <#if "doctor"==stdType>
      <tr>
	  	<td class="darkColumn" rowspan="6" align="center">论文<@bean.message key="filed.planArrange" />
	  	<td class="grayStyle" id="f_thesisPlan">研究阶段开始时间
	  	<td class="brightStyle" colspan="3">
	  	<#if isDisplay><#if (topicOpen.thesisPlan.researchTime)?exists>${topicOpen.thesisPlan.researchTime?string("yyyy-MM-dd")}</#if><#else>
	  		<input type="text"  name="topicOpen.thesisPlan.researchTime" maxlength="10" value="<#if (topicOpen.thesisPlan.researchTime)?exists>${topicOpen.thesisPlan.researchTime?string("yyyy-MM-dd")}</#if>" onfocus="calendar()">
		</#if>	  
	  </tr>
	  <tr>
	  	<td class="grayStyle" id="f_writeTime">论文开始写作时间
	  	<td class="brightStyle" colspan="3">
	  	<#if isDisplay><#if (topicOpen.thesisPlan.thesisWriteTime)?exists>${topicOpen.thesisPlan.thesisWriteTime?string("yyyy-MM-dd")}</#if><#else>
	  	<input type="text" maxlength="10" name="topicOpen.thesisPlan.thesisWriteTime" value="<#if (topicOpen.thesisPlan.thesisWriteTime)?exists>${topicOpen.thesisPlan.thesisWriteTime?string("yyyy-MM-dd")}</#if>" onfocus="calendar()">
	  	</#if>
	  </tr>
	  <tr>
	  	<td class="grayStyle" id="f_finishTime">论文定稿时间
	  	<td class="brightStyle" colspan="3">
	  	<#if isDisplay><#if (topicOpen.thesisPlan.thesisFinishTime)?exists>${topicOpen.thesisPlan.thesisFinishTime?string("yyyy-MM-dd")}</#if><#else>
	  	<input type="text" maxlength="10" name="topicOpen.thesisPlan.thesisFinishTime" value="<#if (topicOpen.thesisPlan.thesisFinishTime)?exists>${(topicOpen.thesisPlan.thesisFinishTime)?string("yyyy-MM-dd")}</#if>" onfocus="calendar()">
		</#if>	  
	  </tr>
	  <tr>
	  	<td class="grayStyle" id="f_beforeAnswerTime">论文预答辩时间
	  	<td class="brightStyle" colspan="3">
	  	<#if isDisplay><#if (topicOpen.thesisPlan.rehearseAnswerTime)?exists>${topicOpen.thesisPlan.rehearseAnswerTime?string("yyyy-MM-dd")}</#if><#else>
	  	<input type="text" maxlength="10" name="topicOpen.thesisPlan.rehearseAnswerTime" value="<#if (topicOpen.thesisPlan.rehearseAnswerTime)?exists>${(topicOpen.thesisPlan.rehearseAnswerTime)?string("yyyy-MM-dd")}</#if>" onfocus="calendar()">
	 	</#if> 
	  </tr>
	  <tr>
	  	<td class="grayStyle" id="f_biBlindTime">论文双盲评审时间
	  	<td class="brightStyle" colspan="3">
	  	<#if isDisplay><#if (topicOpen.thesisPlan.biBlindTime)?exists>${topicOpen.thesisPlan.biBlindTime?string("yyyy-MM-dd")}</#if><#else>
	  	<input type="text" maxlength="10" name="topicOpen.thesisPlan.biBlindTime" value="<#if (topicOpen.thesisPlan.biBlindTime)?exists>${(topicOpen.thesisPlan.biBlindTime)?string("yyyy-MM-dd")}</#if>" onfocus="calendar()">
	    </#if>
	  </tr>
	  <tr>
	  	<td class="grayStyle" id="f_answerTime">论文答辩时间
	  	<td class="brightStyle" colspan="3">
	  	<#if isDisplay><#if (topicOpen.thesisPlan.thesisAnswerTime)?exists>${topicOpen.thesisPlan.thesisAnswerTime?string("yyyy-MM-dd")}</#if><#else>
	  	<input type="text"  name="topicOpen.thesisPlan.thesisAnswerTime" value="<#if (topicOpen.thesisPlan.thesisAnswerTime)?exists>${topicOpen.thesisPlan.thesisAnswerTime?string("yyyy-MM-dd")}</#if>" onfocus="calendar()">
	    </#if>
	  </tr>
      <#elseif "master"==stdType>
      <tr class="darkColumn">
      	<td align="center" id="f_fare">进度安排情况</td>
	    <td class="brightStyle" colspan="4">
	    <#if isDisplay>${(topicOpen.fare)?if_exists}<#else>
	      <textarea name="topicOpen.fare" cols="65" rows="8" style="width:100%;">${(topicOpen.fare)?if_exists}</textarea>
	    </#if>
	    </td>
      </tr>
	  <tr>
	  	<td class="darkColumn" id="f_answerTime">论文答辩时间
	  	<td class="brightStyle" colspan="4">
	  	<#if isDisplay><#if (topicOpen.thesisPlan.thesisAnswerTime)?exists>${topicOpen.thesisPlan.thesisAnswerTime?string("yyyy-MM-dd")}</#if><#else>
	  	<input type="text"  name="topicOpen.thesisPlan.thesisAnswerTime" value="<#if (topicOpen.thesisPlan.thesisAnswerTime)?exists>${topicOpen.thesisPlan.thesisAnswerTime?string("yyyy-MM-dd")}</#if>" onfocus="calendar()">
	    </#if>
	    </td>
	  </tr>
	  <#elseif "undergraduate" == stdType>
	  <tr>
	  	<td class="grayStyle" id="f_answerTime">论文答辩时间
	  	<td class="brightStyle" colspan="5">
	  	<#if isDisplay><#if (topicOpen.thesisPlan.thesisAnswerTime)?exists>${topicOpen.thesisPlan.thesisAnswerTime?string("yyyy-MM-dd")}</#if><#else>
	  	<input type="text"  name="topicOpen.thesisPlan.thesisAnswerTime" value="<#if (topicOpen.thesisPlan.thesisAnswerTime)?exists>${topicOpen.thesisPlan.thesisAnswerTime?string("yyyy-MM-dd")}</#if>" onfocus="calendar()"/>
	    </#if>
	  </tr>
      </#if>
      <#if (topicOpen.finishOn)?exists>
      <tr>
      <td class="darkColumn">
      	  论文开题提交时间
      </td>
      <td colspan="4">${topicOpen.finishOn?string("yyyy-MM-dd")}</td>
      </tr>
      </#if>
      <#if !isDisplay>
      <tr>
	     <td colspan="5" align="center" class="darkColumn">
	     		<input type="hidden" name="topicOpen.finishOn" value="${curDate?string("yyyy-MM-dd")}">
	       		<button name="button9" onClick="doAction(this.form)" class="buttonStyle">提交</button><#if "1"==(topicOpen.isPassed)?default(0)?string>你的论文开题已经通过</#if>
         </td>
	   </tr>
	   </#if>
	 </table>
	 <br>
      <#if (topicOpen.downloadName)?exists>
      <table class="listTable" class="notPrint" width="100%" >
      <tr >
      	<td class="darkColumn" class="notPrint">
      	  论文开题报告附件下载
      	</td>
      	<td colspan="4">
      		<a href="#" onclick="thesisDownLoad(${topicOpen.id})">论文开题报告附件<img class="icon" src="${static_base}/images/action/paperclip.gif"></a>
      	</td>
      </tr>
      </table>
      </#if>
	 <script>
	       function thesisDownLoad(topicId){
	            var form=document.<#if formName?exists>#{formName}<#else>topicOpenInfoForm</#if>;
	       		form.action="loadThesisTopic_std.do?method=doDownloadThesis";
	       		addInput(form,"topicId",topicId);
	       		form.submit();
	       }
	 </script>