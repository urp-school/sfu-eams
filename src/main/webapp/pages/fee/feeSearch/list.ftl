<#include "/templates/head.ftl"/>

<BODY  onload="f_frameStyleResize(self,10)" style="overflow-x:auto;overflow-y:hidden" >
	<table id="feeDetailBar"></table>
	<#include "feeTable.ftl"/>
	<form method="post" action="" name="actionForm" onsubmit="return false;"></form>
	<script language="javaScript">
   		var bar = new ToolBar("feeDetailBar","收费明细查询结果",null,true,true);
  		bar.setMessage('<@getMessage/>');
   		bar.addItem("<@msg.message key="action.info"/>",'info()');
   		bar.addItem("<@msg.message key="action.export"/>", "exportData()");
   		
   		var form = parent.document.feeDetailForm;	   	
  		function info(){
	     	var id = getSelectId("feeDetailId");
	     	if(id==""){
		        alert("请选择一个收费明细");
		        return;
		    }
	     	if(id.indexOf(",")!=-1){
		        alert("请仅选择一个收费明细");
		        return;
	     	}
	     	window.open("feeDetail.do?method=info&feeDetail.id="+id, '', 'scrollbars=yes,left=0,top=0,width=600,height=350,status=yes');
  		}
  		
  		function exportData() {
  			form.action = "feeSearch.do?method=export";
		    var propertyKeys = "createAt,std.code,std.name,calendar.year,calendar.term,type.name,mode.name,shouldPay,payed,invoiceCode,toRMB,currencyCategory.name,rate,remark,HSKGrade,std.enrollYear";
	        var propertyShowKeys = "缴费日期,学号,姓名,学年度,学期,收费类型,缴费方式,应缴金额,实交金额,发票号,折合人民币,币种,汇率,备注,HSK成绩,入学年份";
	    	addInput(form, "keys", propertyKeys, "hidden");
	    	addInput(form, "titles", propertyShowKeys, "hidden");
	    	var ids = getSelectIds("feeDetailId");
	    	if (ids != "") {
	    	  	alert("系统将导出你选择的记录！");
	    	  	addInput(form, "ids", ids, "hidden");
	    	} else {
	    		var totalSize = ${totalSize?default(-1)};
	    	    if (totalSize > 10000) {
	 	         	alert("系统不允许导出超过一万条的数据！");
	 	         	return;
	 	        }
	    	    if (!confirm("你要全部导出所查询的" + (totalSize < 0 ? "" : totalSize + "条") + "收费记录吗？")) {
 	    		   	return;
 	    		}
 	    	}
	    	form.submit();
  		}
	</script>
</body>
<#include "/templates/foot.ftl"/>