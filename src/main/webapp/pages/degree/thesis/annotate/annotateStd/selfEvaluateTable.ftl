<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
  <table width="100%" align="center" class="listTable">   
	  <caption>
        <B>${student.name}<@bean.message key="filed.thesisbyMe" /></B>
      </caption>
        <form name="listForm" method="post" action="" onsubmit="return false;">
      <tr class="darkColumn">
      	<td  width="20%">&nbsp;论文题目:</td>  
	    <td  class="brightStyle" ><#if (thesisManage.thesis.name)?exists>${thesisManage.thesis.name}<#else>${(thesisManage.topicOpen.topicOpenName)?if_exists}</#if>
	    </td>      	 		
      </tr> 
      <tr class="darkColumn">
      	<td rowspan="7" >主要创新内容(不超过800字)<font color="red">*</font>：</td>
      </tr>      
	  <tr>
   		<td class="grayStyle" align="center" id="f_innovateOne">&nbsp;论文创新点(一)：</td>
	  </tr>
	  <tr>
	    <td class="brightStyle">
	    <#if disPlay>${(annotate.selfAnnotate.innovateOne)?if_exists}<#else>
	      <textarea name="annotate.selfAnnotate.innovateOne" cols="50" rows="3" style="width:100%;border:1 solid #000000;">${(annotate.selfAnnotate.innovateOne)?if_exists}</textarea>
	    </#if>
	    </td>	  
	  </tr>	   
	  <tr>
   		<td class="grayStyle" align="center" id="f_innovateTwo">&nbsp;论文创新点(二)：</td>
	  </tr>
	  <tr>
	    <td class="brightStyle">
	    <#if disPlay>${(annotate.selfAnnotate.innovateTwo)?if_exists}<#else>
	      <textarea name="annotate.selfAnnotate.innovateTwo" cols="50" rows="3" style="width:100%;border:1 solid #000000;">${(annotate.selfAnnotate.innovateTwo)?if_exists}</textarea>
	    </#if>
	    </td>	  
	  </tr>
	  <tr>
   		<td class="grayStyle" align="center" id="f_innovateThree">&nbsp;论文创新点(三)：</td>
	  </tr>
	  <tr>
	    <td class="brightStyle">
	    <#if disPlay>${(annotate.selfAnnotate.innovateThree)?if_exists}<#else>
	      <textarea name="annotate.selfAnnotate.innovateThree" cols="50" rows="3" style="width:100%;border:1 solid #000000;">${(annotate.selfAnnotate.innovateThree)?if_exists}</textarea>
	    </#if>
	    </td>	  
	  </tr>	
      <tr class="darkColumn">
      	<td id="f_deficiency">论文不足之处(缘由及改进方法)(不超过800字)：</td>  
	    <td  class="brightStyle" >
	    <#if disPlay>${(annotate.selfAnnotate.thesisLack)?if_exists}<#else>
	      <textarea name="annotate.selfAnnotate.thesisLack" cols="50" rows="6" style="width:100%;border:1 solid #000000;">${(annotate.selfAnnotate.thesisLack)?if_exists}</textarea>
	    </#if>
	    </td>      	 		
      </tr>
   	<tr>
	    <td colspan="2">
			  <table width="100%" align="center" class="listTable">
  				<tbody id='addGradeTable'>
  					<tr align="left">
  						<td class="grayStyle" colspan="5">学位论文相关的在校期间已公开发表的论文</td>
  					</tr>
    				<tr align="center" class="grayStyle">
    					<#if !disPlay>
      					    <@table.selectAllTd id="publishThesisId"/>
      					</#if>
      					<td width="15%"><@bean.message key="filed.thesisTitle" /></td>
      					<td width="15%"><@bean.message key="filed.publishName" /></td>
      					<td width="10%"><@bean.message key="filed.publishSequence" /></td>
      					<td width="7%"><@bean.message key="common.remark" /></td>
    				</tr>
    			<#if disPlay>
    				<#assign thesises=(annotate.publishThesisSet)?if_exists>
    			<#else>
    				<#assign thesises=stdyThesiss>
    			</#if>
    			<#list thesises?if_exists as publishThesis>
	  				<#if publishThesis_index%2==1 ><#assign class="grayStyle" ></#if>
	  				<#if publishThesis_index%2==0 ><#assign class="brightStyle" ></#if>
	    			<tr class="${class}" id="row${publishThesis_index}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)"  align="center" >
	    			    <#if !disPlay>
	    			    	<@table.selectTd id="publishThesisId" value=publishThesis.id/>
	    			    </#if>
      					<td>${publishThesis.name?if_exists}</td>
      					<td>${publishThesis.publicationName?if_exists}</td>
      					<td>${publishThesis.publicationNo?if_exists}</td>
      					<td>${publishThesis.remark?if_exists}</td>
	  			</#list>
	  		<#if !disPlay>
    		<tr><td class="brightStyle" colspan="5">
    			1.论文自评部分必须选择<font color="red">你发表的并且院系审核通过</font>的论文,如果没有可以选择的论文 请先到科研--我的科研里面添加.<br>
    			2.论文选项前面打勾说明这个论文在 论文盲评的时候会被参考
    		</td></tr>
    		</#if>
			</tbody>
  			</table>
	    </td>
      </tr>
      <input type="hidden" name="annotate.id" value="${(annotate.id)?if_exists}">
      <#if !disPlay>
      <tr class="contentTableTitleTextStyle">
	    <td colspan="4" align="center" >
	       	<button name="button9" onClick="stdSubmit(this.form)" class="buttonStyle"><@bean.message key="system.button.confirm" /></button>&nbsp;
	    </td>
	  </tr>
	   </#if>                           	                   
  </table>
  </form>
  <#if !disPlay>
  <script>
  	function getcheckIds(name){
  		 return(getCheckBoxValue(document.getElementsByName(name)));
  	}
  	function stdSubmit(form){
  	var a_fields = {        	  
         'annotate.selfAnnotate.innovateOne':{'l':'论文创新点(一)','r':true,'t':'f_innovateOne','mx':800},
         'annotate.selfAnnotate.innovateTwo':{'l':'论文创新点(二)','r':true,'t':'f_innovateTwo','mx':800},
         'annotate.selfAnnotate.innovateThree':{'l':'论文创新点(三)','r':true,'t':'f_innovateThree','mx':800},
         'annotate.selfAnnotate.thesisLack':{'l':'论文不足之处(缘由及改进方法)(不超过800字)','r':true,'t':'f_deficiency','mx':800}
         }
    var theisSeq = getcheckIds("publishThesisId");
     var v = new validator(form , a_fields, null);
    	if (v.exec()) {
    	    if(/^\s*$/.test(theisSeq)&&!confirm('你确定不选择发表的论文吗?\n发表的论文需要在"科研-我的科研"里面自己添加')){
     			return;
    		}
      		form.action="annotateStd.do?method=save";
      		addInput(form,"publicThesisSeq",theisSeq)
      		form.submit();    
     	}
  	}
  </script>
  </#if>