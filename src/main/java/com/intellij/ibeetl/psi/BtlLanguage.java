package com.intellij.ibeetl.psi;

import com.intellij.lang.Language;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.psi.templateLanguages.TemplateLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Beetl语言，标识为BTL
 */
public class BtlLanguage extends HTMLLanguage implements TemplateLanguage {
	public static final Language INSTANCE = new BtlLanguage();

	private BtlLanguage() {
		super(HTMLLanguage.INSTANCE, "btl", new String[]{"text/btl", "text/x-btl", "application/x-btl"});
	}

	@Nullable
	@Override
	public LanguageFileType getAssociatedFileType() {
		return super.getAssociatedFileType();
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return "Beetl";
	}

	@Override
	public boolean isCaseSensitive() {
		return super.isCaseSensitive();
	}
}
