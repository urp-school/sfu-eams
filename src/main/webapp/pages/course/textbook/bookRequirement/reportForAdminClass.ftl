<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <#list adminClassMap?keys as adminClassId>
    <#assign adminClass = adminClassMap[adminClassId]>
    <h2 align="center"><@i18nName systemConfig.school/> 教材发放清单</h4>
    <h3 align="center">${calendar.year}学年 <#if calendar.term='1'>第一学期<#elseif calendar.term='2'>第二学期<#else>${calendar.term}</#if></h4>
    <div align="center">班级名称:${adminClass.name}&nbsp;&nbsp;&nbsp; 专业:<@i18nName adminClass.speciality?if_exists/>&nbsp;&nbsp;&nbsp; 人数:${adminClass.actualStdCount}</div>
    <@table.table id="table" width="80%" align="center">
      <@table.thead>
        <@table.td name="attr.courseName"/>
        <@table.td name="entity.textbook"/>
        <@table.td name="entity.press"/>
        <@table.td text="定价"/>
        <@table.td text="需求量"/>
        <@table.td text="订购量"/>
        <@table.td name="attr.remark"/>
      </@>
      <@table.tbody datas=bookRequireMap[adminClassId];require>
        <td><@i18nName require.task.course/></td>
        <td><@i18nName require.textbook/></td>
        <td><@i18nName require.textbook.press/></td>
        <td>${require.textbook.price?default('')}</td>
        <td>${adminClass.stdCount?default("")}</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </@>        
    </@>
    <#if adminClassId_has_next>
      <div style='PAGE-BREAK-AFTER: always'></div>
    </#if>
 </#list>
<#include "/templates/foot.ftl"/>