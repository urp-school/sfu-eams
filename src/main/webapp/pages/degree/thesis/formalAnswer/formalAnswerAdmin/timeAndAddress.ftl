<div id="configDiv" style="display:none;width:200px;height:100px;position:absolute;top:30px;right:50px;borderolid;border-width:1px;background-color:white">
	<table class="settingTable">
	<form name="conditionForm" method="post" action="" onSubmit="return false;">
		<input type="hidden" id="parameterss" name="parameterss" value=""/>
	 	<tr>
	 		<td>答辩时间</td>
	 		<td><input type="text" id="timeId" maxlength="10" name="time" style="width:100px;" onfocus="calendar();f_frameStyleResize(self)"></td>
	 	</tr>
	 	<tr>
	 		<td>答辩地点</td>
	 		<td><input type="text" id="addressId" name="address" maxlength="30" style="width:100px;" value=""></td>
	 	</tr>
	 	<tr>
	 		<td align="center" colspan="2">
	 			<button name="buttonName" onclick="${doClick?string}" class="buttonStyle">设置</button>
	 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 			<button name="close" onclick="displayConfig()" class="buttonStyle">关闭</button> 
	 		</td>
	 	</tr>
	 	</form>
	</table>
</div>