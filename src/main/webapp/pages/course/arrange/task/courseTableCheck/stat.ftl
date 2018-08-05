<#include "/templates/head.ftl"/>
 <body >
 <table id="myBar"></table>
 <script>
  var bar = new ToolBar("myBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
  bar.addBack("<@msg.message key="action.back"/>");
 </script>
 <#if stats?size==0>没有课表核对记录</#if>
<#list stats?sort_by(['what','code']) as stat>
 <#assign years= stat.subItemEntities?sort>
 <#assign yearCountMap={}>
 <#assign confirmCountMap={}>
 <#assign whatCount=0>
 <#assign confirmWhatCount=0>
 <div align="center"><br><B><@i18nName stat.what/>课表核对情况(核对人数/总人数)</B></div>
 <@table.table id="statTable" width="100%">
 <@table.thead>
      <@table.td id="what.name" width="150px" name="entity.department"/>
      <#list years as year>
      <td>${year}</td>
      </#list>
      <td>合计</td>
  </@>
  <@table.tbody datas=stat.items?sort_by(["what","name"]);statItem>
      <td><@i18nName statItem.what/></td>
      
      <#assign localWhatCount=0/>
      <#assign localConfirmWhatCount=0/>
      
      <#list years as year>
      <#assign yearCount=(statItem.getItem(year).countors[0])?default(0)>
      <#assign yearConfirmCount=(statItem.getItem(year).countors[1])?default(0)>
      
      <#assign yearCountMap=yearCountMap + {year:(yearCount+yearCountMap[year]?default(0))}>
      <#assign confirmCountMap=confirmCountMap + {year:(yearConfirmCount+confirmCountMap[year]?default(0))}>
      
      <#assign localWhatCount=localWhatCount + yearCount>
      <#assign localConfirmWhatCount=localConfirmWhatCount + yearConfirmCount>
      <td>${yearConfirmCount}/${yearCount}</td>
      </#list>
      
      <td>${localConfirmWhatCount}/${localWhatCount}</td>
      
      <#assign whatCount=localWhatCount + whatCount>
      <#assign confirmWhatCount=localConfirmWhatCount + confirmWhatCount>
  </@>
  <tr align="center">
     <td >合计</td>
      <#list years as year>
      <td>${confirmCountMap[year]}/${yearCountMap[year]}</td>
      </#list>
      <td>${confirmWhatCount}/${whatCount}</td>
  </tr>
</@>
</#list>
<p>以下是在校学生统计结果</p>
<#include "/pages/std/stat/stdOnCampusStat/statYearTable.ftl"/> 
<#list onCampusStats?sort_by(['what','code']) as stat>
<div align="center"><br><B><@i18nName stat.what/> 各年级学生分布情况</B></div>
<@displayStatYearTable stat.items?sort_by(['what','name']),"entity.department",stat.subItemEntities?sort/>
</#list>
</body>
<#include "/templates/foot.ftl"/> 