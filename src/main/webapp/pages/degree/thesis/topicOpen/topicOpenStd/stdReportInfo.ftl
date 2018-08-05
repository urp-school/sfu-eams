<#if "info"==topicOpenInfo><#assign isDisplay=true><#else><#assign isDisplay=false></#if>
<table class="listTable" width="100%">
<form name="<#if formName?exists>#{formName}<#else>topicOpenInfoForm</#if>" method="post" action="" onsubmit="return false;">
   	<tr>
   		<td  class="grayStyle" id="f_openOn">报告日期</td>
   		<td>
   		<#if isDisplay><#if (topicOpen.openReport.openOn)?exists>${(topicOpen.openReport.openOn)?string("yyyy-MM-dd")}</#if><#else>
   		<input type="text" name="topicOpen.openReport.openOn" maxlength="10" value="<#if (topicOpen.openReport.openOn)?exists>${topicOpen.openReport.openOn?string("yyyy-MM-dd")}</#if>" onfocus="calendar()">
   		</#if>
   		</td>
   		<td  class="grayStyle" id="f_participatorCount">与会人数</td>
   		<td>
   		<#if isDisplay>${(topicOpen.openReport.participatorCount)?if_exists}<#else>
   		<input type="text" name="topicOpen.openReport.participatorCount" maxlength="7" value="${(topicOpen.openReport.participatorCount)?if_exists}" style="width:50px">
   		</#if>
   		</td>
   		<td  class="grayStyle" id="f_address">报告地点</td>
   		<td>
   		<#if isDisplay>${(topicOpen.openReport.address)?if_exists}<#else>
   		<input type="text" name="topicOpen.openReport.address" maxlength="50" value="${(topicOpen.openReport.address)?if_exists}">
   		</#if>
   		</td>
   	</tr>
   	<tr>
   		<td class="grayStyle" id="f_experts">与会者</td>
   		<td colspan="5">
   		<#if isDisplay>${(topicOpen.openReport.experts)?if_exists}<#else>
   		<input type="text" name="topicOpen.openReport.experts" maxlength="50" value="${(topicOpen.openReport.experts)?if_exists}" style="width:300px">
   		</#if>
   		</td>
   </tr>
	<tr>
		<td class="grayStyle" id="f_thesisTopicArranged">调整后题目</td>      	      
		<td class="brightStyle" colspan="7">
		<#if isDisplay>${(topicOpen.thesisPlan.thesisTopicArranged)?if_exists}<#else>
		<textarea name="topicOpen.thesisPlan.thesisTopicArranged" cols="65" rows="2" style="width:100%;border:1 solid #000000;">${(topicOpen.thesisPlan.thesisTopicArranged)?if_exists}</textarea>
		</#if>
		</td>      	 		
	</tr>
   	<tr>
   		<td colspan="8"  class="grayStyle" id="f_opinions">与会者主要意见:</td>
   	</tr>
   	<tr>
   		<td colspan="8">
   		<#if isDisplay>${(topicOpen.openReport.opinions)?if_exists}<#else>
   		<textarea name="topicOpen.openReport.opinions" cols="65" rows="5" style="width:100%;solid #000000;">${(topicOpen.openReport.opinions)?if_exists}</textarea>
   		</#if>
   		</td>
	</tr>
	<#if !isDisplay>
   	<tr>
   		<td colspan="8"  class="darkColumn" align="center">
   		<button name="button2" onclick="doAction(this.form)" class="buttonStyle">更新开题情况</button>
   		</td>
   </tr>
   </#if>
</table>