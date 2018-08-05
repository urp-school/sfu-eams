<#include "/templates/head.ftl"/>
 <body>
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","挂牌课汇总(${tasks?size})个",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
  bar.addBack("<@msg.message key="action.back"/>");
 </script>
 <table width="100%" class="listTable" >
    <tr align="center" class="darkColumn">
      <td width="8%"><@bean.message key="attr.taskNo"/></td>
      <td width="20%"><@bean.message key="attr.courseName"/></td>
      <td width="20%"><@bean.message key="entity.teachClass"/></td>
      <td width="10%"><@bean.message key="entity.teacher"/></td>
      <td width="4%"><@bean.message key="attr.stdNum"/></td>
      <td width="4%"><@bean.message key="attr.credit"/></td>
      <td width="4%">周时</td>
      <td width="4%">周数</td>
      <td width="4%"><@bean.message key="attr.creditHour"/></td>
    </tr>

    <#list tasks?sort_by(["course","name"]) as task>
   	  <#if task_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if task_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" >
      <td>${task.seqNo?if_exists}</td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>      
      <td>${task.teachClass.name?html}</td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td>${task.teachClass.planStdCount}</td>
      <td>${task.course.credits}</td>
      <td>${task.arrangeInfo.weekUnits}</td>
      <td>${task.arrangeInfo.weeks}</td>
      <td><input type="hidden" name="${task.id}" value="<#if task.isConfirm == true>1 <#else>0</#if>"/>
       ${task.arrangeInfo.overallUnits}
      </td>
    </tr>
	</#list>
	</table>
</body>
<#include "/templates/foot.ftl"/> 