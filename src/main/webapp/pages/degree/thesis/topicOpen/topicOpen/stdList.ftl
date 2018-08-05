<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<#include "stds.ftl"/>
  	<script>
    	function getIds() {
       		return(getCheckBoxValue(document.getElementsByName("topicOpenId")));
    	}
   		function pageGoWithSize(pageNo, pageSize) {
       		parent.search(pageNo, pageSize);
   		}
   		function stdInfo(form) {
   			var topicId = getSelectIds("topicOpenId");
	   		if ("" == topicId || topicId.indexOf(",") > -1) {
	   			alert("请选择单个选项");
	   			return;
	   		}
	   		var stdId = document.getElementById("stdId" + topicId).value;
	    	form.action = 'thesisTopicOpen.do?method=doLoadThesisTopic&stdId=' + stdId;
	    	form.submit();
	    }      
	</script>
</body>
<#include "/templates/foot.ftl"/>
