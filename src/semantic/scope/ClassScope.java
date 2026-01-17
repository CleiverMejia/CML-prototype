package semantic.scope;

import semantic.Scope;
import semantic.SymbolInfo;

public class ClassScope extends Scope {
    private int nextOffset = 0;

    public ClassScope(Scope parent) {
        super(parent);
    }

    @Override
    public void putSymbol(String name, SymbolInfo symbol) {
        symbol.setOffset(nextOffset++);

        this.symbols.put(name, symbol);
    }
}
