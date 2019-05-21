package com.intellij.ibeetl;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public class BeetlBundle extends AbstractBundle {
	public static final String PATH_TO_BUNDLE = "messages.BeetlBundle";
	private static final AbstractBundle ourInstance = new BeetlBundle();

	public static String message(@NotNull @PropertyKey(resourceBundle = PATH_TO_BUNDLE) String key, @NotNull Object... params) {
		return ourInstance.getMessage(key, params);
	}

	private BeetlBundle() {
		super(PATH_TO_BUNDLE);
	}
}
