package at.tspi.ebnf.parser;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

abstract public class ParserElement implements Iterable<ParserElement> {
	public static final int 					STATUS_BRANCH_DOWN				= 1; // IN ONLY - Another node has pushed us onto the stack and now its our FIRST call
	public static final int 					STATUS_CONTINUE					= 2; // IN ONLY - We continue to get calls (at least the second without interruption)

	public static final int 					STATUS_OK_CONSUME				= 3; // OUT ONLY - The node has consumed the symbol
	public static final int 					STATUS_OK_REDELIVER				= 4; // OUT ONLY - The node succeedes but has not consumed the symbol
	public static final int 					STATUS_FAILED					= 5; // OUT ONLY - The node failed to parse completely

	protected String							productionName;
	protected ParserElement						parentElement;

	protected LinkedList<ParserElement>			childElements;

	protected int								whitespaceMode;
	
	public ParserElement(ParserElement parent) {
		this.parentElement = parent;
		this.childElements = new LinkedList<ParserElement>();
	}
	
	public ParserElement(ParserElement parent, String productionName) {
		this.parentElement = parent;
		this.productionName = productionName;
		this.childElements = new LinkedList<ParserElement>();
	}

	public ParserElement getParent() { return this.parentElement; }
	public String getProductionName() { return this.productionName; }

	public void addChild(ParserElement e) { this.childElements.add(e); }
	public void removeChild(ParserElement e) { this.childElements.remove(e); }
	public int getChildCount() { return childElements.size(); }

	abstract public int parseNext(
		ParserGraphemeCluster nextCluster,
		String dataSourceName,
		int lineNumber,
		int characterPosition,

		Parser parser,
		Preprocessor preproc,
		ParserLogger logger,
		
		boolean lastStepUp,
		int lastResult
	) throws ParserException, IOException;

	abstract public ParserElement factory(ParserElement parent);

	public Iterator<ParserElement> iterator() {
		return childElements.iterator();
	}

	public String toString() { return (productionName == null) ? "<Anonymous Production "+this.getClass().getName()+">" : productionName; }
	public String toString(int depth) {
		String res = "";
		for(int i = 0; i < depth; i++) { res = res + " "; }
		return res + ((productionName == null) ? "<Anonymous Production "+this.getClass().getName()+">" : productionName);
	}

	public String innerText() {
		String res = "";
		for(ParserElement e : childElements) {
			res = res + e.innerText();
		}
		return res;
	}
}