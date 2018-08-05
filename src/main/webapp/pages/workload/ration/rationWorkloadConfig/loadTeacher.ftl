<#include "/templates/head.ftl"/>
 <script>
    var detailArray = {};
    
    function getIds(){
       return(getRadioValue(document.getElementsByName("teacherId")));
    }
    
    function getName(id){
       if (id != ""){
          return detailArray[id]['name'];
       }else{
          return "";
       }       
    }
	function pageGo(pageNo){
       document.pageGoForm.pageNo.value = pageNo;
       document.pageGoForm.submit();
    }
 </script>
 <BODY onblur="self.focus();" LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">  
   <tr>
    <td>
     <#assign labInfo><@bean.message key="field.questionnaireStatistic.teacherList"/></#assign>
     <#include "/templates/back.ftl"/>
    </td>
   </tr>
   <tr>
   		<td height="20px;">
   		</td>
   </tr>  
   <tr>
    <td>
     <table width="100%" align="center" class="listTable">
       <form name="listForm" action="" onsubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center">&nbsp;</td>
	     <td><@bean.message key="field.questionnaireStatistic.teacherName"/></td>
	     <td><@bean.message key="field.questionnarieStatistic.teacherOfDepart"/></td>
	   </tr>
	   <#if result.teacherPagi?exists>	   
	   <#list result.teacherPagi.items?if_exists as teacher>
	   <#if teacher_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if teacher_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}">
	    <script>
	       detailArray['${teacher.id}'] = {'name':'${teacher.name}'};
	    </script>
	    <td width="5%" align="center" bgcolor="#CBEAFF">
	     <input type="radio" name="teacherId" value="${teacher.id}"/>
	    </td>
	    <td width="40%">&nbsp;${teacher.name}</td>
	    <td width="40%">&nbsp;${teacher.department.name}</td>
	   </tr>
	   </#list>
	   </form>
	   <form name="pageGoForm" method="post" action="" onsubmit="return false;">
	    <#assign paginationName="teacherPagi" />
	    <#include "/templates/pageBar.ftl"/>
	    </form> 
	   </#if>
     </table>
    </td>
   </tr>
   <tr><td height="10"></td></tr>
   <tr>
    <td align="center" colspan="3" >
     <input type="button" name="button1" value="<@bean.message key="action.confirm"/>" class="buttonStyle"
            onClick="window.top.opener.setTeacherIdAndDescriptions(getIds(),getName(getIds()));
                     window.close();"  />&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" name="button2" value="<@bean.message key="field.evaluate.clear"/>" class="buttonStyle" 
            onClick="window.top.opener.setTeacherIdAndDescriptions('', '');
                     window.close();"/>
    </td>
   </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>