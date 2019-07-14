package com.intellij.ibeetl.lang;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.intellij.ibeetl.setting.BeetlConfigure.*;

/**
 * 调用beetl自带的校验方法校验语法错误。调用顺序如下：
 * <p>
 * collectInformation > doAnnotate > apply
 */
public class BeetlExternalAnnotator extends ExternalAnnotator<BeetlExternalAnnotator.Info, BeetlExternalAnnotator.Result> {
	
	private static Logger logger = Logger.getInstance(BeetlExternalAnnotator.class);
	
	private Editor editor;
	
	private static GroupTemplate groupTemplate;
	
	static {
		resetConfig();
	}
	
	public static void resetConfig() {
		/*
		 * 关于idea的classloader的几点：
		 * 1. 当前线程的classloader加载路径是运行时的idea下的lib目录
		 * 2. GroupTemplate.class.getClassLoader() 这种方式获取的classloader是idea为每一个plugin独立的classloader。也只有这种方式，才能加载到我们引入的第三方jar
		 * 3. ClassLoader.getSystemClassLoader() 获取的classloader也是运行时idea环境的lib目录
		 * */
		
		Thread currentThread = Thread.currentThread();
		ClassLoader oldClassloader = currentThread.getContextClassLoader();
		try {
			currentThread.setContextClassLoader(GroupTemplate.class.getClassLoader());
			StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
			Configuration cfg = Configuration.defaultConfiguration();
			cfg.setStatementStart(DELIMITER_STATEMENT_START);
			cfg.setStatementEnd(DELIMITER_STATEMENT_END);
			cfg.setPlaceholderStart(DELIMITER_PLACEHOLDER_START);
			cfg.setPlaceholderEnd(DELIMITER_PLACEHOLDER_END);
			cfg.setHtmlTagFlag(HTML_TAG_FLAG);
			groupTemplate = new GroupTemplate(resourceLoader, cfg);
		} catch (Exception e) {
			logger.error("<------>beetl configuration happend error.{}", e);
		} finally {
			currentThread.setContextClassLoader(oldClassloader);
		}
	}
	
	@Nullable
	@Override
	public Info collectInformation(@NotNull PsiFile file) {
		if (null == groupTemplate) resetConfig();
		/*在idea中，线程模型是单线程的，如果要读写应该按照下面代码，将操作给ui线程。
		 * 如果isValid() 是false，则表示当前文件不应该操作。
		 * */
		String content = ApplicationManager.getApplication()
				                 .runReadAction((Computable<String>) () -> file.isValid() ? editor.getDocument().getText() : null);
		if (StringUtils.isBlank(content)) return null;
		String oldLine = System.getProperty("line.separator");
		System.setProperty("line.separator", StringUtils.LF);
		BeetlException beetlException = groupTemplate.validateTemplate(content);
		System.setProperty("line.separator", oldLine);
		if (null == beetlException) return null;
		ErrorInfo errorInfo = beetlException.toErrorInfo();
		return new Info(file, errorInfo);
	}
	
	/**
	 * 无论是否出现语法错误，都经过beetl本身走一次校验
	 *
	 * @param file      当前文件
	 * @param editor    当前编辑器
	 * @param hasErrors 是否出现语法错误
	 * @return
	 */
	@Nullable
	@Override
	public Info collectInformation(@NotNull PsiFile file, @NotNull Editor editor, boolean hasErrors) {
		this.editor = editor;
		return this.collectInformation(file);
	}
	
	@Nullable
	@Override
	public Result doAnnotate(Info collectedInfo) {
		String content = collectedInfo.getFile().getText();
		String[] lines = content.split(StringUtils.LF);
		List<Anno> annotations = new ArrayList<>();
		ErrorInfo errorInfo = collectedInfo.getErrorInfo();
		int errorTokenLine = errorInfo.getErrorTokenLine() - 1;
		String errorTokenText = errorInfo.getErrorTokenText();
		int startOffset = 0;
		int endOffset = 0;
		for (int i = 0; i < lines.length; i++) {
			if (errorTokenLine > i) {
				startOffset += lines[i].length();
				continue;
			}
			int index = StringUtils.indexOf(lines[i], errorTokenText);
			startOffset += i;
			startOffset += index;
			endOffset = startOffset + errorTokenText.length();
			break;
		}
		TextRange textRange = new TextRange(startOffset, endOffset);
		annotations.add(new Anno(textRange, errorInfo));
		return new Result(annotations);
	}
	
	@Override
	public void apply(@NotNull PsiFile file, Result annotationResult, @NotNull AnnotationHolder holder) {
		List<Anno> annotations = annotationResult.getAnnotations();
		Anno anno = annotations.stream().findFirst().orElse(null);
		holder.createErrorAnnotation(anno.getTextRange(), anno.getErrorInfo().getMsg());
	}
	
	@Override
	public String getPairedBatchInspectionShortName() {
		return "BeetlExternalLint";
	}
	
	public static final class Info {
		@NotNull
		private final PsiFile file;
		@NotNull
		private final ErrorInfo errorInfo;
		
		public Info(@NotNull PsiFile file, ErrorInfo errorInfo) {
			this.file = file;
			this.errorInfo = errorInfo;
		}
		
		@NotNull
		public final PsiFile getFile() {
			return this.file;
		}
		
		@NotNull
		public final ErrorInfo getErrorInfo() {
			return this.errorInfo;
		}
		
		@Override
		public String toString() {
			return "Info(file=" + this.file + ", errorInfo=" + errorInfo + ")";
		}
		
		public int hashCode() {
			return this.file != null ? this.file.hashCode() : 0;
		}
		
		@Override
		public boolean equals(@Nullable Object other) {
			if (this != other) {
				if (other instanceof Info) {
					Info info = (Info) other;
					if (this.file.equals(info.getFile()) && this.errorInfo.equals(info.getErrorInfo())) {
						return true;
					}
				}
				return false;
			} else {
				return true;
			}
		}
	}
	
	public static final class Anno {
		@NotNull
		private final TextRange textRange;
		@NotNull
		private final ErrorInfo errorInfo;
		
		@NotNull
		public TextRange getTextRange() {
			return textRange;
		}
		
		@NotNull
		public final ErrorInfo getErrorInfo() {
			return this.errorInfo;
		}
		
		public Anno(TextRange textRange, @NotNull ErrorInfo errorInfo) {
			this.textRange = textRange;
			this.errorInfo = errorInfo;
		}
		
		@NotNull
		public String toString() {
			return "Anno(textRange=" + this.textRange + ", errorInfo=" + this.errorInfo + ")";
		}
		
		public int hashCode() {
			return this.textRange.hashCode() + this.errorInfo.hashCode();
		}
		
		public boolean equals(@Nullable Object other) {
			if (this != other) {
				if (other instanceof Anno) {
					Anno anno = (Anno) other;
					if (this.textRange.equals(anno.getTextRange()) && this.errorInfo.equals(anno.getErrorInfo())) {
						return true;
					}
				}
				return false;
			} else {
				return true;
			}
		}
	}
	
	public static final class Result {
		@NotNull
		private final List<Anno> annotations;
		
		@NotNull
		public final List<Anno> getAnnotations() {
			return this.annotations;
		}
		
		public Result(@NotNull List<Anno> annotations) {
			this.annotations = annotations;
		}
		
		@NotNull
		public String toString() {
			return "Result(annotations=" + this.annotations + ")";
		}
		
		public int hashCode() {
			return this.annotations != null ? this.annotations.hashCode() : 0;
		}
		
		public boolean equals(@Nullable Object other) {
			if (this != other) {
				if (other instanceof Result) {
					Result result = (Result) other;
					if (CollectionUtils.isEqualCollection(this.annotations, result.getAnnotations())) {
						return true;
					}
				}
				return false;
			} else {
				return true;
			}
		}
	}
}

