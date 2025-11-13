public abstract class Content {
    protected String rawContent;

    public Content(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    @Override
    public String toString() {
        return rawContent;
    }
}
