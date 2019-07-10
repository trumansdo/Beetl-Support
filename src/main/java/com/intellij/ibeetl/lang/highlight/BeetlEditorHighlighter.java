package com.intellij.ibeetl.lang.highlight;

import com.intellij.ibeetl.lang.BeetlFile;
import com.intellij.ibeetl.lang.BeetlFileType;
import com.intellij.ibeetl.lang.lexer.BeetlIElementTypes;
import com.intellij.ibeetl.lang.psi.fileview.BeetlFileViewProvider;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.ex.util.LayerDescriptor;
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter;
import com.intellij.openapi.fileTypes.FileTypes;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.templateLanguages.TemplateDataHighlighterWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BeetlEditorHighlighter extends LayeredLexerEditorHighlighter {
	private final Project myProject;
	private final VirtualFile myVirtualFile;

	public BeetlEditorHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile, @NotNull EditorColorsScheme colors) {
		super(new BeetlSyntaxHighlighter(), colors);
		this.myProject = project;
		this.myVirtualFile = virtualFile;
		this.registerLayer(BeetlIElementTypes.BTL_TEMPLATE_HTML_TEXT, new LayerDescriptor(new TemplateDataHighlighterWrapper(getHighlighter(project, virtualFile)), ""));

	}

	/*确定更新不同语言layer的条件*/
	@Override
	protected boolean updateLayers() {
		return true;
	}

	/**
	 * 没看源码。。不知道啥用途，暂定永远返回true
	 * @param e
	 * @return
	 */
	@Override
	protected boolean updateLayers(@NotNull DocumentEvent e) {
		return super.updateLayers(e);
	}

	private SyntaxHighlighter getHighlighter(Project project, VirtualFile virtualFile) {
		LanguageFileType type = (project == null && virtualFile == null) ? null : BeetlFileViewProvider.getTemplateDataLanguage(virtualFile, project).getAssociatedFileType();
		LanguageFileType fileType = type == null ? FileTypes.PLAIN_TEXT : type;
		SyntaxHighlighter syntaxHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(fileType, project, virtualFile);
		assert syntaxHighlighter!=null;
		return syntaxHighlighter;
	}
}
