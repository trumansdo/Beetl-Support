package com.intellij.ibeetl.lang.psi.base;

import com.intellij.ibeetl.generated.psi.BeetlVisitor;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

/**
 * Psi 树节点类型
 * PsiNamedElement 支撑find 引用功能
 */
public interface BeetlPsiNamedElement extends PsiNameIdentifierOwner {
	<R> R accept(@NotNull BeetlVisitor<R> visitor);
}
