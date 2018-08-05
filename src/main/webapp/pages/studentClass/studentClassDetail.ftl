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
	   <table width="80%" align="center" class="listTable">
       <tr class="darkColumn">
	     <td align="center" colspan="8"><@bean.message key="adminClass.stdList"/></td>
	   </tr>
       <tr align="center" class="darkColumn">
         <td width="5%"><@bean.message key="attr.index"/></td>
  	     <td width="10%"><@bean.message key="attr.stdNo"/></td>
	     <td width="10%"><@bean.message key="attr.personName"/></td>
	     <td width="5%"><@bean.message key="attr.gender"/></td>
	     <td width="20%"><@bean.message key="entity.college"/></td>
	     <td width="20%"><@bean.message key="entity.studentType"/></td>
	     <td width="10%"><@msg.message key="entity.studentState"/></td>
	     <td width="10%"><@msg.message key="entity.isStudentStatusAvailable"/></td>
	   </tr>	  	
	   <#list (adminClass.students?sort_by("code"))?if_exists as student>
	   <#if student_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if student_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
   	    <td>&nbsp;${student_index+1}</td>
	    <td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}">${student.code?if_exists}</a></td>
	    <td>&nbsp;<@i18nName student?if_exists/></td>
	    <td>&nbsp;<@i18nName (student.basicInfo.gender)?if_exists/></td>
	    <td>&nbsp;<@i18nName (student.department)?if_exists/></td>
	    <td>&nbsp;<@i18nName (student.type)?if_exists/></td>
	    <td <#if !student.inSchool> style="color:red"</#if>>&nbsp;<@i18nName student.state?if_exists/></td>
	    <td>&nbsp;<#if student.isValid><@msg.message key="entity.available"/><#else><font color="red"><@msg.message key="entity.unavailable"/></font></#if></td>
	   </tr>
	   </#list>
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