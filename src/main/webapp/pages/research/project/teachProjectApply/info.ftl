<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
<#assign labInfo>项目信息</#assign>  
<#include "/templates/back.ftl"/>   
	<table width="90%" align="center"  class="formTable">
		<@searchParams/>
		<tr>
			<td id="f_code" align="right" class="title" width="20%">项目代码</td>
			<td align="bottom" width="30%">${(teachProject.code)?if_exists}</td>
			<td id="f_name" align="right" class="title" width="20%">项目名称</td>
			<td align="bottom" width="30%">${(teachProject.name)?if_exists}</td>
		</tr>

		<tr>
			<td id="f_teachProjectType" class="title">项目类别:</td>
			<td><@i18nName teachProject.teachProjectType?if_exists/></td>
			<td id="f_teachProjectState" class="title">项目状态:</td>
			<td><@i18nName teachProject.teachProjectState?if_exists/></td>
		</tr>
		<tr>
			<td  id="f_describe" class="title">项目负责人:</td>
			<td colspan="3">${(teachProject.principal)?if_exists}</td>
		</tr>
		<tr>
			<td  id="f_describe" class="title">项目描述:</td>
			<td colspan="3">${(teachProject.describe)?if_exists}</td>
		</tr>
		<tr>
			<td id="f_creatAt" align="right" class="title" width="20%">创建时间</td>
			<td align="bottom" width="30%">${(teachProject.createAt)?if_exists}</td>
			<td id="f_name" align="right" class="title" width="20%">申请人代码/名称</td>
			<td align="bottom" width="30%">${(teachProject.petitionBy.name)?if_exists}/${(teachProject.petitionBy.userName)?if_exists}</td>
		</tr>
    </table>
</body> 
</html>