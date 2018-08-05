<@table.table width="100%">
    <@table.thead>
     <@table.td text="成绩种类"/>
     <@table.td text="修改前"/>
     <@table.td text="修改后"/>
     <@table.td text="修改人"/>
     <@table.td text="修改时间"/>
     <@table.td text="备注"/>
    </@>
    <tbody>
    <#list courseGrade.alterInfos?sort_by("modifyAt") as alterInfo>
      <tr align="center">
      <td>总成绩</td>
      <td>${(alterInfo.getScoreDisplay(alterInfo.scoreBefore?default(0)))}</td>
      <td>${(alterInfo.getScoreDisplay(alterInfo.scoreAfter?default(0)))}</td>
      <td>${alterInfo.modifyBy?if_exists.name}</td>
      <td>${(alterInfo.modifyAt?string("yyyy-MM-dd HH:mm:ss"))?if_exists}</td>
      <td>${alterInfo.remark?if_exists}</td>
      </tr>
    </#list>
    <#list courseGrade.examGrades as examGrade>
     <#list examGrade.alterInfos?sort_by("modifyAt") as alterInfo>
      <tr align="center">
      <td><@i18nName examGrade.gradeType/></td>
      <td>${(alterInfo.getScoreDisplay(alterInfo.scoreBefore?default(0)))}</td>
      <td>${(alterInfo.getScoreDisplay(alterInfo.scoreAfter?default(0)))}</td>
      <td>${alterInfo.modifyBy?if_exists.name}</td>
      <td>${(alterInfo.modifyAt?string("yyyy-MM-dd HH:mm:ss"))?if_exists}</td>
      <td>${alterInfo.remark?if_exists}</td>
      </tr>
      </#list>
    </#list>
   </tbody>
 </@>