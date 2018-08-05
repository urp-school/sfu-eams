<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0"> 
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskActivity.js"></script> 
<style>
body{
  font-size:11px;
}
</style>
<script>
    function fillTable(table,weeks,units,tableIndex){
       //删除重复的单元格
   	   for(var index=0;index<weeks*units-1;index++){
 	        var preTd=document.getElementById("TD"+index+"_"+tableIndex);
 	        var nextTd=document.getElementById("TD"+(index+1)+"_"+tableIndex);
 	        while(null!=nextTd&&table.marshalContents[index]!=null&&table.marshalContents[index+1]!=null&&table.marshalContents[index]==table.marshalContents[index+1]){
 	            nextTd.parentNode.removeChild(nextTd);
 	            var spanNumber = new Number(preTd.rowSpan);
 	            spanNumber++;
 	            preTd.rowSpan=spanNumber;
 	            index++;
 	            nextTd=document.getElementById("TD"+(index+1)+"_"+tableIndex);
 	        }
	 	}
 	    
 	    for(var k=0;k<table.unitCounts;k++){
 	         var td=document.getElementById("TD"+k+"_"+tableIndex);
 	         if(td != null&&table.marshalContents[k]!=null){
 	            td.innerHTML = table.marshalContents[k];
 	            td.style.backgroundColor="#94aef3";
 	            td.className="infoTitle";
 	         }
 	    }
   	}
</script>
<table id="courseTableBar" width="100%"></table>
<script>
   var bar = new ToolBar("courseTableBar","<@bean.message key="courseTable.printTitle" arg0=(courseTableList?size)?string/>",null,true,true);
   bar.addItem("<@msg.message key="action.print"/>","print()");
</script>
   <#assign fontSize=setting.fontSize>
   <#assign unitList=calendar.timeSetting.courseUnits>
  
   <#list courseTableList?if_exists as multiTable>
      <#assign avgWith=(100-6)/multiTable.tables?size>
       <table class="listTable" width="100%" height="100%"  style="font-size:${fontSize?default(12)}px">
          <tr>
            <td width="6%"><@msg.message key="common.time"/></td>
            <#list  multiTable.tables as table>
            <td style="width:${avgWith?int}%"><@i18nName table.resource/></td>
            </#list>
          </tr>
		  <#list setting.weekInfos as week>
	  	    <#list 1..unitList?size as unit>
	  	    <tr>
		    <td class="darkColumn" ><@i18nName week/> ${unit}</td>
		    <#list  multiTable.tables as table>
		    <#assign tableIndex>${multiTable_index * setting.tablePerPage + table_index}</#assign>
 	   		<td id="TD${week_index*unitList?size+unit_index}_${tableIndex}"  style="backGround-Color:#ffffff;"></td>
            </#list>
	    	</tr>
			</#list>
	      </#list>
      </table>
     <#list  multiTable.tables as table>
     <#assign tableIndex>${multiTable_index * setting.tablePerPage + table_index}</#assign>
  	 <#assign activityList=table.activities>
     <#include "courseTableContent_script.ftl"/>
     </#list>
    <#if multiTable_has_next><div style='PAGE-BREAK-BEFORE: always'></div></#if>
   </#list>
<#include "/templates/foot.ftl"/>