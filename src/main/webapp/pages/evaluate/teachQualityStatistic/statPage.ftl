<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0">
	<div align="center"><h1>学生网上评教情况</h1></div>
<@table.table id="listTable" width="100%">
  <@table.thead>
  	<td rowspan="2">学年</td>
  	<td rowspan="2">学期</td>
  	<td rowspan="2">被评人次</td>
   <#list markSegments as seg>
     <#if seg_index=0>
     <td colspan="2">${seg.min+1}以上</td>
     <#elseif seg_has_next>
     <td colspan="2">${seg.min}-${seg.max}</>
     <#else>
     <td colspan="2">${seg.max+1}以下</td>
     </#if>
   </#list>
   <td rowspan="2">优良率(%)</td>
  </@>
  <tr align="center">
    <#list markSegments as seg>
      <td>人次</td><td>比例(%)</td>
    </#list>
  </tr>
  <#list condtionCalendars?sort_by("start")?reverse as calendar>
  	<tr align="center">
		<td>${calendar.year}</td>
		<td>${calendar.term}</td>
		<td>${displayMap[calendar.id+'all']?default(0)}</td>
		<#list markSegments as seg>
			<td>${displayMap[calendar.id+"-"+seg_index]?default(0)}</td>
			<td><#if displayMap[calendar.id+'all']?exists>${((displayMap[calendar.id+"-"+seg_index]?default(0)/displayMap[calendar.id+'all'])*100)?string("##0.0#")}<#else>0.0</#if></td>
		</#list>
		<td><#if displayMap[calendar.id+'all']?exists>${((displayMap[calendar.id+'excellent']?default(0)/displayMap[calendar.id+'all'])*100)?string("#0.0#")}<#else>0.0</#if></td>
  	</tr>
  </#list>
  <tr align="center">
  	 <td colspan="2">合计</td> 
  	 <td>${displayMap['0-all']?default(0)}</td>
  	 <#list markSegments as seg>
			<td>${displayMap["0-"+seg_index]?default(0)}</td>
			<td><#if displayMap['0-all']?exists>${((displayMap["0-"+seg_index]?default(0)/displayMap['0-all'])*100)?string("##0.0#")}<#else>0.0</#if></td>
	</#list>
	<td><#if displayMap['0-all']?exists>${((displayMap['0-excellent']?default(0)/displayMap['0-all'])*100)?string("#0.0#")}<#else>0.0</#if></td>
  </tr>
</@>
注:1.分数>=${RequestParameters['excellentMark']?default(80)}分为优良<br>
   2.你统计学年度学期包括<#list condtionCalendars?sort_by("start") as calendar>${calendar.year}-${calendar.term}<#if calendar_has_next>,</#if></#list>

</body>
<#include "/templates/foot.ftl"/>