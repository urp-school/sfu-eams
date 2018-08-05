<#include "/templates/head.ftl"/>
<body onload="parent.clearBarInfo()">
    <table id="bar" width="100%"></table>
    <center>
    <div style="width:100%">
        <#assign departmentMap = {}/>
        <#assign evaluateResultMap = {}/>
        <#list evaluateDepartments as evaluateDepartment>
            <#assign departmentMap = departmentMap + {evaluateDepartment.department.id?string:evaluateDepartment}/>
        </#list>
        <table class="listTable" width="100%" style="text-align:justify;text-justify:inter-ideograph;line-height:5mm">
            <tr style="font-weight:bold">
                <#assign tdWidth = 40.0 / options?size?float/>
                <td width="40%">学院（平均分）</td>
                <td width="20%">人数</td>
                <#list options?sort_by("name") as option>
                <td width="${tdWidth}%">${option.name}</td>
                </#list>
            </tr>
        <#list departmentResults as departmentResult>
            <#assign evaluateResultMap = evaluateResultMap + {departmentResult[0]?string + "_" + departmentResult[1]?string + "_stdCount":departmentResult[2], departmentResult[0]?string + "_" + departmentResult[1]?string + "_stdTicket":departmentResult[3]}/>
        </#list>
            <#assign teacherSum = 0/>
            <#list evaluateDepartments?if_exists as evaluateDepartment>
            <tr>
                <td rowspan="2"><@i18nName evaluateDepartment.department/>（${evaluateDepartment.sumScore?string("0.00")}）</td>
                <#assign stdCount = 0/>
                <#list options?sort_by("name") as option>
                    <#assign optionCount = evaluateResultMap[evaluateDepartment.department.id?string + "_" + option.id?string + "_stdCount"]?default(0)/>
                    <#assign stdCount = stdCount + optionCount/>
                </#list>
            </tr>
            <tr>
                <td>${stdCount}</td>
                <#assign validTickets = 0/>
                <#list options?sort_by("name") as option>
                    <#assign optionCount = evaluateResultMap[evaluateDepartment.department.id?string + "_" + option.id?string + "_stdTicket"]?default(0)/>
                    <#assign validTickets = validTickets + optionCount/>
                </#list>
                <#list options?sort_by("name") as option>
                    <#assign optionCount = evaluateResultMap[evaluateDepartment.department.id?string + "_" + option.id?string + "_stdTicket"]?default(0)/>
                <td>${(optionCount / validTickets * 100)?string("0.00")}%</td>
                </#list>
            </tr>
            </#list>
        </table>
    </div>
    </center>
    <script>
        var bar = new ToolBar("bar", "院系课程评教详情", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addPrint("<@msg.message key="action.print"/>");
        bar.addBack("<@msg.message key="action.back"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>