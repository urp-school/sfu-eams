  <#assign unitList=calendar.timeSetting.courseUnits>
  <table width="100%" align="center" class="listTable" id="courseAgentTable">
    <tr class="darkColumn">
        <td width="6%"></td>
     	<#list unitList as unit>
		<td align="center" width="7%">${unit_index+1}</td>
		</#list>
    </tr>
	<#list weekList as week>
	<tr>
	    <td class="darkColumn" ><@i18nName week/></td>
  	    <#list 1..unitList?size as unit>
   		<td id="TD${week_index*unitList?size+unit_index}_${tableIndex}"  style="backGround-Color:#ffffff"></td>
		</#list>
	</tr>
    </#list>
</table>
<script>   
    var table${tableIndex} = new CourseTable(${calendar.startYear},${unitList?size*weekList?size});
	var unitCount = ${unitList?size};
	var index=0;
	var activity=null;
	<#list activityList as activity>
       activity = new TaskActivity("","","","","","","${activity.time.validWeeks}");
       <#list 1..activity.time.unitCount as unit>
	       <#if (activity.time.startUnit)!=0>
	       index =${(activity.time.weekId-1)}*unitCount+${activity.time.startUnit-1+unit_index};
	       table${tableIndex}.activities[index][table${tableIndex}.activities[index].length]=activity;
	       </#if>
       </#list>
	</#list>
	
	table${tableIndex}.marshalTable(${calendar.weekStart},${startWeek},${endWeek});
	fillTable(table${tableIndex},${weekList?size},${unitList?size},${tableIndex});
</script>