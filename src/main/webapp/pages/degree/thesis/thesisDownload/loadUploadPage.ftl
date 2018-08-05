<#include "/templates/head.ftl"/>
<body>
<#assign labInfo><#if thesisId=="01">论文开题<#elseif thesisId=="03">博士预答辩<#elseif thesisId="04">论文评阅(双盲)<#elseif thesisId=="05">论文答辩</#if></#assign>
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','上传${labInfo}报告',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack();
</script>
       <table border=0 align='left' class="infoTitle">
          <form name="pageGoForm" action="thesisDownload.do?method=doUpload&thesisManageId=${thesisManageId}&storeId=${thesisId}"  method="post" enctype="multipart/form-data" onsubmit="return false;"> 
         <tr>
         	<td>
		      <input type="file" name="file1" size=50 class="buttonStyle" value=''>&nbsp;
	        </td>
	     </tr>
         <tr><td>上传文件大小不得大于30M <button  name="submitNow" onClick="submit()" class="buttonStyle"><@bean.message key="system.button.submit" /></button><br><br></td>
	     </tr>
	     <tr>
	     	<td class="pinkFontTD">注意:请把关于<b>${labInfo?if_exists}</b>附件放入一个文件里面上传,如果多次上传只能保留最后一个</td>
	     </tr>
        </form>
	   </table>
</body>
<#include "/templates/foot.ftl"/>