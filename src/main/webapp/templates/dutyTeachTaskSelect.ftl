<script src='dwr/interface/calendarDAO.js'></script>
<script src='dwr/interface/DutyRecordManager.js'></script>
<script src='scripts/common/DutyTeachTaskSelect.js'></script>
<script>
    <#if result?if_exists.stdTypeList?exists>
    <#assign stdTypeList=result.stdTypeList>
    </#if>
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    var dd = new DutyTeachTaskSelect("stdType","year","term","courseType","studyDep","teachDep","teacher",${stdTypeNullable?default(false)?string},${yearNullable?default(false)?string},${termNullable?default(false)?string},${courseTypeNullable?default(false)?string},${studyDepNullable?default(false)?string},${teachDepNullable?default(false)?string},${teacherNullable?default(false)?string});
    dd.init(stdTypeArray);
</script>