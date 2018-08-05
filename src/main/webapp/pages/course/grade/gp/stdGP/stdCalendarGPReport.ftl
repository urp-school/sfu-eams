<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar" width="100%"></table>
 <#list stdGPs as stdGP>
<#if majorType.id == 1>
    <div align="center"><@msg.message key="attr.stdNo"/>:${stdGP.std.code} <@msg.message key="attr.personName"/>:<@i18nName stdGP.std/> <@msg.message key="entity.department"/>:<@i18nName stdGP.std.department?if_exists/> <@msg.message key="entity.speciality"/>:<@i18nName stdGP.std.firstMajor?if_exists/> <@msg.message key="entity.specialityAspect"/>:<@i18nName stdGP.std.firstAspect?if_exists/></div>
<#else>
    <div align="center"><@msg.message key="attr.stdNo"/>:${stdGP.std.code} <@msg.message key="attr.personName"/>:<@i18nName stdGP.std/> <@msg.message key="entity.department"/>:<@i18nName (stdGP.std.secondMajor.department)?if_exists/> <@msg.message key="entity.speciality"/>:<@i18nName (stdGP.std.secondMajor)?if_exists/> <@msg.message key="entity.specialityAspect"/>:<@i18nName (stdGP.std.secondAspect)?if_exists/></div>
</#if>
<#include "/pages/course/grade/gradeMacros.ftl"/>
<@table.table width="90%" align="center">
    <@table.thead>
        <@table.td name="attr.year2year"/>
        <@table.td name="std.grade.courseNumber"/>
        <@table.td name="std.totalCredit"/>
        <@table.td name="grade.avgPoints"/>
    </@>
    <#assign k = 0/>
    <#assign calendarYear = ""/>
    <#assign countSum = 0/>
    <#assign creditSum = 0/>
    <#assign GPASum = 0/>
    <#list stdGP.GPList as stdGPTerm>
        <#if !calendarYear?exists || calendarYear == "">
            <#assign calendarYear = stdGPTerm.calendar.year/>
        </#if>
        <#if calendarYear != stdGPTerm.calendar.year>
            <#assign k = k + 1/>
    <@table.tr (k % 2 == 1)?string("brightStyle", "grayStyle")>
        <td>${calendarYear}</td>
        <td>${countSum}</td>
        <td>${creditSum}</td>
        <td>${GPASum?string('#0.00')}</td>
    </@>
            <#assign calendarYear = stdGPTerm.calendar.year/>
            <#assign countSum = stdGPTerm.count/>
            <#assign creditSum = stdGPTerm.credits/>
            <#assign GPASum = ((stdGPTerm.GPA * 100)?int / 100)/>
        <#else>
            <#assign countSum = countSum + stdGPTerm.count/>
            <#assign creditSum = creditSum + stdGPTerm.credits/>
            <#assign GPASum = ((stdGPTerm.GPA * 100)?int / 100)/>
        </#if>
    </#list>
    <#assign k = k + 1/>
    <@table.tr (k % 2 == 1)?string("brightStyle", "grayStyle")>
        <td>${calendarYear}</td>
        <td>${countSum}</td>
        <td>${creditSum}</td>
        <td>${GPASum?string('#0.00')}</td>
    </@>
    <tr align="center">
        <td><@msg.message key="grade.schoolSummary"/></td>
        <td>${stdGP.count}</td>
        <td>${stdGP.credits}</td>
        <td><@reserve2 stdGP.GPA/></td>
    </tr>
</@>
 </#list>
  <script>
    var bar = new ToolBar("myBar","学生在校成绩统计",null,true,true);
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
  </script>
</body>
<#include "/templates/foot.ftl"/>
