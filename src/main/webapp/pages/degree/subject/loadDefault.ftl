<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/prompt.js"></script> 
 
 <style  type="text/css">
 <!--
  .trans_msg
    {
    filter:alpha(opacity=100,enabled=1) revealTrans(duration=.2,transition=1) blendtrans(duration=.2);
    }
  -->
 </style>

<BODY LEFTMARGIN="0" TOPMARGIN="0">
<div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
<script>initToolTips()</script>
<br><div align="center"> <font color="red"><@html.errors /></font></div>
<form name="pageGoForm" action="subjectClass.do?method=doDefault" method="post" onsubmit="return false;">
  <table width="100%" align="center" class="listTable">  
    <tr align="center" class="darkColumn">    
      <td width="10%"><@bean.message key="filed.subjectKind" /></td>
      <td width="10%"><@bean.message key="filed.firstSubjectName" /></td>
      <td width="10%"><@bean.message key="filed.subjectCode" /></td>
      <td width="10%"><@bean.message key="filed.secondSubjectName" /></td>
   	  <td width="10%"><@bean.message key="filed.masterSetTime" /></td>
   	  <td width="10%"><@bean.message key="filed.tutorSetTime" /></td>
    </tr>
    <tr align="center" class="brightStyle">
      <td width="10%">
        <#if result.subjectClassCount?exists>
	      	${result.subjectClassCount}<@bean.message key="filed.number" />
	    </#if>  	
	  </td>
      <td width="10%">
        <#if result.firstCount?exists>
	      	${result.firstCount}<@bean.message key="filed.number" />
	    </#if>        
      </td>
      <td width="10%"></td>
      <td width="10%"></td>
   	  <td width="10%">
        <#if result.doctorCount?exists>
	      	${result.doctorCount}<@bean.message key="filed.number" />
	    </#if>    	  
   	  </td>
   	  <td width="10%">
        <#if result.masterCount?exists>
	      	${result.masterCount}个
	    </#if>    	  
   	  </td>
    </tr>    
    <#list result.subjectClassPage.items as subjectClass>
	  <#if subjectClass_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if subjectClass_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}"  align="center" onmouseover="swapOverTR(this,this.className);toolTip('<@bean.message key="filed.bitModify" />!','#000000', '#FFFF00')" onmouseout="swapOutTR(this);toolTip()"> 
      <td onClick="modifySubjectClass('${subjectClass.subjectCode?if_exists}')">&nbsp;${subjectClass.name?if_exists}</td>      	 
      <td colspan='5'>
		  <table width="100%" align="center" class="listTable">		  	
		  	<#list subjectClass.firstSubjectSet?if_exists as firstSubject>
 		  	<tr class="brightStyle" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
		  	  <td width="128" onClick="modifyFirstSubject('${firstSubject.firstCode?if_exists}')">
		  	  	&nbsp;${firstSubject.name?if_exists}${firstSubject.doctorFirst?if_exists?string("#","")}
		  	  </td>		
		  	  <td >
			     <table width="100%" align="center" class="listTable" >		  	  			      
			     	<#list firstSubject.secondSubjectSet?if_exists as secondSubject>
						<#if secondSubject_index%2==1 ><#assign class="grayStyle" ></#if>
						<#if secondSubject_index%2==0 ><#assign class="brightStyle" ></#if>	
						<#assign isFreedom=secondSubject.isFreedom?if_exists?string("*","")>	 	     	
						<tr onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" onClick="modifySecondSubject('${secondSubject.secondCode?if_exists}')">
				     		<td class="${class}" width="128">&nbsp;${secondSubject.secondCode?if_exists}</td>
				     		<td class="${class}" width="128">&nbsp;${secondSubject.name?if_exists}</td>
				     		<td class="${class}" width="130">&nbsp;
				     			<#if secondSubject.doctorDate?exists>
				     				${secondSubject.doctorDate}${isFreedom}
				     			</#if>
				     		</td>
				     		<td class="${class}" width="125">&nbsp;
				     			<#if secondSubject.masterDate?exists>
				     				${secondSubject.masterDate}${isFreedom}
				     			</#if>
				     		</td>
			     		</tr>	
			     	</#list>			      
			     </table>
		  	  </td>  
		  	</tr> 	  	  	    
		  	</#list>		  	  
      	  </table>	
      </td>
    </tr>
    </#list>
    <#assign paginationName="subjectClassPage" />
    <#include "/templates/pageBar.ftl"/> 	   
  </table>
  <div align="left">
  	<font color="red"><br>
  	&nbsp;&nbsp;&nbsp;&nbsp;<@bean.message key="filed.subject.memo1" /><br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;#<@bean.message key="filed.subject.memo2" />
	</font>
  </div>
</form>  
</body>
<#include "/templates/foot.ftl"/>
<#include "javaScript.ftl"/>
	