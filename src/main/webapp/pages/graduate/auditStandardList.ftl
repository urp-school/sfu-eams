<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 
 <table cellpadding="0" cellspacing="0" border="0" width="100%" >
   <tr>
    <td>
     <table width="90%" align="center">
      <TR>
       <TD>
        <a href="#" onClick="MM_changeSearchBarStyle('searchBar')">
         <span class="searchPicStyle"></span>
        </a>
       </TD>
      </TR>
     </table>
     <div id="searchBar" style="position:relative; visibility: hidden; display:none;"> 
     <form name="pageGoForm" method="post" action="searchAuditStandard.do" action="" onsubmit="return false;">
     <table width="90%" align="center" class="listTable">
      <tr> 
       <td colspan="2" align="center" class="darkColumn"><B><@bean.message key="attr.graduate.graduateAuditStandardSearch"/></B></td>
      </tr>
	  <#assign moduleName="AuditStandardSearch"/>
      <#assign studentTypeId = "auditStandard.studentType.id"/>
	  <#assign studentTypeDescriptions = "studentTypeDescriptions"/>
	  <#include "/pages/selector/studentTypeSelectorBarWithAuthority.ftl"/>
	  <tr>
	   <td colspan="2" align="center" class="darkColumn">
	    <input type="hidden" name="pageNo" value="1"/>
	    <input type="hidden" name="method" value="search"/>
	    <input type="button" onClick="search()" value="<@bean.message key="system.button.submit"/>" name="button1" class="buttonStyle"/>&nbsp;
	    <input type="reset" onClick="document.resetForm.submit()" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>
       </td>
	  </tr>
     </table>
     </div>
    </td>
   </tr>   
  </table>
  </form>
  
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="attr.graduate.graduateAuditStandardList"/></B>
    </td>
   </tr>   
   <tr>
    <td>
     <table width="90%" align="center" class="listTable">
       <form name="listForm" onSubmit="return false;">
	   <tr align="center" class="darkColumn">
	     <td width="12%"><@bean.message key="attr.name"/></td>	     
	     <td width="10%"><@bean.message key="entity.studentType"/></td>
	     <td><@bean.message key="attr.graduate.disauditCourseType"/></td>
	     <td>多出学分可冲抵任意选修课的课程类别</td>
	   </tr>
	   <#list (result.auditStandardList.items)?if_exists as auditStandard>
	   <#if auditStandard_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if auditStandard_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td>&nbsp;${auditStandard.name}</td>
	    <td>&nbsp;<@i18nName auditStandard.studentType?if_exists/></td>
	    <#assign courseTypeDescriptionsValue = ""/>
	    <#assign courseTypeIdValue = ","/>
	    <#list auditStandard.disauditCourseTypeSet?if_exists as courseType>	   
	   		<#if courseType_has_next>
			    <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
			    <#if courseType.name?exists>
			    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + ","/>
		    	</#if>
		   	<#else>
			   	<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
			    <#if courseType.name?exists>
			    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string />
			    </#if>
	   		</#if>
	   </#list>
	   <td>&nbsp;${courseTypeDescriptionsValue}</td>
	   <#assign courseTypeDescriptionsValue = ""/>
	    <#assign courseTypeIdValue = ","/>
	    <#list auditStandard.convertableCourseTypeSet?if_exists as courseType>	   
	   		<#if courseType_has_next>
			    <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
			    <#if courseType.name?exists>
			    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + ","/>
		    	</#if>
		   	<#else>
			   	<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
			    <#if courseType.name?exists>
			    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string />
			    </#if>
	   		</#if>
	   </#list>
	   <td>&nbsp;${courseTypeDescriptionsValue}</td>
	   </tr>
	   </#list>
	   </form>
	   <#assign paginationName="auditStandardList"/>
	   <#include "/templates/pageBar.ftl"/>
     </table>
    </td>
   </tr>
  </table>  
  
  <form name="resetForm" method="post" action="searchAuditStandard.do"> 
    <input type="hidden" name="method" value="search"/>
    <input type="hidden" name="pageNo" value="1"/>
  </form>
 </body>
 <script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("auditStandardId")));
    }
    
    function search(){
          document.pageGoForm.submit();
    }
    
    function pageGo(pageNo){
       document.pageGoForm.pageNo.value=pageNo;
       search();
    }
    
    function pageGoWithSize(pageNo,pageSize){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.action+="?pageSize="+pageSize;
       form.submit();
    }
 </script>
<#include "/templates/foot.ftl"/>