<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="printBar" width="100%"></table>
 	<script>
 	  var bar = new ToolBar("printBar","注册统计",null,true,true);
 	  bar.setMessage('<@getMessage/>');
 	  bar.addPrint();
 	  bar.addBack();
 	</script>
 	 <#if courseStat.items?size==0>没有数据
 	 <#else>
 	 <@displayStatcalendarTable courseStat.items?sort_by(['what','code']),"attr.yearAndTerm",courseStat.subItemEntities?sort/>
     </#if>
     
<#macro displayStatcalendarTable(stats,entityName,calendars)>
 <#assign registCountMap={}>
 <#assign stdCountMap={}>
 <#assign registAllCount=0>
 <#assign stdAllCount=0>
 <@table.table id="statTable" sortable="true" width="100%">
 <@table.thead>
      <@table.td id="what.name" width="150px" name="${entityName}"/>
      <#list calendars as calendar>
      <td colspan="2">${calendar.year}-${calendar.term}</td>
      </#list>
      <td colspan="2">合计</td>
  </@>
  <tr align="center">
      <td></td>
      <#list calendars as calendar>
      <td>计划</td><td>任务</td>
      </#list>
      <td>计划</td><td>任务</td>
  </tr>
  <@table.tbody datas=stats;statItem>
      <td><@i18nName statItem.what/></td>
      <#assign localWhatCount=0/>
      <#list calendars as calendar>
      <#assign registCount=(statItem.getItem(calendar).countors[0])?default(0)>
      <#assign registCountMap=registCountMap + {calendar:(registCount+registCountMap[calendar]?default(0))}>
      <#assign stdCount=(statItem.getItem(calendar).countors[1])?default(0)>
      <#assign stdCountMap=stdCountMap + {calendar:(stdCount+stdCountMap[calendar]?default(0))}>
      <td><#if registCount!=0>${registCount}<#else>&nbsp;</#if></td>
      <td><#if stdCount!=0>${stdCount}<#else>&nbsp;</#if></td>
      </#list>
      <#assign registcalendarCount=(statItem.sumItemCounter(0))?default(0)/>
      <#assign stdcalendarCount=(statItem.sumItemCounter(1))?default(0)/>
      <#assign registAllCount=registAllCount+registcalendarCount/>
      <#assign stdAllCount=stdAllCount+stdcalendarCount/>
      <td><#if registcalendarCount!=0>${registcalendarCount}</#if></td>
      <td><#if stdcalendarCount!=0>${stdcalendarCount}</#if></td>
  </@>
  <tr class="darkColumn" style="text-align:center; font-weight: bold">
     <td >合计</td>
      <#list calendars as calendar>
      <td><#if registCountMap[calendar]!=0>${registCountMap[calendar]}<#else>&nbsp;</#if></td>
      <td><#if stdCountMap[calendar]!=0>${stdCountMap[calendar]}<#else>&nbsp;</#if></td>
      </#list>
      <td>${registAllCount}</td><td>${stdAllCount}</td>
  </tr>
 </@>
</#macro>
<#list 1..5 as i><br></#list>
</body>
<#include "/templates/foot.ftl"/>