<div id="<#if divId?exists>${divId}<#else>configDiv</#if>" style="display:none;width:200px;height:50px;position:absolute;top:36px;right:0px;borderolid;border-width:1px;background-color:white">
	<table class="settingTable">
	<form name="<#if formName?exists>${formName}<#else>conditionForm</#if>" method="post" action="" onsubmit="return false;">
	 	<tr>
	 		<td>表格类别</td>
	 		<td>
	 		<input type="radio" name="tutorTypeId" value="1" checked>硕士
	 		<input type="radio" name="tutorTypeId" value="2">博士
	 		</td>
	 	</tr>
	 	<tr>
	 		<input type="hidden" name="fileName" value="">
	 		<input type="hidden" name="displayName" value="">
	 		<td align="center" colspan="2"><input type="button" id="buttonName" name="buttonName" value="<#if divId?exists&&"applyId"==divId?string>申请<#else>下载</#if>" onclick="${doClick?string}" class="buttonStyle"></td>
	 	</tr>
	 	</form>
	</table>
</div>