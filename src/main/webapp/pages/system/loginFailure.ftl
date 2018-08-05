<#include "/templates/simpleHead.ftl"/>
<body>
  <p>
  你还没有登录系统，请首先登录.
  </p>
  <script>
     window.open("index.do");
     self.window.top.close();
  </script>
</body>
<#include "/templates/foot.ftl"/>
  