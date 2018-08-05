<#include "/templates/head.ftl"/> 
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
   <table id="feeBar" width="100%"></table>
     <table align="left" width="100%" class="listTable" >
	   <tr align="center" class="darkColumn">
	     <td>收费类型</td>
	     <td>收费方式</td>
	     <td>币种</td>
	     <td>应缴</td>
	     <td>实缴</td>
	     <td>发票</td>
	     <td>缴费日期</td>
	   </tr>
	   <#list fees as fee>
	   <#if fee_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if fee_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)"
	    onmouseout="swapOutTR(this)" align="center"
	    onclick="onRowChange(event)">
        <td ><@i18nName fee.type/></td>
        <td ><@i18nName fee.mode/></td>
        <td ><@i18nName fee.currencyCategory/></td>
        <td>${fee.shouldPay?if_exists}</td>
        <td>${fee.payed?if_exists}</td>
        <td>${fee.invoiceCode?if_exists}</td>
        <td>${fee.createAt?string("yyyy-MM-dd")}</td>
	   </tr>
	   </#list>
     </table>
   <table id="courseBar" width="100%"></table>
     <table align="left" class="listTable" width="100%">
	   <tr align="center" class="darkColumn">
	     <td><@msg.message key="attr.taskNo"/></td>
	     <td><@msg.message key="attr.courseNo"/></td>
	     <td><@msg.message key="attr.courseName"/></td>
	     <td><@msg.message key="attr.credit"/></td>
	     <td>修读类别</td>
	   </tr>
	   <#list courseTakes as take>
	   <#if take_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if take_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)"
	    onmouseout="swapOutTR(this)" align="center"
	    onclick="onRowChange(event)">
	    <td >${take.task.seqNo}</td>
        <td >${take.task.course.code}</td>
        <td ><@i18nName take.task.course/></td>
        <td >${take.task.course.credits}</td>
        <td ><@i18nName take.courseTakeType/></td>
	   </tr>
	   </#list>
     </table>

  <script>
   var bar = new ToolBar('feeBar','&nbsp;${std.name}[${std.code}]的缴费信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack("<@msg.message key="action.back"/>")
   var bar = new ToolBar('courseBar','&nbsp;学生选课数据',null,true,true);
   bar.setMessage('<@getMessage/>');
  </script>
 </body>
<#include "/templates/foot.ftl"/>