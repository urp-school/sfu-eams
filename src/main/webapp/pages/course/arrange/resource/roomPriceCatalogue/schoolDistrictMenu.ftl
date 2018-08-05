                <table width="100%" id ="menuTable" style="font-size:10pt">
                    <tr class="infoTitle"  valign="top" width="20%" style="font-size:10pt">
                        <td class="infoTitle" align="left" valign="bottom">
                            <img src="${static_base}/images/action/info.gif" align="top"/>
                            <b>所在校区</b>
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size:0px">
                            <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
                        </td>
                    </tr>
                    <#list roomPrices as roomPrice>
                    <tr>
                        <td class="padding" id="item${roomPrice.id}" onclick="displayPrices(this, '${roomPrice.id}')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
                            &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom"/><#if (roomPrice.schoolDistrict)?exists><@i18nName roomPrice.schoolDistrict/><#else>默认价目表</#if>
                        </td>
                    </tr>
                    </#list>
                    <tr height="80%" valign="top" align="center">
                        <td><#if roomPrices?size == 0><A href="#" onclick="defaultSetting()"><font color="blue">请先配置默认教室级差价目表</font></A></#if></td>
                    </tr>
                </table>
