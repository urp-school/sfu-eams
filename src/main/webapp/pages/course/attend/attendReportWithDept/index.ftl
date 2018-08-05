<#include "/templates/head.ftl"/>
    <table id="bar"></table>
    <form method="post" action="" target="contentFrame" name="actionForm" onsubmit="return false;">
    <table class="frameTable_title">
	</table>
    <table class="frameTable" >
      <tr valign="top">
            <td>
                <iframe name="contentFrame" id="contentFrame" src="#" width="100%" ></iframe>
            </td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "院系纬度考勤报表", null, true, true);
        bar.setMessage('<@getMessage/>');
        function search() {
           var form = document.actionForm;
           form.action="attendReportWithDept.do?method=search";
           form.target="contentFrame";
           form.submit();
        }
        search();
        
    </script>
<#include "/templates/foot.ftl"/>