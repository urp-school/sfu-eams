<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <body >
 <#assign labInfo><@msg.message key="security.copyAuth"/></#assign>
 <#include "/templates/back.ftl">
     <table width="80%"  class="formTable" align="center">
      <form name="roleForm" action="role.do?method=copyAuth" method="post" onsubmit="return false;">
       <input type="hidden" name="fromRoleId" value="${fromRole.id}"/>
       <input type="hidden" name="toRoleIds" value=""/>
       <@searchParams/>
	   <tr class="darkColumn">
	     <td  colspan="2"><@msg.message key="security.copyAuth"/></td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name">&nbsp;<@msg.message key="security.fromRole"/>：</td>
	     <td >${fromRole.name}</td>
	   </tr>
	   
	   <tr>
	    <td class="title" id="f_studentType"><font color="red">*</font><@msg.message key="security.toRole"/>：</td>
	    <td >
	     <table>
	      <tr>
	       <td>
	        <select name="Roles" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Roles'], this.form['SelectedRole'])" >
	         <#list toRoles?sort_by('name') as role>
	          <option value="${role.id}"><@i18nName role/></option>
	         </#list>
	        </select>
	       </td>
	       <td  valign="middle">
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['Roles'], this.form['SelectedRole'])" type="button" value="&gt;"> 
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedRole'], this.form['Roles'])" type="button" value="&lt;"> 
	        <br>
	       </td> 
	       <td  class="normalTextStyle">
	        <select name="SelectedRole" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedRole'], this.form['Roles'])">
              
	        </select>
	       </td>             
	      </tr>
	     </table>
	    </td>
	   </tr>	   
	   <tr class="darkColumn" align="center">
	     <td colspan="6"  >
	       <button  onclick="copyAuth(this.form)"><@msg.message key="action.submit"/></button>&nbsp;
	       <input type="reset"  name="reset1" value="<@msg.message key="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
       </form>
     </table>
     <pre>
        拷贝权限实现的是差异拷贝。如果目标角色含有该模块，则不会覆盖拷贝。
        新拷贝模块的数据权限是依照角色配置的数据权限设置的。如果要修改该权限，可以在修改角色信息中进行设置。
     </pre>
  <script language="javascript" >
   function copyAuth(form){
     form['toRoleIds'].value = getAllOptionValue(form.SelectedRole);
     if(""==form['toRoleIds'].value){
        alert("<@msg.message "action.select"/>");
        return;
     }
     if(confirm("<@msg.message "common.confirmAction"/>")){
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>