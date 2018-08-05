<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <#assign captionName>教学活动教室明细汇总表［${calendar.studentType.name} ${calendar.year}${("（" + calendar.term + "）学期")?replace("学期）学期", "学期）")}］</#assign>
    <div id="PrintA">
        <#assign colWidths={
            '1':35,
            '2':180,
            '3':130,
            '4':80,
            '5':220,
            '6':150,
            '7':70,
            '8':80,
            '9':120,
            '10':70,
            '11':90,
            '12':70,
            '13':90,
            '14':70,
            '15':140,
            '16':140
        }/>
        <#assign colLength=0/>
        <#list 1..colWidths?size as i>
	        <#assign colLength=colLength + colWidths[i?string]/>
        </#list>
        <table width="${colLength}" class="listTable" style="text-align:center">
            <@table.thead>
                <@table.td text="序号" width=colWidths['1']?string/>
                <@table.td text="授课教师" width=colWidths['2']?string/>
                <@table.td text="职称" width=colWidths['3']?string/>
                <@table.td text="课程序号" width=colWidths['4']?string/>
                <@table.td text="课程名称" width=colWidths['5']?string/>
                <@table.td text="教师所在院系" width=colWidths['6']?string/>
                <@table.td text="学分" width=colWidths['7']?string/>
                <@table.td text="授课年级" width=colWidths['8']?string/>
                <@table.td text="课程性质" width=colWidths['9']?string/>
                <@table.td text="是否挂牌" width=colWidths['10']?string/>
                <@table.td text="授课语言类型" width=colWidths['11']?string/>
                <@table.td text="上课人数" width=colWidths['12']?string/>
                <@table.td text="上课周状态" width=colWidths['13']?string/>
                <@table.td text="上课时间" width=colWidths['14']?string/>
                <@table.td text="上课地点" width=colWidths['15']?string/>
                <@table.td text="教室设备配置" width=colWidths['16']?string/>
            </@>
            <#list activities as activity>
            <tr>
                <td>${activity_index + 1}</td>
                <td>${activity.task.arrangeInfo.teacherNames}</td>
                <td>${activity.task.arrangeInfo.teacherTitleNames}</td>
                <td>${activity.task.seqNo}</td>
                <td>${activity.task.course.name}</td>
                <td>${activity.task.arrangeInfo.teacherDepartNames}</td>
                <td>${activity.task.course.credits}</td>
                <td>${(activity.task.teachClass.enrollTurn)?if_exists}</td>
                <td>${activity.task.courseType.name}</td>
                <td>${activity.task.requirement.isGuaPai?string("是", "否")}</td>
                <td>${activity.task.requirement.teachLangType.name}</td>
                <td>${activity.task.teachClass.stdCount}</td>
                <td>${activity.weekState}</td>
                <td>${activity.units}</td>
                <td>${activity.room.name}</td>
                <td>${(activity.room.configType.name)?if_exists}</td>
            </tr>
            </#list>
        </table>
    </div>
    <table>
        <tr>
            <td><pre>
注：1.表中上课时间一栏：101表示周一第1节课，102表示周一第2节课，依次类推。
　　2.由于开学选课本科表内容近期可能尚有部分修改，最终以网上系统查询为准。</pre></td>
        </tr>
    </table>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="template" value="roomActivityDetail.xls"/>
        <input type="hidden" name="fileName" value="${captionName}"/>
        <#list RequestParameters?keys as key>
        <input type="hidden" name="${key}" value="${RequestParameters[key]}"/>
        </#list>
    </form>
    <script>
        var bar = new ToolBar("bar", "${captionName}", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("以Excel形式查看", "exportList()", "excel.png");
        bar.addPrint();
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var form = document.actionForm;
        
        function exportList(){
            form.action="?method=export";
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>