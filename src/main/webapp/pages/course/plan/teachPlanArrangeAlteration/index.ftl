<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="frameTable">
        <form method="post"action="" name="actionForm" onsubmit="return false;">
        <tr valign="top">
            <td class="frameTable_view" width="18%"><#include "searchForm.ftl"/></td>
            <input type="hidden" name="orderBy" value="alteration.alterationAt desc"/>
        </form>
            <td><iframe name="pageIframe" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "培养计划日志", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBlankItem();
        
        var form = document.actionForm;
        
        function search() {
            form.action = "teachPlanArrangeAlteration.do?method=search";
            form.target = "pageIframe";
            form.submit();
        }
        
        search();
        
        function formReset() {
            form.reset();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>