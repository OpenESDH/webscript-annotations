<#import "/org/springframework/extensions/webscripts/webscripts.lib.html.ftl" as wsLib/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <body>
      <div>
          <h2>Annotations Based Web Scripts</h2>
          <a href="${url.serviceContext}/index">Back to Alfresco Web Scripts Home</a>
          <br/>
          <#if webScripts?size == 0>
            No registered annotations based web scripts 
          </#if>
          <br/>
          <br/>
          <#if webScripts?size &gt; 0>
                 <a href="${url.serviceContext}/annotatedws/all">Browse all Annotations Based Web Scripts</a>
                 <br/>
                 <br/>
                 <#list wsFamilies as family>
                    <p><a href="${url.serviceContext}/annotatedws/family/${family}">Browse '${family}' Web Scripts</a></p>
                 </#list>
          </#if> 
      </div>
   </body>
</html>