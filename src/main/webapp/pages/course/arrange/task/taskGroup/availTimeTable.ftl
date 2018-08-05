<#assign unitList=calendar.timeSetting.courseUnits?sort_by("index")>
<table id="taskGroupAailBar"></table>
  <table width="100%" align="center" class="listTable">
    <tr class="darkColumn">
        <td width="50px"></td>
     	<#list unitList as unit>
		<td><@i18nName unit/></td>
		</#list>
    </tr>
	<#list weekList as week>
	<tr>
	    <td class="darkColumn"><@i18nName week/></td>
        <#list 0..unitList?size-1 as unit>
        <#assign unitIndex=week_index*unitList?size + unit_index/>
        <#assign unitValue=availTime.available[unitIndex..unitIndex]/>
   		<td align="center"  valign="middle" id="${unitIndex}"  style="<#if unitValue=="0">backGround-Color:yellow; <#else>backGround-Color:#94aef3;</#if>">
   		<input maxLength="1" style="border: 0px solid yellow;<#if unitValue=="0">backGround-Color:yellow;<#else>backGround-Color:#94aef3;</#if> width:100%;height:100%" onclick="changePriority(event)" onChange="validPriority(this);" id="unit${unitIndex}"  value="${unitValue}"/>
   		</td>
		</#list>
	</tr>
    </#list>
</table>
<table align="center" width="100%">
	<tr>
	 <td class="infoTitle"><@bean.message key="info.availTime.legend"/></td>
	 <td class="infoTitle" style="backGround-Color:#94aef3" width="80px"><@bean.message key="info.avaliTime.available"/></td>
	 <td class="infoTitle" style="backGround-Color:yellow" width="80px"><@bean.message key="info.avaliTime.unavailable"/></td>
     <td class="infoTitle">改变权值使用:</td>
	 <td class="infoTitle"><input type="radio" name="inputMode" value="inputByMouseInc" checked/>鼠标点击增加</td>
	 <td class="infoTitle"><input type="radio" name="inputMode" value="inputByMouseDec"/>鼠标点击降低</td>
	 <td class="infoTitle"><input type="radio" name="inputMode" value="inputByHand"/>手动输入</td>
	</tr>
</table>
<script>
 var availColor="#94aef3";
 var notAvailColor="yellow";
   unitsPerDay=${unitList?size};
   function changePriority(event){
       var input = getEventTarget(event);
       var mode =0;
       for(var i=0;i<3;i++)
         if(input.form['inputMode'][i].checked)
            mode=i;
       if(mode!=2){
          var newValue=new Number(input.value);
          newValue += (mode==0)?1:-1;
          if(newValue>9) newValue=0;
          else if(newValue<0) newValue=9;
          input.value=newValue;
          changeColorBy(input);
       }
   }
     function changeAvailTime(elem){
         var input = document.getElementById("unit"+elem.id);
         if(elem.style.backgroundColor==availColor){
             elem.style.backgroundColor=notAvailColor;
             input.value="0";
         }
         else if(elem.style.backgroundColor==notAvailColor){
             elem.style.backgroundColor=availColor;
            input.value="1";
         }
      }
      
     function getAvailTime(){
         var avail="";
         for(i=0;i<7*${unitList?size};i++)
           avail+=document.getElementById("unit"+i).value;
         return  avail;
        
     }
	function validPriority(input){
	     var num=/^[0-9]$/
		 var value =input.value;
		 if(num.test(value)){
		 }else{
		     input.value =1;
		     alert("时间占用优先级在[0-9]之间。默认为1");
		 }
		 changeColorBy(input);
	}
	/**
	 * 改变选择框的颜色
	 */
    function changeColorBy(input){
        if(input.value!="0") {
	    input.style.backgroundColor=availColor;
    	input.parentNode.style.backgroundColor=availColor;
     }
	 else{
	   input.style.backgroundColor=notAvailColor;
	   input.parentNode.style.backgroundColor=notAvailColor;
	 }
    }
     function clearAll(){
         if(!confirm("清空占用标记,将使所有时间变为不可用，确定设置吗？"))return;
     	 for(var i=0; i<${unitList?size*weekList?size};i++){
		     var elem = document.getElementById("unit"+i);
	         elem.value=0;
	         changeColorBy(elem);
	     }
     }
   var bar = new ToolBar('taskGroupAailBar','排课组排课时间',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.clear"/>",clearAll,'update.gif');
</script>