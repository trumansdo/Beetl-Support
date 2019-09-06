package com.intellij.ibeetl.lang;

import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.FilenameIndexImpl;
import com.intellij.util.indexing.ID;
import org.jetbrains.annotations.NotNull;

public class BeetlFileIndex extends FilenameIndexImpl {
	static final ID<String, Void> NAME = ID.create("BeetlFileNameIndex");

	@NotNull
	@Override
	public ID<String, Void> getName() {
		return NAME;
	}
}
