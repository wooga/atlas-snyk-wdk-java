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

import org.gradle.api.Plugin
import org.gradle.api.Project
import wooga.gradle.snyk.SnykConventions
import wooga.gradle.snyk.SnykPlugin
import wooga.gradle.snyk.SnykPluginExtension
import wooga.gradle.snyk.SnykRootPluginExtension
import wooga.gradle.snyk.cli.*

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

            SnykRootPluginExtension snykExtension = project.extensions.getByName(SnykPlugin.EXTENSION_NAME) as SnykRootPluginExtension
            Map<String, String> defaultProjectTags = ["team": "atlas", "component": "library", "platform": "jvm"]

            SnykConventions.projectName.defaultValue = project.name
            SnykConventions.projectLifecycle.defaultValue = toString([LifecycleOption.development, LifecycleOption.production])
            SnykConventions.projectEnvironment.defaultValue = toString([EnvironmentOption.internal])
            SnykConventions.projectBusinessCriticality.defaultValue = toString([BusinessCriticalityOption.low])
            SnykConventions.projectTags.defaultValue = toString(defaultProjectTags)
            SnykConventions.autoDownload.defaultValue = true
            SnykConventions.strategies.defaultValue = toString(["publish_monitor"])
            SnykConventions.failOn.defaultValue = toString(FailOnOption.all)
            SnykConventions.severityThreshold.defaultValue = toString(SeverityThresholdOption.high)
        })
    }
}
