<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="bar" ></table>
	<#include "searchForm.ftl"/>
	<script>
	   	var bar =new ToolBar("bar","<@msg.message key="workload.workload"/><@msg.message key="common.info"/><@msg.message key="action.maintain"/>",null,true,true);
	  	bar.addItem("工作量修改日志", "teachWorkloadAlteration()");
	  	bar.addItem("<@msg.message key="filed.stat"/><@msg.message key="workload.workload"/>","stat()");
	  	bar.addItem("模板下载", "downloadTemplate()", "download.gif");
	  	bar.addItem("导入", "importData()", "excel.png");
	  	
		var form = document.teachWorkloadForm;
		var action="teachWorkload.do";
	  	function teachWorkloadAlteration() {
	  		form.action = "teachWorkloadAlteration.do?method=index";
	  		form.target = "_self";
	  		form.submit();
	  	}
	  	
		function displayDiv(divId){
	       	var div = document.getElementById(divId);
	
	       	if (div.style.display=="block"){
	        	div.style.display="none";
	       	}else{
	         	div.style.display="block";  
	       	}
	   	}
	   	
	   	function getYear(){
	      	return document.getElementById("year");
	   	}
	
		
		function search(){
		    form.action = "teachWorkload.do?method=search";
		    form.submit();
		}
		
	    search();
	    
	   	function stat(){
	    	self.location="teachWorkload.do?method=statHome";
	    }
	    
	    <#--导入-->
	    function importData() {
	    	form.action ="teachWorkload.do?method=importForm&templateDocumentId=12";
	    	addInput(form, "importTitle", "工作量导入");
	    	form.target = "teachWorkloadQueryFrame";
	    	form.submit();
	    }
	    
	    function downloadTemplate() {
	    	self.location="dataTemplate.do?method=download&document.id=12";
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>