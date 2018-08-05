<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
   <script language="javascript" >
   function doAction(form){     
     var a_fields = {
          'teachCategory.code':{'l':'<@bean.message key="field.teachCategory.teachCategoryCode"/>', 'r':true, 't':'f_code', 'mx':10},
          'teachCategory.name':{'l':'<@bean.message key="field.teachCategory.teachCategoryName"/>', 'r':true, 't':'f_name', 'mx':100},
          'teachCategory.studentType.id':{'l':'<@bean.message key="action.select"/><@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'},
          'teachCategory.remark':{'l':'<@bean.message key="field.evaluate.remark"/>', 't':'f_remark', 'mx':250},
          'teachCategory.status':{'l':'<@bean.message key="field.question.estate"/>', 'r':true, 't':'f_estate'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        <#if result.teachCategory?exists>
        form.action="teachCategoryAction.do?method=doUpdate";
        <#else>
     	form.action="teachCategoryAction.do?method=doAdd";
     	</#if>
        form.submit();
     }
   }
  </script>
 <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" method="post" action="" onsubmit="return false;">
   <tr>
   		<td><#assign labInfo>
   			<#if result.teachCategory?exists>
   			<@bean.message key="field.teachCategory.updateTeachCategory"/>
   			<#else>
   			<@bean.message key="field.teachCategory.addTeachCategory"/>
   			</#if>
   			</#assign>
   			<#include "/templates/back.ftl"/>
   		</td>
   </tr>
   <tr>
    <td>
     <table width="100%" align="center" class="listTable">
          <tr align="center" class="darkColumn">
    		<td colspan="4" align="center"><B>${labInfo}</B></td>
   		  </tr>		
		   <tr>
		     <td class="grayStyle" id="f_code">&nbsp;<@bean.message key="field.teachCategory.teachCategoryCode"/><font color="red">*</font>:</td>
		     <td class="brightStyle">
		     	    <input type="text" name="teachCategory.code" value="<#if result.teachCategory?exists>${result.teachCategory.code}</#if>" style="width:100px;">
	         </td>
	         <td class="grayStyle" id="f_name">&nbsp;<@bean.message key="field.teachCategory.teachCategoryName"/><font color="red">*</font>:</td>
		     <td class="brightStyle">
		     	    <input type="text" name="teachCategory.name" value="<#if result.teachCategory?exists>${result.teachCategory.name}</#if>" style="width:100px;">
	         </td>
		   </tr>
		   <tr>
		     <td class="grayStyle"  id="f_studentType">&nbsp;<@bean.message key="action.select"/><@bean.message key="entity.studentType"/><font color="red">*</font>:</td>
		     <td class="brightStyle">
		     	    <select name="teachCategory.studentType.id" style="width:200px;">
		     	    	<#list result.studentTypeList?if_exists as studentType>
		     	    		<#if result.teachCategory?exists&&studentType.id==result.teachCategory.studentType.id>
		     	    			<option value="${studentType.id}" selected>${studentType.name}</option>
		     	    		<#else>
		     	    			<option value="${studentType.id}">${studentType.name}</option>
		     	    		</#if>
		     	    	</#list>
		     	    </select>
	         </td>
	         <td class="grayStyle"  id="f_courseType">&nbsp;<@bean.message key="action.select"/><@bean.message key="common.courseType"/>:</td>
		     <td class="brightStyle">
		     	    <select name="teachCategory.courseType.id" style="width:200px;">
		     	    		<option value="0"></option>
		     	    	<#list result.courseTypeList?if_exists as courseType>
		     	    		<#if result.teachCategory?exists&&result.teachCategory.courseType?exists&&courseType.id==result.teachCategory.courseType.id>
		     	    			<option value="${courseType.id}" selected>${courseType.name}</option>
		     	    		<#else>
		     	    			<option value="${courseType.id}">${courseType.name}</option>
		     	    		</#if>
		     	    	</#list>
		     	    </select>
	         </td>
		   </tr>
		   <tr>
		     <td id="f_estate" class="grayStyle">&nbsp;<@bean.message key="field.question.estate"/><font color="red">*</font>:</td>
		     <td class="brightStyle" align="center">
		       <input type="radio" name="teachCategory.status" value="false"><@bean.message key="field.evaluate.estate0"/>&nbsp;&nbsp;
		       <input type="radio" name="teachCategory.status" value="true" ><@bean.message key="field.evaluate.estate1"/>
		     </td>
		     <script>
	          var selectedStatus = <#if result.teachCategory?exists>'${result.teachCategory.status?string}'<#else>'true'</#if>;
	          var status=document.getElementsByName('teachCategory.status');
	          for(var i=0; i<2; i++){
	             if (status[i].value == selectedStatus){
	                 status[i].checked=true;
	             }
	          }          
	       </script>
	       <td class="grayStyle"  id="f_remark">&nbsp;<@bean.message key="field.evaluate.remark"/></td>
		     <td class="brightStyle">
		     	<textarea name="teachCategory.remark" rows="5" cols="30" style="width:250px;"><#if result.teachCategory?exists>${result.teachCategory.remark?if_exists}</#if></textarea>
	         </td>
		   </tr>
		   <tr class="darkColumn">
		     <td colspan="4" align="center" >
		       <#if result.teachCategory?exists>
		       <input type="hidden" name="teachCategoryId" value="${result.teachCategory.id}">
		       <input type="hidden" name="flag" value="update">
		       <input type="hidden" name="teachCategory.makeTime" value="${result.teachCategory.makeTime?string("yyyy-MM-dd")}">
		       </#if>
		       <input type="button" value="<#if result.teachCategory?exists><@bean.message key="action.update"/><#else><@bean.message key="system.button.submit"/></#if>" name="button1" onClick="doAction(this.form)" class="buttonStyle" />&nbsp;
		       <input type="reset"  name="reset1" value="<@bean.message key="system.button.reset"/>" class="buttonStyle" />
		     </td>
		   </tr>  
     </table>
    </td>
   </tr>
   </form>
  </table>
 </body>
<#include "/templates/foot.ftl"/>