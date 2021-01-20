package za.net.ether.mokse;

public class Content {
    private final long id;
	private final String content;

	public Content(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}