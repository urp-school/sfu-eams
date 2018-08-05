	   <tr class="darkColumn">
	    <#assign totalSize=result[paginationName].itemCount>
	    <td colspan="<#if colspans?exists>${colspans}<#else>16</#if>" align="center">
	      <#if result[paginationName].pageNumber==1>
	      <@bean.message key="page.first"/>&nbsp;
	      <@bean.message key="page.previous"/>
	      <#else>
	      <a href="#" onClick='go(1)' accesskey="f"><@bean.message key="page.first"/>(<U>F</U>)</a>
	      <a href="#" onClick='go(${result[paginationName].previousPageNumber})' accesskey="p"><@bean.message key="page.previous"/>(<U>P</U>)</a>
	      </#if>
	      <#if result[paginationName].pageNumber==result[paginationName].maxPageNumber>
	      <@bean.message key="page.next"/>&nbsp;
	      <@bean.message key="page.last"/>
	      <#else>
	      <a href="#"  onClick='go(${result[paginationName].nextPageNumber})' accesskey="n"><@bean.message key="page.next" />(<U>N</U>)</a>
	      <a href="#" onClick='go(${result[paginationName].maxPageNumber})' accesskey="l"><@bean.message key="page.last"/>(<U>L</U>)</a>
	      </#if>
	      &nbsp;<@bean.message key="page.size" arg0=result[paginationName].items?size?string arg1=result[paginationName].itemCount?string/>&nbsp;
	      &nbsp;<@bean.message key="page.perPage"/><select id="myPageSize" name="pageSize" onChange="go(1,this.value)">
	         <option value="10" <#if result[paginationName].pageSize=10>selected</#if>>10</option>
	         <option value="15" <#if result[paginationName].pageSize=15>selected</#if>>15</option>
	         <option value="20" <#if result[paginationName].pageSize=20>selected</#if>>20</option>
  	         <option value="25" <#if result[paginationName].pageSize=25>selected</#if>>25</option>
	         <option value="30" <#if result[paginationName].pageSize=30>selected</#if>>30</option>
	         <option value="50" <#if result[paginationName].pageSize=50>selected</#if>>50</option>
	         <option value="70" <#if result[paginationName].pageSize=70>selected</#if>>70</option>
	         <option value="90" <#if result[paginationName].pageSize=90>selected</#if>>90</option>
	         <option value="100" <#if result[paginationName].pageSize=100>selected</#if>>100</option>
	         <option value="150" <#if result[paginationName].pageSize=150>selected</#if>>150</option>
	         <option value="300" <#if result[paginationName].pageSize=300>selected</#if>>300</option>
	         <option value="1000" <#if result[paginationName].pageSize=1000>selected</#if>>1000</option>
	        </select>
	      <input type="text" id="myPageNumber" name="pageNumber" maxlength="7" value="${result[paginationName].pageNumber}" style="width:30px;background-color:#CDD6ED">/${result[paginationName].maxPageNumber}
	      <input type="button" name="button11" value="GO" class="buttonStyle" onClick="go()">
	    </td>
	   </tr>
	  <script type="text/javascript">
			//跳转到指定的页面
  			function getPageNumber(){
			  return document.getElementById('myPageNumber').value;
			}
  			function getPageSize(){
			  return document.getElementById('myPageSize').value;
			}
			function go(pageNumber,pageSize){
			    if(null==pageNumber)
			       pageNumber = getPageNumber();
			    var number = pageNumberValidator(pageNumber);
                if(null==pageSize)
   			       pageSize = getPageSize();
   			    if(typeof pageGoWithSize=="function")
   			      pageGoWithSize(number,pageSize);
			    else
     			  pageGo(number);
			}
			
			function pageNumberValidator(pageNumber){			
			    var value = pageNumber;
				if( isNaN(parseInt(value)) ){
					value = 1;
				}else{
					value = parseInt(value);
				}
				if(value <= 0)
					value = 1;
				if(value > ${result[paginationName].maxPageNumber})
					value = ${result[paginationName].maxPageNumber};
			    return value;
			}
	   </script>