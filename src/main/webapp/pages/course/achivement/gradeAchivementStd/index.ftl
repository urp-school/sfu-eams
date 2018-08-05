<#include "/templates/head.ftl"/>
<body >
 <table id="gradeAchivementListBar"></table>
  <@table.table  width="100%" id="listTable" sortable="true">
    <@table.thead>
      	<@table.sortTd width="10%" text="测评年度" id="gradeAchivement.toSemester.year"/>
        <@table.sortTd width="10%" text="智育" id="gradeAchivement.ieScore"/>
        <@table.sortTd width="10%" text="体育" id="gradeAchivement.peScore"/>
        <@table.sortTd width="10%" text="德育" id="gradeAchivement.moralScore"/>
        <@table.sortTd width="10%" text="综合" id="gradeAchivement.score"/>
        <@table.sortTd width="10%" text="绩点" id="gradeAchivement.gpa"/>
        <@table.td width="10%" text="是否全部及格" id="gradeAchivement.gradePassed" />
        <@table.sortTd width="10%" text="四级通过" id="gradeAchivement.cet4Passed"/>
        <@table.td width="10%" text="查看详情" />
    </@>
    <@table.tbody datas=gradeAchivements;gradeAchivement>
      	<td>${(gradeAchivement.toSemester.year)!}</td>
      	<td>${(gradeAchivement.ieScore)!}</td>
      	<td>${(gradeAchivement.peScore)!}</td>
      	<td>${(gradeAchivement.moralScore)!}</td>
      	<td>${(gradeAchivement.score)!}</td>
      	<td>${(gradeAchivement.gpa?string("0.###"))!}</td>
      	<td><#if gradeAchivement.ieScore?exists>${(gradeAchivement.gradePassed)?string("是","<font color='red'>否</font>")}</#if></td>
      	<td>${(gradeAchivement.cet4Passed)?string("是","<font color='red'>否</font>")}</td>
      	<td><a href="gradeAchivementStd.do?method=info&gradeAchivement.id=${gradeAchivement.id}">》》</a>
    </@>
  </@>
  <script>
  	var bar=new ToolBar("gradeAchivementListBar","综合测评成绩列表",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addBack();
  </script>
  <#if gradeAchivements?size==0>
  <p>尚无综合测评记录</p>
  </#if>
</body> 
<#include "/templates/foot.ftl"/> 