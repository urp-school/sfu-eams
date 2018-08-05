<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
        <form name="resourceStatForm" method="post" action="" target="_blank" >
<table class="frameTable_title">
<tr>
<td width="20%"></td>
<td><#include "/pages/course/calendar.ftl"/></td>
</tr>
</table>
<#assign ss = {'1':5}/>
<table class="frameTable">
    <tr>
        <td class="frameTable_content" colspan="2">
            <table width="100%">
                <tr>
                    <td align="left" valign="bottom">
                        <img src="${static_base}/images/action/info.gif" align="top"/>
                        <B>教学楼教室占用情况查询区</B>
                    </td>
                </tr>
                <tr>
                    <td colspan="8" style="font-size:0px">
                        <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
       <td class="frameTable_view" style="width:180px">
        <#include "searchForm.ftl"/>
       </td>
       <td class="frameTable_view" >
        <#include "unitsCondition.ftl"/>
        <center><input type="button" class="buttonStyle" value="<@bean.message key="action.query"/>" onClick="statBuilding()" style="width:60px"/></center>
       </td>
    </tr>
        <input type="hidden" name="units" value=""/>
</table>
    </form>
<script language="javascript">
    var form =document.resourceStatForm;
    var action="roomResourceStat.do";
    
    if (${currentWeek}!=0) {
        document.resourceStatForm.weekSelect.selectedIndex=${currentWeek}-1;
    }
    
    var weekExamTurns=new Array(1);
    var examTurns = new Array(7);
    <#list weeks as week>
    examTurns[${week.id}] = new Object();
        <#list 1..maxUnits as unit>
    examTurns[${week.id}].td${unit} = 0;
        </#list>
    </#list>
    weekExamTurns[0]=examTurns;
    
    //增加排考场次信息表
    function addExamTurns(){
       var newExamTurns = new Array(7);
       <#list weeks as week>
       newExamTurns[${week.id}] = new Object();
            <#list 1..maxUnits as unit>
       newExamTurns[${week.id}].td${unit} = 0;
            </#list>
       </#list>
       weekExamTurns.push(newExamTurns);
    }
   
    //删除最后一个排考场次信息表
    function removeExamTurns(){
       weekExamTurns.pop();
    }

     function statBuilding(){
        if(document.resourceStatForm.district.options[document.resourceStatForm.district.selectedIndex].text=="全部" || document.resourceStatForm.building.options[document.resourceStatForm.building.selectedIndex].text=="全部") {
            alert("必须选择教学楼！");
            return;
        }
        form.action=action+"?method=stat";
        form["units"].value = getTurnInfo();
        form.submit();
    }
    
    function changeTurn(week, turn,tableId) {
        var weekIndex=getWeekIndex(tableId);
        var status = weekExamTurns[weekIndex][week]["td" + turn];
        if (status == 1) {
            _changeTurn(week, turn, 0,tableId);
        }else{
            _changeTurn(week, turn, 1,tableId);
        }
    }
    
    function selectColumnTurn(selectTurn, event,tableId) {
        var ele = getEventTarget(event);
        var weekIndex=getWeekIndex(tableId);
        for (var i = 0; i < weekExamTurns[weekIndex].length; i++) {
            var turns =  weekExamTurns[weekIndex][i];
            if (null != turns) {
                for (var turn in turns) {
                    if (turn == "td" + selectTurn) {
                        _changeTurn (i, turn.substring(2), (ele.checked ? 1 : 0),tableId);
                    }
                }
            }
        }
    }
    
    function selectRowTurn(week, event,tableId) {
        var ele = getEventTarget(event);
        var weekIndex=getWeekIndex(tableId);
        var turns = weekExamTurns[weekIndex][week];
        for (var turn in turns) {
            _changeTurn(week, turn.substring(2), (ele.checked ? 1 : 0),tableId);
        }
    }
    
    function _changeTurn(week, turn, status,tableId) {
        var td = document.getElementById(tableId+"_td" + week + "_" + turn);
        var weekIndex=getWeekIndex(tableId);
        if (status == 0) {
            td.style.backgroundColor = "";
            td.innerHTML = "";
             weekExamTurns[weekIndex][week]["td" + turn] = 0;
        } else {
            td.style.backgroundColor = "#94aef3";
            td.innerHTML = "使用";
             weekExamTurns[weekIndex][week]["td" + turn] = 1;
        }
    }
    
    function getWeekIndex(tableId){
        return parseInt(tableId.substring("timeTable_".length));
    }
    function getTurnInfo() {
        var selectWeekAndTurn = "";
        for(var k=0; k < weekExamTurns.length; k++){
            var examTurns = weekExamTurns[k];
            for (var i = 0; i < examTurns.length; i++) {
                var turns = examTurns[i];
                if (null != turns) {
                    for (var turn in turns) {
                        if (turns[turn] == 1) {
                            selectWeekAndTurn += i + "," + turn.substring(2) + ";";
                        }
                    }
                }
            }
        }
        return selectWeekAndTurn;
    }
    
    function changeStyle(xStyle) {
        xStyle = !xStyle ? 0 : xStyle;
        switch (xStyle) {
            case 0:
                for (var i = 1; i <= ${weeks?size}; i++) {
                    $("row_" + i).checked = false;
                    if (!$("row_" + i).checked) {
                        $("row_" + i).click();
                    }
                    $("row_" + i).click();
                }
                for (var i = 1; i <= ${maxUnits}; i++) {
                    $("col_" + i).checked = false;
                    if (!$("col_" + i).checked) {
                        $("col_" + i).click();
                    }
                    $("col_" + i).click();
                }
                break;
            case 1:
                for (var i = 1; i <= 5; i++) {
                    if ($("row_" + i).checked) {
                        $("row_" + i).click();
                    }
                    $("row_" + i).click();
                }
                for (var i = 6; i <= 7; i++) {
                    if (!$("row_" + i).checked) {
                        $("row_" + i).click();
                    }
                    $("row_" + i).click();
                }
                break;
            case 2:
                for (var i = 1; i <= 8; i++) {
                    if ($("col_" + i).checked) {
                        $("col_" + i).click();
                    }
                    $("col_" + i).click();
                }
                for (var i = 9; i <= 12; i++) {
                    if (!$("col_" + i).checked) {
                        $("col_" + i).click();
                    }
                    $("col_" + i).click();
                }
                for (var i = 6; i <= 7; i++) {
                    if (!$("row_" + i).checked) {
                        $("row_" + i).click();
                    }
                    $("row_" + i).click();
                }
                break;
            case 3:
                for (var i = 1; i <= 8; i++) {
                    if (!$("col_" + i).checked) {
                        $("col_" + i).click();
                    }
                    $("col_" + i).click();
                }
                for (var i = 9; i <= 12; i++) {
                    if ($("col_" + i).checked) {
                        $("col_" + i).click();
                    }
                    $("col_" + i).click();
                }
                for (var i = 6; i <= 7; i++) {
                    if (!$("row_" + i).checked) {
                        $("row_" + i).click();
                    }
                    $("row_" + i).click();
                }
        }
    }
</script>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "/templates/districtBuildingSelect.ftl"/>
</body>
<#include "/templates/foot.ftl"/> 