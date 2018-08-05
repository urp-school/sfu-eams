<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#if RequestParameters['type']?exists&&RequestParameters['type']=="std">
        <#include "stdPlanList.ftl"/>
    <#else>
        <#include "specialityPlanList.ftl"/>
    </#if>
    <@htm.actionForm name="actionForm" action="teachPlanActivityStat.do" entity="teachPlan" onsumbit="return false;"></@>
    <script>
        var bar = new ToolBar("bar", "统计结果", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("查看", "infoData()", "detail.gif");
        
        function infoData(id) {
            var planId = id;
            if (null == planId) {
                planId = getSelectId("teachPlanId");
            }
            if (isEmpty(planId) || isMultiId(planId)) {
                alert("请选择一个要查看的记录。");
                return;
            }
            form.action = "teachPlanActivityStat.do?method=info";
            addInput(form, "teachPlanId", planId, "hidden");
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>