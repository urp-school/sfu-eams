<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <body>
 <#assign labInfo><#if user.name?exists><@msg.message key="action.modify"/><#else><@msg.message key="action.new"/></#if> <@msg.message key="entity.user"/></#assign>
 <#include "/templates/back.ftl">
  
 <table width="100%"  class="formTable">
   <form name="userForm" action="user.do?method=save" method="post">
   <@searchParams/>
   <tr class="darkColumn">
     <td  colspan="2"><@msg.message key="info.user"/></td>
   </tr>
   <tr>
     <td class="title" width="15%" id="f_name"><font color="red">*</font><@msg.message key="attr.loginName"/>：</td>
     <td >
     <#if user.id?exists>
      <input type="hidden" name="user.name" value="${user.name?if_exists}"/> ${user.name?if_exists}
     <#else>
      <input type="text" name="user.name" value="${user.name?if_exists}" style="width:300px;" maxLength="30"/>         
     </#if>
     </td>
   </tr>
   <tr>
     <td class="title" id="f_userName"><font color="red">*</font>真实姓名：</td>
     <td ><input type="text" name="user.userName" value="${user.userName?if_exists}" style="width:300px;" maxLength="60" /></td>
   </tr>
   <tr>
     <td class="title" id="f_email"><font color="red">*</font><@msg.message key="attr.email"/>：</td>
     <td ><input type="text" name="user.email" value="${user.email?if_exists}" style="width:300px;" maxLength="70" /></td>
   </tr>
   <tr>
    <td class="title" id="f_role"><font color="red">*</font><@msg.message key="entity.role"/>：</td>
    <td >
     <table>
      <tr>
       <td>
        <select name="Roles" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Roles'], this.form['SelectedRole'])" >
         <#list mngRoles?sort_by('name') as role>
          <option value="${role.id}">${role.name}</option>
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
         <#list user.roles?if_exists as role>
          <option value="${role.id}">${role.name}</option>
         </#list>
        </select>
       </td>             
      </tr>
     </table>
    </td>
   </tr>
   <tr>
     <td class="title">&nbsp;<font color="red">*</font><@msg.message key="attr.figure" />：</td>
     <td>
      <#list categories as category>
      <input name="categoryIds" value="${category.id}" type="checkbox" <#if user.categories?seq_contains(category)>checked</#if>/>${category.name}
      </#list>
   </tr>
  
   <tr>
     <td class="title" id="f_defaultCategory"><font color="red">*</font>默认<@msg.message key="attr.figure" />：</td>
     <td>
      <#list categories as category>
      <input name="user.defaultCategory.id" value="${category.id}" type="radio" <#if (user.defaultCategory?exists)&&(user.defaultCategory.id==category.id)>checked</#if>/>${category.name}
      </#list>
   </tr>
   
   <tr>
     <td id="f_remark" class="title">&nbsp;<@msg.message key="attr.remark"/>：</td>
     <td><textarea cols="50" rows="3" name="user.remark">${user.remark?if_exists}</textarea></td>
   </tr>
   <tr>
     <td id="f_state" class="title">&nbsp;<font color="red">*</font><@msg.message key="attr.status" />：</td>
     <td>
        <select name="user.state" style="width:100px">
           <option value="1" <#if user.state?default(1)?string=="1">selected</#if>><@msg.message key="action.activate" />
           <option value="2" <#if user.state?default(1)?string=="2">selected</#if>><@msg.message key="action.freeze" />&nbsp;&nbsp;
        </select>
     </td>
   </tr>
   <#if user.id?exists>
   <tr>
     <td id="f_state" class="title">&nbsp;</td>
     <td><a href="#" onClick="editRestriction(${user.id?if_exists})">数据级权限</td>
   </tr>
   </#if>
   <tr class="darkColumn" align="center">
     <td colspan="6"  >
       <input type="hidden" name="user.id" value="${user.id?if_exists}" />
       <input type="hidden" name="roleIds" />
       <input type="button" value="<@msg.message key="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />&nbsp;
       <input type="reset"  name="reset1" value="<@msg.message key="action.reset"/>" class="buttonStyle" />
     </td>
   </tr>  
  </form>
 </table>
  <script language="javascript" >
  
   function editRestriction(holderId){
	  window.open('restriction.do?method=info&forEdit=1&restrictionType=user&restriction.holder.id='+holderId, 'new', 'toolbar=no,top=250,left=250,location=no,directories=no,statue=no,menubar=no,width=400,height=400');
   }
   
   function save(form){
     form.roleIds.value = getAllOptionValue(form.SelectedRole);  
     var a_fields = {
         'user.email':{'l':'<@msg.message key="attr.email" />', 'r':true, 'f':'email', 't':'f_email'},
         'user.name':{'l':'<@msg.message key="attr.loginName"/>', 'r':true, 't':'f_name'},
         'user.state':{'l':'<@msg.message key="attr.status" />', 'r':true, 't':'f_state'},
         'user.userName':{'l':'真实姓名', 'r':true, 't':'f_userName'},
         'user.defaultCategory.id':{'l':'默认<@msg.message key="attr.figure" />', 'r':true, 't':'f_defaultCategory'},
         'user.remark':{'l':'<@msg.message "common.remark"/>','r':false,'t':'f_remark','mx':80}
     };

     var v = new validator(form, a_fields, null);
     if (v.exec()) {
     	var cIds = getSelectIds("categoryIds");
        if(""==getSelectIds("categoryIds")){
           alert("请选择身份");return;
        }
        var arr = cIds.split(",");
        var defaultValue = getSelectIds("user.defaultCategory.id");
        var isIn = false;
        for(var i=0;i<arr.length;i++){
  			if(defaultValue==arr[i]){
  				isIn=true;
  				break;
  			}
  		}
  		if(!isIn){
  			alert("默认身份必须在所选身份中！");
  			return ;
  		}
  		
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>