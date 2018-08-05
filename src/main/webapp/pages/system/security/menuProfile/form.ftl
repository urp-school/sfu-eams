<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>

 <link href="${static_base}/css/tableTree.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript" src="scripts/common/TableTree.js"></script>
<script> defaultColumn=1;</script>

 <body>
 <#assign labInfo><@msg.message key="info.moduleUpdate"/></#assign>  
 <#include "/templates/back.ftl"/> 
   <form name="moduleForm" action="menuProfile.do?method=save" method="post">
   <@searchParams/>
   <input type="hidden" name="menuProfile.id" value="${menuProfile.id?if_exists}" style="width:200px;" />
   <input type="hidden" name="menuProfileIds" />
   <tr>
    <td>
     <table width="70%" class="formTable" align="center">
	   <tr class="darkColumn">
	     <td  colspan="2"><@msg.message key="info.module.detail"/></td>
	   </tr>
	   
	   <tr>
	     <td class="title" width="25%" id="f_name">&nbsp;名称<font color="red">*</font>：</td>
	     <td >
          <input type="text" name="menuProfile.name" value="${menuProfile.name?if_exists}" style="width:200px;" />     
         </td>
	   </tr>
	  <tr>
     		<td class="title" width="25%" id="f_category">&nbsp;<@msg.message key="attr.figure" /><font color="red">*</font>：</td>
     		<td>
     		<select  name="menuProfile.category.id" style="width:100px;" >
	     		<#list categories as category>
	     			<option value="${category.id}"  <#if menuProfile.category?exists&&menuProfile.category.id==category.id>checked</#if>>${category.name}</option>
	      		</#list>
		  	</select>
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
   function save(form){
     var a_fields = {
     	 'menuProfile.name':{'l':'<@msg.message key="attr.name"/>', 'r':true,'t':'f_name'},
     	 'menuProfile.category.id':{'l':'<@msg.message key="attr.figure"/>', 'r':true,'t':'f_category'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>