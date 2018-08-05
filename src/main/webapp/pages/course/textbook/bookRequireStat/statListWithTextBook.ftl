<#include "/templates/head.ftl"/>
 <body >
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
  bar.addHelp("<@msg.message key="action.help"/>");
 </script>
 <table width="100%" class="listTable" >
    <tr align="center" class="darkColumn">
      <td width="10%">序号</td>
      <td width="35%">${RequestParameters['kindName']}</td>
      <td width="35%"><@msg.message key="entity.press"/></td>      
      <td width="10%"><@msg.message key="attr.count"/></td>
       <td width="10%">订购量</td>
    </tr>
    <#assign sum0 = 0/>
    <#assign sum1 = 0/>
    <#list stats as countItem>
     <tr align="center">
      <td>${countItem_index + 1}</td>
      <td><@i18nName countItem.what?if_exists/></td>
      <td><@i18nName countItem.what?if_exists.press?if_exists/></td>
      <td><#assign sum0 = sum0 + countItem.countors[0]/>${countItem.countors[0]}</td>
      <td><#assign sum1 = sum1 + countItem.countors[1]/>${countItem.countors[1]}</td>
    </tr>
	</#list>
	<tr class="darkColumn" style="text-align:center; font-weight: bold">
		<td>合计</td>
		<td colspan="2"></td>
		<td>${sum0?default(0)}</td>
		<td>${sum1?default(1)}</td>
	</tr>
	</table>
	<#list 1..5 as i><br></#list>
</body>
<#include "/templates/foot.ftl"/> 