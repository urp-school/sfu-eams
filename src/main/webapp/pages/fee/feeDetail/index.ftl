<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="bar"></table>
	<table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
	  	<tr style="height: 38mm">
			<td valign="top">
				<#include "/pages/components/initAspectSelectData.ftl"/>
				<#include "searchForm.ftl"/>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<iframe name="addFeeQueryFrame" src="#" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
			</td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "收费信息维护", null, true, true);
		bar.setMessage('<@getMessage/>');
   		bar.addItem("模板下载", "downloadTemplate()", "download.gif");

	    var sds = new StdTypeDepart3Select("std_stdTypeOfSpeciality","std_department","std_speciality","std_specialityAspect",true,true,true,true);    
	    sds.init(stdTypeArray,departArray);
	    sds.firstSpeciality=1;
	    function changeSpecialityType(event){
	       var select = getEventTarget(event);
	       sds.firstSpeciality=select.value;
	       fireChange($("std_department"));
	    }
	    
	 	var form = document.feeDetailForm;
		document.getElementById('stdType').value = "";
	 	search();
	 	function search() {
	 		form.action = "feeDetail.do?method=search";
	 		form.target = "addFeeQueryFrame";
	 		form.submit();
	 	}
	 	
	  	function downloadTemplate() {
	  		self.location="dataTemplate.do?method=download&document.id=11";
	  	}
	</script>
</body>
<#include "/templates/foot.ftl"/>