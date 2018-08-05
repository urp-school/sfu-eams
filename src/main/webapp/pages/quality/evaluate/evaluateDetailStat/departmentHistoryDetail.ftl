<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#assign pageCaption = "本次课程质量评价学院汇总表"/>
    <#assign maxTDSize = departmentResult?size/>
    <#assign tdCount = maxTDSize * 4/>
    <#assign tdTitleWidth = 75/>
    <#assign tdWidth = 60/>
    <#assign tableWidth = 5 * tdTitleWidth + (maxTDSize + 1) * tdWidth + tdCount + 110/>
    <#macro questionsScore object isRed>
            <#assign total = 0/>
            <#if object?exists && object.questionsStat?exists && (object.questionsStat?size > 0)>
                <#assign questions = object.questionsStat?sort_by("question", "id")/>
                <#list 1..maxTDSize as i>
                    <#if (questions[i - 1].evgPoints)?exists>
                        <#assign total = total + (questions[i - 1].evgPoints)?default(0)/>
            <td<#if isRed?default(false)> style="color:red"</#if>>${(questions[i - 1].evgPoints)?default(0)?string("0.00")}</td>
                    <#else>
            <td></td>
                    </#if>
                </#list>
            </#if>
            <td<#if isRed?default(false)> style="color:red"</#if>>${(total)?default(0)?string("0.00")}</td>
    </#macro>
    <table width="${tableWidth}px" align="center">
        <tr>
            <td style="font-size:13.5pt;font-weight:bold;text-align:center">${pageCaption}<br>（${department.name}）</td>
        </tr>
    </table>
    <table class="listTable" width="${tableWidth}px" style="text-align:center" align="center">
        <tr>
            <td width="${tdTitleWidth}px" style="color:red"<#if maxTDSize != 0> rowspan="2"</#if>>学院排名</td>
            <td width="${tdTitleWidth}px" style="color:red"<#if maxTDSize != 0> rowspan="2"</#if>>全校排名</td>
            <td width="${tdTitleWidth}px"<#if maxTDSize != 0> rowspan="2"</#if>>教师名称</td>
            <td width="100px"<#if maxTDSize != 0>  rowspan="2"</#if>>课程名称</td>
            <td width="${tdTitleWidth}px" style="color:red"<#if maxTDSize != 0> rowspan="2"</#if>>评教人数</td>
            <#if maxTDSize != 0>
            <td colspan="${maxTDSize}">各项得分</td>
            </#if>
            <td width="${tdWidth}px"<#if maxTDSize != 0> rowspan="2"</#if>>总分</td>
        </tr>
        <#if maxTDSize != 0>
        <tr>
            <#list 1..maxTDSize as i>
            <td width="${tdWidth}px">${i}</td>
            </#list>
        </tr>
        </#if>
        <#list teacherResults?sort_by(["departRank"]) as evaluateTeacher>
        <tr>
            <td style="color:red">${evaluateTeacher.departRank}</td>
            <td style="color:red">${evaluateTeacher.rank}</td>
            <td>${evaluateTeacher.teacher.name}</td>
            <td style="text-align:justify;text-justifyl:inter-ideograph;">${evaluateTeacher.course.name}</td>
            <td style="color:red">${evaluateTeacher.validTickets?default(0)}</td>
            <@questionsScore object=evaluateTeacher?if_exists isRed=false/>
        </tr>
        </#list>
        <tr>
            <td style="color:red">学院平均分</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <#if (maxTDSize > 0)>
                <#assign total = 0/>
                <#list 0..maxTDSize - 1 as i>
                    <#assign total = total + departmentResult[i]?default(0)/>
            <td style="color:red">${departmentResult[i]?default(0)?string("0.00")}</td>
                </#list>
            <td style="color:red">${total?default(0)?string("0.00")}</td>
            <#else>
            <td style="color:red">0.00</td>
            </#if>
        </tr>
        <tr>
            <td style="color:red">学校平均分</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <#if (maxTDSize > 0)>
                <#assign total = 0/>
                <#list 0..maxTDSize - 1 as i>
                    <#assign total = total + collegeResult[i]?default(0)/>
            <td style="color:red">${collegeResult[i]?default(0)?string("0.00")}</td>
                </#list>
            <td style="color:red">${total?default(0)?string("0.00")}</td>
            <#else>
            <td style="color:red">0.00</td>
            </#if>
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