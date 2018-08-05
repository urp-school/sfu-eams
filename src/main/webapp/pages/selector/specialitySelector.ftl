<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
 <BODY onblur="self.focus();" onload="selfAdapte();" LEFTMARGIN="0" TOPMARGIN="0">
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td colspan="3" align="center" height="5"></td>
   </tr>   
   <tr>
    <td align="center" colspan="3" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="attr.selector.specialityList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" action="" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td align="center">&nbsp;</td>
	     <td><@bean.message key="attr.code"/></td>
	     <td><@bean.message key="attr.selector.speciality.name"/></td>
	   </tr>	   
	   <#list result.specialitList?if_exists as speciality>
	   <#if speciality_index%2==1 ><#assign class="grayStyle"></#if>
	   <#if speciality_index%2==0 ><#assign class="brightStyle"></#if>
	   <tr class="${class}">
	    <script>
	       detailArray['${speciality.id}'] = {'name':'${speciality.name}'};
	    </script>
	    <td width="5%" align="center" bgcolor="#CBEAFF">
	     <input type="radio" name="specialityId" value="${speciality.id}" <#if RequestParameters.specialityId?exists&&RequestParameters.specialityId==speciality.id?string>checked</#if> />
	    </td>
	    <td>&nbsp;${speciality.code}</td>
	    <td>&nbsp;<@i18nName speciality/></td>
	   </tr>
	   </#list>
	   </form>
     </table>
    </td>
   </tr>
   <tr><td height="5"></td></tr>
   <tr>
    <td align="center" colspan="3">
     <input type="button" name="button1" value="<@bean.message key="system.button.confirm" />" class="buttonStyle"
            onClick="window.top.opener.setSpecialityIdAndDescriptions(getId('specialityId'),getName(getId('specialityId')));try{window.top.opener.resetSpecialityAspectsSelector();}catch(e){};
                     window.close();"/>&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="reset" name="reset" value="<@bean.message key="system.button.reset" />" class="buttonStyle" 
            onClick="window.top.opener.resetSpecialitySelector();
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