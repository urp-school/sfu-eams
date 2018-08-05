<div id="configDiv" style="display:none;width:200px;height:100px;position:absolute;top:30px;right:20px;borderolid;border-width:1px;background-color:white">
	<table class="settingTable">
	<form name="conditionForm" method="post">
	 	<tr>
	 		<td width="20%">起始编号<font color="red">*</font></td>
	 		<td><input type="text" id="startNo" maxlength="32" name="startNo" style="width:100px;">例如:06050001</td>
	 	</tr>
	 	<tr>
	 		<td>结束编号<font color="red">*</font></td>
	 		<td><input type="text" id="endNo" name="endNo" maxlength="32" style="width:100px;">例如:06110100</td>
	 	</tr>
	 	<tr>
	 		<td align="center" colspan="2">
	 			<button name="buttonName" onclick="${doClick?string}" class="buttonStyle">设置</button>
	 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 			<button name="buttonName" onclick="displayConfig()" class="buttonStyle">关闭</button>
	 		</td>
	 	</tr>
	 	</form>
	 	<script>
	 		var date = new Date()
	 		var startNo = document.getElementById("startNo");
	 		var endNo = document.getElementById("endNo");
	 		var year = new String(date.getYear());
	 		year = year.substr(year.length-2,2);
	 		if(date.getMonth()>=5){
	 			startNo.value=year+"050001";
	 			endNo.value = year+"051001"
	 		}else{
	 			startNo.value=year+"110001";
	 			endNo.value = year+"111001"
	 		}
	 	</script>
	</table>
</div>