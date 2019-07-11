package com.intellij.ibeetl.lang.highlight;

import com.intellij.ibeetl.lang.BeetlFile;
import com.intellij.ibeetl.lang.BeetlFileType;
import com.intellij.ibeetl.lang.lexer.BeetlIElementTypes;
import com.intellij.ibeetl.lang.psi.fileview.BeetlFileViewProvider;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.Document;
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


	@Override
	protected boolean updateLayers() {
		return true;
	}

	/**
	 * 是否增量进行词法高亮.
	 * 例如：freemarker的增量大致逻辑是：当出现不同的语法< 和 [  时，会全量处理。当只是写${} 语法时，增量更新。
	 * 但是beetl。。。。很难区分。所以偷个懒，直接全量了。
	 * @param e
	 * @return true  全量；false增量;
	 */
	@Override
	protected boolean updateLayers(@NotNull DocumentEvent e) {
//		Document document = e.getDocument(); 当前文档
//		CharSequence oldFragment = e.getOldFragment(); 如果是删除文字，则是删除的字符串；如果是添加文字，则是""
//		int oldLength = e.getOldLength(); 对应oldFragment的长度
//		CharSequence newFragment = e.getNewFragment(); 如果是删除文字，则是""；如果是添加文字，则是添加的字符串
//		int newLength = e.getNewLength(); 对应newFragment的长度
//		int offset = e.getOffset(); 最后光标的偏移位置
		return this.updateLayers();
	}

	private SyntaxHighlighter getHighlighter(Project project, VirtualFile virtualFile) {
		LanguageFileType type = (project == null && virtualFile == null) ? null : BeetlFileViewProvider.getTemplateDataLanguage(virtualFile, project).getAssociatedFileType();
		LanguageFileType fileType = type == null ? FileTypes.PLAIN_TEXT : type;
		SyntaxHighlighter syntaxHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(fileType, project, virtualFile);
		assert syntaxHighlighter!=null;
		return syntaxHighlighter;
	}
}
