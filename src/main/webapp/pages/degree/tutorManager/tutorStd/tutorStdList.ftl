<#include "/templates/head.ftl"/>
<script language="JavaScript" src="scripts/Menu.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<br><div align="center"> <font color="red"><@html.errors /></font></div>
<form name="pageGoForm" action="#" method="post" onsubmit="return false;">
  <table width="100%" border="0">
    <tr>
      <td  class="padding" style="height:22px;width:450px" align="left" valign="bottom">
       <img src="${static_base}/images/action/info.gif" align="top"/><B>
          <B><@bean.message key="filed.tutorList" /></B>
        </B>&nbsp;&nbsp;&nbsp
      </td>

      <td  class="padding" onclick="javascript:addTutor()" onmouseover="MouseOver(event)" onmouseout="MouseOut()" align="right">
          <img src="${static_base}/images/action/backward.gif" class="iconStyle" /><@bean.message key="system.button.add" />
      </td>      

      <td  class="padding" onclick="javascript:removeTutor()" onmouseover="MouseOver(event)" onmouseout="MouseOut()" align="right">
          <img src="${static_base}/images/action/backward.gif" class="iconStyle" alt="<@bean.message key="system.button.del" />" /><@bean.message key="system.button.del" />
      </td>
    </tr>
    <tr>
      <td  colspan="7" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
  </table>

  <input type="hidden" name="pageNo" value="1" />
  <table width="100%" align="center" class="listTable">
    <tr align="center" class="darkColumn">
      <td width="2%"align="center" >
         &nbsp;<input type="checkBox" onClick="toggleCheckBox(document.getElementsByName('tutorId'),event);">
      </td>
      <td width="15%"><@bean.message key="entity.college" /></td>
      <td width="15%"><@bean.message key="entity.specialityAspect" /></td>
      <td width="15%"><@bean.message key="filed.tutorAndMaster" /></td>
      <td width="10%"><@bean.message key="teacher.code" /></td>
      <td width="15%"><@bean.message key="attr.personName" /></td>
   	  <td width="10%"><@bean.message key="filed.researchTutor" /></td>
   	  <td width="10%"><@bean.message key="filed.leaderStdInstance" /></td>
   	  <td width="8%"><@bean.message key="postfix.basInfo" /></td>
    </tr>
    <#list tutors as tutor>
	  <#if tutor_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if tutor_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)"  align="center">
      <td align="center" bgcolor="#CBEAFF" >
        &nbsp;<input type="checkBox" name="tutorId" value="${tutor.id?if_exists}">
      </td> 
      <td>&nbsp;${tutor.department?if_exists.name?if_exists}</td>      	 
      <td>&nbsp;${tutor.secondSubject?if_exists.name?if_exists}</td>
      <td>&nbsp;${tutor.thirdSubject?if_exists.name?if_exists}</td>
      <td>&nbsp;${tutor.code?if_exists}</td>
      <td>&nbsp;${tutor.name?if_exists}</td>
	  <td>&nbsp;${tutor.tutorType?if_exists.name?if_exists}</td>
	  <td>&nbsp;<a href="tutorStdOperation.do?method=doStdList&tutorId=${tutor.id?if_exists}" target="_parent">&gt;&gt;</a></td> 
	  <td>&nbsp;<a href="tutorManager.do?method=doTutorInfo&tutorId=${tutor.id?if_exists}" target="_parent" >&gt;&gt;</a></td> 
    </tr>
    </#list>
   </form>
    <#include "/templates/newPageBar.ftl"/> 	   
  </table>
  
  </body>
<#include "/templates/foot.ftl"/>
<script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("tutorId")));
    }
    function pageGo(pageNo){  
        self.parent.pageGo(pageNo);
    }     
	function addTutor(){
		document.pageGoForm.action="tutorManager.do?method=loadTutorFromTeacher";
		document.pageGoForm.target="_parent";
		document.pageGoForm.submit();
	}
	function removeTutor(){	
     	confirmWithParam('tutorManager.do?method=delTutor','tutorId');   
	}
</script>