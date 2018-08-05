<div id="configDiv" style="display:none;width:200px;height:100px;position:absolute;top:36px;right:0px;borderolid;border-width:1px;background-color:white">
	<table class="settingTable">
	<form name="conditionForm" method="post" action="" onsubmit="return false;">
		<input type="hidden" id="parameterss" name="parameterss" value="">
	 	<tr>
	 		<td id="f_time"><font color="red">*</font>开题时间</td>
	 		<td><input type="text" id="timeId" maxlength="10" name="time" style="width:100px;" onfocus="calendar();f_frameStyleResize(self)"></td>
	 	</tr>
	 	<tr>
	 		<td id="f_address"><font color="red">*</font>开题地点</td>
	 		<td><input type="text" id="addressId" maxlength="30" name="address" style="width:100px;" value=""></td>
	 	</tr>
	 	<tr>
	 		<td align="center" colspan="2">
	 			<button name="buttonName" onclick="${doClick?string}" class="buttonStyle">设置</button>
	 			&nbsp;&nbsp;&nbsp;&nbsp;
	 			<button name="buttonName" onclick="displayConfig()" class="buttonStyle">关闭</button>
	 		</td>
	 	</tr>
	 	</form>
	</table>
</div>