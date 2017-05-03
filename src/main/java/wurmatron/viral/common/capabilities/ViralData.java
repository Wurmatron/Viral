package wurmatron.viral.common.capabilities;

public class ViralData implements IViral {

	private int status;

	@Override
	public int status () {
		return status;
	}

	@Override
	public void set (int status) {
		this.status = status;
	}
}
