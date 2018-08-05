 <@table.table width="100%" align="center" sortable="true" id="listTable">
   <@table.thead>
     <@table.selectAllTd id="awardId"/>
     <@table.sortTd id="award.std.code" name="attr.stdNo"/>
     <@table.sortTd id="award.std.name" name="attr.personName"/>
     <@table.sortTd id="award.std.department.name" name="entity.department"/>
     <@table.sortTd id="award.name" text="获奖名称"/>
     <@table.sortTd id="award.type.name" text="获奖级别"/>
     <@table.sortTd id="award.presentOn" text="颁发时间"/>
     <@table.sortTd id="award.depart" text="颁发部门"/>
     <@table.sortTd id="award.isValid" text="是否有效"/>
   </@>
   <@table.tbody datas=awards;award>
     <@table.selectTd id="awardId" value=award.id/>
     <td><a href="studentDetailByManager.do?method=detail&stdId=${award.std.id}" title="查看学生基本信息">${award.std.code}</A></td>
     <td><@i18nName award.std/></td>
     <td><@i18nName award.std.department/></td>
     <td>${award.name?default('')}</td>
     <td><@i18nName award.type/></td>
     <td>${award.presentOn}</td>
     <td>${award.depart}</td>
     <td>${award.isValid?default(true)?string("有效","无效")}
     <#if !award.isValid?default(true)><#if award.withdrawOn?exists>&nbsp;撤销时间：${award.withdrawOn?string("yyyy-MM-dd")}</#if></#if></td>
   </@>
 </@>