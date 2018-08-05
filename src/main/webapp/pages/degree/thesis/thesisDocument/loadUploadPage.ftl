<#include "/templates/head.ftl"/>
<body>
<#assign labInfo>上传文件</#assign>
<#include "/templates/back.ftl"/>
       <table border=0 align='left' class="infoTitle">
          <form name="pageGoForm" action="thesisDocument.do?method=doUpload&documentId=${documentId?if_exists}"  method="post" enctype="multipart/form-data" onsubmit="return false;"> 
         <tr>
         	<td>
		      <input type="file" name="file1" size=50 class="buttonStyle" value=''>&nbsp; 
	        </td>
	     </tr>
	     <#if !documentId?exists>
         <tr>
         	<td>
		      <input type="file" name="file2" size=50 class="buttonStyle" value=''>&nbsp; 
	        </td>
	     </tr>
         <tr>
         	<td>
		      <input type="file" name="file3" size=50 class="buttonStyle" value=''>&nbsp; 
	        </td>
	     </tr>
	     </#if>
         <tr>
         <td>上传文件大小不得大于30M <button name="submitNow" onClick="doSubmit(this.form)" class="buttonStyle"><@msg.message key="system.button.submit" /></button><br><br>
         	 <#if documentId?exists>
         	 	你现在处于文档替换状态,如果提交现有的文件上传,老的文件将会被覆盖.
         	 </#if>
         </td>
	     </tr>
        </form>
	   </table>      

</body>
<script>
	function doSubmit(form){
		var action = form.action;
		if(<#if documentId?exists>true<#else>false</#if>){
			if(confirm("你现在处于文档替换状态\n如果提交现有的文件上传,老的文件将会被覆盖\n你确定要执行这个操作吗?")){
				form.submit();
			}
		}else{
			form.submit();
		}
	}
</script>
<#include "/templates/foot.ftl"/>