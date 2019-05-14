package com.intellij.ibeetl.lang;

import com.intellij.ibeetl.lang.highlight.BeetlSyntaxHighlighter;
import com.intellij.lang.DependentLanguage;
import com.intellij.lang.InjectableLanguage;
import com.intellij.lang.Language;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.psi.templateLanguages.TemplateLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Beetl语言，标识为BTL
 * 查看 InjectableLanguage 或者  TemplateLanguage
 */
public class BeetlLanguage extends Language implements TemplateLanguage, DependentLanguage, InjectableLanguage {
	public static final BeetlLanguage INSTANCE = new BeetlLanguage();

	private BeetlLanguage() {
		super(HTMLLanguage.INSTANCE, "btl");
	}

	@Nullable
	@Override
	public LanguageFileType getAssociatedFileType() {
		return super.getAssociatedFileType();
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return "Beetl Language";
	}

	@Override
	public boolean isCaseSensitive() {
		return super.isCaseSensitive();
	}

	private void addSyntaxHighlighting() {
		SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
			@NotNull
			protected SyntaxHighlighter createHighlighter() {
				return new BeetlSyntaxHighlighter();
			}
		});
	}

}
