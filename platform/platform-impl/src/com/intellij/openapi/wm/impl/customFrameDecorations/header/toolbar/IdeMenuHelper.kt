// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.openapi.wm.impl.customFrameDecorations.header.toolbar

import com.intellij.ide.ProjectWindowCustomizerService
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.impl.IdeMenuBar
import com.intellij.ui.JBColor
import com.intellij.ui.plaf.beg.IdeaMenuUI
import java.awt.event.ContainerAdapter
import java.awt.event.ContainerEvent

internal class IdeMenuHelper(private val menu: IdeMenuBar) {

  private val containerListener = object : ContainerAdapter() {
    override fun componentAdded(e: ContainerEvent?) {
      updateUI()
    }
  }
  private var customizerServiceDisposable: Disposable? = null

  init {
    menu.isOpaque = false
  }

  fun installListeners() {
    // ideMenu can be populated after switchState invocation
    menu.addContainerListener(containerListener)

    customizerServiceDisposable = Disposer.newDisposable()
    ProjectWindowCustomizerService.getInstance().addListener(customizerServiceDisposable!!, true) {
      updateUI()
    }
  }

  fun uninstallListeners() {
    menu.removeContainerListener(containerListener)
    customizerServiceDisposable?.let {
      Disposer.dispose(it)
      customizerServiceDisposable = null
    }
  }

  fun updateUI() {
    val selectionBackground =
      if (ProjectWindowCustomizerService.getInstance().isActive())
        JBColor.namedColor("MainMenu.transparentSelectionBackground", IdeaMenuUI.getDefaultSelectionBackground())
      else JBColor.namedColor("MainMenu.selectionBackground", IdeaMenuUI.getDefaultSelectionBackground())

    for (i in 0..menu.menuCount - 1) {
      val menu = menu.getMenu(i)
      menu.isOpaque = false
      (menu.ui as? IdeaMenuUI)?.setSelectionBackground(selectionBackground)
    }
  }
}
