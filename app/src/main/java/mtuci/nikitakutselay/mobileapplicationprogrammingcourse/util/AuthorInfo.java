package mtuci.nikitakutselay.mobileapplicationprogrammingcourse.util;


public class AuthorInfo {

    private String nameLabel;
    private String name;
    private String groupLabel;
    private String group;

    public AuthorInfo(String nameLabel, String name, String groupLabel, String group) {
        this.nameLabel = nameLabel;
        this.name = name;
        this.groupLabel = groupLabel;
        this.group = group;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", formatName(), formatGroup());
    }

    private String formatName() {
        return format(nameLabel, name);
    }

    private String formatGroup() {
        return format(groupLabel, group);
    }

    private String format(String label, String value) {
        return String.format("%s: %s", label, value);
    }
}
