<#include "/templates/head.ftl"/>
<body>
<#assign labInfo>上传论文开题报告</#assign>
<#include "/templates/back.ftl"/>
       <table border=0 align='left' class="infoTitle">
          <form name="pageGoForm" action="loadThesisTopic_std.do?method=doUpThesis&isUp=up&topicOpenId=${topicOpenId}"  method="post" enctype="multipart/form-data" onsubmit="return false;"> 
         <tr>
         	<td>
		      <input type="file" name="file1" size=50 class="buttonStyle" value=''>&nbsp;
	        </td>
	     </tr>
         <tr><td>上传文件大小不得大于30M <input type="button" value="<@bean.message key="system.button.submit" />" name="submitNow" onClick="submit()" class="buttonStyle"><br><br></td>
	     </tr>
	     <tr>
	     	<td class="pinkFontTD">注意:请把关于${tacheName?if_exists}附件放入一个文件里面上传,如果多次上传只能保留最后一个</td>
	     </tr>
        </form>
	   </table>
</body>
<#include "/templates/foot.ftl"/>