package com.intellij.ibeetl.lang;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.html.HTMLIsIndexElement;

/**
 * 调用beetl自带的校验方法校验语法错误
 */
public class BeetlExternalAnnotator extends ExternalAnnotator {

	private Editor editor;

	@Nullable
	@Override
	public Object collectInformation(@NotNull PsiFile file) {
		return super.collectInformation(file);
	}

	@Nullable
	@Override
	public Object collectInformation(@NotNull PsiFile file, @NotNull Editor editor, boolean hasErrors) {
		this.editor=editor;
		return this.collectInformation(file);
	}

	@Nullable
	@Override
	public Object doAnnotate(Object collectedInfo) {
		return super.doAnnotate(collectedInfo);
	}

	@Override
	public void apply(@NotNull PsiFile file, Object annotationResult, @NotNull AnnotationHolder holder) {
		super.apply(file, annotationResult, holder);
	}
}
