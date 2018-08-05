<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#assign pageCaption = "本次课程质量评价学院分项统计汇总表"/>
    <#assign tdCount = 5/>
    <#assign tdTitleWidth = 150 + 100/>
    <#assign tdWidth = 70/>
    <#assign tableWidth = tdTitleWidth + tdCount * tdWidth + tdCount/>
    <table width="${tableWidth}px" align="center">
        <tr>
            <td style="font-size:13.5pt;font-weight:bold;text-align:center">${pageCaption}<br>（${department.name}）</td>
        </tr>
    </table>
    <table class="listTable" width="${tableWidth}px" align="center">
        <tr style="font-size:14pt;">
            <td width="150px">项目</td>
            <td width="100px"></td>
            <td width="${tdWidth}px" style="color:red">好</td>
            <td width="${tdWidth}px" style="color:red">较好</td>
            <td width="${tdWidth}px" style="color:red">中</td>
            <td width="${tdWidth}px" style="color:red">较差</td>
            <td width="${tdWidth}px" style="color:red">差</td>
        </tr>
        <#assign typeTotal = 0/>
        <#list groupItemResults?keys as dKey>
            <#if typeTotal != 0 && typeTotal != groupItemResults[dKey][5]>
        <tr style="color:#FF6600;font-weight:bold">
            <td colspan="7">好：${(typeTotal * 0.9)?string("0.#")}分以上；较好：${(typeTotal * 0.8)?string("0.#")}--${(typeTotal * 0.9 - 0.1)?string("0.#")}分；中：${(typeTotal * 0.7)?string("0.#")}--${(typeTotal * 0.8 - 0.1)?string("0.#")}分；较差：${(typeTotal * 0.6)?string("0.#")}--${(typeTotal * 0.7 - 0.1)?string("0.#")}分；差：${(typeTotal * 0.6 - 0.1)?string("0.#")}分以下</td>
        </tr>
            </#if>
            <#assign typeTotal = groupItemResults[dKey][5]/>
        <tr>
            <td rowspan="3">${questionTypeMap[dKey].name}（${typeTotal}分）</td>
            <td style="color:red">人次</td>
            <#assign dTotal = 0/>
            <#assign cTotal = 0/>
            <#list 1..tdCount as i>
                <#assign dTotal = dTotal + groupItemResults[dKey][i - 1]/>
                <#assign cTotal = cTotal + collegeResults[dKey][i - 1]/>
            <td>${groupItemResults[dKey][i - 1]}</td>
            </#list>
        </tr>
        <tr>
            <td>学院比例（%）</td>
            <#list 1..tdCount as i>
            <td>${(groupItemResults[dKey][i - 1] / dTotal * 100)?string("0.00")}</td>
            </#list>
        </tr>
        <tr>
            <td>学校比例（%）</td>
            <#list 1..tdCount as i>
            <td>${(collegeResults[dKey][i - 1] / cTotal * 100)?string("0.00")}</td>
            </#list>
        </tr>
        </#list>
        <tr style="color:#FF6600;font-weight:bold">
            <td colspan="7">好：${(typeTotal * 0.9)?string("0.#")}分以上；较好：${(typeTotal * 0.8)?string("0.#")}--${(typeTotal * 0.9 - 0.1)?string("0.#")}分；中：${(typeTotal * 0.7)?string("0.#")}--${(typeTotal * 0.8 - 0.1)?string("0.#")}分；较差：${(typeTotal * 0.6)?string("0.#")}--${(typeTotal * 0.7 - 0.1)?string("0.#")}分；差：${(typeTotal * 0.6 - 0.1)?string("0.#")}分以下</td>
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