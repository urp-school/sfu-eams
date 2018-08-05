<#include "/templates/head.ftl"/>
<BODY>
<table id="myBar" width="100%"></table>
  <pre>
    录取规则:
      对于所有报名未录取的学生，找出绩点最高的学生，进行录取他的第一个未录取志愿。
      如果录取不成功，则根据平均绩点的级差削减相应的平均绩点。
      如果已经没有志愿可以录取，则根据是否服从调剂，进入调剂带定名单。
      如果该志愿录取成功，则录取下一个学生。
      
      对调剂名单中的学生进行录取。录取的专业为随机选择还有剩余名额的专业。
  </pre>
  <form name="actionForm" action="speciality2ndSignUpStudent.do?method=autoMatriculate" method="post" maxlength="return false;">
    <input type="hidden" name="signUpStd.setting.id" value="${RequestParameters['signUpStd.setting.id']}"/>
    &nbsp;&nbsp;是否允许调剂<@htm.radio2 value=true name="matriculateParams.adjustable"/>
    &nbsp;<button onclick="autoMatriculate()">开始自动录取</button>
  </form>
  <script>
     var bar =new ToolBar("myBar","录取说明",null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
     
     var form=document.actionForm;
     function autoMatriculate(){
        if(confirm("是否确定进行录取?")){
           form.submit();
        }
     }
  </script>
</body>
<#include "/templates/foot.ftl"/> 