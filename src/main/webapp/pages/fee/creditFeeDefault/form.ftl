<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table id="bar"></table>
    <table class="formTable" width="100%" align="100%">
        <tr class="darkColumn" style="text-align:center; font-weight:bold">
            <td colspan="2">学分收费标准设置</td>
        </tr>
        <form method="post" action="creditFeeDefault.do?method=save" name="actionForm" onsubmit="return false;">
        	<@searchParams/>
            <tr>
                <td class="title"><@msg.message key="entity.studentType"/>:</td>
                <td><@htm.i18nSelect datas=stdTypeList selected=(creditFeeDefault.stdType.id?string)?default("") name="creditFeeDefault.stdType.id"/></td>
            </tr>
	        <tr>
	            <td class="title"><@msg.message key="entity.courseType"/>:</td>
	            <td>
                    <@htm.i18nSelect datas=courseTypes selected=(creditFeeDefault.courseType.id?string)?default("0") name="creditFeeDefault.courseType.id">
                        <option value="0">...</option>
                    </@>
	            </td>
	        </tr>
            <tr>
                <td class="title" id="f_value" align="right"><font color="red">*</font>&nbsp;学费（元/分）:</td>
                <td><input type="text" id="creditFeeDefault.value" maxlength="10" name="creditFeeDefault.value" value="${(creditFeeDefault.value?string("0.00"))?default(RequestParameters["creditFeeDefault.value"]?default('0.00'))}"/></td>
            </tr>
        </form>
        <tr class="darkColumn">
            <td colspan="2" align="center"><button accesskey="S" onclick="save()"><@msg.message key="action.save"/>(<u>S</u>)</button>　<button accesskey="R" onclick="docuemnt.actionForm.reset()"><@msg.message key="action.reset"/>(<u>R</u>)</button></td>
        </tr>
    </table>
    <script>
        var bar = new ToolBar("bar", "学分收费标准", null, true, true);
        bar.setMessage('<@getMessage/>');
        bar.addBack("<@msg.message key="action.back"/>");
        
        var form = document.actionForm;
        
        function save() {
            var a_fields = {
                'creditFeeDefault.value':{'l':'学费', 'r':true, 't':'f_value', 'f':'unsignedReal'}
            };
            var v = new validator(form, a_fields, null);
            if (v.exec()) {
                format();
                addInput(form, "creditFeeDefault.id", "${(creditFeeDefault.id)?default('')}", "hidden");
                form.submit();
            }
        }
        
        function format() {
            var point = 0;
            var decimal = 0;
            var str = document.getElementById('creditFeeDefault.value').value;
            
            for (var i = 1; i < str.length; i++) {
                if (str.substring(i - 1, i) == ".")
                    point = i;
            }
            decimal = str.length - point;
            if (point == 0) {
                document.getElementById('creditFeeDefault.value').value = document.getElementById('creditFeeDefault.value').value + ".00";
            } else {
                if (decimal == 0) {
                    document.getElementById('creditFeeDefault.value').value = document.getElementById('creditFeeDefault.value').value + "00";
                } else if (decimal == 1) {
                    document.getElementById('creditFeeDefault.value').value = document.getElementById('creditFeeDefault.value').value + "0";
                }
            }
        }
    </script>
</BODY>
<#include "/templates/foot.ftl"/>