<script src='dwr/interface/departmentDAO.js'></script>
<script src='dwr/interface/specialityDAO.js'></script>
<script src='dwr/interface/specialityAspectDAO.js'></script>
<script src='scripts/secondSpeciality2Select.js'></script>
<script>
    <#if result?if_exists.secondSpecialityList?exists>
    <#assign secondSpecialityList=result.secondSpecialityList>
    </#if>    
    var secondSpecialityArray = new Array();
    <#list secondSpecialityList?if_exists as secondSpeciality>
    secondSpecialityArray[${secondSpeciality_index}]={'id':'${secondSpeciality.id?if_exists}','name':'<@i18nName secondSpeciality/>'};
    </#list>
    var ss = new SecondSpeciality2Select("secondMajor","secondAspect",${secondSpecialityNullable?default('true')},${secondAspectNullable?default('true')});
    ss.init(secondSpecialityArray);
</script>