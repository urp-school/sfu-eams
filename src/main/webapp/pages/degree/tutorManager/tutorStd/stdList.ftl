<#include "/templates/head.ftl"/>
</style>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<form name="pageGoForm" action="tutorStdOperation.do?method=doStdList" method="post" onsubmit="return false;">
  <input type="hidden" name="tutorId" value="${result.tutor?if_exists.id?if_exists}"> 
  <input type="hidden" name="pageNo" value="1" />
  <#assign labInfo>${result.tutor?if_exists.name?if_exists} <@bean.message key="filed.leader" /></#assign>
  <#include "/templates/back.ftl"/>
  <table width="100%" align="center" class="listTable">  
    <tr align="center" class="darkColumn">
      <td width="2%"align="center" >
         &nbsp;<input type="checkBox" onClick="toggleCheckBox(document.getElementsByName('studentId'),event);">
      </td>
      <td width="10%"><@bean.message key="adminClass.enrollYear" /></td>
      <td width="15%"><@bean.message key="entity.studentType" /></td>
      <td width="15%"><@bean.message key="entity.college" /></td>
      <td width="15%"><@bean.message key="entity.speciality" /></td>
      <td width="15%"><@bean.message key="attr.stdNo" /></td>
   	  <td width="10%"><@bean.message key="attr.personName" /></td>
    </tr>
    <#list students as student>
	  <#if student_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if student_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)"  align="center">
      <td align="center" bgcolor="#CBEAFF" >
        &nbsp;<input type="checkBox" name="studentId" value="${student.stdNo?if_exists}">
      </td> 
      <td>&nbsp;${student.enrollYear?if_exists}</td>      	 
      <td>&nbsp;${student.type?if_exists.name?if_exists}</td>
      <td>&nbsp;${student.department?if_exists.name?if_exists}</td>
      <td>&nbsp;${student.firstMajor?if_exists.name?if_exists}</td>
      <td>&nbsp;${student.stdNo?if_exists}</td>
	  <td>&nbsp;${student.name?if_exists}</td>
    </tr>
    </#list>
    </form>
    <#include "/templates/newPageBar.ftl"/> 	   
  </table>
 <br> <br>

 <table width="65%" align="center" class="listTable">
  <form name="addStdForm" method="post" action="tutorStdOperation.do?method=doAddStdToTutor">
       <input type="hidden" name="tutorId" value="${result.tutor?if_exists.id?if_exists}"> 
	   <tr>
	    <td align="center" class="darkColumn" colspan=2>
	     <B><@bean.message key="filed.addStd" /></B>
	    </td>
	   </tr>        
	   <tr>
	     <td class="grayStyle" width="10%" id="f_teacherName">
	      &nbsp;<@bean.message key="field.user.rankId1" />：
	     </td>
	     <td class="brightStyle">
	      <input type="hidden" name="studentIds" />
	      <textarea name="studentNames" cols="40" rows="2" onfocus="this.blur();">${studentNames?if_exists}</textarea>
	      &nbsp;
	      <input type="button" value="<@bean.message key="filed.stdList" />" name="button4" class="buttonStyle"
	            onClick="window.open('examUtilAction.do?method=loadStdSelector', '', 'scrollbars=yes,width=550,height=550,status=yes')"  />
         </td>
	   </tr>
	   <tr>
	    <td width="75%" class="darkColumn" colspan=2 align="center">
	      <input type="submit" value="<@bean.message key="system.button.add" />" name="button1" class="buttonStyle" />&nbsp;
	      <input type="hidden" name="pageNo" value="1" />
	    </td>
	   </tr>	   
  </form>    
 </table>

  </body>
<#include "/templates/foot.ftl"/>
<script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("studentId")));
    }
    function pageGo(pageNo){
       var form = document.pageGoForm;
       form.action="tutorStdOperation.do?method=doStdList";
       form.pageNo.value = pageNo;
       form.submit();
    }    
	function setStudentIdAndDescriptions(ids, descriptions, flag){
        setSelectorIdAndDescriptions(ids, descriptions, 'studentIds', 'studentNames', flag);
    }
    
    function delStd(){
     	confirmWithParam('tutorStdOperation.do?method=doDelStdFromTutor&tutorId=${result.tutor?if_exists.id?if_exists}','studentId');   
    }
</script>