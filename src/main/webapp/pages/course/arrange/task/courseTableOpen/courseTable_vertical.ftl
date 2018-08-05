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
   	   for(var i=0;i<weeks;i++){
	 	    for(var j=0;j<units-1;j++){
	 	        var index =units*i+j;
	 	        var preTd=document.getElementById("TD"+index+"_"+tableIndex);
	 	        var nextTd=document.getElementById("TD"+(index+1)+"_"+tableIndex);
	 	        while(table.marshalContents[index]!=null&&table.marshalContents[index+1]!=null&&table.marshalContents[index]==table.marshalContents[index+1]){
	 	            preTd.parentNode.removeChild(nextTd);
	 	            var spanNumber = new Number(preTd.colSpan);
	 	            spanNumber++;
	 	            preTd.colSpan=spanNumber;
	 	            j++;
	 	            if(j>=units-1){
	 	                break;
	 	            }
	 	            index=index+1;
	 	            nextTd=document.getElementById("TD"+(index+1)+"_"+tableIndex);
	 	        }
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
   <#assign taskPageMap={'std':'taskListOfStd','class','taskListOfClass','teacher':'taskListOfTeacher','room':'taskListOfRoom'}>
   <#list courseTableList?if_exists as multiTable>
      <#list multiTable.tables as table>
        <#include "courseTable_simpleHead.ftl"/>
        <#assign tableIndex>${multiTable_index * setting.tablePerPage + table_index}</#assign>
   		<#assign activityList=table.activities>
   		<#include "courseTableContent_simple.ftl"/>
   		<p></p>
   		</#list>
   		<#if multiTable_has_next><div style='PAGE-BREAK-BEFORE: always'></div></#if>
   </#list>
<#include "/templates/foot.ftl"/>