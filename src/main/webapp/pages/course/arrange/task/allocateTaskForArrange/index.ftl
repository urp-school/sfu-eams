<#include "/templates/head.ftl"/>
<link href="${static_base}/css/tabFrame.css" rel="stylesheet" type="text/css"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/TabPane.js"></script> 
<body>
    <table id="bar"></table>
    <table class="frameTable_title" width="100%">
        <tr>
            <td style="width:50px"><font color="blue"><@bean.message key="action.advancedQuery"/></font></td>
            <td>|</td>
          <form name="actionForm" method="post" action="allocateTaskForArrange.do?method=index" onsubmit="return false;">
            <input type="hidden" name="calendarId" value="${calendar.id}"/>
            <input type="hidden" name="params" value="&primaryKey=&teachCalendarId=null&departmentId=null"/>
            <#include "/pages/course/calendar.ftl"/>
        </tr>
    </table>
    <table class="frameTable" width="100%" height="85%">
        <tr valign="top">
            <td class="frameTable_view" width="20%">
                <div class="dynamic-tab-pane-control tab-pane" id="tabPane">
                    <script type="text/javascript">tp1 = new WebFXTabPane(document.getElementById( "tabPane") ,false);</script>
                    <div style="display:block" class="tab-page" id="tabPage1">
                        <h2 class="tab"><span style="font-size:12px">院系分配</span></h2>
                        <script type="text/javascript">tp1.addTabPage(document.getElementById( "tabPage1"));</script>
                        <#include "departmentList.ftl"/>
                    </div>
                    <div style="display:block" class="tab-page" id="tabPage2">
                        <h2 class="tab"><span style="font-size:12px">查询</span></h2>
                        <script type="text/javascript">tp1.addTabPage(document.getElementById( "tabPage2"));</script>
                        <#assign extraSearchTR>
                            <tr>
                                <td style="color:blue">归属情况:</td>
                                <td><select name="isTaskIn" value="${RequestParameters["isTaskIn"]?if_exists}" style="width:100px">
                                   <option value=""><@bean.message key="common.all"/></option>
                                   <option value="0" selected>未归属</option>
                                   <option value="1">已归属</option>
                                </select></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td style="color:blue">“归属情况”仅供“全部”时查询。</td>
                            </tr>
                        </#assign>
                        <#include "/pages/course/taskBasicSearchForm.ftl"/>
                    </div>
                </div>
            </td>
            </form>
            <td><iframe src="#" id="pageIframe" name="pageIframe" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" style="height:100%" width="100%"></iframe></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "教学任务排课归属院系分配", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("自动分配", "autoInner()", "new.gif", "自动分配开课院系与上课院系相同的任务。");
        
        var form = document.actionForm;
        
        var xTdObj = null;
        var xTableObj = null;
        function searchAll(tableObj, tdObj) {
            beenSelected(tableObj, tdObj);
            $("default").style.color = "green";
            form.action = "allocateTaskForArrange.do?method=searchAll";
            form.target = "pageIframe";
            form.submit();
        }
        
        <#--查看还未归属的任务-->
        function search(tableObj, tdObj) {
            beenSelected(tableObj, tdObj);
            $("all").style.color = "blue";
            form.action = "allocateTaskForArrange.do?method=search";
            form.target = "pageIframe";
            form.submit();
        }
        
        function searchTask() {
            if (xTdObj.id == "all") {
                searchAll(xTableObj, xTdObj);
            } else if (xTdObj.id == "default") {
                search(xTableObj, xTdObj);
            } else {
                info(xTableObj, xTdObj);
            }
        }
        
        function info(tableObj, tdObj) {
            beenSelected(tableObj, tdObj);
            $("all").style.color = "blue";
            $("default").style.color = "green";
            form.action = "allocateTaskForArrange.do?method=info";
            form["primaryKey"].value = xTdObj.id;
            form.target = "pageIframe";
            form.submit();
        }
        
        function beenSelected(tableObj, tdObj) {
            this.xTableObj = tableObj;
            this.xTdObj = tdObj;
            selectedFontBackgroundColor = 'white';
            setSelectedRow(this.xTableObj, this.xTdObj);
        }
        
        <#assign keys = RequestParameters["primaryKey"]?default("default")?split("_")/>
        doClick("<#if keys[0] == calendar.id?string>${RequestParameters["primaryKey"]?default("default")}<#else>default</#if>");
        
        function autoInner() {
            if (confirm("自动分配仅对未分配归属任务的进行补充分配，\n不将消除已经的分配数据。若要取消，可在各自\n归属院系中进行。\n\n要继续吗？")) {
                form.action = "allocateTaskForArrange.do?method=autoInner";
                form.target = "_self";
                form.submit();
            }
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>