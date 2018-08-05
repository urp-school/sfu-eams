<#include "/templates/head.ftl"/>
 <BODY> 
 <table id="backBar" width="100%"></table>
    <#include RequestParameters['productType'] + "List.ftl"/>
    <table>
        <tr height="200"><td></td></tr>
    </table>
    <@htm.actionForm name="actionForm" action="studyProduct.do" entity="studyProduct" onsubmit="return false;">
        <input type="hidden" name="productType" value="${RequestParameters['productType']}"/>
        <input type="hidden" name="excelType" value=""/>
        <input type="hidden" name="template" value=""/>
        <#list RequestParameters?keys as key>
          <#if key?starts_with("studyProduct.")>
            <input type="hidden" name="${key}" value="${RequestParameters[key]}"/>
          </#if>
        </#list>
        <input type="hidden" id="keys" name="keys" value="student.type.name,student.department.name,student.code,student.name,isPassCheck"/>
        <input type="hidden" id="titles" name="titles" value="<@msg.message key="entity.studentType"/>,<@msg.message key="entity.department"/>,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,通过审核"/>
    </@>
    <script>
       var bar = new ToolBar('backBar','查询结果列表',null,true,true);
       bar.setMessage('<@getMessage/>');
       bar.addItem("<@msg.message key="action.info"/>","info()", "detail.gif");
       bar.addItem("<@msg.message key="action.add"/>","add()");
       bar.addItem("<@msg.message key="action.edit"/>","edit()");
       bar.addItem("批量修改","batchEdit()");
       bar.addItem("<@msg.message key="action.delete"/>","multiIdAction('remove')");
       bar.addItem("添加获奖","addAwardInfo()");
       var menu1 =bar.addMenu("<@msg.message key="action.export"/>","exportObject()");
       menu1.addItem('导出单个学生科研情况表','lookFor()', "excel.png");
       var menu2 =bar.addMenu("高级设置");
       menu2.addItem("审核通过","multiIdAction('check&isPassCheck=1')")
       menu2.addItem("取消通过","multiIdAction('check&isPassCheck=0')");
       menu2.addItem("<@msg.message key="action.print"/>","print()");
        function batchEdit(){
           form.action=action+"?method=batchEdit";
           submitId(form,"studyProductId",true);
        }
        
        function exportObject(){
           form.action = action + "?method=export";
           form["excelType"].value = "excelList";
           form["template"].value = "";
           exportList();
        }
        
        function lookFor(){
            form["excelType"].value = "studyInfoStatistic";
            form["template"].value = "studyInfoStatistic.xls";
            submitId(form, "studyProductId", false, "studyProduct.do?method=export");
        }

        function addAwardInfo(){
            form.action =action+"?method=editAward";
            setSearchParams(parent.document.searchForm,form);
            addInput(form,"productType","${RequestParameters['productType']}");
            submitId(form,"studyProductId",false);
        }
        orderBy =function(what){
            parent.search(1,${pageSize},what);
        }
        function pageGoWithSize(pageNo,pageSize){
           parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
        }
        var keyTitleObject ={
            "studyThesisTitle":",论文题目,刊物名称,刊物等级,期刊号,发表时间,本人独立完成字数(万),科研排序,备注",
            "studyThesisKey":",name,publicationName,publicationLevel.name,publicationNo,publishOn,wordCount,rate,remark",
            "literatureTitle":",著作名称,著作分类,出版时间,本人独立完成字数(万),科研排序,备注",
            "literatureKey":",name,literatureType.name,publishOn,wordCount,rate,remark",
            "projectTitle":",项目名称,立项编号,立项单位,立项日期,结项日期,承担任务,责任人,项目类别,本人独立完成字数(万),科研排序,备注",
            "projectKey":",name,projectNo,company,startOn,endOn,bearTask,principal,projectType.name,wordCount,rate,remark",
            "studyMeetingTitle":",会议名称,会议类别,获邀论文题目,会议时间,会议地点,举办单位,备注",
            "studyMeetingKey":",name,meetingType.name,topicName,meetingOn,meetingAddress,openDepart,remark"
        };
        var key= document.getElementById("keys");
        key.value+=keyTitleObject["${RequestParameters['productType']}Key"]+",isAwarded,isPassCheck";
        var title= document.getElementById("titles");
        title.value+=keyTitleObject["${RequestParameters['productType']}Title"]+",是否获奖成果,通过审核";
    </script>
</body>
<#include "/templates/foot.ftl"/>
    