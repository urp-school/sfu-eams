<#include "/templates/head.ftl"/>
<body>
    <table id="taskBar" ></table>
    <script>
         var bar = new ToolBar('taskBar', '学生考勤报表', null, true, false);
         bar.addBlankItem();
    </script>
    <#list statMap?keys as studentCode>
    <table>
        <tr>
            <td>学生学号：${statMap[studentCode].student.code}</td>
            <td width="10px"></td>
            <td>学生姓名：${statMap[studentCode].student.name}</td>
        </tr>
    </table>
    <#assign monthStart = calendar.start?string("yyyyMM")?number/>
    <#assign monthFinish = calendar.finish?string("yyyyMM")?number/>
    <table class="listTable" width="100%" style="text-align:center">
        <tr>
            <td class="darkColumn" style="width:10%">月份</td>
            <#list monthStart..monthFinish as yearMonth>
                <#assign month = yearMonth?string?substring(4)?number/>
                <#if month gt 0 && month lte 12>
            <td class="darkColumn" colspan="2">${yearMonth?string?substring(0, 4)}-${month}</td>
                </#if>
            </#list>
            <td class="darkColumn" colspan="3">总计</td>
        </tr>
        <tr>
            <td class="grayStyle">课程</td>
            <#list monthStart..monthFinish as yearMonth>
                <#assign month = yearMonth?string?substring(4)?number/>
                <#if month gt 0 && month lte 12>
            <td class="grayStyle">缺勤</td>
            <td class="grayStyle">迟到</td>
                </#if>
            </#list>
            <td class="grayStyle" style="width:7%">缺勤率（%）</td>
            <td class="grayStyle" style="width:7%">迟到率（%）</td>
            <td class="grayStyle" style="width:7%">出勤率（%）</td>
        </tr>
        <#assign coursesMap = statMap[studentCode].coursesMap/>
        <#list coursesMap?keys as courseCode>
        <tr>
            <td>${coursesMap[courseCode].course.name}</td>
            <#assign attends = coursesMap[courseCode].attends?sort_by("attenddate")/>
            
            <#assign attendances = []/>
            <#assign attendanceCount = 0/>
            <#assign absences = []/>
            <#assign absenceCount = 0/>
            <#assign lates = []/>
            <#assign lateCount = 0/>
            
            <#assign attendIndex = 0/>
            <#list monthStart..monthFinish as yearMonth>
                <#assign month = yearMonth?string?substring(4)?number/>
                <#if month gt 0 && month lte 12>
                    <#if attendIndex lt attends?size>
                        <#list attendIndex..attends?size - 1 as attend_index>
                            <#if attends[attend_index].attenddate?string("yyyyMM")?number == yearMonth>
                                <#if attends[attend_index].attendtype == "2">
                                    <#assign absenceCount = absenceCount + 1/>
                                <#elseif attends[attend_index].attendtype == "3">
                                    <#assign lateCount = lateCount + 1/>
                                <#elseif attends[attend_index].attendtype == "1">
                                    <#assign attendanceCount = attendanceCount + 1/>
                                </#if>
                                <#assign attendIndex = attend_index + 1/>
                            <#else>
            <td>${absenceCount}</td>
            <td>${lateCount}</td>
                                <#assign attendances = attendances + [attendanceCount]/>
                                <#assign attendanceCount = 0/>
                                <#assign absences = absences + [absenceCount]/>
                                <#assign absenceCount = 0/>
                                <#assign lates = lates + [lateCount]/>
                                <#assign lateCount = 0/>
                                <#break/>
                            </#if>
                            <#if (!attends[attend_index + 1]?exists || attends[attend_index + 1].attenddate?string("yyyyMM")?number != yearMonth)>
            <td>${absenceCount}</td>
            <td>${lateCount}</td>
                                <#assign attendances = attendances + [attendanceCount]/>
                                <#assign attendanceCount = 0/>
                                <#assign absences = absences + [absenceCount]/>
                                <#assign absenceCount = 0/>
                                <#assign lates = lates + [lateCount]/>
                                <#assign lateCount = 0/>
                                <#break/>
                            </#if>
                        </#list>
                    <#else>
            <td>${absenceCount}</td>
            <td>${lateCount}</td>
                    </#if>
                </#if>
            </#list>
            <#assign attendanceSum = 0/>
            <#list attendances as attendanceNum>
                <#assign attendanceSum = attendanceSum + attendanceNum/>
            </#list>
            <#assign absenceSum = 0/>
            <#list absences as absenceNum>
                <#assign absenceSum = absenceSum + absenceNum/>
            </#list>
            <#assign lateSum = 0/>
            <#list lates as lateNum>
                <#assign lateSum = lateSum + lateNum/>
            </#list>
            <td>${(absenceSum / attends?size * 100)?string("0.##")}</td>
            <td>${(lateSum / attends?size * 100)?string("0.##")}</td>
            <td>${(attendanceSum / attends?size * 100)?string("0.##")}</td>
        </tr>
        </#list>
    </table>
    </#list>
</body>
<#include "/templates/foot.ftl"/> 
 