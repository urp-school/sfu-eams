<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table cellpadding="0" cellspacing="0" width="100%" border="0">
		<tr height="28">
			<td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff"><B><@bean.message key="attr.graduate.graduateAuditStandardList"/></B></td>
		</tr>
		<tr>
			<td>
				<@table.table width="100%" align="center" id="auditStandardSearch">
					<@table.thead>
						<@table.selectAllTd id="auditStandardId"/>
						<@table.td name="attr.name" width="12%"/>
						<@table.td name="entity.studentType" width="10%"/>
						<@table.td text="指定选修课" width="10%"/>
						<@table.td name="attr.graduate.disauditCourseType"/>
						<@table.td text="多出学分可冲抵任意选修课的课程类别"/>
					</@>
					<@table.tbody datas=auditStandardList;auditStandard>
						<@table.selectTd id="auditStandardId" value=auditStandard.id/>
						<td>${auditStandard.name}</td>
					    <td><@i18nName auditStandard.studentType?if_exists/></td>
					    <td><@i18nName auditStandard.publicCourseType?if_exists/></td>
					    <#assign courseTypeDescriptionsValue = ""/>
					    <#assign courseTypeIdValue = ","/>
					    <#list auditStandard.disauditCourseTypes?if_exists as courseType>
					   		<#if courseType_has_next>
							    <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
							    <#if courseType.name?exists>
							    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + ","/>
						    	</#if>
						   	<#else>
							   	<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
							    <#if courseType.name?exists>
							    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string />
							    </#if>
					   		</#if>
					   </#list>
					   <td>${courseTypeDescriptionsValue}</td>
					   <#assign courseTypeDescriptionsValue = ""/>
					    <#assign courseTypeIdValue = ","/>
					    <#list auditStandard.convertableCourseTypes?if_exists as courseType>
					   		<#if courseType_has_next>
							    <#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
							    <#if courseType.name?exists>
							    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string + ","/>
						    	</#if>
						   	<#else>
							   	<#assign courseTypeIdValue = courseTypeIdValue + courseType.id?string + ","/>
							    <#if courseType.name?exists>
							    <#assign courseTypeDescriptionsValue = courseTypeDescriptionsValue + courseType.name?string />
							    </#if>
					   		</#if>
					   </#list>
					   <td>${courseTypeDescriptionsValue}</td>
					</@>
				</@>
			</td>
		</tr>
	</table>
	<@htm.actionForm name="actionForm" action="auditStandard.do" entity="auditStandard"/>
	<script>
		var bar = new ToolBar("bar", "计划审核标准", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.add"/>", "add()");
		bar.addItem("<@msg.message key="action.edit"/>", "edit()");
		bar.addItem("<@msg.message key="action.delete"/>", "remove()");
	</script>
</body>
<#include "/templates/foot.ftl"/>