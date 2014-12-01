<!DOCTYPE html>
<html lang="en">
<head>
<title>${appName}</title>
</head>

<body>
	<pre>${appName}
	
Date: ${time?date}
Time: ${time?time}
Updates: ${updates}
Rates: ${rates?size}
		
<#list rates as x>
	${x}
</#list>
</pre>
</body>
</html>