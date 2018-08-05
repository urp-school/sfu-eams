<#include "/templates/head.ftl"/>
<body>
    <table id="bar" width="100%"></table>
    <#include "roomApplyList.ftl"/>
    <@htm.actionForm name="actionForm" action="ecuplRoomApplyApprove.do" entity="roomApply" onsubmit="return false;"/>
    <script>
        var bar = new ToolBar("bar","教室审核结果",null,true,true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("<@msg.message key="action.info"/>", "info()");
        bar.addItem("<@msg.message key="action.delete"/>", "form.target='_self';remove()");
        <#--
        <#if (RequestParameters['lookContent'])?exists>
            <#if RequestParameters['lookContent'] == '1'>
                bar.addItem("审核分配", "form.target='';singleAction('applyRoomSetting')", "update.gif");
                bar.addItem("<@msg.message key="action.edit"/>", "form.target='';singleAction('editApply')");
            <#elseif RequestParameters['lookContent'] == '2'>
                bar.addItem("<@msg.message key="action.edit"/>", "form.target='';singleAction('editApply')");
                bar.addItem("<@msg.message key="action.edit"/>分配", "form.target='';singleAction('applyRoomSetting')", "update.gif");
                bar.addItem("<@msg.message key="action.edit"/>费用", "form.target='';singleAction('adjustFeeForm')", "update.gif");
                bar.addItem("取消分配", "cancelApply()", "update.gif");
            </#if>
        </#if>
        -->
        bar.addItem("<@msg.message key="action.export"/>", "exportData()");

        function printApply(selectStyle) {
            window.open("ecuplRoomApplyApprove.do?method=print&selectStyle=" + selectStyle);
        }
        function cancelApply(){
            var roomId = getSelectId("roomApplyId");
            if (null == roomId || "" == roomId) {
                alert("你没有选择要操作的记录！");
                return;
            }
            if(confirm("确定要取消该申请已分配的教室吗？")){
                addInput(form, "remark", "");
                singleAction('cancel');
            }
        }
        <#include "exportDatasJS.ftl"/>
    </script>
</body>
<#include "/templates/foot.ftl"/>