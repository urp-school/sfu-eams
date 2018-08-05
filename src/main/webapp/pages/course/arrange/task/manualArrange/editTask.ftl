<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="formTable" width="60%" align="center">
        <form method="post" action="" name="actionForm" onsubmit="return false;">
            <input type="hidden" name="taskId" value="${task.id}"/>
        <tr>
            <td class="darkColumn" colspan="3" style="text-align:center;font-weight:bold">已参选任务<font color="blue">选课人数上限</font>修改</td>
        </tr>
        <tr>
            <td class="title">课程序号：</td>
            <td colspan="2">${task.seqNo}</td>
        </tr>
        <tr>
            <td class="title">课程名称：</td>
            <td colspan="2">${task.course.name}（${task.course.code}）</td>
        </tr>
        <tr>
            <td class="title">任务实际人数：</td>
            <td colspan="2">${task.teachClass.stdCount}</td>
        </tr>
        <tr>
            <td class="title">任务人数上限：</td>
            <td colspan="2">${task.electInfo.maxStdCount}</td>
        </tr>
        <tr>
            <td class="title"width="30%" rowspan="2">更新类型：</td>
            <td colspan="2"><input type="radio" name="countType" value="0" onclick="onOff(this)"/>按教室听课人数（${task.arrangeInfo.activities?first.room.capacityOfCourse}）&nbsp;<input type="radio" name="countType" value="1" onclick="onOff(this)"/>按教室真正容量（${task.arrangeInfo.activities?first.room.capacity}）</td>
        </tr>
        <tr>
            <td><input type="radio" name="countType" value="2" onclick="onOff(this)"/>自定义</td>
            <td><input type="text" name="maxStdCount" value="" disabled maxlength="5" style="width:100px"/>人</td>
        </tr>
          <input type="hidden" name="forward" value="actionResult"/>
          <input type="hidden" name="params" value="<#list RequestParameters?keys as key><#if key != "method">&${key}=${RequestParameters[key]}</#if></#list>"/>
        </form>
        <tr>
            <td class="darkColumn" colspan="3" align="center"><button onclick="saveTask()">更新返回</button></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "同步更新教学任务", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("不作同步", "without()", "backward.gif");
        
        var form = document.actionForm;
        
        function without() {
            if (confirm("是否不作选课人数调整，直接返回列表？")) {
                parent.searchTask();
            }
        }
        
        function onOff(obj) {
            if (obj.value == 2) {
                form["maxStdCount"].disabled = "";
                form["maxStdCount"].value = 0;
            } else {
                form["maxStdCount"].disabled = "disabled";
                form["maxStdCount"].value = "";
            }
        }
        
        function saveTask() {
            var countType = getSelectId("countType");
            if (null == countType || "" == countType) {
                alert("请选择要更新选课人数的类型。");
                return;
            }
            if (countType == 2 && !/^\d+$/.test(form['maxStdCount'].value)) {
                alert("请有效的选课人数。");
                return;
            }
            form.action = "manualArrange.do?method=saveTask";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>