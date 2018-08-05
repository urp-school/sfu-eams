<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table width="100%" cellpadding="0" cellspacing="0" style="padding: 0px; border-spacing: 0px">
        <th style="color: blue;background-color:Azure">学生学籍变动基本信息</th>
        <tr valign="top">
            <td style="color: blue" style="padding: 0px; border-spacing: 0px">
                <table class="infoTable" width="100%">
                    <tr>
                        <td class="title">学号：</td>
                        <td class="content">${alteration.std.code}</td>
                        <td class="title">姓名：</td>
                        <td class="content">${alteration.std.name}</td>
                    </tr>
                    <tr>
                        <td class="title">学生类别：</td>
                        <td class="content">${alteration.std.type.name}</td>
                        <td class="title">院系所：</td>
                        <td class="content">${alteration.std.department.name}</td>
                    </tr>
                    <tr>
                        <td class="title">专业：</td>
                        <td class="content">${alteration.std.major.name}</td>
                        <td class="title">学籍状态：</td>
                        <td class="content">${(alteration.beforeStatus.state.name)?default("?")}-${(alteration.afterStatus.state.name)?default("?")}</td>
                    </tr>
                    <tr>
                        <td class="title">变动类型：</td>
                        <td class="content">${alteration.mode.name}</td>
                        <td class="title">变动日期：</td>
                        <td class="content">${alteration.alterBeginOn?string("yyyy-MM-dd")}</td>
                    </tr>
                </table>
                <span>注：学籍状态中的“？”表示状态不明或未设定。</span>
            </td>
        </tr>
        <tr height="15px">
            <td></td>
        </tr>
               <tr height="15px">
            <td></td>
        </tr>
        <th style="color: blue;background-color:Azure">学生选课</th>
        <tr valign="top">
            <td style="color: blue" style="padding: 0px; border-spacing: 0px">
                <#list calendars as calendar>
                    <#if (calendarTakeMap[calendar.id?string]?default(0)?int > 0)>
                <table width="100%">
                    <th style="text-align:left" class="darkColumn">${calendar.year} <#if calendar.term?contains("学期")>${calendar.term}<#else>第${calendar.term}学期</#if></th>
                </table>
                <@table.table width="100%" id="courseTakeList" + calendar.id>
                   <@table.thead>
                     <@table.td text=""/>
                     <@table.td width="8%" name="attr.taskNo"/>
                     <@table.td width="8%" name="attr.courseNo"/>
                     <@table.td name="entity.course"/>
                     <@table.td name="entity.courseType"/>
                     <@table.td text="班级"/>
                     <@table.td text="授课教师"/>
                     <@table.td text="修读类别" width="8%"/>
                   </@>
                   <#assign iPoint = 0/>
                   <#list courseTakes as take>
                       <#if take.task.calendar.id == calendar.id>
                           <#if iPoint % 2==1><#assign class="grayStyle"/><#else><#assign class="brightStyle"/></#if>
                       <tr class="${class}" align="center" onmouseover="swapOverTR(this,this.className)"onmouseout="swapOutTR(this)" onclick="onRowChange(event)">
                        <@table.selectTd id="courseTakeId" value=take.id/>
                        <td>${take.task.seqNo?if_exists}</td>
                        <td>${take.task.course.code}</td>
                        <td><@i18nName take.task.course?if_exists/></td>
                        <td><@i18nName take.task.courseType?if_exists/></td>
                        <td><@i18nName (take.student.firstMajorClass)?if_exists/></td>
                        <td>${take.task.arrangeInfo.teacherNames}</td>
                        <td <#if take.courseTakeType.id?string=='4' ||take.courseTakeType.id?string=='3'> style="color:red"</#if>><@i18nName take.courseTakeType/></td>
                       </tr>
                           <#assign iPoint = iPoint + 1/>
                       </#if>
                   </#list>
                </@>
                    </#if>
                </#list>
            </td>
        </tr>
    </table>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="stdId" value="${alteration.std.id}"/>
    </form>
    <script>
        <#assign barCaption>学生学籍变动相关处理<font color="blue">（学号：${alteration.std.code}）</font></#assign>
        var bar = new ToolBar("bar", "${barCaption?js_string}", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("删除", "removeRelation()");
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var form = document.actionForm;
        
        function removeRelation() {
            var courseGradeIds = getSelectIds("courseGradeId");
            var courseTakeIds = getSelectIds("courseTakeId");
            if (isEmpty(courseGradeIds + courseTakeIds)) {
                alert("请选择要操作的记录。");
                return;
            }
            if (confirm("确认要删除下面所选择的记录吗？")) {
                form.action = "studentAlteration.do?method=removeRelation";
                if (isNotEmpty(courseGradeIds)) {
                    addInput(form, "courseGradeIds", courseGradeIds, "hidden");
                }
                if (isNotEmpty(courseTakeIds)) {
                    addInput(form, "courseTakeIds", courseTakeIds, "hidden");
                }
                addInput(form, "params", "<#list RequestParameters?keys as key><#if key != "method">&${key}=${RequestParameters[key]}</#if></#list>", "hidden");
                form.submit();
            }
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>