<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" > 
<script language="JavaScript" type="text/JavaScript" src="scripts/course/TaskActivity.js"></script> 
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
<#if RequestParameters['ignoreHead']?exists>
<#else>
<table id="courseTableBar" width="100%"></table>
<script>
   var bar = new ToolBar("courseTableBar","<@bean.message key="courseTable.printTitle" arg0=(courseTableList?size)?string/>",null,true,true);
   bar.addItem("<@msg.message key="action.print"/>","print()");
</script>
</#if>
   <#assign userCategory=1>	
   <#assign taskPageMap={'std':'taskListOfStd','class','taskListOfClass','teacher':'taskListOfTeacher','room':'taskListOfRoom'}>
   <#assign fontSize=setting.fontSize>
   <#list courseTableList?if_exists as table>
   		<#if RequestParameters['ignoreHead']?exists>   			
   		<#else>
   		   <#assign labInfo><@i18nName table.resource/></#assign>
   			<#include "/pages/course/arrange/task/courseTable/courseTableHead.ftl"/>
   		</#if>
   		<#assign tableIndex>${table_index}</#assign>
   		<#assign activityList=table.activities>
   		<#include "/pages/course/arrange/task/courseTable/courseTableContent_simple.ftl"/>
   		<#include "/pages/course/arrange/task/courseTable/courseTableRemark.ftl"/>  		
   		
   		<#if !setting.ignoreTask>
   		    <#assign taskList=table.tasks>
   		    <#include "/pages/course/arrange/task/courseTable/${taskPageMap[table.kind]}.ftl"/>
   		</#if>
   		<#if table.kind=="room">
	   		<#if table_has_next&&(table_index+1)%2=0>
	   		<div style='PAGE-BREAK-BEFORE: always'></div> 
	   		</#if>
   		<#else>
   		    <#if table_has_next><div style='PAGE-BREAK-AFTER: always'></div> </#if>
   		</#if>
   		<#include "/pages/course/arrange/task/courseTable/courseTableFoot.ftl"/>
   </#list>
<#include "/templates/foot.ftl"/>