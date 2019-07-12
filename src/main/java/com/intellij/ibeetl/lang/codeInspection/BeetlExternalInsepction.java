package com.intellij.ibeetl.lang.codeInspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.SuppressQuickFix;
import com.intellij.codeInspection.ex.ExternalAnnotatorBatchInspection;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BeetlExternalInsepction extends LocalInspectionTool implements ExternalAnnotatorBatchInspection {
	@NotNull
	public static final String SHORT_NAME = "BeetlExternalLint";

	@NotNull
	public SuppressQuickFix[] getBatchSuppressActions(@Nullable PsiElement element) {
		return SuppressQuickFix.EMPTY_ARRAY;
	}

}