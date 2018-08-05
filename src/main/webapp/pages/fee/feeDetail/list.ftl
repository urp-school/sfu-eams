<#include "/templates/head.ftl"/>

<BODY style="overflow-x:auto;overflow-y:auto;">
	<table id="feeDetailBar"></table>
	<#include "../feeSearch/feeTable.ftl"/>
	<br><br>
 	<form method="post" action="" name="actionForm"></form>
	<script language="javaScript">
   		var bar = new ToolBar("feeDetailBar", "收费明细查询结果", null, true, true);
   		bar.setMessage('<@getMessage/>');
   		<#if (stdList?size==1)>
	   		bar.addItem("添加[${stdList?first.code}  ${stdList?first.name}]", 'addFor("${stdList?first.code}")');
	   	<#elseif (stdList?size>1)>
	    	menu= bar.addMenu("添加....",null,'new.gif');
	   		<#list stdList as std>
	      		menu.addItem("${std.code}  ${std.name?if_exists}", 'addFor("${std.code}")');
	   		</#list>
   		</#if>
   		bar.addItem("导出", 'exportData()');
   		bar.addItem("<@msg.message key="action.add"/>", 'addFor()');
   		bar.addItem("<@msg.message key="action.info"/>", 'info()');
   		bar.addItem("<@msg.message key="action.edit"/>", 'edit()');
   		bar.addItem("<@msg.message key="action.delete"/>", 'remove()');
   		bar.addItem("导入", "importData()");
 		
        var form=document.actionForm;
        
 		<#--根据checkBoxname得到checkbox的Value串。-->
 		function getIds () {
       		return(getCheckBoxValue(document.getElementsByName('feeDetailId')));
	    }
	    
	    function exportData () {
	        var parentForm=parent.document.feeDetailForm;
		    var propertyKeys = "createAt,std.code,std.name,calendar.year,calendar.term,type.name,mode.name,shouldPay,payed,invoiceCode,toRMB,currencyCategory.name,rate,remark,HSKGrade,std.enrollYear";
	        var propertyShowKeys = "缴费日期,学号,姓名,学年度,学期,收费类型,缴费方式,应缴金额,实交金额,发票号,折合人民币,币种,汇率,备注,HSK成绩,入学年份";
	    	parentForm.action = "feeDetail.do?method=export";
	    	addInput(parentForm, "keys", propertyKeys, "hidden");
	    	addInput(parentForm, "titles", propertyShowKeys, "hidden");
	    	var ids = getSelectIds("feeDetailId");
	    	if (ids != "") {
	    	  alert("系统将导出你选择的记录！");
	    	  addInput(parentForm, "ids", ids, "hidden");
	    	}else{
	    	    if(${totalSize}>10000){
	 	         alert("导出数据超过一万，系统不允许导出");return;
	 	        }
	    	    if (!confirm("你要全部导出所查询的${totalSize}个收费记录吗？")) {
 	    		   return;
 	    		}
 	    	}
	    	parentForm.submit();
	    }
	    
	  	function edit() {
		    var id = getIds();
		    if (id == ""){
		        alert("请选择一个收费明细");
		        return;
		    }
		    if (id.indexOf(",") != -1){
		    	alert("请仅选择一个收费明细");
		        return;
	     	}
	     	window.open("feeDetail.do?method=edit&feeDetail.id=" + id, '', 'scrollbars=yes,left=0,top=0,width=600,height=500,status=yes');
	  	}
	  	
	  	function info () {
	     	var id = getIds();
	     	if (id == "") {
	        	alert("请选择一个收费明细");
	        	return;
	     	}
	     	if (id.indexOf(",") != -1) {
	        	alert("请仅选择一个收费明细");
	        	return;
	     	}
	     	window.open("feeDetail.do?method=info&feeDetail.id=" + id, '', 'scrollbars=yes,left=0,top=0,width=600,height=400,status=yes');
	  	}
	  	
		function remove() {
			var ids = getIds();
		    if (ids == "") {
		        alert("请选择一个收费明细");
		        return;
		    }
		    if (confirm("是否确定要删除收费明细信息?")) {
		        addInput(form,"params",getInputParams(parent.document.feeDetailForm, null, false));
			    form.action = "feeDetail.do?method=remove&feeDetailIds=" + ids;
				form.submit();
		    }
	  	}
	  	
	  	function addFor(code) {
	   		var url = "feeDetail.do?method=add";
	   		if (null != code) {
	      		url += "&feeDetail.std.code=" + code;
      		}
	   		window.open(url, '', 'scrollbars=yes,left=0,top=0,width=600,height=400,status=yes');
	  	}
	  	
	  	<#--收费导入-->
	  	function importData() {
	  		form.action = "feeDetail.do?method=importForm";
	  		addInput(form, "templateDocumentId", "11");
	  		addInput(form, "importTitle", " 收费导入");
	  		form.submit();
	  	}
	</script>
</body>
<#include "/templates/foot.ftl"/>
