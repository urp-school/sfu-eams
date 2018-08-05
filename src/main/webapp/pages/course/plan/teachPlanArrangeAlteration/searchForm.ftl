<table width="100%" cellpadding="0">
    <tr class="infoTitle" valign="top" style="font-size:10pt">
        <td class="infoTitle" align="left" valign="bottom" colspan="2">
            <img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<b>查询选项</b>
        </td>
    </tr>
    <tr>
        <td style="font-size:0px" colspan="2">
            <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
        </td>
    </tr>
    <tr>
        <td>创建/修改者：</td>
        <td><input type="text" name="alteration.alterationBy" value="${RequestParameters["alteration.alterationBy"]?if_exists}" maxlength="50" style="width:100px"/></td>
    </tr>
    <tr>
        <td>访问路径：</td>
        <td><input type="text" name="alteration.alterationFrom" value="${RequestParameters["alteration.alterationFrom"]?if_exists}" maxlength="30" style="width:100px"/></td>
    </tr>
    <tr>
        <td>记录状态：</td>
        <td>
            <select name="alteration.happenStatus" style="width:100px">
                <option value="">全部</option>
                <option value="1">新建</option>
                <option value="2">修改</option>
                <option value="3">删除</option>
            </select>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <fieldSet align=center> 
                <legend>操作时间</legend>
                    从：<input type="text" name="beginAt" onfocus="calendar()" maxlength="10" value="${(RequestParameters['logBeginDate']?string('yyyy-MM-dd'))?default('')}" style="width:130"/><br>
                    到：<input type="text" name="endAt" onfocus="calendar()" maxlength="10" value="${(RequestParameters['logEndDate']?string('yyyy-MM-dd'))?default('')}" style="width:130"/>
                </legend>
            </fieldSet>
        </td>
    </tr>
    <tr height="25px">
        <td colspan="2" align="center">
            <button onclick="search()">查询</button>&nbsp;<button onclick="formReset()">重置</button>
        </td>
    </tr>
</table>
<#include "/templates/stdTypeDepart3Select.ftl"/>