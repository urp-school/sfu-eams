<#include "/templates/head.ftl"/>
<body>
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','上传我的论文报告',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack();
</script>
       <table border=0 align='left' class="infoTitle">
          <form name="pageGoForm" action="thesisStd.do?method=doUpload&thesisManageId=${thesisManageId}&storeId=${thesisId}"  method="post" enctype="multipart/form-data" onSubmit="return false;"> 
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