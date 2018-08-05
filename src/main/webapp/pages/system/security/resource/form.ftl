<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <body>
 <#assign labInfo><@msg.message key="security.resource.info"/></#assign>  
<#include "/templates/back.ftl"/> 
   <form name="moduleForm" action="resource.do?method=save" method="post">
   <input type="hidden" name="resource.id" value="${(resource.id)?if_exists}" style="width:200px;" />
   <input type="hidden" name="patternIds" value=""/>
   <tr>
    <td>
     <table width="80%" class="formTable" align="center">
	   <tr class="darkColumn">
	     <td  colspan="2"><@msg.message key="security.resource.info"/></td>
	   </tr>
	   <tr>
	     <td class="title" id="f_title">标题<font color="red">*</font>：</td>
	     <td >
          <input type="text" name="resource.title" value="${resource.title?if_exists}" style="width:200px;"/>
         </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name"><@msg.message key="attr.name"/><font color="red">*</font>：</td>
	     <td >
          <input type="text" name="resource.name" value="${resource.name?if_exists}" style="width:200px;"/>
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_description"><@msg.message key="attr.description"/>：</td>
	     <td >
	        <textarea cols="30" rows="2" name="resource.description">${resource.description?if_exists}</textarea>
         </td>
	   </tr>
		<tr>
	   	  <td class="title"><@msg.message "attr.status"/>：</td>
	   	  <td><select  name="resource.enabled" style="width:100px;" >
		   		<option value="true" <#if (resource.enabled?exists)&&(resource.enabled==true)>selected</#if>><@msg.message "action.activate"/></option>
		   		<option value="false" <#if (resource.enabled?exists)&&(resource.enabled==false)>selected</#if>><@msg.message "action.freeze"/></option>
		  </select>
		</td>
		</tr>
		 <tr>
	     <td class="title">适用用户：</td>
	     <td>
	      <#list categories as category>
	      <input name="categoryIds" value="${category.id}" type="checkbox" <#if resource.categories?seq_contains(category)>checked</#if>/>${category.name}
	      </#list>
	   </tr>
		<tr>
	    <td class="title" >数据限制模式:</td>
	    <td >
	     <table>
	      <tr>
	       <td>
	        <select name="Patterns" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Patterns'], this.form['SelectedPattern'])" >
	         <#list patterns?sort_by('name') as pattern>
	          <option value="${pattern.id}">${pattern.name}</option>
	         </#list>
	        </select>
	       </td>
	       <td  valign="middle">
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['Patterns'], this.form['SelectedPattern'])" type="button" value="&gt;"> 
	        <br><br>
	        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedPattern'], this.form['Patterns'])" type="button" value="&lt;"> 
	        <br>
	       </td> 
	       <td  class="normalTextStyle">
	        <select name="SelectedPattern" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedPattern'], this.form['Patterns'])">
	         <#list resource.patterns?if_exists as pattern>
	          <option value="${pattern.id}">${pattern.name}</option>
	         </#list>
	        </select>
	       </td>
	      </tr>
	     </table>
	    </td>
	   </tr>
	   <tr class="darkColumn" align="center">
	     <td colspan="6" >
	       <input type="button" value="<@msg.message key="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />
	       <input type="reset"  name="reset1" value="<@msg.message key="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
     </table>
    </td>
   </tr>
   </form>
  </table>
  <script language="javascript" >
   function save(form){
     form['patternIds'].value = getAllOptionValue(form.SelectedPattern);  
     var a_fields = {
         'resource.title':{'l':'标题', 'r':true, 't':'f_title'},
         'resource.name':{'l':'<@msg.message key="attr.name"/>', 'r':true,'t':'f_name'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>