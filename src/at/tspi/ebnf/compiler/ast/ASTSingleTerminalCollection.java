/*
	Collection of a list of allowed single terminals as a choice
*/

package at.tspi.ebnf.compiler.ast;

import at.tspi.ebnf.compiler.ASTException;

public class ASTSingleTerminalCollection extends ASTNode {
	String choices = "";
	long min = 1;
	long max = 1;

	public ASTSingleTerminalCollection() 					{ super(); }
	public ASTSingleTerminalCollection(ASTSingleTerminalCollection other) { super(); this.choices = other.choices; }

	public String choicesGet()								{ return this.choices; }
	public ASTSingleTerminalCollection choicesAddMultiple(String newChoices) {
		this.choices = this.choices + newChoices;
		return this;
	}
	public ASTSingleTerminalCollection choicesAdd(String newChoice) {
		char ch;

		// Check the length is really only 1 character
		if(newChoice.length() != 1) {
			throw new ASTException("ASTSingleTerminalCollection only accepts single characters, not whole character strings");
		}
		ch = newChoice.charAt(0);

		if(choices.indexOf(ch) == -1) {
			// Add new character
			choices = choices + ch;
		}
		return this;
	}
	public ASTSingleTerminalCollection choicesRemove(String oldChoice) {
		return choicesRemove(oldChoice, true);
	}
	public ASTSingleTerminalCollection choicesRemove(String oldChoice, boolean singleCharacter) {
		char ch;
		int idx, idx2;

		// Check the length is really only 1 character
		if(singleCharacter) {
			if(oldChoice.length() != 1) {
				throw new ASTException("ASTSingleTerminalCollection only accepts single characters, not whole character strings");
			}
		}

		for(idx2 = 0; idx2 < oldChoice.length(); idx2++) {
			ch = oldChoice.charAt(idx2);

			idx = choices.indexOf(ch);
			if(singleCharacter) {
				if(idx == -1) {
					throw new ASTException("Deleting character from ASTSingleTerminalCollection which has never been added");
				}
			} else if(idx == -1) {
				continue;
			}
			this.choices = (idx > 0 ? choices.substring(0, idx) : "") + (idx < choices.length() ? choices.substring(idx + 1) : "");
		}

		return this;
	}
	public ASTSingleTerminalCollection minMaxSet(long min, long max) { this.min = min; this.max = max; return this; }
	public long getMin() { return this.min; }
	public long getMax() { return this.max; }

	public String toString() { return this.getClass().getSimpleName() + " ("+this.choices+")" + " min " + this.min + " max " + this.max + ((this.originalProductionName != null) ? (" original "+originalProductionName) : "") ; }
}
