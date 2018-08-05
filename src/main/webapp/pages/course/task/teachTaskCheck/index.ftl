<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="frameTable_title">
          <form name="actionForm" method="post" action="" onsubmit="return false;">
        <tr>
            <td></td>
          <#--
            <td style="width:50px"><font color="blue"><@bean.message key="action.advancedQuery"/></font></td>
             <td class="infoTitle"><@msg.message key="course.arrangeState"/>:</td>
             <td>
                <select name="task.arrangeInfo.isArrangeComplete" style="width:100px" onchange="search()">
                   <option value=""><@bean.message key="common.all"/></option>
                   <option value="1"><@msg.message key="course.beenArranged"/></option>
                   <option value="0"><@msg.message key="course.noArrange"/></option>
                </select>
             </td>
            <td>|</td>
          -->
            <input type="hidden" name="task.calendar.id" value="${calendar.id}"/>
            <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    <table class="frameTable">
        <tr>
            <td valign="top" width="18%" class="frameTable_view">
                <#include "searchForm.ftl"/>
            </td>
          </form>
            <td valign="top" bgcolor="white">
                <iframe  src="#" name="pageIframe" scrolling="no" marginwidth="0" marginheight="0" frameborder="0"  height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "教学任务核对", null, true, false);
        bar.setMessage('<@getMessage/>');
        bar.addBlankItem();
        
        var form = document.actionForm;
        
        function search() {
            form.action = "teachTaskCheck.do?method=search";
            form.target = "pageIframe";
            form.submit();
        }
        
        search();
    </script>
</body>
<#include "/templates/foot.ftl"/>