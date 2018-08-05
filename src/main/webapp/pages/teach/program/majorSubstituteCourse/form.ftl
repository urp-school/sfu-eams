<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
    <table id="bar"></table>
    <#assign pageTitle>${(majorCourse.id)?exists?string("修改", "制定")}替代课程</#assign>
    <table width="100%">
        <tr>
            <td>
                <table class="formTable" width="100%">
                    <form method="post" action="" name="actionForm" onsubmit="return false;">
                        <input type="hidden" name="majorCourse.id" value="${(majorCourse.id)?if_exists}"/>
                        <input type="hidden" name="params" value="${RequestParameters['params']?if_exists}"/>
                    <tr>
                        <td class="darkColumn" style="font-weight:bold;text-align:center" colspan="4">${pageTitle}</td>
                    </tr>
                    <tr>
                        <td class="title" width="15%" id="f_enrollTurn"><font color="red">*</font>所在年级：</td>
                        <td width="35%"><input type="text" name="majorCourse.enrollTurn" value="${(majorCourse.enrollTurn)?if_exists}" style="width:150px" maxlength="7"/></td>
                        <td class="title" width="15%"><font color="red">*</font>学生类别：</td>
                        <td width="35%">
                            <select id="stdTypeOfSpeciality" name="majorCourse.stdType.id" style="width:150px;">
                                <option value="${(majorCourse.stdType.id)?if_exists}"></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">院系：</td>
                        <td>
                            <select id="department" name="majorCourse.department.id" style="width:150px;">
                                <option value="${(majorCourse.department.id)?if_exists}"></option>
                            </select>
                        </td>
                        <td class="title">专业：</td>
                        <td>
                            <select id="speciality" name="majorCourse.major.id" style="width:150px;">
                                <option value="${(majorCourse.major.id)?if_exists}"></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">专业方向：</td>
                        <td>
                            <select id="specialityAspect" name="majorCourse.majorField.id" style="width:150px;">
                                <option value="${(majorCourse.majorField.id)?if_exists}"></option>
                            </select>
                        </td>
                        <td class="title"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="title">要替代的课程：</td>
                        <td>
                            <table width="100%">
                                <tr>
                                    <td rowspan="3" style="border-style:none">
                                        <input type="hidden" name="originIds" value="<#list (majorCourse.origins)?if_exists as origin>${origin.id}<#if origin_has_next>,</#if></#list>"/>
                                        <select multiple name="originSeq" style="width:250px;" ondblclick="removeOrigin()" size="5">
                                            <#list (majorCourse.origins)?if_exists as origin>
                                            <option value="${origin.id}">（${origin.code}）${origin.name}</option>
                                            </#list>
                                        </select>
                                    </td>
                                    <td style="border-style:none"><button onclick="pointOrigin()" style="width:50px">指定</button></td>
                                </tr>
                                <tr>
                                    <td style="border-style:none"><button onclick="removeOrigin()" style="width:50px">移除</button></td>
                                </tr>
                                <tr>
                                    <td style="border-style:none"><button onclick="clearOrigin()" style="width:50px">清空</button></td>
                                </tr>
                            </table>
                        </td>
                        <td class="title">替代的课程：</td>
                        <td>
                            <table width="100%">
                                <tr>
                                    <td rowspan="3" style="border-style:none">
                                        <input type="hidden" name="courseIds" value="<#list (majorCourse.substitutes)?if_exists as substitute>${substitute.id}<#if substitute_has_next>,</#if></#list>"/>
                                        <select multiple name="substituteSeq" style="width:250px;" ondblclick="removeSubstitute()" size="5">
                                            <#list (majorCourse.substitutes)?if_exists as substitute>
                                            <option value="${substitute.id}">（${substitute.code}）${substitute.name}</option>
                                            </#list>
                                        </select>
                                    </td>
                                    <td style="border-style:none"><button onclick="pointSubstitute()" style="width:50px">指定</button></td>
                                </tr>
                                <tr>
                                    <td style="border-style:none"><button onclick="removeSubstitute()" style="width:50px">移除</button></td>
                                </tr>
                                <tr>
                                    <td style="border-style:none"><button onclick="clearSubstitute()" style="width:50px">清空</button></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">备注：</td>
                        <td colspan="3"><textarea name="majorCourse.remark" cols="50" rows="2">${(majorCourse.remark)?if_exists}</textarea></td>
                    </tr>
                    <tr>
                        <td class="darkColumn" style="text-align:center" colspan="4"><button onclick="save()">保存</button></td>
                    </tr>
                    </form>
                </table>
            </td>
        </tr>
        <tr>
            <td>
                <div id="originDiv" style="width:100%;text-align:center;display:none">
                    <iframe src="" id="originIframe" name="originIframe" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
                </div>
                <div id="substituteDiv" style="width:100%;text-align:center;display:none">
                    <iframe src="" id="substituteIframe" name="substituteIframe" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
                </div>
            </td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "${pageTitle}", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
        
        var form = document.actionForm;
        
        function isValidate() {
            var a_fields = {
                'majorCourse.enrollTurn':{'l':'<@bean.message key="attr.enrollTurn"/>', 'r':true, 't':'f_enrollTurn', 'f':'yearMonth'}
            };
            return new validator(form, a_fields, null).exec();
        }
        
        function pointOrigin() {
            if ($("substituteDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“以替换的课表列表”吗？")) {
                    $("substituteDiv").style.display = "none";
                    $("substituteIframe").src = "";
                    adaptFrameSize();
                }
            }
            if (isValidate()) {
                form.action = "majorSubstituteCourse.do?method=selector";
                addInput(form, "isWithPlan", true, "hidden");
                addInput(form, "plan.enrollTurn", form["majorCourse.enrollTurn"].value, "hidden");
                addInput(form, "plan.stdType.id", form["majorCourse.stdType.id"].value, "hidden");
                var departmentId = form["majorCourse.department.id"].value;
                if (null != departmentId) {
                    addInput(form, "plan.department.id", departmentId, "hidden");
                }
                var majorId = form["majorCourse.major.id"].value;
                if (null != majorId) {
                    addInput(form, "plan.speciality.id", majorId, "hidden");
                }
                var majorFieldId = form["majorCourse.majorField.id"].value;
                if (null != majorFieldId) {
                    addInput(form, "plan.aspect.id", majorFieldId, "hidden");
                }
                form.target = "originIframe";
                form.submit();
                $("originDiv").style.display = "block";
            }
        }
        
        function removeOrigin() {
            if ($("substituteDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“以替换的课表列表”吗？")) {
                    $("substituteDiv").style.display = "none";
                    $("substituteIframe").src = "";
                    adaptFrameSize();
                } else {
                    return;
                }
            }
            if ($("originDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“要替换的课表列表”吗？")) {
                    $("originDiv").style.display = "none";
                    $("originIframe").src = "";
                    adaptFrameSize();
                } else {
                    return;
                }
            }
            var originSeqValue = getAllOptionValue(form["originSeq"]);
            if (isEmpty(originSeqValue)) {
                alert("请选择要移除的课。");
                return;
            }
            form["originIds"].value = "";
            for (var i = 0; i < form["originSeq"].options.length;) {
                if (form["originSeq"].options[i].selected) {
                    try {
                        form["originSeq"].options.remove(i);
                    } catch (e) {
                        form["originSeq"].remove(i);
                    }
                } else {
                    i++;
                }
            }
            form["originIds"].value += getAllOptionValue(form["originSeq"]);
        }
        
        function clearOrigin() {
            if ($("substituteDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“以替换的课表列表”吗？")) {
                    $("substituteDiv").style.display = "none";
                    $("substituteIframe").src = "";
                    adaptFrameSize();
                } else {
                    return;
                }
            }
            if ($("originDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“要替换的课表列表”吗？")) {
                    $("originDiv").style.display = "none";
                    $("originIframe").src = "";
                    adaptFrameSize();
                } else {
                    return;
                }
            }
            while ( form["originSeq"].options.length > 0) {
                 form["originSeq"].options[0] = null;
            }
            form["originIds"].value = "";
        }
        
        function pointSubstitute() {
            if ($("originDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“要替换的课表列表”吗？")) {
                    $("originDiv").style.display = "none";
                    $("originIframe").src = "";
                    adaptFrameSize();
                } else {
                    return;
                }
            }
            form.action = "majorSubstituteCourse.do?method=selector";
            addInput(form, "isWithPlan", false, "hidden");
            form.target = "substituteIframe";
            form.submit();
            $("substituteDiv").style.display = "block";
        }
        
        function removeSubstitute() {
            if ($("substituteDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“以替换的课表列表”吗？")) {
                    $("substituteDiv").style.display = "none";
                    $("substituteIframe").src = "";
                    adaptFrameSize();
                } else {
                    return;
                }
            }
            if ($("originDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“要替换的课表列表”吗？")) {
                    $("originDiv").style.display = "none";
                    $("originIframe").src = "";
                    adaptFrameSize();
                } else {
                    return;
                }
            }
            var courseSeqValue = getAllOptionValue(form["substituteSeq"]);
            if (isEmpty(courseSeqValue)) {
                alert("请选择要移除的课。");
                return;
            }
            form["courseIds"].value = "";
            for (var i = 0; i < form["substituteSeq"].options.length;) {
                if (form["substituteSeq"].options[i].selected) {
                    try {
                        form["substituteSeq"].options.remove(i);
                    } catch (e) {
                        form["substituteSeq"].remove(i);
                    }
                } else {
                    i++;
                }
            }
            form["courseIds"].value += getAllOptionValue(form["substituteSeq"]);
        }
        
        function clearSubstitute() {
            if ($("originDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“要替换的课表列表”吗？")) {
                    $("originDiv").style.display = "none";
                    $("originIframe").src = "";
                    adaptFrameSize();
                } else {
                    return;
                }
            }
            if ($("substituteDiv").style.display == "block") {
                if (confirm("要放弃当前正在操作的“以替换的课表列表”吗？")) {
                    $("substituteDiv").style.display = "none";
                    $("substituteIframe").src = "";
                    adaptFrameSize();
                } else {
                    return;
                }
            }
            while ( form["substituteSeq"].options.length > 0) {
                 form["substituteSeq"].options[0] = null;
            }
            form["courseIds"].value = "";
        }
        
        function save() {
            if (isValidate()) {
                if (isEmpty(form["originIds"].value) || isEmpty(form["courseIds"].value)) {
                    alert("替代课程设置无效。");
                    return;
                }
                form.action = "majorSubstituteCourse.do?method=save";
                form.target = "_self";
                
                form.submit();
            }
        }
        
        adaptFrameSize();
        inputElementWidth = "150px";
    </script>
    <#include "/templates/stdTypeDepart3Select.ftl"/>
</body>
<#include "/templates/foot.ftl"/>
