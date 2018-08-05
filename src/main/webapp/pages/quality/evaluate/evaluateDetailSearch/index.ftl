<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="frameTable" width="100%">
        <tr valign="top">
            <form method="post" action="" name="actionForm">
            <td width="20%" class="frameTable_view"><#include "searchForm.ftl"/></td>
            </form>
            <td><iframe name="iframePage" src="#" width="100%" height="100%" marginwidth="0px" marginheight="0px" frameborder="0" scrolling="no"></iframe></td>
        </tr>
    </table>
    <script>
        var form = document.actionForm;
        
        var bar = new ToolBar("bar", "教师个人评教明细", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBlankItem();
        
        function search() {
            form.action = "evaluateDetailSearch.do?method=search";
            form.target = "iframePage";
            form.submit();
        }
        search();
    </script>
</body>
<#include "/templates/foot.ftl"/>