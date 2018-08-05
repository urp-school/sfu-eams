<#include "/templates/head.ftl"/>
<#include "/templates/print.ftl"/>
<#if RequestParameters['toXLS']?exists>
<#else>
<body  onload="SetPrintSettings()">
<table id="teachPlanInfoBar"></table>
<script>
  var bar = new ToolBar("teachPlanInfoBar",'<@msg.message key="plan.planSearch"/>',null,true,true);
  bar.setMessage('<@getMessage/>')
  bar.addItem("<@msg.message key="plan.asCredit"/>","getInfoWith('credit=1')");
  bar.addItem("<@msg.message key="plan.asWeekHour"/>","getInfoWith('weekHour=1')");
  bar.addItem("<@bean.message key="action.print"/>","print()");
  bar.addItem("<@bean.message key="action.export"/>","getInfoWith('<#if RequestParameters['credit']?exists>credit=1<#else>weekHour=1</#if>&toXLS=1')");
  bar.addBack("<@bean.message key="action.back"/>");
</script>
</#if>
<style>
/*数据列表的样式单*/
.listTable {
	border-collapse: collapse;
    border:solid;
	border-width:1px;
    border-color:#006CB2;
  	vertical-align: middle;
  	font-style: normal; 
	font-size: 10pt; 
}
table.listTable td{
	border:solid;
	border-width:0px;
	border-right-width:1;
	border-bottom-width:1;
	border-color:#006CB2;

}
</style>
<#if RequestParameters['credit']?exists>
   <#include "planInfoTable.ftl"/>
<#else>
  <#include "planInfoTableWithWeekHour.ftl"/>
</#if>
 </body>
<#include "/templates/foot.ftl"/>

<script>
    function getInfoWith(kind){
        self.location="teachPlanSearch.do?method=info&teachPlan.id=${teachPlan.id}&"+kind;
    }
</script>