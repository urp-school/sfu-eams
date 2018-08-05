<#include "/templates/head.ftl"/>
<body>
 
 <#assign segs={'1':'上午','2':'中午','3':'下午','4':'晚上'}>
 <#macro displaySeq(index,unitSeg)>
   <select name="${index}segNo" style="width:100%" >
      <#list segs?keys as seg>
         <option value="${seg}" <#if seg==unitSeg>selected</#if>>${segs[seg]}</option>
      </#list>
   </select>
 </#macro>
 
   <table  width="100%"  >
   <form name="timeSettingForm" action="timeSetting.do?method=save" method="post" onsubmit="return false;">
   <input type="hidden" name="timeSetting.id" value="${timeSetting.id?if_exists}">
    <tr>
      <td  class="infoTitle" align="left"  style="height:22px;width:500px" >
       <img src="${static_base}/images/action/info.gif" />
          <B>上课时间小节对应表:名称<font color="red">*</font><input type="text" name="timeSetting.name" maxlength="20" value="${timeSetting.name?if_exists}">适合
           <select name="timeSetting.stdType.id" >
           <#if !timeSetting.stdType?exists>
              <option value="" selected>全部</option>
           </#if>
           <#list stdTypeList as stdType>
              <option value="${stdType.id}"<#if stdType.id?string==timeSetting.stdType?if_exists.id?if_exists?string>selected</#if> ><@i18nName stdType/></Option>
           </#list>
           </select>
      </td>
      <td></td>
      <td  class="padding" style="font-size:10pt"onclick="javascript:save()" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          <img src="${static_base}/images/action/save.gif" class="iconStyle"  /><@bean.message key="action.save"/>
      </td>
      <td  class="padding" style="font-size:10pt"onclick="javascript:history.back()" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          <img src="${static_base}/images/action/backward.gif" class="iconStyle"  /><@bean.message key="action.back"/>
      </td>
    <tr>
      <td colspan="8" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
  </table>	
  <table width="100%"  class="listTable">
     <tr  class="darkColumn">
       <td width="5%"><@bean.message key="attr.index"/></td>
       <td width="15%">时段</td>
       <td width="20%">名称</td>
       <td width="10%">起始时间</td>
       <td width="10%">结束时间</td>
       <td width="20%">英文名称</td>
     </tr>
     <#list timeSetting.courseUnits?sort_by("index") as courseUnit>
     <tr class="infoTitle">
      <input type="hidden" name ="${courseUnit_index}index" value="${courseUnit.index}">
      <td>${courseUnit_index+1}<input type="hidden" name="${courseUnit_index}id" value="${courseUnit.id?if_exists}"><input type="hidden" name="${courseUnit_index}index" value="${courseUnit.index?if_exists}"></td>
      <td><@displaySeq courseUnit_index,courseUnit.segNo?string/></td>
      <td><input type="text" maxLength="10" style="border:1 solid #000000;width:100%" name="${courseUnit_index}name" value="${courseUnit.name?if_exists}"></td>
      <#assign startTime="">
      <#if courseUnit.startTime?exists>
        <#assign startTime>${courseUnit.startTime?string?left_pad(4,"0")[0..1]}:${courseUnit.startTime?string?left_pad(4,"0")[2..3]}</#assign>
      </#if>
      <td><input type="text" maxLength="5" style="border:1 solid #000000;width:100%" name="${courseUnit_index}startTime" value="${startTime}"></td>
      <#assign finishTime="">
      <#if courseUnit.finishTime?exists>
        <#assign finishTime>${courseUnit.finishTime?string?left_pad(4,"0")[0..1]}:${courseUnit.finishTime?string?left_pad(4,"0")[2..3]}</#assign>
      </#if>
      <td><input type="text" maxLength="5" style="border:1 solid #000000;width:100%" name="${courseUnit_index}finishTime" value="${finishTime}"></td>
      <td><input type="text" maxLength="50" style="border:1 solid #000000;width:100%" name="${courseUnit_index}engName" value="${courseUnit.engName?if_exists}"></td>
     </tr>
     </#list>
     </form>
  </table>
  <script>
     function save(){
         if(check()){
           <#if !(timeSetting.id?exists)>
            document.timeSettingForm.target="_parent"
           </#if>
            document.timeSettingForm.submit();
         }
     }
     function check(){
         var form = document.timeSettingForm;
         var errorMsgs =""
         if(form['timeSetting.name'].value.trim()==""){
            alert("上课时间设置的名称不能为空");
            return false;
         }
            
         for(var i=0;i<${timeSetting.courseUnits?size};i++){
            var startTime = form[i+'startTime'].value;
            errorMsgs =checkATime(startTime);
            if(""!=errorMsgs) {
              alert(errorMsgs);return false;
            }
            var finishTime =form[i+'finishTime'].value;
            errorMsgs =checkATime(finishTime);
            if(""!=errorMsgs) {
              alert(errorMsgs);return false;
            }
            if(finishTime<=startTime) {alert("第"+(i+1)+"小节起始时间不得超过或等于结束时间");return false;}
            
            var lastFinishTime="00:00"
            if(i>0){
               lastFinishTime=form[(i-1)+'finishTime'].value;
               if(lastFinishTime>startTime) {alert("第"+(i+1)+"小节起始时间不得晚于或等于上一节的结束时间");return false;}
            }
            if(form[i+'name'].value.trim()==""){alert("第"+(i+1)+"小节名称不能为空");return false;}
            if(form[i+'engName'].value.trim()==""){alert("第"+(i+1)+"小节英文名称不能为空");return false;}
         }
         return true;
     }
     function checkATime(time){
       
        if(!(/^[0-2][0-9]:[0-6][0-9]$/.test(time))){
          return time+"的格式不正确,应为hh:mm的二十四小时时间格式";
        }
        var timeSplits =time.split(":");
        var hour=timeSplits[0];
        var minute =timeSplits[1];
        if(parseInt(hour)>=24) {return time+"中的小时不能超过23";}
        if(parseInt(minute)>=60){return time+"中的分钟不能超过59";}
        return "";
     }
  </script>
</body>
<#include "/templates/foot.ftl"/>