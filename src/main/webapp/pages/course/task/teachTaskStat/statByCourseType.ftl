<#include "/templates/head.ftl"/>
 <body>
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
  bar.addBack("<@msg.message key="action.back"/>");
 </script>
 <table width="100%" >
 <td  width="50%" valign="top">
 <table width="100%" class="listTable" >
    <tr align="center" class="darkColumn">
      <td width="10%">序号</td>
      <td width="50%"><@msg.message key="entity.courseType"/></td>
      <td width="20%">数量</td>
    </tr>
    <#list countResult as countItem>
     <tr align="center" >
      <td>${countItem_index}</td>
      <td><@i18nName countItem.what/></td>
      <td>${countItem.count}</td>
    </tr>
	</#list>
	</table>
</td>
<td>
<@cewolf.chart  id="line"  title="按照课程类别统计"   type="pie" 
  xaxislabel="Page"  yaxislabel="Views" showlegend=false
  backgroundimagealpha=0.5>   
 <@cewolf.data>
    <@cewolf.producer id="pageViews"/>    
 </@cewolf.data>
 </@cewolf.chart>
 <p>
 <@cewolf.img chartid="line" renderer="cewolf" width=400 height=300/>
 
</td>
</table>

</body>
<#include "/templates/foot.ftl"/> 