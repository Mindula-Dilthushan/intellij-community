// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.codeInspection.test.junit

import com.intellij.java.library.JavaLibraryUtil
import com.intellij.openapi.module.ModuleUtil
import com.intellij.psi.PsiFile
import com.siyeh.ig.junit.JUnitCommonClassNames.*

internal fun isJUnit3InScope(file: PsiFile): Boolean {
  return JavaLibraryUtil.hasLibraryClass(ModuleUtil.findModuleForFile(file), JUNIT_FRAMEWORK_TEST_CASE)
}

internal fun isJUnit4InScope(file: PsiFile): Boolean {
  return JavaLibraryUtil.hasLibraryClass(ModuleUtil.findModuleForFile(file), ORG_JUNIT_TEST)
}

internal fun isJUnit5InScope(file: PsiFile): Boolean {
  return JavaLibraryUtil.hasLibraryClass(ModuleUtil.findModuleForFile(file), ORG_JUNIT_JUPITER_API_TEST)
}