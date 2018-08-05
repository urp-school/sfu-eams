<#include "/templates/head.ftl"/>
 <body>
 <table id="myBar"></table>
 <script>
  var bar = new ToolBar("myBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
 <table width="100%" class="listTable" >
    <tr align="center" class="darkColumn">
      <td width="50%">学科门类</td>
      <td width="20%">专业数量</td>
      <td width="20%">百分比</td>
    </tr>
    <#assign all=0>
    <#list stats as countItem>
    <#assign all=all+countItem.countors[0]?default(0)>
    </#list>
    <#list stats as countItem>
     <tr align="center" >
      <td><@i18nName countItem.what?if_exists/></td>
      <td>${countItem.countors[0]?default("")}</td>
      <td>${(countItem.countors[0]?default(0)/all*100)?string("#0.0")}%</td>
    </tr>
	</#list>
	<tr class="darkColumn" align="center">
	   <td>总计</td>
	   <td>${all}</td>
	   <td>100%</td>
	</tr>
	</table>
</body>
<#include "/templates/foot.ftl"/> 