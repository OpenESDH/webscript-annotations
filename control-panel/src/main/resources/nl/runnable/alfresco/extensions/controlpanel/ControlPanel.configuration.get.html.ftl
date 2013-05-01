<#import "templates/html-macros.inc.ftl" as html>
<@html.document title="Configuration - Dynamic Extensions" active="configuration">

  <div class="row">

    <div class="span6">
      
      <h2>System Packages</h2>
      <p>
        <a href="system-packages">View all System Packages</a>
      </p>
        <p>
          System Packages are Java libraries from the Alfresco repository. Classes from these packages
          are available to OSGi bundles.
          Dynamic Extensions scans the System Packages when it starts up for the first time and 
          caches them to speed up subsequent repository startups.
        </p>
      <div class="well">
        <#if systemPackageCacheExists>
          <p>
            If you change any of the Java libraries in the repository application, you should delete 
            <#assign url = "${url.serviceContext}/api/node/content/${systemPackageCacheNodeRef?replace('://', '/')}" />
            the <a href="${url}" target="_blank">System Package cache</a>.
            With the cache deleted, the System Packages will be rescanned on the next repository 
            startup.
          </p>
          <form action="delete-system-package-cache" method="post" 
            data-title="Delete System Package cache"
            data-confirm="Are you sure you want to delete the System Package cache?">
            <button class="btn btn-danger">Delete System Package cache</button>
          </form>
        <#else>
          <p>
            <em>The System Package cache cannot be found.</em>
          </p>
        </#if>
        <p>
          Note: When the System Package cache is not available the OSGI container cannot be 
          restarted. You should then restart the repository to have the cache rebuilt on startup.
        </p>
      </div>

      <#if canRestartFramework>
        <h2>Restart OSGi container</h2>
        <p>
          Restart the OSGi container to reinitialize Dynamic Extensions:
        </p>
        <div class="well">
          <p>
            <a href="framework/restart" 
              data-method="post" 
              data-wait="15000" 
              data-title="Restarting OSGi container" 
              data-message="<p>Please wait while the OSGi container is restarting. The page will refresh automatically.</p>" 
              data-button="Refresh page now"
              class="btn btn-primary">
              Restart OSGi container
            </a>
          </p>
          <p>            
            The Control Panel will be available while the OSGI container is restarting.
          </p>
          <p>
            If you get a 404 Not Found, wait a few seconds, then refresh the page.
          </p>
        </div>
      </#if>

    </div> <#-- .span6 -->

    <div class="span6">
      <h2>Services</h2>
      <p>
        <a href="services">View all Services</a>
      </p>
      <p>
        Services are available for use by Dynamic Extensions. Most services are part of the
        Alfresco repository API.
      </p>
      <div class="well">
<p>
  To obtain a reference to a service using dependency injection:
</p>
<pre class="java fragment">
<em class="comment">// Obtain a dependency by interface</em>
<em class="comment">// This is the recommended approach</em>
<strong class="annotation">@Inject</strong>
private NodeService nodeService;
</pre>

<p>Alternative: inject by name</p>
<pre class="java fragment">
<em class="comment">// Directly reference a named bean</em>
<em class="comment">// This approach can be useful in some cases</em>
<strong class="annotation">@Inject</strong>
<strong class="annotation">@Named("nodeService")</strong>
private NodeService nodeService;
</pre>

<p>Using Spring XML configuration:</p>
<pre class="xml fragment">
&lt;bean class="..."&gt;
  &lt;property name="nodeService" ref="NodeService" /&gt;
&lt;/bean&gt;
</pre>

      </div>
    </div> <#-- .span6 -->

  </div> <#-- .row -->

</@html.document>