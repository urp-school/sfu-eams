<#include "/templates/head.ftl"/>
<#macro showCount collection propertyList propertyListIndex remPropertyList>
	<#list remPropertyList as property>
 		<#assign count = collection.getCount(propertyList[propertyListIndex].dataName,property) />
 		<td><#if count.totalCount?exists>${count.totalCount}(${count.maleCount?default(0)}/${count.femaleCount?default(0)})</#if></td>
 	</#list>
 	<td><#if collection.totalCount.totalCount?exists>${collection.totalCount.totalCount}(${collection.totalCount.maleCount?default(0)}/${collection.totalCount.femaleCount?default(0)})</#if></td>
</#macro>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table cellpadding="0" cellspacing="0" width="100%" border="0">
	<td align="center" class="contentTableTitleTextStyle">
     	<br>
    </td>
	<tr>
    	<td align="center" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     	<B>学生人数统计页面</B>
    	</td>
   	</tr>
   	<tr>
    	<td align="center" class="contentTableTitleTextStyle">
     	<br>
    	</td>
   	</tr>
   	<tr>
    	<td>
    		<table width="90%" align="center" style="font-style: normal;font-size: 10pt;">
    			<tr>
    				<td width="34%">数据记录方式：总数（男生人数/女生人数）</td>
    				<td width="33%">统计日期：${currentDate?string("yyyy-MM-dd")}</td>
    				<td width="33%"></td>
    			</tr>
    		</table>
    	</td>
   	</tr>   
  	<tr>
    	<td>
     		<table width="90%" align="center" class="listTable">
     		<tr>
     			<#assign propertyListSize = propertyList?size/>
     			<#list 0..(propertyListSize-3) as propertyIndex>
     			<td><@i18nName propertyList[propertyIndex] /></td>
     			</#list>
     			<#list remPropertyList as property>
     			<td>${property}</td>
     			</#list>
     			<td>合计</td>
     		</tr>
     			<#list countCollection.getList(propertyList,0,"key.name") as collection0>
     				<tr>
     					<td rowspan="${collection0.getSize(propertyList,1,propertyListSize-2)}" >
     						<@i18nName collection0.key />
     					</td>
     				</tr>
     				<#list collection0.getList(propertyList,1,"key.name") as collection1>
 						<tr>
	 						<td rowspan="${collection1.getSize(propertyList,2,propertyListSize-2)}" >
	 							<@i18nName collection1.key />
	 						</td>
 						</tr>
     					<#list collection1.getList(propertyList,2,"key.name") as collection2>
     						<tr>
	     						<td rowspan="${collection2.getSize(propertyList,3,propertyListSize-2)}" >
	     							<@i18nName collection2.key?if_exists />
	     						</td>
     						</tr>
     						<#list collection2.getList(propertyList,3,"key.name") as collection3>
     						<tr>
	     						<td rowspan="${collection3.getSize(propertyList,4,propertyListSize-2)}" >
	     							<@i18nName collection3.key?if_exists />
	     						</td>
	     						<@showCount collection3, propertyList, (propertyListSize-2), remPropertyList />
     						</tr>
     						</#list>
     						<tr>
	     						<td colspan="2" >小计</td>
	     						<@showCount collection2, propertyList, (propertyListSize-2), remPropertyList />
     						</tr>
     					</#list>
     					<tr>
     						<td colspan="3" >小计</td>
     						<@showCount collection1, propertyList, (propertyListSize-2), remPropertyList />
 						</tr>
     				</#list>
     				<tr>
 						<td colspan="4" >合计</td>
 						<@showCount collection0, propertyList, (propertyListSize-2), remPropertyList />
 					</tr>
     			</#list>
     		<tr>
     		</tr>
     			
       		</table>
		</td>
	</tr>
  	</table>  
 </body>
 <script>
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("stdId")));
    }
    function doAction(){
    	document.getElementById("stdIds").value=getIds();
    	document.getElementById("resetForm").submit();
    }
 </script>
<#include "/templates/foot.ftl"/>