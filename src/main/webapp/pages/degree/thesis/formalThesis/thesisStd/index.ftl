<#include "/templates/head.ftl"/>
<#if editType="home"><#assign display=true><#else><#assign display=false></#if>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<script>
<#if !display>
    function save(form){
     var a_fields = {
         'thesis.name':{'l':'论文题目', 'r':true, 't':'f_name', 'mx':200},
         'thesis.keyWords':{'l':'论文主题词', 'r':true, 't':'f_keyWords', 'mx':200},
         'thesis.abstract_cn':{'l':'中文摘要', 'r':true, 't':'f_abstract_cn','mx':8000},
         'thesis.abstract_en':{'l':'英文摘要', 'r':true, 't':'f_abstract_en','mx':8000},
         'thesis.startOn':{'l':'论文开始时间', 'r':true, 't':'f_startOn'},
         'thesis.thesisSource.id':{'l':'论文来源', 'r':true, 't':'f_thesisSource'},
         'thesis.thesisType.id':{'l':'论文类型', 'r':true, 't':'f_thesisType'},
         'thesis.thesisNum':{'l':'论文字数', 'r':true, 'f':'unsignedReal','t':'f_thesisNum','mx':10},
         'thesis.endOn':{'l':'论文结束时间', 'r':true,'t':'f_endOn'},
        'thesis.remark':{'l':'备注','t':'f_remark', 'mx':500}
     };
     var v = new validator(form, a_fields, null); 
      if (v.exec()&&confirm("你确定要提交你的论文信息吗??")) {   
        form.action="thesisStd.do?method=saveThesis";
        form.submit();
       }    
   }
  <#else>
  function modify(form){
  	form.action="thesisStd.do?method=edit";
  	form.submit();
  }
  function upload(form){
    if(<#if (myThesis.id)?exists>false<#else>true</#if>){
    	alert("你还没有提交我的论文信息,需要在提交论文信息以后才能上传信息");
    	return;
    }
  	form.action="thesisStd.do?method=loadUploadPage";
  	addInput(form,"thesisId","06");
  	addInput(form,"thesisManageId","${(thesisManage.id)?if_exists}");
  	form.submit();
  }
  </#if>
</script>

<body>
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','我的论文',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if editType="home">
   bar.addItem("修改","modify(document.listForm)");
   bar.addItem("论文上传","upload(document.listForm)");
   bar.addPrint();
   <#else>
   bar.addItem("保存","save(document.listForm)");
   bar.addBack();
   </#if>
</script>
<#include "thesisForm.ftl"/>
</body>
<#include "/templates/foot.ftl"/>