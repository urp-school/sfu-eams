<#include "/templates/head.ftl"/>
<BODY >
<table id="bar" width="100%"></table>
<table  width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td align="center">     
      <span class="contentTableTitleTextStyle">
       <@html.errors />
      </span><br><br>
      [<a href="javascript:history.back();"> <@bean.message key="attr.backPage"/> </a>]     
      [<a href="javascript:displayStactTrace()">显示日志</a>]      
    </td>
  </tr>
</table>
<input type="hidden" name="stackTrace" value="${stackTrace?if_exists}">
<table  width="100%" height="20" border="0" cellpadding="0" cellspacing="0">
<tr>
<td align="center">
<div id="stackTraceDiv" style="position:relative;width:100%;display:none;font-size:8px">
   <table width="100%">
      <tr>
          <td>${stackTrace?if_exists}</td>
      </tr>
   </table>
</div>
</td>
</tr>
</table>
<script>
var display=false;
function displayStactTrace(){
    var stackTraceDiv = document.getElementById('stackTraceDiv');
    if(display==false){
        stackTraceDiv.style.display='block';
        display=true;
    }
    else{
        stackTraceDiv.style.display='none';
        display=false;
    }
}
var bar = new ToolBar("bar","操作提示信息",null,true,true);
bar.addBack();
</script>
</BODY>
<#include "/templates/foot.ftl"/>
