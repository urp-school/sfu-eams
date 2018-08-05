<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body onload="DWRUtil.useLoadingMessage();">
    <table id="teachPlanBar"></table>
    <table class="frameTable" width="100%">
        <tr>
            <#assign withAuthority=1>
            <td width="20%" class="frameTable_view"><#include "searchForm.ftl"/></td>
            <td valign="top">
                <iframe src="#" id="planListFrame" name="planListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%"></iframe>
            </td>
        </tr>
    </table>
    <script>
        var bar=new ToolBar("teachPlanBar","培养计划开课情况统计",null,true,true);
        bar.setMessage('<@getMessage/>');
        bar.addBlankItem();
    
        multi=false;
        withAuthority=true;
        
        search();
        
        function search() {
            form.action = "teachPlanActivityStat.do?method=search";
            form.target = "planListFrame";
            if(multi){
                 form["multi"].value = "1";
            } else {
                 form["multi"].value = "0";
            }
            var params =getInputParams(form,"teachPlan");
            if(form["type"].checked){
                params += "&type=" + form["type"].value;
            }
            form["params"].value = params;
            form.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>