<#include "/templates/head.ftl"/>
<#include "/templates/print.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self);SetPrintSettings()"> 
<table id="resourceBar"></table>
<object id="factory2" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="css/smsx.cab#Version=6,2,433,14"></object> 

<script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskActivity.js"></script> 
<script>
    var bar = new ToolBar("resourceBar","占用时间表",null,true,true);
 	bar.addItem("<@msg.message key="action.print"/>","print()");
 	bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 	
   delimiter="";
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
 	         if (td != null && table.marshalContents[k] != null) {
 	            td.innerHTML = table.marshalContents[k];
 	            td.style.backgroundColor = "#94aef3";
 	            td.className = "infoTitle";
 	         }
 	    }
   	}
</script>
   <#list occupyTables?if_exists as occupyTable>
   		<#include "occupyTableHead.ftl"/>
   		<#assign tableIndex>${occupyTable_index}</#assign>
   		<#assign activityList=occupyTable.activities>
   		<#include "occupyTable.ftl"/>
   		
	   		<#if occupyTable_index%3=2>
	   		<div style='PAGE-BREAK-AFTER: always'></div> 
	   		</#if>
   		
   </#list>
<#include "/templates/foot.ftl"/>