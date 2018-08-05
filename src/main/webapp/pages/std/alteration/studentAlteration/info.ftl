<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="infoTable">
		<tr>
			<th colspan="4">学籍变动详细信息</th>
		</tr>
		<tr>
			<td class="title"><@msg.message key="std.stdNo"/>：</td>
			<td>${alteration.std.code}</td>
			<td class="title" width="10%"><@msg.message key="attr.personName"/>：</td>
			<td><@i18nName alteration.std/></td>
		</tr>
		<tr>
			<td class="title">学籍变动日期：</td>
			<td>${alteration.alterBeginOn?string("yyyy-MM-dd")}</td>
			<td class="title">变动截止日期：</td>
			<td>${(alteration.alterEndOn?string("yyyy-MM-dd"))?if_exists}</td>
		</tr>
		<tr>
			<td class="title">学籍变动种类：</td>
			<td><@i18nName alteration.mode/></td>
			<td class="title">变更原因：</td>
			<td><@i18nName (alteration.reason)?if_exists/></td>
		</tr>
		<tr>
			<td class="title"><@msg.message key="entity.adminClass"/>：</td>
			<td colspan="3"><@getBeanListNames (alteration.std.adminClasses)?if_exists/></td>
		</tr>
		<tr>
			<td class="title" style="vertical-align:top"><@msg.message key="attr.remark"/>：</td>
			<td colspan="3">${(alteration.remark)?if_exists + "<br><br>"}</td>
		</tr>
	</table>
	<table class="infoTable">
		<tr>
			<th colspan="4">学生信息变更情况</th>
		</tr>
		<tr>
			<td class="title" style="font-weight: bold">变更项</td>
			<td class="title" style="text-align:left">变更前</td>
			<td class="title" style="text-align:left">变更后</td>
			<td class="title" style="text-align:left">是否变更</td>
		</tr>
		<#if alteration.beforeStatus?exists && alteration.afterStatus?exists>
		<tr>
			<td class="title"><@msg.message key="entity.studentType"/>：</td>
			<td><@i18nName (alteration.beforeStatus.stdType)?if_exists/></td>
			<td><@i18nName (alteration.afterStatus.stdType)?if_exists/></td>
			<td><#if (alteration.beforeStatus.stdType.id)?default("")?string!=(alteration.afterStatus.stdType.id)?default("")?string>变更</#if></td>
		</tr>
        <tr>
        	<td class="title">院系：</td>
			<td><@i18nName (alteration.beforeStatus.department)?if_exists/></td>
			<td><@i18nName (alteration.afterStatus.department)?if_exists/></td>
			<td><#if (alteration.beforeStatus.department.id)?default("")?string!=(alteration.afterStatus.department.id)?default("")?string>变更</#if></td>
        </tr>
		<tr>
			<td class="title"><@msg.message key="entity.speciality"/>：</td>
			<td><@i18nName (alteration.beforeStatus.speciality)?if_exists/></td>
			<td><@i18nName (alteration.afterStatus.speciality)?if_exists/></td>
			<td><#if (alteration.beforeStatus.speciality.id)?default("")?string!=(alteration.afterStatus.speciality.id)?default("")?string>变更</#if></td>
		</tr>
        <tr>
			<td class="title"><@msg.message key="entity.specialityAspect"/>：</td>
			<td><@i18nName (alteration.beforeStatus.aspect)?if_exists/></td>
			<td><@i18nName (alteration.afterStatus.aspect)?if_exists/></td>
			<td><#if (alteration.beforeStatus.aspect.id)?default("")?string!=(alteration.afterStatus.aspect.id)?default("")?string>变更</#if></td>
        </tr>
		<tr>
			<td class="title"><@msg.message key="attr.enrollTurn"/>：</td>
			<td>${(alteration.beforeStatus.enrollYear)?if_exists}</td>
			<td>${(alteration.afterStatus.enrollYear)?if_exists}</td>
			<td><#if (alteration.beforeStatus.enrollYear)?default("")?string!=(alteration.afterStatus.enrollYear)?default("")?string>变更</#if></td>
		</tr>
		<tr>
			<td class="title">毕业时间：</td>
			<td>${(alteration.beforeStatus.graduateOn?string("yyyy-MM-dd"))?if_exists}</td>
			<td>${(alteration.afterStatus.graduateOn?string("yyyy-MM-dd"))?if_exists}</td>
			<td><#if (alteration.beforeStatus.graduateOn?string("yyyy-MM-dd"))?default("")!=(alteration.afterStatus.graduateOn?string("yyyy-MM-dd"))?default("")>变更</#if></td>
		</tr>
		<tr>
			<td class="title"><@msg.message key="entity.studentState"/>：</td>
			<td><@i18nName (alteration.beforeStatus.status)?if_exists/></td>
			<td><@i18nName (alteration.afterStatus.status)?if_exists/></td>
			<td><#if (alteration.beforeStatus.status?string)?default("")!=(alteration.afterStatus.status?string)?default("")>变更</#if></td>
		</tr>
		<tr>
			<td class="title">学籍是否有效：</td>
			<td><#if (alteration.beforeStatus.isActive)?exists><#if alteration.beforeStatus.isActive><@msg.message key="common.yes"/><#else><@msg.message key="common.no"/></#if></#if></td>
			<td><#if (alteration.afterStatus.isActive)?exists><#if alteration.afterStatus.isActive><@msg.message key="common.yes"/><#else><@msg.message key="common.no"/></#if></#if></td>
			<td><#if (alteration.beforeStatus.isActive)?exists && (alteration.beforeStatus.isActive)?exists><#if (alteration.beforeStatus.isActive!=alteration.afterStatus.isActive)>变更</#if></#if></td>
		</tr>
		<tr>
			<td class="title">是否在校：</td>
			<td><#if (alteration.beforeStatus.isInSchool)?exists><#if alteration.beforeStatus.isInSchool><@msg.message key="common.yes"/><#else><@msg.message key="common.no"/></#if></#if></td>
			<td><#if (alteration.afterStatus.isInSchool)?exists><#if alteration.afterStatus.isInSchool><@msg.message key="common.yes"/><#else><@msg.message key="common.no"/></#if></#if></td>
			<td><#if (alteration.beforeStatus.isInSchool)?exists && (alteration.beforeStatus.isInSchool)?exists><#if (alteration.beforeStatus.isInSchool!=alteration.afterStatus.isInSchool)>变更</#if></#if></td>
		</tr>
		<tr>
			<td class="title">班级：</td>
			<td><#assign adminClassBefore = (alteration.beforeStatus.adminClass)?if_exists/><#if adminClassBefore?exists><@i18nName adminClassBefore/><#else><@getBeanListNames (alteration.std.adminClasses)?if_exists/></#if></td>
			<td><#assign adminClassAfter = (alteration.afterStatus.adminClass)?if_exists/><#if adminClassAfter?exists><@i18nName adminClassAfter/><#else><@getBeanListNames (alteration.std.adminClasses)?if_exists/></#if></td>
			<#if (adminClassBefore.id)?exists>
			<td><#if !(adminClassBefore.id)?exists && (adminClassAfter.id)?exists || (adminClassBefore.id)?exists && !(adminClassAfter.id)?exists || (adminClassBefore.id != adminClassAfter.id)>变更</#if></td>
		    </#if>
		</tr>
		</#if>
	</table>
	<#list 1..5 as i><br></#list>
	<script>
		var bar = new ToolBar("bar", "查看异动明细", null, true, true);
		bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>
