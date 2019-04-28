package com.intellij.ibeetl.lang.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.ibeetl.generated.psi.BeetlVisitor;
import com.intellij.ibeetl.lang.psi.BeetlPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * psi  语法树 的节点类型
 * 用来包装ASTNode转换成PsiElement类型
 * 可以看一下CompositePsiElement，但是CompositePsiElement这个类应该是某个用途的时候才用
 */
public class BeetlCompositeElement extends ASTWrapperPsiElement implements BeetlPsiElement {

	public BeetlCompositeElement(@NotNull ASTNode node) {
		super(node);

	}

	@Override
	public <R> R accept(@NotNull BeetlVisitor<R> visitor) {
		return visitor.visitPsiElement(this);
	}

	@Override
	public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
		return this;
	}

	@Nullable
	@Override
	public PsiElement getNameIdentifier() {
		return this;
	}

	@Nullable
	@Override
	public PsiElement getIdentifyingElement() {
		return this;
	}

	@Override
	public int getTextOffset() {
		return super.getTextOffset();
	}
}
