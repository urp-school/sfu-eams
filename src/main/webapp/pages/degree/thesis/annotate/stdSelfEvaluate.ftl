  <table width="100%" align="center" class="listTable">   
	  <caption>
        <B>${student.name?if_exists} <@bean.message key="filed.thesisbyMe" /></B>
      </caption>
      <tr class="darkColumn">
      	<td  width="20%"  id="f_thesisName">&nbsp;论文题目<font color="red">*</font>：</td>  
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
	      <textarea name="annotate.selfAnnotate.innovateOne" cols="50" rows="3" style="width:100%;border:1 solid #000000;">${thesisAnnotate?if_exists.selfAnnotate?if_exists.innovateOne?if_exists}</textarea>
	    </td>	  
	  </tr>	   
	  <tr>
   		<td class="grayStyle" align="center" id="f_innovateTwo">&nbsp;论文创新点(二)：</td>
	  </tr>
	  <tr>
	    <td class="brightStyle">
	      <textarea name="annotate.selfAnnotate.innovateTwo" cols="50" rows="3" style="width:100%;border:1 solid #000000;">${thesisAnnotate?if_exists.selfAnnotate?if_exists.innovateTwo?if_exists}</textarea>
	    </td>	  
	  </tr>
	  <tr>
   		<td class="grayStyle" align="center" id="f_innovateThree">&nbsp;论文创新点(三)：</td>
	  </tr>
	  <tr>
	    <td class="brightStyle">
	      <textarea name="annotate.selfAnnotate.innovateThree" cols="50" rows="3" style="width:100%;border:1 solid #000000;">${thesisAnnotate?if_exists.selfAnnotate?if_exists.innovateThree?if_exists}</textarea>
	    </td>	  
	  </tr>	
      <tr class="darkColumn">
      	<td id="f_deficiency">论文不足之处(缘由及改进方法)(不超过800字)：</td>  
	    <td  class="brightStyle" >
	      <textarea name="annotate.selfAnnotate.thesisLack" cols="50" rows="6" style="width:100%;border:1 solid #000000;">${thesisAnnotate?if_exists.selfAnnotate?if_exists.thesisLack?if_exists}</textarea>
	    </td>      	 		
      </tr>
   	<tr>
	    <td colspan="2">
			<#include "publishThesis.ftl"/>
	    </td>
      </tr>
      <tr class="contentTableTitleTextStyle">
	    <td colspan="4" align="center" >
	      <input type="hidden" name="pageNo" value="1" />
	      <input type="hidden" name="thesisManageId" value="${thesisManage?if_exists.id?if_exists}">
	      <#if flag?exists&&flag=="std"&&thesisManage?if_exists.id?exists>    
	       		<input type="button" value="<@bean.message key="system.button.confirm" />" name="button9" onClick="stdSubmit(this.form)" class="buttonStyle"/>&nbsp;
	      <#elseif flag?exists&&(flag=="admin"||flag=="tutor")><#else>没有参与论文开题,不能填写论文评阅书</#if>
	    </td>
	  </tr>	                           	                   
  </table>
  <script>	
  	function getcheckIds(name){
  		 return(getCheckBoxValue(document.getElementsByName(name)));
  	}
  	function stdSubmit(form){
  	var a_fields = {        	  
         'annotate.selfAnnotate.innovateOne':{'l':'论文创新点(一)','t':'f_innovateOne','mx':800},
         'annotate.selfAnnotate.innovateTwo':{'l':'论文创新点(二)','t':'f_innovateTwo','mx':800},
         'annotate.selfAnnotate.innovateThree':{'l':'论文创新点(三)','t':'f_innovateThree','mx':800},
         'annotate.selfAnnotate.thesisLack':{'l':'论文不足之处(缘由及改进方法)(不超过800字)','t':'f_deficiency','mx':800}
         }
    var theisSeq = getcheckIds("publishThesisId");
    if(/^\s*$/.test(theisSeq)&&!confirm('你确定不选择发表的论文吗?\n发表的论文需要在"科研-我的科研"里面自己添加')){
     	return;
     }
     var v = new validator(form , a_fields, null);
    	if (v.exec()) {
      		form.action='annotateStd.do?method=thesisOperation&publicThesisSeq='+theisSeq;
      		form.submit();    
     	}
  	}
  	<#if successString?exists&&successString=="1">
  		alert("操作成功");
  	</#if>
  </script>