<#include "/templates/head.ftl"/>
<#include "/pages/course/grade/gradeMacros.ftl"/>
<style>
.title {
    font-style: normal;
    font-size: 7pt;
    height:30px;
}
.singleCourse {
    font-style: normal;
    font-size: 8pt;
    height:30px;
}
</style>
<BODY style="margin:0px;"f_frameStyleResize(self)'>
 <table id="dutyStdListBar" width="100%"></table>
 <div id = "PrintA" width="100%" align="center" cellpadding="0" cellspacing="0">
   <#assign tableIndex=0/>
    <#list multiStdGPs as multiStdGP>
    <#if multiStdGP.stdGPs?exists>
    <#assign stdGPs = multiStdGP.stdGPs?sort_by(["std","code"])/>
    <#assign calendars = multiStdGP.calendars?sort_by("yearTerm")/>
    <#list stdGPs?chunk(pageSize) as stdGPList>
 	 <table width="100%" style="border:0;<#if tableIndex!=0>PAGE-BREAK-before: always;</#if>" cellpadding="0" cellspacing="0">
 	    <tr>
 	      <td align="center" colspan="20"><h3><@i18nName systemConfig.school/>班级绩点一览表</h3></td>
 	    </tr>
 	 	<tr>
 	 	  <td style="font-size:15px;" colspan="20">
 	 	  <#if multiStdGP.adminClass?if_exists.name?exists>
 	 	  	班级：<@i18nName multiStdGP.adminClass/>
 	 	  </#if>
 	 	  </td>
 	 	</tr>
	 </table>
	 <#assign tableIndex = tableIndex + 1/>
     <table width="100%" align="center" class="listTable">
	   <tr class="title" align='center'>
	     <td><@msg.message key="attr.stdNo"/></td>
	     <td style="width:80px"><@msg.message key="attr.name"/></td>
	     <td>总绩点</td>
	     <#list calendars as calendar>
	     <td >${calendar.year} ${calendar.term}</td>
	     </#list>
	   </tr>
	   <#list stdGPList as stdGP>
	    <tr align="center">
		   <td>${stdGP.std.code}</td>
		   <td><@i18nName stdGP.std/></td>
		   <td><@reserve2 stdGP.GPA/></td>
		   <#list calendars as calendar>
		   <td><#if stdGP.getGP(calendar)?exists><@reserve2  stdGP.getGP(calendar)/></#if></td>
		   </#list>
	   </#list>
     </table>
  </#list>
  </#if>
  </#list>
  </div>
<script>
   var bar = new ToolBar("dutyStdListBar","学生成绩登记总表",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.print"/>","print()");
   bar.addItem("导出到Excel","AllAreaExcel()");
   bar.addItem("导出到Word","AllAreaWord()");
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
   
 //指定页面区域内容导入Excel
 function AllAreaExcel(){
  var oXL= newActiveX("Excel.Application");
  if(null==oXL) {
  	return;
  } 
  var oWB = oXL.Workbooks.Add(); 
  var oSheet = oWB.ActiveSheet;  
  var sel=document.body.createTextRange();
  sel.moveToElementText(PrintA);
  sel.select();
  sel.execCommand("Copy");
  oSheet.Paste();
  oXL.Visible = true;
 }

 //指定页面区域内容导入Word
 function AllAreaWord(){
  var oWD= newActiveX("Word.Application");
  if(null==oWD) {
  	return;
  }
  var oDC = oWD.Documents.Add("",0,1);
  var oRange =oDC.Range(0,1);
  var sel = document.body.createTextRange();
  sel.moveToElementText(PrintA);
  sel.select();
  sel.execCommand("Copy");
  oRange.Paste();
  oWD.Application.Visible = true;
 }
</SCRIPT>
</body>
<#include "/templates/foot.ftl"/>
