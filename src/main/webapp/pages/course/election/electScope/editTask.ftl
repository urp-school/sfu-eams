<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body style="Z-index: 1">
  	<#assign labInfo><@bean.message key="entity.teachTask"/>基本信息</#assign>
 	<#include "/templates/back.ftl"/>
    <table width="100%" align="center" class="formTable">
       <form name="teachTaskForm" action="" method="post" onsubmit="return false;">
       <input name="params" value="${RequestParameters['params']}" type="hidden"/>
       <input type="hidden" value="${task.id?if_exists}" name="task.id"/>
       <tr  class="darkColumn">
          	<td colspan="4">&nbsp;选退课信息</td>
       	</tr>
       	<tr>
	     	<td class="title" width="16%" id="f_isCancelable">&nbsp;是否允许退课<font color="red">*</font>:</td>
	     	<td class="content" colspan="3">
		       	<select name="task.electInfo.isCancelable" style="width:100px" >
		         	<option value="1"<#if task.electInfo.isCancelable> selected</#if>>允许</option>
		         	<option value="0"<#if task.electInfo.isCancelable == false> selected</#if>>不允许</option>
		       	</select>
	     	</td>
       	</tr>
       	<tr class="darkColumn">
          	<td colspan="4">其他信息</td>
       	</tr>
       	<tr>
       		<td colspan="4">
	           	<div id="otherInfo">
	             	<table class="formTable" width="100%">
	               		<tr>
	                 		<td class="title" width="16%" >HSK级别要求:</td>
	                 		<td>
	                     		<select name="task.electInfo.HSKDegree.id" style="width:150px">
	                     			<option value="">请选择</option>
	                     			<#list HSKDegreeList as degree>
	                        			<option value="${degree.id}" <#if task.electInfo.HSKDegree?if_exists.id?if_exists?string=degree.id?string>selected</#if>><@i18nName degree/></option>
	                     			</#list>
	                     		</select>
	                 		</td>
	                 		<td class="title" width="16%">先修课程(课程代码串):</td>
	                 		<td><input name="preCourseCodeSeq" type="text" value="<#list task.electInfo.prerequisteCourses as course>${course.code},</#list>" maxLength="20"></td>
	               		</tr>
			       		<tr>
			         		<td class="title" id="f_remark">&nbsp;<@bean.message key="attr.remark"/>:</td>
			         		<td colspan="3" class="content">
			            		<textarea name="task.remark" cols="55">${task.remark?if_exists?html}</textarea>
			         		</td>
			       		</tr>
	             	</table>
	           	</div>
           	</td>
       	</tr>
	   	<tr class="darkColumn">
	     	<td colspan="6" align="center">
	       		<input type="button" value="<@bean.message key="action.submit"/>" name="saveButton" onClick="save(this.form)" class="buttonStyle"/>&nbsp;
	       		<input type="reset" name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle"/>
	     	</td>
	   	</tr>
		</form>
	</table>
	<script language="javascript">
		var action = "";
	   	function save(form){
	    	form.action = action + "electScope.do?method=saveTask";
	     	var a_fields = {
	         	'task.remark':{'l':'<@bean.message key="attr.remark"/>', 'r':false, 't':'f_remark', 'mx':100}
	     	};
	     	var v = new validator(form, a_fields, null);
	     	if (v.exec()) {
	        	form.submit();
			}
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>