<#include "/templates/head.ftl"/>
<body> 
 <table id="resourceBar"></table>
 
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
      form.submit();
   }
   function redirectTo(url){
     window.open(url);
   }
   search();
   var bar = new ToolBar('resourceBar','系统资源管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("数据限制模式","redirectTo('restrictionPattern.do?method=search')");
   bar.addItem("数据限制参数","redirectTo('patternParam.do?method=search')");
   bar.addHelp();
  </script>
</script>
</body>
<#include "/templates/head.ftl"/>