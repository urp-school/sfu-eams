    <table width="100%" cellpadding="0">
        <tr class="infoTitle" valign="top" style="font-size:10pt">
            <td class="infoTitle" align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>计划查询(模糊输入)</B></td>
        </tr>
        <tr>
            <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
        </tr>
        <form name="planSearchForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="pageNo" id="pageNo" value="1"/>
        <input type="hidden" name="pageSize" id="pageSize" value="20"/>
        <input type="hidden" name="params" value=""/>
        <tr>
            <td id="f_grade" width="50%">所在年级：</td>
            <td><input name="majorCourse.enrollTurn" type="text" value="" style="width:100%" maxlength="7"/></td>
        </tr>
        <tr>
            <td><@bean.message key="entity.studentType"/>：</td>
            <td>
                <select id="stdTypeOfSpeciality" name="majorCourse.stdType.id" value="" style="width:100px">
                    <option value="${stdTypeList?first.id}"></option>
                </select>
            </td>
        </tr>
        <tr>
            <td><@bean.message key="entity.department"/>：</td>
            <td>
                <select id="department" name="majorCourse.department.id" style="width:100px;">
                    <option value="<#if departmentList?size==1>${departmentList?first.id}</#if>"></option>
                </select>
            </td>
        </tr>
        <tr>
            <td>专业：</td>
            <td align="left">
                <select id="speciality" name="majorCourse.major.id" style="width:100px;">
                    <option value=""><@bean.message key="common.selectPlease"/></option>
                </select>
            </td>
        </tr>
        <tr>
            <td>专业方向：</td>
            <td >
                <select id="specialityAspect" name="majorCourse.majorField.id" style="width:100px;">
                    <option value=""><@bean.message key="common.selectPlease"/></option>
                </select>
            </td>
        </tr>
        <tr align="center" height="50px">
            <td colspan="2">
                <input type="submit" onClick="searchTeachPlan(1,20);" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>
                <input type="reset" class="buttonStyle" value="<@msg.message key="action.reset"/>" style="width:60px"/>
            </td>
        </tr>
        </form>
    </table>
    <script>
        var form = document.planSearchForm;
        function searchTeachPlan() {
           form.action = "majorSubstituteCourse.do?method=search";
           form.target = "planListFrame";
           form.submit();
        }
        inputElementWidth = "100%";
    </script>
    <#include "/templates/stdTypeDepart3Select.ftl"/>
