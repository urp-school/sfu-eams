<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
    <table id="bar"></table>
    <table width="100%" align="center" class="formTable">
        <form method="post" action="" name="actionForm" onsubmit="return false;">
        <tr>
            <td class="title" style="width:15%" id="f_experience">教学经历（可输入800字):</td>
            <td><textarea name="experience" cols="70" rows="10">${(teacher.degreeInfo.experience)?default("")}</textarea></td>
        </tr>
        <tr>
            <td class="title" style="width:15%"id="f_achievements">科研成果（可输入800字):</td>
            <td><textarea name="achievements" cols="70" rows="10">${(teacher.degreeInfo.achievements)?default("")}</textarea></td>
        </tr>
        <tr>
            <td class="title" style="width:15%"id="f_partTimeJob">学术兼职（可输入800字):</td>
            <td><textarea name="partTimeJob" cols="70" rows="10">${(teacher.degreeInfo.partTimeJob)?default("")}</textarea></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center" class="darkColumn"><button onclick="saveTeacher()">保存</table></td>
        </tr>
        </form>
    </table>
    <br><br><br><br>
    <script>
        var bar = new ToolBar("bar", "${teacher.name?js_string}（${teacher.code}）个人简介", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var form = document.actionForm;
        
        function saveTeacher(){
            var a_fields = {
                'experience':{'l':'教学经历','r':false,'t':'f_experience','mx':800},
                'achievements':{'l':'科研成果','r':false,'t':'f_achievements','mx':800},
                'partTimeJob':{'l':'学术兼职','r':false,'t':'f_partTimeJob','mx':800}
            };
            var v = new validator(form , a_fields, null);
            if (v.exec()) {
                form.action="tutorManager_tutor.do?method=saveTeacher";
                form.submit();
            }
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>