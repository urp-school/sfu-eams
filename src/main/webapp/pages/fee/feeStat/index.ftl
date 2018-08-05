<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
 	<table id="bar"></table>
 	<table class="frameTable_title">
 		<form method="post" action="" name="feeStatForm" target="displayFrame" onsubmit="return false;">
 		<tr>
 			<td style="font-size: 10pt;" align="left">统计项目</td>
 				<#include "/pages/course/calendar.ftl"/>
 				<#--
 				<input type="hidden" name="calendar.year" value="${calendar.year}"/>
 				<input type="hidden" name="calendar.term" value="${calendar.term}"/>
 				-->
 		</tr>
 	</table>
 	<#include "/pages/components/initAspectSelectData.ftl"/>
 	<table class="frameTable">
 		<tr valign="top" height="90">
 			<td class="frameTable_view" width="24%">
 				<#include "menu.ftl"/>
	 		</td>
		</form>
 			<td rowspan="3">
 				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
 			</td>
	 	</tr>
 	</table>
	<script>
		<#--页面工具栏定义-->
	    var bar=new ToolBar("bar", "缴费统计", null, true, true);
	    <#--定义一个动态“搜索”查询功能点-->
        bar.addItem("<@msg.message key="action.help"/>", "javascript:alert('正在建设中...')", "help.png");
        
		<#--当第一次进入时默认选择第一项菜单，以后就选择上次选择的菜单项-->
		var selectDo = "${RequestParameters['indexPage']?default("statFeeType")}";

		<#--三联动-->
	    var sdsFee = new StdTypeDepart3Select("fee_std_stdTypeOfSpeciality", "fee_std_department", "fee_std_speciality", "fee_std_specialityAspect", true, true, true, true);    
	    sdsFee.init(stdTypeArray,departArray);
	    sdsFee.firstSpeciality=1;
	    var sdsCredit = new StdTypeDepart3Select("credit_std_stdTypeOfSpeciality", "credit_std_department", "credit_std_speciality", "credit_std_specialityAspect", true, true, true, true);    
	    sdsCredit.init(stdTypeArray,departArray);
	    sdsCredit.firstSpeciality=1;
	    function changeSpecialityType(event){
	       var select = getEventTarget(event);
	       sdsFee.firstSpeciality=select.value;
	       sdsCredit.firstSpeciality=select.value;
	       fireChange($("fee_std_department"));
	       fireChange($("credit_std_department"));
	    }

		<#--动态选择被执行的查询路径-->
     	function selectOnclick()
    	{
    		if (selectDo == "statFeeType") {
    			document.getElementById('statFeeType').onclick();
    		} else {
				document.getElementById('creditFeeStats').onclick();
    		}
       	}
       	
	    <#--声明form-->
	    var form = document.feeStatForm;

	    <#--当用户点击查询选择项(菜单项)的时候触发的动作-->
	    function selectFrame(td, indexPage) {
			<#--查询区域随选择的菜单项改变(name)-->	    
	    	if (selectDo != indexPage) {
	    		if (selectDo == "statFeeType") {
	    			$("viewFee").style.display = "none";
	    			$("viewCredit").style.display = "block";
	    		} else {
	    			$("viewCredit").style.display = "none";
	    			$("viewFee").style.display = "block";
	    		}
		    	<#--当选择的菜单项发生改变时，记住所选的查询路径(菜单项)，以备切换学期时使用-->
		    	selectDo = indexPage;
	    	}
	    	
	    	clearSelected(menuTable, td);
		    setSelectedRow(menuTable, td);
		    
	    	form.action = "feeStat.do?method=" + indexPage;
	    	addInput(form, "indexPage", indexPage, "hidden");
	    	form.target = "displayFrame";
	    	form.submit();
	    }
	    
	    selectOnclick(); 
	</script> 
</body>   
<#include "/templates/foot.ftl"/> 