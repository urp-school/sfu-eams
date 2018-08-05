<table width="100%">
    <tr>
        <td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>评教结果查询(模糊查询)</B></td>
    </tr>
    <tr>
        <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
    </tr>
    <tr>
        <td><@bean.message key="entity.studentType"/></td>
        <td>
            <select id="stdType" name="evaluateTeacher.calendar.studentType.id" style="width:100px;">
            </select>
        </td>
    </tr>
    <tr>
        <td><@bean.message key="attr.year2year"/></td>
        <td><select id="year" name="evaluateTeacher.calendar.year" style="width:100px;">
            </select>
        </td>
    </tr>
    <tr>
        <td><@bean.message key="attr.term"/>
        </td>
        <td><select id="term" name="evaluateTeacher.calendar.term" style="width:100px;">
            </select>
        </td>
    </tr>
    <tr>
        <td>课程代码：</td>
        <td><input type="text" name="evaluateTeacher.course.code" value="${RequestParameters["evaluateTeacher.course.code"]?if_exists}" maxlength="50" style="width:100px"/></td>
    </tr>
    <tr>
        <td>课程名称：</td>
        <td><input type="text" name="evaluateTeacher.course.name" value="${RequestParameters["evaluateTeacher.course.name"]?if_exists}" maxlength="50" style="width:100px"/></td>
    </tr>
    <tr>
        <td colspan="2" align="center" height="50"><button onclick="search()">查询</button><br></td>
    </tr>
</table>
<input type="hidden" name="orderBy" value="evaluateTeacher.sumScore desc"/>
<#assign yearNullable=true>
<#assign termNullable=true>
<#include "/templates/calendarSelect.ftl"/>
<input type="hidden" name="searchFormFlag" value="beenStat"/>