<table width="100%">
    <tr class="infoTitle" valign="top" style="font-weight:bold">
        <td><img src="${static_base}/images/action/info.gif"/>查询选项（模糊查询）</td>
    </tr>
    <tr class="infoTitle" valign="top" style="font-size:10pt;font-weight:bold">
        <td><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
    </tr>
</table>
<table width="100%" onkeypress="DWRUtil.onReturn(event, search)">
    <tr>
        <td>日志编号：</td>
        <td align="right">#<input type="text" name="gradeLog.id" value="" maxlength="19" style="width:90px;"/></td>
    </tr>
    <tr>
        <td width="100px"><@bean.message key="entity.studentType"/>：</td>
        <td>
            <select id="stdTypeOfSpeciality" name="stdTypeId" style="width:100px;">
                <option value=""></option>
            </select>
        </td>
    </tr>
    <tr>
        <td><@bean.message key="attr.year2year"/>：</td>
        <td>
            <select id="year" name="year" style="width:100px;">
                <option value=""></option>
            </select>
        </td>
    </tr>
    <tr>
        <td><@bean.message key="attr.term"/>：</td>
        <td>
            <select id="term" name="term" style="width:100px;">
                <option value=""></option>
            </select>
       </td>
    </tr>
    <tr>
        <td>学生学号：</td>
        <td><input type="text" name="stdCode" value="" maxlength="20" style="width:100px;"/></td>
    </tr>
    <tr>
        <td>学生姓名：</td>
        <td><input type="text" name="stdName" value="" maxlength="30" style="width:100px;"/></td>
    </tr>
    <tr>
        <td>课程序号：</td>
        <td><input type="text" name="taskSeqNo" value="" maxlength="5" style="width:100px;"/></td>
    </tr>
    <tr>
        <td>课程代码：</td>
        <td><input type="text" name="courseCode" value="" maxlength="10" style="width:100px;"/></td>
    </tr>
    <tr>
        <td>课程代码：</td>
        <td><input type="text" name="courseName" value="" maxlength="30" style="width:100px;"/></td>
    </tr>
    <tr>
        <td>记录状态：</td>
        <td>
            <select name="gradeLog.status" style="width:100px;">
                <option value="">全部</option>
                <option value="0">开始</option>
                <option value="1">添加</option>
                <option value="2">修改</option>
                <option value="3">删除</option>
            </select>
       </td>
    </tr>
    <tr>
        <td>描述(关键字)：</td>
        <td><input type="text" name="gradeLog.context" value="" maxlength="30" style="width:100px;"/></td>
    </tr>
    <tr>
        <td colspan="2" style="padding-left:36px;text-indent:-36px;color:green">提示：描述查询，填入关键字模糊查询，通配符为“%”。</td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <fieldSet align=center> 
                <legend>记录时间：</legend>
                    从：<input type="text" name="beginOn" onfocus="calendar()" maxlength="10" value="" style="width:60"/>&nbsp;<input type="text" name="beginAt" maxlength="5" value="" style="width:60"/><br>
                    到：<input type="text" name="endOn" onfocus="calendar()" maxlength="10" value="" style="width:60"/>&nbsp;<input type="text" name="endAt" maxlength="5" value="" style="width:60"/>
                </legend>
            </fieldSet>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="padding-left:36px;text-indent:-36px;color:green">提示：与教学日历为条件的时间范围是交集。格式为：日期 + 时间，时间默认为 00:00 ，范围为：[起始日 00:00 ，结束次日 00:00)。</td>
    </tr>
    <tr>
        <td>操作者帐号：</td>
        <td><input type="text" name="userCode" value="" maxlength="20" style="width:100px;"/></td>
    </tr>
    <tr>
        <td>操作者姓名：</td>
        <td><input type="text" name="userName" value="" maxlength="30" style="width:100px;"/></td>
    </tr>
    <input type="hidden" name="orderBy" value="gradeLog.generateAt desc"/>
    <tr height="30px">
        <td colspan="2" align="center">
            <button onclick="search()">查询</button></button>
        </td>
    </tr>
</table>
<#include "/templates/stdTypeDepart3Select.ftl"/>
<script src='dwr/interface/calendarDAO.js'></script>
<script src='scripts/common/CalendarSelect.js'></script>
<script>
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var dd = new CalendarSelect("stdTypeOfSpeciality","year","term",true,true,true,false);
    dd.init(stdTypeArray);
    sds.firstSpeciality=1;
    
    function changeSpecialityType(event){
       var select = getEventTarget(event);
       sds.firstSpeciality=select.value;
       fireChange($("department"));
    }
</script>