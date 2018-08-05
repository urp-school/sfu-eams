<#include "/templates/head.ftl"/>
<#include "/pages/course/grade/gradeMacros.ftl"/>
<body onload="SetPrintSettings()">
 	<table id="myBar"></table>
 	<script>
   		var bar = new ToolBar("myBar","<@msg.message key="common.personGradeTablePrint"/>${RequestParameters["reportSetting.gradePrintType"]?if_exists}",null,true,true);
   		bar.addPrint("<@msg.message key="action.print"/>");
   		bar.addClose();
 	</script>
 	<#if RequestParameters['request_locale']?default('zh_CN')=='zh_CN'>
     	<#include "template/" + (setting.template)?default('default') + "_zh.ftl"/>
    <#else>
        <#include "template/" + (setting.template)?default('default') + "_en.ftl"/>
    </#if>
 	
</body>
<#include "/templates/foot.ftl"/>
 	<object id="factory" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="css/smsx.cab#Version=6,2,433,14"></object> 
<script>
 function window.onload(){
       try{
           if(typeof factory.printing != 'undefined'){ 
             factory.printing.header = ""; 
             factory.printing.footer = "";
             
                 // -- advanced features
                //factory.printing.SetMarginMeasure(2); // measure margins in inches
                //factory.printing.printer = "HP DeskJet 870C";
                factory.printing.paperSize = "A3";
                //factory.printing.paperSource = "Manual feed";
                //factory.printing.collate = true;
                //factory.printing.copies = 2;
                //factory.printing.SetPageRange(false, 1, 3); // need pages from 1 to 3
               
                // -- basic features
                //factory.printing.header = "This is MeadCo";
                //factory.printing.footer = "Printing by ScriptX";
                //factory.printing.portrait = true;
                factory.printing.leftMargin = 10.0;
                factory.printing.topMargin = 10.0;
                factory.printing.rightMargin = 10.0;
                factory.printing.bottomMargin = 10.0;
           }
       }catch(e){
           
       }
    }
</script>
