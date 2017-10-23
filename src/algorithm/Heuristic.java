package algorithm;

public enum Heuristic {
    H1("H1"), H2("H2"), H3("H3");

    private final String name;

    Heuristic(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
