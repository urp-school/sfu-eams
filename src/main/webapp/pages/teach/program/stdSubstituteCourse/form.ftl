<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script src='dwr/interface/courseDao.js'></script>
<script src='dwr/interface/studentService.js'></script>
<body   valign="top">
<table id="courseFormBar"></table>
    <table width="100%">
        <tr>
            <td>
                <table width="100%" valign="top" class="formTable">
                   <form name="stdSubstituteCourseForm" action="" method="post" onsubmit="return false;">
                    <input type="hidden" name="params" value="${RequestParameters['params']?if_exists}"/>
                   <tr class="darkColumn">
                     <td align="center" colspan="4">可代替课程基本信息</td>
                   </tr>
                   <tr>
                     <td class="title" id="f_stdCode">&nbsp;学生学号<font color="red">*</font>：</td>
                     <td id="f_planCourseId" colspan="3">
                      <input type="text" id="substitutionCourse.std.code" name="substitutionCourse.std.code" value="${(substitutionCourse.std.code)?if_exists}" size="20" onChange="checkCode()" maxlength="20"/>
                      <input type="hidden" id="substitutionCourse.std.id" name="substitutionCourse.std.id" value="${(substitutionCourse.std.id)?if_exists}"/>
                      <span id="substitutionCourse.std.name">${(substitutionCourse.std.name)?if_exists}</span>
                    </td>
                   </tr>
                    <tr>
                        <td class="title" width="15%">要替代的课程<font color="red">*</font>：</td>
                        <td>
                            <table width="100%">
                                <tr>
                                    <td rowspan="3" style="border-style:none">
                                        <#assign originCourse><#list (substitutionCourse.origins)?if_exists as origin>${origin.id}<#if origin_has_next>,</#if></#list></#assign>
                                        <input type="hidden" name="originIds" value="${originCourse?html}"/>
                                        <select id="originSeq" multiple name="originSeq" style="width:180px;" ondblclick="removeOrigin()" size="5">
                                            <#list (substitutionCourse.origins)?if_exists as origin>
                                            <option value="${origin.id}">（${origin.code}）${origin.name}</option>
                                            </#list>
                                        </select>
                                    </td>
                                    <td style="border-style:none"><button onclick="pointOrigin()">指定课程</button></td>
                                </tr>
                                <tr>
                                    <td style="border-style:none"><button onclick="removeOrigin()">移除指定</button></td>
                                </tr>
                                <tr>
                                    <td style="border-style:none"><button onclick="clearOrigin()">清空指定</button></td>
                                </tr>
                            </table>
                        </td>
                        <td class="title" width="15%">替代的课程<font color="red">*</font>：</td>
                        <td>
                            <table width="100%">
                                <tr>
                                    <td rowspan="3" style="border-style:none">
                                        <#assign substituteCourse><#list (substitutionCourse.substitutes)?if_exists as substitute>${substitute.id}<#if substitute_has_next>,</#if></#list></#assign>
                                        <input type="hidden" name="courseIds" value="${substituteCourse?html}"/>
                                        <select multiple name="substituteSeq" style="width:180px;" ondblclick="removeSubstitute()" size="5">
                                            <#list (substitutionCourse.substitutes)?if_exists as substitute>
                                            <option value="${substitute.id}">（${substitute.code}）${substitute.name}</option>
                                            </#list>
                                        </select>
                                    </td>
                                    <td style="border-style:none"><button onclick="pointSubstitute()">指定课程</button></td>
                                </tr>
                                <tr>
                                    <td style="border-style:none"><button onclick="removeSubstitute()">移除指定</button></td>
                                </tr>
                                <tr>
                                    <td style="border-style:none"><button onclick="clearSubstitute()">清空指定</button></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                   <tr>
                     <td class="title">&nbsp;<@bean.message key="attr.remark"/></td>
                     <td colspan="3">
                      <textarea name="substitutionCourse.remark" cols="25" rows="2">${(substitutionCourse.remark)?if_exists}</textarea>
                     </td>
                   </tr>
                   <tr class="darkColumn">
                     <td colspan="4" align="center">
                        <input type="hidden" name="substitutionCourse.id" value="${(substitutionCourse.id)?if_exists}">
                        <input type="button" value="<@bean.message key="action.submit"/>" onclick="majorSubmit()" id="stdSubmit" class="buttonStyle"/>
                     </td>
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
  var bar= new ToolBar("courseFormBar","学生替代课程维护",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addBack();
  
  function checkCode(){
      var valueCode = document.getElementById("substitutionCourse.std.code").value;
      document.getElementById("stdSubmit").disabled="";
      if(""==valueCode) {
        alert("请输入学号");
        document.getElementById("stdSubmit").disabled="true";
      }
      studentService.getStd(afterCheck,valueCode);
  }
  function afterCheck(data){
      if(data==null){
        alert("该学号学生不存在！");
        document.getElementById("substitutionCourse.std.code").value="${(substitutionCourse.std.code)?if_exists}";
        document.getElementById("substitutionCourse.std.name").innerHTML="${(substitutionCourse.std.name)?if_exists}";
        document.getElementById("substitutionCourse.std.id").value="${(substitutionCourse.std.id)?if_exists}";
        document.getElementById("substitutionCourse.std.code").focus();
        document.getElementById("substitutionCourse.std.code").select();
      }else{
        document.getElementById("substitutionCourse.std.name").innerHTML=data['name'];
        document.getElementById("substitutionCourse.std.id").value="" + data['id'];
      }
  }
  
    var form = document.stdSubstituteCourseForm;
    
    function isValidate() {
        var a_fields = {
            'substitutionCourse.std.code':{'l':'学号', 'r':true, 't':'f_stdCode'}
        };
        return new validator(form, a_fields, null).exec() && isNotEmpty(form["substitutionCourse.std.id"].value);
    }
    
    function pointOrigin() {
        if ($("substituteDiv").style.display == "block") {
            if (confirm("要放弃当前正在操作的“以替换的课表列表”吗？")) {
                $("substituteDiv").style.display = "none";
                $("substituteIframe").src = "";
                adaptFrameSize();
            } else {
                return;
            }
        }
        if (isValidate()) {
            form.action = "stdSubstituteCourse.do?method=selector";
            addInput(form, "isWithPlan", true, "hidden");
            form.target = "originIframe";
            form.submit();
            $("originDiv").style.display = "block";
        } else {
            alert("当前学号输入无效，请清空再次输入试试。");
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
        form.action = "stdSubstituteCourse.do?method=selector";
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
    
    function majorSubmit(){
        if (isValidate()) {
            if (isEmpty(form["originIds"].value) || isEmpty(form["courseIds"].value)) {
                alert("替代课程设置无效。");
                return;
            }
            form.action = "stdSubstituteCourse.do?method=save";
            form.target = "_self";
            
            form.submit();
        }
    }
    
    adaptFrameSize();
</script>
</body>