<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <body>
 <#assign labInfo><@msg.message key="info.moduleUpdate"/></#assign>  
 <#include "/templates/back.ftl"/> 
   <form name="moduleForm" action="menu.do?method=save" method="post">
   <@searchParams/>
   <input type="hidden" name="menu.id" value="${menu.id?if_exists}" style="width:200px;" />
   <input type="hidden" name="resourceIds" />
   <tr>
    <td>
     <table width="70%" class="formTable" align="center">
	   <tr class="darkColumn">
	     <td  colspan="2"><@msg.message key="info.module.detail"/></td>
	   </tr>
	   <tr>
	    <td class="title">菜单配置<font color="red">*</font></td>
	    <td><select  name="menu.profile.id" style="width:150px;" >
	        <#list profiles as profile>
	        <option value="${profile.id}" <#if (menu.profile.id)?default(0) ==profile.id>selected</#if>>${profile.name}</optino>
	        </#list>
	        </select>
	    </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_id">&nbsp;<@msg.message key="attr.id"/><font color="red">*</font>：</td>
	     <td >
	          <input id="menu_code" type="text" name="menu.code" value="${menu.code?if_exists}" style="width:200px;" onKeyUp="value=validCode(value);"/> 数字    
         </td>
	   </tr>	   
	   <tr>
	     <td class="title" width="25%" id="f_title">&nbsp;标题<font color="red">*</font>：</td>
	     <td >
          <input type="text" name="menu.title" value="${menu.title?if_exists}" style="width:200px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_engTitle">英文标题<font color="red">*</font>：</td>
	     <td >
          <input type="text" name="menu.engTitle" value="${menu.engTitle?if_exists}" style="width:200px;" />     
         </td>
	   </tr>
		<tr>
	   	  <td class="title" width="25%" id="f_name">&nbsp;<@msg.message "attr.status"/>：</td>
	   	  <td><select  name="menu.enabled" style="width:100px;" >
		   		<option value="true" <#if (menu.enabled?exists)&&(menu.enabled==true)>selected</#if>><@msg.message "action.activate"/></option>
		   		<option value="false" <#if (menu.enabled?exists)&&(menu.enabled==false)>selected</#if>><@msg.message "action.freeze"/></option>
		  </select>
		</td>
		</tr>
	   <tr>
	     <td class="title" width="25%" id="f_entry">&nbsp;入口：</td>
	     <td><input type="text" name="menu.entry" value="${menu.entry?if_exists}"/></td>
	   </tr>
	   
	   <tr>
	    <td class="title" width="25%" id="f_resources">使用资源：</td>
	    <td >
	     <table>
	      <tr>
	       <td>
	        <select name="Resources" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Resources'], this.form['SelectedResource'])" >
	        	<#list resources?sort_by("title") as resource>
	       			<option value="${resource.id}">
	       				${resource.title?if_exists}-${resource.name?if_exists}
	       			</option>
	       		</#list>
	        </select>
	       </td>
	       <td  valign="middle">
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['Resources'], this.form['SelectedResource'])" type="button" value="&gt;"> 
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedResource'], this.form['Resources'])" type="button" value="&lt;"> 
	        <br>
	       </td> 
	       <td  class="normalTextStyle">
	        <select name="SelectedResource" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedResource'], this.form['Resources'])">
	         	<#list menu.resources as resource>
	       			<option value="${resource.id}" <#if (menu.resources?seq_contains(resource))>selected</#if>>
	       				${resource.title?if_exists}-${resource.name?if_exists}
	       			</option>
	       		</#list>
	        </select>
	       </td>             
	      </tr>
	     </table>
	    </td>
	   </tr>
   
   
	   <tr>
	     <td class="title" width="25%" id="f_description">&nbsp;<@msg.message key="attr.description"/>：</td>
	     <td >
	        <textarea cols="30" rows="3" name="menu.description">${menu.description?if_exists}</textarea>
         </td>
	   </tr>
	   <tr class="darkColumn" align="center">
	     <td colspan="6"  >
	       <input type="button" value="<@msg.message key="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="reset1" value="<@msg.message key="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
     </table>
    </td>
   </tr>
   </form>
  </table>
  <script language="javascript" >
   	function validCode(codeValue){
		return codeValue.replace(/[^\d]/g,'')
	}
   function save(form){
    form.resourceIds.value = getAllOptionValue(form.SelectedResource);  
     var a_fields = {
     	 'menu.code':{'l':'<@msg.message key="attr.id"/>', 'r':true,'t':'f_id'},
         'menu.title':{'l':'标题', 'r':true, 't':'f_title'},
         'menu.engTitle':{'l':'英文标题', 'r':true, 't':'f_engTitle'}
     };
     
     var codeValue=document.getElementById("menu_code").value;
      var le = codeValue.length;
     if(le%2!=0){
          alert("代码必须为双数位!");
          return;
     }
     
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>