<div id="configDiv" style="display:none;width:300px;height:100px;position:absolute;top:30px;right:0px;borderolid;border-width:1px;background-color:white">
	<form name="printConfigForm" method="post">
	<table class="settingTable">
		<tr>
			<td colspan="2" align="center">预答辩备案表</td>
		</tr>
		<tr>
			<td>答辩次数</td>
			<td>
				<#assign num>${(preAnswer.answerNum)?default(0)?string}</#assign>
				<#if (num?number-1>=0)>
					<#list 0..(num?number-1) as i>
						<input type="radio" name="preAnswerNum" value="${i+1}">第${i+1}次
					</#list>
				</#if>
			    <#if num?number<3>
			    	<input type="radio" name="preAnswerNum" value="0,${num}">第${num?number+1}次空表
			    </#if>
			</td>
		</tr>
		<tr>
			<td>字体大小</td>
			<td>
			<select name="fontSize" style="width:100%">
				<#list 5..20 as i>
					<option value="${i}" <#if i==10>selected</#if>>${i}pt</option>
				</#list>
			</select>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" ><button name="buttonName" onclick="previewPrint()" class="buttonStyle">打印预览</button>&nbsp;&nbsp;&nbsp;<button name="buttonName" onclick="displayConfig()" class="buttonStyle">关闭</button></td>
		</tr>
		<tr>
			<td colspan="2">备注:打印显示的次数 第一次表示你参与第一次答辩的备案表 空表表示你将要参与的下次的空的备案表</td>
		</tr>
		</form>
	</table>
</div>