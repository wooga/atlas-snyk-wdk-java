/*
 * Copyright 2022 Wooga GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package wooga.gradle.snyk.wdk.java

import com.wooga.snyk.GradleInit
import org.apache.commons.io.FileUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import wooga.gradle.snyk.SnykConventions
import wooga.gradle.snyk.SnykPlugin
import wooga.gradle.snyk.cli.*

import java.nio.file.Files
import java.nio.file.StandardCopyOption

class JavaSnykConventionsPlugin implements Plugin<Project> {

    static String toString(List<?> listValue) {
        listValue.collect({it.toString()}).join(",")
    }

    static String toString(Map<?,?> mapValue) {
        toString(mapValue.collect {key, value -> "${key}=${value}"})
    }

    static String toString(Enum<?> enumValue) {
        enumValue.toString()
    }

    @Override
    void apply(Project project) {
        project.pluginManager.withPlugin("net.wooga.snyk", {
            project.logger.info("Apply snyk conventions for WDK java project")
            Map<String, String> defaultProjectTags = ["team": "atlas", "component": "library", "platform": "jvm", "language": "groovy"]
            SnykConventions.initScript.defaultValue = snykInitScript.path
            SnykConventions.projectName.defaultValue = project.name
            SnykConventions.projectLifecycle.defaultValue = toString([LifecycleOption.development, LifecycleOption.production])
            SnykConventions.projectEnvironment.defaultValue = toString([EnvironmentOption.internal])
            SnykConventions.projectBusinessCriticality.defaultValue = toString([BusinessCriticalityOption.low])
            SnykConventions.projectTags.defaultValue = toString(defaultProjectTags)
            SnykConventions.autoDownload.defaultValue = true
            SnykConventions.strategies.defaultValue = toString([SnykPlugin.MONITOR_CHECK])
            SnykConventions.failOn.defaultValue = toString(FailOnOption.all)
            SnykConventions.severityThreshold.defaultValue = toString(SeverityThresholdOption.high)
            SnykConventions.orgName.defaultValue = "wooga"
        })
    }

    /**
     * A temp solution to support buildScript dependency reporting.
     * <p>
     * We construct a custom init script which will override the init-script passed by snyk-cli to gradle to fetch
     * the dependencies and inject the buildScript `classpath` configuration along the other selected configurations.
     * This should be fixed and supported by snyk itself. I will open a support ticket.
     *
     * @return a {@code File} object pointing to the custom init script file.
     */
    static File getSnykInitScript() {
        GradleInit.getSnykInitScript()
    }
}
