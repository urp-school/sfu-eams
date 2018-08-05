<div id="configDiv" style="display:none;width:400px;height:170px;position:absolute;top:30px;right:0px;borderolid;border-width:1px;background-color:white">
	<table class="settingTable" width="100%" cellspacing="0" cellpadding="0">
	<form name="conditionForm" method="post" action="" onsubmit="return false;">
		<input type="hidden" id="parameterss" name="parameterss" value="">
	 	<tr>
	 		<td align="center">
				<table align="center" cellspacing="0" cellpadding="0" border="0">
				 	<tr>
				 		<td>学年度学期</td>
				 		<td><select id="stdType" name="stdTypeId" style=":width:100px;">
							</select>
							<select id="year" name="year" style="width:100px;">
							</select>
							<select id="term" name="term" style="width:100px;">
							</select>
						</td>
					</tr>
				 	<tr>
				 		<td colspan="2">分数段区间设置</td>
				 	</tr>
				</table>
			</td>
	 	</tr>
	 	<tr>
	 		<td align="center">
				 <table align="center" cellspacing="0" cellpadding="0" border="0">
				 	<tr>
				 		<td witdh="150" id="f_best"><font color="red">*</font>优秀:</td>
				 		<td>从<input type="text" name="AMin" style="width:100px;" maxlength="4" value="<#if segMentMap["A"]?exists>${segMentMap["A"].min?if_exists}<#else>90</#if>"/>到<input type="text" name="AMax" style="width:100px;" value="<#if segMentMap["A"]?exists>${segMentMap["A"].max?if_exists}<#else>100</#if>" maxlength="4"/></td>
				 	</tr>
				 	<tr>
				 		<td id="f_good"><font color="red">*</font>良好:</td>
				 		<td>从<input type="text" name="BMin" style="width:100px;" maxlength="4" value="<#if segMentMap["B"]?exists>${segMentMap["B"].min?if_exists}<#else>75</#if>"/>到<input type="text" name="BMax" style="width:100px;" value="<#if segMentMap["B"]?exists>${segMentMap["B"].max?if_exists}<#else>89</#if>" maxlength="4"/></td>
				 	</tr>
				 	<tr>
				 		<td id="f_normal"><font color="red">*</font>一般:</td>
				 		<td>从<input type="text" name="CMin" style="width:100px;" maxlength="4" value="<#if segMentMap["C"]?exists>${segMentMap["C"].min?if_exists}<#else>61</#if>"/>到<input type="text" name="CMax" style="width:100px;" value="<#if segMentMap["C"]?exists>${segMentMap["C"].max?if_exists}<#else>74</#if>" maxlength="4"/></td>
				 	</tr>
				 	<tr>
				 		<td id="f_bad"><font color="red">*</font>不及格:</td>
				 		<td>从<input type="text" name="DMin" style="width:100px;" maxlength="4" value="<#if segMentMap["D"]?exists>${segMentMap["D"].min?if_exists}<#else>0</#if>"/>到<input type="text" name="DMax" style="width:100px;" value="<#if segMentMap["D"]?exists>${segMentMap["D"].max?if_exists}<#else>60</#if>" maxlength="4"/></td>
				 	</tr>
				 </table>
			</td>
		</tr>
	 	<tr>
	 		<td align="center" colspan="2">
	 			<button name="buttonName" onclick="${doClick?string}" class="buttonStyle">设置</button>
	 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 			<button name="buttonName" onclick="displayConfig()" class="buttonStyle">关闭</button>
	 		</td>
	 	</tr>
		<#include "/templates/calendarSelect.ftl"/>
	 	</form>
	</table>
</div>