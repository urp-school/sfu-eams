<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar"></table>
 <#include "/pages/components/adminClassListTable.ftl"/>
  <form name="actionForm" action="" method="post" onsubmit="return false;">
    <input type="hidden" name="majorTypeId" value="${RequestParameters['majorTypeId']}"/>
    <input type="hidden" name="orderBy" value="${RequestParameters['orderBy']?default('')}"/>
    <input type="hidden" name="pageSize" value="35"/>
  </form>
  <script language="javascript">
   	var bar = new ToolBar('myBar','班级列表',null,true,true);
   	bar.setMessage('<@getMessage/>');
    bar.addItem("查看班级绩点", "adminClassIdAction()", "detail.gif");
    bar.addItem("查看学年绩点", "adminClassIdByYear()", "detail.gif");
    
    var form = document.actionForm;
    function adminClassIdAction(adminClassId) {
       actionForm.target = "_blank";
   	   form.action = "multiStdGP.do?method=classGPReport";
       if (null == adminClassId || "" == adminClassId) {
	       submitId(form, "adminClassId", true);
       } else {
       		addInput(form, "adminClassId", adminClassId, "hidden");
       		form.submit();
       }
    }
    
    function adminClassIdByYear(adminClassId) {
       actionForm.target = "_blank";
   	   form.action = "multiStdGP.do?method=classGPReportByYear";
       if (null == adminClassId || "" == adminClassId) {
	       submitId(form, "adminClassId", true);
       } else {
       		addInput(form, "adminClassId", adminClassId, "hidden");
       		form.submit();
       }
    }
  </script>
</body>
<#include "/templates/foot.ftl"/>