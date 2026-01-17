package semantic;

import enums.SymbolKind;

public class SymbolInfo {
    private final String name;
    private final SymbolKind kind;
    private int slot;
    private int offset = 0;

    public SymbolInfo(String name, SymbolKind kind) {
        this.name = name;
        this.kind = kind;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getName() {
        return name;
    }

    public SymbolKind getKind() {
        return kind;
    }

    public int getSlot() {
        return slot;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        return "[name: " + name + ", kind: " + kind + ", slot: " + slot + ", offset: " + offset + "]";
    }
}
