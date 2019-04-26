package com.intellij.ibeetl.lang.psi;

import com.intellij.ibeetl.generated.psi.BeetlVisitor;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

/**
 * PsiNamedElement 支撑find 引用功能
 */
public interface BeetlCompositePsiElement extends PsiNameIdentifierOwner {
	<R> R accept(@NotNull BeetlVisitor<R> visitor);
}
