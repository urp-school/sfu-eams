  <table id="courseGroupListBar"></table>
  <script>
    var bar= new ToolBar("courseGroupListBar","课程组列表",null,true,true);
    bar.addItem("<@bean.message key="action.add"/>","add()");
    bar.addItem("<@bean.message key="action.modify"/>","edit()");
    bar.addItem("<@bean.message key="action.delete"/>","remove()");
  </script>
 
  <table width="100%" align="center" class="listTable" >
    <tr align="center" class="darkColumn">
      <td width="2%"></td>
      <td width="25%"><@msg.message key="entity.courseType"/></td>
      <td width="10%">要求学时</td>
      <td width="10%">要求学分</td>
      <td width="25%">学分分布</td>
      <td width="25%">周课时分布</td>
    </tr>
       <#assign total_credit=0 />
       <#assign total_creditHour=0 />
       <#assign total_term_credit={} />
       <#assign total_term_weekHour={} />
       
       <#list 1..teachPlan.termsCount as i >
        <#assign total_term_credit=total_term_credit + {i:0} />
       </#list>
       <#list 1..teachPlan.termsCount as i >
        <#assign total_term_weekHour=total_term_weekHour + {i:0} />
       </#list>
       
    <#list teachPlan.courseGroups?sort_by(["courseType","id"]) as group>
	  <#if group_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if group_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center"
        onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" 
        onclick="onRowChange(event)">
     <td  class="select"><input type="radio" name="courseGroupId" value="${group.id?if_exists}"></td>
     <td>
         <#if group.parentCourseType?exists&&group.parentCourseType.id!=group.courseType.id><@i18nName group.parentCourseType/>/</#if><@i18nName group.courseType/>
     </td>
     <td>&nbsp;${group.creditHour}</td>
     <td>&nbsp;${group.credit}</td>
    <#assign creditPerTerms="">
    <#assign total_creditHour=total_creditHour+group.creditHour/>
    <#assign total_credit=total_credit+group.credit/>
    <#assign i=1>
    <#list group.creditPerTerms[1..group.creditPerTerms?length-2]?split(",") as credit>
	    <#assign current_totle=total_term_credit[i?string]?if_exists/>
        <#assign total_term_credit=total_term_credit + {i:current_totle+credit?number} />       
	    <#assign i=i+1>
	    <#assign creditPerTerms=creditPerTerms+credit?left_pad(3," ")/>
	</#list>
	<td>${creditPerTerms?replace(" ","&nbsp;")}</td>
	<#assign weekHourPerTerms="">
    <#assign i=1>
    <#list group.weekHourPerTerms[1..group.weekHourPerTerms?length-2]?split(",") as weekHour>
	    <#assign current_totle=total_term_weekHour[i?string]?if_exists/>
        <#assign total_term_weekHour=total_term_weekHour + {i:current_totle+weekHour?number} />       
	    <#assign i=i+1>
	    <#assign weekHourPerTerms=weekHourPerTerms+weekHour?left_pad(3," ")/>
    </#list>
     <td>${weekHourPerTerms?replace(" ","&nbsp;")}</td>
    </tr>
    </#list>
    <tr class="brightStyle" align="center">
       <td  colspan="2"><@bean.message key="attr.cultivateScheme.allTotle"/></td>
       <td >${total_creditHour}</td>
       <td >${total_credit}</td>
	   <td><#list 1..teachPlan.termsCount as i>${total_term_credit[i?string]?string?left_pad(3)}</#list></td>
	   <td><#list 1..teachPlan.termsCount as i>${total_term_weekHour[i?string]?string?left_pad(3)}</#list></td>	
    </tr>
  <form name="courseGroupForm" method="post" action="" onsubmit="return false;">
      <input type="hidden" name="teachPlan.id" value="${RequestParameters['teachPlan.id']}">
      <input type="hidden" name="courseGroup.id" value="">
  </form>
  </table>

  <script>
     function selectId(){
       var id = (getRadioValue(document.getElementsByName("courseGroupId")));
       if(id==""){alert("请选择一个");return false;}
       else
          courseGroupForm['courseGroup.id'].value=id;
       return true;
     }
     function add(){
         courseGroupForm.action="courseGroup.do?method=add";
         courseGroupForm.submit();
     }
     function edit(){
         if(selectId()){
             var url="courseGroup.do?method=edit&teachPlan.id=${RequestParameters['teachPlan.id']}&courseGroup.id="+courseGroupForm['courseGroup.id'].value;
             MakeFull(url,"yes");
         }
     }
     function remove(){
         if(selectId()&&confirm("删除培养计划课程组，确认？")){
	         courseGroupForm.action="courseGroup.do?method=removeGroup";
	         courseGroupForm.submit();
         }
     }
  </script>