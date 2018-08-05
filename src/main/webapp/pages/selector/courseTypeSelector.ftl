<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
 <BODY onblur="self.focus();" onload="selfAdapte();" LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td colspan="3" align="center" height="5"></td>
   </tr>   
   <tr>
    <td align="center" colspan="3" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="entity.courseType"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center"><input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('courseTypeId'),event);"></td>
	     <td><@bean.message key="filed.chineseName"/></td>
	     <td><@bean.message key="filed.englishNName"/></td>
	   </tr>	   
	   <#list result.courseTypeList?if_exists as courseType>
	   <#if courseType_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if courseType_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}">
	    <script>
	      detailArray['${courseType.id}'] = {'name':'${courseType.name}'};
	    </script>
	    <td width="5%" align="center" bgcolor="#CBEAFF">
	    <#if RequestParameters.courseTypeId?exists>
	    	<#assign flag = RequestParameters.courseTypeId?index_of(','+courseType.id?string+',') />
	    </#if>
	    <input type="checkbox" name="courseTypeId" value="${courseType.id}" <#if flag?exists&&(flag >= 0)>checked</#if>>
	    </td>
	    <td width="40%">&nbsp;${courseType.name}</td>
	    <td width="35%">&nbsp;${courseType.engName?if_exists}</td>
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
            onClick="window.top.opener.setCourseTypeIdAndDescriptions${RequestParameters.showCourseType?if_exists}(getIds('courseTypeId'),getNames(getIds('courseTypeId')));
                     window.close();"  />&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="reset" name="reset" value="<@bean.message key="action.reset"/>" class="buttonStyle" onClick="window.top.opener.resetCourseTypeSelector${RequestParameters.showCourseType?if_exists}();
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