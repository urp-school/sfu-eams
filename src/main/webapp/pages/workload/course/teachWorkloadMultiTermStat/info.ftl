<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','工作量明细表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addBack();
</script>
<table align="center" width="100%" class="listTable">
  <form name="listForm" method="post" action="" onsubmit="return false;">
  		<tr class="darkColumn">
  			<td>开课院系</td>
  			<td>教师</td>
  			<#list teachCalendars?if_exists?sort_by("start") as teachCalendar>
  			<td><#if teachCalendar?exists>${teachCalendar.year}/${teachCalendar.term}</#if></td>
  			</#list>
  			<td>合计</td>
  		</tr>
  		<#assign class="grayStyle">
  		<#list techerAndCalendarsMap['teachers']?if_exists as teacher>
  			<tr class="${class}">
  				<#if class="grayStyle"><#assign class="brightStyle"><#else><#assign class="grayStyle"></#if>
  				<td><#if depart?exists>${depart.name}</#if></td>
  				<td><#if teacher?exists>${teacher.name}</#if></td>
  				<#list teachCalendars?if_exists?sort_by("start") as teachCalendar>
  					<td>${techerAndCalendarsMap[teacher.id+"-"+teachCalendar.id]?default(0)?string("##0.0")}</td>
  				</#list>
  				<td>${techerAndCalendarsMap[teacher.id+"-0"]?default(0)?string("##0.0")}</td>
  			</tr>
  		</#list>
  		<tr  class="${class}">
  			<td colspan="2" align="center">合计</td>
  			<#list teachCalendars?if_exists?sort_by("start") as teachCalendar>
  					<td>${techerAndCalendarsMap["0-"+teachCalendar.id]?default(0)?string("##0.0")}</td>
  			</#list>
  			<td>${techerAndCalendarsMap["0-0"]?default(0)?string("##0.0")}</td>
  		</tr>
  </from>
</table>
  </body>
<#include "/templates/foot.ftl"/>