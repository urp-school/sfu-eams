<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
 <BODY onblur="self.focus();" onload="selfAdapte();" LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td colspan="3" align="center" height="5"></td>
   </tr>   
   <tr>
    <td align="center" colspan="3" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="filed.stdTypeList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center">&nbsp;</td>
	     <td><@bean.message key="filed.stdTypeName"/></td>
	     <td><@bean.message key="attr.nameEn"/></td>
	   </tr>	   
	   <#list result.studentTypeList?if_exists?sort_by('name') as studentType>
	   <#if studentType_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if studentType_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}">
	    <script>
	      detailArray['${studentType.id}'] = {'name':'${studentType.name}'};
	    </script>
	    <td width="5%" align="center" bgcolor="#CBEAFF">
	     <input type="radio" name="studentTypeId" value="${studentType.id}" <#if RequestParameters.studentTypeId?exists&&RequestParameters.studentTypeId==studentType.id?string>checked</#if>>
	    </td>
	    <td width="40%">&nbsp;${studentType.name}</td>
	    <td width="35%">&nbsp;${studentType.engName?if_exists}</td>
	   </tr>
	   </#list>
	   </form>
     </table>
    </td>
   </tr>
   <tr><td height="5"></td></tr>
   <tr>
    <td align="center" colspan="3" >
     <input type="button" name="button1" value="<@bean.message key="action.confirm"/>" class="buttonStyle"
            onClick="window.top.opener.setStudentTypeIdAndDescriptions(getId('studentTypeId'),getName(getId('studentTypeId')));
                     window.close();"  />&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="reset" name="reset" value="<@bean.message key="action.reset"/>" class="buttonStyle" onClick="window.top.opener.resetStudentTypeSelector();
                     window.close();"/>
    </td>  
   </tr>
  </table>
  <script>
     function selfAdapte(){       	
   	   window.self.moveTo((screen.width-400)/3, (screen.height-(8*30+150))/5);   	    
   	   window.self.resizeTo(400, screen.height*(3/4));
     }
  </script>
 </body>
<#include "/templates/foot.ftl"/>