小节查询（清空为全部）：
<input type="radio" name="unitStyle" value="2" onclick="changeStyle(2)"/>周一～周五（白天）
<input type="radio" name="unitStyle" value="3" onclick="changeStyle(3)"/>周一～周五（晚上）
<input type="radio" name="unitStyle" value="1" onclick="changeStyle(1)"/>周一～周五（全天）
<input type="radio" name="unitStyle" value="0" onclick="changeStyle(0)" checked/>清空
<table width="100%" class="listTable" id="timeTable_0">
    <tr align="center" class="darkColumn">
        <td width="10%"></td>
        <#list 1..maxUnits as unit>
            <td><input type="checkbox" id="col_${unit}" onclick="selectColumnTurn(${unit},event,'timeTable_0')"/>${unit}</td>
        </#list>
    </tr>
    <#list weeks as week>
        <tr align="center">
            <td class="darkColumn">${week.name}<input type="checkbox" id="row_${week.id}" onclick="selectRowTurn(${week.id},event,'timeTable_0')"/></td>
            <#list 1..maxUnits as unit>
                <td title="点击选中或者取消" onClick="changeTurn(${week.id},'${unit}','timeTable_0')" id="timeTable_0_td${week.id}_${unit}" style="cursor:hand"></td>
            </#list>
        </tr>
    </#list>
</table>
