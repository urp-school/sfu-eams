<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <@table.table id="substituteCourse" width="100%" headIndex="1" sortable="true">
        <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery(event)">
            <form method="post" action="stdSubstituteCourse.do?method=selector" name="actionForm" onsubmit="return false;">
            <td align="center"><img src="${static_base}/images/action/search.png"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/></td>
                <input type="hidden" name="isWithPlan" value="false"/>
                <input type="hidden" name="originIds" value="${RequestParameters["originIds"]?if_exists}"/>
                <input type="hidden" name="courseIds" value="${RequestParameters["courseIds"]?if_exists}"/>
            <td><input type="text" name="course.code" value="${RequestParameters["course.code"]?if_exists}" style="width:100%"/></td>
            <td><input type="text" name="course.name" value="${RequestParameters["course.name"]?if_exists}" style="width:100%"/></td>
            <td><input type="text" name="course.extInfo.period" value="${RequestParameters["course.extInfo.period"]?if_exists}" style="width:100%"/></td>
            <td><input type="text" name="course.weekHour" value="${RequestParameters["course.weekHour"]?if_exists}" style="width:100%"/></td>
            <td><input type="text" name="course.credits" value="${RequestParameters["course.credits"]?if_exists}" style="width:100%"/></td>
            </form>
        </tr>
        <@table.thead>
            <@table.selectAllTd id="courseId"/>
            <@table.sortTd name="attr.code" id="course.code"/>
            <@table.sortTd name="attr.infoname" id="course.name"/>
            <@table.sortTd name="common.courseLength" id="course.extInfo.period"/>
            <@table.sortTd text="周课时" id="course.weekHour"/>
            <@table.sortTd name="common.grade" id="course.credits"/>
        </@>
        <@table.tbody datas=courses;course>
            <@table.selectTd id="courseId" value=course.id/>
            <td>${course.code}</td>
            <td><a href="courseSearch.do?method=info&type=course&id=${course.id}"><@i18nName course/></a></td>
            <td>${(course.extInfo.period)?if_exists}</td>
            <td>${course.weekHour?if_exists}</td>
            <td>${course.credits?if_exists}</td>
        </@>
    </@>
    <script>
        var bar = new ToolBar("bar", "以替换的课表列表", null, true, true);
        bar.setMessage('<@getMessage/>')
        bar.addItem("选择增加", "addSubstitute()", "new.gif");
        bar.addItem("关闭列表", "closeSelector()");
        
        var form = document.actionForm;
        
        var courseArray = {
            <#list courses as course>
            '${course.id}':'（${course.code}）${course.name}'<#if course_has_next>,</#if>
            </#list>
        };
        
        function enterQuery(event) {
            if (portableEvent(event).keyCode == 13) {
                query();
            }
        }
        function query() {
            form.submit();
        }
        
        function addSubstitute() {
            var substituteSeq = getSelectIds("courseId");
            if (isEmpty(substituteSeq)) {
                alert("请选择要替代的课程。");
                return;
            }
            var substituteIds = substituteSeq.split(",");
            for(var i = 0; i < substituteIds.length; i++) {
                parent.form["substituteSeq"].options.add(new Option(courseArray[substituteIds[i]], substituteIds[i]));
            }
            parent.form["courseIds"].value += (isEmpty(parent.form["courseIds"].value) ? "" : ",") + substituteSeq;
            closeSelector();
        }
        
        function closeSelector() {
            parent.$("substituteDiv").style.display = "none";
            adaptFrameSize();
            parent.$("substituteIframe").src = "#";
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>