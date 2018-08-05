<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table width="99%" align="center" class="infoTable">
        <tr>
            <td width="15%" class="title">工号:</td>
            <td width="35%">${teacher.code}</td>
            <td width="15%" class="title">名称:</td>
            <td width="35%">${teacher.name?html}</td>
        </tr>
        <tr>
            <td class="title">性别:</td>
            <td><@i18nName (teacher.gender)?if_exists/></td>
            <td class="title">职称:</td>
            <td><@i18nName (teacher.title)?if_exists/></td>
        </tr>
        <tr>
            <td class="title">毕业学校:</td>
            <td colspan="3"><@i18nName (teacher.degreeInfo.graduateSchool)?if_exists/></td>
        </tr>
        <tr>
            <td class="title" style="width:15%">教学经历:</td>
            <td colspan="3">${((teacher.degreeInfo.experience)?html?replace("\n", "<br>"))?default("")}</td>
        </tr>
        <tr>
            <td class="title" style="width:15%">科研成果:</td>
            <td colspan="3">${((teacher.degreeInfo.achievements)?html?replace("\n", "<br>"))?default("")}</td>
        </tr>
        <tr>
            <td class="title" style="width:15%">学术兼职:</td>
            <td colspan="3">${((teacher.degreeInfo.partTimeJob)?html?replace("\n", "<br>"))?default("")}</td>
        </tr>
    </table>
    <br><br><br><br>
    <script>
        var bar = new ToolBar("bar", "${teacher.name?js_string}（${teacher.code}）个人简介", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>