<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#assign pageCaption = "本次课程质量评价分类汇总表"/>
    <#assign tdCount = questionnaire.questions?size/>
    <#assign tdTitleWidth = 50 + 100 + 150 + 70 +180/>
    <#assign tdWidth = 70/>
    <#assign tableWidth = tdTitleWidth + (tdCount + 1) * tdWidth + tdCount + 1/>
    <table width="${tableWidth}px" align="center">
        <tr>
            <td style="font-size:13.5pt;font-weight:bold;text-align:center">${pageCaption}<br>（${questionnaire.description}）</td>
        </tr>
    </table>
    <table class="listTable" width="${tableWidth}px" align="center" style="text-align:center">
        <tr>
            <td width="50px" rowspan="2">排名</td>
            <td width="100px" rowspan="2">教师姓名</td>
            <td width="150px" rowspan="2">课程名称</td>
            <td width="70px" style="color:red" rowspan="2">评教人数</td>
            <td width="180px" rowspan="2">所在学院</td>
            <td colspan="${tdCount}">各项得分</td>
            <td width="${tdWidth}px" rowspan="2">总分</td>
        </tr>
        <tr>
            <#list 1..tdCount as i>
            <td width="${tdWidth}px">${i}</td>
            </#list>
        </tr>
        <#list teacherResults?sort_by(["rank"]) as result>
        <tr>
            <td>${result.rank}</td>
            <td>${result.teacher.name}</td>
            <td>${result.course.name}</td>
            <td>${result.validTickets?default(0)}</td>
            <td style="text-align:left">${result.teacher.department.name}</td>
            <#list 1..tdCount as i>
            <td>${(result.questionsStat[i - 1].evgPoints?default(0))?string("0.00")}</td>
            </#list>
            <td>${result.sumScore?string("0.00")}</td>
        </tr>
        </#list>
        <tr style="color:#339966">
            <td>平均分</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <#list 1..tdCount as i>
            <td>${(collegeEvaluate.questionsStat[i - 1].evgPoints?default(0))?string("0.00")}</td>
            </#list>
            <td>${collegeEvaluate.sumScore?string("0.00")}</td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "${pageCaption}（${calendar.year} ${calendar.term}）", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addPrint("<@msg.message key="action.print"/>");
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>