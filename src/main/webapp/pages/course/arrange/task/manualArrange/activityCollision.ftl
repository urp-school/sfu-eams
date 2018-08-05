<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table>
        <tr>
            <td>
                <span style="color:blue">排课冲突原因如下：</span><br>
                1. 在所排的教室、时间上<span style="color:red">已经安排了课程</span>。<br>
                2. 在所在的教室、选定的时间上，<span style="color:red">已经有</span>其它安排，比如<span style="color:red">考试、借用教室等安排</span>。
            </td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "排课有冲突", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>