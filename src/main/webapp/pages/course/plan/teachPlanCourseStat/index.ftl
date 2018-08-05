<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="frameTable" width="100%">
        <tr valign="top">
            <form method="post" action="" name="actionForm" onsubmit="return false;">
            <td class="frameTable_view" width="20%"><#include "searchForm.ftl"/></td>
            </form>
            <td><iframe name="pageIframe" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "培养计划课程统计结果", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBlankItem();
        
        var form = document.actionForm;
        
        function search() {
            form.action = "teachPlanCourseStat.do?method=search";
            form.target = "pageIframe";
            form.submit();
        }
        
        search();
    </script>
</body>
<#include "/templates/foot.ftl"/>