    <table width="100%">
        <tr>
            <td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B>计划查询(模糊输入)</B></td>
        </tr>
        <tr>
            <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"></td>
        </tr>
        <form name="planSearchForm" method="post" action="" onsubmit="return false;">
        <#--因下些句致不能偏好设置影响
        <input type="hidden" name="pageNo" id="pageNo" value="1"/>
        <input type="hidden" name="pageSize" id="pageSize" value="20"/>
        -->
        <input type="hidden" name="params" value=""/>
        <#if withAuthority?exists>
            <input type="hidden" name="withAuthority" value="1"/>
        </#if>
        <tr>
            <td id="f_enrollTurn" width="40%"><@bean.message key="attr.enrollTurn"/>：</td>
            <td><input name="teachPlan.enrollTurn" type="text" value="" style="width:100px" maxlength="7"/></td>
        </tr>
        <tr>
            <td><@bean.message key="entity.studentType"/>：</td>
            <td>
                <select id="stdTypeOfSpeciality" name="teachPlan.stdType.id" value="" style="width:100px">
                    <option value="${stdTypeList?first.id}"></option>
                </select>
            </td>
        </tr>
        <tr>
            <td><@bean.message key="entity.department"/>：</td>
            <td>
                <select id="department" name="teachPlan.department.id" style="width:100px;">
                    <option value="<#if departmentList?size==1>${departmentList?first.id}</#if>"></option>
                </select>
            </td>
        </tr>
        <tr>
            <td><@bean.message key="entity.speciality"/>：</td>
            <td align="left">
                <select id="speciality" name="teachPlan.speciality.id" style="width:100px;">
                    <option value=""><@bean.message key="common.selectPlease"/></option>
                </select>
            </td>
        </tr>
        <tr>
            <td ><@bean.message key="entity.specialityAspect"/>：</td>
            <td align="left" >
                <select id="specialityAspect" name="teachPlan.aspect.id" style="width:100px;">
                    <option value=""><@bean.message key="common.selectPlease"/></option>
                </select>
            </td>
        </tr>
        <tr>
            <td><@msg.message key="std.specialityType"/>:</td>
            <td>
                <select name="teachPlan.speciality.majorType.id" onchange="changeMajorType(event)" style="width:100px;">
                    <option value="1"><@msg.message key="entity.firstSpeciality"/></option>
                    <option value="2"><@msg.message key="entity.secondSpeciality"/></option>
                </select>
            </td>
        </tr>
        <tr>
            <td  colspan="2">
                <input type="radio" name="teachPlan.isConfirm" checked value=""/><@msg.message key="common.all"/>
                <input type="radio" name="teachPlan.isConfirm" value="1"/><@msg.message key="action.affirm"/>
                <input type="radio" name="teachPlan.isConfirm" value="0"/><@msg.message key="action.negate"/>
            </td>
        </tr>
        <tr>
            <td align="left" valign="bottom" colspan="2">
                <input type="checkbox" name="type" onclick="switchPlanQuery(this.checked)" value="std"/><B><@msg.message key="teachPlan.person"/></B>
            </td>
        </tr>
        <tr>
            <td><@bean.message key="std.code"/>：</td>
            <td><input name="teachPlan.std.code" maxlength="32" type="text" value="" disabled="true" style="width:100px"/></td>
        </tr>
        <tr>
            <td><@msg.message key="attr.personName"/>：</td>
            <td><input name="teachPlan.std.name" maxlength="20" type="text" value="" disabled="true" style="width:100px"/></td>
        </tr>
            <input type="hidden" name="multi" value=""/>
            <input type="hidden" name="params" value=""/>
        <tr align="center">
            <td colspan="2">
                <input type="reset" class="buttonStyle" value="<@msg.message key="action.reset"/>" style="width:60px"/>
                <input type="submit" onClick="doSearch()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>          
            </td>
        </tr>
        </form>
    </table>
    <#include "/templates/stdTypeDepart3Select.ftl"/>
    <script>
        sds.majorTypeId=1;
        var form = document.planSearchForm;
        function switchPlanQuery(isStd){
            form['teachPlan.std.code'].disabled = !isStd;
            form['teachPlan.std.name'].disabled = !isStd;
        }
        function doSearch() {
            search();
        }
    </script>