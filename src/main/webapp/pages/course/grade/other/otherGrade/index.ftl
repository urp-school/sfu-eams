<#include "/templates/head.ftl"/>
<body>
 <table id="gradeBar"></table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr>
   <td style="width:20%" valign="top" class="frameTable_view">
		<#include "../otherGradeSearch/searchForm.ftl"/>
     </td>
     <td valign="top">
	     <iframe src="#" id="gradeFrame" name="gradeFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
    </tr>
  <table>
<script>
    var bar=new ToolBar("gradeBar","校内外成绩管理",null,true,true);
    bar.addItem("导入","importData()");
    bar.addItem("下载数据模板","downloadTemplate()",'download.gif');
    bar.addHelp("<@msg.message key="action.help"/>");
    
    var action="otherGrade.do";
    var form = document.searchForm;
    function search(pageNo,pageSize,orderBy){
	   form.action = action+"?method=search";
	   form.target="gradeFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    
    search();
    
    function downloadTemplate(){
      self.location="dataTemplate.do?method=download&document.id=5";
    }
    
    function importData(){
       form.action=action+"?method=importForm&templateDocumentId=5";
       addInput(form,"importTitle","校内外其他成绩数据上传")
       form.submit();
    }
</script> 
 </body>   
<#include "/templates/foot.ftl"/> 