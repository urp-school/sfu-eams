<#include "/templates/head.ftl"/>
<body>
 <#assign segs={'1':'上午','2':'中午','3':'下午','4':'晚上'}>
<table id="timeSettingInfoBar" width="100%"></table>
  <table width="100%"  class="listTable">
     <form name="calendarListForm" method="post" action="calendar.do" onsubmit="return false;">
     <input type="hidden" name="studentTypeIdSeq" value=""/>
     <tr  class="darkColumn">
       <td width="5%"><@bean.message key="attr.index"/></td>
       <td width="10%">时段</td>
       <td width="10%">名称</td>
       <td width="10%">英文名称</td>
       <td width="15%">起始时间</td>
       <td width="15%">结束时间</td>
     </tr>
     <#list timeSetting.courseUnits?sort_by("index") as courseUnit>
	  <#if courseUnit_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if courseUnit_index%2==0 ><#assign class="brightStyle"></#if>
     <tr class="${class}" onmouseover="swapOverTR(this,this.className)" 
         onmouseout="swapOutTR(this)" >
      <td>${courseUnit_index+1}</td>
      <td class="timeSeq${courseUnit.segNo}">${segs[courseUnit.segNo?string]}
      <td class="timeSeq${courseUnit.segNo}">${courseUnit.name}</td>
      <td>${courseUnit.engName}</td>
      <td>${courseUnit.startTime?string?left_pad(4,"0")[0..1]}:${courseUnit.startTime?string?left_pad(4,"0")[2..3]}</td>
      <td>${courseUnit.finishTime?string?left_pad(4,"0")[0..1]}:${courseUnit.finishTime?string?left_pad(4,"0")[2..3]}</td>
     </tr>
     </#list>    
  </table>
  <script>
     function update(){
         self.window.location="timeSetting.do?method=edit&timeSetting.id=${timeSetting.id}";
     }
     function remove(){
        if(confirm("删除该时间设置,其他的教学日历将失去参照?确定删除"))
          parent.window.location="timeSetting.do?method=remove&timeSetting.id=${timeSetting.id}";
     }
   var bar = new ToolBar('timeSettingInfoBar','上课时间小节对应表   ${timeSetting.name}(<#if timeSetting.stdType?exists >适合${timeSetting.stdType?if_exists.name?if_exists}<#else>系统默认</#if>)',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if inAuthority>
   bar.addItem('<@bean.message key="action.modify"/>',"javascript:update()",'update.gif');
   bar.addItem('<@bean.message key="action.delete"/>',"javascript:remove()",'delete.gif');
   </#if>
  </script>
</body>
<#include "/templates/foot.ftl"/>