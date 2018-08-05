<#include "/templates/head.ftl"/>
<#assign labInfo>教学日历详细信息</#assign>
<#include "/templates/back.ftl"/>
<body>
 <table width="90%"  align="center" class="formTable">
   <form name="calendarForm" action="calendar.do?method=saveCalendar" method="post" onsubmit="return false;">
   <tr class="darkColumn">
     <td  colspan="4">教学日历详细信息</td>
   </tr>   
   <#if calendar.previous?exists>
   <tr>
	   <input name="preCalendar.id" value="${calendar.previous.id}" type="hidden"/>
	   <input name="preCalendar.firstYear" value="${calendar.previous.year?string[0..3]}" type="hidden"/>
       <td class="title" width="25%" id="f_name">&nbsp;上个学期：</td>  
       <td colspan="3">${calendar.previous.year}&nbsp;:&nbsp;${calendar.previous.term}</td> 	
   </tr>
   </#if>
   <#if calendar.next?exists>
   <tr>
	   <input name="nextCalendar.id" value="${calendar.next.id}" type="hidden"/>
	   <input name="nextCalendar.firstYear" value="${calendar.next.year?string[0..3]}" type="hidden"/>
       <td class="title" width="25%" id="f_name">&nbsp;下个学期：</td>  
       <td colspan="3">${calendar.next.year}&nbsp;:&nbsp;${calendar.next.term}</td> 	
   </tr>
   </#if>
   <tr>
         <input type="hidden" value="${calendar.id?if_exists}" name="calendar.id"/>
         <input type="hidden" value="${RequestParameters['scheme.id']}" name="scheme.id"/>
 	     <td class="title" width="25%" id="f_name" >&nbsp;时间设置：</td>  
 	     <#assign beenSelectSchemeTime = (calendar.scheme.timeSetting.id)?if_exists/>
 	     <#if !beenSelectSchemeTime?exists || "" == beenSelectSchemeTime?string>
 	     	<#assign beenSelectSchemeTime = (scheme.timeSetting.id)?if_exists/>
 	     </#if>
 	     <#if !beenSelectSchemeTime?exists || "" == beenSelectSchemeTime?string>
	 	     <#assign beenSelectSchemeTime = (calendar.timeSetting.id)?if_exists/>
 	     </#if>
 	     <td colspan="3"><@htm.i18nSelect datas=timeSettingList selected=(beenSelectSchemeTime?string)?default("") name="calendar.timeSetting.id"><option value="">...</option></@>
 	       <input type="checkbox" name="calendar.displayTimeDetail" <#if calendar.displayTimeDetail?default(true)>checked</#if>>在课表中显示
 	     </td>
   </tr>
   <tr>
 	     <td class="title" width="25%" id="f_name">&nbsp;<@bean.message key="attr.year2year"/><font color="red">*</font>：</td> 	     
 	     <td ><input type="text" name="calendar.year" maxlength="9" value="${calendar.year?if_exists}" /></td>
 	     <td class="title" width="25%" id="f_name">&nbsp;<@bean.message key="attr.term"/><font color="red">*</font>：</td> 	     
 	     <td ><input maxlength="10" type="text" name="calendar.term" maxlength="10" value="${calendar.term?if_exists}" /></td>
   </tr>
   <tr>
 	     <td class="title" width="25%" id="f_name">&nbsp;开始日期<font color="red">*</font>：</td> 	     
 	     <td >
 	         <input type="text" name="calendar.start" maxlength="10" value="${(calendar.start?string("yyyy-MM-dd"))?if_exists}" onfocus="calendar()"/>
 	     </td>
 	     <td class="title" width="25%" id="f_name">&nbsp;结束日期<font color="red">*</font>：</td> 	     
 	     <td >
 	         <input type="text" name="calendar.finish"  onchange="fillWeeks()" maxlength="10" value="${(calendar.finish?string("yyyy-MM-dd"))?if_exists}" onfocus="calendar()"/ />         
 	     </td>
   </tr>
   <tr>
     <td class="title" width="25%" id="f_teachDepart">&nbsp;上课周数<font color="red">*</font>：</td>
     <td >
      <input type="text" maxlength="3" name="calendar.weeks" value="${calendar.weeks?if_exists}" />
     </td>
     <td class="title" width="25%" id="f_teachDepart">是否小学期<font color="red">*</font>：</td>
     <td>
       <select name="calendar.isSmallTerm">
         <option value="0">不是</option>
         <option value="1" <#if calendar.isSmallTerm?if_exists>selected</#if>>是</option>
       </select>
     </td>
   </tr>
   <tr class="darkColumn" align="center">
     <td colspan="6"  >
       <input type="button" value="<@bean.message key="action.submit"/>" name="saveButton" onClick="save(this.form)" class="buttonStyle" />&nbsp;
       <input type="reset"  name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle" />
     </td>
   </tr>
    </form>
 </table>
 <blockquote>
  <pre>
  时间设置:是指该学期中,每个小节的起始和结束时间.
          它通过"是否在课表中显示"来控制,是否在该日历对应的所有课表中显示该时间设置.
  开始日期:请选择星期天,周数是以星期天作为第一天计算的.
  <font color="red">请在排课和排考之前核准该日期.</font>
 </pre></blockquote>
<p></p>
<script>
	function save(form){
	    var errors = "";
	    // check year
        var year = form['calendar.year'].value;
	    if(!/^\d{4}-\d{4}$/.test(year)) {alert("学年度格式应为yyyy-yyyy\n");return;}
	    var twoYear = year.split("-");
	    if(twoYear[1]-twoYear[0]!=1)errors+="学年度中开始年份应比结束年份早一年!\n";
	    <#if calendar.previous?exists>
        if(twoYear[0]-form['preCalendar.firstYear'].value>1) errors+="该学期和上个学期学年度设置不连续!\n";
        </#if>
	    <#if calendar.next?exists>
        if(form['nextCalendar.firstYear'].value-twoYear[0]>1) errors+="该学期和下个学期学年度设置不连续!\n";
        </#if>
        // check term
        if(""==form['calendar.term'].value) errors+="学期名称不能为空!\n";
        
        // check start and finish date
	    var start = form['calendar.start'].value;
	    var finish =form['calendar.finish'].value;	     
  	    if(start=="") errors+="起始日期不能为空!\n";
  	    if(finish=="") errors+="结束日期不能为空!\n";  	    
  	    if(!isDateBefore(start,finish)) errors+="结束日期不能早于开始日期\n";
  	    if(errors!="") {alert(errors);return;}
  	    if(start.substring(0,start.indexOf("-"))-twoYear[0]>1 ||
  	       start.substring(0,start.indexOf("-"))-twoYear[0]<0 ) errors+="开始日期和学年度年份不符!\n";
  	    
  	    // check weeks
  	    if(!/^\d+$/.test(form['calendar.weeks'].value)) errors+="上课周数应为正整数!\n";
  	    var weeks = calcWeeks(start,finish);  	    
  	    if(weeks<form['calendar.weeks'].value)
  	    errors +="给定日期之间的周数为" + weeks +",应大于输入的上课周数\n";
  	    if(form['calendar.weeks'].value>53)errors+="开始、结束日期之间不能超过一年!"
        
        var startDateInfo = start.split("-");
  	    var sun = new Date(startDateInfo[0],startDateInfo[1]-1,startDateInfo[2]);
        if(sun.getDay()!=0) errors+="开始日期应以星期日开始";
  	    if(errors!="") {alert(errors);return;}
  	    form.submit();
	}
	
	function calcWeeks(start,finish){
	    var startDateInfo = start.split("-");
  	    var finishDateInfo = finish.split("-");
  	    var startDate = new Date(startDateInfo[0],startDateInfo[1]-1,startDateInfo[2]);
  	    var finishDate= new Date(finishDateInfo[0],finishDateInfo[1]-1,finishDateInfo[2]);
  	    
  	    var diff = finishDate.getTime()- startDate.getTime();
  	    var weeks = Math.ceil(diff/(1000*60*60*24*7));
  	    return weeks+1;
	}
	function fillWeeks(){
	    var start = document.calendarForm['calendar.start'].value;
	    var finish = document.calendarForm['calendar.finish'].value;
	    if(start==""||finish=="")
	    	return;	    
	    document.calendarForm['calendar.weeks'].value=calcWeeks(start,finish);
	    document.calendarForm['calendar.weeks'].select=true;
	}
    function isDateBefore(first,second){
       var firstYear = first.substring(0,4);
       var secondYear = second.substring(0,4);
       if(firstYear>secondYear) return false;
       else if(firstYear<secondYear) return true;

       var firstMonth = new Number(first.substring(first.indexOf('-')+1,first.lastIndexOf('-')));
       var secondMonth = new Number(second.substring(second.indexOf('-')+1,second.lastIndexOf('-')));
       if(firstMonth>secondMonth) return false;
       else if(firstMonth<secondMonth) return true;
       
       var firstDay = new Number(first.substring(first.lastIndexOf('-')+1));
       var secondDay = new Number(second.substring(second.lastIndexOf('-')+1));
       if(firstDay>secondDay) return false;
       else if(firstDay<secondDay) return true;
       return false;
   }
</script>
</body>

<#include "/templates/foot.ftl"/>