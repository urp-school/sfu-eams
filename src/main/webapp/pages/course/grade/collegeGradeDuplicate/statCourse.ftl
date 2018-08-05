<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="statBar"></table>
<script>
   var bar = new ToolBar("statBar","成绩分段统计（实践）",null,true,true);
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addClose("<@msg.message key="action.close"/>");
</script>
<style>
.statTable{
  width:60%;
  align:center;
}
</style>
<#list courseStats as courseStat>
     <#assign course=courseStat.course>
 	 <div align='center'><h3><@i18nName systemConfig.school/>成绩分段统计表</h3></div>
 	 <div align='center'>${courseStat.calendar.year}年度 ${courseStat.calendar.term}</div><br> 	 
 	 <table width='90%' align="center" border='0' style="font-size:13px">
 	 	<tr>
 	 	    <td width='40%'><@msg.message key="attr.courseName"/>:<@i18nName course/></td>
 	 	</tr>
 	 </table>
 	 <@table.table width="90%" align="center">
 	    <tr>
 	    <td>
 	       分段依据：
 	    </td>
 	    <#list courseStat.scoreSegments as seg>
 	     <td>${seg.min?string("##.#")}-${seg.max?string("##.#")}</td>
 	    </#list>
 	    </tr>
 	 </@>
   <#list courseStat.gradeSegStats as gradeStat>
   <p></p>
   <div align="center"><@i18nName gradeStat.gradeType/> 分段统计表. 人数(${gradeStat.stdCount})平均分：${(gradeStat.average?string("##.#"))?default('')} 最高分：${(gradeStat.heighest?string("##.#"))?default('')} 最低分：${(gradeStat.lowest?string("##.#"))?default('')}</div>
   <@table.table width="90%" align="center">
     <@table.thead>
      <td>成绩段</td>
      <td>人数</td>
      <td>占总人数的比例</td>
     </@>
     <@table.tbody datas=gradeStat.scoreSegments;seg>
      <td>${seg.min?string("##.#")}-${seg.max?string("##.#")}</td>
      <td>${seg.count}</td>
      <td>${((seg.count/gradeStat.stdCount)*100)?string("##.#")}%</td>
     </@>
   </@>
   </#list>
    <#if courseStat_has_next>
    <div style='PAGE-BREAK-AFTER: always'></div>
    </#if>
</#list>
</body>
<#include "/templates/foot.ftl"/>