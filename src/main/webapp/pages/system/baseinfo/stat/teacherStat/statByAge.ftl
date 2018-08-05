<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','年龄结构统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
   function statistic(form){
   		var errors="";
   		if(!/^\d{1,2}$/.test(form.start.value)){
   			errors+="起始年龄要是数字,并且要小于100\n";
   		}
   		if(!/^\d{1,2}$/.test(form.span.value)){
   			errors+="年龄跨度要是数字,并且要小于100\n";
   		}
   		if(!/^\d{1,2}$/.test(form.count.value)){
   			errors+="显示数目要是数字,并且要小于100\n";
   		}
   		if(""!=errors){
   			alert(errors);
   			return;
   		}
   		form.action="teacherStat.do?method=statByAge";
   		form.submit();
   }
</script>
<table width="100%" align="center" class="listTable">
	<form name="conditionForm" method="post" action="" onsubmit="return false;">
	<input type="hidden" name="doStatistic" value="statistic">
	<tr class="darkColumn">
		<td colspan="8" align="center">职称年龄统计配置</td>
	</tr>
	<tr>
		<td class="title">起始年龄：<input type="text" name="start" size ="10" maxlength="3" value="${RequestParameters['start']}"></td>
		<td class="title">年龄跨度：<input type="text" name="span" size ="10" maxlength="3" value="${RequestParameters['span']}"></td>
		<td class="title">显示数目：<input type="text" name="count" size ="10" maxlength="7" value="${RequestParameters['count']}"></td>
		<td  align="center">
			<input type="button" name="button1" value="统计"  class="buttonStyle" onclick="statistic(this.form)">
		</td>
	</tr>
	</form>
</table>
<#assign count=0>
<@table.table id="listTable" width="100%">
  <@table.thead>
   <#list ageSegs as seg>
     <#if seg_index=0>
     <td colspan="2">${seg.max+1}以下</td>
     <#elseif seg_has_next>
     <td colspan="2">${seg.min}-${seg.max}</>
     <#else>
     <td colspan="2">${seg.min+1}以上</td>     
     </#if>
     <#assign count=count+seg.count>
   </#list>
     <td>总计</td>
  </@>
  <tr align="center">
    <#list ageSegs as seg>
      <td>人数</td><td>比例</td>
    </#list>
    <td>总数</td>
  </tr>
  <tr align="center">
  <#list ageSegs as seg>
  <td>${seg.count}</td>
  <td><#if count!=0>${(seg.count/count)*10000?int/100}%<#else>0.00%</#if></td>
  </#list>
  <td>#{count}</td>
  </tr>
</@>
<#list 1..5 as i><br></#list>
<body>
<#include "/templates/foot.ftl"/>