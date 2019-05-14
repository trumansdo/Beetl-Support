package com.intellij.ibeetl.lang;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class BeetlFile extends PsiFileBase implements PsiFile{
	public BeetlFile(@NotNull FileViewProvider viewProvider) {
		super(viewProvider, BeetlLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public FileType getFileType() {
		return BeetlFileType.INSTANCE;
	}
}
