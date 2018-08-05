<#include "/templates/head.ftl"/>
<BODY>
<table id="myBar" width="100%"></table>
  <@table.table id="listTable" width="100%" sortable="true">
     <@table.thead>
       <@table.selectAllTd id="signUpStdId"/>
       <@table.sortTd name="attr.stdNo" id="signUpStd.std.code"/>
       <@table.sortTd name="attr.personName" id="signUpStd.std.name"/>
       <@table.sortTd name="attr.enrollTurn" id="signUpStd.std.enrollYear"/>
       <@table.sortTd text="报名时间" id="signUpStd.signUpAt"/>
       <@table.sortTd text="平均绩点" id="signUpStd.GPA"/>
       <@table.sortTd text="服从调剂" id="signUpStd.isAdjustable"/>
       <@table.td text="录取方向"/>
     </@>
     <@table.tbody datas=signUpStds;signUpStd>
       <@table.selectTd id="signUpStdId" value=signUpStd.id/>
       <td><a href="studentDetailByManager.do?method=detail&stdId=${signUpStd.std.id}" title="查看学生基本信息">${signUpStd.std.code}</A></td>
       <td><A href="#" onclick="signUpStdInfo(${signUpStd.id})" title="查看该学生报名详情"><@i18nName signUpStd.std/></A></td>
       <td>${signUpStd.std.enrollYear}</td>
       <td>${(signUpStd.signUpAt?string('yyyy-MM-dd'))?if_exists}</td>
       <td>${signUpStd.GPA}</td>
       <td>${signUpStd.isAdjustable?string("是","否")}</td>
       <td><@i18nName (signUpStd.matriculated.aspect)?if_exists/></td>
     </@>
  </@>
  <@htm.actionForm name="actionForm" action="speciality2ndSignUpStudent.do" entity="signUpStd">
    <input type="hidden" name="signUpStd.setting.id" value="${RequestParameters['signUpStd.setting.id']}"/>
  </@>
  <script>
     var bar =new ToolBar("myBar","报名列表",null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem("查看详情","signUpStdInfo()");
     bar.addItem("重算绩点","multiAction('reCalcGPA','是否重新计算选中学生的报名绩点?')");
     bar.addItem("<@msg.message key="action.delete"/>","remove()","delete.gif","直接删除记录，不影响录取结果");
     bar.addItem("添加","addSignUp()");
     bar.addItem("<@msg.message key="action.export"/>","exportData()");
     bar.addPrint("<@msg.message key="action.print"/>");
     var form=document.actionForm;
     var action="speciality2ndSignUpStudent.do";
     function exportData(){
        if(confirm("是否导出查询出的${totalSize}条报名记录?")){
	        addInput(form,"keys",'std.code,std.name,<#list 1..signUpSetting.choiceCount as i>record[${i}].specialitySetting.aspect.name,record[${i}].specialitySetting.aspect.speciality.subjectCategory.name,</#list>GPA,isAdjustable,isMatriculated,matriculated.aspect.name,signUpAt,std.department.name,std.firstMajor.name,std.firstMajor.subjectCategory.name,std.firstMajorClass.name,std.basicInfo.homeAddress,std.basicInfo.phone,std.basicInfo.idCard');
	        addInput(form,"titles",'学号,姓名,<#list 1..signUpSetting.choiceCount as i>第${i}志愿,第${i}志愿所属学科,</#list>平均绩点,服从调剂,是否录取,录取专业方向,报名时间,主修专业院系,主修专业,主修专业学科,主修专业班级,联系地址,联系电话,身份证号');
	        form.action=action+"?method=export";
	        form.submit();
        }
     }
     function addSignUp(){
        addInput(form,"params",queryStr);
        form.action=action+"?method=addSignUp";
        form.submit();
     }
    
    function signUpStdInfo(id) {
    	if (null == id || "" == id) {
    		info();
    	} else {
    		form.action = "speciality2ndSignUpStudent.do?method=info";
    		addInput(form, "signUpStdId", id, "hidden");
    		form.submit();
    	}
    }
  </script>
</body>
<#include "/templates/foot.ftl"/> 