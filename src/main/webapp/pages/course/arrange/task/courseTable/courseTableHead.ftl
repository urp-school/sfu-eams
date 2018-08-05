
<#if table.kind=="std">
    <h1 align="center"><@i18nName systemConfig.school/> <@msg.message key="course.personalCurriculums"/></h4>
    <h3 align="center"><#if calendar.term='1'><@bean.message key="course.yearTerm1" arg0=calendar.year/><#elseif calendar.term='2'><@bean.message key="course.yearTerm2" arg0=calendar.year/><#else><@bean.message key="course.yearTermOther" arg0=calendar.year arg1=calendar.term/></#if></h4>
   <@msg.message key="attr.stdNo"/>:${table.resource.code}&nbsp;&nbsp;&nbsp; <@msg.message key="attr.studentName"/>:<@i18nName table.resource/>&nbsp;&nbsp;&nbsp;<@msg.message key="std.withAdminClass"/>: <@i18nName (table.resource.firstMajorClass)?if_exists/>&nbsp;&nbsp;&nbsp;<@msg.message key="std.totalCredit"/>:${table.credits}
<#elseif table.kind=="class">
    <h2 align="center"><@i18nName systemConfig.school/> <@msg.message key="course.proposedCurriculumsOfAdminClass"/></h4>
    <h3 align="center"><#if calendar.term='1'><@bean.message key="course.yearTerm1" arg0=calendar.year/><#elseif calendar.term='2'><@bean.message key="course.yearTerm2" arg0=calendar.year/><#else><@bean.message key="course.yearTermOther" arg0=calendar.year arg1=calendar.term/></#if></h4>
    &nbsp;<@msg.message key="info.studentClassManager.className"/>:${table.resource.name}&nbsp;&nbsp;&nbsp; <@msg.message key="attr.cultivateScheme.resideSpecility"/>:<@i18nName table.resource.speciality?if_exists/>&nbsp;&nbsp;&nbsp; <@msg.message key="course.creditsShouldBeElected"/>:${table.credits}
<#elseif table.kind=="room">
<h5 align="center"><#if calendar.term='1'><@bean.message key="course.curriculum.yearTerm1" arg0=labInfo arg1=calendar.year/><#elseif calendar.term='2'><@bean.message key="course.curriculum.yearTerm2" arg0=labInfo arg1=calendar.year/><#else><@bean.message key="course.curriculum.yearTermOther" arg0=labInfo arg1=calendar.year arg2=calendar.term/></#if></h4>
  <@msg.message key="entity.schoolDistrict"/>:<@i18nName table.resource.schoolDistrict?if_exists/>&nbsp;&nbsp;&nbsp;&nbsp;<@msg.message key="entity.building"/>:<@i18nName (table.resource.building)?if_exists/>&nbsp;&nbsp;&nbsp;&nbsp;
  <@msg.message key="attr.equipmentConfiguration"/>:<@i18nName table.resource.configType?if_exists/>&nbsp;&nbsp;&nbsp;&nbsp;<@msg.message key="attr.capacityOfCourse"/>:${table.resource.capacityOfCourse}
<#else>
<h5 align="center"><#if calendar.term='1'><@bean.message key="course.curriculum.yearTerm1" arg0=labInfo arg1=calendar.year/><#elseif calendar.term='2'><@bean.message key="course.curriculum.yearTerm2" arg0=labInfo arg1=calendar.year/><#else><@bean.message key="course.curriculum.yearTermOther" arg0=labInfo arg1=calendar.year arg2=calendar.term/></#if></h4>
</#if>
