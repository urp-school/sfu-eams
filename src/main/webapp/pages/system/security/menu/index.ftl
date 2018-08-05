<#include "/templates/head.ftl"/>
<body> 
 <table id="menuBar"></table>
 <table  class="frameTable">
   <tr>
    <td style="width:160px"  class="frameTable_view"><#include "searchForm.ftl"/></td>
    <td valign="top">
    <iframe  src="#" id="contentFrame" name="contentFrame" 
      marginwidth="0" marginheight="0"
      scrolling="no" frameborder="0"  height="100%" width="100%">
    </iframe>
    </td>
   </tr>
  </table>
 <script>
   var form=document.pageGoForm;
   var action="menu.do";
   function search(){
   	  form.action=action+"?method=search";
      form.submit();
   }
   search();
   
   function redirectTo(url){
     window.open(url);
   }
   var bar = new ToolBar('menuBar','系统菜单管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("菜单配置","redirectTo('menuProfile.do?method=search')");
   bar.addHelp();
  </script>
</body>
<#include "/templates/foot.ftl"/>