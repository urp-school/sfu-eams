<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body  >
<#assign labInfo>为培养计划新建一类课程</#assign> 
<#include "/templates/back.ftl"/>
  <table class="listTable" align="center" width="80%">
          <form name="courseGroupForm" action="courseGroupMamager.do?method=save" method="post" onsubmit="return false;">
          <tr align="center" class="darkColumn">
             <td colspan="4">新建课程组</td>
          </tr>
          <tr>
             <td class="grayStyle" id="f_courseTypeId" width="20%"><@msg.message key="entity.courseType"/>:</td>
             <td class="brightStyle">
               <select name="courseGroup.courseType.id" style="width:150px">
               <#list unusedCourseTypeList?sort_by("name") as courseType>
                   <option value="${courseType.id}">${courseType.name}</option>
               </#list>
               </select>
             </td>
             <td class="grayStyle" width="20%">上级类别:</td>
             <td class="brightStyle">
               <select name="courseGroup.parentCourseType.id" style="width:150px">
                   <option id=""></option>
               <#list couseTypeList?sort_by("name") as courseType>
                   <option value="${courseType.id}">${courseType.name}</option>
               </#list>
               </select>
             </td>
          </tr>
          <tr>
             <td class="grayStyle" id="f_credit">要求总学分:</td>
             <td class="brightStyle"><input type="text" name="courseGroup.credit" value="0" style="width:60px" maxlength="3"/></td>
             <td class="grayStyle" id="f_creditHour">要求总学时:</td>
             <td class="brightStyle"><input type="text" maxlength="3" name="courseGroup.creditHour" value="0" style="width:60px"></td>
          </tr>
          <tr>
             <td class="grayStyle">学期:</td>
             <td class="brightStyle" colspan="3">
             <table class="grayStyle" style="width:100%;"cellpadding="0" cellspacing="0">
                 <tr>
                  <#list 0..teachPlan.termsCount-1 as term>
                 <td id="f_term${term+1}">${term+1}</td>
                 </#list>
                 </tr>
             </table>
             </td>
          </tr>
          <tr>
             <td class="grayStyle">学分分布:</td>
             <td class="brightStyle" colspan="3">
             <table class="grayStyle" style="width:100%;"cellpadding="0" cellspacing="0">
                 <tr>
                 <#list 0..teachPlan.termsCount-1 as term>
                 <td><input type="text" name="credit${term_index}" style="width:100%" value="0"></td>
                 </#list>
                 </tr>
             </table>
             </td>
          </tr>
          <tr>
             <td class="grayStyle">周课时分布:</td>
             <td class="brightStyle" colspan="3">
             <table class="grayStyle" style="width:100%;"cellpadding="0" cellspacing="0">
                 <tr>
                 <#list 0..teachPlan.termsCount-1 as term>
                 <td><input type="text" name="weekHour${term_index}" style="width:100%" value="0" maxlength="3"/></td>
                 </#list>
                 </tr>
             </table>
             </td>
          </tr>
          <tr>
             <td class="darkColumn" align="center" colspan="4">
               <input type="button" onClick='saveNewGroup(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
               <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
               <input type="hidden" name="courseGroup.creditPerTerms" value=""/>
               <input type="hidden" name="courseGroup.weekHourPerTerms" value=""/>
               <input type="hidden" name="teachPlan.id" value="${teachPlan.id}"/>
             </td>             
          </tr>
          </form>
      </table>
  </div>
  <script>
      function saveNewGroup(form){
           var a_fields = {
           <#list 0..teachPlan.termsCount-1 as term>
             'credit${term_index}':{'l':'第${term+1}学期学分', 'r':true, 't':'f_term${term+1}','f':'unsignedReal'},
             'weekHour${term_index}':{'l':'第${term+1}学期周课时', 'r':true, 't':'f_term${term+1}','f':'unsigned'},
           </#list>           
         'courseGroup.creditHour':{'l':'要求总学时', 'r':true, 't':'f_creditHour','f':'unsigned'},
         'courseGroup.credit':{'l':'要求总学分','r':true,'t':'f_credit','f':'unsignedReal'},
         'courseGroup.courseType.id':{'l':'<@msg.message key="entity.courseType"/>','r':true,'t':'f_courseTypeId'}
       };
       var v = new validator(form , a_fields, null);
       if (v.exec()) {         
         var creditPerTerms =",";
         var weekHourPerTerms =",";
         var credits=0;
         for(var i=0;i<${teachPlan.termsCount};i++){
            creditPerTerms+=form['credit'+i].value+",";
            weekHourPerTerms+=form['weekHour'+i].value+",";
            credits +=new Number(form['credit'+i].value);
         }
         form['courseGroup.creditPerTerms'].value=creditPerTerms;
         form['courseGroup.weekHourPerTerms'].value=weekHourPerTerms;
         if(credits!=new Number(form['courseGroup.credit'].value)){
           if(!confirm("总学分学分分布的各个值不吻合\n总学分为:"+form['courseGroup.credit'].value+"\n学分分布中的总和为："+credits+"\n是否提交保存?"))
           return;
         }
         form.action="courseGroup.do?method=save";
         form.submit();
        }
      }
  </script>
  </body>