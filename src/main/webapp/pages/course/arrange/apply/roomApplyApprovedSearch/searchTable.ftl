<table width="100%" id="menuTable" class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
	<tr class="infoTitle"  valign="top">
		<td class="infoTitle" align="left" valign="bottom" colspan="2">
			<img src="${static_base}/images/action/info.gif" align="top"/>
			<b>借用查询</b>
		</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2">&nbsp</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2">&nbsp</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2">&nbsp</td>
	</tr>
	<tr>
	  <td colspan="2">
		 <fieldSet  align=center> 
		 <legend>日期区间</legend>
			开始：<input type="text" name="dateBegin"  onfocus="calendar()" size="10" maxlength="10"/> <br>
			结束：<input type="text" name="dateEnd"  onfocus="calendar()" size="10" maxlength="10"/>
		</fieldSet>
		</td>
	</tr>
	<tr>
	 <td colspan="2" id="f_Time">
		 <fieldset align=center>
		 <legend>时间区间</legend>
			开始：<input type="text" name="timeBegin" size="10" maxlength="5"/><br>
			结束：<input type="text" name="timeEnd" size="10" maxlength="5"/>
		</fieldset>
		</td>
	</tr>
    <tr>
	    <td colspan="2">
		 <fieldSet  align=center> 
		 <legend>教室</legend>
			多媒体设备：<select name="isMultimedia">
				<option value="" selected>...</option>
			    	<option value="1">使用</option>
				<option value="0">不使用</option>
			</select>
		</fieldSet>
		</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2">&nbsp</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2">&nbsp</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2">&nbsp</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2">&nbsp</td>
	</tr>
	<tr class="title">
		<td colspan="2" align="center">
		 <button onClick="this.form.reset()">重置</button>&nbsp;
		 <button onClick="search()" id="searchRoom">查询</button>
		 </td>
	</tr>
	
</table>
