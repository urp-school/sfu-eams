<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <#assign gradeStatus={'0':'新添加','1':'已确认','2':'已发布'}/>
 <table id="gradeBar" width="100%"></table>
 <table class="infoTable">
   <tr>
     <td class="title"><@msg.message key="attr.stdNo"/></td>
     <td class="content">${courseGrade.std.code}</td>
     <td class="title"><@msg.message key="attr.taskNo"/></td>
     <td class="content">${courseGrade.taskSeqNo?if_exists}</td>
     <td class="title">是否通过</td>
     <td class="content"><#if courseGrade.isPass>是<#else><font color="red">否</#if></td>
  </tr>
  <tr>
     <td class="title"><@msg.message key="attr.personName"/></td>
     <td class="content"><@i18nName courseGrade.std/></td>
     <td class="title"><@msg.message key="attr.courseNo"/></td>
     <td class="content">${courseGrade.course.code}</td>
     <td class="title"><@msg.message key="attr.courseName"/></td>
     <td class="content"><@i18nName courseGrade.course/></td>
  </tr>
  <tr>
     <td class="title">成绩</td>
     <td class="content">${(courseGrade.scoreDisplay)?if_exists}</td>
     <td class="title"><@msg.message key="entity.courseType"/></td>
     <td class="content"><@i18nName courseGrade.courseType/></td>
     <td class="title">修读类别</td>
     <td class="content"><@i18nName courseGrade.courseTakeType?if_exists/></td>
  </tr>
  <tr>
     <td class="title">总评</td>
     <td class="content">${(courseGrade.getScoreDisplay(7))?if_exists}</td>
     <td class="title"><@msg.message key="attr.credit"/></td>
     <td class="content">${(courseGrade.credit)?if_exists}</td>
     <td class="title">绩点</td>
     <td class="content">${(courseGrade.GP?string("#.##"))?if_exists}</td>
  </tr>
  <tr>
     <td class="title">状态</td>
     <td class="content">${gradeStatus[courseGrade.status?string]}</td>
     <td class="title"><@msg.message key="entity.markStyle"/></td>
     <td class="content"><@i18nName courseGrade.markStyle/></td>
     <td class="title">创建时间</td>
     <td class="content"><#if courseGrade.createAt?exists>${courseGrade.createAt?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
  </tr>
 </table>
 <#if (courseGrade.task.gradeState)?exists>
   <#assign gradeState=courseGrade.task.gradeState>
 </#if>
 <@table.table width="100%">
    <@table.thead>
     <@table.td text="成绩种类"/>
     <@table.td text="考试情况"/>
     <@table.td text="得分"/>
     <@table.td text="百分比"/>
     <@table.td text="是否通过"/>
     <@table.td text="状态"/>
    </@>
    <@table.tbody datas=courseGrade.examGrades;examGrade>
     <td><@i18nName examGrade.gradeType/></td>
     <td><@i18nName examGrade.examStatus/></td>
     <td>${(examGrade.score?string("#.##"))?if_exists}</td>
     <td><#if gradeState?exists>${(gradeState.getPercent(examGrade.gradeType)?string.percent)?if_exists}</#if></td>
     <td><#if examGrade.isPass>是<#else><font color="red">否</#if></td>
     <td>${gradeStatus[examGrade.status?string]}</td>
    </@>
 </@>
 <table id="gradeAlterInfoBar" width="100%"></table>
 <#include "../courseGradeAlterInfo.ftl"/>
  <script>
    var bar = new ToolBar("gradeBar","学生单科成绩信息（实践）",null,true,true);
    bar.addItem("<@msg.message key="action.print"/>","print()");
    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    
    var bar = new ToolBar("gradeAlterInfoBar","成绩修改信息",null,true,true);
 </script>
</body>
<#include "/templates/foot.ftl"/>
