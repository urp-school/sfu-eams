<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <table style="text-align:center">
        <tr>
            <td><h2>${calendar.year}${("（" + calendar.term + "）学期")?replace("学期）学期", "小学期")}教室占用情况一览表<h2></td>
        </tr>
        <#assign weekCountNum = 5/>
        <#assign normalUnitCount = 14/>
        <#assign specialUnitCount = 8/>
        <#assign total=(weekCountNum - 1) * normalUnitCount + specialUnitCount/>
        <tr>
            <td>
                <table class="listTable" style="text-align:center" width="${170 + total * 81}">
                    <tr>
                        <td colspan="${2 + total}" class="darkColumn" style="text-align:left;font-weight:bold">教室占用情况统计表（其中101表示周一第一节课，202表示周二第二节课，以此类推）</td>
                    </tr>
                    <tr>
                        <td width="50">序号</td>
                        <td width="120">教室</td>
                        <#assign toBreak = false/>
                        <#list 1..weekCountNum as i>
                            <#list 1..normalUnitCount as j>
                                <#if (i >= weekCountNum && j > specialUnitCount)>
			                        <#assign toBreak = true/>
                                    <#break/>
                                </#if>
                        <td width="80">${i + (j)?string("00")}</td>
                            </#list>
                            <#if toBreak>
                                <#break/>
                            </#if>
                        </#list>
                    </tr>activity.room.building.code,activity.room.name
                    <#list classrooms as room>
	                    <#assign pointW = 1/>
	                    <#assign pointU = 1/>
                    <tr>
                        <td width="50">${room_index + 1}</td>
                        <td width="120">${room.name} (${room.capacityOfCourse}/${room.capacity})</td>
                        <#--不要删除，下面语句用以调试-->
                        <#--<font color="red">${room.id + "_" + pointW + pointU?string("00")}</font>-->
                        <#list 1..total as k>
                        <td>${(roomMap[room.id + "_" + pointW + pointU?string("00")].weekState)?if_exists}</td>
                            <#assign pointU = k % normalUnitCount + 1/>
                            <#if k % normalUnitCount == 0>
                                <#assign pointW = pointW + 1/>
                            </#if>
                        </#list>
                    </tr>
                    </#list>
                </table>
            </td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "教学活动教室占用汇总表", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addPrint();
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>