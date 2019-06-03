package com.intellij.ibeetl.lang.base;

import com.intellij.ibeetl.lang.psi.elements.BeetlPsiElementVisitor;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

/**
 * Psi 树节点类型
 * PsiNamedElement 支撑find 引用功能
 */
public interface BeetlPsiNamedElement extends PsiNameIdentifierOwner {
	<R> R accept(@NotNull BeetlPsiElementVisitor<R> visitor);
}
