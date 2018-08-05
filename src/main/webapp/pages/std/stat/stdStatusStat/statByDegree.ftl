<#include "/templates/head.ftl"/>
 <body >
 <table id="myBar"></table>
 <script>
  var bar = new ToolBar("myBar","按应获取学位的统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
 <div align="center"><br><B>应获取学位的分布情况</B></div>
  	<@table.table width="80%" align="center">
  			<@table.thead>
  				<td><b>应获取的学位</b></td>
  				<td><b>学生人数</b></td>
   			</@>
   			<#assign total=0>
   			<@table.tbody datas=statDatas;statData>
   				<td align="center">${statData[0]}</td>
   				<td align="center">${statData[1]}</td>
   				<#assign total=total+statData[1]>
   			</@>
   			<tr align="center">
   				<td>合计</td>
   				<td>${total}</td>
  			</@>
</body>
<#include "/templates/foot.ftl"/> 