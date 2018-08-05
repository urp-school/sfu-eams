<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
    <table id="bar"></table>
    <@table.table align="center" width="80%">
        <@table.thead>
            <@table.td name="attr.stdNo"/>
            <@table.td name="attr.personName"/>
            <@table.td name="common.studentState"/>
            <@table.td name="std.degreeInfo.graduateOn"/>
            <@table.td name="entity.isStudentStatusAvailable"/>
            <td>是否在校</td>
        </@>
        <@table.tbody datas=students;std>
            <td>${std.code}</td>
            <td>${std.name}</td>
            <td><@i18nName std["studentState"]?if_exists/></td>
            <td>${(std["studentGradudateOns"]?string("yyyy-MM-dd"))?default('')}</td>
            <td>${(std["active"]?string('有效','无效'))?default('')}</td>
            <td>${(std["inSchool"]?string('在校','不在校'))?default('')}</td>
        </@>
    </@>
    <table width="80%" align="center" class="listTable">
        <form name="commonForm" action="" method="post" onsubmit="return false;">
            <@searchParams/>
            <tr>
	           <td class="title" id="f_infoStatus">
	               &nbsp;<input type="checkbox" id="infoStatusCheck" onclick="checkStatus('infoStatusCheck', 'studentStateId');"/>&nbsp;<@bean.message key="info.studentRecordManager.assignStudentStatus"/>：
	           </td>
	           <td><@htm.i18nSelect datas=statusList selected="" name="studentStateId" id="studentStateId"/></td>
            </tr>
            <tr>
                <td class="title" id="f_status">
	      	        &nbsp;<input type="checkbox" id="activeCheck" onclick="checkStatus('activeCheck', 'isActive');"/>&nbsp;指定学籍有效性：
                </td>
                <td>
                    <select id="isActive" name="isActive" style="width:100px;">
                        <option value="0">无效</option>
                        <option value="1">有效</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="title" id="f_status">
	      	        &nbsp;<input type="checkbox" id="inSchoolCheck" onclick="checkStatus('inSchoolCheck', 'isInSchool');"/>&nbsp;指定是否在校：
                </td>
                <td>
                    <select id="isInSchool" name="isInSchool" style="width:100px;">
                        <option value="0">不在校</option>
                        <option value="1">在校</option>
                    </select>
                </td>
            </tr>
            <tr class="darkColumn">
                <td colspan="2" align="center" >
                    <input type="hidden" name="stdIds" value="${RequestParameters["stdIds"]}"/>
                    <button accesskey="S" onclick="doAction()" id="saveButton"><@msg.message key="system.button.submit"/>(<u>S</u>)</button>　<button onclick="doReset()"><@msg.message key="system.button.reset"/>(<u>R</u>)</button>
                </td>
            </tr>   
        </form>
    </table>
	<script language="javascript" >
	    var bar = new ToolBar("bar", "学籍状态设置", null, true, true);
	    bar.addBack("<@msg.message key="action.back"/>");
        //saveButton.enable=false;
	    
	    var form = document.commonForm;
	  	function checkStatus(checkName, selectName){
	  		if($(checkName).checked){
	  			$(selectName).disabled=false;
	  			$(checkName).value = $(checkName).id;
	  		}else{
	  			$(selectName).disabled=true;
	  			$(checkName).value = "";
	  		}
	  	}
	  	checkStatus("infoStatusCheck", "studentStateId");
	  	checkStatus("activeCheck", "isActive");
	  	checkStatus("inSchoolCheck", "isInSchool");
	  	
	    function doAction(){
            var selected = getSelectId("infoStatusCheck") + getSelectId("activeCheck") + getSelectId("inSchoolCheck");
            if (selected == null || selected == "") {
                alert("请至少选择一项后再提交！");
                return;
            }
            if (confirm("是否要设置学籍的状态？")) {
		        form.action = "studentStatus.do?method=updateStatus";
		        form.submit();
            }
        }
	   
	    function doReset(){
			form.reset();
			checkStatus("infoStatusCheck", "studentStateId");
		  	checkStatus("activeCheck", "isActive");
		  	checkStatus("inSchoolCheck", "isInSchool");
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>