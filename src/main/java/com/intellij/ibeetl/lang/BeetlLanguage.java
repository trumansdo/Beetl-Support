package com.intellij.ibeetl.lang;

import com.intellij.ibeetl.lang.highlight.BeetlSyntaxHighlighter;
import com.intellij.lang.InjectableLanguage;
import com.intellij.lang.Language;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Beetl语言，标识为BTL
 */
public class BeetlLanguage extends Language implements InjectableLanguage {
	public static final BeetlLanguage INSTANCE = new BeetlLanguage();
	public static final BeetlLanguage HTML_TAG_EXPRESSIONS = new BeetlLanguage(INSTANCE, "BeetlHtmlTagExpressions");

	private BeetlLanguage() {
		super(HTMLLanguage.INSTANCE, "beetl", new String[]{"text/html", "text/htmlh","text/btl", "text/x-btl", "application/x-btl"});
	}
	protected BeetlLanguage(BeetlLanguage baseLanguage, String ID) {
		super(baseLanguage, ID, new String[0]);
		this.addSyntaxHighlighting();
	}

	@Nullable
	@Override
	public LanguageFileType getAssociatedFileType() {
		return super.getAssociatedFileType();
	}

	@NotNull
	@Override
	public String getDisplayName() {
		if (this == HTML_TAG_EXPRESSIONS) {
			return "Beetl HTML tag Expression";
		}  else {
			return "Beetl Language";
		}
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
