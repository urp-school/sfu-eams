<#include "/templates/head.ftl"/>
 <body>
 <table width="100%">
 <td  width="50%" valign="top">
 <table width="100%" class="listTable">
    <tr align="center" class="darkColumn">
      <td width="10%">序号</td>
      <td >统计项</td>
      <td width="15%">数量</td>
    </tr>
    <#assign sum0 = 0/>
    <#assign countSize = 0/>
    <#list studentInfoStatList as studentInfoStatArray>
     <tr align="center">
      <td>${studentInfoStatArray_index+1}</td>
      <td><#assign groupArrayLength=(groupArray?size)?default(0)/><#if groupArrayLength==0>全部<#else><#list 0..groupArrayLength-1 as i><#if studentInfoStatArray[i]?is_hash ><@i18nName studentInfoStatArray[i] />(代码:${studentInfoStatArray[i].code})<#else>${studentInfoStatArray[i]}</#if></#list></#if></td>
      <td><#if groupArrayLength==0><#assign countSize=studentInfoStatArray/><#else><#assign countSize=studentInfoStatArray[(groupArray?size)?default(0)]/></#if><#assign sum0 = sum0 + countSize/>${countSize}</td>
    </tr>
	</#list>
  	<tr class="darkColumn" style="text-align:center; font-weight: bold">
  		<td></td>
	  	<td>汇总</td>
	  	<td>${sum0}</td>
  	</tr>
	</table>
</td>
<td valign="top">
	<@cewolf.chart id="studentInfoStatPie" title="统计图" type="pie" xaxislabel="Page"  yaxislabel="Views" showlegend=false backgroundimagealpha=0.5>
		<@cewolf.data>
		    <@cewolf.producer id="generalDatasetProducer"/>    
		</@cewolf.data>
	</@cewolf.chart>
	<p>
	<@cewolf.img chartid="studentInfoStatPie" renderer="cewolf" width=400 height=300/>
</td>
</table>

</body>
<#include "/templates/foot.ftl"/> 