<#include "/templates/head.ftl"/>
 <body >
 <table id="myBar"></table>
 <script>
  var bar = new ToolBar("myBar","按学生类别统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
<#include "statYearTable.ftl"/> 
<div align="center"><br><B>各类别学生分布情况</B></div>
<@displayStatYearTable stdTypeStat.items?sort_by(['what','code']),"entity.studentType",stdTypeStat.subItemEntities?sort/>

<#list stats?sort_by(['what','code']) as stat>
<div align="center"><br><B><@i18nName stat.what/> 各年级学生分布情况</B></div>
<@displayStatYearTable stat.items?sort_by(['what','name']),"entity.department",stat.subItemEntities?sort/>
</#list>
</body>
<#include "/templates/foot.ftl"/> 