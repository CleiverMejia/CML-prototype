package semantic;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    protected int nextSlot = 0;
    protected final Map<String, SymbolInfo> symbols = new HashMap<>();
    private Scope parent = null;

    public Scope(Scope parent) {
        this.parent = parent;
    }

    public Scope() {}

    public void putSymbol(String name, SymbolInfo symbol) {
        symbol.setSlot(nextSlot++);

        this.symbols.put(name, symbol);
    }

    public Map<String, SymbolInfo> getSymbols() {
        return symbols;
    }

    public Scope getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return (parent != null ? parent.toString() : "") + symbols.toString() + "\n";
    }
}
