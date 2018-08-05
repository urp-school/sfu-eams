<#include "/templates/head.ftl"/>
 <body >
 <table id="myBar"></table>
 <script>
  var bar = new ToolBar("myBar","留学生－中国学生的统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
 <div align="center"><br><B>留学生－中国学生统计结果</B></div>
  	<@table.table width="80%" align="center">
  			<@table.thead>
  				<td><b>学生类别</b></td>
  				<td><b>学生人数</b></td>
   			</@>
   			<#assign local=0>
   			<tr align="center">
   				<td>留学生</td>
   				<td>${abroadNum}</td>
   			</tr>
   			<#assign local=all-abroadNum>
   			<tr align="center">
   				<td>中国学生</td>
   				<td>${local}</td>
   			</tr>
   			<tr align="center">
   				<td>合计</td>
   				<td>${all}</td>
   			</tr>
  			</@>
</body>
<#include "/templates/foot.ftl"/> 