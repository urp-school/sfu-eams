<#include "/templates/head.ftl"/>
<body>
    <table id="majorSubstitutionCourseBar"></table>
    <table class="frameTable" width="100%">
        <tr>
            <td width="18%" class="frameTable_view"><#include "searchForm.ftl"/></td>
            <td valign="top">
                <iframe src="#" id="planListFrame" name="planListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
    </form>
    <script>
        var bar=new ToolBar("majorSubstitutionCourseBar","专业替代课程管理",null,true,true);
        bar.setMessage('<@getMessage/>');
        bar.addBlankItem();
        searchTeachPlan();
    </script>  
</body>
<#include "/templates/foot.ftl"/>