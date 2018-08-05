<#include "/templates/head.ftl"/>
 <body>
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","统计结果",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
  bar.addBack("<@msg.message key="action.back"/>");
 </script>
<@cewolf.chart  id="line"  title=" ${teachCalendar?if_exists.year?if_exists}学年度 ${teachCalendar?if_exists.term?if_exists}学期 汇总情况"  type="line"  
  xaxislabel="Page"  yaxislabel="Views" showlegend=true
  backgroundimagealpha=0.5>   
 <@cewolf.data>
    <@cewolf.producer id="pageViews"/>    
 </@cewolf.data>
 </@cewolf.chart>
 <p>
 <@cewolf.img chartid="line" renderer="cewolf" width=800 height=600/>
 <body>
 <#include "/templates/foot.ftl"/>