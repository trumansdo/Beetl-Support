package com.intellij.ibeetl.lang;

import com.intellij.ibeetl.BeetlBundle;
import com.intellij.ibeetl.lang.highlight.BeetlEditorHighlighter;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.VirtualFile;
import icons.BeetlIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.nio.charset.Charset;

/**
 * Beetl 的文件类型
 */
public class BeetlFileType extends LanguageFileType implements TemplateLanguageFileType {
	public static final LanguageFileType INSTANCE = new BeetlFileType();

	private BeetlFileType() {
		super(BeetlLanguage.INSTANCE);
		FileTypeEditorHighlighterProviders.INSTANCE.addExplicitExtension(this, new EditorHighlighterProvider() {
			@Override
			public EditorHighlighter getEditorHighlighter(@Nullable Project project, @NotNull FileType fileType, @Nullable VirtualFile virtualFile, @NotNull EditorColorsScheme colors) {
				return new BeetlEditorHighlighter(project, virtualFile, colors);
			}
		});
	}

	@NotNull
	@Override
	public String getName() {
		return BeetlBundle.message("file.type.description");
	}

	@NotNull
	@Override
	public String getDescription() {
		return BeetlBundle.message("file.type.description");
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return BeetlBundle.message("file.extension");
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return BeetlIcons.ICON;
	}

	@Override
	public String getCharset(@NotNull VirtualFile file, @NotNull byte[] content) {
		return CharsetToolkit.UTF8;
	}

	@Override
	public Charset extractCharsetFromFileContent(@Nullable Project project, @Nullable VirtualFile file, @NotNull CharSequence content) {
		return CharsetToolkit.UTF8_CHARSET;
	}
}
