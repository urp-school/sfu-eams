<#include "/templates/head.ftl"/>
<body>
    <table id="bar"></table>
    <div style="display:none" id="addConfigSetting">
        <table class="formTable" width="100%">
            <tr class="darkColumn">
                <td colspan="5" style="font-weight:bold;text-align:center">添加成绩记录方式的配置</td>
            </tr>
            <@table.thead>
                <@table.td text=""/>
                <@table.td text="显示名称"/>
                <@table.td text="最大值"/>
                <@table.td text="最小值"/>
                <@table.td text="默认值"/>
            </@>
        <form method="post" action="" name="addConfigForm" onsubmit="return false;">
            <tr>
                <td width="5%" align="center">+</td>
                <td><input type="text" name="configItem.grade" value="" maxlength="10" style="width:100px"/></td>
                <td><input type="text" name="configItem.maxScore" value="" maxlength="5" style="width:100px"/></td>
                <td><input type="text" name="configItem.minScore" value="" maxlength="5" style="width:100px"/></td>
                <td><input type="text" name="configItem.defaultScore" value="" maxlength="5" style="width:100px"/></td>
            </tr>
            <tr class="darkColumn">
                <td colspan="5" style="font-weight:bold;text-align:center">
                    <button onclick="saveAddSetting(this.form)">保存</button>
                    <button onclick="cancleSetting(this.form)">取消</button>
                </td>
            </tr>
            <input type="hidden" name="addConfig" value=""/>
        </form>
        </table>
        <hr>
    </div>
    <#assign configItems = defaultConfig.converters/>
    <@table.table  width="100%" id="configSetting">
        <tr class="darkColumn">
            <td colspan="5" style="font-weight:bold;text-align:center">修改已设置的成绩记录方式配置</td>
        </tr>
        <form method="post" action="" name="editConfigForm" onsubmit="return false;">
        <@table.thead>
            <@table.selectAllTd id="configItemId"/>
            <@table.td text="分数名称"/>
            <@table.td text="最大值"/>
            <@table.td text="最小值"/>
            <@table.td text="默认值"/>
        </@>
        <@table.tbody datas=configItems;configItem>
            <@table.selectTd id="configItemId" value=configItem.id/>
            <td><input type="text" name="scoreName${configItem.id}" value="${configItem.grade?html}" maxlength="10" style="width:100px"/></td>
            <td><input type="text" name="maxScore${configItem.id}" value="${configItem.maxScore}" maxlength="5" style="width:100px"/></td>
            <td><input type="text" name="minScore${configItem.id}" value="${configItem.minScore}" maxlength="5" style="width:100px"/></td>
            <td><input type="text" name="defaultScore${configItem.id}" value="${configItem.defaultScore}" maxlength="5" style="width:100px"/></td>
        </@>
        <tr class="darkColumn">
            <td colspan="5" style="text-align:center">
                <button onclick="saveSetting(this.form)">保存修改配置</button>
                <button onclick="resetConfigItem()">恢复修改配置</button>
            </td>
        </tr>
            <@searchParams/>
        </form>
    </@>
    <table height="200"><tr><td></td></tr></table>
    <script>
        var bar = new ToolBar("bar", "成绩记录方式设置", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("添加配置", "addSetting(document.addConfigForm)");
        bar.addItem("删除配置", "removeSetting(document.editConfigForm)", "delete.gif");
        bar.addItem("保存修改配置", "saveSetting(document.editConfigForm)");
        bar.addItem("<@msg.message key="action.goback"/>", "backward()", "backward.gif");
        
        var configItemList = {
        <#list configItems as configItem>
            ${"'" + configItem.grade + "'"}:{'max':${configItem.maxScore},'min':${configItem.minScore},'default':${configItem.defaultScore}}<#if configItem_has_next>,</#if>
        </#list>
        };
        var configItemNames = [<#list configItems as configItem>${"'" + configItem.grade + "'"}<#if configItem_has_next>, </#if></#list>];
        var configItemIds = [<#list configItems as configItem>${configItem.id}<#if configItem_has_next>, </#if></#list>];
        
        function addSetting(form) {
            if ($("addConfigSetting").style.display != "none" && !isBreakEditing(document.editConfigForm)) {
                return;
            }
            
            $("addConfigSetting").style.display = "block";
            form["configItem.grade"].focus();
        }
        
        function cancleSetting(form) {
            if ($("addConfigSetting").style.display == "none") {
                return true;
            }
            var x1 = form["configItem.grade"].value;
            var x2 = form["configItem.maxScore"].value;
            var x3 = form["configItem.minScore"].value;
            var x4 = form["configItem.defaultScore"].value;
            if ("" != x1 || "" != x2 || "" != x3 || "" != x4) {
                if (confirm("真的要放弃添加的分数配置吗？")) {
                    $("addConfigSetting").style.display = "none";
                    form["configItem.grade"].value = "";
                    form["configItem.maxScore"].value = "";
                    form["configItem.minScore"].value = "";
                    form["configItem.defaultScore"].value = "";
                    $("addConfigSetting").style.display = "none";
                    return true;
                }
            } else {
                $("addConfigSetting").style.display = "none";
            }
            return false;
        }
        
        function saveAddSetting(form) {
            if ($("addConfigSetting").style.display != "none" && isBreakEditing(document.editConfigForm)) {
                form["configItem.grade"].value = "";
                form["configItem.maxScore"].value = "";
                form["configItem.minScore"].value = "";
                form["configItem.defaultScore"].value = "";
                $("addConfigSetting").style.display = "none";
                return;
            }
            
            var gradeName = form["configItem.grade"].value;
            if ("" == gradeName.trim()) {
                alert("分数名称不能为空，或空字符（如空格）。");
                form["configItem.grade"].value = "";
                form["configItem.grade"].focus();
                return;
            }
            if (null != configItemList[gradeName]) {
                alert("分数名称已经存在，不能保存。");
                form["configItem.grade"].focus();
                return;
            }
            var maxScore = parseFloat(form["configItem.maxScore"].value);
            var minScore = parseFloat(form["configItem.minScore"].value);
            if (isNaN(minScore) || isNaN(maxScore)) {
                alert("请输入要新增分数的最大值和最小值。");
                form["configItem.maxScore"].focus();
                return;
            }
            if (!(/^[\+\-]?\d*\.?\d*$/.test(maxScore)) || !(/^[\+\-]?\d*\.?\d*$/.test(minScore))) {
                alert("输入的最大值和最小值不是数字。");
                form["configItem.maxScore"].focus();
                return;
            }
            if (minScore > maxScore) {
                alert("最小值不能超过最大值。");
                form["configItem.maxScore"].focus();
                return;
            }
            for (var i = 0; i < configItemNames.length; i++) {
                if (configItemList[configItemNames[i]]['min'] >= minScore && configItemList[configItemNames[i]]['min'] <= maxScore
                || configItemList[configItemNames[i]]['max'] >= minScore && configItemList[configItemNames[i]]['max'] <= maxScore) {
                    alert("分数范围" + minScore + "～" + maxScore + "区间与已设范围有冲突。");
                    form["configItem.maxScore"].focus();
                    return;
                }
            }
            var defaultScore = parseFloat(form["configItem.defaultScore"].value);
            if (isNaN(defaultScore)) {
                alert("默认分值不能为空值，或者不是数字。");
                form["configItem.maxScore"].focus();
                return;
            }
            if (!(defaultScore >= minScore && defaultScore <= maxScore)) {
                alert("默认分值不能超出已设定的" + minScore + "～" + maxScore + "区间范围。");
                form["configItem.defaultScore"].focus();
                return;
            }
            
            if (confirm("确定要保存所添加的分数设置吗？")) {
                form.action = "markStyleConfig.do?method=saveConfigSettng";
                addInput(form, "defaultConfigId", "${defaultConfig.id}", "hidden");
                addInput(form, "params", "defaultConfigId=${defaultConfig.id}", "hidden");
                form.submit();
            }
        }
        
        function removeSetting(form) {
            if (!cancleSetting(document.addConfigForm) && !isBreakEditing(form)) {
                return;
            }
            
            var configItemIds = getSelectIds("configItemId");
            if (null == configItemIds || "" == configItemIds) {
                alert("请选择要删除的配置项。");
                return;
            }
            
            if (confirm("确定要删除所选择的分数设置吗？")) {
                form.action = "markStyleConfig.do?method=removeConfigSettng";
                addInput(form, "configItemIds", configItemIds, "hidden");
                addInput(form, "params", "defaultConfigId=${defaultConfig.id}", "hidden");
                form.submit();
            }
        }
        
        function saveSetting(form) {
            if (!cancleSetting(document.addConfigForm)) {
                resetConfigItem();
                return;
            }
            
            var isModified = false;
            for (var i = 0; i < configItemIds.length; i++) {
                var toCompareValue1 = form["scoreName" + configItemIds[i]].value;
                var toCompareValue2 = parseFloat(form["maxScore" + configItemIds[i]].value);
                var toCompareValue3 = parseFloat(form["minScore" + configItemIds[i]].value);
                var toCompareValue4 = parseFloat(form["defaultScore" + configItemIds[i]].value);
                if ("" == toCompareValue1) {
                    alert("分数名称(第" + (i + 1) + "行)不能为空。");
                    return;
                }
                if (isNaN(toCompareValue2) || isNaN(toCompareValue3) || !(/^[\+\-]?\d*\.?\d*$/.test(form["maxScore" + configItemIds[i]].value)) || !(/^[\+\-]?\d*\.?\d*$/.test(form["minScore" + configItemIds[i]].value))) {
                    alert("第" + (i + 1) + "行分数范围输入了非法数字,或没有输入。");
                    return;
                }
                if (isNaN(toCompareValue4)|| !(/^[\+\-]?\d*\.?\d*$/.test(form["defaultScore" + configItemIds[i]].value))) {
                    alert("第" + (i + 1) + "行分数默认值输入了非法数字,或没有输入。");
                    return;
                }
                if (configItemNames[i] != form["scoreName" + configItemIds[i]].value
                    || configItemList[configItemNames[i]]['max'] != form["maxScore" + configItemIds[i]].value
                    || configItemList[configItemNames[i]]['min'] != form["minScore" + configItemIds[i]].value
                    || configItemList[configItemNames[i]]['default'] != form["defaultScore" + configItemIds[i]].value) {
                    isModified = true;
                }
                for (var j = 0; j < configItemIds.length; j++) {
                    if (j == i) {
                        continue;
                    }
                    var otherRowValue1 = form["scoreName" + configItemIds[j]].value;
                    var otherRowValue2 = parseFloat(form["maxScore" + configItemIds[j]].value);
                    var otherRowValue3 = parseFloat(form["minScore" + configItemIds[j]].value);
                    var otherRowValue4 = parseFloat(form["defaultScore" + configItemIds[j]].value);
                    if ("" == toCompareValue1) {
                        alert("分数名称(第" + (j + 1) + "行)不能为空。");
                        return;
                    }
                    if (otherRowValue1 == toCompareValue1) {
                        alert("分数名称" + toCompareValue1 + "于第" + (i + 1) + "行、第" + (j + 1) + "行重复。");
                        return;
                    }
                    if (isNaN(otherRowValue2) || isNaN(otherRowValue3) || !(/^[\+\-]?\d*\.?\d*$/.test(form["maxScore" + configItemIds[j]].value)) || !(/^[\+\-]?\d*\.?\d*$/.test(form["minScore" + configItemIds[j]].value))) {
                        alert("第" + (j + 1) + "行分数范围输入了非法数字,或没有输入。");
                        return;
                    }
                    if (otherRowValue3 >= otherRowValue2) {
                        alert("第" + (j + 1) + "行分数范围设定不正确！\n即，最小值（" + otherRowValue3 + "）超过了最大值（" + otherRowValue2 + "）。");
                        return;
                    }
                    if (toCompareValue2 >= otherRowValue3 && toCompareValue2 <= otherRowValue2 || toCompareValue3 >= otherRowValue3 && toCompareValue3 <= otherRowValue2) {
                        alert("分数范围" + toCompareValue3 + "～" + toCompareValue2 + "（第" + (i + 1) + "行）和" + otherRowValue3 + "～" + otherRowValue2 + "(第" + (j + 1) + "行)之间冲突。");
                        return;
                    }
                    if (isNaN(toCompareValue4) || !(/^[\+\-]?\d*\.?\d*$/.test(form["defaultScore" + configItemIds[j]].value))) {
                        alert("第" + (j + 1) + "行分数默认值输入了非法数字,或没有输入。");
                        return;
                    }
                    if (!(toCompareValue4 >= toCompareValue3 && toCompareValue4 <= toCompareValue2)) {
                        alert("第" + (i + 1) + "行输入的分数默认值" + toCompareValue4 + "，不在该行\n的" + toCompareValue3 + "～" + toCompareValue2 + "分数范围之内。");
                        return;
                    }
                }
            }
            if (isModified == false) {
                if (confirm("当前分数配置信息没有被修改，无需保存。\n\n现在若想返回列表，单击“确定”；\n若想继续修改分数配置，单击“取消”。")) {
                    backward();
                }
                return;
            }
            if (confirm("确定要保存所修改的分数配置吗？")) {
                form.action = "markStyleConfig.do?method=saveConfigSettng";
                addInput(form, "defaultConfigId", ${defaultConfig.id}, "hidden");
                addInput(form, "configItemIds", configItemIds, "hidden");
                form.submit();
            }
        }
        
        function isBreakEditing(form) {
            for (var i = 0; i < configItemNames.length; i ++) {
                if (configItemNames[i] != form["scoreName" + configItemIds[i]].value
                    || configItemList[configItemNames[i]]['max'] != form["maxScore" + configItemIds[i]].value
                    || configItemList[configItemNames[i]]['min'] != form["minScore" + configItemIds[i]].value
                    || configItemList[configItemNames[i]]['default'] != form["defaultScore" + configItemIds[i]].value) {
                    if (confirm("要放弃当前修改的分数配置吗？")) {
                        resetConfigItem();
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
        }
        
        function resetConfigItem() {
            document.editConfigForm.reset();
        }
        
        function backward() {
            parent.document.actionForm.submit();
        }
    </script>
</body>
<#include "/templates/foot.ftl"/>