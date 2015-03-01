<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body>
<pre>${appName}
--------------------------------------------------------------------</pre>
<pre>
<#list rates as x>
${x}
</#list>
</pre>
<#include "footer.ftl">
</body>
</html>