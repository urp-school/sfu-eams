<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar" width="100%"></table>
    <#if catalogues?exists == false || catalogues?size == 0>
    	<p style="font-size:12px; color:blue">当前《教室级差价目表》没有设定!</p>
    </#if>
  <#list catalogues as catalogue>
    <div align="center" style="font-size:20px"><B><@i18nName systemConfig.school/>教室借用级差收费标准<#if catalogue.schoolDistrict?exists>(<@i18nName catalogue.schoolDistrict/>)</#if></B></div>
    <br>
 	<@table.table width="90%" align="center" sortable="true" id="roomPrice">
	 	<@table.thead>
	 	    <@table.sortTd text="教室类别" id="roomPrice.roomConfigType.name"/>
	 	    <@table.sortTd text="座位数（座）" id="roomPrice.minSeats"/>
	 	    <@table.sortTd text="收费标准（元/小时）" id="roomPrice.price"/>
	 	</@>
	 	<@table.tbody datas=catalogue.prices?sort_by(["roomConfigType","name"]);roomPrice>
	 		<td width="20%">${roomPrice.roomConfigType.name}</td>
	 		<td width="20%">${roomPrice.minSeats} ~ ${roomPrice.maxSeats}</td>
	 		<td width="20%">${roomPrice.price}</td>
		</@>
 	</@>
 	<br>
 	<table width="90%" align="center"><tr><td align="right"><@i18nName catalogue.department?if_exists/><br>${catalogue.publishedOn?string("yyyy-MM-dd")}</td></tr></table>
 	</#list>
 	<script>
		var bar = new ToolBar("bar","教室级差价目表",null,true,true);
		bar.setMessage('<@getMessage/>');
	    bar.addPrint("<@msg.message key="action.print"/>");
 	</script>
 </body>
<#include "/templates/foot.ftl"/>
