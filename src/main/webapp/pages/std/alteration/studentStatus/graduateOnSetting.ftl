<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
    <table id="bar"></table>
    <@table.table align="center" width="80%">
        <@table.thead>
            <@table.td name="attr.stdNo"/>
            <@table.td name="attr.personName"/>
            <@table.td name="std.degreeInfo.graduateOn"/>
            <@table.td name="entity.isStudentStatusAvailable"/>
        </@>
        <@table.tbody datas=students;std>
            <td>${std.code}</td>
            <td><@i18nName std/></td>
            <td>${(std.graduateOn?string("yyyy-MM-dd"))?default("")}</td>
            <td>${(std.active?string('有效','无效'))?default('')}</td>
        </@>
    </@>
    <table width="80%" align="center" class="listTable">
        <form name="commonForm" action="" method="post" onsubmit="return false;">
            <@searchParams/>
            <tr>
	           <td class="title" id="f_infoStatus">
	               &nbsp;<input type="checkbox" id="infoGraduateOnCheck" onclick="checkGraduateOn('infoGraduateOnCheck', 'studentGraduateOn');"/>&nbsp;<@bean.message key="std.degreeInfo.graduateOn"/>：
	           </td>
	           <td><input type="text" name="studentGraduateOn" onfocus="calendar()" value="请选择毕业时间"/></td>
            </tr>
            <tr class="darkColumn">
                <td colspan="2" align="center" >
                    <input type="hidden" name="stdIds" value="${RequestParameters["stdIds"]}"/>
                    <button accesskey="S" onclick="doAction()" id="saveButton"><@msg.message key="system.button.submit"/>(<u>S</u>)</button>　<button onclick="doReset()"><@msg.message key="system.button.reset"/>(<u>R</u>)</button>
                </td>
            </tr>
        </form>
    </table>
    <#list 1..15 as i><br></#list>
	<script language="javascript" >
	    var bar = new ToolBar("bar", "毕业时间设置", null, true, true);
	    bar.addBack("<@msg.message key="action.back"/>");
	    
	    var form = document.commonForm;
	  	function checkGraduateOn(checkName, selectName){
	  		if($(checkName).checked){
	  			$(selectName).disabled=false;
	  			$(checkName).value = $(checkName).id;
	  			form["studentGraduateOn"].value = "";
	  			form["studentGraduateOn"].focus();
	  		}else{
	  			$(selectName).disabled=true;
	  			$(checkName).value = "";
	  			form["studentGraduateOn"].value = "请选择毕业时间";
	  		}
	  	}
	  	checkGraduateOn("infoGraduateOnCheck", "studentGraduateOn");
	  	
	    function doAction(){
            var selected = getSelectId("infoGraduateOnCheck");
            if (selected == null || selected == "") {
                alert("请至少选择一项后再提交！");
                return;
            } else if (form["studentGraduateOn"].value == null || form["studentGraduateOn"].value == "") {
            	alert("修改项没有填写。");
            	return;
            }
            
            if (confirm("是否要设置学生的毕业时间？")) {
	            form.action = "studentStatus.do?method=updateGraduateOn";
	            form.submit();
            }
        }
	   
	    function doReset(){
			form.reset();
			checkStatus("infoGraduateOnCheck", "studentGraduateOn");
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>