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
import wooga.gradle.snyk.wdk.java.internal.DefaultJavaSnykPluginConventionsPluginExtension
import wooga.gradle.snyk.wdk.java.tasks.JavaSnykPluginConventions

class JavaSnykPluginConventionsPlugin implements Plugin<Project> {

    static String EXTENSION_NAME = "snykJava"

    @Override
    void apply(Project project) {
        def extension = createAndConfigureExtension(project)
    }

    protected static JavaSnykPluginConventionsPluginExtension createAndConfigureExtension(Project project) {
        def extension = project.extensions.create(JavaSnykPluginConventionsPluginExtension, EXTENSION_NAME, DefaultJavaSnykPluginConventionsPluginExtension, project)
        extension
    }

    protected static void createAndConfigureDefaultTasks(Project project, JavaSnykPluginConventionsPluginExtension extension){
        // TODO: Remove example
        def exampleTask = project.tasks.create("example", JavaSnykPluginConventions)
        exampleTask.group = "wooga.gradle.snyk.wdk.java"
        exampleTask.description = "example task"
    }

}
