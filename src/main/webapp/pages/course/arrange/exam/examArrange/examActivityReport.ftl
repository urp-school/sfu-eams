<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <script>
        var bar = new ToolBar("bar", "期末考试日程安排一览表", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("打印", "print()");
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        $("message").innerHTML = "正在打开页面……";
    </script>
    <#assign tdWidth = 150/>
    <#assign termValue><#if calendar.term?ends_with("学期")>${calendar.term}<#else>第${(calendar.term == "1")?string("一", "二")}学期</#if></#assign>
    <table width="${(2 + result2?size) * tdWidth}px" style="font-size:18pt;font-weight:bold;text-align:center">
        <tr>
            <td>${calendar.year}学年${termValue}期末考试日程安排表</td>
        </tr>
    </table>
    <table class="listTable" style="font-size:12;text-align:center" width="${(2 + result2?size) * tdWidth}px" id="report">
        <tr>
            <td colspan="2">学院</td>
        <#list result2 as result>
            <td>${result[0].name}</td>
        </#list>
        </tr>
        <tr>
            <td width="${tdWidth}px">日期↓</td>
            <td width="${tdWidth}px">专业→</td>
        <#list result2 as result>
            <td width="${tdWidth}px">${result[1].name}<br>（${result[2]}人）</td>
        </#list>
        </tr>
        <#list times as time>
        <tr>
            <td colspan="2">${timeMap[time].startDay?string("yyyy-MM-dd")}　（${weekValues[timeMap[time].time.weekId - 1].name?replace("星期", "周")}）　${timeMap[time].startDay?string("HH:mm")}～${timeMap[time].endDay?string("HH:mm")}</td>
            <#list result2 as result>
            <td id="${time + "_" + result[0].code + "_" + result[1].code}"></td>
            </#list>
        </tr>
        </#list>
    </table>
    <script>
        function init() {
            $("message").innerHTML = "正在加载数据……";
        }
        <#--
        <#assign recordCount = 100/>
            <#if result_index % recordCount == 0>
                <#if result_index != 0>
        }
                </#if>
            </#if>
        </#list>
        <#if result1?size % recordCount != 0>
        </#if>
        -->
        
        function init1() {
        <#list result1 as result>
            document.getElementById("${result.startDay?string("yyyy-MM-dd HH:mm") + "-" + result.endDay?string("yyyy-MM-dd HH:mm")}_${result.task.teachClass.adminClasses?first.department.code}_${result.task.teachClass.adminClasses?first.speciality.code}").innerHTML = "${result.task.course.name}";
        </#list>
        }
        setTimeout("init()", 1);
        setTimeout("init1()", 5);
        <#--
        <#assign frequency = 70/>
        <#assign waitTime = frequency/>
        <#list 1..result1?size as i>
            <#if i % recordCount == 0>
        setTimeout("init${i}()", ${waitTime});
                <#assign waitTime = waitTime + frequency/>
            </#if>
        </#list>
        -->
        
        function initEnd() {
            $("message").innerHTML = "正在合并单元格……";
	        setTimeout("mergeTable()", ${result1?size + 100});
        }
        
        setTimeout("initEnd()", 200);
        
        function mergeTable(){
          mergeCol('report', 0, 1);
          $("message").innerHTML = "";
          $("message").style.display = "none";
        }
        
        function mergeCol(tableId, rowStart, colStart) {
            var rows = $(tableId).rows;
            for (var i = rowStart; i < rows.length; i++) {
                for (var j = colStart + 1; j < rows[i].cells.length;) {
                    if (rows[i].cells[j - 1].innerHTML == rows[i].cells[j].innerHTML && "" != rows[i].cells[j - 1].innerHTML && "" != rows[i].cells[j].innerHTML) {
                        rows[i].removeChild(rows[i].cells[j]);
                        rows[i].cells[j - 1].colSpan++;
                    } else {
                        j++;
                    }
                }
            }
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>