                <table width="100%">
                    <tr>
                        <td align="left" valign="bottom">
                            <img src="${static_base}/images/action/info.gif" align="top"/>
                            <B><@bean.message key="action.advancedQuery"/></B>
                        </td>
                    <tr>
                    <tr>
                        <td colspan="8" style="font-size:0px">
                            <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
                        </td>
                    </tr>
                </table>
                <table onKeyDown="javascript:search();" width="100%">
                    <input type="hidden" name="pageNo" value="1">
                    <tr>
                        <td width="40%"><@bean.message key="attr.enrollTurn"/></td>
                        <td><input name="teachPlan.enrollTurn" maxlength="7" type="text" value="" style="width:100px"/></td>
                    </tr>
                    <tr>
                        <td><@bean.message key="entity.studentType"/></td>
                        <td>
                            <select id="stdTypeOfSpeciality" name="teachPlan.stdType.id" value="" style="width:100px">
                                <#if defaultStdType?exists>
                                    <#assign defaultStdTypeId=defaultStdType.id>
                                <#else>
                                    <#assign defaultStdTypeId=stdTypeList?first.id>
                                </#if>
                                <option value='${defaultStdTypeId}'></option>
                             </select>
                        </td>
                    </tr>
                    <tr>
                        <td><@bean.message key="entity.department"/></td>
                        <td> 
                            <select id="department" name="teachPlan.department.id"  style="width:100px;">
                               <option value=""><@bean.message key="common.selectPlease"/></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><@bean.message key="entity.speciality"/></td>
                        <td align="left">
                            <select id="speciality" name="teachPlan.speciality.id" style="width:100px;">
                               <option value=""><@bean.message key="common.selectPlease"/></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><@bean.message key="entity.specialityAspect"/></td>
                        <td align="left">
                            <select id="specialityAspect" name="teachPlan.aspect.id" style="width:100px;">
                                <option value=""><@bean.message key="common.selectPlease"/></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><@msg.message key="std.specialityType"/>:</td>
                        <td>
                            <select name="teachPlan.speciality.majorType.id" onchange="changeClassSpecialityType(event)" style="width:100px;">
                                <option value="1"><@msg.message key="entity.firstSpeciality"/></option>
                                <option value="2"><@msg.message key="entity.secondSpeciality"/></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="radio" name="teachPlan.isConfirm" value="1"><@msg.message key="action.affirm"/>
                            <input type="radio" name="teachPlan.isConfirm" value="0"><@msg.message key="action.negate"/>
                            <input type="radio" name="teachPlan.isConfirm" value=""><@msg.message key="common.all"/>
                        </td>
                    </tr>
                    <tr align="center">
                        <td colspan="2">
                            <input type="button" onClick="searchPlan()" class="buttonStyle" value="<@bean.message key="action.query"/>" style="width:60px"/>            
                        </td>
                    </tr>
                </table>
