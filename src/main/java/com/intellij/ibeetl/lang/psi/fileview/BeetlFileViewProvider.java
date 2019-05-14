package com.intellij.ibeetl.lang.psi.fileview;

import com.intellij.ibeetl.lang.BeetlLanguage;
import com.intellij.ibeetl.lang.lexer.BeetlIElementTypes;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.LanguageSubstitutors;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.templateLanguages.ConfigurableTemplateLanguageFileViewProvider;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import com.intellij.psi.templateLanguages.TemplateLanguage;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

/**
 * getTemplateDataLanguage
 * 在idea中，逻辑是这样的：将自定义语言看作主语言，依赖语言看做是templateDataLanguage语言
 * 例如在Beetl中，将Beetl看做主语言，最常用的Html看作templateDataLanguage语言
 * 所以需要通过virtualFile来判断是什么类型的文件
 */
public class BeetlFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider implements ConfigurableTemplateLanguageFileViewProvider {

	private Language myTemplateDataLanguage;
	private Language myBaseLanguage;
	private Set<Language> myAllLanguages;

	public BeetlFileViewProvider(@NotNull PsiManager manager, @NotNull VirtualFile virtualFile, boolean eventSystemEnabled) {
		this(manager, virtualFile, eventSystemEnabled, getSubstitutedLanguage(manager, virtualFile), BeetlLanguage.INSTANCE);
	}


	private BeetlFileViewProvider(PsiManager manager, VirtualFile virtualFile, boolean physical, Language templateDataLanguage, Language baseLanguage) {
		super(manager, virtualFile, physical);
		this.myTemplateDataLanguage = templateDataLanguage;
		this.setBaseLanguage(baseLanguage);
	}

	private static Language getSubstitutedLanguage(PsiManager manager, VirtualFile virtualFile) {
		Language language = getTemplateDataLanguage(virtualFile, manager.getProject());
		Language substituteLanguage = LanguageSubstitutors.INSTANCE.substituteLanguage(language, virtualFile, manager.getProject());
		return (language instanceof TemplateLanguage ? PlainTextLanguage.INSTANCE : substituteLanguage);
	}

	public static Language getTemplateDataLanguage(VirtualFile virtualFile, Project project) {
		Language language = TemplateDataLanguageMappings.getInstance(project).getMapping(virtualFile);
		if (language == null) {
			return ("btlx".equals(virtualFile.getExtension()) ? XMLLanguage.INSTANCE : HTMLLanguage.INSTANCE);
		} else {
			return language;
		}
	}

	@NotNull
	@Override
	public Language getBaseLanguage() {
		return this.myBaseLanguage;
	}

	@NotNull
	@Override
	public Set<Language> getLanguages() {
		ApplicationManager.getApplication().assertReadAccessAllowed();
		return this.myAllLanguages;
	}

	@Override
	public boolean hasLanguage(@NotNull Language language) {
		return false;
	}

	@NotNull
	@Override
	public Language getTemplateDataLanguage() {
		return this.myTemplateDataLanguage;
	}

	@NotNull
	@Override
	protected BeetlFileViewProvider cloneInner(@NotNull VirtualFile fileCopy) {
		return new BeetlFileViewProvider(this.getManager(), fileCopy, false, this.myTemplateDataLanguage, this.myBaseLanguage);
	}

	@Override
	public void contentsSynchronized() {
		super.contentsSynchronized();
		/*Language language = FtlLexer.guessDirectiveStyle(this.getContents()).getLanguage();
		if (this.myBaseLanguage != language) {
			this.removeFile(this.myBaseLanguage);
			this.setBaseLanguage(language);
		}
*/
	}

	private void setBaseLanguage(Language language) {
		this.myBaseLanguage = language;
		this.myAllLanguages = Collections.unmodifiableSet(ContainerUtil.newTroveSet(this.myBaseLanguage, this.myTemplateDataLanguage));
	}

	@Nullable
	protected PsiFile createFile(@NotNull Language lang) {
		if (lang == this.getBaseLanguage()) {
			return LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this);
		} else if (lang == this.getTemplateDataLanguage()) {
			PsiFileImpl file = (PsiFileImpl) LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this);
			file.setContentElementType(BeetlIElementTypes.TEMPLATE_DATA);
			return file;
		} else {
			return null;
		}
	}
}
