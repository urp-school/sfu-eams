<#include "/templates/head.ftl"/>
 <body >
 <table id="myBar"></table>
 <script>
  var bar = new ToolBar("myBar","按生源地的统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
  	<@table.table width="80%" align="center">
  		<@table.thead>
  			<@table.td text="生源地"/>
  			<@table.td text="学生人数"/>
   		</@>
   		<#assign total=0>
   		<@table.tbody datas=statDatas;statData>
   			<td align="center">${statData[0]?default("&nbsp;")}</td>
   			<td align="center">${statData[1]}</td>
   			<#assign total=total+statData[1]/>
   		</@>
   		<tr class="darkColumn" style="text-align:center; font-weight: bold">
   			<td>合计</td>
   			<td>${total}</td>
   		</tr>
  	</@>
</body>
<#include "/templates/foot.ftl"/> 