<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','论文评阅',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
 <table  width="100%"  class="frameTable"> 
	<tr>
		<td width="20%" class="frameTable_view" valign="top">
			<table width="100%" class="searchTable">
  				<form name="pageGoForm" action="#"  target="researchHarvestFrame" method="post" onsubmit="return false;">
	  			<#assign stdTypeName>stdTypeOfSpeciality</#assign>
	  			<#include "../../stdConditions.ftl"/>
	  		<tr>
	  			<td>自评表</td>
	  			<td><select name="hasWrite" style="width:100px">
	  			<option value="true">已填写</option>
	  			<option value="false">未填写</option>
	  			</select>
	  			</td>	
	   		<tr>
	     		<td colspan="4" align="center">   
	       		<button name="button9" onClick="search(1)" class="buttonStyle"><@bean.message key="system.button.query" /></button>&nbsp;
         	</td>
	   		</tr>
	   		<#assign stdTypeNullable=true>
	   		<#include "/templates/stdTypeDepart3Select.ftl"/>
	   		</form>  
			</table>
		</td>
	   <td valign="top"><iframe id="researchHarvestFrame" name="researchHarvestFrame"
    		marginwidth="0" marginheight="0" scrolling="no"
     		frameborder="0"  height="100%" width="100%">
  		</iframe>
  	   </td>
  	</tr>
 </table>
 <script>
    function search(){
    	 document.pageGoForm.action ='annotateTutor.do?method=stdList';
      	 document.pageGoForm.submit();
    } 
    function pageGo(pageNo){
       var form = document.pageGoForm;
       form.pageNo.value = pageNo;
       form.submit();
    }
    function setData(){
    	var params = getInputParams(document.pageGoForm);
    	researchHarvestFrame.setData(params);
    }
    search(1); 
</script>
</body>     
<#include "/templates/foot.ftl"/>
    