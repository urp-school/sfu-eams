<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
<#assign labInfo>参数信息</#assign>  
<#include "/templates/back.ftl"/>   
	<table width="90%" align="center"  class="formTable">
		<form action="ruleParameter.do?method=save" name="ruleParameterForm" method="post" onsubmit="return false;">
		<@searchParams/>
		<input type="hidden" name="param.id" value="${(param.id)?default('')}"/>
		<input type="hidden" value="${ruleId?if_exists}" name="ruleId"/>
		<tr>
			<td id="f_name" align="right" class="title" width="20%"><font color="red">*</font>参数名称:</td>
			<td align="bottom" width="30%">
			<input type="text" name="param.name" value="${(param.name)?if_exists}" maxlength="100" style="width:100%"/>
			</td>
			<td id="f_type" class="title" width="20%"><font color="red">*</font>参数类型:</td>
			<td width="30%">
			<input type="text" name="param.type" value="${(param.type)?if_exists}" maxlength="100" style="width:100%"/>
			</td>
		</tr>
		<tr>
			<td id="f_title" align="right" class="title" width="20%"><font color="red">*</font>参数标题:</td>
			<td align="bottom" width="30%">
			<input type="text" name="param.title" value="${(param.title)?if_exists}" maxlength="100" style="width:100%"/>
			</td>
			<td id="f_description" class="title" width="20%">参数描述:</td>
			<td width="30%">
			<input type="text" name="param.description" value="${(param.description)?if_exists}" maxlength="100" style="width:100%"/>
			</td>
		</tr>
		<tr>
			<td  id="f_parent" class="title"><font color="red">*</font>上级参数:</td>
			<td colspan="3">
	     		<select name="param.parent.id" style="width:100%">
			     	<option value="">...</option>
			        <#list ruleParams as ruleParam>
				       <option value="${ruleParam.id}"<#if (param.parent.id)?default("")?string == (ruleParam.id)?string>selected</#if>><@i18nName ruleParam/></option>
				    </#list>
		     	</select>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center" class="darkColumn">
				<button onClick='save(this.form)'><@bean.message key="system.button.submit"/></button>
	     	</td>
		</tr>
		</form> 
    </table>
    <SCRIPT language=javascript> 
    function save(form){        
         var a_fields = {
         'param.name':{'l':'参数名称', 'r':true, 't':'f_name'},
         'param.type':{'l':'参数类型', 'r':true, 't':'f_type'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }  
   </SCRIPT>
</body> 
</html>