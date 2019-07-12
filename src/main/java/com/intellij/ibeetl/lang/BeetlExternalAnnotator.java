package com.intellij.ibeetl.lang;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.ExternalAnnotator;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import com.intellij.util.containers.ContainerUtil;
import org.apache.commons.collections.CollectionUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
			Configuration cfg = null;
			cfg = Configuration.defaultConfiguration();
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
		String text = editor.getDocument().getText();
		System.out.println(text);
		groupTemplate.validateTemplate(text);
		return super.collectInformation(file);
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
		return super.doAnnotate(collectedInfo);
	}

	@Override
	public void apply(@NotNull PsiFile file, Result annotationResult, @NotNull AnnotationHolder holder) {
		super.apply(file, annotationResult, holder);
	}

	@Override
	public String getPairedBatchInspectionShortName() {
		return "BeetlExternalLint";
	}

	public static final class Info {
		@NotNull
		private final PsiFile file;

		@NotNull
		public final PsiFile getFile() {
			return this.file;
		}

		public Info(@NotNull PsiFile file) {
			this.file = file;
		}


		@NotNull
		public final Info copy(@NotNull PsiFile file) {
			return new Info(file);
		}

		@Override
		public String toString() {
			return "Info(file=" + this.file + ")";
		}

		public int hashCode() {
			return this.file != null ? this.file.hashCode() : 0;
		}

		@Override
		public boolean equals(@Nullable Object other) {
			if (this != other) {
				if (other instanceof Info) {
					Info info = (Info) other;
					if (this.file.equals(info.getFile())) {
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
		private final List annotations;

		@NotNull
		public final List getAnnotations() {
			return this.annotations;
		}

		public Result(@NotNull List annotations) {
			this.annotations = annotations;
		}

		@NotNull
		public final Result copy(@NotNull List annotations) {
			return new Result(annotations);
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


	public static class CustomClassLoader extends URLClassLoader {

		private final Set excludes = new HashSet();

		/**
		 * @param libraries A list of jar files from where classes should
		 *                  be loaded.
		 * @param excludes  A set of classes which should be loaded from
		 *                  the original classloader.
		 */
		CustomClassLoader(Collection libraries, Collection
				excludes) {
			// It is important to pass null as the parent classloader,
			// else the system classloader is set as parent classloader.
			super(getUrls(libraries), null);
			this.excludes.addAll(excludes);
		}

		@Override

		@SuppressWarnings({"NonSynchronizedMethodOverridesSynchronizedMethod"}
		)
		protected Class loadClass(String name, boolean resolve) throws
				ClassNotFoundException {
			return isExcluded(name) ?
					getClass().getClassLoader().loadClass(name) : super.loadClass(name,
					resolve);
		}

		private boolean isExcluded(String name) {
			return name.startsWith("com.intellij.") ||
					excludes.contains(name);
		}

		private static URL[] getUrls(Collection<File> files) {
			return ContainerUtil.map2Array(files, URL.class, file -> {
				try {
					return file.toURI().toURL();
				} catch (MalformedURLException e) {
					return null;
				}
			});
		}
	}
}

