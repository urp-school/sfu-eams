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
      <td width="25%">${RequestParameters['kindName']}</td>
      <td width="25%"><@msg.message key="entity.press"/></td>
      <td width="10%"><@msg.message key="attr.count"/></td>
      <td width="10%">通过审核</td>
      <td width="10%">未通过</td>
      <td width="10%">未审核</td>
    </tr>
    <#assign sum0 = 0/>
    <#assign sum1 = 0/>
    <#assign sum2 = 0/>
    <#assign sum3 = 0/>
    <#list stats as countItem>
     <tr align="center" >
      <td>${countItem_index}</td>
      <td><@i18nName countItem.what?if_exists/></td>
      <td><@i18nName countItem.what?if_exists.press?if_exists/></td>
      <td><#assign sum0 = sum0 + countItem.countors[0]?default(0)/>${countItem.countors[0]}</td>
      <td><#assign sum1 = sum1 + countItem.countors[1]?default(0)/>${countItem.countors[1]}</td>
      <td><#assign sum2 = sum2 + countItem.countors[2]?default(0)/>${countItem.countors[2]}</td>
      <td><#assign sum3 = sum3 + countItem.countors[3]?default(0)/>${countItem.countors[3]}</td>
    </tr>
	</#list>
	<tr class="darkColumn" style="text-align:center; font-weight: bold">
		<td>合计</td>
		<td colspan="2"></td>
		<td>${sum0?default(0)}</td>
		<td>${sum1?default(0)}</td>
		<td>${sum2?default(0)}</td>
		<td>${sum3?default(0)}</td>
	</tr>
	</table>
	<#list 1..5 as i><br></#list>
	</table>
</body>
<#include "/templates/foot.ftl"/> 