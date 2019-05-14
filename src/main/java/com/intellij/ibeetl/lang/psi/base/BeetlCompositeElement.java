package com.intellij.ibeetl.lang.psi.base;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.ibeetl.lang.BeetlFile;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * psi  语法树 的节点类型
 * 用来包装ASTNode转换成PsiElement类型
 * 同时实现命名引用
 * 可以看一下CompositePsiElement，但是CompositePsiElement这个类应该是某个用途的时候才用
 */
public class BeetlCompositeElement extends ASTWrapperPsiElement {

	public BeetlCompositeElement(@NotNull ASTNode node) {
		super(node);
	}

	@Override
	public int getTextOffset() {
		return super.getTextOffset();
	}

	@Override
	public BeetlFile getContainingFile() {
		return (BeetlFile) super.getContainingFile();
	}
}
