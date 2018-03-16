package com.daijinlin.libcheck

import com.daijinlin.libcheck.checkstyle.CheckstyleCheck
import com.daijinlin.libcheck.common.Utils
import com.daijinlin.libcheck.findbugs.FindbugsCheck
import com.daijinlin.libcheck.lint.LintCheck
import com.daijinlin.libcheck.pmd.PmdCheck
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * <pre>
 * Created by J!nl!n on 2018/3/15.
 * Copyright © 1990-2018 J!nl!n™ Inc. All rights reserved.
 *
 * </pre>
 */
class CodeCheckPlugin implements Plugin<Project> {

  @Override
  void apply(Project project) {
    project.extensions.create(CodeCheckExtension.NAME, CodeCheckExtension, project)
    new CheckstyleCheck().apply(project)
    new FindbugsCheck().apply(project)
    new PmdCheck().apply(project)
//    if (Utils.isAndroidProject(project) || Utils.isKotlinProject(project)) {
//      new LintCheck().apply(project)
//    }
  }

}