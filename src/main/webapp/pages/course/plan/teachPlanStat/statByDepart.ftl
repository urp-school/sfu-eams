<#include "/templates/head.ftl"/>
 <body >
 <table id="gpBar"></table>
 <script>  
  function statByDepart(enrollTurn){
     self.location="teachPlanStat.do?method=statByDepart&enrollTurn="+enrollTurn;
  }
　var select ="<select name='enrollTurn' onchange='statByDepart(this.value)'><#list enrollTurns as enrollTurn><option value='${enrollTurn}' <#if enrollTurn=defaultTurn> selected</#if>>${enrollTurn}</option></#list ></select>"
  var bar = new ToolBar("gpBar","统计结果(所在年级)"+select,null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
  bar.addHelp("<@msg.message key="action.help"/>");
 </script>
 <table width="100%" >
 <td  width="50%" valign="top" >
 <table width="100%" class="listTable" >
    <tr align="center" class="darkColumn">
      <td width="10%">序号</td>
      <td width="50%"><@msg.message key="entity.department"/></td>
      <td width="20%">数量</td>
    </tr>
    <#assign sum0 = 0/>
    <#list countResult as countItem>
     <tr align="center" >
      <td>${countItem_index + 1}</td>
      <td><@i18nName countItem.what/></td>
      <td><#assign sum0 = sum0 + (countItem.getCount())?default(0)/>${countItem.getCount()}</td>
    </tr>
	</#list>
	<tr class="darkColumn" style="text-align:center; font-weight: bold">
		<td>合计</td>
		<td></td>
		<td>${sum0?default(0)}</td>
	</tr>
	</table>
	<#list 1..5 as i><br></#list>
</td>
<td valign="top">
<@cewolf.chart  id="line"  title="按照院系统计"   type="pie" 
  xaxislabel="Page"  yaxislabel="Views" showlegend=false
  backgroundimagealpha=0.5>   
 <@cewolf.data>
    <@cewolf.producer id="planStatByDepart"/>    
 </@cewolf.data>
 </@cewolf.chart>
 <p>
 <@cewolf.img chartid="line" renderer="cewolf" width=400 height=300/>
 
</td>
</table>

</body>
<#include "/templates/foot.ftl"/> 