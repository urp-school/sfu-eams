<#macro displayStatYearTable(stats,entityName,years)>
 <#assign yearCountMap={}>
 <#assign whatCount=0>
 <@table.table id="statTable" width="100%">
 <@table.thead>
      <@table.td id="what.name" width="150px" name="${entityName}"/>
      <#list years as year>
      <td>${year}</td>
      </#list>
      <td>合计</td>
  </@>
  <@table.tbody datas=stats;statItem>
      <td><@i18nName statItem.what/></td>
      <#assign localWhatCount=0/>
      <#list years as year>
      <#assign yearCount=(statItem.getItem(year).countors[0])?default(0)>
      <#assign yearCountMap=yearCountMap + {year:(yearCount+yearCountMap[year]?default(0))}>
      <#assign localWhatCount=localWhatCount + yearCount>
      <td><#if yearCount!=0>${yearCount}</#if></td>
      </#list>
      <td>${localWhatCount}</td>
      <#assign whatCount=localWhatCount + whatCount>
  </@>
  <tr align="center">
     <td >合计</td>
      <#list years as year>
      <td><#if yearCountMap[year]!=0>${yearCountMap[year]}</#if></td>
      </#list>
      <td>${whatCount}</td>
  </tr>
 </@>
</#macro>