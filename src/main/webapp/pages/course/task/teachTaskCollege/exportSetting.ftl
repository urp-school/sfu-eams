<#include "/templates/head.ftl"/>
<body >
<table id="exportBar"></table>
<script>
   var bar = new ToolBar("exportBar","导出教学任务，选择导出列",null,true,true);
   bar.addItem("<@bean.message key="action.export"/>","exportTeachPlan()");
   bar.addBack("<@msg.message key="action.back"/>");
</script>

<table width="100%" align="center" class="listTable">   
   <form name="exportForm" method="post" action="" onsubmit="return false;">
   <input type="hidden" name="keys" value="">
   <input type="hidden" name="titles" value="">
	<#list RequestParameters['params']?if_exists?split("&") as param>
	    <#if (param?length>2)>
	    <input  type="hidden" name="${param[0..param?index_of('=')-1]}" value="${param[param?index_of('=')+1..param?length-1]}"/>
	    </#if>
	</#list>
   </form>
   <tr class="darkColumn">
      <td colspan="4">选择导出的列
      <input type="checkbox" onclick="setBasicChecked()">基本信息&nbsp;
      <input type="checkbox" onclick="setChecked('teachClass')">教学班信息&nbsp;
      <input type="checkbox" onclick="setChecked('requirement')">任务要求信息&nbsp;
      <input type="checkbox" onclick="setChecked('arrangeInfo')">课程安排信息&nbsp;
      <input type="checkbox" onclick="setChecked('electInfo')">选课信息&nbsp;
      </td>
   </tr>
</table>

<table width="100%" align="center" id="exportTable" class="listTable">
   <tbody>  
   <form name="exportListForm" method="post">
   </tbody>
 </table>
 </form>
 <script>
     var keys="";
     var titles="";
     function exportTeachPlan(){
       var form = document.exportListForm;
       getParams();
       //alert(keys);
       //alert(titles);
       if(""==keys){alert("请选择导出列");return;}
       document.exportForm['keys'].value=keys;
       document.exportForm['titles'].value=titles;
       document.exportForm.action="?method=export";
       document.exportForm.submit();
     }
     function getParams(){
          for(i =0;i < keyMap.length; i++){
	          elem=$("key"+keyMap[i].key);
	          if(elem.id.indexOf("key")==0){
      	        if(elem.checked==false) {
      	        	continue;
      	        }
      	      	keys+="," + elem.value;
      	      	var titleInput =document.getElementById(elem.value);
      	      	titles+="," +titleInput.value;
           }
	    }
	 }
	 var keyMap = new Array();
	 <#list keyMap?keys as key>
	    keyMap[${key_index}]={"key":'${key}',"title":'<#if key='requirement.referenceBooks'>教学软件（多媒体教室）<#elseif key='requirement.cases'>上机时间（公共实验室机房）<#elseif key='requirement.requireRemark'>上机所需软件（公共实验室机房）<#else>${keyMap[key]}</#if>'} 
	 </#list>
	 function orderFunc(attr1,attr2){
	   var rs =0;	   
	   if(attr1.key.indexOf(".")==-1&&attr2.key.indexOf(".")!=-1) rs= -1;
	   else if(attr1.key.indexOf(".")!=-1&&attr2.key.indexOf(".")==-1) rs= 1;
	   else{
		   if(attr1.key>attr2.key) rs=1;
		   else rs=-1
	   }
	   return rs;
	 }
	 function initTable(){	   
	   keyMap.sort(orderFunc);
	   var keysPerCol = Math.ceil(${keyMap?keys?size}/4)	  
	    for(var j = 0 ; j < keysPerCol;j++){
    	    var tr = document.createElement('tr');
	        for(var i=0;i<4;i++){
	            var index = i*keysPerCol+j;
	            if( index >= ${keyMap?keys?size}) break;
		        var td = document.createElement('td');
		        var innerHTML ='<input type="checkbox" id="key' + keyMap[index].key + '" value="' + keyMap[index].key
		                       +'"><input type="hidden" id="' +keyMap[index].key
		                       +'" name="'+keyMap[index].key +'" value="'+keyMap[index].title + '">'+keyMap[index].title;
		        td.innerHTML=innerHTML;
		        //alert(innerHTML);
		        tr.appendChild(td);      
	        }
	        tr.className="grayStyle";
	        //alert(tr.innerHTML)
	        exportTable.tBodies[0].appendChild(tr);
	    }
	 }
	 initTable();
	 function setChecked(kind){
	    for(i =0;i < keyMap.length; i++){
	      elem=$("key"+keyMap[i].key);
  	      if(elem.value.indexOf(kind)==0){
      	        elem.checked=!elem.checked;
           }
	    } 
	 }
	 var other = ["teachClass","arrangeInfo","requirement","electInfo"];
	 
	 function setBasicChecked(){
	    for(i =0;i < keyMap.length; i++){
	      elem=$("key"+keyMap[i].key);
	      var notBasic=false;
	      for(var j=0;j<other.length;j++){
	         if(elem.value.indexOf(other[j])==0){
	           notBasic=true;
	           break;
	         }
          }
          if(!notBasic){
 	         elem.checked=!elem.checked;
 	      }
	    } 
	 }
 </script>
</body>
<#include "/templates/foot.ftl"/>