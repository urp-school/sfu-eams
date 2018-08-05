<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="bar"></table>
    <div align="center" style="font-size:20px"><B><@i18nName systemConfig.school/>369校区教室借用收费标准</B></div>
    <br>
    <table width="90%" align="center" border="1" bordercolor="#006CB2" style="border-collapse:collapse">
    <tr>
    	<td align="center" bgcolor="#c7dbff">类型</td>
    	<td align="center" bgcolor="#c7dbff">收费标准(元/次)</td>
    </tr>
    <tr>
    	<td align="center">20座以下教室</td>
    	<td align="center">40</td>
    </tr>	
    <tr>
    	<td align="center">21座至40座教室</td>
    	<td align="center">60</td>
    </tr>
    <tr>
    	<td align="center">41座至70座教室</td>
    	<td align="center">80</td>
    </tr>	
    <tr>
    	<td align="center">71座以上教室</td>
    	<td align="center">100</td>
    </tr>
    <tr>
    	<td align="center">MBA报告厅</td>
    	<td align="center">300</td>
    </tr>
    <tr>
    	<td align="center">图书馆报告厅</td>
    	<td align="center">200</td>
    </tr>
    <tr>
    	<td align="center">大礼堂</td>
    	<td align="center">1000</td>
    </tr>
    </table>        
     	<br>
 	<table width="90%" align="center">
 	<tr>
 		<td align="center" colspan="2">备注:凡有空调和多媒体的教室、报告厅使用费用在上面价目表的基础上增加20%;<br>星期六、星期日各时段的有偿使用，使用费用在正常工作日基础上升20%。</td>
 	</tr>
 	</table>
 	<script>
 		var bar = new ToolBar("bar", "369校区的价目表", null, true, true);
 		bar.addPrint();
 	</script>
</body>
<#include "/templates/foot.ftl"/>
