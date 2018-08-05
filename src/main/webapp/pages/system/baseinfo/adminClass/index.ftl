<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="myBar"></table>
 <table class="frameTable">
   <tr>
    <td width="20%" class="frameTable_view">
    <form name="searchForm" method="post" target="contentListFrame">
      <#include "/pages/components/initAspectSelectData.ftl"/>
	  <#include "/pages/components/adminClassSearchTable.ftl"/>
	  <table><tr height="200px"><td></td></tr></table>
     </form>
    </td>
    <td valign="top">
	  <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no"frameborder="0"  height="100%" width="100%"></iframe>   
    </td>
   </tr>
  </table>
  	<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
   	
    type="adminClass";
    keys="code,name,stdType.name,enrollYear,department.name,speciality.name,aspect.name,eduLength,planStdCount,actualStdCount,stdCount,instructor,createAt,modifyAt";
    titles="<@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="entity.studentType"/>,<@msg.message key="attr.enrollTurn"/>,<@msg.message key="entity.department"/>,<@msg.message key="entity.speciality"/>,<@msg.message key="entity.specialityAspect"/>,学制,计划人数,实际在校人数,学籍有效人数,辅导员,<@msg.message key="attr.createAt"/>,<@msg.message key="attr.modifyAt"/>";
    labelInfo="<@bean.message key="page.adminClassListFrame.label" />";
    function initToolBarHook(bar){
       	bar.addItem("计算班级人数","batchUpdateStdCount()",'update.gif');
       	menu1 =bar.addMenu("导入","importData()");
       	menu1.addItem("下载模板","downloadTemplate()","download.gif");
		var statusMenu = bar.addMenu("批量修改状态");
		statusMenu.addItem("有效", "batchUpdateState('1')");
		statusMenu.addItem("无效", "batchUpdateState('0')");
    }
    search();
    searchClass=search;
    initBaseInfoBar();
    
    function batchUpdateStdCount(){
       var classIds = getIds();
       if(classIds=="")
         window.alert('<@bean.message key="common.select"/>!');
       else{
           if(!confirm("系统将计算选定班级的实际在校人数和学籍有效人数，点击[确定]继续"))return;
           form.action=getAction()+"?method=batchUpdateStdCount&adminClassIds=" +classIds;
           addParamsInput(form,contentListFrame.queryStr);
           form.target="";
       	   form.submit();
       }
    }
    function downloadTemplate(){
      self.location="dataTemplate.do?method=download&document.id=3";
    }
    function importData(){
       form.action=getAction()+"?method=importForm&templateDocumentId=3";
       addInput(form,"importTitle","班级数据上传")
       form.submit();
    }
    
    function batchUpdateState(status) {
    	var ids = getCheckBoxValue(contentListFrame.document.getElementsByName("adminClassId"));
    	if (ids == null || ids == "") {
    		alert("请选择一个或多个进行操作。");
    		return;
    	}
    	form.action = "adminClass.do?method=batchUpdateState";
    	addInput(form, "ids", ids, "hidden");
    	addInput(form, "status", status, "hidden");
    	addParamsInput(form, contentListFrame.queryStr);
    	form.submit();
    }
    
 </script>
</body>
<#include "/templates/foot.ftl"/>