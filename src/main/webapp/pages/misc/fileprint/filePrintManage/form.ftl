<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body   valign="top">
<table id="filePrintFormBar"></table>
<script>
  var bar= new ToolBar("filePrintFormBar","耗材维护",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addBack();
  

</script>
<table width="100%" valign="top" class="formTable">
       <form name="filePrintMaterialForm"  action="filePrintManage.do?method=save" method="post" onsubmit="return false;">
	   <tr class="darkColumn">
	     <td align="center" colspan="4">耗材基本信息</td>
	   </tr>
	   <tr>
		   <td class="title" width="16%" id="f_name">&nbsp;耗材代码:</td>	     
		   <td><input type="text" name="filePrintMaterial.materialCode" value="${(filePrintMaterial.materialCode)?if_exists}" maxlength="20"/></td>
	 	   <td class="title" width="16%" id="f_name">&nbsp;耗材名称:</td> 	     
		   <td><input type="text" name="filePrintMaterial.materialName" value="${(filePrintMaterial.materialName)?if_exists}" maxlength="20"/></td>
	 	   <input type="hidden" value="${filePrintApplicationId?if_exists}" name="filePrintApplicationId"/>
	   </tr>
		<!--
	   <tr>
	     <td class="title" id="f_mode">&nbsp;收费方式:</td>	
     	 <td class="content">
       		<select id="mode" name="filePrintMaterial.mode.id" style="width:155px">	       
   	         	<#list modes as mode>
   	         	<option value="${mode.id}" <#if (filePrintMaterial.mode.id)?exists&&(filePrintMaterial.mode.id)==mode.id>selected</#if> ><@i18nName mode?if_exists/></option>
   	         	</#list>
       		</select>
     	 </td>
	     <td class="title" id="f_currencyCategory">&nbsp;货币类别:</td>	
     	 <td class="content">
       		<select id="currencyCategory" name="filePrintMaterial.currencyCategory.id" style="width:155px">	       
   	         	<#list currencyCategorys as currencyCategory>
   	         	<option value="${currencyCategory.id}" <#if (filePrintMaterial.currencyCategory.id)?exists&&(filePrintMaterial.currencyCategory.id)==currencyCategory.id>selected</#if> ><@i18nName currencyCategory?if_exists/></option>
   	         	</#list>
       		</select>
     	 </td>
       </tr>-->
       <tr>
	     <td class="title">&nbsp;耗材数量</td>
	     <td class="brightStyle" id="f_valueId">
	      <input type="text" name="filePrintMaterial.value" value="${(filePrintMaterial.value)?if_exists}" maxlength="20"/>
         </td>       
	     <td class="title">&nbsp;单价</td>
	     <td class="brightStyle" id="f_valueId">
	      <input type="text" name="filePrintMaterial.payedOne" value="${(filePrintMaterial.payedOne)?if_exists}" maxlength="20"/>
         </td>
	   </tr>
	   
	   <tr>
	     <td class="title" width="25%" id="f_auditAt">&nbsp;总费用</td>
	     <td colspan="3">${(filePrintMaterial.payed)?if_exists}</td>
	   </tr>
	   
	   <tr>
	     <td class="title">&nbsp;请印<@bean.message key="attr.remark"/></td>
	     <td class="brightStyle" colspan="3">
	      <textarea name="filePrintMaterial.remark" cols="25" style="width:100%" rows="10">${(filePrintMaterial.remark)?if_exists}</textarea>
         </td>
	   </tr>
	  
	   <tr class="darkColumn">
	   	 <td colspan="4" align="center">
	   	    <input type="hidden" name="filePrintMaterial.id" value="${(filePrintMaterial.id)?if_exists}">
	     	<input type="button" value="<@bean.message key="action.submit"/>" onclick="this.form.submit()" class="buttonStyle"/>
         	<input type="reset" value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>   
         </td>
	   </tr>
       </form>
	  </table>
 </body>