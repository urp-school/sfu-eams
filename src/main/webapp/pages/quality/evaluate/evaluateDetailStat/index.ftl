<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="frameTable_title">
        <tr>
            <td></td>
        <form method="post" action="" name="actionForm">
            <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    <#if !searchFormFlag?exists || searchFormFlag == "" || searchFormFlag == "beenStat">
    <input type="hidden" name="forwardPage" value=""/>
    <table class="frameTable" width="100%" cellpadding="0" cellspacing="0">
        <tr valign="top">
            <td width="22%" class="frameTable_view"><#include "searchForm.ftl"/></td>
        </form>
            <td><iframe name="iframePage" src="#" width="100%" height="100%" marginwidth="0px" marginheight="0px" frameborder="0" scrolling="no"></iframe></td>
        </tr>
    </table>
    <#elseif searchFormFlag == "noStat">
    <#include "initStat.ftl"/>
    </#if>
    <script>
        var form = document.actionForm;
        
        var bar = new ToolBar("bar", "评教统计管理", null, true, true);
        bar.setMessage('<@getMessage/>');
        <#if !searchFormFlag?exists || searchFormFlag == "beenStat">
        bar.addItem("院系评教详情（全校）", "departmentChoiceConfig()", "detail.gif");
        bar.addItem("历史课程评教汇总", "historyCollegeStat()", "detail.gif");
        bar.addItem("学校分项评教汇总", "collegeGroupItemInfo()", "detail.gif");
        <#--
        bar.addItem("初始化/重新统计...", "initStat()");
        -->
        
        function search(searchFormFlag) {
            form.action = "evaluateDetailStat.do?method=search";
            form.target = "iframePage";
            form["searchFormFlag"].value = searchFormFlag;
            form.submit();
        }
        search("${RequestParameters["searchFormFlag"]?default("beenStat")}");
        
        // 初始化统计
        function initStat() {
            clearBarInfo();
            form.action = "evaluateDetailStat.do?method=index";
            form.target = "_self";
            form["searchFormFlag"].value = "noStat";
            form.submit();
        }
        
        function departmentChoiceConfig() {
            clearBarInfo();
            form.action = "evaluateDetailStat.do?method=departmentChoiceConfig";
            form.target = "iframePage";
            form["forwardPage"].value = "";
            form["searchFormFlag"].value = "beenStat";
            form.submit();
        }
        
        function historyCollegeStat() {
            clearBarInfo();
            form.action = "evaluateDetailStat.do?method=departmentChoiceConfig";
            form.target = "iframePage";
            form["forwardPage"].value = "historyCollegeStat";
            form["searchFormFlag"].value = "beenStat";
            form.submit();
        }
        <#else>
        bar.addItem("<@msg.message key="action.back"/>", "backPage()", "backward.gif");
        
        var stdTypeBar = new ToolBar("stdTypeBar", "<@msg.message key="entity.studentType"/>", null, true, true);
        stdTypeBar.addBlankItem();
        var departmentBar = new ToolBar("departmentBar", "<@msg.message key="entity.department"/>", null, true, true);
        departmentBar.addBlankItem();
        var initStatBar = new ToolBar("initStatBar", "初始化统计评教记录", null, true, true);
        initStatBar.addItem("统计", "stat()", null, "统计所选择学生类别和开课院系的学生评教记录。");
        
        function backPage() {
            form.action = "evaluateDetailStat.do?method=index";
            form.target = "_self";
            form["searchFormFlag"].value = "beenStat";
            form.submit();
        }
        
        function stat() {
            clearBarInfo();
            var stdTypeIds = getSelectIds("stdTypeId");
            var departmentIds = getSelectIds("departmentId");
            if (null == stdTypeIds || "" == stdTypeIds) {
                alert("请选择要统计的学生类别。");
                return;
            }
            addInput(form, "stdTypeIds", stdTypeIds, "hidden");
            if (null == departmentIds || "" == departmentIds) {
                alert("请选择要统计的开课院系。");
                return;
            }
            addInput(form, "departmentIds", departmentIds, "hidden");
            if (confirm("可能会覆盖原来的统计结果，是否要继续？")) {
                $("message").innerHTML = "正在统计中，请稍候……";
                form.action = "evaluateDetailStat.do?method=stat";
                form.target = "_self";
                form["searchFormFlag"].value = "beenStat";
                form.submit();
            }
        }
        </#if>
        
        function clearBarInfo() {
            if ("" != $("message").innerHTML) {
                $("message").innerHTML = null;
                $("message").style.display = "none";
            }
            if ("" != $("error").innerHTML) {
                $("error").innerHTML = null;
                $("error").style.display = "none";
            }
        }
        
        function collegeGroupItemInfo() {
            form.action = "evaluateDetailStat.do?method=collegeGroupItemInfo";
            form.target = "iframePage";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>