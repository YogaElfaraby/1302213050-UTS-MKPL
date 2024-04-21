package lib;

class spouse {
    private String name;
    private String idNumber;

    public spouse(String name, String idNumber) {
        this.name = name;
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public boolean hasNoSpouse() {
        return name == null || name.isEmpty();
    }
}
