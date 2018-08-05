            <table border="0">
                <tr>
                    <td colspan="2"><#if currentWeek!=0>当前为第${currentWeek}教学周</#if></td>
                </tr>
                <tr>
                    <td class="infoTitle" style="width:100%"><@bean.message key="common.schoolDistrict"/>:</td>
                    <td>
                        <select id="district" name="classroom.schoolDistrict.id" style="width:100px;">
                            <option value=""><@bean.message key="common.all"/></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="infoTitle"><@bean.message key="common.building"/>:</td>
                    <td align="left">
                        <select id="building" name="classroom.building.id" style="width:100px;">
                            <option value=""><@bean.message key="common.all"/></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="infoTitle">设备配置:</td>
                    <td align="left">
                        <@htm.i18nSelect datas=configTypes selected="" name="classroom.configType.id" style="width:100px">
                            <option value=""><@bean.message key="common.all"/></option>
                        </@>
                    </td>
                </tr>
                <tr>
                    <td class="infoTitle">教学周:</td>
                    <td align="left">
                        <select id="weekSelect" name="classroom.week" style="width:100px;">
                            <#list 1..weekNum as week>
                            <option value="${week}">第${week}周</option>
                            </#list>
                        </select>
                    </td>
                </tr>
            </table>
