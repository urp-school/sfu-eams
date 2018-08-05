<#include "/templates/head.ftl"/>
<script src="dwr/interface/examActivityService.js"></script>
<script src='dwr/interface/teacherDAO.js'></script>
<body>
    <table id="bar"></table>
    <table class="formTable" width="100%">
        <form method="post" action="" name="actionForm" onsubmit="return false;">
        <tr>
            <td class="title" width="20%"><font color="red">*</font>分组名称：</td>
            <td width="20%"><input type="text" name="taskGroup.name" value="" maxlength="100" style="width:200px"/></td>
            <td>示例：<font color="blue">会计I一3三3</font>（时间点是排课后的时间点）。<br>意为：<font color="blue">会计I</font>（可缩写），星期<font color="blue">一</font>的第<font color="blue">3</font>大节（即，6-7节），星期<font color="blue">三</font>的第<font color="blue">3</font>大节。</td>
        </tr>
    </table>
    <#assign taskIds = ""/>
    <#assign tableHead = {}/>
    <#assign tableHead = tableHead + {'1':{'t':"课程序号", 'w':"8%"}}/>
    <#assign tableHead = tableHead + {'2':{'t':"课程名称", 'w':""}}/>
    <#assign tableHead = tableHead + {'3':{'t':"课程类别", 'w':"15%"}}/>
    <#assign tableHead = tableHead + {'4':{'t':"教学班", 'w':"25%"}}/>
    <#assign tableHead = tableHead + {'5':{'t':"教师", 'w':"15%"}}/>
    <#assign tableHead = tableHead + {'6':{'t':"计划人数", 'w':""}}/>
    <#assign tableHead = tableHead + {'7':{'t':"学分", 'w':""}}/>
    <#assign tableHead = tableHead + {'8':{'t':"周时", 'w':""}}/>
    <#assign tableHead = tableHead + {'9':{'t':"周数", 'w':""}}/>
    <#assign tableHead = tableHead + {'10':{'t':"课时", 'w':""}}/>
    <#assign tableHead = tableHead + {'11':{'t':"所属组", 'w':""}}/>
    <@table.table id="taskList" width="100%">
        <@table.thead>
            <#list 1..tableHead?size as i>
            <@table.td text=tableHead[i?string]['t'] width=tableHead[i?string]['w']/>
            </#list>
        </@>
        <@table.tbody datas=tasks;task>
            <#assign taskIds = taskIds + task.id + ","/>
            <td id="seqNo${task.id}">${task.seqNo}</td>
            <td>${task.course.name}</td>
            <td>${task.courseType.name}</td>
            <td title="${task.teachClass.name?html}" nowrap><span style="display:block;width:280px;overflow:hidden;text-overflow:ellipsis;"><#if task.teachClass.gender?exists>(<@i18nName task.teachClass.gender/>)</#if>${task.teachClass.name?html}</span></td>
            <td><@getTeacherNames task.arrangeInfo.teachers/></td>
            <td>${task.teachClass.planStdCount}</td>
            <td>${task.course.credits}</td>
            <td>${task.arrangeInfo.weekUnits}</td>
            <td>${task.arrangeInfo.weeks}</td>
            <td>${task.arrangeInfo.overallUnits}</td>
            <td>${(task.taskGroup.name)?if_exists}</td>
        </@>
        <tr>
            <td colspan="${tableHead?size}" style="color:red">注意：如果所选的教学任务的已经有组了，保存之后原来的挂牌分组将被解散或重新分组，所以操作一定要慎重。<br>
　　　例如：<br>
　　　　　　1. 有5条任务已经挂牌分组为AAA的任务组，这时现在教学任务中选择这5条记录挂牌分组为BBB，结果AAA将被解散，重新分组在BBB中。<br>
　　　　　　2. 同样有上述的已经挂牌分组的教学任务，如果其中选择了2条记录，再选择4条教学任务，组名为BBB，保存后AAA中的这2条任务将被解散，和后面的4条记录一起归入BBB中。
            </td>
        </tr>
    </@>
    <input type="hidden" name="taskIds" value="${taskIds}"/>
    <#assign beenTeacherIds><#list teachers as teacher>${teacher.id},</#list></#assign>
    <#assign beenTeacherNames><#list teachers as teacher>${teacher.name}<#if teacher_has_next>,</#if></#list></#assign>
    <table class="formTable" width="100%">
        <tr>
            <td class="title">已有教师(添加/修改结果)：</td>
            <td><label id="teacherNames" width="100%">${beenTeacherNames}</label><input type="hidden" name="teacherIds" value="${beenTeacherIds}"/></td>
        </tr>
        <tr>
            <td class="title" width="10%">院系/部门：</td>
            <td width="40%">
                <select id="department" onmouseover="displayDepartList(event);" onChange="displayTeacherList('teacher',this.value,true)" style="width:220px">
                    <option value=""></option>
                </select>
                <button onclick="resetTeacher()">还原</button>&nbsp;<button onclick="cleanTeacher()">清空</button>
            </td>
        </tr>
        <tr>
            <td class="title">教师：</td>
            <td>
                <select id="teacher" style="width:220px" onmouseover="displayTeacherList('teacher',department.value,false);">
                    <option value=""></option>
                </select>
                <button onclick="addTeacher()">添加</button>
            </td>
        </tr>
        </form>
    </table>
    <table class="formTable" width="100%">
        <tr>
            <td class="darkColumn" align="center"><button onclick="save()">保存</button></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "挂牌分组 － 核对/修正教师", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("保存", "saveGroup()");
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var departmentList= new Array();
        <#list teacherDepartList as department>
        departmentList[${department_index}] = {'id':'${department.id}','name':'<@i18nName department/>'};
        </#list>
        var form = document.actionForm;
        
        function cleanTeacher() {
            form['teacherIds'].value = "";
            $('teacherNames').innerHTML = "";
        }
        
        function addTeacher() {
            var teacher = $('teacher');
            if(teacher.value != "") {
                if(form['teacherIds'].value.indexOf(teacher.value) == -1) {
                    form['teacherIds'].value += teacher.value + ",";
                    if ("" != $("teacherNames").innerHTML) {
                        $("teacherNames").innerHTML += ",";
                    }
                    $("teacherNames").innerHTML += DWRUtil.getText('teacher');
                }
            }
        }
        
        function resetTeacher() {
            form['teacherIds'].value = "${beenTeacherIds}";
            $('teacherNames').innerHTML = "${beenTeacherNames}";
        }
        
        resetTeacher();
        
        function save() {
            if (form["taskGroup.name"].value.trim() == "") {
                alert("请起一个分组名称，并且名称的首尾不要有多余的空格。");
                return;
            }
            if (form["teacherIds"].value == "") {
                alert("请为这些任务指定任课的教师。");
                return;
            }
            var ddd = $("teacherNames").innerHTML.match(new RegExp(",", "gi"));
            var teacherCount = ((ddd == null) ? 0 : ddd.length) + 1;
            if (confirm("分组名称为：" + form["taskGroup.name"].value + "\n教学任务：${tasks?size}个\n任课老师：" + teacherCount + "位。\n\n要创建这样诉挂牌分组吗？")) {
                form.action = "taskGroup.do?method=saveGroup";
                form.submit();
            }
        }
    </script>
    <script language="JavaScript" type="text/JavaScript" src="scripts/course/DepartTeacher.js"></script>
</body>
<#include "/templates/foot.ftl"/>