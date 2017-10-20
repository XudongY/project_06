package important;

import java.io.IOException;
import java.io.OutputStream;

public class MultiOutputSteam extends OutputStream {
	OutputStream outputStream1, outputStream2;

	public MultiOutputSteam(OutputStream stream1, OutputStream stream2)
			throws IOException {
		outputStream1 = stream1;
		outputStream2 = stream2;
	}

	@Override
	public void write(int b) throws IOException {
		outputStream1.write(b);
		outputStream2.write(b);
	}

}
