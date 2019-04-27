package com.intellij.ibeetl.lang.psi.impl;

import com.intellij.ibeetl.generated.psi.BeetlVisitor;
import com.intellij.ibeetl.lang.psi.BeetlCompositePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * psi  语法树 的节点类型
 * 同时 也是AstNode
 */
public class BeetlCompositePsiElementImpl extends CompositePsiElement implements BeetlCompositePsiElement {

	public BeetlCompositePsiElementImpl(IElementType type) {
		super(type);
	}

	@Override
	public <R> R accept(@NotNull BeetlVisitor<R> visitor) {
		return visitor.visitCompositePsiElement(this);
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
