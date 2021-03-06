/*
* Copyright 2012 John Ahlroos
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package fi.jasoft.plugin;

import org.gradle.api.Plugin
import org.gradle.api.Project
import fi.jasoft.plugin.DependencyListener
import org.gradle.api.plugins.jetty.JettyPlugin
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.tasks.compile.Compile

import fi.jasoft.plugin.tasks.CreateProjectTask
import fi.jasoft.plugin.tasks.CreateComponentTask
import fi.jasoft.plugin.tasks.CreateThemeTask
import fi.jasoft.plugin.tasks.CompileWidgetsetTask
import fi.jasoft.plugin.tasks.DevModeTask
import fi.jasoft.plugin.tasks.SuperDevModeTask
import fi.jasoft.plugin.tasks.CompileThemeTask
import fi.jasoft.plugin.tasks.RunTask

class VaadinPlugin implements Plugin<Project>{

	void apply(Project project){

		// Extensions
		project.extensions.create('vaadin', VaadinPluginExtension)

		// Dependency resolution
		project.getGradle().addProjectEvaluationListener(new DependencyListener());
		project.getGradle().getTaskGraph().addTaskExecutionListener(new TaskListener())

		// Plugins
		project.plugins.apply(WarPlugin)
		
		// Tasks
		project.tasks.add(name: 'createVaadinProject', 	type: CreateProjectTask, 	group: 'Vaadin')	
		project.tasks.add(name: 'createVaadinComponent',type: CreateComponentTask,	group: 'Vaadin')
		project.tasks.add(name: 'createVaadinTheme',	type: CreateThemeTask,		group: 'Vaadin')

		project.tasks.add(name: 'widgetset', 			type: CompileWidgetsetTask, group: 'Vaadin')
		project.tasks.add(name: 'devmode', 				type: DevModeTask, 			group: 'Vaadin')
		project.tasks.add(name: 'superdevmode', 		type: SuperDevModeTask, 	group: 'Vaadin')
		project.tasks.add(name: 'themes',				type: CompileThemeTask,		group: 'Vaadin')
		project.tasks.add(name: 'vaadinRun',			type: RunTask,				group: 'Vaadin')

		// Add debug information to all compilation results
		project.tasks.compileJava.options.debugOptions.debugLevel = 'source,lines,vars'

		// Add sources to test classpath
		project.sourceSets.test.runtimeClasspath += project.files(project.sourceSets.main.java.srcDirs)

	}
}
