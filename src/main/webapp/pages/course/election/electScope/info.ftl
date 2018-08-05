<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0" >
<table id="bar"></table>
<table class="listTable" width="100%">
  <tr><td colspan="6" class="darkColumn">选课范围</td></tr>
  <tr class="darkColumn">
  	<td width="10%">入学年份</td>
   <td width="20%">学生类别</td>
   <td width="20%">院系</td>
   <td width="10%">专业</td>
   <td width="10%">专业方向</td>
   <td width="40%">班级</td>
 </tr>
	   <#list scopes as scope>
	   <#if scope['startNo']?exists><#else>
	   <tr>
	     <td>${scope['enrollTurns']?if_exists}</td>
	     <td><@getBeanListNames scope['stdTypes']/></td>
	     <td><@getBeanListNames scope['departs']/></td>
	     <td><@getBeanListNames scope['specialities']/></td>
	     <td><@getBeanListNames scope['aspects']/></td>
	     <td><@getBeanListNames scope['adminClasses']/></td>
	   </tr>
	   </#if>
	   </#list>
</table>
<br>
<table class="listTable" width="100%">
<tr><td colspan="2" class="darkColumn">学号段</td></tr>
  <tr class="darkColumn">
  	<td>起始学号</td>
   <td>截至学号</td>
 </tr>
	   <#list scopes as scope>
	   <#if scope['startNo']?exists>
	   <tr>
	     <td>${scope['startNo']}</td>
	     <td>${scope['endNo']}</td>
	   </tr>
	   </#if>
	   </#list>
</table>
<pre>
  说明：对于多个选课范围（分范围和学号段），只要学生符合其中一个范围，就可以选择这们课程。
  在一个范围中，首先查看班级，如果学生的班级符合班级要求即可，否则查看同一范围的其他信息。
  
<script>
  var bar =new ToolBar("bar","选课范围信息",null,true,true);
  bar.addBack("<@msg.message key="action.back"/>");
</script>
</body>
<#include "/templates/foot.ftl"/>