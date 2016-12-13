package de.ws1617.pccl.grammar;

/**
 * Generic symbol class in formal grammars.
 * 
 * @author bjoern
 *
 */
public abstract class Symbol {
	
	// underlying string value.
	private String value;

	/**
	 * Creates an empty Symbol.
	 */
	public Symbol(){
		super();
	}
	
	/**
	 * Creates a symbol based on the given string value.
	 * 
	 * @param value the underlying string value.
	 */
	public Symbol(String value) {
		super();
		this.value = value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symbol other = (Symbol) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
}
