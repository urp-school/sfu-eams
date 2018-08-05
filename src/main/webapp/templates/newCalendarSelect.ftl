<script src='dwr/interface/calendarDAO.js'></script>
<script src='scripts/common/NewCalendarSelect.js'></script>
<script>
    var schemeArray = new Array();
    <#list calendarSchemes as scheme>
    schemeArray[${scheme_index}]={'id':'${scheme.id?if_exists}','name':'${scheme.name}'};
    </#list>
    var dd = new NewCalendarSelect("calendarSchemeId","calendarId",${(calendarNullable?default(false))?string});
    dd.init(schemeArray);
</script>