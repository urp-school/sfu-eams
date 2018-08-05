<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
<#assign labInfo>文档信息</#assign>  
<#include "/templates/back.ftl"/>   
	<table width="90%" align="center"  class="formTable">
		<form action="projectDocument.do?method=save" name="projectDocumentForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
		<@searchParams/>
		<input type="hidden" name="projectDocument.id" value="${(projectDocument.id)?default('')}"/>
		<input type="hidden" value="${teachProjectId?if_exists}" name="teachProjectId"/>
		<tr>
    	 	<td class="title" width="25%" id="f_file">&nbsp;文档上传:</td>
	     	<td>
	     		<input type="file" name="formFile" size=50 class="buttonStyle" value=''>&nbsp;<br>
	     		<a href='projectDocument.do?method=download&projectDocumentId=${(projectDocument.id)?if_exists}'>${(projectDocument.fileName)?if_exists}</a>
	    	</td>
	   	</tr>
		<tr>
			<td  id="f_describe" class="title"><font color="red">*</font>文档描述:</td>
			<td>
	     		<textarea name="projectDocument.describe" rows="5" style="width:100%;">${(projectDocument.describe)?if_exists}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" class="darkColumn">
				<button onClick='this.form.submit()'><@bean.message key="system.button.submit"/></button>
	     	</td>
		</tr>
		</form> 
    </table>
</body> 
</html>