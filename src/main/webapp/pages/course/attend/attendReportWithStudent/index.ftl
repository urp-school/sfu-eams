<#include "/templates/head.ftl"/>
    <table id="bar"></table>
    <table class="frameTable_title">
        <tr>
            <form method="post" action="" target="contentFrame" name="actionForm" onsubmit="return false;">
            <td style="width:35%;"></td>
                <input type="hidden" name="calendarId" value="${calendar.id!}" />
                <input type="hidden" name="student.type.id" value="${studentType.id}"/>
                <input type="hidden" name="calendar.id" value="${calendar.id!}" />
            <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    <table class="frameTable">
        <tr valign="top">
            <td class="frameTable_view" width="20%" align="center">
                    <#include "searchForm.ftl"/>
            </form>
            </td>
            <td>
                <iframe name="contentFrame" id="contentFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
            </td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "学生纬度考勤报表", null, true, true);
        bar.addBlankItem();
        function search() {
           var form = document.actionForm;
           form.action="attendReportWithStudent.do?method=search";
           form.target="contentFrame";
           form.submit();
        }
        search();
        
    </script>
<#include "/templates/foot.ftl"/>