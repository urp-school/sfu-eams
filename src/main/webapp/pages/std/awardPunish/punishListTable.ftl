     <@table.table width="100%" align="center" sortable="true" id="listTable">
	   <@table.thead>
	     <@table.selectAllTd id="punishId"/>
	     <@table.sortTd id="punish.std.code" name="attr.stdNo"/>
	     <@table.sortTd id="punish.std.name" name="attr.personName"/>
	     <@table.sortTd id="punish.std.department.name" name="entity.department"/>
	     <@table.sortTd id="punish.name" text="处分名称"/>
	     <@table.sortTd id="punish.type.name" text="处分类别"/>
	     <@table.sortTd id="punish.presentOn" text="处分时间"/>
	     <@table.sortTd id="punish.depart" text="部门"/>
	     <@table.sortTd id="punish.isValid" text="是否有效"/>
	   </@>
	   <@table.tbody datas=punishs;punish>
         <@table.selectTd id="punishId" value=punish.id/>
         <td><a href="studentDetailByManager.do?method=detail&stdId=${punish.std.id}" title="查看学生基本信息">${punish.std.code}</A></td>
         <td><@i18nName punish.std/></td>
         <td><@i18nName punish.std.department/></td>
         <td>${punish.name?default('')}</td>
	     <td><@i18nName punish.type/></td>
	     <td>${punish.presentOn?if_exists}</td>
	     <td>${punish.depart}</td>
	     <td>${punish.isValid?default(true)?string("有效","无效")}<#if !punish.isValid?default(true)><#if punish.withdrawOn?exists>&nbsp;撤销时间：${punish.withdrawOn?string("yyyy-MM-dd")}</#if></#if></td>
	   </@>
     </@>
