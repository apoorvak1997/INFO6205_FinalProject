import java.util.Comparator;

public class ChineseToPinyin {
    String pinyin;
    String chinese;

    public ChineseToPinyin(String pinyin, String chinese) {
        this.pinyin = pinyin;
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
