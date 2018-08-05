<#include "/templates/head.ftl"/>
 <body >
 <table id="myBar"></table>
 <script>
  var bar = new ToolBar("myBar","按院系所统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>
 <#include "statYearTable.ftl"/>
 <div align="center"><br><B>各部门学生分布情况</B></div>
 <@displayStatYearTable departStat.items?sort_by(['what','name']),"entity.department",departStat.subItemEntities?sort/>
 
<#list stats?sort_by(['what','name']) as stat>
 <div align="center"><br><B><@i18nName stat.what/> 各年级学生分布情况</B></div>
 <@displayStatYearTable stat.items?sort_by(['what','code']),"entity.studentType",stat.subItemEntities?sort/>
</#list>
</body>
<#include "/templates/foot.ftl"/> 