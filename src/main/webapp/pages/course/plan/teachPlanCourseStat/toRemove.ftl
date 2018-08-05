<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table class="infoTable" width="100%">
        <tr>
            <td class="title">课程代码</td>
            <td>${course.code}</td>
            <td class="title">课程名称</td>
            <td>${course.name}</td>
        </tr>
        <tr>
            <td class="title">初设学分</td>
            <td>${course.credits?default(0)}</td>
            <td class="title">初设周课时</td>
            <td>${course.weekHour?default(0)}</td>
        </tr>
        <tr>
            <td class="title">初设学时</td>
            <td>${course.extInfo.period?default(0)}</td>
            <td class="title">计划数</td>
            <td style="font-family:宋体,MiscFixed">${planCount[0] + planCount[1]}（专业：${planCount[0]}/个人：${planCount[1]}）</td>
        </tr>
    </table>
    <@table.table id="planCourseTable" width="100%">
        <@table.thead>
            <@table.selectAllTd id="teachPlanId"/>
            <@table.td text="所在年级"/>
            <@table.td text="学生类别"/>
            <@table.td text="院系"/>
            <@table.td text="专业"/>
            <@table.td text="专业方向"/>
            <@table.td text="所设学期"/>
            <@table.td text="所属课程组"/>
            <@table.td text="开课院系"/>
            <@table.td text="计划状态"/>
        </@>
        <@table.tbody datas=results;plan>
            <@table.selectTd id="teachPlanId" value=plan.id/>
            <td>${plan.enrollTurn}</td>
            <td>${plan.stdType.name}</td>
            <td title="${plan.department.name}" nowrap><span style="display:block;width=100px;overflow:hidden;text-overflow:ellipsis">${plan.department.name}</span></td>
            <td title="${(plan.speciality.name)?if_exists}" nowrap><span style="display:block;width=100px;overflow:hidden;text-overflow:ellipsis">${(plan.speciality.name)?if_exists}</span></td>
            <td title="${(plan.aspect.name)?if_exists}" nowrap><span style="display:block;width=100px;overflow:hidden;text-overflow:ellipsis">${(plan.aspect.name)?if_exists}</span></td>
            <td>${plan.getPlanCourse(course).termSeq}</td>
            <td title="${plan.getPlanCourse(course).courseGroup.courseType.name}" nowrap><span style="display:block;width=100px;overflow:hidden;text-overflow:ellipsis">${plan.getPlanCourse(course).courseGroup.courseType.name}</span></td>
            <td>${plan.getPlanCourse(course).teachDepart.name}</td>
            <td nowrap><span style="font-weight:bold;color:${(plan.std)?exists?string("red", "blue")}">${(plan.std)?exists?string("个人", "专业")}</td>
        </@>
    </@>
    <@htm.actionForm name="actionFrom" action="teachPlanCourseStat.do" entity="teachPlan" onsubmit="return false;">
        <input type="hidden" name="courseId" value="${course.id}"/>
        <input type="hidden" name="teachPlanIds" value=""/>
        <input type="hidden" name="params" value="${RequestParameters["params"]?if_exists}"/>
    </@>
    <script>
        var bar = new ToolBar("bar", "${course.name}（${course.code}）所在培养计划", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("选择删除", "removeSome()");
        bar.addItem("全部删除", "removeAll()");
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        function removeSome() {
            var planIds = getSelectIds("teachPlanId");
            if (isEmpty(planIds)) {
                alert("请选择培养计划，以在从中删除该门课程。");
                return;
            }
            if (confirm("是否要批量从已选的培养计划中删除该门课程？")) {
                form.action = "teachPlanCourseStat.do?method=remove";
                form["teachPlanIds"].value = planIds;
                form.submit();
            }
        }
        
        function removeAll() {
            if (confirm("是否要批量从下面所有的培养计划中删除该门课程？")) {
                form.action = "teachPlanCourseStat.do?method=remove";
                form["teachPlanIds"].value = "";
                form.submit();
            }
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>