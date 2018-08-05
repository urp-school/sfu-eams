<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="printBar" width="100%"></table>
 	<script>
 	  var bar = new ToolBar("printBar","注册统计",null,true,true);
 	  bar.setMessage('<@getMessage/>');
 	  bar.addPrint();
 	  bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 	</script>
 	 <#if departStat.items?size==0>没有数据
 	 <#else>
 	 <@displayStatYearTable departStat.items?sort_by(['what','name']),"entity.department",departStat.subItemEntities?sort/>
     </#if>
<#macro displayStatYearTable(stats,entityName,years)>
 <#assign registCountMap={}/>
 <#assign stdCountMap={}/>
 <#assign registAllCount=0/>
 <#assign stdAllCount=0/>
 <@table.table id="statTable" sortable="true" width="100%">
 <@table.thead>
      <@table.td id="what.name" width="150px" name=entityName rowspan="2"/>
      <#list years as year>
      <@table.td colspan="3" text=year/>
      </#list>
      <@table.td colspan="3" text="合计"/><#--横向合计-->
  </@>
  <@table.thead>
      <#list 1..(years?size + 1) as i>
      <@table.td text="实到"/>
      <@table.td text="未到"/>
      <@table.td text="注册率"/>
      </#list>
  </@>
  <@table.tbody datas=stats;statItem>
      <td><@i18nName statItem.what/></td>
      <#assign localWhatCount=0/>
      <#list years as year>
      <#assign registCount=(statItem.getItem(year).countors[0])?default(0)>
      <#assign registCountMap=registCountMap + {year:(registCount+registCountMap[year]?default(0))}>
      <#assign stdCount=(statItem.getItem(year).countors[1])?default(0)>
      <#assign stdCountMap=stdCountMap + {year:(stdCount+stdCountMap[year]?default(0))}>
      <td><#if registCount!=0>${registCount}<#else>&nbsp;</#if></td>
      <td><#if stdCount!=0><A href='#' title='点击查看未注册学生名单' onclick="unRegisterStd(${statItem.what.id},'${year}')">${stdCount-registCount}</A><#else>&nbsp;</#if></td>
      <td><#if stdCount!=0>${(registCount*100/(stdCount*1.0))?string("##.##")}%<#else>&nbsp;</#if></td>
      </#list>
      <#--累加部门的注册应到和实到人数-->
      <#assign registDepartCount=(statItem.sumItemCounter(0))?default(0)/>
      <#assign stdDepartCount=(statItem.sumItemCounter(1))?default(0)/>
      <#assign registAllCount=registAllCount+registDepartCount/>
      <#assign stdAllCount=stdAllCount+stdDepartCount/>
      <td><#if registDepartCount!=0>${registDepartCount}</#if></td>
      <td><#if stdDepartCount!=0><A href='#' title='点击查看未注册学生名单' onclick="unRegisterStd(${statItem.what.id})">${stdDepartCount-registDepartCount}</A></#if></td>
      <td><#if stdDepartCount!=0>${(registDepartCount*100/(stdDepartCount*1.0))?string("##.##")}%</#if></td>
  </@>
  <tr align="center">
     <td >合计</td>
      <#list years as year>
      <td><#if registCountMap[year]!=0>${registCountMap[year]}<#else>&nbsp;</#if></td>
      <td><#if stdCountMap[year]!=0><A href='#' title='点击查看未注册学生名单' onclick="unRegisterStd(null,'${year}')">${stdCountMap[year]-registCountMap[year]}</A><#else>&nbsp;</#if></td>
      <td><#if registCountMap[year]!=0&&stdCountMap[year]!=0>${(registCountMap[year]*100/(stdCountMap[year]*1.0))?string("##.##")}%</#if></td>
      </#list>
      <td>${registAllCount}</td><td><A href='#' title='点击查看未注册学生名单' onclick="unRegisterStd(null,null)">${stdAllCount-registAllCount}</A></td><td><#if stdAllCount!=0>${(registAllCount*100/(stdAllCount*1.0))?string("##.##")}%</#if></td>
  </tr>
 </@>
</#macro>
<form name="actionForm" method="post" action="" onsubmit="return false;">
  <#list RequestParameters?keys as key>
  <input type="hidden" name="${key}" value="${RequestParameters[key]}"/>
  </#list>
</form>
<script>
  var form =document.actionForm;
  function unRegisterStd(departId,enrollYear){
     if(null!=departId){
       addInput(form,"std.department.id",departId);
     }
     if(null!=enrollYear){
       addInput(form,"std.enrollYear",enrollYear);
     }
     form.action='register.do?method=unregisterList';
     form.target="_blank";
     form.submit();
  }
</script>
</body>
<#include "/templates/foot.ftl"/>