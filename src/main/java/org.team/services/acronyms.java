pack org.team.services;

public enum Acronyms {

    facultyACRONYM ("D\t"),
    studentACRONYM ("C\t");

    private final String text;

    Acronyms(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
