<table width="100%" id="menuTable" class="searchTable" onkeypress="DWRUtil.onReturn(event, search)">
	<tr class="infoTitle"  valign="top">
		<td class="infoTitle" align="left" valign="bottom" colspan="2">
			<img src="${static_base}/images/action/info.gif" align="top"/>
			<b>借用查询</b>
		</td>
	</tr>
	<tr>
		<td style="font-size:0px" colspan="2">
			<img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
		</td>
	</tr>
	<tr>
	  <td colspan="2">
		 <fieldSet  align=center> 
		 <legend>日期区间</legend>
			开始：<input type="text" name="applyTime.dateBegin" value="${RequestParameters['roomApply.applyTime.dateBegin']?if_exists}" onfocus="calendar()" size="10" maxlength="10"/> <br>
			结束：<input type="text" name="applyTime.dateEnd" value="${RequestParameters['roomApply.applyTime.dateEnd']?if_exists}" onfocus="calendar()" size="10" maxlength="10"/>
		</fieldSet>
		</td>
	</tr>
	<tr>
	 <td colspan="2" id="f_Time">
		 <fieldset align=center>
		 <legend>时间区间</legend>
			开始：<input type="text" name="applyTime.timeBegin" value="${RequestParameters['applyTime.timeBegin']?if_exists}" size="10" maxlength="5"/><br>
			结束：<input type="text" name="applyTime.timeEnd" value="${RequestParameters['applyTime.timeEnd']?if_exists}" size="10" maxlength="5"/>
		</fieldset>
		</td>
	</tr>
	<tr>
		<td class="title" colspan="2" id="f_cycleCount">
		<fieldSet  align=center> 
		 <legend>时间周期</legend>
		 每：<input type="text" name="roomApply.applyTime.cycleCount" style="width:20px" value="${RequestParameters['roomApply.applyTime.cycleCount']?default('')}" maxlength="2"/>
			<select name="roomApply.applyTime.cycleType">
			    <option value="">...</option>
				<option value="1">天</option>
				<option value="2">周</option>
				<option value="4">月</option>
			</select>
		</fieldSet>
		</td>
	</tr>
    <tr>
	    <td colspan="2">
		 <fieldSet  align=center> 
		 <legend>教室</legend>
			名称：<input type="text" name="classroom.name" style="width:90px" value="" maxlength="20"/><br>
			类别：<@htm.i18nSelect datas=roomConfigTypes selected="" name="classroom.configType.id" style="width:90px">
			<option value=''>...</option>
			</@><br>
			多媒体设备：<select name="roomApply.isMultimedia">
				<option value="" selected>...</option>
			    <option value="1">使用</option>
				<option value="0">不使用</option>
			</select>
		</fieldSet>
		</td>
	</tr>
	<tr>
		<td class="title" colspan="2" id="f_cycleCount">
		<fieldSet align=center> 
		 <legend>校区</legend>
		 	<@htm.i18nSelect datas=schoolDistricts selected="" name="roomApply.schoolDistrict.id" style="width:150px">
				<option value=''>...</option>
			</@>
		</fieldSet>
		</td>
	</tr>
	<#assign lookContent=RequestParameters['lookContent']?default("1")>
	<tr>
		<td class="title" colspan="2">
			<fieldSet align=center>
				<legend>查看内容</legend>
				<input type="radio" name="lookContent" value="" <#if !(RequestParameters['lookContent'])?exists || RequestParameters['lookContent'] == ''>checked</#if>/>&nbsp;归口未审核<br>
				<input type="radio" name="lookContent" value="0" <#if (RequestParameters['lookContent'])?exists && RequestParameters['lookContent'] == '0'>checked</#if>/>&nbsp;归口审核未通过<br>
				<input type="radio" name="lookContent" value="1" <#if lookContent== '1'>checked</#if>/>&nbsp;归口审核已通过<br>
				<input type="radio" name="lookContent" value="2" <#if lookContent== '2'>checked</#if>/>&nbsp;正式通过的请求
			</fieldSet>
		</td>
	</tr>
	<tr class="title">
		<td colspan="2" align="center">
		 <button onClick="this.form.reset()">重置</button>&nbsp;
		 <button onClick="search(document.searchRoomApplyApproveForm)" id="searchRoom">查询</button>
		 </td>
	</tr>
</table>