<#include "/templates/head.ftl"/>
<#import "/pages/course/arrange/apply/cycleType.ftl" as RoomApply/>
<BODY>
    <#assign cycyleNames={'1':'天','2':'周','4':'月'}/>
    <table id="myBar" width="100%"></table>
    <table style="font-size:14px;border-bottom:black 1px dotted" width="600px" align="center">
        <tr>
            <td>
                <table style="font-size:14px;line-height:0.75cm" width="100%">
                    <tr>
                        <th style="text-align:center;font-size:20px;height:70px;vertical-align:middle">教室借用凭证（存根）</th>
                    </tr>
                    <tr>
                        <#assign tdStyle="border-bottom:black 1px solid"/>
                        <#assign departmentValue = department?default('')?replace("学院", "")/>
                        <#assign borrowerName = roomApply.borrower.user.userName/>
                        <td style="text-align:justify;text-justify:inter-ideograph;text-indent:28px;font-family:宋体,MiscFixed">兹有<label style="${tdStyle}<#if (departmentValue?default('')?length <= 5)>;width:120px;text-indent:0;text-align:center;">${departmentValue?default('')}<#else>;padding:6px">　${departmentValue?default('')}　</#if></label>学院<#if roomApply.borrower.user.defaultCategory.id == 1><label style="${tdStyle}<#if (borrowerName?default('')?length <= 8)>;width:120px;text-indent:0;text-align:center;">${borrowerName}<#else>;padding:6px">　${borrowerName}　</#if></label>同学（学号<label style="${tdStyle};width:120px;text-indent:0;text-align:center;">${roomApply.borrower.user.name}</label>）<#elseif roomApply.borrower.user.defaultCategory.id == 2><label style="${tdStyle}<#if (borrowerName?default('')?length <= 8)>;width:120px;text-indent:0;text-align:center;">${borrowerName}<#else>;padding:6px">　${borrowerName}　</#if></label>老师（工号<label style="${tdStyle};width:120px;text-indent:0;text-align:center;">${roomApply.borrower.user.name}</label>）<#else><label style="${tdStyle}<#if (borrowerName?default('')?length <= 8)>;width:120px;text-indent:0;text-align:center;">${borrowerName}<#else>;padding:6px">　${borrowerName}　</#if></label>用户（帐号<label style="${tdStyle};width:120px;text-indent:0;text-align:center;">${roomApply.borrower.user.name}</label>）</#if>，因<label style="${tdStyle};padding:6px">　${roomApply.activityName}　</label>之需，于<label style="${tdStyle};padding:6px">　${roomApply.applyTime.dateBegin?string("yyyy-MM-dd")}～${roomApply.applyTime.dateEnd?string("yyyy-MM-dd")}（${roomApply.applyTime.timeBegin}-${roomApply.applyTime.timeEnd}） </label>借用<label style="${tdStyle};padding:6px">　<#list roomApply.getClassrooms()?if_exists as classroom><@i18nName classroom?if_exists/><#if classroom_has_next>、</#if></#list>　</label>，并使用教学设备（用“√”，“×”表示）电脑<label style="${tdStyle};width:70px"></label>投影仪<label style="${tdStyle};width:70px"></label>扩音设备<label style="${tdStyle};width:70px"></label>。</td>
                    </tr>
                </table>
                <table width="100px" align="right" style="font-size:14px;text-align:center;font-family:宋体,MiscFixed">
                    <tr>
                        <td height="50px" valign="bottom">教务处</td>
                    </tr>
                    <tr>
                        <td height="50px" valign="top">${now?string("yyyy-MM-dd")}</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <table style="font-size:14px" width="600px" align="center">
        <tr>
            <td>
                <table style="font-size:14px;line-height:0.75cm" width="100%">
                    <tr>
                        <th style="text-align:center;font-size:20px;height:70px;vertical-align:middle">教室借用凭证（暨使用回执）</th>
                    </tr>
                    <tr>
                        <td style="text-align:justify;text-justify:inter-ideograph;text-indent:28px;font-family:宋体,MiscFixed">兹有<label style="${tdStyle}<#if (departmentValue?default('')?length <= 5)>;width:120px;text-indent:0;text-align:center;">${departmentValue?default('')}<#else>;padding:6px">　${departmentValue?default('')}　</#if></label>学院<#if roomApply.borrower.user.defaultCategory.id == 1><label style="${tdStyle}<#if (borrowerName?default('')?length <= 8)>;width:120px;text-indent:0;text-align:center;">${borrowerName}<#else>;padding:6px">　${borrowerName}　</#if></label>同学（学号<label style="${tdStyle};width:120px;text-indent:0;text-align:center;">${roomApply.borrower.user.name}</label>）<#elseif roomApply.borrower.user.defaultCategory.id == 2><label style="${tdStyle}<#if (borrowerName?default('')?length <= 8)>;width:120px;text-indent:0;text-align:center;">${borrowerName}<#else>;padding:6px">　${borrowerName}　</#if></label>老师（工号<label style="${tdStyle};width:120px;text-indent:0;text-align:center;">${roomApply.borrower.user.name}</label>）<#else><label style="${tdStyle}<#if (borrowerName?default('')?length <= 8)>;width:120px;text-indent:0;text-align:center;">${borrowerName}<#else>;padding:6px">　${borrowerName}　</#if></label>用户（帐号<label style="${tdStyle};width:120px;text-indent:0;text-align:center;">${roomApply.borrower.user.name}</label>）</#if>，因<label style="${tdStyle};padding:6px">　${roomApply.activityName}　</label>之需，于<label style="${tdStyle};padding:6px">　${roomApply.applyTime.dateBegin?string("yyyy-MM-dd")}～${roomApply.applyTime.dateEnd?string("yyyy-MM-dd")}（${roomApply.applyTime.timeBegin}-${roomApply.applyTime.timeEnd}） </label>借用<label style="${tdStyle};padding:6px">　<#list roomApply.getClassrooms()?if_exists as classroom><@i18nName classroom?if_exists/><#if classroom_has_next>、</#if></#list>　</label>，并使用教学设备（用“√”，“×”表示）电脑<label style="${tdStyle};width:70px"></label>投影仪<label style="${tdStyle};width:70px"></label>扩音设备<label style="${tdStyle};width:70px"></label>。请管理人员届时开放教室，并于教室使用完毕后予以检查。</td>
                    </tr>
                </table>
                <table width="150px" align="right" style="font-size:14px;text-align:center;font-family:宋体,MiscFixed">
                    <tr>
                        <td height="50px" valign="bottom"></td>
                    </tr>
                    <tr>
                        <td height="20px" valign="top" style="text-align:left">签发人：</td>
                    </tr>
                    <tr>
                        <td height="50px" valign="top">${now?string("yyyy-MM-dd")}</td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <table style="font-size:14px;border:black 1px solid;text-align:justify;text-justify:inter-ideograph;text-indent:28px;font-family:宋体,MiscFixed;line-height:0.75cm" width="600px" align="center">
        <tr>
            <td>以下部分由物业管理人员填写</td>
        </tr>
        <tr>
            <td>教室借用情况（用“√”表示）</td>
        </tr>
        <tr>
            <td>卫生状况：好<label style="${tdStyle};width:70px"></label>中<label style="${tdStyle};width:70px"></label>差<label style="${tdStyle};width:70px"></label></td>
        </tr>
        <tr>
            <td>课桌复位：已复位<label style="${tdStyle};width:70px"></label>未复位<label style="${tdStyle};width:70px"></label></td>
        </tr>
        <tr>
            <td>教学设备归还：电脑<label style="${tdStyle};width:70px"></label>投影仪<label style="${tdStyle};width:70px"></label>扩音设备<label style="${tdStyle};width:70px"></label></td>
        </tr>
        <tr>
            <td height="30px"></td>
        </tr>
        <tr>
            <td style="text-indent:${14*20}px">紫泰物业保安签名：</td>
        </tr>
        <tr>
            <td style="text-indent:${14*25}px"><label style="width:50px"></label>年<label style="width:50px"></label>月<label style="width:50px"></label>日</td>
        </tr>
    </table>
    <table style="font-size:14px;font-family:宋体,MiscFixed;line-height:0.75cm" width="600px" align="center">
        <tr>
            <td height="80px">注：本回执由教学值班室汇总后交教务处存档</td>
        </tr>
    </table>
    <script><#assign pageTitle><font color="blue">（<@RoomApply.cycleValue count=roomApply.applyTime.cycleCount type=roomApply.applyTime.cycleType?string/>）</font></#assign>
        var bar =new ToolBar("myBar","教室借用凭证${pageTitle?js_string}",null,true,true);
        bar.addPrint("<@msg.message key="action.print"/>");
        bar.addBack("<@msg.message key="action.back"/>");
    </script>
</body>
<#include "/templates/foot.ftl"/>