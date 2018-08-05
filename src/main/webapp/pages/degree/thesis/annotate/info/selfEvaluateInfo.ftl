  <table width="100%" class="infoTable">   
	  <caption>
        <B>${student.name?if_exists} <@bean.message key="filed.thesisbyMe" /></B>
      </caption>
      <tr>
      	<td  width="20%"   class="title">&nbsp;论文题目：</td>  
	    <td  ><#if (thesisManage.thesis.name)?exists>${thesisManage.thesis.name}<#else>${(thesisManage.topicOpen.topicOpenName)?if_exists}</#if>
	    </td>
      </tr> 
      <tr>
      	<td rowspan="7"  class="title">主要创新内容(不超过800字)：</td>
      </tr>      
	  <tr>
   		<td class="title" style="text-align:left">&nbsp;论文创新点(一)：</td>
	  </tr>
	  <tr>
	    <td class="content">${thesisAnnotate?if_exists.selfAnnotate?if_exists.innovateOne?if_exists}</td>
	  </tr>	   
	  <tr>
   		<td class="title"  style="text-align:left">&nbsp;论文创新点(二)：</td>
	  </tr>
	  <tr>
	    <td class="content">${thesisAnnotate?if_exists.selfAnnotate?if_exists.innovateTwo?if_exists}</td>	  
	  </tr>
	  <tr>
   		<td class="title" style="text-align:left">&nbsp;论文创新点(三)：</td>
	  </tr>
	  <tr>
	    <td class="content">${thesisAnnotate?if_exists.selfAnnotate?if_exists.innovateThree?if_exists}</td>	  
	  </tr>	
      <tr>
      	<td class="title">论文不足之处(缘由及改进方法)(不超过800字)：</td>  
	    <td  class="content" >${thesisAnnotate?if_exists.selfAnnotate?if_exists.thesisLack?if_exists}</td>      	 		
      </tr>
   	<tr>
	    <td colspan="2">
			<#include "publishThesisInfo.ftl"/>
	    </td>
      </tr>
  </table>