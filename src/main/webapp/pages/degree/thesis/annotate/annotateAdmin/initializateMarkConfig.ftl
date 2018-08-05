<div id="configDiv" style="display:none;width:300px;height:200px;position:absolute;top:30px;right:20px;borderolid;border-width:1px;background-color:white">
	<table class="settingTable">
	<form name="conditionForm" method="post" action="" onsubmit="return false;">
		<input type="hidden" id="parameterss" name="parameterss" value="">
	 	<tr>
	 		<td width="20%">编号年份</td>
	 		<td><input type="text" maxlength="7" id="markYear" name="markYear" value="${year}05" style="width:100px;">例如:2006上半年：0605,2006下半年 0611</td>
	 	</tr>
	 	<tr>
	 		<td>起始数字</td>
	 		<td><input type="text" id="beginNumber" name="beginNumber" maxlength="5" style="width:100px;" value="1">例如:06110001 起始数字是1</td>
	 	</tr>
	 	<tr>
	 		<td>专家个数</td>
	 		<td><input type="text" id="exportNum" name="exportNum" maxlength="5" style="width:100px;" value="3">表示评阅专家的个数,生成论文评阅书的份数</td>
	 	</tr>
	 	<tr>
	 		<td>有效位数</td>
	 		<td><input type="text" id="virtualNum" name="virtualNum" maxlength="5" style="width:100px;" value="4">例如：06110001 有效位数 4 表示编号年份后面的位数</td>
	 	</tr>
	 	<tr>
	 		<td align="center" colspan="2">
	 			<button name="buttonName" onclick="${doClick?string}" class="buttonStyle">设置</button>
	 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 			<button name="buttonName" onclick="displayConfig()" class="buttonStyle">关闭</button>
	 		</td>
	 	</tr>
	 	</form>
	</table>
</div>