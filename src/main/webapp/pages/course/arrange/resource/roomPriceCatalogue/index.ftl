<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
    <table id="bar"></table>
    <table width="100%" class="frameTable">
        <tr valign="top">
            <td width="20%" class="frameTable_view"><#include "schoolDistrictMenu.ftl"/></td>
            <td><iframe name="displayFrame" src="#" marginwidth="0" marginheight="0" width="100%" frameborder="0" scrolling="no"></iframe></td>
        </tr>
    </table>
    <form name="actionForm" method="post" action="" target="displayFrame" onsubmit="return false;"></form>
    <script>
        var form = document.actionForm;
        
        var bar = new ToolBar("bar", "教室借用价目表", null, true, true);
        bar.setMessage('<@getMessage/>');
        <#if roomPrices?size != 0>
        bar.addItem("<@msg.message key="action.add"/>", "add()");
        bar.addItem("<@msg.message key="action.edit"/>", "edit()");
        bar.addItem("<@msg.message key="action.info"/>", "info()", "detail.gif");
        bar.addItem("<@msg.message key="action.delete"/>", "remove()");
        <#else>
        bar.addItem("设置默认价目表", "defaultSetting()");
        
        form.action = "roomPriceCatalogue.do?method=edit";
        form.submit();
        </#if>
        
        var roomPriceCatalogueId = null;
        function add() {
            form.action = "roomPriceCatalogue.do?method=edit";
            form.target = "displayFrame";
            form.submit();
        }
        
        function edit() {
            addInput(form, "roomPriceCatalogueId", roomPriceCatalogueId, "hidden");
            add();
        }
        
        function remove() {
            if (confirm("确定要删除所选校区或默认配置吗？")) {
	            form.action = "roomPriceCatalogue.do?method=remove";
	            addInput(form, "roomPriceCatalogueId", roomPriceCatalogueId, "hidden");
	            form.target = "_self";
	            form.submit();
            }
        }
        
        function info() {
            form.action = "roomPriceCatalogue.do?method=info";
            form.target = "displayFrame";
            addInput(form, "roomPriceCatalogueId", roomPriceCatalogueId, "hidden");
            form.submit();
        }
        
        <#if roomPrices?size != 0>
        document.getElementById("item${roomPrices?first.id}").onclick(); 
        function displayPrices(td, roomPriceId) {
            clearSelected(menuTable,td);
            setSelectedRow(menuTable,td);
            
            roomPriceCatalogueId = roomPriceId;
            form.action = "roomPriceCatalogue.do?method=search";
            addInput(form, "roomPriceId", roomPriceCatalogueId, "hidden");
            form.target = "displayFrame";
            form.submit();
        }
        <#else>
        function defaultSetting() {
            addInput(form, "defaultSetting", true, "hidden");
            add();
        }
        defaultSetting();
        </#if>
    </script>
</body>
<#include "/templates/foot.ftl"/>