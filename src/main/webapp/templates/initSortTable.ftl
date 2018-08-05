    <#if sortTableId?exists>
    initSortTable('${sortTableId}',${headIndex?default(0)},"${RequestParameters['orderBy']?default('null')}");
    <#else>
    initSortTable('sortTable',${headIndex?default(0)},"${RequestParameters['orderBy']?default('null')}");
    </#if>
    