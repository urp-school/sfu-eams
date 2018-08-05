<#assign enter = 10/>
<br>
<table class="listTable"  align="center">
	<tr class="darkColumn">
	    <#assign remain = 0/>
		<#list placesAccounts as placeAcc>
			<#assign i = placeAcc_index>
			<#assign keys = placeAcc?keys/>
			<#assign key = keys?first/>
			<#if i % enter == 0 && i != 0>
				</tr><tr>
				<#assign j = 0/>
				<#list placesAccounts as placeAcc1>
					<#if ((placeAcc_index - placeAcc1_index) <= enter)>
						<#assign keys1 = placeAcc1?keys/>
						<#assign key1 = keys1?first/>
						<td>${placeAcc1[key1]}</td>
						<#if (j >= enter - 1)><#break></#if>
						<#assign j = j + 1/>
					</#if>
					<#assign remain = placeAcc1_index/>
				</#list>
				</tr><tr class="darkColumn" >
			</#if>
			<td>${key}</td>
		</#list>
		<#list placesAccounts?size..(placesAccounts?size + (enter - placesAccounts?size % enter) - 1) as k>
			<td>&nbsp;</td>
		</#list>
	</tr>
	<tr>
		<#list placesAccounts as placeAcc>
			<#assign i = placeAcc_index/>
			<#assign keys = placeAcc?keys/>
			<#assign key = keys?first/>
			<#if (placeAcc_index > remain + 1)>
				<td>${placeAcc[key]}</td>
			</#if>
		</#list>
		<#list placesAccounts?size..(placesAccounts?size + (enter - placesAccounts?size % enter) - 1) as k>
			<td>&nbsp;</td>
		</#list>
	</tr>
</table>
<br>
