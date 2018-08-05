 <#macro stateSelect(baseInfoType)>
	<tr><td>教室状态:</td><td><select name="${baseInfoType}.state" style="width:100px;" value="${RequestParameters[baseInfoType + ".state"]?if_exists}">
	   		<option value="1" selected><@bean.message key="common.enabled" /></option>
	   		<option value="0" ><@bean.message key="common.disabled" /></option>
	   </select>
	</td></tr>
 </#macro>