<script src='dwr/interface/calendarDAO.js'></script>
<script src='scripts/common/CalendarSelect.js'></script>
<script>
    <#if result?if_exists.stdTypeList?exists>
    <#assign stdTypeList=result.stdTypeList>
    </#if>
    var stdTypeArray = new Array();
    <#list stdTypeList as stdType>
    stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    </#list>
    <#if !(stdTypeNullable?exists)>
      <#assign stdTypeNullable=false>
    </#if>
    <#if !(yearNullable?exists)>
      <#assign yearNullable=false>
    </#if>
    <#if !(termNullable?exists)>
      <#assign termNullable=false>
    </#if>
    <#if !(stdTypeDefaultFirst)?exists>
      <#assign stdTypeDefaultFirst=true>
    </#if>
    var dd = new CalendarSelect("stdType","year","term",${stdTypeNullable?string},${yearNullable?string},${termNullable?string},${stdTypeDefaultFirst?string});
    dd.init(stdTypeArray);
</script>