<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Menu.js"></script>
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

<form name="pageGoForm" action="tutorAspect.do?method=doSearch" method="post" onsubmit="return false;">
  <input type="hidden" name="isNew" value="" />
  <input type="hidden" name="pageNo" value="1" />
  <#include "head.ftl">
  <table width="100%" align="center" class="listTable">  
    <tr align="center" class="darkColumn">
      <td width="2%"align="center" >
         &nbsp;<input type="checkBox" onClick="toggleCheckBox(document.getElementsByName('thirdSubjectId'),event);">
      </td>
      <td width="10%"><@bean.message key="filed.subjectKind" /></td>
      <td width="15%"><@bean.message key="filed.firstSubject" /></td>
      <td width="15%"><@bean.message key="filed.secondSubject" /></td>
      <td width="15%"><@bean.message key="filed.tutorAndMaster" /></td>
    </tr>
    <#list result.thirdSubjectPage.items as thirdSubject>
	  <#if thirdSubject_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if thirdSubject_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" onmouseover="swapOverTR(this,this.className);toolTip('<@bean.message key="filed.bitModify" />！','#000000', '#FFFF00')" onmouseout="swapOutTR(this);toolTip()"  align="center">
      <td align="center" bgcolor="#CBEAFF" >
        &nbsp;<input type="checkBox" name="thirdSubjectId" value="${thirdSubject.thirdCode?if_exists}">
      </td> 
      <td onClick="modifyThirdSubject('${thirdSubject.thirdCode}')">&nbsp;${thirdSubject.secondSubject?if_exists.firstSubject?if_exists.subjectClass?if_exists.name?if_exists}</td>      	 
      <td onClick="modifyThirdSubject('${thirdSubject.thirdCode}')">&nbsp;${thirdSubject.secondSubject?if_exists.firstSubject?if_exists.name?if_exists}</td>
      <td onClick="modifyThirdSubject('${thirdSubject.thirdCode}')">&nbsp;${thirdSubject.secondSubject?if_exists.name?if_exists}</td>
      <td onClick="modifyThirdSubject('${thirdSubject.thirdCode}')">&nbsp;${thirdSubject.name?if_exists}</td>
    </tr>
    </#list>
    </form>
    <#assign paginationName="thirdSubjectPage" />
    <#include "/templates/pageBar.ftl"/> 	   
  </table>
  </body>
<#include "/templates/foot.ftl"/>
<script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("thirdSubjectId")));
    }
    function pageGo(pageNo){
       self.parent.pageGo(pageNo);
    }        
    function addThirdSubject(){
    	document.pageGoForm.action='tutorAspect.do?method=loadThirdSubjectOperation';
    	document.pageGoForm.target='_parent';
    	document.pageGoForm.isNew.value = 'true';
    	document.pageGoForm.submit();
    }
   
    function modifyThirdSubject(){
		gotoWithSingleParam('tutorAspect.do?method=loadThirdSubjectOperation&isNew=false','thirdSubjectId'); 
    }
    function modifyThirdSubject(thirdCode){
    	document.pageGoForm.action='tutorAspect.do?method=loadThirdSubjectOperation&isNew=false&thirdSubjectId='+thirdCode;
    	document.pageGoForm.target='_parent';
    	document.pageGoForm.isNew.value = 'false';   	
    	document.pageGoForm.submit();
    }
     
    function delThirdSubject(){
     	confirmWithParam('tutorAspect.do?method=doDel','thirdSubjectId');       	
    }
    
</script>