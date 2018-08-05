<#include "/templates/head.ftl"/>
<script>
    function search(){
    	 document.listForm.action ="preAnswer_tutor.do?method=stdList";
    	 document.listForm.target="answerFrame";
      	 document.listForm.submit();
    } 
    function setData(){
    	var params = getInputParams(document.listForm);
    	answerFrame.setData(params);
    }
</script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','博士论文预答辩',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<#assign extraTr>
	<input type="hidden" name="stdTypeIdSeq" value="<#list result.stdTypeList?if_exists as stdType><#if stdType_has_next>${stdType.id},<#else>${stdType.id}</#if></#list>">
	<input type="hidden" name="departmentIdSeq" value="<#list result.departmentList?if_exists as department><#if department_has_next>${department.id},<#else>${department.id}</#if></#list>">
</#assign>
<#include "../preAnswer/condition.ftl"/>
  <script>
  	search();
  </script>
</body>     
<#include "/templates/foot.ftl"/>