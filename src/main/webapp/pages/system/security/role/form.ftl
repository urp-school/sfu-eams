<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <body >
 <#assign labInfo>角色详细信息</#assign>
 <#include "/templates/back.ftl">
     <table width="80%"  class="formTable" align="center">
      <form name="roleForm" action="role.do?method=save" method="post">
       <@searchParams/>
	   <tr class="darkColumn">
	     <td  colspan="2"><@msg.message key="info.role"/></td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name"><@msg.message key="attr.name"/><font color="red">*</font>：</td>
	     <td >
	     <#if role.id?exists>
          <input type="hidden" name="role.name" value="${role.name?if_exists}"/>${role.name?if_exists}      
         <#else>
          <input type="text" name="role.name" value="${role.name?if_exists}" style="width:300px;" />   
         </#if>
         </td>
	   </tr>
	   <tr>
	   	  <td class="title" ><@msg.message "attr.status"/>：</td>
	   	  <td><select  name="role.enabled" style="width:100px;" >
		   		<option value="1" <#if role.enabled>selected</#if>><@msg.message "action.activate"/></option>
		   		<option value="0" <#if !role.enabled>selected</#if>><@msg.message "action.freeze"/></option>
		  </select>
		</td>
	   </tr>
	   <tr>
	   	  <td class="title" >适用身份：</td>
	   	  <td><select  name="role.category.id" style="width:100px;" onchange="isSelectStdCategory(this);">
		   		<#list categories as category>
		   		<option value="${category.id}" <#if category.id=(role.category.id)?default(0)>selected</#if>>${category.name}</option>
		   		</#list>
		   	  </select>
		  </select>
		   <select  name="role.stdType.id" style="width:100px;visibility:<#if role.category?exists&&role.category.id==STDCATEGORYID>visible;<#else>hidden;</#if>" id="role_stdType">
     			<option value="" >请选择类型</option>
	     		<#list stdTypes as stdType>
	     			<option value="${stdType.id}" <#if role.stdType?exists&&role.stdType.id==stdType.id>selected</#if>>${stdType.name}</option>
	      		</#list>
		  	</select>
		</td>
	   </tr>
	   <tr>
	     <td class="title" id="f_remark"><@msg.message key="attr.description"/><font color="red">*</font>：</td>
	     <td >
	        <textarea cols="30" rows="2" name="role.remark">${role.remark?if_exists}</textarea>
         </td>
	   </tr>
	    <#if role.id?exists>
	    <tr>
		     <td id="f_state" class="title">&nbsp;</td>
		     <td><a href="#" onClick="editRestriction(${role.id})">数据级权限</td>
		</tr>
		</#if>
	   <tr class="darkColumn" align="center">
	     <td colspan="6">
	       <input type="hidden" name="role.id" value="${role.id?if_exists}" />
	       <input type="button" value="<@msg.message key="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />
	       <input type="reset"  name="reset1" value="<@msg.message key="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
       </form>
     </table>
  <script language="javascript" >
  	function isSelectStdCategory(selectedTag){
  		var role_stdType = document.getElementById("role_stdType");
  		if(${STDCATEGORYID}==selectedTag.value){
  			role_stdType.style.visibility='visible';
  		}else{
  			role_stdType.style.visibility='hidden';
  		}
  	}
  
	function editRestriction(holderId){
	   	window.open('restriction.do?method=info&forEdit=1&restrictionType=role&restriction.holder.id='+holderId, 'new', 'toolbar=no,top=250,left=250,location=no,directories=no,statue=no,menubar=no,width=400,height=400');
	}
	   
   function save(form){
     //form['role.dataRealm.studentTypeIdSeq'].value = getAllOptionValue(form.SelectedStudentType);  
     //form['role.dataRealm.departmentIdSeq'].value = getAllOptionValue(form.SelectedDepartment);  
     var a_fields = {
         'role.name':{'l':'<@msg.message key="attr.name"/>', 'r':true, 't':'f_name'}        
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>