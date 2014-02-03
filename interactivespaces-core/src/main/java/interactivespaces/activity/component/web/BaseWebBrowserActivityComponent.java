/*
 * Copyright (C) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package interactivespaces.activity.component.web;

import com.google.common.collect.ImmutableList;

import interactivespaces.InteractiveSpacesException;
import interactivespaces.activity.component.BaseActivityComponent;
import interactivespaces.configuration.Configuration;

import java.util.List;

/**
 * Abstract base class for web browser activity components, providing for common constants
 * and base functionality. Needs to be overridden to provide an actual implementation.
 *
 * @author Keith M. Hughes
 */
public abstract class BaseWebBrowserActivityComponent extends BaseActivityComponent {

  /**
   * Configuration property giving the initial page of the web activity.
   *
   * <p>
   * If there is no content location, this should be a fully qualified URL.
   * Otherwise it can be relative to
   * {@link #CONFIGURATION_WEBAPP_CONTENT_LOCATION}.
   */
  public static final String CONFIGURATION_INITIAL_PAGE = "space.activity.webapp.url.initial";

  /**
   * Configuration property of any query string parameter which should be added
   * to the URL given to the web browser.
   */
  public static final String CONFIGURATION_INITIAL_URL_QUERY_STRING =
      "space.activity.webapp.url.query_string";

  /**
   * Configuration property saying whether the browser should be started in
   * debug mode or not.
   */
  public static final String CONFIGURATION_BROWSER_DEBUG = "space.activity.webapp.browser.debug";

  /**
   * Configuration property saying whether the browser should be started up when
   * the app starts up.
   */
  public static final String CONFIGURATION_BROWSER_STARTUP =
      "space.activity.webapp.browser.startup";

  /**
   * List of allowed prefixes for external URLs.
   */
  public static final List<String> EXTERNAL_URL_PREFIXES = ImmutableList.of("http:", "https:");

  /**
   * List of disallowed prefixes for external URLs.
   */
  public static final List<String> DISALLOWED_URL_PREFIXES = ImmutableList.of("file:");

  /**
   * Separator for web path elements.
   */
  public static final char WEB_PATH_SEPARATOR = '/';

  /**
   * Separator for a web url query string.
   */
  public static final char WEB_QUERY_STRING_SEPARATOR = '?';

  /**
   * The base of the content URL (minus the opening page and query string).
   */
  protected String baseContentUrl;

  /**
   * The full initial URL for the browser.
   */
  protected String initialUrl;

  /**
   * {@code true} if the browser should be started in debug mode.
   */
  protected boolean browserDebug;

  /**
   * {@code true} if the browser should be started up.
   */
  protected boolean browserStartup;

  @Override
  public void configureComponent(Configuration configuration) {
    super.configureComponent(configuration);

    StringBuilder initialUrlBuilder = new StringBuilder();

    String configurationInitialPage =
        configuration.getRequiredPropertyString(CONFIGURATION_INITIAL_PAGE);

    configurationInitialPage = configurationInitialPage.trim();
    if (isDisallowedPrefix(configurationInitialPage)) {
      throw new InteractiveSpacesException(String.format(
          "The initial page %s starts with an illegal prefix", configurationInitialPage));
    }

    if (isExternalPrefix(configurationInitialPage)) {
      initialUrlBuilder.append(configurationInitialPage);
    } else {
      WebServerActivityComponent webServer =
          componentContext.getRequiredActivityComponent(WebServerActivityComponent.COMPONENT_NAME);
      initialUrlBuilder.append(webServer.getWebContentUrl()).append(WEB_PATH_SEPARATOR)
          .append(configurationInitialPage);
    }

    String queryString = configuration.getPropertyString(CONFIGURATION_INITIAL_URL_QUERY_STRING);
    if (queryString != null) {
      initialUrlBuilder.append(WEB_QUERY_STRING_SEPARATOR).append(queryString);
    }

    initialUrl = initialUrlBuilder.toString();

    browserDebug = configuration.getPropertyBoolean(CONFIGURATION_BROWSER_DEBUG, false);

    browserStartup = configuration.getPropertyBoolean(CONFIGURATION_BROWSER_STARTUP, true);
  }

  /**
   * Does the initial page have a disallowed prefix?
   *
   * @param initialPage
   *          URL to check for disallowed prefix
   *
   * @return {@code true} if disallowed
   */
  private boolean isDisallowedPrefix(String initialPage) {
    for (String prefix : DISALLOWED_URL_PREFIXES) {
      if (initialPage.startsWith(prefix)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Does the initial page have an external prefix?
   *
   * @param initialPage
   *          URL to check for external prefix
   *
   * @return {@code true} if external
   */
  private boolean isExternalPrefix(String initialPage) {
    for (String prefix : EXTERNAL_URL_PREFIXES) {
      if (initialPage.startsWith(prefix)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Set the base URL that the browser will connect to.
   *
   * @param baseContentUrl
   *          the base URL (can be {@code null}
   */
  public void setBaseContentUrl(String baseContentUrl) {
    this.baseContentUrl = baseContentUrl;
  }
}