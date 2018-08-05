<#if setting.kind="room">
<@msg.message key="entity.classroom"/>:<@i18nName table.resource/>  <@i18nName table.resource.schoolDistrict?if_exists/> <@i18nName table.resource.building?if_exists/>&nbsp;&nbsp;&nbsp;&nbsp;
         <@i18nName table.resource.configType?if_exists/> <@msg.message key="attr.capacityOfCourse"/>:${table.resource.capacityOfCourse}
<#elseif setting.kind="teacher">
<@msg.message key="entity.teacher"/>:<@i18nName table.resource/>
<#elseif setting.kind="std">
<@msg.message key="attr.personName"/>：<@i18nName table.resource/>
<#elseif setting.kind="class">
<@msg.message key="info.studentClassManager.className"/>:${table.resource.name}&nbsp;&nbsp;&nbsp; <@msg.message key="attr.cultivateScheme.resideSpecility"/>:<@i18nName table.resource.speciality?if_exists/>
</#if>