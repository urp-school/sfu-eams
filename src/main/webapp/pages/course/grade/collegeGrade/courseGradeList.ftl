 <#assign gradeStatus={'0':'新增','1':'确认','2':'已发布'}>
 
 <@table.table width="100%" id="listTable" sortable="true">
   <@table.thead>
    <@table.selectAllTd id="courseGradeId"/>
    <@table.sortTd id="std.code" name="attr.stdNo"/>
    <@table.sortTd id="std.name" name="attr.personName"/>
    <@table.td name="entity.courseType"/>
    <@table.td text="修读类别"/>
    <#list gradeTypes as gradeType>
    <td class="tableHeaderSort" id="gradeType.${gradeType.shortName}"><@i18nName gradeType/></td>
    </#list>
    <@table.sortTd id="GP" text="绩点"/>
    <@table.td text="是否通过"/>
	<@table.td width="8%" text="状态"/>
   </@>
   <@table.tbody datas=grades;grade>
     <@table.selectTd id="courseGradeId" type="checkbox" value=grade.id/>
     <td>${grade.std.code}</td>
     <td><@i18nName grade.std/></td>
     <td><@i18nName grade.courseType/></td>
     <td><@i18nName grade.courseTakeType/></td>
     <#list gradeTypes as gradeType>
     <td>${(grade.getScoreDisplay(gradeType.id))?if_exists}</td>
     </#list>
     <td>${((grade.GP*100)?int/100)?string('#0.00')?if_exists}</td>
     <td><#if grade.isPass>是<#else><font color="red">否</#if></td>
     <td>${gradeStatus[grade.status?string]}</td>
   </@>
 </@>