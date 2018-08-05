<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" style="overflow:auto;">
	<table id="bar"></table>
  <#if result.adminClasses?exists&&(result.adminClasses?size > 0)>
  <#list (result.adminClasses?sort_by("code"))?if_exists as adminClass>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">   
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="std.adminClass.title"/></B>
    </td>
   </tr>
   <tr>
    <td>
     <table width="80%" align="center" class="listTable">
       <tr class="darkColumn">
	     <td align="center" colspan="4"><@bean.message key="std.adminClass.baseInfo.title"/></td>
	   </tr>
       <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="std.adminClass.baseInfo.name"/>：</td>
	    <td class="brightStyle">&nbsp;<@i18nName adminClass?if_exists/></td>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="std.adminClass.baseInfo.code"/>：</td>
	    <td class="brightStyle">&nbsp;${(adminClass.code)?if_exists}</td>
       </tr>
       <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.college"/>：</td>
	    <td class="brightStyle">&nbsp;<@i18nName (adminClass.department)?if_exists/></td>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.studentType"/>：</td>
	    <td class="brightStyle">&nbsp;<@i18nName (adminClass.stdType)?if_exists/></td>
       </tr>
       <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.speciality"/>：</td>
	    <td class="brightStyle">&nbsp;<@i18nName (adminClass.speciality)?if_exists/></td>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="entity.specialityAspect"/>：</td>
	    <td class="brightStyle">&nbsp;<@i18nName (adminClass.aspect)?if_exists/></td>
       </tr>
       <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="std.adminClass.baseInfo.createOn"/>：</td>
	    <td class="brightStyle">&nbsp;${(adminClass.dateEstablished)?if_exists}</td>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="std.adminClass.baseInfo.enrollYear"/>：</td>
	    <td class="brightStyle">&nbsp;${(adminClass.enrollYear)?if_exists}</td>
       </tr>
       <tr>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="std.adminClass.baseInfo.planStudents"/>：</td>
	    <td class="brightStyle">&nbsp;${(adminClass.planStdCount)?if_exists}</td>
	    <td class="grayStyle" width="25%">&nbsp;<@bean.message key="std.adminClass.baseInfo.numberOfStudents"/>：</td>
	    <td class="brightStyle">&nbsp;${(adminClass.actualStdCount)?if_exists}</td>
       </tr>
      </table>
    </td>
   </tr>
  </table>
  </#list>
  <#else>
  	<table cellpadding="0" cellspacing="0" width="100%" border="0">
   		<tr>
    		<td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     		<B><@bean.message key="error.model.notExist"/></B>
    		</td>
   		</tr>
   	</table>
  </#if>
  <script>
  	var bar = new ToolBar("bar","<@bean.message key="std.adminClass.title"/>",null,true,true);
  	bar.setMessage('<@getMessage/>');
  	bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
  </script>
</body>
<#include "/templates/foot.ftl"/>