<BODY>
  <table id="staticBar"></table>
  <script>
    var bar = new ToolBar("staticBar","<#if RequestParameters['isOccupy']='1'><@bean.message key="info.occupyList"/><#else>空闲时间统计表</#if>",null,true,true);
    bar.addItem("<@msg.message key="action.print"/>","print()")
    bar.addItem("<@msg.message key="action.export"/>",exportToExcel,"excel.png");
    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 //指定页面区域内容导入Excel
 function exportToExcel()  {
  var oXL= newActiveX("Excel.Application");
  if(null==oXL) return;
  var oWB = oXL.Workbooks.Add(); 
  var oSheet = oWB.ActiveSheet;  
  var sel=document.body.createTextRange();
  sel.moveToElementText(document.getElementById("occupyStatTable"));
  sel.select();
  sel.execCommand("Copy");
  oSheet.Paste();
  oXL.Visible = true;
 }
  </script>
