	<table class="infoTable">
	   	<tr>
	 		<td class="title" width="18%" id="f_stdId"><@msg.message key="attr.stdNo"/>：</td>
	 		<td width="27%">${(std.code)?default('&nbsp;')}</td>
	     	<td class="title" width="18%" id="f_name"><@msg.message key="attr.personName"/>：</td>
	     	<td width="20%">${std.name?default('&nbsp;')}</td>
	 		<td width="15%" align="center" rowspan="6" width="10%">
	 			<#assign spurl = "org.beangle.freemarker.StudentPhotoUrlMethod"?new()/>
				<img id="studentPhoto" src="${spurl(std.code)}"
				<#--
				<img id="studentPhoto" src="studentPhoto.do?method=download&student.id=${std.id?default('')}" 
				--> 
					width="130" style="heignt:100%;vertical-align:top;margin-top:2px;border:2px"/>
	 		</td>
	   	</tr>
	     	<td class="title"><@msg.message key="attr.engName"/>：</td>
	     	<td>${std.engName?default('&nbsp;')}</td>
	     	<td class="title"><@msg.message key="filed.enrollYearAndSequence"/>：</td>
	     	<td>${std.enrollYear?default('&nbsp;')}</td>
	   	<tr>
	     	<td class="title">学制：</td>
	     	<td>${std.schoolingLength?default('&nbsp;')}</td>
	     	<td class="title"><@msg.message key="entity.studentType"/>：</td>
	     	<td><#if std.type?exists><@i18nName std.type/><#else>&nbsp;</#if></td>
	   	</tr>
	   	<tr>
	     	<td class="title"><@msg.message key="entity.college"/>：</td>
	     	<td><#if std.department?exists><@i18nName std.department/><#else>&nbsp;</#if></td>
	     	<td class="title"><@msg.message key="entity.Chief.college"/>：</td>
	     	<td><#if std.managementDepart?exists><@i18nName std.managementDepart/><#else>&nbsp;</#if></td>
	   	</tr>
	   	<tr>
	   		<td class="title"><@msg.message key="entity.speciality"/>：</td>
	     	<td><#if std.firstMajor?exists><@i18nName std.firstMajor/><#else>&nbsp;</#if></td>
	     	<td class="title"><@msg.message key="entity.specialityAspect"/>：</td>
	     	<td><#if std.firstAspect?exists><@i18nName std.firstAspect/><#else>&nbsp;</#if></td>
	   	</tr>
	   	<tr>
	   	    <td class="title">导师：</td>
	 		<td><#if std.teacher?exists><@i18nName std.teacher/><#else>&nbsp;</#if></td>
	     	<td class="title"><@msg.message key="common.languageAbility"/>：</td>
	     	<td><#if std.languageAbility?exists><@i18nName std.languageAbility/><#else>&nbsp;</#if></td>
	   	</tr>
	   	<tr>
	   		<td class="title"><@msg.message key="entity.schoolDistrict"/>：</td>
	 		<td><@i18nName std.schoolDistrict?if_exists/></td>
	 		<td class="title"><@msg.message key="entity.adminClass"/>：</td>
	 		<td><#if std.adminClasses?exists && std.adminClasses?size !=0><#list std.adminClasses?sort_by("code") as adminClass><#if adminClass_has_next ><@i18nName adminClass />(${adminClass["code"]})<#if (adminClass_index+1)%2==1>,</#if><#if (adminClass_index+1)%2==0><br></#if><#else><@i18nName adminClass />(${adminClass["code"]})</#if></#list><#else>&nbsp;</#if></td>
	   	</tr>
	   	<tr>
	   		<td class="title">学籍有效性：</td>
	 		<td>${std.active?string("是","否")}</td>
	 		<td class="title">是否在校：</td>
	 		<td>${std.inSchool?string("是","否")}</td>
	   	</tr>
	   	<tr>
	     	<td class="title"><@msg.message key="common.remark"/>：</td>
	     	<td style="word-warp:break-word;word-break:break-all" colspan="6">${std.remark?default('&nbsp;')}</td>
	   	</tr>
	</table>
