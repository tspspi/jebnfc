package at.tspi.ebnf.compiler.ast;

import java.util.LinkedList;
import java.util.Iterator;

import java.lang.Iterable;
import java.lang.IndexOutOfBoundsException;

public class ASTNode implements Iterable<ASTNode> {
	protected String						originalProductionName = null;
	protected LinkedList<ASTNode>			childElements;
	protected ASTNode						parent = null;
	// ToDo: Add filename, line number and character from/to

	public ASTNode() {
		childElements = new LinkedList<ASTNode>();
	}
	public ASTNode originalProductionNameSet(String oldName) { this.originalProductionName = oldName; return this; }
	public String originalProductionNameGet() { return this.originalProductionName; }
	public ASTNode parentGet() {
		return this.parent;
	}
	public ASTNode childGetLast() {
		return childElements.getLast();
	}
	public ASTNode childGet(int position) {
		return childElements.get(position);
	}
	public ASTNode childAppend(ASTNode newChild) {
		childElements.add(newChild);
		newChild.parent = this;
		return this;
	}
	public ASTNode childRemove(ASTNode child) {
		if(!childElements.remove(child)) {
			throw new IndexOutOfBoundsException("Specified node did not exist");
		}
		child.parent = null;
		return this;
	}
	public ASTNode childRemoveAll() {
		this.childElements = new LinkedList<ASTNode>();
		return this;
	}
	public ASTNode childReplace(ASTNode childOld, ASTNode childNew) {
		int idx;
		for(idx = 0; idx < childElements.size(); idx++) {
			if(childElements.get(idx) == childOld) {
				childElements.set(idx, childNew);
				childOld.parent = null;
				childNew.parent = this;
				return this;
			}
		}

		throw new IndexOutOfBoundsException("Specified node did not exist");
	}
	public ASTNode childReplaceCloneChildren(ASTNode childOld, ASTNode childNew) {
		int idx;
		for(idx = 0; idx < childElements.size(); idx++) {
			if(childElements.get(idx) == childOld) {
				childNew.childElements = childOld.childElements;
				childElements.set(idx, childNew);
				childOld.parent = null;
				childOld.childElements = new LinkedList<ASTNode>();
				childNew.parent = this;
				return this;
			}
		}

		throw new IndexOutOfBoundsException("Specified node did not exist");
	}
	public long childGetCount() { return this.childElements.size(); }

	public Iterator<ASTNode> iterator() { return childElements.iterator(); }

	public String toString() {
		return this.getClass().getSimpleName();
	}
	public String toString(int level) {
		int i;
		String res = "";
		for(i = 0; i < level; i++) { res = res + " "; }
		return res + this.toString();
	}
}

