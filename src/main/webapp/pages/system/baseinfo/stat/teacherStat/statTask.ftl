<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" onload="f_frameStyleResize(parent)">
 <#assign titleCountMap={}>
 <#assign whatCount=0>
 <#assign titles=stat.subItemEntities?sort_by("name")>
 <@table.table id="statTable" sortable="true" width="100%">
 <@table.thead>
      <@table.td id="what.name" width="150px" text="学年学期"/>
      <#list titles as title>
      <td><@i18nName title?if_exists/></td>
      </#list>
      <td>合计</td>
  </@>
  <@table.tbody datas=stat.items?sort_by(["what","start"]);statItem>
      <td>${statItem.what.year} ${statItem.what.term}</td>
      <#assign localWhatCount=0/>
      <#list titles as title>
      <#assign titleCount=(statItem.getItem(title).countors[0])?default(0)>
      <#assign titleCountMap=titleCountMap + {(title.id)?default("")?string:(titleCount+titleCountMap[(title.id)?default("")?string]?default(0))}>
      <#assign localWhatCount=localWhatCount + titleCount>
      <td><#if titleCount!=0>${titleCount}</#if></td>
      </#list>
      <td>${localWhatCount}</td>
      <#assign whatCount=localWhatCount + whatCount>
  </@>
  <tr class="darkColumn" style="text-align:center; font-weight: bold">
     <td >合计</td>
      <#list titles as title>
      <td><#if titleCountMap[(title.id)?default("")?string]!=0>${titleCountMap[(title.id)?default("")?string]}</#if></td>
      </#list>
      <td>${whatCount}</td>
  </tr>
 </@>
 <#list 1..5 as i><br></#list>
</body>
<#include "/templates/foot.ftl"/>