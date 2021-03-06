package com.daijinlin.libcheck.common

import com.daijinlin.libcheck.CodeCheckExtension
import org.gradle.api.Project

abstract class CommonCheck<Config extends CommonConfig> {

  final String taskName
  final String taskDescription
  final String taskGroup = "verification"
  CodeCheckExtension extension

  CommonCheck(String taskName, String taskDescription) {
    this.taskName = taskName
    this.taskDescription = taskDescription
  }

  protected Set<String> getDependencies() { [] }

  protected abstract Config getConfig(CodeCheckExtension extension)

  protected abstract void performCheck(Project project, List<File> sources,
                                       File configFile, File xmlReportFile)

  protected abstract int getErrorCount(File xmlReportFile)

  protected abstract String getErrorMessage(int errorCount, File htmlReportFile)

  protected void reformatReport(Project project, File styleFile,
                                File xmlReportFile, File htmlReportFile) {
    project.ant.xslt(in: xmlReportFile, out: htmlReportFile) {
      style { string(styleFile.text) }
    }
  }

  void apply(Project target) {
    extension = target.extensions.findByType(CodeCheckExtension)
    final String name = target.name
    if (extension.excludeProjects.contains(name) || extension.skip) { // 如果项目在忽略列表中则直接跳过
      L.d("skip project $name")
      return
    }

    Config config = getConfig(extension)
    boolean skip = config.resolveSkip(extension.skip)
    boolean abortOnError = config.resolveAbortOnError(extension.abortOnError)
    File configFile = config.resolveConfigFile(taskName)
//        File styleFile = config.resolveStyleFile(taskCode)
    File xmlReportFile = config.resolveXmlReportFile(taskName)
//        File htmlReportFile = config.resolveHtmlReportFile(taskCode)
    List<File> sources = config.getAndroidSources()

    if (skip) {
      L.d("skip $taskName")
    } else {
//          xmlReportFile.parentFile.mkdirs()
      performCheck(target, sources, configFile, xmlReportFile)
//          htmlReportFile.parentFile.mkdirs()
//          reformatReport(target, styleFile, xmlReportFile, htmlReportFile)

//          int errorCount = getErrorCount(xmlReportFile)
//          if (errorCount) {
//            String errorMessage = getErrorMessage(errorCount, htmlReportFile)
//            if (abortOnError) {
//              throw new GradleException(errorMessage)
//            } else {
//              target.logger.warn errorMessage
//            }
//          }
    }

//    target.tasks.getByName('check').dependsOn taskName
  }
}