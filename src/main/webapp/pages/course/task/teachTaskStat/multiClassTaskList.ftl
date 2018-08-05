<#include "/templates/head.ftl"/>
 <body>
 <table id="gpBar"></table>
 <script>
  var bar = new ToolBar("gpBar","合班汇总(${tasks?size})个",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.print"/>","print()");
  bar.addBack("<@msg.message key="action.back"/>");
 </script>
 <table width="100%" class="listTable" >
    <tr align="center" class="darkColumn">
      <td width="5%">序号</td>
      <td width="10%"><@bean.message key="attr.taskNo"/></td>
      <td width="20%"><@bean.message key="attr.courseName"/></td>
      <td width="40%">班级</td>
      <td width="10%"><@bean.message key="entity.teacher"/></td>
      <td width="5%"><@msg.message key="attr.credit"/></td>
      <td width="8%">计划<@bean.message key="attr.stdNum"/></td>
      <td width="14%">班级数</td>
    </tr>

    <#list tasks?sort_by(["course","name"]) as task>
   	  <#if task_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if task_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" >
      <td>${task_index+1}</td>
      <td>${task.seqNo?if_exists}</td>
      <td><A href="teachTask.do?method=info&task.id=${task.id}" title="<@bean.message key="info.task.info"/>"><@i18nName task.course/></A></td>      
      <td><@getBeanListNames task.teachClass.adminClasses/></td>
      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
      <td>${task.course.credits}</td>
      <td>${task.teachClass.planStdCount}</td>
      <td>${task.teachClass.adminClasses?size}</td>
    </tr>
	</#list>
	</table>
</body>
<#include "/templates/foot.ftl"/> 