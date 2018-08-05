<html>
 <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <title><@bean.message key="system.title" /></title>
  <link href="${static_base}/css/default.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" type="text/JavaScript" src="scripts/common/Common.js"></script>
 </head>
<body LEFTMARGIN="0" TOPMARGIN="0" ondblclick="changeFlag();" >

<table id="processBarTable" cellspacing="0" cellspadding="0"  style="width:100%;font-size:12px;" border="0" >
   <tr>
     <td colspan="2" height="20px">
     <table>
     <td><img src="${static_base}/images/action/info.gif"  align="top" /></td>
     <td id="summary" style="font-size:12px"></td>
     </tr>
     </table>
    </td>
   </tr>
   <tr>
	   <td id="completedTd"  style="height:15px;width:0%;font-size:12px" align="center"  bgColor="#00cc00"></td>
	   <td id="unCompletedTd" style="width:100%;height:15px;"  bgColor="#EBEBEB"></td>
   </tr>
   <tr>
	   <td style="width:100%;height:600px;border-style:hidden;" colspan="2" >
	   <select id="processContent" name="" multiple style="width:100%;height:100%;"></select>
	   </td>
   </tr>
</table>
<script>
   var complete=0;
   var count=1;
   var completedTd= document.getElementById("completedTd");
   var unCompletedTd= document.getElementById("unCompletedTd");
   var contentSelect=  document.getElementById("processContent");
   function setSummary(msg){
       document.getElementById("summary").innerHTML=msg;
   }

   colors =['black','blue','red'];
   var flag = true;
   function changeFlag(){
		flag = !flag;
   }
   function addProcessMsg(level,msg,increaceStep){
      var option =new Option(msg,"");
      option.style.color=colors[level-1];
      if(contentSelect.options.length>0)
         contentSelect.options[contentSelect.options.length-1].selected=false;
      contentSelect.options[contentSelect.options.length]=option;
      if(flag){
      contentSelect.options[contentSelect.options.length-1].selected=true;
      }
      increaceProcess(increaceStep);
   }
   function increaceProcess(increaceStep){
      if(increaceStep==null)
        increaceStep=1;
      else if(increaceStep<=0) return;
      complete+=increaceStep;
      var percent =Math.floor((complete/count)*100);
      if(percent>100) percent=100;
      completedTd.innerHTML=percent+"%"+"("+complete+"/"+count+")";
      completedTd.style.width=percent+"%";
      unCompletedTd.style.width=(100-percent)+"%";
   }
</script>