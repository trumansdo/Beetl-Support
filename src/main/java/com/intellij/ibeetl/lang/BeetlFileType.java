package com.intellij.ibeetl.lang;

import com.intellij.ibeetl.utils.BeetlConstants;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.TemplateLanguageFileType;
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
	}

	@NotNull
	@Override
	public String getName() {
		return BeetlConstants.BTL;
	}

	@NotNull
	@Override
	public String getDescription() {
		return "Beetl files";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return "btl";
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
